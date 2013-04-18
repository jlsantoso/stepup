package org.be.kuleuven.hci.openbadges.badges.rules;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.be.kuleuven.hci.openbadges.OnceXWeekBadgesServlet;
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

public class ChiCourseBadgesLinguistics {

	private static final Logger log = Logger.getLogger(OnceXWeekBadgesServlet.class.getName());

	public static void mostChallengerStudent(){
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
			String query_add = "and (";
			//creating query for lingŸistics
			for (int i=0;i<StepUpConstants.challenge.length;i++){
				query_add += "originalrequest like '%"+StepUpConstants.challenge[i]+"%'";
				if (i!=StepUpConstants.challenge.length-1) query_add += " or ";
			}
			query_add += ")";
			String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select username, count(*) from event where verb ='commented' and context='chikul13'"+query_add+Dates.getQueryBetweenDates()+" group by username order by count desc\", \"pag\": \""+pagination+"\"}");
			System.out.println(queryresult);
			JSONArray query	 = new JSONArray(queryresult);
			String username="";
			String urlBase = "";
			Badge badge = new Badge();
			if (query.length()>0){
				username=query.getJSONObject(0).getString("username");
				urlBase = "http://openbadges-hci.appspot.com";
				badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklyGoldLinguisticChallenge(twitterOpenBadges.get(username), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+username, twitterEmailNotification.get(username));
					PersistanceLayer.sendBadgeAsEvent(username, badge.getDescription());
				}
			}
			if (query.length()>1){
				username=query.getJSONObject(1).getString("username");
				urlBase = "http://openbadges-hci.appspot.com";
				badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklySilverLinguisticChallenge(twitterOpenBadges.get(username), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+username, twitterEmailNotification.get(username));
					PersistanceLayer.sendBadgeAsEvent(username, badge.getDescription());
				}
			}
			if (query.length()>2){
				username=query.getJSONObject(2).getString("username");
				urlBase = "http://openbadges-hci.appspot.com";
				badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklyBronzeLinguisticChallenge(twitterOpenBadges.get(username), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+username, twitterEmailNotification.get(username));
					PersistanceLayer.sendBadgeAsEvent(username, badge.getDescription());
				}
			}			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());;
		}
	}
	
	public static void mostEvaluatorStudent(){
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
			String query_add = "and (";
			//creating query for lingŸistics
			for (int i=0;i<StepUpConstants.evaluation.length;i++){
				query_add += "originalrequest like '%"+StepUpConstants.evaluation[i]+"%'";
				if (i!=StepUpConstants.evaluation.length-1) query_add += " or ";
			}
			query_add += ")";
			String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select username, count(*) from event where verb ='commented' and context='chikul13'"+query_add+Dates.getQueryBetweenDates()+" group by username order by count desc\", \"pag\": \""+pagination+"\"}");
			System.out.println(queryresult);
			JSONArray query	 = new JSONArray(queryresult);
			String username="";
			String urlBase = "";
			Badge badge = new Badge();
			if (query.length()>0){
				username=query.getJSONObject(0).getString("username");
				urlBase = "http://openbadges-hci.appspot.com";
				badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklyGoldLinguisticEvaluation(twitterOpenBadges.get(username), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+username, twitterEmailNotification.get(username));
					PersistanceLayer.sendBadgeAsEvent(username, badge.getDescription());
				}
			}
			if (query.length()>1){
				username=query.getJSONObject(1).getString("username");
				urlBase = "http://openbadges-hci.appspot.com";
				badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklySilverLinguisticEvaluation(twitterOpenBadges.get(username), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+username, twitterEmailNotification.get(username));
					PersistanceLayer.sendBadgeAsEvent(username, badge.getDescription());
				}
			}
			if (query.length()>2){
				username=query.getJSONObject(2).getString("username");
				urlBase = "http://openbadges-hci.appspot.com";
				badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklyBronzeLinguisticEvaluation(twitterOpenBadges.get(username), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+username, twitterEmailNotification.get(username));
					PersistanceLayer.sendBadgeAsEvent(username, badge.getDescription());
				}
			}			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());;
		}
	}
	
	public static void mostExtenderStudent(){
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
			String query_add = "and (";
			//creating query for lingŸistics
			for (int i=0;i<StepUpConstants.extension.length;i++){
				query_add += "originalrequest like '%"+StepUpConstants.extension[i]+"%'";
				if (i!=StepUpConstants.extension.length-1) query_add += " or ";
			}
			query_add += ")";
			String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select username, count(*) from event where verb ='commented' and context='chikul13'"+query_add+Dates.getQueryBetweenDates()+" group by username order by count desc\", \"pag\": \""+pagination+"\"}");
			System.out.println(queryresult);
			JSONArray query	 = new JSONArray(queryresult);
			String username="";
			String urlBase = "";
			Badge badge = new Badge();
			if (query.length()>0){
				username=query.getJSONObject(0).getString("username");
				urlBase = "http://openbadges-hci.appspot.com";
				badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklyGoldLinguisticExtension(twitterOpenBadges.get(username), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+username, twitterEmailNotification.get(username));
					PersistanceLayer.sendBadgeAsEvent(username, badge.getDescription());
				}
			}
			if (query.length()>1){
				username=query.getJSONObject(1).getString("username");
				urlBase = "http://openbadges-hci.appspot.com";
				badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklySilverLinguisticExtension(twitterOpenBadges.get(username), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+username, twitterEmailNotification.get(username));
					PersistanceLayer.sendBadgeAsEvent(username, badge.getDescription());
				}
			}
			if (query.length()>2){
				username=query.getJSONObject(2).getString("username");
				urlBase = "http://openbadges-hci.appspot.com";
				badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklyBronzeLinguisticExtension(twitterOpenBadges.get(username), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+username, twitterEmailNotification.get(username));
					PersistanceLayer.sendBadgeAsEvent(username, badge.getDescription());
				}
			}			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());;
		}
	}
	
	public static void mostReasonerStudent(){
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
			String query_add = "and (";
			//creating query for lingŸistics
			for (int i=0;i<StepUpConstants.reasoning.length;i++){
				query_add += "originalrequest like '%"+StepUpConstants.reasoning[i]+"%'";
				if (i!=StepUpConstants.reasoning.length-1) query_add += " or ";
			}
			query_add += ")";
			String queryresult = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select username, count(*) from event where verb ='commented' and context='chikul13'"+query_add+Dates.getQueryBetweenDates()+" group by username order by count desc\", \"pag\": \""+pagination+"\"}");
			System.out.println(queryresult);
			JSONArray query	 = new JSONArray(queryresult);
			String username="";
			String urlBase = "";
			Badge badge = new Badge();
			if (query.length()>0){
				username=query.getJSONObject(0).getString("username");
				urlBase = "http://openbadges-hci.appspot.com";
				badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklyGoldLinguisticReasoning(twitterOpenBadges.get(username), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+username, twitterEmailNotification.get(username));
					PersistanceLayer.sendBadgeAsEvent(username, badge.getDescription());
				}
			}
			if (query.length()>1){
				username=query.getJSONObject(1).getString("username");
				urlBase = "http://openbadges-hci.appspot.com";
				badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklySilverLinguisticReasoning(twitterOpenBadges.get(username), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+username, twitterEmailNotification.get(username));
					PersistanceLayer.sendBadgeAsEvent(username, badge.getDescription());
				}
			}
			if (query.length()>2){
				username=query.getJSONObject(2).getString("username");
				urlBase = "http://openbadges-hci.appspot.com";
				badge = new Badge();
				badge.setDescription(ChiCourse.createBiWeeklyBronzeLinguisticReasoning(twitterOpenBadges.get(username), ""+current_week, Dates.getStartDate(), Dates.getEndDate()));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+username, twitterEmailNotification.get(usernames));
					PersistanceLayer.sendBadgeAsEvent(username, badge.getDescription());
				}
			}			
			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());;
		}
	}
	
	

}
