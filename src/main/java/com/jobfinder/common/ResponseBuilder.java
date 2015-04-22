package com.jobfinder.common;

public abstract class ResponseBuilder {
	
	public abstract Object getRespObj();
	
	public abstract void setContent(Object content);
	
	public abstract void setState(int state);

}
