package org.be.kuleuven.hci.stepup.controller;

import javax.mail.MessagingException;

import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.utils.JSONValidation;
import org.be.kuleuven.hci.stepup.notifications.SendMail;
import org.be.kuleuven.hci.stepup.persistanceLayer.Threads.InsertEvent;
import org.be.kuleuven.hci.stepup.controller.utils.MyThreadPoolExecutor;
import org.json.JSONException;
import org.json.JSONObject;

public class EventController {
	
	public static String insertEvent(String json){
		try {
			JSONObject event = new JSONObject(json);
			if (!JSONValidation.checkJSONMandatoryAtributtes(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Remember that username, verb, starrtime and object are mandatory\"}"; 
			if (!JSONValidation.checkJSONStarttimeAtributte(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Remember that the date format is yyyy-MM-dd hh:mm:ss ZZZZZ\"}"; 
			if (!JSONValidation.checkJSONEndtimeAtributte(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Remember that the date format is yyyy-MM-dd hh:mm:ss ZZZZZ\"}"; 
			MyThreadPoolExecutor.getMyThreadPoolExecutor().runTask(new InsertEvent(event));
		} catch (JSONException e) {
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.controller.EventController", "JSONObject\n"+json+"\n==============Exception==========\n"+e.toString()).send();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		}
		return "";		
	}
}
