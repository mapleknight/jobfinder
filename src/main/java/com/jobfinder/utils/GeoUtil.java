package com.jobfinder.utils;

import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.google.code.geocoder.Geocoder;
import com.google.code.geocoder.GeocoderRequestBuilder;
import com.google.code.geocoder.model.GeocodeResponse;
import com.google.code.geocoder.model.GeocoderRequest;

public class GeoUtil {
	
	public static JSONObject getLocation(String location)
	{
		final Geocoder geocoder = new Geocoder();
		GeocoderRequest geocoderRequest = new GeocoderRequestBuilder().setAddress(location).setLanguage("en").getGeocoderRequest();
		GeocodeResponse geocoderResponse;
		try {
			geocoderResponse = geocoder.geocode(geocoderRequest);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		JSONObject geoLocation = JSONObject.fromObject(JSONObject.fromObject(JSONObject.fromObject(JSONArray.fromObject(geocoderResponse.getResults()).get(0)).get("geometry")).get("location"));
//		String lat = geoLocation.getString("lat");
//		String lng = geoLocation.getString("lng");
//		System.out.println(lat);
//		System.out.println(lng);
		//System.out.println(JSONArray.fromObject(geocoderResponse.getResults()).toString());
		return geoLocation;
	}

}
