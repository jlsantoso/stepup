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

public class ChiCourseBadgesRulesLinks {

	public static void numberLinks(){
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
			while(e.hasMoreElements()){
				group++;
				String[] usernames = membersGroup.get(e.nextElement()).split(";");
				String queryusernames = " and (";
				for (int i=0;i<usernames.length-1;i++) queryusernames += " username='"+usernames[i]+"' or";
				queryusernames += " username='"+usernames[usernames.length-1]+"')	";
				//select distinct substring(object,8, position('/' in substring(object,8))-1) from event where verb ='comment';
				String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select originalRequest from event where verb ='posted' "+queryusernames+" and context='chikul13'"+Dates.getQueryBetweenDates()+"\", \"pag\": \""+pagination+"\"}");
				System.out.println("{ \"query\": \"select originalRequest from event where verb ='posted' "+queryusernames+" and context='chikul13'"+Dates.getQueryBetweenDates()+"\", \"pag\": \""+pagination+"\"}");
				Pattern link = Pattern.compile("<a\\s");
		        Matcher  matcher = link.matcher(queryresult);
		        int count = 0;
		        
		        while (matcher.find())
		            count++;
		        System.out.println(group+"-Counting:"+count);
				if (count>=2){
					String urlBase = "http://openbadges-hci.appspot.com";
					System.out.println(group+"-Bronze Medal");
					for (int i=0;i<usernames.length;i++){
						Badge badge = new Badge();
						badge.setDescription(ChiCourse.createBiWeekly2LinksOnPostBadge(twitterOpenBadges.get(usernames[i]), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
						if (!PersistanceLayer.existBadge(badge)){
							//System.out.println(badge.getDescription());
							PersistanceLayer.saveBadge(badge);
							Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames[i], twitterEmailNotification.get(usernames[i]));
							PersistanceLayer.sendBadgeAsEvent(usernames[i], badge.getDescription());
						}
						
					}
				}
				if (count>=5){
					String urlBase = "http://openbadges-hci.appspot.com";
					System.out.println(group+"-Silver Medal");
					for (int i=0;i<usernames.length;i++){
						Badge badge = new Badge();
						badge.setDescription(ChiCourse.createBiWeekly5LinksOnPostBadge(twitterOpenBadges.get(usernames[i]), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
						if (!PersistanceLayer.existBadge(badge)){
							//System.out.println(badge.getDescription());
							PersistanceLayer.saveBadge(badge);
							Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames[i], twitterEmailNotification.get(usernames[i]));
							PersistanceLayer.sendBadgeAsEvent(usernames[i], badge.getDescription());	
						}
					}
				}
				if (count>=8){
					String urlBase = "http://openbadges-hci.appspot.com";
					System.out.println(group+"-Gold Medal");
					for (int i=0;i<usernames.length;i++){
						Badge badge = new Badge();
						badge.setDescription(ChiCourse.createBiWeekly8LinksOnPostBadge(twitterOpenBadges.get(usernames[i]), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
						if (!PersistanceLayer.existBadge(badge)){
							//System.out.println(badge.getDescription());
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
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
