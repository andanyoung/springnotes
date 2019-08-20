# 基于注解的 AOP 配置
- bean.xml
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:aop="http://www.springframework.org/schema/aop"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/aop
        http://www.springframework.org/schema/aop/spring-aop.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">

    <!-- 告知 spring，在创建容器时要扫描的包 -->
    <context:component-scan base-package="cn.andyoung"></context:component-scan>

    <!-- 开启 spring 对注解 AOP 的支持 -->
    <aop:scoped-proxy></aop:scoped-proxy>

</beans>
```
- java
```
@Component("logger")
@Aspect // 表明当前类是一个切面类
public class Logger {

  @Autowired private AccountServiceImpl accountService;

  /** 用于打印日志：计划让其在切入点方法执行之前执行（切入点方法就是业务层方法） */
  @Before("execution(* cn.andyoung.service.impl.*.*(..))")
  public void printLogBefore() {
    System.out.println("printLogBefore。。。");
  }

  @AfterReturning("execution(* cn.andyoung.service.impl.*.*(..))")
  public void printLogAfter() {
    System.out.println("printLogAfter。。。");
  }

  @Around("pt()") // )//注意：千万别忘了写括号
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

  @Pointcut("execution(* cn.andyoung.service.impl.*.*(..))")
  private void pt() {}
}
```


