package org.be.kuleuven.hci.stepup.controller;

import java.text.ParseException;

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

public class EventController {
	static final Logger logger = Logger.getLogger(EventController.class);
	
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
	
	//This function allows whatever kind of query you can do to the database.	
	public static String getEvents(String json){
		try {
			JSONObject query = new JSONObject(json);
			if (query.has("query")&&query.has("pag")){
				return EventPostgreSQL.getOpenDB(query.getString("query"), query.getString("pag"));
			}
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		} catch (JSONException e) {
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.controller.EventController", "JSONObject\n"+json+"\n==============Exception==========\n"+e.toString()).send();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		}
	}
	
	
	// From here onwards, are specific queries that give support to the REST services 
	//implemented at package org.be.kuleuven.hci.stepup.services;
	//Some of them are specific for one course. This is something that should change.
	public static String getCourses(String json){
		try {
			JSONObject additional_info = new JSONObject(json);
			if (additional_info.has("pag")){
				String query = "select distinct context from event";
				if (additional_info.has("startdate")||additional_info.has("enddate")){
					query += " where";
				}
				if (additional_info.has("startdate")){
					query += " starttime>'"+additional_info.getString("startdate")+"'";
				}
				if (additional_info.has("enddate")){
					if (additional_info.has("startdate")) query += " and";
					query += " endtime>'"+additional_info.getString("enddate")+"'";
				}
				return EventPostgreSQL.getOpenDB(query, additional_info.getString("pag"));
			}
			
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		} catch (JSONException e) {
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.controller.EventController", "JSONObject\n"+json+"\n==============Exception==========\n"+e.toString()).send();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		}
	}
	
	public static String getStudents(String course ,String json){
		try {
			JSONObject additional_info = new JSONObject(json);
			if (additional_info.has("pag")){
				String query = "select distinct lower(username) as username from event where context like \'%"+course+"%'";
				if (additional_info.has("startdate")||additional_info.has("enddate")){
					query += " and";
				}
				if (additional_info.has("startdate")){
					query += " starttime>"+additional_info.getString("startdate");
				}
				if (additional_info.has("enddate")){
					if (additional_info.has("startdate")) query += " and";
					query += " endtime>"+additional_info.getString("enddate");
				}
				return EventPostgreSQL.getOpenDB(query, additional_info.getString("pag"));
			}
			
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		} catch (JSONException e) {
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.controller.EventController", "JSONObject\n"+json+"\n==============Exception==========\n"+e.toString()).send();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		}
	}
	
	public static String getChiBadges(){
			
				String query = "select * from event where context='chikul13' and verb='awarded'";
				return EventPostgreSQL.getOpenDB(query);
	}
	
	public static String getStudent(String course , String username, String json){
		try {
			JSONObject additional_info = new JSONObject(json);
			if (additional_info.has("pag")){
				String query = "select * from event where context like \'%"+course+"%' and lower(username)='"+username.toLowerCase()+"'";
				if (additional_info.has("startdate")||additional_info.has("enddate")){
					query += " and";
				}
				if (additional_info.has("startdate")){
					query += " starttime>"+additional_info.getString("startdate");
				}
				if (additional_info.has("enddate")){
					if (additional_info.has("startdate")) query += " and";
					query += " endtime>"+additional_info.getString("enddate");
				}
				return EventPostgreSQL.getOpenDB(query, additional_info.getString("pag"));
			}
			
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		} catch (JSONException e) {
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.controller.EventController", "JSONObject\n"+json+"\n==============Exception==========\n"+e.toString()).send();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		}
	}
	
	public static String getStudentPerVerb(String course , String verb, String json){
		
		try {
			JSONObject additional_info = new JSONObject(json);
			if (additional_info.has("pag")){
				String query = "select distinct lower(username) as username from event where context like \'%"+course+"%' and verb='"+verb+"'";
				if (additional_info.has("startdate")||additional_info.has("enddate")){
					query += " and";
				}
				if (additional_info.has("startdate")){
					query += " starttime>"+additional_info.getString("startdate");
				}
				if (additional_info.has("enddate")){
					if (additional_info.has("startdate")) query += " and";
					query += " endtime>"+additional_info.getString("enddate");
				}
				return EventPostgreSQL.getOpenDB(query, additional_info.getString("pag"));
			}
			
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		} catch (JSONException e) {
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.controller.EventController", "JSONObject\n"+json+"\n==============Exception==========\n"+e.toString()).send();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		}
	}
	
	public static String getStudent(String course , String username, String verb, String json){
		try {
			JSONObject additional_info = new JSONObject(json);
			if (additional_info.has("pag")){
				String query = "select * from event where context like \'%"+course+"%' and username='"+username+"' and verb='"+verb+"'";
				if (additional_info.has("startdate")||additional_info.has("enddate")){
					query += " and";
				}
				if (additional_info.has("startdate")){
					query += " starttime>"+additional_info.getString("startdate");
				}
				if (additional_info.has("enddate")){
					if (additional_info.has("startdate")) query += " and";
					query += " endtime>"+additional_info.getString("enddate");
				}
				return EventPostgreSQL.getOpenDB(query, additional_info.getString("pag"));
			}
			
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		} catch (JSONException e) {
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.controller.EventController", "JSONObject\n"+json+"\n==============Exception==========\n"+e.toString()).send();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		}
	}

	public static String getActivityRelated(String json) {
		Event event;
		try {
			event = JSONandEvent.transformFromJsonToEvent(new JSONObject(json));
			String username = event.getUsername();
			JSONObject originalrequest = event.getOriginalRequest();
			System.out.println("Original request "+originalrequest.toString());
			//System.out.println("{ \"query\": \"select * from event where context='chikul13' and DATE(starttime)>='"+originalrequest.getString("startdate")+"' and DATE(starttime)<='"+originalrequest.getString("enddate")+"'\", \"pag\": \"0\"}");
			String query = "select * from event where context='chikul13' and lower(username)='"+username+"' and DATE(starttime)>='"+originalrequest.getString("startdate")+"' and DATE(starttime)<='"+originalrequest.getString("enddate")+"'";
			System.out.println(query);
			String results = EventPostgreSQL.getOpenDB(query);
			JSONArray activity = new JSONArray(results);
			JSONArray result = new JSONArray();
			int max = 0;
			if (json.contains(" 5 tweets")){
				max = 5;
			}else if (json.contains("10 tweets")){
				max = 10;
			}else if (json.contains("15 tweets")){
				max = 15;
			}
			for (int i=0;i<max;i++){
				result.put(activity.get(i));
			}
			return result.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getBadges(String useridentifier, String from, String until, int pag) {
		if (pag>=0){
				String query = "select * from event where context like '%reinforcement%' and username='"+useridentifier+"'";
				if (from!=null||until!=null){
					query += " and";
				}
				if (from!=null){
					query += " starttime>'"+from+"'";
				}
				if (until!=null){
					if (from!=null) query += " and";
					query += " starttime<'"+until+"'";
				}
				return EventPostgreSQL.getOpenDB(query, Integer.toString(pag));
			}
			
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 

	}

	public static String getEventsByInquiryId(String inquiryid, String json) {
		try {
			JSONObject additional_info = new JSONObject(json);
			if (additional_info.has("pag")){
				String query = "select * from event where context like '%"+inquiryid+"%'";
				if (additional_info.has("startdate")||additional_info.has("enddate")){
					query += " and";
				}
				if (additional_info.has("startdate")){
					query += " starttime>"+additional_info.getString("startdate");
				}
				if (additional_info.has("enddate")){
					if (additional_info.has("startdate")) query += " and";
					query += " endtime>"+additional_info.getString("enddate");
				}
				return EventPostgreSQL.getOpenDB(query, additional_info.getString("pag"));
			}
			
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		} catch (JSONException e) {
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.controller.EventController", "JSONObject\n"+json+"\n==============Exception==========\n"+e.toString()).send();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return "{\"status\":\"500\", \"error\":\"JSON is not properly defined\"}"; 
		} 
	}	
	
}
