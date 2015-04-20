package com.jobfinder.servlet;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.jobfinder.beans.ReturnObject;
import com.jobfinder.common.StateCode;
import com.jobfinder.service.JobSearchService;

public class JobSearchServlet extends HttpServlet{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2663403094172241068L;
	
	JobSearchService jobSearchService = new JobSearchService();
	
	/**
	 * response to /job -post 
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		
		response.setCharacterEncoding("UTF-8");
	    response.setContentType("application/json; charset=utf-8");
	    
	    PrintWriter out = null;
		JSONObject result = null;

		try {
			out = response.getWriter();
			
			BufferedReader br = new BufferedReader(new InputStreamReader(
					(ServletInputStream) request.getInputStream(), "utf-8"));
			StringBuffer sb = new StringBuffer("");
			String temp;
			while ((temp = br.readLine()) != null) {
				sb.append(temp);
			}
			br.close();
			
			
			String acceptjson = sb.toString();  
            if (acceptjson != "") {  
                JSONObject query = JSONObject.fromObject(acceptjson);
                String jobID = query.getString("jobID");
                String jobSource = query.getString("jobSource");
    			result = jobSearchService.getJob(jobID, jobSource);
            }

		} catch (Exception e) {
			e.printStackTrace();
			result = JSONObject.fromObject(new ReturnObject(StateCode.FAILED_SYSTEM));
		} finally {
			//result.remove("content");
			out.append(result.toString());
			
			if (out != null) {
				out.close();
			}
		}
	    
	}

}
