package com.zhuang.quartz.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;


public abstract class BaseJob extends QuartzJobBean {


    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("begin " + jobExecutionContext.getJobDetail().getKey());
        exec(jobExecutionContext);
        System.out.println("end " + jobExecutionContext.getJobDetail().getKey());
    }

    protected abstract void exec(JobExecutionContext context);

}
