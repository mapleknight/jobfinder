package com.jobfinder.service;

import com.jobfinder.addons.IAddonInterface;
import com.jobfinder.addons.careerbuilder.CareerbuilderAddon;
import com.jobfinder.addons.jobserve.JobserveAddon;
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
		IAddonInterface jobSearchAddon = CareerbuilderAddon.getInstance();;
		if(jobSource.equalsIgnoreCase("jobserve")){
			jobSearchAddon = JobserveAddon.getInstance();
		}
		
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
		IAddonInterface jobSearchAddon = CareerbuilderAddon.getInstance();;
		if(jobSource.equalsIgnoreCase("jobserve")){
			jobSearchAddon = JobserveAddon.getInstance();
		}
		
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
