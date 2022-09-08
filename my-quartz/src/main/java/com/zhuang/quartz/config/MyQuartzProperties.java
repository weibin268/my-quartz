package com.zhuang.quartz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "my.quartz")
public class MyQuartzProperties {
    // 是否启用日志记录
    private Boolean log = true;
    // 只记录失败的日志
    private Boolean logErrorOnly = false;
    // 日志记录保存天数
    private Integer logRemainDays = 10;

}
