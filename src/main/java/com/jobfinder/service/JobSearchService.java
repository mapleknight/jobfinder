package com.jobfinder.service;

import com.jobfinder.addons.AbstractJobFactory;
import com.jobfinder.addons.AddonFactory;
import com.jobfinder.beans.ReturnObject;
import com.jobfinder.common.JobDetail;
import com.jobfinder.common.JobList;
import com.jobfinder.common.JobListQueryParameters;
import com.jobfinder.common.StateCode;

import net.sf.json.JSONObject;

public class JobSearchService {

	public JSONObject getJobList(JobListQueryParameters p, String jobSource) {
		ReturnObject ret = new ReturnObject();
		ret.setState(StateCode.SUCCESS);
		AbstractJobFactory jobSearchAddon = AddonFactory.getConcreteFactory(jobSource);
		
		JobList jobList = jobSearchAddon.getJobList(p);
		if(null == jobList)
		{
			ret.setState(StateCode.FAILED_SYSTEM);
			return JSONObject.fromObject(ret);
		}
		ret.setContent(JSONObject.fromObject(jobList));
		
		return JSONObject.fromObject(ret);
	}

	public JSONObject getJob(String jobID, String jobSource) {
		ReturnObject ret = new ReturnObject();
		ret.setState(StateCode.SUCCESS);
		AbstractJobFactory jobSearchAddon = AddonFactory.getConcreteFactory(jobSource);
		
		JobDetail job = jobSearchAddon.getJobDetail(jobID);
		if(null == job)
		{
			ret.setState(StateCode.FAILED_SYSTEM);
			return JSONObject.fromObject(ret);
		}
		ret.setContent(JSONObject.fromObject(job));
		
		return JSONObject.fromObject(ret);
	}
}
