package com.zhuang.quartz.job;

import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class TestJob extends BaseJob {


    @Override
    protected void exec(JobExecutionContext context) {
        System.out.println("test-------");
        //throw new RuntimeException("error");
    }
}
