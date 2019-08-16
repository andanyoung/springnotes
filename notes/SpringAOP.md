# Spring AOP
## AOP 概述
### 什么是AOP
AOP：全称是 Aspect Oriented Programming 即：面向切面编程。
简单的说它就是把我们程序重复的代码抽取出来，在需要执行的时候，使用动态代理的技术，在不修改源码的
基础上，对我们的已有方法进行增强。
![srpingaop](../image/springAOP.png)
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
     