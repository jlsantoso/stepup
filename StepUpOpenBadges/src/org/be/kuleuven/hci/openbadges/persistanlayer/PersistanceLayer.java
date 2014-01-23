package org.be.kuleuven.hci.openbadges.persistanlayer;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.be.kuleuven.hci.openbadges.badges.Badge;
import org.be.kuleuven.hci.openbadges.badges.Badges;
import org.be.kuleuven.hci.openbadges.badges.rules.utils.Dates;
import org.be.kuleuven.hci.openbadges.utils.ActivityStream;
import org.be.kuleuven.hci.openbadges.utils.Event;
import org.be.kuleuven.hci.openbadges.utils.JSONandEvent;
import org.be.kuleuven.hci.openbadges.utils.RestClient;
import org.be.kuleuven.hci.openbadges.utils.StepUpConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.mortbay.log.Log;

import sun.util.logging.resources.logging;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;



public class PersistanceLayer {
	
	private static final Logger log = Logger.getLogger(PersistanceLayer.class.getName());

	public static void saveBadge(Badge badge){
		String description = "";
		if (badge.getDescription().length()>=500){
			description = badge.getDescription();
			badge.setDescription(badge.getDescription().substring(0, 500));
		}
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    syncCache.put(badge, true);
	    OfyService.getOfyService().ofy().save().entity(badge).now(); 	    
	    if (description.length()>0) badge.setDescription(description);
	}
	
	public static boolean existBadge(Badge badge){
		String description = "";
		if (badge.getDescription().length()>=500){
			description = badge.getDescription();
			badge.setDescription(badge.getDescription().substring(0, 500));
		}
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    if (syncCache.get(badge)!=null){
	    	if (description.length()>0) badge.setDescription(description);
	    	return true;	  
	    	
	    }else{
	    	System.out.println("access db");
	    	try{
	    		Badge badges = OfyService.getOfyService().ofy().load().type(Badge.class).filter("description", badge.getDescription()).first().get();
	    		if (description.length()>0) badge.setDescription(description);
	    		if (badges!=null) return true;
		    	return false;   	
	    	}catch(Exception e){
	    		return false;
	    	}
	    	
	    }
	    
	}
	
	public static String sendBadgeAsEvent(String username, String badge){
		String result = "";
		Event e = new Event();
		e.setContext("chikul13");
		e.setVerb("awarded");
		e.setStartTime(Calendar.getInstance().getTime());
		e.setUsername(username);
		
		try {
			JSONObject badgeJson = new JSONObject(badge);			
			e.setObject(badgeJson.getJSONObject("badge").getString("name"));
			e.setOriginalRequest(badgeJson);
			result += JSONandEvent.transformFromEvemtToJson(e).toString();
			String queryresult = RestClient.doPost(StepUpConstants.URLPUSHEVENT, JSONandEvent.transformFromEvemtToJson(e).toString());
			result += queryresult;
			log.warning("Sending an event to the data store: "+queryresult);
			/*ActivityStream as = new ActivityStream();
	    	as.setActor(username);
	    	as.setVerb("awarded");
	    	as.setObject("http://navi-hci.appspot.com/badgeboard?username="+username, "has earned a new badge:" +badgeJson.getJSONObject("badge").getString("description"));
	    	as.setPublishedDate(Calendar.getInstance().getTime());
	    	log.warning("Sending an event to TinyARM: "+RestClient.doPost("http://chi13course.appspot.com/api/activities/add", as.getActivityStream().toString()));*/
			return result;
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			log.severe(e2.toString());
		} catch (UnsupportedEncodingException e1) {
			log.severe(e1.toString());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			log.severe(e1.toString());
		}
		return "ERROR";
	}
	
		
	public static String sendBadgeAsEvent(String username, String badge, String course){
		String result = "";
		Event e = new Event();
		e.setContext(course);
		e.setVerb("awarded");
		e.setStartTime(Calendar.getInstance().getTime());
		e.setUsername(username);
		
		try {
			//System.out.println(e+badge);
			JSONObject badgeJson = new JSONObject(badge);		
			//System.out.println(e);
			e.setObject(badgeJson.getJSONObject("badge").getString("name"));
			//System.out.println(e);
			e.setOriginalRequest(badgeJson);
			//System.out.println(e);
			result += JSONandEvent.transformFromEvemtToJson(e).toString();
			
			
			String queryresult = RestClient.doPost(StepUpConstants.URLPUSHEVENT, JSONandEvent.transformFromEvemtToJson(e).toString());
			result += queryresult;
			log.warning("Sending an event to the data store: "+queryresult);
			return result;
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			log.severe(e2.toString());
		} catch (UnsupportedEncodingException e1) {
			log.severe(e1.toString());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			log.severe(e1.toString());
		}
		return "ERROR";
	}
	
	
	
	//Still to implement cache service
	
	public static boolean existInDbBadge(Badge badge){ 
		System.out.println("access db");
    	try{
    		Badge badges = OfyService.getOfyService().ofy().load().type(Badge.class).filter("description", badge.getDescription()).first().get();
	    	if (badges!=null) return true;
	    	return false;   	
    	}catch(Exception e){
    		return false;
    	}
	}
	
	//Still to implement cache service
	
	public static JSONArray badgesrewarded(){
		System.out.println("access db");
		JSONArray jsonArray = new JSONArray();
    	try{
    		List<Badge> badges = OfyService.getOfyService().ofy().load().type(Badge.class).list();
	    	for (Badge b : badges){
	    		JSONObject json = new JSONObject(b.getDescription());
	    		json.put("id", ""+b.getId()+"");
	    		jsonArray.put(json);
	    	}
	    	return jsonArray;
    	}catch(Exception e){

    	}
    	return null;
	}
	
	public static String badgeId(Long id){
		System.out.println("access db");
    	try{
    		Badge badge = OfyService.getOfyService().ofy().load().type(Badge.class).filter("id", id).first().get();
	    	return badge.getDescription();
    	}catch(Exception e){
    		log.severe(e.toString());
    	}
    	return null;
	}
}
