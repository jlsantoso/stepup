package org.be.kuleuven.hci.stepup.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.mail.MessagingException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.utils.JSONValidation;
import org.be.kuleuven.hci.stepup.model.utils.JSONandEvent;
import org.be.kuleuven.hci.stepup.notifications.SendMail;
import org.be.kuleuven.hci.stepup.persistanceLayer.EventPostgreSQL;
import org.be.kuleuven.hci.stepup.persistanceLayer.Threads.InsertEvent;
import org.be.kuleuven.hci.stepup.services.PushEvent;
import org.be.kuleuven.hci.stepup.utils.RestClient;
import org.be.kuleuven.hci.stepup.controller.utils.MyThreadPoolExecutor;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventControllerV2 {
	static final Logger logger = Logger.getLogger(EventControllerV2.class);
	
	public static String getStringFromLongDate(String date){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ZZZZZ");
		long datelong= Long.parseLong(date);
		String datestring = formatter.format(new Date(datelong));
		return datestring;
	}
	
	public static String addTimeRestrictionToQuery(String from, String to){
		String query = "";
		if (from.compareTo("")!=0||to.compareTo("")!=0){
			query += " and";
			if (from.compareTo("")!=0){
				query += " starttime>"+getStringFromLongDate(from);
			}
			if (to.compareTo("")!=0){
				if (from.compareTo("")!=0) query += " and";
				query += " endtime>"+getStringFromLongDate(to);
			}				
		}
		return query;
	}
	
	//Insert the event in the data store. First it checks if the data is correct. 
	//Once that the event is validated, it creates a thread for the insertion.
	public static String insertEvent(String json){
		try {
			JSONObject event = new JSONObject(json);
			if (!JSONValidation.checkJSONMandatoryAtributtes(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Remember that username, verb, starrtime and object are mandatory\"}"; 
			if (!JSONValidation.checkJSONStarttimeAtributte(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Remember that the date format is yyyy-MM-dd hh:mm:ss ZZZZZ\"}"; 
			if (!JSONValidation.checkJSONEndtimeAtributte(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Remember that the date format is yyyy-MM-dd hh:mm:ss ZZZZZ\"}"; 
			if (!JSONValidation.checkJSONOriginalrequestJSONFormatAtributte(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Check Original Request field\"}"; 
			//MyThreadPoolExecutor.getMyThreadPoolExecutor().runTask(new InsertEvent(event));
			BasicConfigurator.configure();
			logger.warn("InsertEvent: "+event.toString());
			new InsertEvent(event).run();
			
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.controller.EventController", "JSONObject\n"+json+"\n==============Exception==========\n"+e.toString()).send();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		}
		return "{\"status\":\"200\", \"error\":\"\"}";	
	}
		
	//Insert and array of events in the data store. First it checks if the data is correct. 
	//Once that the event is validated, it creates a thread for the insertion.
	public static String insertEvents(String json){
		try {
			JSONArray events = new JSONArray(json);
			for (int i=0; i<events.length(); i++){
				JSONObject event = events.getJSONObject(i);
				if (!JSONValidation.checkJSONMandatoryAtributtes(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Remember that username, verb, starrtime and object are mandatory\"}"; 
				if (!JSONValidation.checkJSONStarttimeAtributte(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Remember that the date format is yyyy-MM-dd hh:mm:ss ZZZZZ\"}"; 
				if (!JSONValidation.checkJSONEndtimeAtributte(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Remember that the date format is yyyy-MM-dd hh:mm:ss ZZZZZ\"}"; 
				if (!JSONValidation.checkJSONOriginalrequestJSONFormatAtributte(event)) return "{\"status\":\"500\", \"error\":\"JSON is not properly defined. Check Original Request field\"}"; 
				//MyThreadPoolExecutor.getMyThreadPoolExecutor().runTask(new InsertEvent(event));
				BasicConfigurator.configure();
				logger.warn("InsertEvent: "+event.toString());
				new InsertEvent(event).run();
			}			
		} catch (JSONException e) {
			e.printStackTrace();
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.controller.EventController", "JSONObject\n"+json+"\n==============Exception==========\n"+e.toString()).send();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		}
		return "{\"status\":\"200\", \"error\":\"\"}";	
	}
	
	public static String getStudentsIds(String course , String from, String to, String pag){
		String query = "select distinct lower(username) as username from event where context like \'%"+course+"%'";
		query+=addTimeRestrictionToQuery(from, to);
		return EventPostgreSQL.getOpenDB(query, pag);
	}
	
	public static String getEventsPerStudentsIdsAndCourse(String course, String student, String from, String to, String pag){
		String query = "select * from event where context like \'%"+course+"%' and lower(username)='"+student.toLowerCase()+"'";
		query+=addTimeRestrictionToQuery(from, to);
		return EventPostgreSQL.getOpenDB(query, pag);
	}
	
	public static String getEventsPerCourseIds(String course, String from, String to, String pag){
		String query = "select * from event where context like \'%"+course+"%'";
		query+=addTimeRestrictionToQuery(from, to);
		return EventPostgreSQL.getOpenDB(query, pag);
	}
	
	public static String getEventsPerCourseIdsAndVerbs(String course, String verb, String from, String to, String pag){
		String query = "select * from event where context like \'%"+course+"%' and verb='"+verb+"'";
		query+=addTimeRestrictionToQuery(from, to);
		return EventPostgreSQL.getOpenDB(query, pag);
	}
			
	
}
