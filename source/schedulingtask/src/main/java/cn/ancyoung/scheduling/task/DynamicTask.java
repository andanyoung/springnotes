package cn.ancyoung.scheduling.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

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
