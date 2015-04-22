package com.jobfinder.addons;

import com.jobfinder.common.JobDetail;
import com.jobfinder.common.JobList;
import com.jobfinder.common.JobListQueryParameters;

public abstract class AbstractJobFactory {
	
	public abstract JobList getJobList(JobListQueryParameters p);
	
	public abstract JobDetail getJobDetail(String jobID);

}
