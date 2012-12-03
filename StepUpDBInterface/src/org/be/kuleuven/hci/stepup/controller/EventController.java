package org.be.kuleuven.hci.stepup.controller;

import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.utils.JSONValidation;
import org.json.JSONException;
import org.json.JSONObject;

public class EventController {
	
	public static String insertEvent(String json){
		try {
			JSONObject event = new JSONObject(json);
			if (!JSONValidation.checkJSONMandatoryAtributtes(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Remember that username, verb, starrtime and object are mandatory\"}"; 
			if (!JSONValidation.checkJSONStarttimeAtributte(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Remember that the date format is yyyy-MM-dd hh:mm:ss ZZZZZ\"}"; 
			if (!JSONValidation.checkJSONEndtimeAtributte(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Remember that the date format is yyyy-MM-dd hh:mm:ss ZZZZZ\"}"; 
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		}
		return "";		
	}
}
