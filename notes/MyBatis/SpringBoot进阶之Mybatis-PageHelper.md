# SpringBoot进阶之Mybatis-PageHelper
如果你也在用 MyBatis，建议尝试该分页插件，这一定是最方便使用的分页插件。
分页插件支持任何复杂的单表、多表分页，部分特殊情况请看重要提示。
## 添加依赖
```
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
        <!--<dependency>-->
        <!--<groupId>com.github.pagehelper</groupId>-->
        <!--<artifactId>pagehelper</artifactId>-->
        <!--<version>5.1.2</version>-->
        <!--</dependency>-->
        <dependency>
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>1.2.3</version>
        </dependency>
```
推荐使用starter的配置方式
## 配置
```
##日志级别
logging.level.cn.andyoung.mybatis.pagehelper.dao.IUser=debug
##数据库url
spring.datasource.url=jdbc:mysql://localhost:3306/test?characterEncoding=utf8&useSSL=false
##数据库用户名
spring.datasource.username=root
##数据库密码
spring.datasource.password=123456
##数据库驱动
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
#pagehelper分页插件配置
pagehelper.helperDialect=mysql
```
## 添加model-User
```

public class User implements Serializable {
  private Integer id;
  private String username;
  private Date birthday;
  private String sex;
  private String address;
  // get set 
}
```
## 添加dao - IUser
```
@Mapper
public interface IUser {
  @Select("select * From User")
  Page<User> findUsers();
}
```
## Test
```
@RunWith(SpringRunner.class)
@SpringBootTest
public class IUserTest {

  @Autowired public IUser user;

  @Test
  public void pageTest() {
    PageHelper.startPage(0, 10);
    Page<User> pages = user.findUsers();
    List<User> users = pages.getResult();
    for (User user : users) {
      System.out.println(user);
    }

    PageHelper.startPage(1, 10);
    pages = user.findUsers();
    users = pages.getResult();
    for (User user : users) {
      System.out.println(user);
    }
  }
}
```
## 运行结果
```
2019-09-03 16:26:18.690 DEBUG 7308 --- [           main] c.a.m.p.dao.IUser.findUsers_COUNT        : ==>  Preparing: SELECT count(0) FROM User 
2019-09-03 16:26:18.727 DEBUG 7308 --- [           main] c.a.m.p.dao.IUser.findUsers_COUNT        : ==> Parameters: 
2019-09-03 16:26:18.758 DEBUG 7308 --- [           main] c.a.m.p.dao.IUser.findUsers_COUNT        : <==      Total: 1
2019-09-03 16:26:18.765 DEBUG 7308 --- [           main] c.a.m.pagehelper.dao.IUser.findUsers     : ==>  Preparing: select * From User LIMIT ? 
2019-09-03 16:26:18.766 DEBUG 7308 --- [           main] c.a.m.pagehelper.dao.IUser.findUsers     : ==> Parameters: 10(Integer)
2019-09-03 16:26:18.770 DEBUG 7308 --- [           main] c.a.m.pagehelper.dao.IUser.findUsers     : <==      Total: 10
User{id=1, username='zhangsan', birthday=null, sex='null', address='北京市132'}
User{id=2, username='lisi', birthday=null, sex='null', address='null'}
User{id=10, username='modify user name', birthday=Mon Aug 26 10:25:43 CST 2019, sex='男', address='nb'}
User{id=11, username='modify user name', birthday=Tue Aug 27 02:42:27 CST 2019, sex='男', address='nb'}
User{id=13, username='add user', birthday=Tue Aug 27 05:38:22 CST 2019, sex='n', address='nb'}
User{id=14, username='add user', birthday=Tue Aug 27 05:41:09 CST 2019, sex='n', address='nb'}
User{id=15, username='add user', birthday=Tue Aug 27 05:41:20 CST 2019, sex='n', address='nb'}
User{id=16, username='zhangsan', birthday=null, sex='null', address='new address'}
User{id=17, username='add user1567497890843', birthday=Tue Sep 03 08:04:51 CST 2019, sex='n', address='nb'}
User{id=18, username='add user1567497903111', birthday=Tue Sep 03 08:05:03 CST 2019, sex='n', address='nb'}
2019-09-03 16:26:18.774 DEBUG 7308 --- [           main] c.a.m.p.dao.IUser.findUsers_COUNT        : ==>  Preparing: SELECT count(0) FROM User 
2019-09-03 16:26:18.774 DEBUG 7308 --- [           main] c.a.m.p.dao.IUser.findUsers_COUNT        : ==> Parameters: 
2019-09-03 16:26:18.775 DEBUG 7308 --- [           main] c.a.m.p.dao.IUser.findUsers_COUNT        : <==      Total: 1
2019-09-03 16:26:18.776 DEBUG 7308 --- [           main] c.a.m.pagehelper.dao.IUser.findUsers     : ==>  Preparing: select * From User LIMIT ? 
2019-09-03 16:26:18.777 DEBUG 7308 --- [           main] c.a.m.pagehelper.dao.IUser.findUsers     : ==> Parameters: 10(Integer)
2019-09-03 16:26:18.781 DEBUG 7308 --- [           main] c.a.m.pagehelper.dao.IUser.findUsers     : <==      Total: 10
User{id=1, username='zhangsan', birthday=null, sex='null', address='北京市132'}
User{id=2, username='lisi', birthday=null, sex='null', address='null'}
User{id=10, username='modify user name', birthday=Mon Aug 26 10:25:43 CST 2019, sex='男', address='nb'}
User{id=11, username='modify user name', birthday=Tue Aug 27 02:42:27 CST 2019, sex='男', address='nb'}
User{id=13, username='add user', birthday=Tue Aug 27 05:38:22 CST 2019, sex='n', address='nb'}
User{id=14, username='add user', birthday=Tue Aug 27 05:41:09 CST 2019, sex='n', address='nb'}
User{id=15, username='add user', birthday=Tue Aug 27 05:41:20 CST 2019, sex='n', address='nb'}
User{id=16, username='zhangsan', birthday=null, sex='null', address='new address'}
User{id=17, username='add user1567497890843', birthday=Tue Sep 03 08:04:51 CST 2019, sex='n', address='nb'}
User{id=18, username='add user1567497903111', birthday=Tue Sep 03 08:05:03 CST 2019, sex='n', address='nb'}
```
[demo获取](https://github.com/AndyYoungCN/springbootexample/tree/master/source/pagehelper)
[官网github](https://github.com/pagehelper/Mybatis-PageHelper)