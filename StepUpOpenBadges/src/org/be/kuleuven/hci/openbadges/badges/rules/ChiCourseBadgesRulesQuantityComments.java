package org.be.kuleuven.hci.openbadges.badges.rules;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
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

public class ChiCourseBadgesRulesQuantityComments {
	
	private static final Logger log = Logger.getLogger(ChiCourseBadgesRulesQuantityComments.class.getName());

	
	public static void getPopularGroup(){
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
			Enumeration e = membersGroup.keys();
			int max_comments = 0;
			while(e.hasMoreElements()){
				String[] usernames = membersGroup.get(e.nextElement()).split(";");
				String queryusernames = "";
				for (int i=0;i<usernames.length;i++) queryusernames += " and username!='"+usernames[i]+"'";
				//select distinct substring(object,8, position('/' in substring(object,8))-1) from event where verb ='comment';
				String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select substring(object,8, position('/' in substring(object,8))-1) as url, count(*) from event where object like '%"+usernamesUrl.get(usernames[0])+"%' and verb ='commented' "+queryusernames+" and context='chikul13'"+Dates.getQueryBetweenDates()+" group by url\", \"pag\": \""+pagination+"\"}");
				System.out.println(queryresult);
				JSONArray query	 = new JSONArray(queryresult);
				if (max_comments<Integer.parseInt(query.getJSONObject(0).getString("count"))) max_comments = Integer.parseInt(query.getJSONObject(0).getString("count"));
			}
			e = membersGroup.keys();
			while(e.hasMoreElements()){
				String[] usernames = membersGroup.get(e.nextElement()).split(";");
				String queryusernames = "";
				for (int i=0;i<usernames.length;i++) queryusernames += " and username!='"+usernames[i]+"'";
				//select distinct substring(object,8, position('/' in substring(object,8))-1) from event where verb ='comment';
				String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select substring(object,8, position('/' in substring(object,8))-1) as url, count(*) from event where object like '%"+usernamesUrl.get(usernames[0])+"%' and verb ='commented' "+queryusernames+" and context='chikul13'"+Dates.getQueryBetweenDates()+" group by url\", \"pag\": \""+pagination+"\"}");
				JSONArray query	 = new JSONArray(queryresult);
				if (max_comments==Integer.parseInt(query.getJSONObject(0).getString("count"))){
					urls.add(query.getJSONObject(0).getString("url"));
				}
			}
			for (int z=0; z<urls.size();z++){
				String[] usernames = membersURL.get(urls.get(z)).split(";");
				String urlBase = "http://openbadges-hci.appspot.com";
				for (int i=0;i<usernames.length;i++){
					Badge badge = new Badge();
					badge.setDescription(ChiCourse.createBiWeeklyGroupieBadge(twitterOpenBadges.get(usernames[i]), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
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
	
	public static void getNoCommentsGroup(){
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
			Enumeration e = membersGroup.keys();
			int max_comments = 0;
			while(e.hasMoreElements()){
				String[] usernames = membersGroup.get(e.nextElement()).split(";");
				String queryusernames = "";
				for (int i=0;i<usernames.length;i++) queryusernames += " and username!='"+usernames[i]+"'";
				//select distinct substring(object,8, position('/' in substring(object,8))-1) from event where verb ='comment';
				String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select substring(object,8, position('/' in substring(object,8))-1) as url, count(*) from event where object like '%"+usernamesUrl.get(usernames[0])+"%' and verb ='commented' "+queryusernames+" and context='chikul13'"+Dates.getQueryBetweenDates()+" group by url\", \"pag\": \""+pagination+"\"}");
				JSONArray query	 = new JSONArray(queryresult);
				if (max_comments==Integer.parseInt(query.getJSONObject(0).getString("count"))){
					urls.add(query.getJSONObject(0).getString("url"));
				}
			}
			for (int z=0; z<urls.size();z++){
				String[] usernames = membersURL.get(urls.get(z)).split(";");
				String urlBase = "http://openbadges-hci.appspot.com";
				for (int i=0;i<usernames.length;i++){
					Badge badge = new Badge();
					badge.setDescription(ChiCourse.createBiWeeklyNoCommentOnYourBlogBadge(twitterOpenBadges.get(usernames[i]), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
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
