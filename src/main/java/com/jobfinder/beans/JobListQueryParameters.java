package com.jobfinder.beans;

/**
 * this is the parameters used to search jobs
 * @author wangke
 *
 */
public class JobListQueryParameters {
	
	public String[] getKeywords() {
		return keywords;
	}
	
	public String getKeywordsString() {
		String result = "";
		for(String word : keywords)
		{
			result = result + "," + word;
		}
		return result.substring(1, result.length());
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}

	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * search keywords
	 */
	private String[] keywords;
	
	/**
	 * for CareerBuilder: Date, Relevance
	 * for JobServe: OVERALL_SCORE_DESC, EXPLORER_DATE_DESC
	 */
	private String sortBy = "Relevance";
	
	/**
	 * page number of this search
	 */
	private int pageNumber = 1;
	
	/**
	 * number of jobs listed in this search
	 */
	private int pageSize = 10;
	
	/**
	 * Can accept a single city name, a single state name, a postal code (as in: 30092), 
	 * a comma-separated city/state pair (as in: Atlanta, GA), 
	 * or a latitude and longitude in decimal degree (DD) format (as in 36.7636::-119.7746). 
	 */
	private String location;

}
