package com.zhuang.quartz.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_job_log")
public class SysJobLog {

    @TableId
    private String id;
    private String jobGroup;
    private String jobName;
    private String jobClass;
    private Integer result;
    private String message;
    private Date createTime;
    private String createBy;

}
