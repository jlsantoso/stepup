package org.be.kuleuven.hci.openbadges.badges.rules;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.be.kuleuven.hci.openbadges.PeriodicBadgesServlet;
import org.be.kuleuven.hci.openbadges.badges.Badge;
import org.be.kuleuven.hci.openbadges.badges.ChiCourse;
import org.be.kuleuven.hci.openbadges.badges.rules.utils.Dates;
import org.be.kuleuven.hci.openbadges.mailnotification.Mail;
import org.be.kuleuven.hci.openbadges.persistanlayer.PersistanceLayer;
import org.be.kuleuven.hci.openbadges.utils.ReadGoogleSpreadSheet;
import org.be.kuleuven.hci.openbadges.utils.RestClient;
import org.be.kuleuven.hci.openbadges.utils.StepUpConstants;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class ChiCourseBadgesRulesFaster {
	
	private static final Logger log = Logger.getLogger(ChiCourseBadgesRulesFaster.class.getName());

	
	public static void fasterInPostAfterLesson(){
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
			String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select substring(object,8, position('/' in substring(object,8))-1) as url,starttime from event where context='chikul13' and verb='posted' "+Dates.getQueryBetweenDates()+" order by starttime asc\", \"pag\": \""+pagination+"\"}");
			System.out.println( "{ \"query\": \"select substring(object,8, position('/' in substring(object,8))-1) as url,starttime from event where context='chikul13' and verb='posted' "+Dates.getQueryBetweenDates()+" order by starttime asc limit 2 offset 0 \", \"pag\": \""+pagination+"\"}");
			System.out.println(queryresult);
			JSONArray query	 = new JSONArray(queryresult);
			JSONObject json = query.getJSONObject(0);
			String url = json.getString("url");
			String[] usernames = membersURL.get(url).split(";");
			String urlBase = "http://openbadges-hci.appspot.com";
			for (int i=0;i<usernames.length;i++){
				Badge badge = new Badge();
				badge.setDescription(ChiCourse.createEarlierOnPostingAfterLesson(twitterOpenBadges.get(usernames[i]), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					System.out.println(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames[i], twitterEmailNotification.get(usernames[i]));
					PersistanceLayer.sendBadgeAsEvent(usernames[i], badge.getDescription());
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
	
	public static void fasterInGetACommentAfterLesson(){
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
			String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select substring(object,8, position('/' in substring(object,8))-1) as url,starttime, username from event where context='chikul13' and verb='commented' "+Dates.getQueryBetweenDates()+Dates.getFilterURLComments()+" order by starttime asc\", \"pag\": \""+pagination+"\"}");
			System.out.println("{ \"query\": \"select substring(object,8, position('/' in substring(object,8))-1) as url,starttime, username from event where context='chikul13' and verb='commented' "+Dates.getQueryBetweenDates()+Dates.getFilterURLComments()+" order by starttime asc\", \"pag\": \""+pagination+"\"}");
			JSONArray query	 = new JSONArray(queryresult);
			JSONObject json = null;
			if (query.length()>0){
				int j=0;
				while (!membersURL.get(query.getJSONObject(j).getString("url")).contains(query.getJSONObject(j).getString("username"))){
					j++;
					if (j>=query.length()) break;
				}
				json = query.getJSONObject(j);
				String url = json.getString("url");
				String[] usernames = membersURL.get(url).split(";");
				String urlBase = "http://openbadges-hci.appspot.com";
				for (int i=0;i<usernames.length;i++){
					Badge badge = new Badge();
					badge.setDescription(ChiCourse.createEarlierOnGetAComment(twitterOpenBadges.get(usernames[i]), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
					if (!PersistanceLayer.existBadge(badge)){
						System.out.println(badge.getDescription());
						PersistanceLayer.saveBadge(badge);
						Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames[i], twitterEmailNotification.get(usernames[i]));
						PersistanceLayer.sendBadgeAsEvent(usernames[i], badge.getDescription());
					}
					
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
