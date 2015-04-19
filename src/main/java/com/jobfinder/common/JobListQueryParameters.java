package com.jobfinder.common;

/**
 * this is the parameters used to search jobs
 * @author wangke
 *
 */
public class JobListQueryParameters {
	
	/**
	 * search keywords
	 */
	private String[] keywords;
	
	/**
	 * Date, Relevance
	 * OVERALL_SCORE_DESC
	 * EXPLORER_DATE_DESC
	 */
	private String sortBy;
	
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
