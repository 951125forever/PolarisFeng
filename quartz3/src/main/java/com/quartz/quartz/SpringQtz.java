package com.quartz.quartz;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SpringQtz extends QuartzJobBean {

    static Logger logger = LoggerFactory.getLogger(SpringQtz.class);
    private static int counter = 0;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        long ms = System.currentTimeMillis();
        logger.error(" SpringQtz start  执行");
        logger.info("-------" + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date(ms)) + "  " + "(" + counter++ + ")");
    }

}
