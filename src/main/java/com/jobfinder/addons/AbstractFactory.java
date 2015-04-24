package com.jobfinder.addons;

public abstract class AbstractFactory {
	
	public abstract JobServiceInterface getJobService(String source);

}
