# elasticsearch

## [lucene 教程](https://github.com/AndyYoungCN/springnotes/blob/master/notes/lucene/lucene%E6%95%99%E6%A1%88.pdf)

## [elasticsearch 安装与配置介绍](https://blog.csdn.net/agonie201218/article/details/103596165)

## 抽象类比

<table>
<thead>
<tr class="header">
<th>MySql</th>
<th>Es</th>
</tr>
</thead>
<tbody>
<tr class="odd">
<td>Table</td>
<td>Index(Type)</td>
</tr>
<tr class="even">
<td>Row</td>
<td>Document</td>
</tr>
<tr class="odd">
<td>Column</td>
<td>Field</td>
</tr>
<tr class="even">
<td>Schema</td>
<td>Mapping</td>
</tr>
<tr class="odd">
<td>SQL</td>
<td>DSL</td>
</tr>
</tbody>
</table>

## <span>一些概念</span>[#](#idx_1)

### <span>cluster(集群)</span>[#](#idx_2)

一个 ES 集群由一个或多个节点（Node）组成，每个集群都有一个 cluster name 作为标识。

### <span>node(节点)</span>[#](#idx_3)

一个 ES 实例就是一个 node，一个机器可以有多个实例，所以并不能说一台机器就是一个 node，大多数情况下每个 node 运行在一个独立的环境或虚拟机上。

### <span>index(索引)</span>[#](#idx_4)

即一系列 documents 的集合。每个索引可以有多个 type，不过 7.0 之后将会被废弃，略过。

### <span>mappings(映射)</span>[#](#idx_5)

映射, 就像数据库中的 schema ，描述了文档可能具有的字段或 属性 、每个字段的数据类型—比如 string, integer 或 date —以及 Lucene 是如何索引和存储这些字段的。

- 字符串: string
- 整数: byte, short, integer, long
- 浮点数: float, double
- 布尔型: boolean
- 日期: date
- keyword：存储数据时候，不会分词建立索引
- text：存储数据时候，会自动分词，并生成索引

### <span>shard(分片)</span>[#](#idx_6)

ES 是分布式搜索引擎，每个索引有一个或多个分片，索引的数据被分配到各个分片上，相当于一桶水用了 N 个杯子装。分片有助于横向扩展，N 个分片会被尽可能平均地（rebalance）分配在不同的节点上（例如你有 2 个节点，4 个主分片(不考虑备份)，那么每个节点会分到 2 个分片，后来你增加了 2 个节点，那么你这 4 个节点上都会有 1 个分片，这个过程叫 relocation，ES 感知后自动完成)。分片是独立的，对于一个 Search Request 的行为，每个分片都会执行这个 Request。

### <span>replica(复制)</span>[#](#idx_7)

可以理解为备份分片，相应地有 primary shard（主分片)，主分片和备分片不会出现在同一个节点上（防止单点故障），默认情况下一个索引创建 5 个分片一个备份（即 5primary+5replica=10 个分片）。如果你只有一个节点，那么 5 个 replica 都无法分配（unassigned），此时 cluster status 会变成 Yellow。

### <span>倒排索引</span>[#](#idx_8)

Elasticsearch 使用一种称为 倒排索引 的结构，它适用于快速的全文搜索。一个倒排索引由文档中所有不重复词的列表构成，对于其中每个词，有一个包含它的文档列表。

### <span>analysis</span>[#](#idx_9)

Document 中的数据是如何转变成倒排索引的，以及查询语句是如何转换成一个个词(Term)使高效率文本搜索变得可行，这种转换数据的过程就称为文本分析(analysis)。

elasticsearch 会用某种算法(Tokenizer)对要建索引的文档进行分析， 从文档中提取出若干 Token(词元)， 这些算法称为 Tokenizer(分词器)， 这些 Token 会被进一步处理， 比如转成小写等， 这些进一步的处理算法被称为 Filter(过滤器), 被处理后的结果被称为 Term(词)， 文档中包含了几个这样的 Term 被称为 Frequency(词频)。 引擎会建立 Term 和原文档的 Inverted Index(倒排索引)， 这样就能根据 Term 很快到找到源文档了。

文本分析(analysis)工作由 analyzer(分析器)组件负责。analyzer 由一个分词器(tokenizer)和 0 个或者多个过滤器(filter)组成,也可能会有 0 个或者多个字符映射器(character mappers)组成。

tokenizer 用来把文本拆分成一个个的 Token。Token 包含了比较多的信息，比如 Term 在文本的中的位置及 Term 原始文本，以及 Term 的长度。文本经过 tokenizer 处理后的结果称为 token stream。token stream 其实就是一个个 Token 的顺序排列。token stream 将等待着 filter 来处理。

filter 链将用来处理 Token Stream 中的每一个 token。这些处理方式包括删除 Token, 改变 Token，甚至添加新的 Token。比如变小写，去掉里面的 HTML 标记， 这些处理的算法被称为 Character Filter(字符过滤器)

### <span>ES 集群状态有三种：</span>[#](#idx_10)

- Green：所有主分片和备份分片都准备就绪（分配成功），即使有一台机器挂了（假设一台机器一个实例），数据都不会丢失，但会变成 Yellow 状态

- Yellow：所有主分片准备就绪，但存在至少一个主分片（假设是 A）对应的备份分片没有就绪，此时集群属于警告状态，意味着集群高可用和容灾能力下降，如果刚好 A 所在的机器挂了，并且你只设置了一个备份（已处于未就绪状态），那么 A 的数据就会丢失（查询结果不完整），此时集群进入 Red 状态

- Red：至少有一个主分片没有就绪（直接原因是找不到对应的备份分片成为新的主分片）,此时查询的结果会出现数据丢失（不完整）

### <span>replica 作用：</span>[#](#idx_11)

- 容灾：primary 分片丢失，replica 分片就会被顶上去成为新的主分片，同时根据这个新的主分片创建新的 replica，集群数据安然无恙;
- 提高查询性能：replica 和 primary 分片的数据是相同的，所以对于一个 query 既可以查主分片也可以查备分片，在合适的范围内多个 replica 性能会更优（但要考虑资源占用也会提升[cpu/disk/heap]），另外 index request 只能发生在主分片上，replica 不能执行 index request。

对于一个索引，除非重建索引否则不能调整分片的数目（主分片数, number_of_shards），但可以随时调整 replica 数(number_of_replicas)。

## <span>安装运行</span>[#](#idx_12)

通过 docker 启动一个单节点集群

```
$ docker run -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:6.7.2
```

## <span>与 Elasticsearch 交互</span>[#](#idx_13)

使用 RESTful API 通过端口 9200 和 Elasticsearch 进行通信

```
$ curl -X<METHOD> http://localhost:9200/<PATH>?<QUERY_STRING> -d '<BODY>'
```

### <span>操作索引</span>[#](#idx_14)

新建索引时可以指定设置或者映射，也可以不指定自动生成

```
$ PUT foo_index
{
    "settings": {
        "number_of_shards" :   1,   #每个索引的主分片数，默认值是 5 。这个配置在索引创建后不能修改。
        "number_of_replicas" : 0    #每个主分片的副本数，默认值是 1 。对于活动的索引库，这个配置可以随时修改。
     },
    "mappings": {
        "type_one": { ... any mappings ... },
        "type_two": { ... any mappings ... },
        ...
    }
}
```

修改索引设置

```
$ PUT foo_index/_settings
{
    "number_of_replicas": 1
}
```

修改索引映射

```
$ PUT foo_index/_mappings/_doc
{
    "properties": {...}
}
```

删除索引

```
$ DELETE foo_index
```

### <span>操作文档</span>[#](#idx_15)

使用 put 新建文档项指定 id 为 1

```
$ PUT foo_index/_doc/1
{
  "name": "demo 1",
  "body": "demo foo bar"
}
```

使用 post 可以不指定 id 来新建文档，自动生成 id

```
$ POST foo_index/_doc
{
  "name": "demo test",
  "body": "demo test"
}
```

查看 id=1 的文档

```
$ GET foo_index/_doc/1
```

### <span>搜索</span>[#](#idx_16)

使用 `curl -X GET foo_index/_search -d 'body'` 来搜索文档

match_all 匹配所有文档，等价于空查询{}

```
{
  "query": {
    "match_all": {}
  }
}
```

mathc 匹配包含，如果在一个精确值的字段上使用 match，例如数字、日期、布尔或者一个 not_analyzed 字符串字段，那么它将会精确匹配给定的值：

```
{
  "query": {
    "match": {
        "name": "demo"
    }
  }
}
```

多字段 match 使用 multi_match

```
{
    "multi_match": {
        "query":    "full text search",
        "fields":   [ "title", "body" ]
    }
}
```

term 查询被用于精确值匹配，这些精确值可能是数字、时间、布尔或者那些 not_analyzed 的字符串，term 查询对于输入的文本不分析 ，所以它将给定的值进行精确查询

```
{
  "query": {
    "term": {
        "name": "demo"
    }
  }
}
```

terms 查询被用于匹配多个精确值

```
{
  "query": {
    "terms": {
        "name": ["demo", "demo1"]
    }
  }
}
```

合并(bool)查询可以组合多种查询方法

```
{
    "bool": {
        "must":     { "match": { "tweet": "elasticsearch" }},
        "must_not": { "match": { "name":  "mary" }},
        "should":   { "match": { "tweet": "full text" }},
        "filter":   { "range": { "age" : { "gt" : 30 }} }
    }
}
```

### 聚合搜索

## 其他教程
