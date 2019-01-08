package com.zhuang.quartz.job;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public abstract class BaseJob implements Job {

    Logger logger = LoggerFactory.getLogger(BaseJob.class);

    public void execute(JobExecutionContext context) throws JobExecutionException {
        String jobName = "“" + this.getClass().getName() + "”";
        logger.info(jobName + " executing……");
        try {
            executeInternal(context);
        } catch (Throwable e) {
            logger.error(jobName + " execute error！", e);
        }
        logger.info(jobName + " executed successfully！");
    }

    public abstract void executeInternal(JobExecutionContext context);

}
