package com.fl.demo.quartz;

import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;

@Configuration
public class QuartzConfig {
    private String Host="192.168.78.128";
    private int Port=6379;
    @Bean
    public JobDetail test01(){
        Jedis jedis=new Jedis(Host, Port);
        jedis.setnx("test01","0");
        JobDetail job = JobBuilder.newJob(test01.class)//PrintTimeJob我们的业务类
                .withIdentity("test01")//可以给该JobDetail起一个id
                .storeDurably()//即使没有Trigger关联时，也不需要删除该JobDetail
                .build();
        return job;
    }
    /**
     * 触发Job执行的时间触发规则实现类SimpleTrigger和CronTrigger
     * 可以通过crom表达式定义出各种复杂的调度方案
     */
    @Bean
    public Trigger test01Trigger() {
        Jedis jedis=new Jedis(Host, Port);
        if (0==jedis.setnx("test01","0")) {
            SimpleScheduleBuilder scb = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(5)
                    .repeatForever();
            Trigger trigger = TriggerBuilder.newTrigger()
                    .forJob(test01())
                    .withIdentity("test01")
                    .withSchedule(scb)
                    .build();
            return trigger;
        }else {
            System.out.println("该定时已存在");
            return null;
        }
    }
}
