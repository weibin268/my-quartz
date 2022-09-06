package com.zhuang.quartz.controller;


import com.zhuang.quartz.model.ApiResult;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("quartz")
public class JobController {

    @Autowired
    private Scheduler scheduler;

    @GetMapping("createJob")
    public ApiResult<String> createJob(@RequestParam("jobName") String jobName, @RequestParam("jobClass") String jobClass, @RequestParam("cron") String cron) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName);
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
        Class<? extends Job> jobClazz = null;
        try {
            Class<?> tempJobClazz = Class.forName(jobClass);
            if (tempJobClazz.isAssignableFrom(Job.class)) {
                jobClazz = (Class<? extends Job>) tempJobClazz;
            }
        } catch (ClassNotFoundException e) {
            ApiResult.error("类不存在！");
        }
        JobDetail jobDetail = JobBuilder.newJob(jobClazz)
                .withIdentity(jobKey)
                .build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "Trigger")
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
        return ApiResult.success("ok!");
    }

    @GetMapping("deleteJob")
    public String deleteJob(@RequestParam("jobName") String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName);
        if (scheduler.checkExists(jobKey)) {
            scheduler.deleteJob(jobKey);
        }
        return "ok";
    }

    @GetMapping("pauseJob")
    public String papauseJobuse(@RequestParam("jobName") String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName);
        if (scheduler.checkExists(jobKey)) {
            scheduler.pauseJob(jobKey);
        }
        return "ok";
    }
}
