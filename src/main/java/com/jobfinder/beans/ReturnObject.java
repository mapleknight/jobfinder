package com.jobfinder.beans;


import com.jobfinder.common.StateCode;

import net.sf.json.JSONObject;

public class ReturnObject {
	
	private int state;
	
	private JSONObject content;
	
	public ReturnObject(){
		content = new JSONObject();
		state = StateCode.SUCCESS;
	}
	
	public ReturnObject(int state)
	{
		this.state = state;
		content = new JSONObject();
	}
	
	public ReturnObject(int state, JSONObject content)
	{
		this.state = state;
		this.content = content;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public JSONObject getContent() {
		return content;
	}

	public void setContent(JSONObject content) {
		this.content = content;
	}
	
	

}
