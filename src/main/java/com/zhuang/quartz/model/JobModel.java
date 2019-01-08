package com.zhuang.quartz.model;

public class JobModel {

	private String name;
	
	private String clazz;
	
	private String cron;
	
	private Integer interval;
	
	private String data;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public Integer getInterval() {
		return interval;
	}

	public void setInterval(Integer interval) {
		this.interval = interval;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "JobModel [name=" + name + ", clazz=" + clazz + ", cron=" + cron + ", interval=" + interval + ", data="
				+ data + "]";
	}
	
	
}
