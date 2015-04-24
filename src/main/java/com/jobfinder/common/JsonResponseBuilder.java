package com.jobfinder.common;

import net.sf.json.JSONObject;

public class JsonResponseBuilder extends ResponseBuilder {

	private JSONObject respObj;
	
	@Override
	public void setContent(Object content) {
		respObj.put("content", JSONObject.fromObject(content));
	}

	@Override
	public void setState(int state) {
		respObj.put("state", state);
	}

	@Override
	public Object getRespObj() {
		return respObj;
	}
}
