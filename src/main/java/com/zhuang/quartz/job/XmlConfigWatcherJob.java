package com.zhuang.quartz.job;

import org.quartz.JobExecutionContext;

public class XmlConfigWatcherJob extends BaseJob {

	@Override
	public void executeInternal(JobExecutionContext context) {
		System.out.println("XmlConfigWatcherJob");
	}

}
