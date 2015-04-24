package com.jobfinder.addons;

import com.jobfinder.addons.careerbuilder.CareerbuilderService;
import com.jobfinder.addons.jobserve.JobserveService;

public class JobFactory extends AbstractFactory {

	@Override
	public JobServiceInterface getJobService(String source) {
		
		JobServiceInterface jobService = null;
		
		if(source.equalsIgnoreCase("careerbuilder")){
			jobService = CareerbuilderService.getInstance();
		}
		else if(source.equalsIgnoreCase("jobserve")){
			jobService = JobserveService.getInstance();
		}
		return jobService;
	}

}
