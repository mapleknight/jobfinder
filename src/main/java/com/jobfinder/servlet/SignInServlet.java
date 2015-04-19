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
import com.jobfinder.service.AccountService;

public class SignInServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1511635158379530762L;
	
	private AccountService accountService = new AccountService();
	
	/**
	 * response to /signin -post 
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
                JSONObject jo = JSONObject.fromObject(acceptjson);
                
                String name = jo.getString("name");
    			String password = jo.getString("password");
    			
    			result = accountService.checkUser(name, password);
            }

            
			
		} catch (Exception e) {
			//e.printStackTrace();
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
