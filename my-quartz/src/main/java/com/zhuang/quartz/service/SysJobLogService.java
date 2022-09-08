package com.zhuang.quartz.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhuang.quartz.entity.SysJobLog;
import com.zhuang.quartz.mapper.SysJobMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class SysJobLogService extends ServiceImpl<SysJobMapper, SysJobLog> {

    public void add(SysJobLog log) {
        SysJobLog sysJobLog = new SysJobLog();
        BeanUtils.copyProperties(log, sysJobLog);
        sysJobLog.setId(UUID.randomUUID().toString());
        sysJobLog.setCreateTime(new Date());
        save(sysJobLog);
    }

    public List<SysJobLog> getListByEndTime(Date endTime) {
        return list(
                new LambdaQueryWrapper<SysJobLog>()
                        .lt(SysJobLog::getCreateTime, endTime)
        );
    }
}
