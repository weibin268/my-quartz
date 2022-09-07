package com.zhuang.quartz.controller;


import com.zhuang.quartz.model.ApiResult;
import com.zhuang.quartz.model.JobVo;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("quartz")
public class JobController {

    @Autowired
    private Scheduler scheduler;

    @GetMapping("createJob")
    public ApiResult<Object> createJob(@RequestParam(value = "jobGroup", required = false) String jobGroup,
                                       @RequestParam("jobName") String jobName,
                                       @RequestParam("jobClass") String jobClass,
                                       @RequestParam(value = "cron", required = false) String cron,
                                       @RequestParam(value = "intervalSeconds", required = false) Integer intervalSeconds
    ) throws SchedulerException {

        if (cron == null && intervalSeconds == null) {
            return ApiResult.error("参数cron和intervalSeconds不能同时为空！");
        }
        JobKey jobKey = new JobKey(jobName, jobGroup);
        if (scheduler.checkExists(jobKey)) {
            return ApiResult.error("job已存在！");
        }
        Class<? extends Job> jobClazz = null;
        try {
            Class<?> tempJobClazz = Class.forName(jobClass);
            if (Job.class.isAssignableFrom(tempJobClazz)) {
                jobClazz = (Class<? extends Job>) tempJobClazz;
            } else {
                return ApiResult.error("不是job类！");
            }
        } catch (ClassNotFoundException e) {
            return ApiResult.error("类不存在！");
        }
        JobDetail jobDetail = JobBuilder.newJob(jobClazz)
                .withIdentity(jobKey)
                .build();
        ScheduleBuilder scheduleBuilder = null;
        if (intervalSeconds != null) {
            scheduleBuilder = SimpleScheduleBuilder.repeatSecondlyForever(intervalSeconds);
        } else {
            scheduleBuilder = CronScheduleBuilder.cronSchedule(cron);
        }
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(jobName + "Trigger", jobKey.getGroup())
                .withSchedule(scheduleBuilder).build();
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
