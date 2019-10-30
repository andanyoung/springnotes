# WebMagic
![](http://webmagic.io/images/logo.jpeg)
## 1.WebMagic概览
WebMagic项目代码分为核心和扩展两部分。核心部分(webmagic-core)是一个精简的、模块化的爬虫实现，而扩展部分则包括一些便利的、实用性的功能。WebMagic的架构设计参照了Scrapy，目标是尽量的模块化，并体现爬虫的功能特点。

这部分提供非常简单、灵活的API，在基本不改变开发模式的情况下，编写一个爬虫。

扩展部分(webmagic-extension)提供一些便捷的功能，例如注解模式编写爬虫等。同时内置了一些常用的组件，便于爬虫开发。

## 2. 总体架构
WebMagic的结构分为`Downloader`(处理)、`PageProcessor`(管理)、`Scheduler`(下载)、`Pipeline`(持久化)四大组件，并由`Spide`r将它们彼此组织起来。这四大组件对应爬虫生命周期中的下载、处理、管理和持久化等功能。`WebMagic`的设计参考了`Scapy`，但是实现方式更Java化一些。

而`Spider`则将这几个组件组织起来，让它们可以互相交互，流程化的执行，可以认为`Spider`是一个大的容器，它也是`WebMagic`逻辑的核心。

WebMagic总体架构图如下：
![](../../image/webmagic.png)

### 2.1 WebMagic的四个组件
#### 1.Downloader
Downloader负责从互联网上下载页面，以便后续处理。WebMagic默认使用了Apache HttpClient作为下载工具。

#### 2.PageProcessor
PageProcessor负责解析页面，抽取有用信息，以及发现新的链接。WebMagic使用Jsoup作为HTML解析工具，并基于其开发了解析XPath的工具Xsoup。

在这四个组件中，PageProcessor对于每个站点每个页面都不一样，是需要使用者定制的部分。

#### 3.Scheduler
Scheduler负责管理待抓取的URL，以及一些去重的工作。WebMagic默认提供了JDK的内存队列来管理URL，并用集合来进行去重。也支持使用Redis进行分布式管理。

除非项目有一些特殊的分布式需求，否则无需自己定制Scheduler。

#### 4.Pipeline
Pipeline负责抽取结果的处理，包括计算、持久化到文件、数据库等。WebMagic默认提供了“输出到控制台”和“保存到文件”两种结果处理方案。

Pipeline定义了结果保存的方式，如果你要保存到指定数据库，则需要编写对应的Pipeline。对于一类需求一般只需编写一个Pipeline。

### 2.2 用于数据流转的对象
#### 1. Request
Request是对URL地址的一层封装，一个Request对应一个URL地址。

它是PageProcessor与Downloader交互的载体，也是PageProcessor控制Downloader唯一方式。

除了URL本身外，它还包含一个Key-Value结构的字段extra。你可以在extra中保存一些特殊的属性，然后在其他地方读取，以完成不同的功能。例如附加上一个页面的一些信息等。

#### 2. Page
Page代表了从Downloader下载到的一个页面——可能是HTML，也可能是JSON或者其他文本格式的内容。

Page是WebMagic抽取过程的核心对象，它提供一些方法可供抽取、结果保存等。在第四章的例子中，我们会详细介绍它的使用。

#### 3. ResultItems
ResultItems相当于一个Map，它保存PageProcessor处理的结果，供Pipeline使用。它的API与Map很类似，值得注意的是它有一个字段skip，若设置为true，则不应被Pipeline处理。

### 2.3 控制爬虫运转的引擎--Spider
Spider是WebMagic内部流程的核心。Downloader、PageProcessor、Scheduler、Pipeline都是Spider的一个属性，这些属性是可以自由设置的，通过设置这个属性可以实现不同的功能。Spider也是WebMagic操作的入口，它封装了爬虫的创建、启动、停止、多线程等功能。下面是一个设置各个组件，并且设置多线程和启动的例子。详细的Spider设置请看第四章——爬虫的配置、启动和终止。

```
public static void main(String[] args) {
    Spider.create(new GithubRepoPageProcessor())
            //从https://github.com/code4craft开始抓    
            .addUrl("https://github.com/code4craft")
            //设置Scheduler，使用Redis来管理URL队列
            .setScheduler(new RedisScheduler("localhost"))
            //设置Pipeline，将结果以json方式保存到文件
            .addPipeline(new JsonFilePipeline("D:\\data\\webmagic"))
            //开启5个线程同时执行
            .thread(5)
            //启动爬虫
            .run();
}
```

### 2.4 快速上手
上面介绍了很多组件，但是其实使用者需要关心的没有那么多，因为大部分模块WebMagic已经提供了默认实现。

一般来说，对于编写一个爬虫，PageProcessor是需要编写的部分，而Spider则是创建和控制爬虫的入口。在第四章中，我们会介绍如何通过定制PageProcessor来编写一个爬虫，并通过Spider来启动。
## 3. 快速开始
```
<dependency>
    <groupId>us.codecraft</groupId>
    <artifactId>webmagic-core</artifactId>
    <version>0.7.3</version>
</dependency>
<dependency>
    <groupId>us.codecraft</groupId>
    <artifactId>webmagic-extension</artifactId>
    <version>0.7.3</version>
</dependency>
```
WebMagic使用slf4j-log4j12作为slf4j的实现.如果你自己定制了slf4j的实现，请在项目中去掉此依赖。
```
<dependency>
    <groupId>us.codecraft</groupId>
    <artifactId>webmagic-extension</artifactId>
    <version>0.7.3</version>
    <exclusions>
        <exclusion>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```
### 第一个爬虫项目
在你的项目中添加了WebMagic的依赖之后，即可开始第一个爬虫的开发了！我们这里拿一个抓取Github信息的例子：
```
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoPageProcessor implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public void process(Page page) {
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            //skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor()).addUrl("https://github.com/code4craft").thread(5).run();
    }
}
```
[详细教程](http://webmagic.io/docs/zh/)  
[使用Selenium来抓取动态加载的页面](https://my.oschina.net/flashsword/blog/147334)