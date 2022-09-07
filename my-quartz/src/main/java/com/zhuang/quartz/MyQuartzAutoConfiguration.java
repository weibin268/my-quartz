package com.zhuang.quartz;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.zhuang.quartz.mapper")
@ComponentScan
public class MyQuartzAutoConfiguration {

}
