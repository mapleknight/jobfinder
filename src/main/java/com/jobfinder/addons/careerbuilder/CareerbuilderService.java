package com.jobfinder.addons.careerbuilder;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.jobfinder.addons.JobServiceInterface;
import com.jobfinder.beans.JobDetail;
import com.jobfinder.beans.JobList;
import com.jobfinder.beans.JobListEntry;
import com.jobfinder.beans.JobListQueryParameters;
import com.jobfinder.utils.StringTool;
import com.jobfinder.utils.Xml2JsonUtil;

@SuppressWarnings("deprecation")

public class CareerbuilderService implements JobServiceInterface {
	/**
	 * Singleton
	 */
	private static CareerbuilderService instance;
	private CareerbuilderService(){}
	
	public static JobServiceInterface getInstance(){
        if(instance==null) 
            synchronized(CareerbuilderService.class){
                if(instance==null)
                	instance = new CareerbuilderService();
            }    
        return instance;
    }

	private HttpClient httpClient = new DefaultHttpClient();
	
	@Override
	public JobList getJobList(JobListQueryParameters p) {
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		// DeveloperKey=WDHL5DS66L2JTCTLYM63&HostSite=US&Keywords=software&OrderBy=Date
		qparams.add(new BasicNameValuePair("DeveloperKey",
				"WDHL5DS66L2JTCTLYM63"));
		qparams.add(new BasicNameValuePair("HostSite", "US"));
		qparams.add(new BasicNameValuePair("Keywords", p.getKeywordsString()));
		qparams.add(new BasicNameValuePair("OrderBy", p.getSortBy()));
		qparams.add(new BasicNameValuePair("PageNumber", String.valueOf(p
				.getPageNumber())));
		qparams.add(new BasicNameValuePair("PerPage", String.valueOf(p
				.getPageSize())));
		if (null != p.getLocation()) {
			qparams.add(new BasicNameValuePair("Location", p.getLocation()));
		}

		JobList list = new JobList();
		URI uri = null;
		try {
			uri = URIUtils.createURI("http",
					"api.careerbuilder.com", -1, "/v1/jobsearch",
					URLEncodedUtils.format(qparams, "UTF-8"), null);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			return null;
		}
		
		HttpGet get = new HttpGet(uri);
		try {
			get.addHeader("Connection", "close");
			get.addHeader("Accept-Charset", "utf-8");
			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String xmlStr = StringTool.convertStreamToString(instreams);
				String jsonStr = Xml2JsonUtil.xml2JSON(xmlStr);
				System.out.println(jsonStr);
				JSONObject resp = JSONObject.fromObject(jsonStr);
				resp = JSONObject.fromObject(resp.get("ResponseJobSearch"));
				
				String totalCount = resp.getJSONArray("TotalCount").getString(0);
				String firstItemIndex = resp.getJSONArray("FirstItemIndex").getString(0);
				String lastItemIndex = resp.getJSONArray("LastItemIndex").getString(0);
				String totalPages = resp.getJSONArray("TotalPages").getString(0);
				
				JSONArray jobArray = resp.getJSONArray("Results").getJSONObject(0).getJSONArray("JobSearchResult");
				
				
				list.setFirstItemIndex(firstItemIndex);
				list.setLastItemIndex(lastItemIndex);
				list.setTotalCount(totalCount);
				list.setTotalPages(totalPages);
				
				for(Object obj : jobArray)
				{
					JSONObject detail = JSONObject.fromObject(obj);
					JobListEntry entry = new JobListEntry();
					
					if(detail.containsKey("JobTitle")){
						String jobTitle = detail.getJSONArray("JobTitle").getString(0);
						entry.setJobTitle(jobTitle);
					}
					if(detail.containsKey("Company")){
						String company = detail.getJSONArray("Company").getString(0);
						entry.setCompany(company);
					}
					String postDate = detail.getJSONArray("PostedDate").getString(0);
					String jobID = detail.getJSONArray("DID").getString(0);
					String location = detail.getJSONArray("Location").getString(0);
					
					
					entry.setJobID(jobID);
					
					entry.setLocation(location);
					entry.setPostDate(postDate);
					
					list.addJob(JSONObject.fromObject(entry));
				}
				
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			get.releaseConnection();
			get.abort();
		}

		return list;

	}

	@Override
	public JobDetail getJobDetail(String jobID) {
		List<NameValuePair> qparams = new ArrayList<NameValuePair>();
		// DeveloperKey=WDHL5DS66L2JTCTLYM63&HostSite=US&Keywords=software&OrderBy=Date
		qparams.add(new BasicNameValuePair("DeveloperKey",
				"WDHL5DS66L2JTCTLYM63"));
		qparams.add(new BasicNameValuePair("DID", jobID));
		
		URI uri = null;
		try {
			uri = URIUtils.createURI("http",
					"api.careerbuilder.com", -1, "/v1/job",
					URLEncodedUtils.format(qparams, "UTF-8"), null);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			return null;
		}
		
		JobDetail job = new JobDetail();
		
		HttpGet get = new HttpGet(uri);
		try {
			get.addHeader("Connection", "close");
			get.addHeader("Accept-Charset", "utf-8");
			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String xmlStr = StringTool.convertStreamToString(instreams);
				String jsonStr = Xml2JsonUtil.xml2JSON(xmlStr);
				JSONObject resp = JSONObject.fromObject(jsonStr);
				System.out.println(resp.toString());
				
				resp = JSONObject.fromObject(resp.get("ResponseJob"));
				
				JSONObject jobJson = JSONArray.fromObject(resp.get("Job")).getJSONObject(0);
				
				job.setJobID(jobID);
				
				String jobDescription = null;
				String company = null;
				String contactPhone = null;
				String jobType = null;
				String jobTitle = null;
				String jobRequirements = null;
				String urlLink = null;
				
				if(jobJson.containsKey("Company")) {
					company = JSONArray.fromObject(jobJson.get("Company")).getString(0);
				}
				
				if(jobJson.containsKey("ContactInfoPhone")) {
					contactPhone = JSONArray.fromObject(jobJson.get("ContactInfoPhone")).getString(0);
				}
				
				if(jobJson.containsKey("JobTitle")) {
					jobTitle = JSONArray.fromObject(jobJson.get("JobTitle")).getString(0);
				}
				
				if(jobJson.containsKey("EmploymentType")) {
					jobType = JSONArray.fromObject(jobJson.get("EmploymentType")).getString(0);
				}
				
				if(jobJson.containsKey("JobDescription")) {
					jobDescription = JSONArray.fromObject(jobJson.get("JobDescription")).getString(0);
				}
				
				if(jobJson.containsKey("JobRequirements")) {
					jobRequirements = JSONArray.fromObject(jobJson.get("JobRequirements")).getString(0);
				}
				
				if(jobJson.containsKey("ApplyURL")) {
					urlLink = JSONArray.fromObject(jobJson.get("ApplyURL")).getString(0);
				}
				
				job.setCompany(company);
				job.setContactPhone(contactPhone);
				job.setJobDescription(jobDescription);
				job.setJobRequirements(jobRequirements);
				job.setJobTitle(jobTitle);
				job.setJobType(jobType);
				job.setUrlLink(urlLink);
				
			}

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			get.releaseConnection();
			get.abort();
		}
		
		return job;
	}
}
