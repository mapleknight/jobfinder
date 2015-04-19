package com.jobfinder.addons;

import com.jobfinder.common.JobDetail;
import com.jobfinder.common.JobListEntry;
import com.jobfinder.common.JobListQueryParameters;


public interface IAddonInterface {
	
	public JobListEntry[] getJobList(JobListQueryParameters p);
	
	public JobDetail getJobDetail();

}
