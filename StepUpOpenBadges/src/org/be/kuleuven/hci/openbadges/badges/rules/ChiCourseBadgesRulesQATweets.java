package org.be.kuleuven.hci.openbadges.badges.rules;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.be.kuleuven.hci.openbadges.badges.Badge;
import org.be.kuleuven.hci.openbadges.badges.ChiCourse;
import org.be.kuleuven.hci.openbadges.badges.rules.utils.Dates;
import org.be.kuleuven.hci.openbadges.mailnotification.Mail;
import org.be.kuleuven.hci.openbadges.persistanlayer.PersistanceLayer;
import org.be.kuleuven.hci.openbadges.utils.ReadGoogleSpreadSheet;
import org.be.kuleuven.hci.openbadges.utils.RestClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class ChiCourseBadgesRulesQATweets {

	public static void qatweets(){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		int current_week=Dates.getCurrentBiWeekOfTheCourse();
		int pagination = 0;
		try {
			if (syncCache.get("membersGroup")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterEmailNotification")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterOpenBadges")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("usernamesUrl")==null) ReadGoogleSpreadSheet.read();
			Hashtable<String,String> twitterEmailNotification = (Hashtable<String, String>) syncCache.get("twitterEmailNotification");
			Hashtable<String,String> membersGroup = (Hashtable<String, String>) syncCache.get("membersGroup");
			Hashtable<String,String> twitterOpenBadges = (Hashtable<String, String>) syncCache.get("twitterOpenBadges");
			Hashtable<String,String> usernamesUrl = (Hashtable<String, String>) syncCache.get("usernamesUrl");
			Enumeration e = membersGroup.keys();
			int group = 0;

			String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select target, count (*) from event where context='chikul13' and target!='' and verb='tweeted' "+Dates.getQueryBetweenDates()+" group by target order by count desc\", \"pag\": \""+pagination+"\"}");
			JSONArray results = new JSONArray(queryresult);
			System.out.println(queryresult);

	        int count = 0;
	        
	        for (int i=0;i<results.length();i++){
	        	JSONObject json = results.getJSONObject(i);
	        	if (Integer.parseInt(json.getString("count"))>=5){
	        		Badge badge = new Badge();
					badge.setDescription(ChiCourse.createBiWeekly5QATweets(twitterOpenBadges.get(json.getString("target")), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
					if (!PersistanceLayer.existBadge(badge)&&twitterEmailNotification.containsKey(json.getString("target"))){
						//System.out.println(badge.getDescription());
						PersistanceLayer.saveBadge(badge);
						Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+json.getString("target"), twitterEmailNotification.get(json.getString("target")));
						PersistanceLayer.sendBadgeAsEvent(json.getString("target"), badge.getDescription());
					}
	        	}
	        	if (Integer.parseInt(json.getString("count"))>=10){
	        		Badge badge = new Badge();
					badge.setDescription(ChiCourse.createBiWeekly10QATweets(twitterOpenBadges.get(json.getString("target")), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
					if (!PersistanceLayer.existBadge(badge)&&twitterEmailNotification.containsKey(json.getString("target"))){
						//System.out.println(badge.getDescription());
						PersistanceLayer.saveBadge(badge);
						Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+json.getString("target"), twitterEmailNotification.get(json.getString("target")));
						PersistanceLayer.sendBadgeAsEvent(json.getString("target"), badge.getDescription());
					}
	        	}
	        	if (Integer.parseInt(json.getString("count"))>=15&&twitterEmailNotification.containsKey(json.getString("target"))){
	        		Badge badge = new Badge();
					badge.setDescription(ChiCourse.createBiWeekly15QATweets(twitterOpenBadges.get(json.getString("target")), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
					if (!PersistanceLayer.existBadge(badge)){
						//System.out.println(badge.getDescription());
						PersistanceLayer.saveBadge(badge);
						Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+json.getString("target"), twitterEmailNotification.get(json.getString("target")));
						PersistanceLayer.sendBadgeAsEvent(json.getString("target"), badge.getDescription());
					}
	        	}
	        }
		        
	        
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}