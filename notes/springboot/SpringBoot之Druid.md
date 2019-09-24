# druid
## druid 介绍
Druid是Java语言中最好的数据库连接池。Druid能够提供强大的监控和扩展功能。DruidDataSource支持的数据库：
理论上说，支持所有有jdbc驱动的数据库。最近发现Druid在springboot框架下有更加好用的Druid Spring Boot Starter，可以省去原本写Druid的一些配置文件或者@Configuration来配置，直接将配置写在application.yml/application.properties里，看起来更简单一些。
[demo获取,欢迎start](https://github.com/AndyYoungCN/springbootexample/tree/master/source/druid4mybatis)
## 快速开始
### 使用`@Configuration`
> 该方法配置起来较麻烦不做介绍参考[alibaba/druid](https://github.com/alibaba/druid/wiki/%E9%A6%96%E9%A1%B5)
- pox.xml 
```
<dependency>
		<groupId>com.alibaba</groupId>
		<artifactId>druid</artifactId>
		<version>${druid-version}</version>
</dependency>
```
## 使用Spring Boot Starter
[仓库地址](https://github.com/alibaba/druid/tree/master/druid-spring-boot-starter)
- pox.xml
```
<dependency>
   <groupId>com.alibaba</groupId>
   <artifactId>druid-spring-boot-starter</artifactId>
   <version>1.1.17</version>
</dependency>
```
- 添加配置
```
spring.datasource.url= 
spring.datasource.username=
spring.datasource.password=
# ...其他配置（可选，不是必须的，使用内嵌数据库的话上述三项也可省略不填）
```
### 配置属性
Druid Spring Boot Starter 配置属性的名称完全遵照 Druid，你可以通过 Spring Boot 配置文件来配置Druid数据库连接池和监控，如果没有配置则使用默认值。
- JDBC 配置
```
spring.datasource.druid.url= # 或spring.datasource.url= 
spring.datasource.druid.username= # 或spring.datasource.username=
spring.datasource.druid.password= # 或spring.datasource.password=
spring.datasource.druid.driver-class-name= #或 spring.datasource.driver-class-name=
```
- 连接池配置
```
# 下面为连接池的补充设置，应用到上面所有数据源中
# 初始化大小，最小，最大
spring.datasource.druid.initialSize=1
spring.datasource.druid.minIdle=5
spring.datasource.druid.maxActive=20
# 配置获取连接等待超时的时间
spring.datasource.druid.maxWait=60000
# 配置间隔多久才进行一次检测，检测需要关闭的空闲连接，单位是毫秒
spring.datasource.druid.timeBetweenEvictionRunsMillis=60000
# 配置一个连接在池中最小生存的时间，单位是毫秒
spring.datasource.druid.minEvictableIdleTimeMillis=300000
spring.datasource.druid.validationQuery=SELECT 1 FROM DUAL
spring.datasource.druid.testWhileIdle=true
spring.datasource.druid.testOnBorrow=false
spring.datasource.druid.testOnReturn=false
# 打开PSCache，并且指定每个连接上PSCache的大小
spring.datasource.druid.poolPreparedStatements=true
spring.datasource.druid.maxPoolPreparedStatementPerConnectionSize=20
# 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
spring.datasource.druid.filters=stat,wall,logFilter
```
- 监控配置
```
# 通过connectProperties属性来打开mergeSql功能；慢SQL记录
spring.datasource.druid.connectionProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
#  合并多个DruidDataSource的监控数据
spring.datasource.druid.useGlobalDataSourceStat=true
# druid 监控的配置 如果不使用 druid 的监控功能的话 以下配置就不是必须的
# 本项目监控台访问地址: http://localhost:8080/druid/login.html
# WebStatFilter用于采集web-jdbc关联监控的数据。
# 更多配置可参见: https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_%E9%85%8D%E7%BD%AEWebStatFilter
spring.datasource.druid.web-stat-filter.enabled=true
spring.datasource.druid.web-stat-filter.url-pattern=/*
spring.datasource.druid.web-stat-filter.exclusions="*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*"
# 配置WallFilter
spring.datasource.druid.filter.wall.enabled=true
spring.datasource.druid.filter.wall.db-type=h2
spring.datasource.druid.filter.wall.config.delete-allow=false
spring.datasource.druid.filter.wall.config.drop-table-allow=false
# Druid内置提供了一个StatViewServlet用于展示Druid的统计信息。
# 更多配置可参见:https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_StatViewServlet%E9%85%8D%E7%BD%AE
spring.datasource.druid.stat-view-servlet.enabled=true
# 需要拦截的url
spring.datasource.druid.stat-view-servlet.url-pattern=/druid/*
spring.datasource.druid.stat-view-servlet.allow=127.0.0.1
# 允许清空统计数据
spring.datasource.druid.stat-view-servlet.reset-enable=true
spring.datasource.druid.stat-view-servlet.login-username=druid
spring.datasource.druid.stat-view-servlet.login-password=druid
# 日志管理 开启内置的logback日志 spring.datasource.druid.filters=logFilter
spring.datasource.druid.filter.slf4j.enabled=true 
spring.datasource.druid.filter.slf4j.statement-create-after-log-enabled=false 
spring.datasource.druid.filter.slf4j.statement-close-after-log-enabled=false 
spring.datasource.druid.filter.slf4j.result-set-open-after-log-enabled=false 
spring.datasource.druid.filter.slf4j.result-set-close-after-log-enabled=false
```
Druid Spring Boot Starter 不仅限于对以上配置属性提供支持，DruidDataSource 内提供setter方法的可配置属性都将被支持。你可以参考WIKI文档或通过IDE输入提示来进行配置。配置文件的格式你可以选择.properties或.yml，效果是一样的，在配置较多的情况下推荐使用.yml。
## Filter

[WallFilter](https://github.com/alibaba/druid/wiki/简介_WallFilter)
WallFilter的功能是防御SQL注入攻击。它是基于SQL语法分析，理解其中的SQL语义，然后做处理的，智能，准确，误报率低。
[StatFilter](https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_StatFilter)
Druid内置提供一个StatFilter，用于统计监控信息。
[StatViewServlet](https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_StatViewServlet%E9%85%8D%E7%BD%AE)
Druid内置提供了一个StatViewServlet用于展示Druid的统计信息。
[logFilter](https://github.com/alibaba/druid/wiki/%E9%85%8D%E7%BD%AE_LogFilter)
ruid内置提供了四种LogFilter（Log4jFilter、Log4j2Filter、CommonsLogFilter、Slf4jLogFilter），用于输出JDBC执行的日志。这些Filter都是Filter-Chain扩展机制中的Filter，所以配置方式可以参考这里：Filter配置

这个StatViewServlet的用途包括：

- 提供监控信息展示的html页面
- 提供监控信息的JSON API
注意：使用StatViewServlet，建议使用druid 0.2.6以上版本。
## 如何获取 Druid 的监控数据
Druid 的监控数据可以在开启 StatFilter 后通过 DruidStatManagerFacade 进行获取，获取到监控数据之后你可以将其暴露给你的监控系统进行使用。Druid 默认的监控系统数据也来源于此。下面给做一个简单的演示，在 Spring Boot 中如何通过 HTTP 接口将 Druid 监控数据以 JSON 的形式暴露出去，实际使用中你可以根据你的需要自由地对监控数据、暴露方式进行扩展。
```
@RestController
public class DruidStatController {
    @GetMapping("/druid/stat")
    public Object druidStat(){
        // DruidStatManagerFacade#getDataSourceStatDataList 该方法可以获取所有数据源的监控数据，除此之外 DruidStatManagerFacade 还提供了一些其他方法，你可以按需选择使用。
        return DruidStatManagerFacade.getInstance().getDataSourceStatDataList();
    }
}
```
```
[
  {
    "Identity": 1583082378,
    "Name": "DataSource-1583082378",
    "DbType": "h2",
    "DriverClassName": "org.h2.Driver",
    "URL": "jdbc:h2:file:./demo-db",
    "UserName": "sa",
    "FilterClassNames": [
      "com.alibaba.druid.filter.stat.StatFilter"
    ],
    "WaitThreadCount": 0,
    "NotEmptyWaitCount": 0,
    "NotEmptyWaitMillis": 0,
    "PoolingCount": 2,
    "PoolingPeak": 2,
    "PoolingPeakTime": 1533782955104,
    "ActiveCount": 0,
    "ActivePeak": 1,
    "ActivePeakTime": 1533782955178,
    "InitialSize": 2,
    "MinIdle": 2,
    "MaxActive": 30,
    "QueryTimeout": 0,
    "TransactionQueryTimeout": 0,
    "LoginTimeout": 0,
    "ValidConnectionCheckerClassName": null,
    "ExceptionSorterClassName": null,
    "TestOnBorrow": true,
    "TestOnReturn": true,
    "TestWhileIdle": true,
    "DefaultAutoCommit": true,
    "DefaultReadOnly": null,
    "DefaultTransactionIsolation": null,
    "LogicConnectCount": 103,
    "LogicCloseCount": 103,
    "LogicConnectErrorCount": 0,
    "PhysicalConnectCount": 2,
    "PhysicalCloseCount": 0,
    "PhysicalConnectErrorCount": 0,
    "ExecuteCount": 102,
    "ErrorCount": 0,
    "CommitCount": 100,
    "RollbackCount": 0,
    "PSCacheAccessCount": 100,
    "PSCacheHitCount": 99,
    "PSCacheMissCount": 1,
    "StartTransactionCount": 100,
    "TransactionHistogram": [
      55,
      44,
      1,
      0,
      0,
      0,
      0
    ],
    "ConnectionHoldTimeHistogram": [
      53,
      47,
      3,
      0,
      0,
      0,
      0,
      0
    ],
    "RemoveAbandoned": false,
    "ClobOpenCount": 0,
    "BlobOpenCount": 0,
    "KeepAliveCheckCount": 0,
    "KeepAlive": false,
    "FailFast": false,
    "MaxWait": 1234,
    "MaxWaitThreadCount": -1,
    "PoolPreparedStatements": true,
    "MaxPoolPreparedStatementPerConnectionSize": 5,
    "MinEvictableIdleTimeMillis": 30001,
    "MaxEvictableIdleTimeMillis": 25200000,
    "LogDifferentThread": true,
    "RecycleErrorCount": 0,
    "PreparedStatementOpenCount": 1,
    "PreparedStatementClosedCount": 0,
    "UseUnfairLock": true,
    "InitGlobalVariants": false,
    "InitVariants": false
  }
]
```
## 基于logback的日志
`application.properties`中添加配置如`spring.datasource.druid.filters=stat,wall,logFilter`
### 添加配置`logback-spring.xml`文件
详细配置教程查看[日志管理LogBack](https://github.com/AndyYoungCN/springnotes/blob/master/notes/springboot/SpringBoot%E7%9A%84%E6%97%A5%E5%BF%97%E8%AF%A6%E8%A7%A3.md)
```
<?xml version="1.0" encoding="UTF-8"?>
<configuration scan="true" scanPeriod="60 seconds" debug="false">
    <contextName>spring-boot-logging</contextName>
    <property name="logPath" value="D:/java/log"/>

    <!--输出到控制台-->
    <appender name="console" class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <pattern>%d{yyyy-MM-dd-HH:mm:ss} [%thread] %-5level %logger- %msg%n</pattern>
        </encoder>
    </appender>

    <!--输出到文件-->
    <appender name="file" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <file>${logPath}/spring-boot-logging.log</file>

        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- 添加.gz 历史日志会启用压缩 大大缩小日志文件所占空间 -->
            <fileNamePattern>${logPath}/spring-boot-logging.%d{yyyy-MM-dd}.log.zip</fileNamePattern>
            <!-- 日志保存周期 -->
            <maxHistory>30</maxHistory>
            <!-- 总大小 -->
            <totalSizeCap>100KB</totalSizeCap>
        </rollingPolicy>
        <encoder>
            <pattern>%d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n</pattern>
        </encoder>
    </appender>

    <root level="Info">
        <appender-ref ref="console"/>
        <appender-ref ref="file"/>
    </root>
    <logger name="cn.andyoung.druid4mybatis.dao" level="DEBUG"/>
    <!--记录druid-sql的记录-->
    <logger name="druid.sql.Statement" level="DEBUG"/>
</configuration>
```
## 如何配置多数据源
- 添加配置
```
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```

### Druid 数据源配置，继承spring.datasource.* 配置，相同则覆盖
```
spring.datasource.druid.initial-size=5
spring.datasource.druid.max-active=5
```

### Druid 数据源 1 配置，继承spring.datasource.druid.* 配置，相同则覆盖
```
spring.datasource.druid.one.max-active=10
spring.datasource.druid.one.max-wait=10000
```

### Druid 数据源 2 配置，继承spring.datasource.druid.* 配置，相同则覆盖
```
spring.datasource.druid.two.max-active=20
spring.datasource.druid.two.max-wait=20000
```
>强烈注意：Spring Boot 2.X 版本不再支持配置继承，多数据源的话每个数据源的所有配置都需要单独配置，否则配置不会生效

创建数据源
```
@Primary
@Bean
@ConfigurationProperties("spring.datasource.druid.one")
public DataSource dataSourceOne(){
    return DruidDataSourceBuilder.create().build();
}
@Bean
@ConfigurationProperties("spring.datasource.druid.two")
public DataSource dataSourceTwo(){
    return DruidDataSourceBuilder.create().build();
}
```

[demo获取](https://github.com/AndyYoungCN/springbootexample/tree/master/source/druid4mybatis)

## 使用Class配置不适用autoconfig
```
@Configuration
public class DruidConfig {
  private static final Logger logger = LoggerFactory.getLogger(DruidConfig.class);

  @ConfigurationProperties(prefix = "spring.datasource")
  @Bean
  public DataSource druid() {
    return new DruidDataSource();
  }

  // 配置Druid的监控
  // 1、配置一个管理后台的Servlet
  @Bean
  public ServletRegistrationBean statViewServlet() {
    ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
    Map<String, String> initParams = new HashMap<>();

    initParams.put("loginUsername", "admin");
    initParams.put("loginPassword", "123456");
    // 默认就是允许所有访问
    initParams.put("allow", "");
    initParams.put("deny", "192.168.15.21");

    bean.setInitParameters(initParams);
    return bean;
  }

  // 2、配置一个web监控的filter
  @Bean
  public FilterRegistrationBean webStatFilter() {
    FilterRegistrationBean bean = new FilterRegistrationBean();
    bean.setFilter(new WebStatFilter());
    // set other
    Map<String, String> initParams = new HashMap<>();
    initParams.put("exclusions", "*.js,*.css,/druid/*");

    bean.setInitParameters(initParams);

    bean.setUrlPatterns(Arrays.asList("/*"));

    return bean;
  }
}
```