# Spring AOP
## AOP 概述
### 什么是AOP
AOP：全称是 Aspect Oriented Programming 即：面向切面编程。
简单的说它就是把我们程序重复的代码抽取出来，在需要执行的时候，使用动态代理的技术，在不修改源码的
基础上，对我们的已有方法进行增强。
![srpingaop](../../image/springAOP.png)
### AOP 的作用及优势
- 作用：
在程序运行期间，不修改源码对已有方法进行增强。
- 优势：
减少重复代码
提高开发效率
维护方便
### AOP 的实现方式
- 使用动态代理技术
## AOP 的具体应用
### 动态代理回顾
- 字节码随用随创建，随用随加载。
- 它与静态代理的区别也在于此。因为静态代理是字节码一上来就创建好，并完成加载。
- 装饰者模式就是静态代理的一种体现。
### 动态代理常用的有两种方式
- 基于接口的动态代理
提供者：JDK 官方的 Proxy 类。
要求：被代理类最少实现一个接口。
- 基于子类的动态代理
提供者：第三方的 CGLib，如果报 asmxxxx 异常，需要导入 asm.jar。
要求：被代理类不能用 final 修饰的类（最终类）。
#### 使用 JDK 官方的 Proxy
> 在很久以前，演员和剧组都是直接见面联系的。没有中间人环节。
而随着时间的推移，产生了一个新兴职业：经纪人（中间人），这个时候剧组再想找演员就需要通过经纪
人来找了。下面我们就用代码演示出来。
```
/**
* 一个经纪公司的要求:
* 能做基本的表演和危险的表演
*/
public interface IActor {
/**
* 基本演出
* @param money
*/
public void basicAct(float money);
/**
* 危险演出
* @param money
*/
public void dangerAct(float money);
}
/**
* 一个演员
*/
//实现了接口，就表示具有接口中的方法实现。即：符合经纪公司的要求
public class Actor implements IActor{
    public void basicAct(float money){
        System.out.println("拿到钱，开始基本的表演："+money);
    }

    public void dangerAct(float money){
        System.out.println("拿到钱，开始危险的表演："+money);
    } 
}
```
- test
```
public class Client {
  public static void main(String[] args) {
    // 一个剧组找演员：
    final Actor actor = new Actor(); // 直接
    /*
     * 代理： 间接
     * 获取代理对象：
     * 要求： 被代理类最少实现一个接口。
     * 创建的方式: Proxy.newProxyInstance(三个参数) 参数含义：
     * ClassLoader：和被代理对象使用相同的类加载器
     * Interfaces：和被代理对象具有相同的行为 实现相同的接口。
     * InvocationHandler：如何代理。
     * 策略模式：使用场景是： 数据有了，目的明确。 如何达成目标，就是策略。
     */
    IActor proxyActor =
        (IActor)
            Proxy.newProxyInstance(
                actor.getClass().getClassLoader(),
                actor.getClass().getInterfaces(),
                new InvocationHandler() {
                  /**
                   * 执行被代理对象的任何方法，都会经过该方法。 此方法有拦截的功能。
                   *
                   * <p>参数： proxy：代理对象的引用。不一定每次都用得到 method：当前执行的方法对象 args：执行方法所需的参数 返回值： 当前执行方法的返回值
                   */
                  public Object invoke(Object proxy, Method method, Object[] args)
                      throws Throwable {
                    String name = method.getName();
                    Float money = (Float) args[0];
                    Object rtValue = null;

                    // 每个经纪公司对不同演出收费不一样，此处开始判断
                    if ("basicAct".equals(name)) {
                      // 基本演出，没有 2000 不演
                      if (money > 2000) {
                        // 看上去剧组是给了 8000，实际到演员手里只有 4000
                        // 这就是我们没有修改原来 basicAct 方法源码，对方法进行了增强
                        rtValue = method.invoke(actor, money / 2);
                      }
                    }
                    if ("dangerAct".equals(name)) {
                      // 危险演出,没有 5000 不演
                      if (money > 5000) {
                        // 看上去剧组是给了 50000，实际到演员手里只有 25000
                        // 这就是我们没有修改原来 dangerAct 方法源码，对方法进行了增强
                        rtValue = method.invoke(actor, money / 2);
                      }
                    }
                    return rtValue;
                  }
                });
    // 没有经纪公司的时候，直接找演员。
    // actor.basicAct(1000f);
    // actor.dangerAct(5000f);
    // 剧组无法直接联系演员，而是由经纪公司找的演员
    proxyActor.basicAct(8000f);
    proxyActor.dangerAct(50000f);
  }
}
```
#### 使用 CGLib 的 Enhancer 类创建代理对象
> 还是那个演员的例子，只不过不让他实现接口。
```
public class Actor implements IActor{
    public void basicAct(float money){
        System.out.println("拿到钱，开始基本的表演："+money);
    }

    public void dangerAct(float money){
        System.out.println("拿到钱，开始危险的表演："+money);
    } 
}
```
Test
```
public class EnhancerClient {
  /**
   * * 基于子类的动态代理* 要求：* 被代理对象不能是最终类* 用到的类：* Enhancer* 用到的方法：* create(Class, Callback)* 方法的参数：*
   * Class：被代理对象的字节码* Callback：如何代理* @param args
   */
  public static void main(String[] args) {
    final Actor actor = new Actor();

    Actor cglibActor =
        (Actor)
            Enhancer.create(
                actor.getClass(),
                new MethodInterceptor() {
                  /**
                   * 执行被代理对象的任何方法，都会经过该方法。在此方法内部就可以对被代理对象的任何 方法进行增强。
                   *
                   * <p>参数： 前三个和基于接口的动态代理是一样的。 MethodProxy：当前执行方法的代理对象。 返回值： 当前执行方法的返回值
                   */
                  public Object intercept(
                      Object proxy, Method method, Object[] args, MethodProxy methodProxy)
                      throws Throwable {
                    String name = method.getName();
                    Float money = (Float) args[0];
                    Object rtValue = null;
                    if ("basicAct".equals(name)) {
                      // 基本演出
                      if (money > 2000) {
                        rtValue = method.invoke(actor, money / 2);
                      }
                    }
                    if ("dangerAct".equals(name)) {
                      // 危险演出
                      if (money > 5000) {
                        rtValue = method.invoke(actor, money / 2);
                      }
                    }
                    return rtValue;
                  }
                });
    cglibActor.basicAct(10000);
    cglibActor.dangerAct(100000);
  }
}
```
# Spring 中的 AOP
## Spring 中 AOP 的细节
### 说明 
> 我们学习 spring 的 aop，就是通过配置的方式，实现上一章节的功能。
### AOP 相关术语
- Joinpoint(连接点):
所谓连接点是指那些被拦截到的点。在 spring 中,这些点指的是方法,因为 spring 只支持方法类型的
连接点。
- Pointcut(切入点):
所谓切入点是指我们要对哪些 Joinpoint 进行拦截的定义。
- Advice(通知/增强):
所谓通知是指拦截到 Joinpoint 之后所要做的事情就是通知。
通知的类型：前置通知,后置通知,异常通知,最终通知,环绕通知。
- Introduction(引介):
引介是一种特殊的通知在不修改类代码的前提下, Introduction 可以在运行期为类动态地添加一些方
法或 Field。
- Target(目标对象):
代理的目标对象。
-Weaving(织入):
是指把增强应用到目标对象来创建新的代理对象的过程。
spring 采用动态代理织入，而 AspectJ 采用编译期织入和类装载期织入。
- Proxy（代理）:
一个类被 AOP 织入增强后，就产生一个结果代理类。
- Aspect(切面):
是切入点和通知（引介）的结合。
### 学习 spring 中的 AOP 要明确的事
- a、开发阶段（我们做的）
编写核心业务代码（开发主线）：大部分程序员来做，要求熟悉业务需求。
把公用代码抽取出来，制作成通知。（开发阶段最后再做）：AOP 编程人员来做。
在配置文件中，声明切入点与通知间的关系，即切面。：AOP 编程人员来做。
- b、运行阶段（Spring 框架完成的）
Spring 框架监控切入点方法的执行。一旦监控到切入点方法被运行，使用代理机制，动态创建目标对
象的代理对象，根据通知类别，在代理对象的对应位置，将通知对应的功能织入，完成完整的代码逻辑运行。
## 基于 XML 的 AOP 配置
- bean.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd">

    <!-- 配置srping的Ioc,把service对象配置进来-->
    <bean id="accountService" class="cn.andyoung.service.impl.AccountServiceImpl"></bean>

    <!--spring中基于XML的AOP配置步骤
        1、把通知Bean也交给spring来管理
        2、使用aop:config标签表明开始AOP的配置
        3、使用aop:aspect标签表明配置切面
                id属性：是给切面提供一个唯一标识
                ref属性：是指定通知类bean的Id。
        4、在aop:aspect标签的内部使用对应标签来配置通知的类型
               我们现在示例是让printLog方法在切入点方法执行之前之前：所以是前置通知
               aop:before：表示配置前置通知
                    method属性：用于指定Logger类中哪个方法是前置通知
                    pointcut属性：用于指定切入点表达式，该表达式的含义指的是对业务层中哪些方法增强

            切入点表达式的写法：
                关键字：execution(表达式)
                表达式：
                    访问修饰符  返回值  包名.包名.包名...类名.方法名(参数列表)
                标准的表达式写法：
                    public void com.itheima.service.impl.AccountServiceImpl.saveAccount()
                访问修饰符可以省略
                    void com.itheima.service.impl.AccountServiceImpl.saveAccount()
                返回值可以使用通配符，表示任意返回值
                    * com.itheima.service.impl.AccountServiceImpl.saveAccount()
                包名可以使用通配符，表示任意包。但是有几级包，就需要写几个*.
                    * *.*.*.*.AccountServiceImpl.saveAccount())
                包名可以使用..表示当前包及其子包
                    * *..AccountServiceImpl.saveAccount()
                类名和方法名都可以使用*来实现通配
                    * *..*.*()
                参数列表：
                    可以直接写数据类型：
                        基本类型直接写名称           int
                        引用类型写包名.类名的方式   java.lang.String
                    可以使用通配符表示任意类型，但是必须有参数
                    可以使用..表示有无参数均可，有参数可以是任意类型
                全通配写法：
                    * *..*.*(..)

                实际开发中切入点表达式的通常写法：
                    切到业务层实现类下的所有方法
                        * com.itheima.service.impl.*.*(..)
    -->

    <!-- 配置Logger类 -->
    <bean id="logger" class="cn.andyoung.utils.Logger"></bean>

    <!--配置AOP-->
    <aop:config>
        <!--配置切面 -->
        <!--用于配置切入点表达式。就是指定对哪些类的哪些方法进行增强。-->
        <aop:pointcut id="pt" expression="execution(* cn.andyoung.service.impl.*.*(..))"></aop:pointcut>
        <aop:aspect id="logAdvice" ref="logger">
            <!-- 配置通知的类型，并且建立通知方法和切入点方法的关联-->
            <aop:before method="printLogBefore" pointcut="execution(* cn.andyoung.service.impl.*.*(..))"></aop:before>
            <aop:after method="printLogAfter" pointcut-ref="pt"></aop:after>
            <!-- 配置环绕通知 -->
            <aop:around method="printLogAround" pointcut-ref="pt"/>
        </aop:aspect>
    </aop:config>

</beans>
```
### 配置步骤
#### 第一步：把通知类用 bean 标签配置起来
```
 <!-- 配置Logger类 -->
    <bean id="logger" class="cn.andyoung.utils.Logger"></bean>
```
#### 第二步：使用 aop:config 声明 aop 配置
- aop:config:
 作用：用于声明开始 aop 的配置
```
<aop:config>
    <!-- 配置的代码都写在此处 -->
</aop:config>
```
#### 第三步：使用 aop:aspect 配置切面
- aop:aspect:
  - 作用：
用于配置切面。
  - 属性：
id：给切面提供一个唯一标识。
ref：引用配置好的通知类 bean 的 id。 
```
<aop:aspect id="txAdvice" ref="txManager">
<!--配置通知的类型要写在此处-->
</aop:aspect>
```
#### 第四步：使用 aop:pointcut 配置切入点表达式
- aop:pointcut：
  - 作用：
用于配置切入点表达式。就是指定对哪些类的哪些方法进行增强。
  - 属性：
expression：用于定义切入点表达式。
id：用于给切入点表达式提供一个唯一标识
```
<!--用于配置切入点表达式。就是指定对哪些类的哪些方法进行增强。-->
<aop:pointcut id="pt" expression="execution(* cn.andyoung.service.impl.*.*(..))"></aop:pointcut>
```
#### 第五步：使用 aop:xxx 配置对应的通知类型
- aop:before
  - 作用：
用于配置前置通知。指定增强的方法在切入点方法之前执行
  - 属性：
method:用于指定通知类中的增强方法名称
ponitcut-ref：用于指定切入点的表达式的引用
poinitcut：用于指定切入点表达式
- 执行时间点：
切入点方法执行之前执行
```
<aop:aspect id="logAdvice" ref="logger">
  <!-- 配置通知的类型，并且建立通知方法和切入点方法的关联-->
  <aop:before method="printLog" pointcut="execution(* cn.andyoung.service.impl.*.*(..))"></aop:before>
</aop:aspect>
```
- aop:after-returning
  - 作用：
用于配置后置通知
  - 属性：
method：指定通知中方法的名称。
pointct：定义切入点表达式
pointcut-ref：指定切入点表达式的引用
- 执行时间点：
切入点方法正常执行之后。它和异常通知只能有一个执行
```
<aop:after method="printLog" pointcut-ref="pt"></aop:after>
```
- aop:after-throwing
  - 作用：
用于配置异常通知
  - 属性：
method：指定通知中方法的名称。
pointct：定义切入点表达式
pointcut-ref：指定切入点表达式的引用
- 执行时间点：
切入点方法执行产生异常后执行。它和后置通知只能执行一个
```
<aop:after-throwing method="rollback" pointcut-ref="pt1"/>
```
- aop:after
  - 作用：
用于配置最终通知
  - 属性：
method：指定通知中方法的名称。
pointct：定义切入点表达式
pointcut-ref：指定切入点表达式的引用
- 执行时间点：
无论切入点方法执行时是否有异常，它都会在其后面执行。
```<aop:after method="release" pointcut-ref="pt1"/>```
### 2.2.3 切入点表达式说明
- execution:匹配方法的执行(常用)
  - execution(表达式)
表达式语法：execution([修饰符] 返回值类型 包名.类名.方法名(参数))
写法说明：
全匹配方式：
```
public void 
cn.andyoung.service.impl.AccountServiceImpl.saveAccount(cn.andyoung.domain.Account)
```
访问修饰符可以省略
```
void 
cn.andyoung.service.impl.AccountServiceImpl.saveAccount(cn.andyoung.domain.Account)
```
返回值可以使用*号，表示任意返回值
```
* cn.andyoung.service.impl.AccountServiceImpl.saveAccount(cn.andyoung.domain.Account)
```
包名可以使用*号，表示任意包，但是有几级包，需要写几个*
```
* *.*.*.*.AccountServiceImpl.saveAccount(cn.andyoung.domain.Account)
```
使用..来表示当前包，及其子包
```
* cn.andyoung.service.AccountServiceImpl.saveAccount(cn.andyoung.domain.Account)
```
类名可以使用*号，表示任意类
```
* cn..*.saveAccount(cn.andyoung.domain.Account)
```
方法名可以使用*号，表示任意方法
```
* cn..*.*( cn.andyoung.domain.Account)
```
参数列表可以使用*，表示参数可以是任意数据类型，但是必须有参数
```
* cn..*.*(*)
```
参数列表可以使用..表示有无参数均可，有参数可以是任意类型
```
* cn..*.*(..)
```
全通配方式：
```
* *..*.*(..)
```
- 注：
通常情况下，我们都是对业务层的方法进行增强，所以切入点表达式都是切到业务层实现类。
```execution(* cn.andyoung.service.impl.*.*(..))```
### 环绕通知
- 最后一种通知是环绕通知。环绕通知在一个方法执行之前和之后执行。它使得通知有机会 在一个方法执行之前和执行之后运行。而且它可以决定这个方法在什么时候执行，如何执行，甚至是否执行。 环绕通知经常在某线程安全的环境下，你需要在一个方法执行之前和之后共享某种状态的时候使用。 请尽量使用最简单的满足你需求的通知。（比如如果简单的前置通知也可以适用的情况下不要使用环绕通知）。
```
<!-- 配置环绕通知 -->
<aop:around method="printLogAround" pointcut-ref="pt"/>
```
- aop:around：
  - 作用：
用于配置环绕通知
  - 属性：
method：指定通知中方法的名称。
pointct：定义切入点表达式
pointcut-ref：指定切入点表达式的引用
- 说明：
它是 spring 框架为我们提供的一种可以在代码中手动控制增强代码什么时候执行的方式。
- 注意：
通常情况下，环绕通知都是独立使用的
- java
```
/**
   * 环绕通知
   *
   * @param pjp spring 框架为我们提供了一个接口：ProceedingJoinPoint，它可以作为环绕通知的方法参数。 在环绕通知执行时，spring
   *     框架会为我们提供该接口的实现类对象，我们直接使用就行。
   * @return
   */
  public Object printLogAround(ProceedingJoinPoint pjp) {
    System.out.println("printLogAround。。。");
    // 定义返回值
    Object rtValue = null;
    try {
      // 获取方法执行所需的参数
      Object[] args = pjp.getArgs();
      // 前置通知：开启事务
      // beginTransaction();
      // 执行方法
      rtValue = pjp.proceed(args);
      // 后置通知：提交事务
      //  commit();
    } catch (Throwable e) {
      // 异常通知：回滚事务
      //  rollback();
      e.printStackTrace();
    } finally {
      // 最终通知：释放资源
      // release();
    }
    return rtValue;
  }
```