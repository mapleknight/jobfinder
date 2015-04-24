package com.jobfinder.addons.jobserve;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import com.jobfinder.addons.JobServiceInterface;
import com.jobfinder.beans.JobDetail;
import com.jobfinder.beans.JobList;
import com.jobfinder.beans.JobListEntry;
import com.jobfinder.beans.JobListQueryParameters;
import com.jobfinder.utils.GeoUtil;
import com.jobfinder.utils.StringTool;

@SuppressWarnings("deprecation")

public class JobserveService implements JobServiceInterface {
	
	/**
	 * Singleton
	 */
	private static JobserveService instance;
	private JobserveService(){}

	public static JobServiceInterface getInstance() {
		if (instance == null)
			synchronized (JobserveService.class) {
				if (instance == null)
					instance = new JobserveService();
			}
		return instance;
	}
	
	private static final String AuthorizationHeader = "token 6t6eXsLhq8JuESV3ZjXUQCdUzxzcpM3r3kZif239d-85w2Abc-avgvmelgAouveRxGouN6Yx-fAmVfT54d-MYsw5ohP_tqC-uIrQviJY8X8vRx_0Do4AfDdqaYVJCRYqiv2MQVTlB9aEX6z7r748QMYRWynYAZF8Y6Xd3hEYzQU";

	private HttpClient httpClient = new DefaultHttpClient();

	@Override
	public JobList getJobList(JobListQueryParameters p) {
		JobList list = new JobList();

		HttpPost post = new HttpPost("http://services.jobserve.com/Jobs");

		JSONObject jsonBody = new JSONObject();
		jsonBody.put("Skills", p.getKeywordsString());
		
		jsonBody.put("SortOrder", p.getSortBy());
		jsonBody.put("Page", String.valueOf(p.getPageNumber()));
		jsonBody.put("PageSize", String.valueOf(p.getPageSize()));
		if (null != p.getLocation()) {
			JSONObject geo = GeoUtil.getLocation(p.getLocation());
			JSONObject location = this.getLocation(geo);
			JSONArray locations = new JSONArray();
			locations.add(location);
			jsonBody.put("Locations", locations);
		}

		try {
			post.addHeader("Connection", "close");
			post.addHeader("Accept-Charset", "utf-8");
			post.addHeader("Authorization", AuthorizationHeader);
			post.addHeader("Accept", "application/json");
			post.addHeader(HTTP.CONTENT_TYPE, "application/json");

			StringEntity se = new StringEntity(jsonBody.toString());
			post.setEntity(se);

			HttpResponse response = httpClient.execute(post);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String jsonStr = StringTool.convertStreamToString(instreams);
				
				System.out.println(jsonStr);
				JSONObject resp = JSONObject.fromObject(jsonStr);

				String totalCount = resp.getString("JobCount");
				String totalPages = resp.getString("PageCount");
				int start = p.getPageSize() * (resp.getInt("PageNo") - 1) + 1;
				int end = start + resp.getInt("ThisPageSize") - 1;
				String firstItemIndex = String.valueOf(start);
				String lastItemIndex = String.valueOf(end);

				list.setFirstItemIndex(firstItemIndex);
				list.setLastItemIndex(lastItemIndex);
				list.setTotalCount(totalCount);
				list.setTotalPages(totalPages);

				JSONArray jobArray = resp.getJSONArray("Jobs");

				for (Object obj : jobArray) {

					JSONObject detail = JSONObject.fromObject(obj);
					JobListEntry entry = new JobListEntry();

					if (detail.containsKey("Position")) {
						String jobTitle = detail.getString("Position");
						entry.setJobTitle(jobTitle);
					}
					if (detail.containsKey("RecruiterName")) {
						String company = detail.getString("RecruiterName");
						entry.setCompany(company);
					}
					String postDate = detail.getString("DatePosted");
					String jobID = detail.getString("ID");
					String location = detail.getJSONObject("Location")
							.getString("Text");

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
			post.releaseConnection();
			post.abort();
		}

		return list;
	}

	@Override
	public JobDetail getJobDetail(String jobID) {
		URI uri = null;
		try {
			uri = URIUtils.createURI("http", "services.jobserve.com", -1,
					"/Jobs/" + jobID, null, null);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			return null;
		}
		
		JobDetail job = new JobDetail();
		
		HttpGet get = new HttpGet(uri);
		try {
			get.addHeader("Connection", "close");
			get.addHeader("Accept-Charset", "utf-8");
			get.addHeader("Authorization", AuthorizationHeader);
			get.addHeader("Accept", "application/json");
			
			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String jsonStr = StringTool.convertStreamToString(instreams);
				
				JSONObject resp = JSONObject.fromObject(jsonStr);
				
				job.setJobID(jobID);
				
				String jobDescription = null;
				String company = null;
				String contactPhone = null;
				String jobType = null;
				String jobTitle = null;
				String urlLink = null;
				
				if(resp.containsKey("RecruiterName")) {
					company = resp.getString("RecruiterName");
				}
				
				if(resp.containsKey("Telephone")) {
					contactPhone = resp.getString("Telephone");
				}
				
				if(resp.containsKey("Position")) {
					jobTitle = resp.getString("Position");
				}
				
				if(resp.containsKey("JobType")) {
					jobType = resp.getString("JobType");
				}
				
				if(resp.containsKey("HtmlDescription")) {
					jobDescription = resp.getString("HtmlDescription");
				}
				
				if(resp.containsKey("Permalink")) {
					urlLink = resp.getString("Permalink");
				}
				
				job.setCompany(company);
				job.setContactPhone(contactPhone);
				job.setJobDescription(jobDescription);
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
	
	public JSONObject getLocation(JSONObject latAndlng) {

		URI uri = null;
		try {
			uri = URIUtils.createURI("http", "services.jobserve.com", -1,
					"/Locations/" + latAndlng.getString("lat") + "/"
							+ latAndlng.getString("lng"), null, null);
		} catch (URISyntaxException e1) {
			e1.printStackTrace();
			return null;
		}

		HttpGet get = new HttpGet(uri);
		JSONObject location = null;
		try {
			get.addHeader("Connection", "close");
			get.addHeader("Accept-Charset", "utf-8");
			get.addHeader("Authorization", AuthorizationHeader);
			get.addHeader("Accept", "application/json");

			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String jsonStr = StringTool.convertStreamToString(instreams);
				location = JSONObject.fromObject(jsonStr).getJSONObject(
						"Location");
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} finally {
			get.releaseConnection();
			get.abort();
		}

		return location;
	}
}
