# 定时任务（Scheduling Tasks）
> 这篇文章将介绍怎么通过spring去做调度任务。
## 简介
定时任务或者说定时调度，是系统中比较普遍的一个功能，例如数据归档、清理，数据定时同步（非实时），定时收发，流量控制等等都需要用到定时任务，常见的定时调度框架有Quartz、TBSchedule等。

同样，Spring自3.0版本起也增加了任务调度功能Schedule，它好比是一个轻量级的Quartz，使用起来方便、简洁，且不需要依赖其他的JAR包。之所以说它是轻量级Quartz，是因为在现如今遍地分布式的大环境下，Spring自带的Schedule不支持分布式部署，所以若是分布式环境开发请忽略此文章，可以选用Quartz、TBSchedule等，且一般稍大点的公司都有独立的统一调度中心。
## 正文
- @EnableScheduling,开启调度任务。
```
@SpringBootApplication
@EnableScheduling
public class SchedulingApplication {

  public static void main(String[] args) {
    SpringApplication.run(SchedulingApplication.class, args);
  }
}
```
## 创建定时任务
> 创建一个定时任务，每过5s在控制台打印当前时间。
```
/** 定时任务 */
@Component
public class ScheduledTasks {
  private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
  private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

  @Scheduled(fixedRate = 5000)
  public void reportCurrentTime() {
    log.info("The time is now {}", dateFormat.format(new Date()));
  }
}
```
通过在方法上加@Scheduled注解，表明该方法是一个调度任务。

- @Scheduled(fixedRate = 5000) ：上一次开始执行时间点之后5秒再执行
- @Scheduled(fixedDelay = 5000) ：上一次执行完毕时间点之后5秒再执行
- @Scheduled(initialDelay=1000, fixedRate=5000) ：第一次延迟1秒后执行，之后按fixedRate的规则每5秒执行一次
- @Scheduled(cron=" /5 ") ：通过cron表达式定义规则，什么是cro表达式，自行搜索引擎。
> 测试
启动springboot工程，控制台没过5s就打印出了当前的时间。
```
2019-09-23 10:18:10.170  INFO 4768 --- [   scheduling-1] c.a.scheduling.task.ScheduledTasks       : The time is now 10:18:10
2019-09-23 10:18:15.170  INFO 4768 --- [   scheduling-1] c.a.scheduling.task.ScheduledTasks       : The time is now 10:18:15
2019-09-23 10:18:20.169  INFO 4768 --- [   scheduling-1] c.a.scheduling.task.ScheduledTasks       : The time is now 10:18:20
2019-09-23 10:18:25.170  INFO 4768 --- [   scheduling-1] c.a.scheduling.task.ScheduledTasks       : The time is now 10:18:25
2019-09-23 10:18:30.170  INFO 4768 --- [   scheduling-1] c.a.scheduling.task.ScheduledTasks       : The time is now 10:18:30
2019-09-23 10:18:35.170  INFO 4768 --- [   scheduling-1] c.a.scheduling.task.ScheduledTasks       : The time is now 10:18:35
```
## 总结
在springboot创建定时任务比较简单，只需2步：

- 1.在程序的入口加上@EnableScheduling注解。
- 2.在定时方法上加@Scheduled注解。
源码下载：
[https://github.com/AndyYoungCN/springbootexample/tree/master/source/schedulingtask](https://github.com/AndyYoungCN/springbootexample/tree/master/source/schedulingtask)
## 动态配置定时任务
Spring提供了SchedulingConfigurer接口，下面介绍可配置、动态修改触发器的定时任务
可配置的定时任务组件需要实现SchedulingConfigurer接口中的configureTasks方法，该方法有两个入参（Runnable task, Trigger trigger），第一个为我们任务的具体逻辑实现，第二个为触发器，动态的定时任务则意味着Trigger需要动态获取，由于之前我们已经集成redis，因此这里我们从redis获取相关配置。
```
/** 动态配置定时 */
@Component
public class DynamicTask implements SchedulingConfigurer {
  private static Logger log = LoggerFactory.getLogger(DynamicTask.class);

  @Override
  public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
    taskRegistrar.addTriggerTask(doTask(), getTrigger());
  }

  private Runnable doTask() {
    return new Runnable() {

      @Override
      public void run() {
        // 业务逻辑
        log.info("执行了MyDynamicTask,时间为:" + new Date(System.currentTimeMillis()));
      }
    };
  }

  private Trigger getTrigger() {
    return new Trigger() {
      @Override
      public Date nextExecutionTime(TriggerContext triggerContext) {
        // 触发器
        CronTrigger trigger = new CronTrigger(getCron());
        return trigger.nextExecutionTime(triggerContext);
      }
    };
  }

  public String getCron() {
    // 可以在这边修改定时器。查找配置，返回需要的配置
    return "2 */10 22 1-10 * *";
  }
}
```
> 上述cron也可以从其他地方动态获取，这样在配置值修改后，定时任务调度器便被更新了，不过需要注意的是，此种方式修改了配置值后，需要在下一次调度结束后，才会更新调度器，并不会在修改配置值时实时更新，实时更新需要在修改配置值时额外增加相关逻辑处理。