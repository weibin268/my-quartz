package com.zhuang.quartz.job;

import org.quartz.JobExecutionContext;

public class TestJob extends BaseJob {
    @Override
    protected void exec(JobExecutionContext context) {
        System.out.println("test-------");
    }
}
