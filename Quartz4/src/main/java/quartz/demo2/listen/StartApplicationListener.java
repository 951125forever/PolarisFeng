package quartz.demo2.listen;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import quartz.demo2.config.MyQuartzJobBean2;
import quartz.demo2.config.SchedulerConfig;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * @Description:利用Quartz定时任务，可以在初始化上实现，
 */
@Component
public class StartApplicationListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    SchedulerConfig schedulerConfig;
    public static AtomicInteger count = new AtomicInteger(0);
    private static String TRIGGER_GROUP_NAME="test_trriger4";
    private static String JOB_GROUP_NAME ="test_job4";
    protected Logger log = LoggerFactory.getLogger(StartApplicationListener.class);
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 防止重复执行
        if (event.getApplicationContext().getParent() == null && count.incrementAndGet() <= 1) {
            initMyJob();
        }
    }
    public void initMyJob()  {
        //调度器
        Scheduler scheduler = null;
        try {
            //创建scheduler
            scheduler = schedulerConfig.scheduler();
            //定义一个TriggerKey
            TriggerKey triggerKey2 = TriggerKey.triggerKey("触发器2", TRIGGER_GROUP_NAME);
            CronTrigger trigger2 = (CronTrigger) scheduler.getTrigger(triggerKey2);
            if (null == trigger2) {
                Class clazz = MyQuartzJobBean2.class;
                //定义一个JobDetail,其中的定义Job类，是真正的执行逻辑所在
                JobDetail jobDetail2 = JobBuilder.newJob(clazz).withIdentity("任务2", JOB_GROUP_NAME).build();
                CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");
                //定义一个Trigger
                trigger2 = TriggerBuilder.newTrigger().withIdentity("触发器2", TRIGGER_GROUP_NAME)
                        .withSchedule(scheduleBuilder).build();
                scheduler.scheduleJob(jobDetail2, trigger2);
                log.info("Quartz 创建了job:...:{}",jobDetail2.getKey());
            } else {
                log.info("job已存在:{}",trigger2.getKey());
            }
            scheduler.start();
        } catch (Exception e) {
            log.info(ExceptionUtils.getFullStackTrace(e));
        }
    }
}
