package cn.andyoung.utils;

import org.aspectj.lang.ProceedingJoinPoint;

public class Logger {
  /** 用于打印日志：计划让其在切入点方法执行之前执行（切入点方法就是业务层方法） */
  public void printLogBefore() {
    System.out.println("printLogBefore。。。");
  }

  public void printLogAfter() {
    System.out.println("printLogAfter。。。");
  }

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
}
