package com.zhuang.quartz.controller;


import com.zhuang.quartz.model.ApiResult;
import com.zhuang.quartz.model.JobVo;
import org.quartz.*;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("quartz")
public class JobController {

    @Autowired
    private Scheduler scheduler;

    @GetMapping("createJob")
    public ApiResult<String> createJob(@RequestParam(value = "jobGroup", required = false) String jobGroup,
                                       @RequestParam("jobName") String jobName,
                                       @RequestParam("jobClass") String jobClass,
                                       @RequestParam("cron") String cron) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            ApiResult.error("job已存在！");
        }
        Class<? extends Job> jobClazz = null;
        try {
            Class<?> tempJobClazz = Class.forName(jobClass);
            if (Job.class.isAssignableFrom(tempJobClazz)) {
                jobClazz = (Class<? extends Job>) tempJobClazz;
            } else {
                ApiResult.error("不是job类！");
            }
        } catch (ClassNotFoundException e) {
            ApiResult.error("类不存在！");
        }
        JobDetail jobDetail = JobBuilder.newJob(jobClazz)
                .withIdentity(jobKey)
                .build();
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "Trigger", jobKey.getGroup())
                .withSchedule(cronScheduleBuilder).build();
        scheduler.scheduleJob(jobDetail, trigger);
        return ApiResult.success("ok!");
    }

    @GetMapping("deleteJob")
    public ApiResult<String> deleteJob(@RequestParam(value = "jobGroup", required = false) String jobGroup,
                                       @RequestParam("jobName") String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        checkJobExists(jobKey);
        scheduler.deleteJob(jobKey);
        return ApiResult.success("ok");
    }

    @GetMapping("pauseJob")
    public ApiResult<String> pauseJob(@RequestParam(value = "jobGroup", required = false) String jobGroup,
                                      @RequestParam("jobName") String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        checkJobExists(jobKey);
        scheduler.pauseJob(jobKey);
        return ApiResult.success("ok");
    }

    @GetMapping("resumeJob")
    public ApiResult<String> resumeJob(@RequestParam(value = "jobGroup", required = false) String jobGroup,
                                       @RequestParam("jobName") String jobName) throws SchedulerException {
        JobKey jobKey = new JobKey(jobName, jobGroup);
        checkJobExists(jobKey);
        scheduler.resumeJob(jobKey);
        return ApiResult.success("ok");
    }

    @GetMapping("getJobList")
    public ApiResult<List<JobVo>> getJobList() {
        List<JobVo> jobVoList = new ArrayList<>();
        return ApiResult.success(jobVoList);
    }

    private void checkJobExists(JobKey jobKey) {
        try {
            if (!scheduler.checkExists(jobKey)) {
                throw new RuntimeException("job不存在！");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
