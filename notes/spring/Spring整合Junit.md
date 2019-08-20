# Spring 整合 Junit
## 问题
- 在测试类中，每个测试方法都有以下两行代码：
```
ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
IAccountService as = ac.getBean("accountService",IAccountService.class);
```
这两行代码的作用是获取容器，如果不写的话，直接会提示空指针异常。所以又不能轻易删掉。
## 解决思路分析
- 针对上述问题，我们需要的是程序能自动帮我们创建容器。一旦程序能自动为我们创建 spring 容器，我们就无须手动创建了，问题也就解决了。
- 我们都知道，junit 单元测试的原理（在 web 阶段课程中讲过），但显然，junit 是无法实现的，因为它自己都无法知晓我们是否使用了 spring 框架，更不用说帮我们创建 spring 容器了。不过好在，junit 给我们暴露
了一个注解，可以让我们替换掉它的运行器。
- 这时，我们需要依靠 spring 框架，因为它提供了一个运行器，可以读取配置文件（或注解）来创建容器。我们只需要告诉它配置文件在哪就行了。

### 使用@RunWith 注解替换原有运行器
```
@RunWith(SpringJUnit4ClassRunner.class)
public class AccountServiceTest {
}
```
### 使用@ContextConfiguration 指定 spring 配置文件的位置
```
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:bean.xml"})
public class AccountServiceTest {

}
```
> @ContextConfiguration 注解：
>- locations 属性：用于指定配置文件的位置。如果是类路径下，需要classpath:表明
>- classes 属性：用于指定注解的类。当不使用 xml 配置时，需要用此属性指定注解类的位置。
### 第四步：使用@Autowired 给测试类中的变量注入数据
```
 @RunWith(SpringJUnit4ClassRunner.class)
 @ContextConfiguration(locations= {"classpath:bean.xml"})
 public class AccountServiceTest {
     @Autowired
    private IAccountService as ; 
}
```
## 为什么不把测试类配到 xml 中
在解释这个问题之前，先解除大家的疑虑，配到 XML 中能不能用呢？  
答案是肯定的，没问题，可以使用。  
那么为什么不采用配置到 xml 中的方式呢？
> 这个原因是这样的：
>- 第一：当我们在 xml 中配置了一个 bean，spring 加载配置文件创建容器时，就会创建对象。
>- 第二：测试类只是我们在测试功能时使用，而在项目中它并不参与程序逻辑，也不会解决需求上的问题，所以创建完了，并没有使用。那么存在容器中就会造成资源的浪费。所以，基于以上两点，我们不应该把测试配置到 xml 文件中。