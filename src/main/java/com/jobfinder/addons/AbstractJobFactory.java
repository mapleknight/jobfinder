package com.jobfinder.addons;

import com.jobfinder.beans.JobDetail;
import com.jobfinder.beans.JobList;
import com.jobfinder.beans.JobListQueryParameters;

public abstract class AbstractJobFactory {
	
	public abstract JobList getJobList(JobListQueryParameters p);
	
	public abstract JobDetail getJobDetail(String jobID);

}
