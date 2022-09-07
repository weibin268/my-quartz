package com.zhuang.quartz.job;

import com.zhuang.quartz.entity.SysJobLog;
import com.zhuang.quartz.service.SysJobLogService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;


@Slf4j
public abstract class BaseJob extends QuartzJobBean {

    @Autowired
    private SysJobLogService sysJobLogService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("exec job begin -> {}", jobExecutionContext.getJobDetail().getKey());
        SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobClass(jobExecutionContext.getJobDetail().getJobClass().getName());
        sysJobLog.setJobName(jobExecutionContext.getJobDetail().getKey().getName());
        try {
            exec(jobExecutionContext);
            sysJobLog.setResult(0);
        } catch (Exception e) {
            log.error("job exec fail!", e);
            sysJobLog.setResult(1);
            sysJobLog.setMessage(e.getMessage());
        }
        sysJobLogService.add(sysJobLog);
        log.debug("exec job end -> {}", jobExecutionContext.getJobDetail().getKey());
    }

    protected abstract void exec(JobExecutionContext context);

}
