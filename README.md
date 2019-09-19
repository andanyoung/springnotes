# spring笔记
> 本仓库是对spirng学习的笔记，欢迎大家指点，持续更新中
## 目录
- spring
  - [基础](/notes/spring/spring.md)
  - [基于注解的spring](/notes/spring/springIOC注解.md)
  - [Spring整合Junit](/notes/spring/Spring整合Junit.md)
  - [SpringAOP](/notes/spring/SpringAOP.md)
  - [SpringAOP注解](/notes/spring/SpringAOP注解.md)
- spring mvc
  - [基本概念](/notes/springmvc/基本概念.md)
  - [spring mvc](/notes/springmvc/springmvc.md)
  - [SpringMVC 注解](/notes/springmvc/SpringMVC注解.md)
  - [SpringMVC异常处理](/notes/springmvc/SpringMVC异常处理.md)
  - [SpringMVC拦截器](/notes/springmvc/SpringMVC拦截器.md)
- springboot
  - [基础](/notes/springboot/SpringBoot基础.md)
  - [Spring常用注解](/notes/springboot/Spring常用注解.md)
  - [Redis](/notes/springboot/Redis.md)
  - [日志管理LogBack](/notes/springboot/SpringBoot的日志详解.md)
  - [SpringBoot之Druid.md](/notes/springboot/SpringBoot之Druid.md)
### 第三方框架
- MyBatis
  - [MyBatis入门](/notes/MyBatis/MyBatis入门.md)
  - [MyBatis参数配置](/notes/MyBatis/MyBatis参数配置.md)
  - [Mybatis的动态 SQL 语句](/notes/MyBatis/Mybatis的动态SQL语句.md)
  - [Mybatis多表查询](/notes/MyBatis/Mybatis多表查询.md)
  - [Mybatis延迟加载缓存策略](/notes/MyBatis/Mybatis延迟加载策略.md)
  - [Mybatis 注解开发](/notes/MyBatis/Mybatis注解开发.md)
  - [Mybatis 日志输出](/notes/MyBatis/Mybatis日志输出.md)
  - [MyBatis 开启事务](/notes/MyBatis/MyBatis开启事务.md)
  - [Mybatis进阶之Mybatis-PageHelper](/notes/MyBatis/SpringBoot进阶之Mybatis-PageHelper.md)
  - [Mybatis Plus](https://mybatis.plus/guide/)
  - [Mybatis 主从]
### 源码分析
- [1.spring-boot-starter-data-redis.md](/notes/source/1.spring-boot-starter-data-redis.md)
- [2.commons-pool2](/notes/source/2.commons-pool2.md)
### 设计模式
- [工厂模式](https://www.runoob.com/design-pattern/factory-pattern.html)
> 在工厂模式中，我们在创建对象时不会对客户端暴露创建逻辑，并且是通过使用一个共同的接口来指向新创建的对象。
>
>使用场景： 1、日志记录器：记录可能记录到本地硬盘、系统事件、远程服务器等，用户可以选择记录日志到什么地方。 2、数据库访问，当用户不知道最后系统采用哪一类数据库，以及数据库可能有变化时。 3、设计一个连接服务器的框架，需要三个协议，"POP3"、"IMAP"、"HTTP"，可以把这三个作为产品类，共同实现一个接口。
> 工厂方法通过传入不同参数创建对象（返回同个父类、协议），调用该对象（父类、协议）的方法实现。目的根据不同参数，创建不同对象。调用同一方法名实现不同效果、功能。
- [建造者模式(Builder)](https://www.runoob.com/design-pattern/builder-pattern.html)
> 建造者模式（Builder Pattern）使用多个简单的对象一步一步构建成一个复杂的对象。这种类型的设计模式属于创建型模式，它提供了一种创建对象的最佳方式。
>
>一个 Builder 类会一步一步构造最终的对象。该 Builder 类是独立于其他对象的。
- [更多设计模式](https://www.runoob.com/design-pattern/design-pattern-tutorial.html)
