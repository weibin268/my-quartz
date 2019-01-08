package com.zhuang.quartz.config;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.zhuang.quartz.model.JobModel;
import com.zhuang.quartz.util.PathUtils;
import com.zhuang.quartz.util.XmlUtils;

public class XmlConfigProvider implements ConfigProvider {

	private String xmlPath;

	private static final String XML_NAME = "jobs.xml";
	private static final String TAG_NAME_JOB = "job";
	private static final String TAG_NAME_NAME = "name";
	private static final String TAG_NAME_CLAZZ = "clazz";
	private static final String TAG_NAME_CRON = "cron";
	private static final String TAG_NAME_INTERVAL = "interval";
	private static final String TAG_NAME_DATA = "data";

	public XmlConfigProvider() {
		xmlPath = PathUtils.getCurrentDirectory()+ "/" + XML_NAME;
	}

	public List<JobModel> getJobModelList() {
		List<JobModel> jobModels = new ArrayList<JobModel>();
		Document document = XmlUtils.getDocument(xmlPath);
		NodeList jobs = document.getElementsByTagName(TAG_NAME_JOB);
		for (int i = 0; i < jobs.getLength(); i++) {
			Element job = (Element) jobs.item(i);

			Node name = job.getElementsByTagName(TAG_NAME_NAME).item(0);
			Node clazz = job.getElementsByTagName(TAG_NAME_CLAZZ).item(0);
			Node cron = job.getElementsByTagName(TAG_NAME_CRON).item(0);
			Node interval = job.getElementsByTagName(TAG_NAME_INTERVAL).item(0);
			Node data=job.getElementsByTagName(TAG_NAME_DATA).item(0);
			
			JobModel jobModel = new JobModel();
			jobModel.setName(name.getTextContent().trim());
			jobModel.setClazz(clazz.getTextContent().trim());

			if (cron != null  && cron.getTextContent().trim() != "") {
				jobModel.setCron(cron.getTextContent().trim());
			}

			if (interval != null && interval.getTextContent().trim() != "") {
				jobModel.setInterval(Integer.parseInt(interval.getTextContent().trim()));
			}

			if (data != null) {
				jobModel.setData(data.getTextContent().trim());
			}
			
			jobModels.add(jobModel);
		}
		return jobModels;
	}

}
