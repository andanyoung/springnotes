# Spring常用注解
## 常用注解
### 用于创建对象的
- 相当于：`<bean id="" class="">`
#### @Component
- 作用：
把资源让 spring 来管理。相当于在 xml 中配置一个 bean。
- 属性：
value：指定 bean 的 id。如果不指定 value 属性，默认 bean 的 id 是当前类的类名。首字母小写。
#### @Controller @Service @Repository
>他们三个注解都是针对一个的衍生注解，他们的作用及属性都是一模一样的。
他们只不过是提供了更加明确的语义化。

- @Controller：一般用于表现层的注解。
- @Service：一般用于业务层的注解。
- @Repository：一般用于持久层的注解。
- @RestController： Spring4之后加入的注解，原来在@Controller中返回json需要@ResponseBody来配合，如果直接用@RestController替代@Controller就不需要再配置@ResponseBody，默认返回json格式。
细节：如果注解中有且只有一个属性要赋值时，且名称是 value，value 在赋值是可以不写。

### 用于注入数据的
相当于：
```
<property name="" ref=""> 
<property name="" value="">
```
#### @Autowired
- 作用：
自动按照类型注入。当使用注解注入属性时，set 方法可以省略。它只能注入其他 bean 类型。当有多个类型匹配时，使用要注入的对象变量名称作为 bean 的 id，在 spring 容器查找，找到了也可以注入成功。找不到就报错。
#### @Qualifier
- 作用：
在自动按照类型注入的基础之上，再按照 Bean 的 id 注入。它在给字段注入时不能独立使用，必须和
@Autowire 一起使用；但是给方法参数注入时，可以独立使用。
- 属性：
value：指定 bean 的 id。
#### @Resource
- 作用：
直接按照 Bean 的 id 注入。它也只能注入其他 bean 类型。
- 属性：
name：指定 bean 的 id。
#### @Value
- 作用：
注入基本数据类型和 String 类型数据的
- 属性：
value：用于指定值
### 用于改变作用范围的：
相当于：```<bean id="" class="" scope="">```
#### @Scope
- 作用：指定 bean 的作用范围。
- 属性：value：指定范围的值。取值：singleton prototype request session globalsession
#### 和生命周期相关的：
相当于：```<bean id="" class="" init-method="" destroy-method="" />```
#### @PostConstruct
- 作用：
用于指定初始化方法。
#### @PreDestroy
- 作用：
用于指定销毁方法。
### 关于 Spring 注解和 XML 的选择问题
- 注解的优势：配置简单，维护方便（我们找到类，就相当于找到了对应的配置）。
- XML 的优势：修改时，不用改源码。不涉及重新编译和部署。- Spring 管理 Bean 方式的比较：


- ![Spring管理Bean方式的比较](../../../image/Spring管理Bean方式的比较.png)

## spring 管理对象细节
> 基于注解的 spring IoC 配置中，bean 对象的特点和基于 XML 配置是一模一样的。
## spring 的纯注解配置
### @Configuration
- 作用：
用于指定当前类是一个 spring 配置类，当创建容器时会从该类上加载注解。获取容器时需要使用
AnnotationApplicationContext(有@Configuration 注解的类.class)。
- 属性：
value:用于指定配置类的字节码
```
@Configuration
public class SpringConfiguration {

}
```
> 我们已经把配置文件用类来代替了，但是如何配置创建容器时要扫描的包呢？
请看下一个注解。
### @ComponentScan
- 作用：
用于指定 spring 在初始化容器时要扫描的包。作用和在 spring 的 xml 配置文件中的：
`<context:component-scan base-package="com.itheima"/>`是一样的。
属性：
- basePackages：用于指定要扫描的包。和该注解中的 value 属性作用一样。
```
@Configurationpublic 
@ComponentScan("cn.andyoung")
class SpringConfiguration {

}
```
### @Bean
- 作用：
该注解只能写在方法上，表明使用此方法创建一个对象，并且放入 spring 容器。
- 属性：
name：给当前@Bean 注解方法创建的对象指定一个名称(即 bean 的 id）。
```
public class JdbcConfig {
  /** * 创建一个数据源，并存入 spring 容器中* @return */
  @Bean(name = "dataSource")
  public DataSource createDataSource() {

    return null;
  }
}
```
### @PropertySource
- 作用：
用于加载.properties 文件中的配置。例如我们配置数据源时，可以把连接数据库的信息写到properties 配置文件中，就可以使用此注解指定 properties 配置文件的位置。
- 属性：value[]：用于指定 properties 文件位置。如果是在类路径下，需要写上 classpath:
```
public class JdbcConfig {
  @Value("${jdbc.driver}")
  private String driver;

  @Value("${jdbc.url}")
  private String url;

  @Value("${jdbc.username}")
  private String username;

  @Value("${jdbc.password}")
  private String password;
}
```
### @Import
- 作用：
用于导入其他配置类，在引入其他配置类时，可以不用再写@Configuration 注解。当然，写上也没问题。
- 属性：value[]：用于指定其他配置类的字节码。
```
@Configuration
@ComponentScan(basePackages = "com.itheima.spring")
@Import({JdbcConfig.class})
public class SpringConfiguration {

}

@Configuration
@PropertySource("classpath:jdbc.properties")
public class JdbcConfig {
    
}
```
## 用于Http
### @RequestMapping
- 类定义处: 提供初步的请求映射信息，相对于 WEB 应用的根目录。
- 方法处: 提供进一步的细分映射信息，相对于类定义处的 URL

### @RequestParam
- 用于将请求参数区数据映射到功能处理方法的参数上
例如
![](https://mmbiz.qpic.cn/mmbiz_png/SJm51egHPPHPFBdJvmeVY2MOiaIcCJcR76avMJkjhMiaeiaSBibePy8Kiav3eORNqt4wzSfDFqKcBzebAFVpiaF30JeQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)
这个id就是要接收从接口传递过来的参数id的值的，如果接口传递过来的参数名和你接收的不一致，也可以如下
![](https://mmbiz.qpic.cn/mmbiz_png/SJm51egHPPHPFBdJvmeVY2MOiaIcCJcR7on7Jbs8r2iaSocCOwNicicPxwhAbicOiawIy4oKZrpT5lpKSkXE7do2ibBtQ/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)
其中course_id就是接口传递的参数，id就是映射course_id的参数名
### @ModelAttribute
使用地方有三种：
- 1、标记在方法上。
标记在方法上，会在每一个@RequestMapping标注的方法前执行，如果有返回值，则自动将该返回值加入到ModelMap中。
  - (1).在有返回的方法上:
当ModelAttribute设置了value，方法返回的值会以这个value为key，以参数接受到的值作为value，存入到Model中，如下面的方法执行之后，最终相当于 ```model.addAttribute("user_name", name);```假如 @ModelAttribute没有自定义value，则相当于```model.addAttribute("name", name);```
![](https://mmbiz.qpic.cn/mmbiz_png/SJm51egHPPHPFBdJvmeVY2MOiaIcCJcR7x8PKK3ymOMPs069YCdcHbLZl7JRXnv1lTjkfaTJBvl5nUkUwT0OKFg/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)
  - (2) 在没返回的方法上：
需要手动model.add方法
![](https://mmbiz.qpic.cn/mmbiz_png/SJm51egHPPHPFBdJvmeVY2MOiaIcCJcR76BKPYstZts43HTsx4NXg8WgmrZD37FpPoHwa2qiaKAp1Oq4S1CibLYSA/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)
- 2、标记在方法的参数上。
标记在方法的参数上，会将客户端传递过来的参数按名称注入到指定对象中，并且会将这个对象自动加入ModelMap中，便于View层使用.我们在上面的类中加入一个方法如下
![](https://mmbiz.qpic.cn/mmbiz_png/SJm51egHPPHPFBdJvmeVY2MOiaIcCJcR72DkD60VU9SRXB7Kq8KKqArKmjSUs2FVo3L4vW0Wgt66I3fNHpaQSdw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)
> 从结果就能看出，用在方法参数中的@ModelAttribute注解，实际上是一种接受参数并且自动放入Model对象中，便于使用。
### @Cacheable
用来标记缓存查询。可用用于方法或者类中，当标记在一个方法上时表示该方法是支持缓存的，当标记在一个类上时则表示该类所有的方法都是支持缓存的。
- 参数列表
![](https://mmbiz.qpic.cn/mmbiz_jpg/SJm51egHPPH0OP8QMqx7DITJypTzJhEm5hWjlJkaxic1JjLKBLGtobyjJyvIYDjVjnlV3WWbqWCAEatMZ4mibgXA/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)

比如@Cacheable(value="UserCache") 标识的是当调用了标记了这个注解的方法时，逻辑默认加上从缓存中获取结果的逻辑，如果缓存中没有数据，则执行用户编写查询逻辑，查询成功之后，同时将结果放入缓存中。

但凡说到缓存，都是key-value的形式的，因此key就是方法中的参数（id），value就是查询的结果，而命名空间UserCache是在spring*.xml中定义.
![](https://mmbiz.qpic.cn/mmbiz_png/SJm51egHPPHPFBdJvmeVY2MOiaIcCJcR7rrnAFAKRNE5cwual5LZPhhDc1WRZwu7DSbiazt00BnMnDP9qJBGtSmw/640?wx_fmt=png&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)
### @CacheEvict
用来标记要清空缓存的方法，当这个方法被调用后，即会清空缓存。@CacheEvict(value=”UserCache”)
参数列表
![](https://mmbiz.qpic.cn/mmbiz_jpg/SJm51egHPPH0OP8QMqx7DITJypTzJhEmrn9VEtjibDOC0zgoE6AzmaGic3WiceU9Csbm1TFJq6AicMKtcl5fhLN3KQ/640?wx_fmt=jpeg&tp=webp&wxfrom=5&wx_lazy=1&wx_co=1)
