package com.zhuang.quartz;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zhuang.quartz.config.ConfigProvider;
import com.zhuang.quartz.config.XmlConfigProvider;
import com.zhuang.quartz.model.JobModel;

public class QuartzStarter {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private Scheduler scheduler;
    private ConfigProvider configProvider;

    private static final String JOB_DATA_DATA = "data";
    private static final String JOB_DATA_STARTER = "starter";

    public QuartzStarter() {

        init();
    }

    public void start() {
        try {
            doStart();
        } catch (Exception e) {
            logger.error("quartz start error!", e);
        }
    }

    public void doStart() throws SchedulerException, ClassNotFoundException {
        logger.info("quartz starting……");
        List<JobModel> jobModels = configProvider.getJobModelList();
        for (JobModel jobModel : jobModels) {
            Class<Job> jobClazz = (Class<Job>) Class.forName(jobModel.getClazz());
            String jobId = jobModel.getClazz();
            String groupName = jobModel.getName();
            JobDetail job = JobBuilder.newJob(jobClazz).withIdentity(jobId, groupName).build();
            job.getJobDataMap().put(JOB_DATA_DATA, jobModel.getData());
            job.getJobDataMap().put(JOB_DATA_STARTER, this);
            TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withIdentity(jobModel.getClazz(), groupName);
            if (jobModel.getCron() != null) {
                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(jobModel.getCron()));
            }
            if (jobModel.getInterval() != null) {
                triggerBuilder.withSchedule(
                        SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(jobModel.getInterval())
                                .withMisfireHandlingInstructionNextWithRemainingCount().repeatForever());
            }
            Trigger trigger = triggerBuilder.startNow().build();
            scheduler.scheduleJob(job, trigger);
        }
        scheduler.start();
        logger.info("quartz start successfully！");
    }

    public void stop() {
        try {
            logger.info("quartz shutting down……");
            scheduler.shutdown();
            logger.info("quartz shutdown successfully！");
        } catch (Exception e) {
            logger.error("quartz stop error!", e);
        }
    }

    public void restart() {
        stop();
        init();
        start();
    }

    private void init() {
        try {
            scheduler = new StdSchedulerFactory().getScheduler();
            configProvider = new XmlConfigProvider();
        } catch (Exception e) {
            logger.error("quartz init error!", e);
        }
    }

}
