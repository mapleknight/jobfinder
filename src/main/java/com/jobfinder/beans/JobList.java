package com.jobfinder.beans;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JobList {
	
	private String totalCount;
	
	private String totalPages;
	
	private String lastItemIndex;
	
	private String firstItemIndex;
	
	private JSONArray jobs = new JSONArray();

	public String getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}

	public String getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(String totalPages) {
		this.totalPages = totalPages;
	}

	public String getLastItemIndex() {
		return lastItemIndex;
	}

	public void setLastItemIndex(String lastItemIndex) {
		this.lastItemIndex = lastItemIndex;
	}

	public String getFirstItemIndex() {
		return firstItemIndex;
	}

	public void setFirstItemIndex(String firstItemIndex) {
		this.firstItemIndex = firstItemIndex;
	}

	public JSONArray getJobs() {
		return jobs;
	}

	public void setJobs(JSONArray jobs) {
		this.jobs = jobs;
	}
	
	public void addJob(JSONObject job) {
		this.jobs.add(job);
	}

}
