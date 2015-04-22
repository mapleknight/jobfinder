package com.jobfinder.service;

import com.jobfinder.addons.AbstractJobFactory;
import com.jobfinder.addons.AddonFactory;
import com.jobfinder.beans.JobDetail;
import com.jobfinder.beans.JobList;
import com.jobfinder.beans.JobListQueryParameters;
import com.jobfinder.common.*;

public class JobSearchService {

	public Object getJobList(JobListQueryParameters p, String jobSource) {
		ResponseDirector respDirector = new ResponseDirector();
		ResponseBuilder respBuilder = new JsonResponseBuilder();
		respDirector.setRespBuilder(respBuilder);
		
		AbstractJobFactory jobSearchAddon = AddonFactory.getConcreteFactory(jobSource);
		
		JobList jobList = jobSearchAddon.getJobList(p);
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
		
		AbstractJobFactory jobSearchAddon = AddonFactory.getConcreteFactory(jobSource);
		
		JobDetail job = jobSearchAddon.getJobDetail(jobID);
		if(null == job)
		{
			respDirector.constructRespObj(null, StateCode.FAILED_SYSTEM);
			return respDirector.getResponseObject();
		}
		respDirector.constructRespObj(job, StateCode.SUCCESS);
		
		return respDirector.getResponseObject();
	}
}
