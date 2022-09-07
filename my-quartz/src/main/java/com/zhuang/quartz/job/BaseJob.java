package com.zhuang.quartz.job;

import com.zhuang.quartz.config.MyQuartzProperties;
import com.zhuang.quartz.entity.SysJobLog;
import com.zhuang.quartz.enums.ExecResult;
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
    @Autowired
    private MyQuartzProperties myQuartzProperties;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.debug("exec job begin -> {}", jobExecutionContext.getJobDetail().getKey());
        SysJobLog sysJobLog = new SysJobLog();
        sysJobLog.setJobGroup(jobExecutionContext.getJobDetail().getKey().getGroup());
        sysJobLog.setJobName(jobExecutionContext.getJobDetail().getKey().getName());
        sysJobLog.setJobClass(jobExecutionContext.getJobDetail().getJobClass().getName());
        try {
            exec(jobExecutionContext);
            sysJobLog.setResult(ExecResult.SUCCESS.getValue());
        } catch (Exception e) {
            log.error("job exec fail!", e);
            sysJobLog.setResult(ExecResult.ERROR.getValue());
            sysJobLog.setMessage(e.getMessage());
        }
        if (myQuartzProperties.getLog()) {
            if (!myQuartzProperties.getLogErrorOnly() || !ExecResult.SUCCESS.getValue().equals(sysJobLog.getResult())) {
                sysJobLogService.add(sysJobLog);
            }
        }
        log.debug("exec job end -> {}", jobExecutionContext.getJobDetail().getKey());
    }

    protected abstract void exec(JobExecutionContext context);

}
