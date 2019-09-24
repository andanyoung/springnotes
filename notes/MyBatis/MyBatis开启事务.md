# MyBatis 开启事务
`springboot`开启事务很简单，只需要一个注解`@Transactional` 就可以了。因为在springboot中已经默认对jpa、jdbc、mybatis开启了事事务，引入它们依赖的时候，事物就默认开启。当然，如果你需要用其他的orm，比如beatlsql，就需要自己配置相关的事物管理器。
## 环境依赖
- pom.xml
```
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.0</version>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>RELEASE</version>
        </dependency>
```
- sql
```
CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `money` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
INSERT INTO `account` VALUES ('1', 'aaa', '1000');
INSERT INTO `account` VALUES ('2', 'bbb', '1000');
INSERT INTO `account` VALUES ('3', 'ccc', '1000');
```
- application.properties
```
spring.datasource.url=jdbc:mysql://localhost:3306/test?serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=123456
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
mybatis.type-aliases-package=cn.andyoung.transactional.dao
```
### 数据访问dao 层
```
package cn.andyoung.transactional.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface IAccount {
  @Update("UPDATE account set money=#{money} WHERE id=#{id}")
  int update(@Param("money") double money, @Param("id") int id);
}
```
### service层
```
@Service
public class AccountService {
  @Autowired IAccount account;

  @Transactional
  public void transfer() throws RuntimeException {

    account.update(90, 1); // 用户1减10块 用户2加10块
    int i = 1 / 0; //制造错误
    account.update(110, 2);
  }
}
```
@Transactional，声明事务，并设计一个转账方法，用户1减10块，用户2加10块。在用户1减10 ，之后，抛出异常，即用户2加10块钱不能执行，当加注解@Transactional之后，两个人的钱都没有增减。当不加@Transactional，用户1减了10，用户2没有增加，即没有操作用户2 的数据。可见@Transactional注解开启了事物。

结语
springboot 开启事物很简单，只需要加一行注解就可以了，前提你用的是jdbctemplate, jpa, mybatis，这种常见的orm。

源码下载：https://github.com/AndyYoungCN/springbootexample/tree/master/source/transactional
