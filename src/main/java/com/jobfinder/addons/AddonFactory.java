package com.jobfinder.addons;

import com.jobfinder.addons.careerbuilder.CareerbuilderFactory;
import com.jobfinder.addons.jobserve.JobserveFactory;

public class AddonFactory {
	
	public static AbstractJobFactory getConcreteFactory(String source) {
		
		AbstractJobFactory addon = null;
		
		if(source.equalsIgnoreCase("careerbuilder")){
			addon = CareerbuilderFactory.getInstance();
		}
		else if(source.equalsIgnoreCase("jobserve")){
			addon = JobserveFactory.getInstance();
		}
		return addon;
	}

}
