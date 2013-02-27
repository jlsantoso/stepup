package org.be.kuleuven.hci.stepup.controller;

import java.text.ParseException;

import javax.mail.MessagingException;

import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.utils.JSONValidation;
import org.be.kuleuven.hci.stepup.model.utils.JSONandEvent;
import org.be.kuleuven.hci.stepup.notifications.SendMail;
import org.be.kuleuven.hci.stepup.persistanceLayer.EventPostgreSQL;
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
			//MyThreadPoolExecutor.getMyThreadPoolExecutor().runTask(new InsertEvent(event));
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
				String query = "select distinct lower(username) as username from event where context=\'"+course+"'";
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
				return EventPostgreSQL.getOpenDB(query, "0");
	}
	
	public static String getStudent(String course , String username, String json){
		try {
			JSONObject additional_info = new JSONObject(json);
			if (additional_info.has("pag")){
				String query = "select * from event where context=\'"+course+"' and lower(username)='"+username.toLowerCase()+"'";
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
				String query = "select * from event where context=\'"+course+"' and username='"+username+"' and verb='"+verb+"'";
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
