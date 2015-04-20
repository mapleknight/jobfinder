package com.jobfinder.addons;

import com.jobfinder.common.JobDetail;
import com.jobfinder.common.JobList;
import com.jobfinder.common.JobListQueryParameters;


public interface IAddonInterface {
	
	public JobList getJobList(JobListQueryParameters p);
	
	public JobDetail getJobDetail(String jobID);

}
