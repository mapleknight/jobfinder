package com.jobfinder.service;

import com.jobfinder.addons.AbstractFactory;
import com.jobfinder.addons.FactoryProducer;
import com.jobfinder.addons.JobServiceInterface;
import com.jobfinder.beans.JobDetail;
import com.jobfinder.beans.JobList;
import com.jobfinder.beans.JobListQueryParameters;
import com.jobfinder.common.*;

public class JobSearchService {
	
	public Object getJobList(JobListQueryParameters p, String jobSource) {
		ResponseDirector respDirector = new ResponseDirector();
		ResponseBuilder respBuilder = new JsonResponseBuilder();
		respDirector.setRespBuilder(respBuilder);
		
		AbstractFactory jobFactory = FactoryProducer.getFactory("job");
		JobServiceInterface jobService = jobFactory.getJobService(jobSource);
		
		JobList jobList = jobService.getJobList(p);
		if(null == jobList)
		{
			respDirector.constructRespObj(null, StateCode.FAILED_SYSTEM);
			return respDirector.getResponseObject();
		}
		
		respDirector.constructRespObj(jobList, StateCode.SUCCESS);
		
		return respDirector.getResponseObject();
	}

	public Object getJob(String jobID, String jobSource) {
		ResponseDirector respDirector = new ResponseDirector();
		ResponseBuilder respBuilder = new JsonResponseBuilder();
		respDirector.setRespBuilder(respBuilder);
		
		AbstractFactory jobFactory = FactoryProducer.getFactory("job");
		JobServiceInterface jobService = jobFactory.getJobService(jobSource);
		
		JobDetail job = jobService.getJobDetail(jobID);
		if(null == job)
		{
			respDirector.constructRespObj(null, StateCode.FAILED_SYSTEM);
			return respDirector.getResponseObject();
		}
		respDirector.constructRespObj(job, StateCode.SUCCESS);
		
		return respDirector.getResponseObject();
	}
}
