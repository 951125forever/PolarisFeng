package quazrdemo2.demo.quartz2;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class QuartzConfig {
    private String Host="192.168.78.128";
    private int Port=6379;
    @Bean
    public JobDetail test02() {
        Jedis jedis=new Jedis(Host, Port);
        jedis.setnx("test01","1");
        JobDetail test02 = JobBuilder.newJob(test01.class)//PrintTimeJob我们的业务类
                .withIdentity("test01")//可以给该JobDetail起一个id
                .storeDurably()//即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
        return test02;
    }

    @Bean
    public Trigger test02Trigger() throws SchedulerException {
        Jedis jedis=new Jedis(Host, Port);
        if (1==jedis.setnx("test01","1")) {
            CronScheduleBuilder csb1 = CronScheduleBuilder.cronSchedule("*/5 * * * * ?");
            return TriggerBuilder.newTrigger()
                    .forJob(test02())
                    .withIdentity("test01")
                    .withSchedule(csb1)
                    .build();
        }else {
            System.out.println("该定时已存在");
            return null;
        }
    }
}
