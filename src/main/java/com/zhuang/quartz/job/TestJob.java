package com.zhuang.quartz.job;

import org.quartz.JobExecutionContext;

import com.zhuang.quartz.QuartzStarter;

public class TestJob extends BaseJob {

    @Override
    public void executeInternal(JobExecutionContext context) {
        Object data = context.getJobDetail().getJobDataMap().get("data");
        Integer integer = Integer.parseInt(data.toString());
        System.out.println(integer++);
        context.getJobDetail().getJobDataMap().put("data", integer);
        if (integer == 10) {
            QuartzStarter quartzStarter = (QuartzStarter) context.getJobDetail().getJobDataMap().get("starter");
            quartzStarter.restart();
        }
    }

}
