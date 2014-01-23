package org.be.kuleuven.hci.openbadges.badges.rules;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

public class ChiCourseBadgesParticipation {

	public static void mostAndNoParticipativeStudent(){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		int current_week=Dates.getCurrentBiWeekOfTheCourse();
		int pagination = 0;
		try {
			if (syncCache.get("membersGroup")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterEmailNotification")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterOpenBadges")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("usernamesUrl")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("membersURL")==null) ReadGoogleSpreadSheet.read();
			Hashtable<String,String> twitterEmailNotification = (Hashtable<String, String>) syncCache.get("twitterEmailNotification");
			Hashtable<String,String> membersGroup = (Hashtable<String, String>) syncCache.get("membersGroup");
			Hashtable<String,String> twitterOpenBadges = (Hashtable<String, String>) syncCache.get("twitterOpenBadges");
			Hashtable<String,String> usernamesUrl = (Hashtable<String, String>) syncCache.get("usernamesUrl");
			Hashtable<String,String> membersURL = (Hashtable<String, String>) syncCache.get("membersURL");
			ArrayList<String> usernames = new ArrayList();
			ArrayList<String> usernamesZeroActivity = new ArrayList();
			Enumeration e = usernamesUrl.keys();
			int max_comments = 0;
			String final_username="";
			while(e.hasMoreElements()){
				String username = (String) e.nextElement();
				//System.out.println("{ \"query\": \"select username from event where lower(username)='"+username.toLowerCase()+"' and verb ='commented' and context='chikul13'"+Dates.getQueryBetweenDates()+"\", \"pag\": \""+pagination+"\"}");
				String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select username from event where lower(username)='"+username.toLowerCase()+"' and verb ='commented' and context='chikul13'"+Dates.getQueryBetweenDates()+"\", \"pag\": \""+pagination+"\"}");
				//System.out.println(queryresult);
				JSONArray query	 = new JSONArray(queryresult);
				if (max_comments<query.length()){
					System.out.println("No comments at all:"+username+"-"+max_comments);				
					max_comments = query.length();
				}
				if (query.length()==0){					
					usernamesZeroActivity.add(username);
				}
			}
			e = usernamesUrl.keys();
			while(e.hasMoreElements()){
				String username = (String) e.nextElement();
				String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select username from event where lower(username)='"+username.toLowerCase()+"' and verb ='commented' and context='chikul13'"+Dates.getQueryBetweenDates()+"\", \"pag\": \""+pagination+"\"}");
				//System.out.println(queryresult);
				JSONArray query	 = new JSONArray(queryresult);
				if (max_comments==query.length()){
					System.out.println("Most commenting:"+username);
					usernames.add(username);
				}
			}
			for (int z=0; z<usernames.size();z++){
				String urlBase = "http://openbadges-hci.appspot.com";
				Badge badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklyParticipativeBadge(twitterOpenBadges.get(usernames.get(z)), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					System.out.println(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames.get(z), twitterEmailNotification.get(usernames.get(z)));
					PersistanceLayer.sendBadgeAsEvent(usernames.get(z), badge.getDescription());
				}					
			}	
			for (int z=0; z<usernamesZeroActivity.size();z++){
				String urlBase = "http://openbadges-hci.appspot.com";
				Badge badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklyNoCommentOnAnyBlogBadge(twitterOpenBadges.get(usernamesZeroActivity.get(z)), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					System.out.println(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernamesZeroActivity.get(z), twitterEmailNotification.get(usernamesZeroActivity.get(z)));
					PersistanceLayer.sendBadgeAsEvent(usernamesZeroActivity.get(z), badge.getDescription());
				}					
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
