package com.jobfinder.addons.careerbuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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

import com.jobfinder.addons.IAddonInterface;
import com.jobfinder.common.JobDetail;
import com.jobfinder.common.JobListEntry;
import com.jobfinder.common.JobListQueryParameters;
import com.jobfinder.utils.Xml2JsonUtil;

@SuppressWarnings("deprecation")
public class CareerbuilderAddon implements IAddonInterface {

	@Override
	public JobListEntry[] getJobList(JobListQueryParameters p) {

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

		HttpClient httpClient = new DefaultHttpClient();

		try {
			URI uri = URIUtils.createURI("http",
					"api.careerbuilder.com", -1, "/v1/jobsearch",
					URLEncodedUtils.format(qparams, "UTF-8"), null);
			HttpGet get = new HttpGet(uri);
			get.addHeader("Accept-Charset", "utf-8");

			HttpResponse response = httpClient.execute(get);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream instreams = entity.getContent();
				String xmlStr = convertStreamToString(instreams);
				String jsonStr = Xml2JsonUtil.xml2JSON(xmlStr);
				JSONObject resp = JSONObject.fromObject(jsonStr);
				resp = JSONObject.fromObject(resp.get("ResponseJobSearch"));
				
				String totalCount = JSONArray.fromObject(resp.get("TotalCount")).get(0).toString();
				
				String firstItemIndex = JSONArray.fromObject(resp.get("FirstItemIndex")).get(0).toString();
				String lastItemIndex = JSONArray.fromObject(resp.get("LastItemIndex")).get(0).toString();
				String totalPages = JSONArray.fromObject(resp.get("TotalPages")).get(0).toString();
				JSONArray jobArray = JSONArray.fromObject(JSONObject.fromObject(JSONArray.fromObject(resp.get("Results")).get(0)).get("JobSearchResult"));
				for(Object obj : jobArray)
				{
					JSONObject job = JSONObject.fromObject(obj);
					System.out.println(job.toString());
				}
				
				//System.out.println(job.toString());
				// Do not need the rest
				get.abort();
			}

		} catch (URISyntaxException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public JobDetail getJobDetail() {
		// TODO Auto-generated method stub
		return null;
	}

	public static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JobListQueryParameters p = new JobListQueryParameters();
		String[] keywords = {"software"};
		p.setKeywords(keywords);
		p.setLocation("dc");
		
		CareerbuilderAddon cba = new CareerbuilderAddon();
		cba.getJobList(p);
		
	}

}
