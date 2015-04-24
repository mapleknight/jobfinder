package com.jobfinder.addons;

import com.jobfinder.beans.JobDetail;
import com.jobfinder.beans.JobList;
import com.jobfinder.beans.JobListQueryParameters;

public interface JobServiceInterface {
	
	/**
	 * get the instance of a plugin job service
	 * @return
	 */
	public static JobServiceInterface getInstance(){ return null;}
	
	/**
	 * get a job list using the fiters described in JobListQueryParameters p
	 * @param p
	 * @return
	 */
	public JobList getJobList(JobListQueryParameters p);
	
	/**
	 * get the detail of a job which is specified by jobID
	 * @param jobID
	 * @return
	 */
	public JobDetail getJobDetail(String jobID);

}
