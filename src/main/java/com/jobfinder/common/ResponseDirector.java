package com.jobfinder.common;

public class ResponseDirector {
	
	private ResponseBuilder respBuilder;
	
	public void setRespBuilder(ResponseBuilder builder) {
		this.respBuilder = builder;
	}
	
	public Object getResponseObject(){
		
		return respBuilder.getRespObj();
	}
	
	public void constructRespObj(Object content, int state){
		
		respBuilder.setContent(content);
		respBuilder.setState(state);
		
	}
}
