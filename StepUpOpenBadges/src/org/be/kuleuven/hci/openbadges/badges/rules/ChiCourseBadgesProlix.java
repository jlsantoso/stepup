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

public class ChiCourseBadgesProlix {

	public static void prolixPost(){
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
			ArrayList<String> urls = new ArrayList();
			int length = 0;
			String url = "";
			String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select substring(object,8, position('/' in substring(object,8))-1) as urlshort, originalrequest from event where verb ='posted' and context='chikul13'"+Dates.getQueryBetweenDates()+" \", \"pag\": \""+pagination+"\"}");
			//System.out.println("{ \"query\": \"select substring(object,8, position('/' in substring(object,8))-1) as urlshort, originalrequest from event where verb ='posted' and context='chikul13'"+Dates.getQueryBetweenDates()+" \", \"pag\": \""+pagination+"\"}");
			//System.out.println(queryresult);
			JSONArray query	 = new JSONArray(queryresult);
			for (int i=0; i<query.length();i++){
				String stripJSON = query.getJSONObject(i).getString("originalrequest").replaceAll(":\\\"", ":\"").replaceAll("\n", "").replaceAll("\\\"}", "\"}").replaceAll("\\\",", "\",");
				if (length<stripJSON.length()){
					length = stripJSON.length();
					url = query.getJSONObject(i).getString("urlshort");
				}
			}				
			String[] usernames = membersURL.get(url).split(";");
			String urlBase = "http://openbadges-hci.appspot.com";
			for (int i=0;i<usernames.length;i++){
				Badge badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklyProlixPostBadge(twitterOpenBadges.get(usernames[i]), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
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
	
	public static void prolixComment(){
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
			ArrayList<String> urls = new ArrayList();
			int length = 0;
			String username = "";
			String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select username,substring(object,8, position('/' in substring(object,8))-1) as urlshort, originalrequest from event where verb ='commented' and context='chikul13'"+Dates.getQueryBetweenDates()+" \", \"pag\": \""+pagination+"\"}");
			//System.out.println("{ \"query\": \"select substring(object,8, position('/' in substring(object,8))-1) as urlshort, originalrequest from event where verb ='posted' and context='chikul13'"+Dates.getQueryBetweenDates()+" \", \"pag\": \""+pagination+"\"}");
			//System.out.println(queryresult);
			JSONArray query	 = new JSONArray(queryresult);
			for (int i=0; i<query.length();i++){
				String stripJSON = query.getJSONObject(i).getString("originalrequest").replaceAll(":\\\"", ":\"").replaceAll("\n", "").replaceAll("\\\"}", "\"}").replaceAll("\\\",", "\",");
				if (length<stripJSON.length()){
					length = stripJSON.length();
					username = query.getJSONObject(i).getString("username");
				}
			}				

			String urlBase = "http://openbadges-hci.appspot.com";
			Badge badge = new Badge();
			badge.setDescription(ChiCourse.createBiWeeklyProlixCommentBadge(twitterOpenBadges.get(username), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
			if (!PersistanceLayer.existBadge(badge)){
				System.out.println(badge.getDescription());
				PersistanceLayer.saveBadge(badge);
				Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+username, twitterEmailNotification.get(username));
				PersistanceLayer.sendBadgeAsEvent(username, badge.getDescription());
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
