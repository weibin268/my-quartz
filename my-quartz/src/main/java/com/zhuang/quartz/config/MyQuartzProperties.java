package com.zhuang.quartz.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "my.quartz")
public class MyQuartzProperties {
    private Boolean log = true;
    private Boolean logErrorOnly = false;
}
