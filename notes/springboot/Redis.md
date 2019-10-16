# Redis
![Redise基础教程](https://upload-images.jianshu.io/upload_images/7896890-42d0747d8e2a3d8f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/700/format/webp)
[Redise基础教程](https://www.runoob.com/redis/redis-tutorial.html)
 
# Spring boot 集成Redis
## 关于spring-redis
spring-data-redis针对jedis提供了如下功能：
- 1.连接池自动管理，提供了一个高度封装的“RedisTemplate”类
- 2.针对jedis客户端中大量api进行了归类封装,将同一类型操作封装为operation接口
  - ValueOperations：简单K-V操作
  - SetOperations：set类型数据操作
  - ZSetOperations：zset类型数据操作
  - HashOperations：针对map类型的数据操作
  - ListOperations：针对list类型的数据操作
3.提供了对key的“bound”(绑定)便捷化操作API，可以通过bound封装指定的key，然后进行一系列的操作而无须“显式”的再次指定Key，即BoundKeyOperations：
  - BoundValueOperations  
  - BoundSetOperations
  - BoundListOperations
  - BoundSetOperations
  - BoundHashOperations
4.将事务操作封装，有容器控制。
5.针对数据的“序列化/反序列化”，提供了多种可选择策略(RedisSerializer)
- `JdkSerializationRedisSerializer`：POJO对象的存取场景，使用JDK本身序列化机制，将pojo类通过`ObjectInputStream/ObjectOutputStream`进行序列化操作，最终redis-server中将存储字节序列。是目前最常用的序列化策略。
- `StringRedisSerializer`：Key或者value为字符串的场景，根据指定的charset对数据的字节序列编码成string，是“new String(bytes, charset)”和“string.getBytes(charset)”的直接封装。是最轻量级和高效的策略。
- `JacksonJsonRedisSerializer`：jackson-json工具提供了javabean与json之间的转换能力，可以将pojo实例序列化成json格式存储在redis中，也可以将json格式的数据转换成pojo实例。因为jackson工具在序列化和反序列化时，需要明确指定Class类型，因此此策略封装起来稍微复杂。【需要jackson-mapper-asl工具支持】
- `OxmSerializer`：提供了将javabean与xml之间的转换能力，目前可用的三方支持包括jaxb，apache-xmlbeans；redis存储的数据将是xml工具。不过使用此策略，编程将会有些难度，而且效率最低；不建议使用。【需要spring-oxm模块的支持】
## 关于key的设计
### key的存活时间：
无论什么时候，只要有可能就利用key超时的优势。一个很好的例子就是储存一些诸如临时认证key之类的东西。当你去查找一个授权key时——以OAUTH为例——通常会得到一个超时时间。
这样在设置key的时候，设成同样的超时时间，Redis就会自动为你清除。
### 关系型数据库的redis
- 1: 把表名转换为key前缀 如, tag:
- 2: 第2段放置用于区分区key的字段--对应mysql中的主键的列名,如userid
- 3: 第3段放置主键值,如2,3,4…., a , b ,c
- 4: 第4段,写要存储的列名
> 例：user:userid:9:username
## Redis的数据类型
### String字符串
- string是redis最基本的类型，一个key对应一个value。
- string类型是二进制安全的。意思是redis的string可以包含任何数据。比如jpg图片或者序列化的对象 。
- string类型是Redis最基本的数据类型，一个键最大能存储512MB。
String类型的操作参考：
[http://www.runoob.com/redis/redis-strings.html](http://www.runoob.com/redis/redis-strings.html)
### 链表(list)
- redis列表是简单的字符串列表，排序为插入的顺序。列表的最大长度为2^32-1。
- redis的列表是使用链表实现的，这意味着，即使列表中有上百万个元素，增加一个元素到列表的头部或尾部的操作都是在常量的时间完成。
- 可以用列表获取最新的内容（像帖子，微博等），用ltrim很容易就会获取最新的内容，并移除旧的内容。
- 用列表可以实现生产者消费者模式，生产者调用lpush添加项到列表中，消费者调用rpop从列表中提取，如果没有元素，则轮询去获取，或者使用brpop等待生产者添加项到列表中。
List类型的操作参考：
[http://www.runoob.com/redis/redis-lists.html](http://www.runoob.com/redis/redis-lists.html)
### 集合(set)
- redis集合是无序的字符串集合，集合中的值是唯一的，无序的。可以对集合执行很多操作，例如，测试元素是否存在，对多个集合执行交集、并集和差集等等。
- 我们通常可以用集合存储一些无关顺序的，表达对象间关系的数据，例如用户的角色，可以用sismember很容易就判断用户是否拥有某个角色。
- 在一些用到随机值的场合是非常适合的，可以用 srandmember/spop 获取/弹出一个随机元素。同时，使用@EnableCaching开启声明式缓存支持，这样就可以使用基于注解的缓存技术。注解缓存是一个对缓存使用的抽象，通过在代码中添加下面的一些注解，达到缓存的效果。
Set类型的操作参考：
[http://www.runoob.com/redis/redis-sets.html](http://www.runoob.com/redis/redis-sets.html)
### ZSet 有序集合
- 有序集合由唯一的，不重复的字符串元素组成。有序集合中的每个元素都关联了一个浮点值，称为分数。可以把有序看成hash和集合的混合体，分数即为hash的key。
- 有序集合中的元素是按序存储的，不是请求时才排序的。
ZSet类型的操作类型
[http://www.runoob.com/redis/redis-sorted-sets.html](http://www.runoob.com/redis/redis-sorted-sets.html)
### Hash-哈希
- redis的哈希值是字符串字段和字符串之间的映射，是表示对象的完美数据类型。
- 哈希中的字段数量没有限制，所以可以在你的应用程序以不同的方式来使用哈希。
Hash类型的操作参考：
[http://www.runoob.com/redis/redis-hashes.html](http://www.runoob.com/redis/redis-hashes.html)
## springboot 与redis的整合
- Lettuce
Lettuce 和 Jedis 的都是连接Redis Server的客户端程序。Jedis在实现上是直连redis server，多线程环境下非线程安全，除非使用连接池，为每个Jedis实例增加物理连接。Lettuce基于Netty的连接实例（StatefulRedisConnection），可以在多个线程间并发访问，且线程安全，满足多线程环境下的并发访问，同时它是可伸缩的设计，一个连接实例不够的情况也可以按需增加连接实例。
> 在 pom.xml 中spring-boot-starter-data-redis的依赖，Spring Boot2.x 后底层不在是Jedis默认为Lettuce，如果做版本升级的朋友需要注意下
- pom文件
具体依赖如下：
```
<dependencies>
        <dependency>
            <groupId>org.apache.commons</groupId>
            <artifactId>commons-pool2</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-redis</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
</dependencies>
```
> application.properties
```
server.port=8888
# Redis数据库索引（默认为0）
spring.redis.database=0
# Redis服务器地址
spring.redis.host=127.0.0.1
# Redis服务器连接端口
spring.redis.port=6379
# Redis服务器连接密码（默认为空）
spring.redis.password=
# 连接池最大连接数（使用负值表示没有限制）
spring.redis.lettuce.pool.max-active=8
# 连接池最大阻塞等待时间（使用负值表示没有限制）
spring.redis.lettuce.pool.max-wait=10000ms
# 连接池中的最大空闲连接
spring.redis.lettuce.pool.max-idle=8
# 连接池中的最小空闲连接
spring.redis.lettuce.pool.min-idle=0
# 连接超时时间（毫秒）
spring.redis.timeout=10000ms
```
> 注意如果设置了redis连接池必须要添加`commons-pool2`依赖
- redisTemplate的配置
新建一个redisConfig类，进行相关bean的配置：
```
@Configuration
public class RedisConfig extends CachingConfigurerSupport {

  @Override
  public KeyGenerator keyGenerator() {
    return (target, method, objects) -> {
      StringBuilder sb = new StringBuilder();
      sb.append(target.getClass().getName());
      sb.append("::" + method.getName() + ":");
      for (Object obj : objects) {
        sb.append(obj.toString());
      }
      return sb.toString();
    };
  }

  /** * 选择redis作为默认缓存工具 * @param redisTemplate * @return */
  //  @Bean
  //  public CacheManager cacheManager(RedisTemplate<String, Object> redisTemplate) {
  //    RedisCacheManager rcm = new RedisCacheManager(redisTemplate);
  //    return rcm;
  //  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    // 配置连接工厂
    template.setConnectionFactory(factory);
    // 使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
    Jackson2JsonRedisSerializer<Object> jacksonSeial =
        new Jackson2JsonRedisSerializer<>(Object.class);

    ObjectMapper om = new ObjectMapper();
    // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
    om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
    // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
    om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
    jacksonSeial.setObjectMapper(om);

    // 值采用json序列化
    template.setValueSerializer(jacksonSeial);
    // 使用StringRedisSerializer来序列化和反序列化redis的key值
    template.setKeySerializer(new StringRedisSerializer());

    // 设置hash key 和value序列化模式
    template.setHashKeySerializer(new StringRedisSerializer());
    template.setHashValueSerializer(jacksonSeial);
    template.afterPropertiesSet();

    return template;
  }

  /**
   * 对hash类型的数据操作
   *
   * @param redisTemplate
   * @return
   */
  @Bean
  public HashOperations<String, String, Object> hashOperations(
      RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForHash();
  }

  /**
   * 对redis字符串类型数据操作
   *
   * @param redisTemplate
   * @return
   */
  @Bean
  public ValueOperations<String, Object> valueOperations(
      RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForValue();
  }

  /**
   * 对链表类型的数据操作
   *
   * @param redisTemplate
   * @return
   */
  @Bean
  public ListOperations<String, Object> listOperations(
      RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForList();
  }

  /**
   * 对无序集合类型的数据操作
   *
   * @param redisTemplate
   * @return
   */
  @Bean
  public SetOperations<String, Object> setOperations(RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForSet();
  }

  /**
   * 对有序集合类型的数据操作
   *
   * @param redisTemplate
   * @return
   */
  @Bean
  public ZSetOperations<String, Object> zSetOperations(
      RedisTemplate<String, Object> redisTemplate) {
    return redisTemplate.opsForZSet();
  }
}
```
> spring-redis中使用了RedisTemplate来进行redis的操作，通过泛型的K和V设置键值对的对象类型。这里使用了string作为key的对象类型，值为Object。
对于Object，spring-redis默认使用了jdk自带的序列化，不推荐使用默认了。所以使用了json的序列化方式
- 对spring-redis对redis的五种数据类型也有支持
>HashOperations：对hash类型的数据操作
ValueOperations：对redis字符串类型数据操作
ListOperations：对链表类型的数据操作
SetOperations：对无序集合类型的数据操作
ZSetOperations：对有序集合类型的数据操作
- redis操作的工具类
```
@Component
public class RedisService {
  @Autowired private RedisTemplate<String, String> redisTemplate;
  /** * 默认过期时长，单位：秒 */
  public static final long DEFAULT_EXPIRE = 60 * 60 * 24;
  /** * 不设置过期时长 */
  public static final long NOT_EXPIRE = -1;

  public boolean existsKey(String key) {
    return redisTemplate.hasKey(key);
  }
  /** * 重名名key，如果newKey已经存在，则newKey的原值被覆盖 * * @param oldKey * @param newKey */
  public void renameKey(String oldKey, String newKey) {
    redisTemplate.rename(oldKey, newKey);
  }
  /** * newKey不存在时才重命名 * * @param oldKey * @param newKey * @return 修改成功返回true */
  public boolean renameKeyNotExist(String oldKey, String newKey) {
    return redisTemplate.renameIfAbsent(oldKey, newKey);
  }
  /** * 删除key * * @param key */
  public void deleteKey(String key) {
    redisTemplate.delete(key);
  }
  /** * 删除多个key * * @param keys */
  public void deleteKey(String... keys) {
    Set<String> kSet = Stream.of(keys).map(k -> k).collect(Collectors.toSet());
    redisTemplate.delete(kSet);
  }
  /** * 删除Key的集合 * * @param keys */
  public void deleteKey(Collection<String> keys) {
    Set<String> kSet = keys.stream().map(k -> k).collect(Collectors.toSet());
    redisTemplate.delete(kSet);
  }
  /** * 设置key的生命周期 * * @param key * @param time * @param timeUnit */
  public void expireKey(String key, long time, TimeUnit timeUnit) {
    redisTemplate.expire(key, time, timeUnit);
  }
  /** * 指定key在指定的日期过期 * * @param key * @param date */
  public void expireKeyAt(String key, Date date) {
    redisTemplate.expireAt(key, date);
  }
  /** * 查询key的生命周期 * * @param key * @param timeUnit * @return */
  public long getKeyExpire(String key, TimeUnit timeUnit) {
    return redisTemplate.getExpire(key, timeUnit);
  }
  /** * 将key设置为永久有效 * * @param key */
  public void persistKey(String key) {
    redisTemplate.persist(key);
  }
}
```
- redis的key工具类
```
public class Redis {
  /**
   * redis的key 形式为： 表名:主键名:主键值:列名 *
   *
   * @param tableName 表名
   * @param majorKey 主键名
   * @param majorKeyValue 主键值
   * @param column 列名
   * @return
   */
  public static String getKeyWithColumn(
      String tableName, String majorKey, String majorKeyValue, String column) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tableName).append(":");
    buffer.append(majorKey).append(":");
    buffer.append(majorKeyValue).append(":");
    buffer.append(column);
    return buffer.toString();
  }
  /**
   * * redis的key * 形式为： * 表名:主键名:主键值 * * @param tableName 表名 * @param majorKey 主键名 * @param
   * majorKeyValue 主键值 * @return
   */
  public static String getKey(String tableName, String majorKey, String majorKeyValue) {
    StringBuffer buffer = new StringBuffer();
    buffer.append(tableName).append(":");
    buffer.append(majorKey).append(":");
    buffer.append(majorKeyValue).append(":");
    return buffer.toString();
  }
}
```

> [高级进阶-通过spring-boot-starter-data-redis源码了解starter和autoconfigure模块](https://github.com/AndyYoungCN/springnotes/blob/master/notes/source/1.spring-boot-starter-data-redis.md)
[demo](https://github.com/AndyYoungCN/springbootexample/tree/master/source/redis)