package com.zhuang.quartz.config;

import java.util.List;

import com.zhuang.quartz.model.JobModel;

public interface ConfigProvider {

	List<JobModel> getJobModelList();
}
