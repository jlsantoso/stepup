package org.be.kuleuven.hci.openbadges.badges.rules;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.be.kuleuven.hci.openbadges.TweetsServlet;
import org.be.kuleuven.hci.openbadges.badges.Badge;
import org.be.kuleuven.hci.openbadges.badges.ChiCourse;
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

public class ChiCourseBadgesRules {
	
	private static final Logger log = Logger.getLogger(ChiCourseBadgesRules.class.getName());
	
	static Calendar startCourse(){
		Calendar startCourse = Calendar.getInstance();
		startCourse.set(2013, 1, 19);
		return startCourse;
	}
	
	static Calendar endCourse(){
		Calendar endCourse = Calendar.getInstance();
		endCourse.set(2013, 4, 28);
		return endCourse;
	}
	
	public static int getCurrentBiWeekOfTheCourse(){
		Calendar today = Calendar.getInstance();
		int biweek = 0;
		today.add(Calendar.DAY_OF_MONTH, -14);
		while (today.after(startCourse())){
			biweek++;
			today.add(Calendar.DAY_OF_MONTH, -14);			
		}		
		return biweek;
	}
	
	public static String getQueryBetweenDates(){
		int week = getCurrentBiWeekOfTheCourse();
		Calendar start = startCourse();
		start.add(Calendar.DAY_OF_MONTH, week*14);
		String query = " and starttime>'"+start.get(Calendar.YEAR)+"-"+(start.get(Calendar.MONTH)+1)+"-"+start.get(Calendar.DAY_OF_MONTH)+"' ";
		start.add(Calendar.DAY_OF_MONTH, 14);
		query = query+" and starttime<'"+start.get(Calendar.YEAR)+"-"+(start.get(Calendar.MONTH)+1)+"-"+start.get(Calendar.DAY_OF_MONTH)+"' ";
		return query;
	}
	
	public static String getStartDate(){
		int week = getCurrentBiWeekOfTheCourse();
		Calendar start = startCourse();
		start.add(Calendar.DAY_OF_MONTH, week*14);
		String query = start.get(Calendar.YEAR)+"-"+(start.get(Calendar.MONTH)+1)+"-"+start.get(Calendar.DAY_OF_MONTH);
		return query;
	}
	
	public static String getEndDate(){
		int week = getCurrentBiWeekOfTheCourse();
		Calendar start = startCourse();
		start.add(Calendar.DAY_OF_MONTH, week*14);
		start.add(Calendar.DAY_OF_MONTH, 14);
		String query = start.get(Calendar.YEAR)+"-"+(start.get(Calendar.MONTH)+1)+"-"+start.get(Calendar.DAY_OF_MONTH);
		return query;
	}
	
	public static void commentsOnOtherBlogs(int totalBlogs){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		int current_week=getCurrentBiWeekOfTheCourse();
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
			System.out.println("Size:"+membersGroup.size());
			Enumeration e = membersGroup.keys();
			while(e.hasMoreElements()){
				String[] usernames = membersGroup.get(e.nextElement()).split(";");
				String queryusernames = " and (";
				for (int i=0;i<usernames.length-1;i++) queryusernames += " username='"+usernames[i]+"' or";
				queryusernames += " username='"+usernames[usernames.length-1]+"')	";
				//System.out.println(queryusernames);
				//select distinct substring(object,8, position('/' in substring(object,8))-1) from event where verb ='comment';
				//System.out.println("{ \"query\": \"select distinct substring(object,8, position('/' in substring(object,8))-1) as url from event where url not like '%"+usernamesUrl.get(usernames[0])+"%' and verb ='"+StepUpConstants.BLOGCOMMENT+"' "+queryusernames+" and context='chikul13'"+getQueryBetweenDates()+"\", \"pag\": \""+pagination+"\"}");
				String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select distinct substring(object,8, position('/' in substring(object,8))-1) as url from event where object not like '%"+usernamesUrl.get(usernames[0])+"%' and verb ='"+StepUpConstants.BLOGCOMMENT+"' "+queryusernames+" and context='chikul13'"+getQueryBetweenDates()+"\", \"pag\": \""+pagination+"\"}");
				
				JSONArray query	 = new JSONArray(queryresult);
				System.out.println(query.length()*100/totalBlogs+"-"+queryresult);
				if (query.length()*100/totalBlogs>=33){
					String urlBase = "http://openbadges-hci.appspot.com";
					System.out.println(usernames.length+":SIZE");
					for (int i=0;i<usernames.length;i++){
						Badge badge = new Badge();
						badge.setDescription(ChiCourse.createBiWeeklyBronzeCommentsOnOthersBlogs(twitterOpenBadges.get(usernames[i]), ""+current_week, getStartDate(), getEndDate()));
						if (!PersistanceLayer.existBadge(badge)){
							System.out.println(badge.getDescription());
							PersistanceLayer.saveBadge(badge);
							Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames[i], twitterEmailNotification.get(usernames[i]));
							PersistanceLayer.sendBadgeAsEvent(usernames[i], badge.getDescription());
						}
						
					}
				}
				if (query.length()*100/totalBlogs>=66){
					String urlBase = "http://openbadges-hci.appspot.com";
					for (int i=0;i<usernames.length;i++){
						Badge badge = new Badge();
						badge.setDescription(ChiCourse.createBiWeeklySilverCommentsOnOthersBlogs(twitterOpenBadges.get(usernames[i]), ""+current_week, getStartDate(), getEndDate()));
						if (!PersistanceLayer.existBadge(badge)){
							System.out.println(badge.getDescription());
							PersistanceLayer.saveBadge(badge);
							Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames[i], twitterEmailNotification.get(usernames[i]));
							PersistanceLayer.sendBadgeAsEvent(usernames[i], badge.getDescription());		
						}
					}
				}
				if (query.length()==totalBlogs){
					String urlBase = "http://openbadges-hci.appspot.com";
					for (int i=0;i<usernames.length;i++){
						Badge badge = new Badge();
						badge.setDescription(ChiCourse.createBiWeeklyGoldCommentsOnOthersBlogs(twitterOpenBadges.get(usernames[i]), ""+current_week, getStartDate(), getEndDate()));
						if (!PersistanceLayer.existBadge(badge)){
							System.out.println(badge.getDescription());
							PersistanceLayer.saveBadge(badge);
							Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames[i], twitterEmailNotification.get(usernames[i]));
							PersistanceLayer.sendBadgeAsEvent(usernames[i], badge.getDescription());
						}
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
	
	public static void tweets(){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		int current_week=getCurrentBiWeekOfTheCourse();
		int pagination = 0;
		try {
			if (syncCache.get("membersGroup")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterEmailNotification")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterOpenBadges")==null) ReadGoogleSpreadSheet.read();
			Hashtable<String,String> twitterEmailNotification = (Hashtable<String, String>) syncCache.get("twitterEmailNotification");
			Hashtable<String,String> membersGroup = (Hashtable<String, String>) syncCache.get("membersGroup");
			Hashtable<String,String> twitterOpenBadges = (Hashtable<String, String>) syncCache.get("twitterOpenBadges");

			String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses/chikul13", "{ \"pag\": \"0\"}");
			log.severe(result);
			JSONArray students = new JSONArray(result);
			for(int j=0;j<students.length();j++){
				//select distinct substring(object,8, position('/' in substring(object,8))-1) from event where verb ='comment';
				String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select count(*) from event where verb ='"+StepUpConstants.TWITTER+"' and username='"+students.getJSONObject(j).getString("username")+"' and context='chikul13'"+getQueryBetweenDates()+"\", \"pag\": \"0\"}");
				log.severe(queryresult+"\n-"+"{ \"query\": \"select count(*) from event where verb ='tweet' and username='"+students.getJSONObject(j).getString("username")+"' and context='chikul13'"+getQueryBetweenDates());
				System.out.println(queryresult+"\n-"+"{ \"query\": \"select count(*) from event where verb ='tweet' and username='"+students.getJSONObject(j).getString("username")+"' and context='chi13'"+getQueryBetweenDates());
				JSONArray query	 = new JSONArray(queryresult);
				if (Integer.parseInt(query.getJSONObject(0).getString("count"))>=5){
					String urlBase = "http://openbadges-hci.appspot.com";
					Badge badge = new Badge();
					badge.setDescription(ChiCourse.createBiWeekly5Tweets(twitterOpenBadges.get(students.getJSONObject(j).getString("username")), ""+current_week, getStartDate(), getEndDate()));
					if (!PersistanceLayer.existBadge(badge)){
						System.out.println(students.getJSONObject(j).getString("username")+"-"+badge.getDescription());
						PersistanceLayer.saveBadge(badge);
						Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+students.getJSONObject(j).getString("username"), twitterEmailNotification.get(students.getJSONObject(j).getString("username")));
						PersistanceLayer.sendBadgeAsEvent(students.getJSONObject(j).getString("username"), badge.getDescription());
					}
				}
				if (Integer.parseInt(query.getJSONObject(0).getString("count"))>=10){
					String urlBase = "http://openbadges-hci.appspot.com";
					Badge badge = new Badge();
					badge.setDescription(ChiCourse.createBiWeekly10Tweets(twitterOpenBadges.get(students.getJSONObject(j).getString("username")), ""+current_week, getStartDate(), getEndDate()));
					if (!PersistanceLayer.existBadge(badge)){
						System.out.println(badge.getDescription());
						PersistanceLayer.saveBadge(badge);
						Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+students.getJSONObject(j).getString("username"), twitterEmailNotification.get(students.getJSONObject(j).getString("username")));
						PersistanceLayer.sendBadgeAsEvent(students.getJSONObject(j).getString("username"), badge.getDescription());
					}
				}
				if (Integer.parseInt(query.getJSONObject(0).getString("count"))>=15){
					String urlBase = "http://openbadges-hci.appspot.com";
					Badge badge = new Badge();
					badge.setDescription(ChiCourse.createBiWeekly15Tweets(twitterOpenBadges.get(students.getJSONObject(j).getString("username")), ""+current_week, getStartDate(), getEndDate()));
					if (!PersistanceLayer.existBadge(badge)){
						System.out.println(badge.getDescription());
						PersistanceLayer.saveBadge(badge);
						Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+students.getJSONObject(j).getString("username"), twitterEmailNotification.get(students.getJSONObject(j).getString("username")));
						PersistanceLayer.sendBadgeAsEvent(students.getJSONObject(j).getString("username"), badge.getDescription());
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
	
	public static void diigo(){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		int current_week=getCurrentBiWeekOfTheCourse();
		int pagination = 0;
		try {
			if (syncCache.get("membersGroup")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterEmailNotification")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterOpenBadges")==null) ReadGoogleSpreadSheet.read();
			Hashtable<String,String> twitterEmailNotification = (Hashtable<String, String>) syncCache.get("twitterEmailNotification");
			Hashtable<String,String> membersGroup = (Hashtable<String, String>) syncCache.get("membersGroup");
			Hashtable<String,String> twitterOpenBadges = (Hashtable<String, String>) syncCache.get("twitterOpenBadges");

			String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses/chi13", "{ \"pag\": \"0\"}");
			JSONArray students = new JSONArray(result);
			for(int j=0;j<students.length();j++){
				//select distinct substring(object,8, position('/' in substring(object,8))-1) from event where verb ='comment';
				String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select count(*) from event where verb ='"+StepUpConstants.DIIGOVERB+"' and username='"+students.getJSONObject(j).getString("username")+"' and context='chikul13'"+getQueryBetweenDates()+"\", \"pag\": \"0\"}");
				JSONArray query	 = new JSONArray(queryresult);
				if (Integer.parseInt(query.getJSONObject(0).getString("count"))>=5){
					String urlBase = "http://openbadges-hci.appspot.com";
					Badge badge = new Badge();
					badge.setDescription(ChiCourse.createBiWeekly5Diigo(twitterOpenBadges.get(students.getJSONObject(j).getString("username")), ""+current_week, getStartDate(), getEndDate()));
					if (!PersistanceLayer.existBadge(badge)){
						PersistanceLayer.saveBadge(badge);
						Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+students.getJSONObject(j).getString("username"), twitterEmailNotification.get(students.getJSONObject(j).getString("username")));
						PersistanceLayer.sendBadgeAsEvent(students.getJSONObject(j).getString("username"), badge.getDescription());
					}
				}
				if (Integer.parseInt(query.getJSONObject(0).getString("count"))>=10){
					String urlBase = "http://openbadges-hci.appspot.com";
					Badge badge = new Badge();
					badge.setDescription(ChiCourse.createBiWeekly10Diigo(twitterOpenBadges.get(students.getJSONObject(j).getString("username")), ""+current_week, getStartDate(), getEndDate()));
					if (!PersistanceLayer.existBadge(badge)){
						PersistanceLayer.saveBadge(badge);
						Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+students.getJSONObject(j).getString("username"), twitterEmailNotification.get(students.getJSONObject(j).getString("username")));
						PersistanceLayer.sendBadgeAsEvent(students.getJSONObject(j).getString("username"), badge.getDescription());
					}
				}
				if (Integer.parseInt(query.getJSONObject(0).getString("count"))>=15){
					String urlBase = "http://openbadges-hci.appspot.com";
					Badge badge = new Badge();
					badge.setDescription(ChiCourse.createBiWeekly15Diigo(twitterOpenBadges.get(students.getJSONObject(j).getString("username")), ""+current_week, getStartDate(), getEndDate()));
					if (!PersistanceLayer.existBadge(badge)){
						PersistanceLayer.saveBadge(badge);
						Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+students.getJSONObject(j).getString("username"), twitterEmailNotification.get(students.getJSONObject(j).getString("username")));
						PersistanceLayer.sendBadgeAsEvent(students.getJSONObject(j).getString("username"), badge.getDescription());
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
	
	public static void commentsOnOwnBlogs(){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		int current_week=getCurrentBiWeekOfTheCourse();
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
			while(e.hasMoreElements()){
				String[] usernames = membersGroup.get(e.nextElement()).split(";");
				String queryusernames = "";
				for (int i=0;i<usernames.length;i++) queryusernames += " and username!='"+usernames[i]+"'";
				//select distinct substring(object,8, position('/' in substring(object,8))-1) from event where verb ='comment';
				String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select substring(object,8, position('/' in substring(object,8))-1) as url from event where url like '%"+usernamesUrl.get(usernames[0])+"%' and verb ='comment' "+queryusernames+" and context='chikul13'"+getQueryBetweenDates()+"\", \"pag\": \""+pagination+"\"}");
				JSONArray query	 = new JSONArray(queryresult);
				if (query.length()>=5){
					String urlBase = "http://openbadges-hci.appspot.com";
					for (int i=0;i<usernames.length;i++){
						Badge badge = new Badge();
						badge.setDescription(ChiCourse.createBiWeekly5CommentsOnYourBlog(twitterOpenBadges.get(usernames[i]), ""+current_week, getStartDate(), getEndDate()));
						if (!PersistanceLayer.existBadge(badge)){
							PersistanceLayer.saveBadge(badge);
							Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames[i], twitterEmailNotification.get(usernames[i]));
							PersistanceLayer.sendBadgeAsEvent(usernames[i], badge.getDescription());
						}
						
					}
				}
				if (query.length()>=10){
					String urlBase = "http://openbadges-hci.appspot.com";
					for (int i=0;i<usernames.length;i++){
						Badge badge = new Badge();
						badge.setDescription(ChiCourse.createBiWeekly10CommentsOnYourBlog(twitterOpenBadges.get(usernames[i]), ""+current_week, getStartDate(), getEndDate()));
						if (!PersistanceLayer.existBadge(badge)){
							PersistanceLayer.saveBadge(badge);
							Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames[i], twitterEmailNotification.get(usernames[i]));
							PersistanceLayer.sendBadgeAsEvent(usernames[i], badge.getDescription());		
						}
					}
				}
				if (query.length()>=15){
					String urlBase = "http://openbadges-hci.appspot.com";
					for (int i=0;i<usernames.length;i++){
						Badge badge = new Badge();
						badge.setDescription(ChiCourse.createBiWeekly15CommentsOnYourBlog(twitterOpenBadges.get(usernames[i]), ""+current_week, getStartDate(), getEndDate()));
						if (!PersistanceLayer.existBadge(badge)){
							PersistanceLayer.saveBadge(badge);
							Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames[i], twitterEmailNotification.get(usernames[i]));
							PersistanceLayer.sendBadgeAsEvent(usernames[i], badge.getDescription());
						}
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
