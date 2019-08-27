# Mybatis 的动态 SQL 语句
Mybatis 的映射文件中，前面我们的 SQL 都是比较简单的，有些时候业务逻辑复杂时，我们的 SQL 是动态变化的，此时在前面的学习中我们的 SQL 就不能满足要求了。
## 动态 SQL 之<if>标签
我们根据实体类的不同取值，使用不同的 SQL 语句来进行查询。比如在 id 如果不为空时可以根据 id 查询，如果 username 不为空时还要加入用户名作为条件。这种情况在我们的多条件组合查询中经常会碰到。
### 持久层 Dao 接口
```
 /**
   * 使用动态sql 根据用户信息，查询用户列表
   *
   * @param user
   * @return
   */
  List<User> findByUser(User user);
```
### 持久层 Dao 映射配置
```
<select id="findByUser" resultType="User">
        select * from user where 1 = 1
        <if test="username !=null and username != ''">
            and username like #{username}
        </if>
        <if test="address != null">
            and address like #{address}
        </if>
</select>
```
>注意：`<if>`标签的 test 属性中写的是对象的属性名，如果是包装类的对象要使用 OGNL 表达式的写法。
另外要注意 `where 1=1` 的作用~！
```
### 测试
@Test
  public void testFindByUser() {
    User u = new User();
    u.setUsername("%z%");
    u.setAddress("%n%");
    // 6.执行操作
    List<User> users = userDao.findByUser(u);
    for (User user : users) {
      System.out.println(user);
    }
  }
```
## 动态 SQL 之<where>标签
为了简化上面 `where 1=1` 的条件拼装，我们可以采用`<where>`标签来简化开发。
```
 <select id="findByUser" resultType="user" parameterType="user">
        select * from user
        <where>
            <if test="username!=null and username != '' ">
                and username like #{username}
            </if>
            <if test="address != null">
                and address like #{address}
            </if>
        </where>
    </select>
```
### 动态标签之<foreach>标签
### 需求
传入多个 id 查询用户信息，用下边两个 sql 实现：
SELECT * FROM USERS WHERE username LIKE '%张%' AND (id =10 OR id =89 OR id=16)
SELECT * FROM USERS WHERE username LIKE '%张%' AND id IN (10,89,16)
这样我们在进行范围查询时，就要将一个集合中的值，作为参数动态添加进来。
这样我们将如何进行参数的传递？
### 在 QueryVo 中加入一个 List 集合用于封装参数
```
public class QueryVo {
  private List<Integer> ids;
  //get set
}
```
### 持久层 Dao 接口
```
  /**
   * 根据 id 集合查询用户
   *
   * @param vo
   * @return
   */
  List<User> findInIds(QueryVo vo);
```
### 持久层 Dao 映射配置
```
 <!-- 查询所有用户在 id 的集合之中 -->
    <select id="findInIds" resultType="user" parameterType="queryvo">
        <!-- select * from user where id in (1,2,3,4,5); -->
        select * from user
        <where>
            <if test="ids != null and ids.size() > 0">
                <foreach collection="ids" open="id in ( " close=")" item="uid"
                         separator=",">
                    #{uid}
                </foreach>
            </if>
        </where>
    </select>
```
- SQL 语句：
  - select 字段 from user where id in (?)
- `<foreach>`标签用于遍历集合，它的属性：
  - collection:代表要遍历的集合元素，注意编写时不要写#{}
  - open:代表语句的开始部分
  - close:代表结束部分
  - item:代表遍历集合的每个元素，生成的变量名
  - sperator:代表分隔符
  ### 编写测试方法
  ```
  @Test
  public void testFindInIds() {
    QueryVo vo = new QueryVo();
    List<Integer> ids = new ArrayList<>();
    ids.add(1);
    ids.add(2);
    ids.add(3);
    ids.add(6);
    ids.add(7);
    vo.setIds(ids);
    // 6.执行操作
    List<User> users = userDao.findInIds(vo);
    for (User user : users) {
      System.out.println(user);
    }
  }
  ```
## Mybatis 中简化编写的 SQL 片段
  > Sql 中可将重复的 sql 提取出来，使用时用 include 引用即可，最终达到 sql 重用的目的。
### 定义代码片段
```
  <!-- 抽取重复的语句代码片段 -->
    <sql id="defaultSql">
      select * from user
    </sql>
```
### 引用代码片段
```
<!-- 配置查询所有操作 --> 
<select id="findAll" resultType="user"> 
    <include refid="defaultSql"></include>
</select>
<!-- 根据 id 查询 --> 
<select id="findById" resultType="UsEr" parameterType="int">
    <include refid="defaultSql"></include>
    where id = #{uid}
</select>
```
## 动态 SQL 之set - 更新语句
当 update 语句中没有使用 if 标签时，如果有一个参数为 null，都会导致错误。

当在 update 语句中使用if标签时，如果前面的if没有执行，则或导致逗号多余错误。使用set标签可以将动态的配置 SET 关键字，并剔除追加到条件末尾的任何不相关的逗号。使用 if+set 标签修改后，如果某项为 null 则不进行更新，而是保持数据库原值。如下示例：
```
<!--  if/set(判断参数) - 将实体 User类不为空的属性更新 -->  
<update id="updateUser_if_set" parameterType="com.pojo.User">  
    UPDATE user  
    <set>  
        <if test="username!= null and username != '' ">  
            username = #{username},  
        </if>  
        <if test="sex!= null and sex!= '' ">  
           sex = #{sex},  
        </if>  
        <if test="birthday != null ">  
            birthday = #{birthday},  
        </if>  
    </set>  
    WHERE user_id = #{userid};      
</update> 
```
- 再看看下面的一个示例：
```
<update id="dynamicSetTest" parameterType="Blog">
        update t_blog
        <set>
            <if test="title != null">
                title = #{title},
            </if>
            <if test="content != null">
                content = #{content},
            </if>
            <if test="owner != null">
                owner = #{owner}
            </if>
        </set>
        where id = #{id}
</update>
```
> set 标签元素主要是用在更新操作的时候，它的主要功能和 where 标签元素其实是差不多的，主要是在包含的语句前输出一个 set，然后如果包含的语句是以逗号结束的话将会把该逗号忽略，如果 set 包含的内容为空的话则会出错。有了 set 元素就可以动态的更新那些修改了的字段。
## trim代替where/set标签
trim 是更灵活用来去处多余关键字的标签，它可以用来实现 where 和 set 的效果。 
```
<!-- 使用 if/trim 代替 where(判断参数) - 将 User 类不为空的属性作为 where 条件 -->  
<select id="getUsertList_if_trim" resultMap="resultMap_User">  
    SELECT * 
      FROM user u
    <trim prefix="WHERE" prefixOverrides="AND|OR">  
        <if test="username !=null ">  
            u.username LIKE CONCAT(CONCAT('%', #{username, jdbcType=VARCHAR}),'%')  
        </if>  
        <if test="sex != null and sex != '' ">  
            AND u.sex = #{sex, jdbcType=INTEGER}  
        </if>  
        <if test="birthday != null ">  
            AND u.birthday = #{birthday, jdbcType=DATE}  
        </if>
    </trim>     
</select>  
```
- trim 代替 set
```
<!-- if/trim代替set(判断参数) - 将 User 类不为空的属性更新 -->  
<update id="updateUser_if_trim" parameterType="com.yiibai.pojo.User">  
    UPDATE user  
    <trim prefix="SET" suffixOverrides=",">  
        <if test="username != null and username != '' ">  
            username = #{username},  
        </if>  
        <if test="sex != null and sex != '' ">  
            sex = #{sex},  
        </if>  
        <if test="birthday != null ">  
            birthday = #{birthday},  
        </if>  
         
    </trim>  
    WHERE user_id = #{user_id}  
</update>  
```
- trim (对包含的内容加上 prefix,或者 suffix 等，前缀，后缀) 
```
<select id="dynamicTrimTest" parameterType="Blog" resultType="Blog">
        select * from t_blog 
        <trim prefix="where" prefixOverrides="and |or">
            <if test="title != null">
                title = #{title}
            </if>
            <if test="content != null">
                and content = #{content}
            </if>
            <if test="owner != null">
                or owner = #{owner}
            </if>
        </trim>
    </select>
```
trim 元素的主要功能是可以在自己包含的内容前加上某些前缀，也可以在其后加上某些后缀，与之对应的属性是 prefix 和 suffix；可以把包含内容的首部某些内容覆盖，即忽略，也可以把尾部的某些内容覆盖，对应的属性是 prefixOverrides 和 suffixOverrides；正因为 trim 有这样的功能，所以我们也可以非常简单的利用 trim 来代替 where 元素的功能。
## choose (when, otherwise)标签
- 有时候我们并不想应用所有的条件，而只是想从多个选项中选择一个。而使用if标签时，只要test中的表达式为 true，就会执行 if 标签中的条件。MyBatis 提供了 choose 元素。if标签是与(and)的关系，而 choose 是或(or)的关系。
- choose标签是按顺序判断其内部when标签中的test条件出否成立，如果有一个成立，则 choose 结束。当 choose 中所有 when 的条件都不满则时，则执行 otherwise 中的sql。类似于Java 的 switch 语句，choose 为 switch，when 为 case，otherwise 则为 default。
- 例如下面例子，同样把所有可以限制的条件都写上，方面使用。choose会从上到下选择一个when标签的test为true的sql执行。安全考虑，我们使用where将choose包起来，放置关键字多于错误。
```
<!--  choose(判断参数) - 按顺序将实体类 User 第一个不为空的属性作为：where条件 -->  
<select id="getUserList_choose" resultMap="resultMap_user" parameterType="com.yiibai.pojo.User">  
    SELECT *  
      FROM User u   
    <where>  
        <choose>  
            <when test="username !=null ">  
                u.username LIKE CONCAT(CONCAT('%', #{username, jdbcType=VARCHAR}),'%')  
            </when >  
            <when test="sex != null and sex != '' ">  
                AND u.sex = #{sex, jdbcType=INTEGER}  
            </when >  
            <when test="birthday != null ">  
                AND u.birthday = #{birthday, jdbcType=DATE}  
            </when >  
            <otherwise>  
            </otherwise>  
        </choose>  
    </where>    
</select> 
```
choose (when,otherwize) ,相当于java 语言中的 switch ,与 jstl 中 的 choose 很类似。
```
<select id="dynamicChooseTest" parameterType="Blog" resultType="Blog">
        select * from t_blog where 1 = 1 
        <choose>
            <when test="title != null">
                and title = #{title}
            </when>
            <when test="content != null">
                and content = #{content}
            </when>
            <otherwise>
                and owner = "owner1"
            </otherwise>
        </choose>
</select>
```
> when元素表示当 when 中的条件满足的时候就输出其中的内容，跟 JAVA 中的 switch 效果差不多的是按照条件的顺序，当 when 中有条件满足的时候，就会跳出 choose，即所有的 when 和 otherwise 条件中，只有一个会输出，当所有的我很条件都不满足的时候就输出 otherwise 中的内容。所以上述语句的意思非常简单， 当 title!=null 的时候就输出 and titlte = #{title}，不再往下判断条件，当title为空且 content!=null 的时候就输出 and content = #{content}，当所有条件都不满足的时候就输出 otherwise 中的内容。