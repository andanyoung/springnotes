# Mybatis注解开发
这几年来注解开发越来越流行，Mybatis 也可以使用注解开发方式，这样我们就可以减少编写 Mapper 映射文件了。本次我们先围绕一些基本的 CRUD 来学习，再学习复杂映射关系及延迟加载。
## mybatis 的常用注解说明
> @Insert:实现新增
@Update:实现更新
@Delete:实现删除
@Select:实现查询
@Result:实现结果集封装
@Results:可以与@Result 一起使用，封装多个结果集
@ResultMap:实现引用@Results 定义的封装
@One:实现一对一结果集封装
@Many:实现一对多结果集封装
@SelectProvider: 实现动态 SQL 映射
@CacheNamespace:实现注解二级缓存的使用
## 使用 Mybatis 注解实现基本 CRUD
### 使用注解方式开发持久层接口
```
/** 给予注解的dao */
@Mapper
public interface IUserAnnoDao {

  /**
   * 查询所有用户
   *
   * @return
   */
  @Select("select * from user")
  @Results(
      id = "userMap",
      value = {
        @Result(id = true, column = "id", property = "userId"),
        @Result(column = "username", property = "userName"),
        @Result(column = "sex", property = "userSex"),
        @Result(column = "address", property = "userAddress"),
        @Result(column = "birthday", property = "userBirthday")
      })
  List<User> findAll();

  /*
   * 根据 id 查询一个用户
   * @param userId
   * @return
   */
  @Select("select * from user where id = #{uid} ")
  @ResultMap("userMap")
  User findById(Integer userId);

  /**
   * 保存操作
   *
   * @param user
   * @return
   */
  @Insert(
      "insert into user (username,sex,birthday,address) values(#{username},#{sex},#{birthday},#{address})")
  @SelectKey(
      keyColumn = "id",
      keyProperty = "id",
      resultType = Integer.class,
      before = false,
      statement = {"select last_insert_id()"})
  int saveUser(User user);
  /**
   * 更新操作
   *
   * @param user
   * @return
   */
  @Update(
      "update user set username=#{username},address=#{address},sex=#{sex},birthday=#{birthday} where id =#{id} ")
  int updateUser(User user);
  /**
   * 删除用户
   *
   * @param userId
   * @return
   */
  @Delete("delete from user where id = #{uid} ")
  int deleteUser(Integer userId);

  /**
   * 查询使用聚合函数
   *
   * @return
   */
  @Select("select count(*) from user ")
  int findTotal();

  /**
   * 模糊查询
   *
   * @param name
   * @return
   */
  @Select("select * from user where username like #{username} ")
  List<User> findByName(String name);
}
```
> 通过注解方式，我们就不需要再去编写 UserDao.xml 映射文件了。
### 编写 SqlMapConfig 配置文件
```
 <mappers>
        <!-- 配置 dao 接口的位置，它有两种方式
        第一种：使用 mapper 标签配置 class 属性
        第二种：使用 package 标签，直接指定 dao 接口所在的包
        -->
        <package name="cn.andyoung.dao"/>
    </mappers>
```
## 使用注解实现复杂关系映射开发
> 实现复杂关系映射之前我们可以在映射文件中通过配置`<resultMap>`来实现，在使用注解开发时我们需要借助@Results 注解，@Result 注解，@One 注解，@Many 注解。
### 复杂关系映射的注解说明
@Results 注解
- 代替的是标签`<resultMap>`
该注解中可以使用单个@Result 注解，也可以使用@Result 集合
@Results（{@Result（），@Result（）}）或@Results（@Result（））
- @Resutl 注解
代替了 `<id>`标签和`<result>`标签
  - @Result 中 属性介绍：
    - id 是否是主键字段
    - column 数据库的列名
    - property 需要装配的属性名
    - one 需要使用的@One 注解（@Result（one=@One）（）））
    - many 需要使用的@Many 注解（@Result（many=@many）（）））

  - @One 注解（一对一）
代替了`<assocation>`标签，是多表查询的关键，在注解中用来指定子查询返回单一对象。
- @One 注解属性介绍：
  - select 指定用来多表查询的 sqlmapper
  - fetchType 会覆盖全局的配置参数 lazyLoadingEnabled。。
使用格式：
`@Result(column=" ",property="",one=@One(select=""))`
- @Many 注解（多对一）
代替了`<Collection>`标签,是是多表查询的关键，在注解中用来指定子查询返回对象集合。
注意：聚集元素用来处理“一对多”的关系。需要指定映射的 Java 实体类的属性，属性的 javaType
（一般为 ArrayList）但是注解中可以不定义；
使用格式：
`@Result(property="",column="",many=@Many(select=""))`
### 使用注解实现一对一复杂关系映射及延迟加载
- 需求：
加载账户信息时并且加载该账户的用户信息，根据情况可实现延迟加载。（注解方式实现）
```
/**
   * 查询所有账户，采用延迟加载的方式查询账户的所属用户
   *
   * @return
   */
  @Select("select * from account")
  @Results(
      id = "accountMap",
      value = {
        @Result(id = true, column = "id", property = "id"),
        @Result(column = "uid", property = "uid"),
        @Result(column = "money", property = "money"),
        @Result(
            column = "uid",
            property = "user",
            one = @One(select = "cn.andyong.dao.IUserDao.findById", fetchType = FetchType.LAZY))
      })
  List<Account> findAllLazy();
```
### 使用注解实现一对多复杂关系映射
- 需求：
查询用户信息时，也要查询他的账户列表。使用注解方式实现。
- 分析：
一个用户具有多个账户信息，所以形成了用户(User)与账户(Account)之间的一对多关系。
```
/**
   * 查询所有用户
   *
   * @return
   */
  @Select("select * from user")
  @Results(
      id = "userMap",
      value = {
        @Result(id = true, column = "id", property = "userId"),
        @Result(column = "username", property = "userName"),
        @Result(column = "sex", property = "userSex"),
        @Result(column = "address", property = "userAddress"),
        @Result(column = "birthday", property = "userBirthday"),
        @Result(
            column = "id",
            property = "accounts",
            many =
                @Many(select = "cn.andyoung.dao.IAccountDao.findByUid", fetchType = FetchType.LAZY))
      })
  List<User> findAllManyLazy();
```
## mybatis 基于注解的二级缓存
### 在 SqlMapConfig 中开启二级缓存支持
```
<settings>
        <setting name="lazyLoadingEnabled" value="true"/>
        <setting name="aggressiveLazyLoading" value="false"/>
    </settings>
```
### 在持久层接口中使用注解配置二级缓存
```
/**
* 
* <p>Title: IUserDao</p>
* <p>Description: 用户的持久层接口</p>
*/
@CacheNamespace(blocking=true)//mybatis 基于注解方式实现配置二级缓存
public interface IUserDao {}
```
## @Mappe与@MapperScan关系
### @Mapper
为了让DemoMapper能够让别的类进行引用，我们可以在DemMapper类上添加@Mapper注解：
```
@Mapper  
public interface DemoMapper {  
    @Insert("insert into Demo(name) values(#{name})")  
    @Options(keyProperty="id",keyColumn="id",useGeneratedKeys=true)  
    public void save(Demo demo);  
}  
```
直接在Mapper类上面添加注解@Mapper，这种方式要求每一个mapper类都需要添加此注解，麻烦。
### @MapperScan
```
@SpringBootApplication  
//@MapperScan({"cn.andyoung.*.mapper","org.kfit.*.mapper"})  
@MapperScan("cn.andyoung.*.mapper")  
public class App {  
    public static void main(String[] args) {  
       SpringApplication.run(App.class, args);  
    }  
} 
```
如果mapper类没有在Spring Boot主程序可以扫描的包或者子包下面，可以使用如下方式进行配置：