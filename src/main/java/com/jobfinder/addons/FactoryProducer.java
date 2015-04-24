package com.jobfinder.addons;

public class FactoryProducer {
	
	public static AbstractFactory getFactory(String service){
		   
	      if(service.equalsIgnoreCase("Job")){
	         return new JobFactory();
	      }
	      
	      return null;
	   }
}

