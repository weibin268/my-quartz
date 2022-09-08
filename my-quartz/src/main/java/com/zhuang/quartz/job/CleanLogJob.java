package com.zhuang.quartz.job;

import com.zhuang.quartz.config.MyQuartzProperties;
import com.zhuang.quartz.entity.SysJobLog;
import com.zhuang.quartz.service.SysJobLogService;
import com.zhuang.quartz.util.DateUtils;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CleanLogJob extends BaseJob {

    @Autowired
    private MyQuartzProperties myQuartzProperties;
    @Autowired
    private SysJobLogService sysJobLogService;

    @Override
    protected void exec(JobExecutionContext context) {
        Date endTime = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, -myQuartzProperties.getLogRemainDays());
        List<SysJobLog> sysJobLogList = sysJobLogService.getListByEndTime(endTime);
        for (SysJobLog sysJobLog : sysJobLogList) {
            sysJobLogService.removeById(sysJobLog.getId());
        }
    }
}
