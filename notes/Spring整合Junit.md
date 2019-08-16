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