package com.zhuang.quartz;


import com.zhuang.quartz.config.MyQuartzProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.zhuang.quartz.mapper")
@EnableConfigurationProperties(MyQuartzProperties.class)
@ComponentScan
public class MyQuartzAutoConfiguration {

}
