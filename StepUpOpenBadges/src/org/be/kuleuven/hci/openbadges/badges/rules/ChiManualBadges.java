package org.be.kuleuven.hci.openbadges.badges.rules;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.be.kuleuven.hci.openbadges.OnceXWeekBadgesServlet;
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

public class ChiManualBadges {
	private static final Logger log = Logger.getLogger(ChiManualBadges.class.getName());

	public static void iterationBadge(String group, String iteration){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		try {
			if (syncCache.get("membersGroup")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterOpenBadges")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterEmailNotification")==null) ReadGoogleSpreadSheet.read();
			Hashtable<String,String> membersGroup = (Hashtable<String, String>) syncCache.get("membersGroup");
			Hashtable<String,String> twitterOpenBadges = (Hashtable<String, String>) syncCache.get("twitterOpenBadges");
			Hashtable<String,String> twitterEmailNotification = (Hashtable<String, String>) syncCache.get("twitterEmailNotification");
			Enumeration e = membersGroup.keys();
			while (e.hasMoreElements()){
				System.out.println(e.nextElement()+"-"+group);
			}
			String[] usernames = membersGroup.get(group).split(";");
		
			String urlBase = "http://openbadges-hci.appspot.com";
			for (int i=0;i<usernames.length;i++){
				Badge badge = new Badge();
				badge.setDescription(ChiCourse.createIterations(twitterOpenBadges.get(usernames[i]), iteration));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames[i], twitterEmailNotification.get(usernames[i]));
					PersistanceLayer.sendBadgeAsEvent(usernames[i], badge.getDescription());
				}					
			}		
					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void involvedPeopleEvaluationBadge(String group, String iteration, int people){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		try {
			if (syncCache.get("membersGroup")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterOpenBadges")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterEmailNotification")==null) ReadGoogleSpreadSheet.read();
			Hashtable<String,String> membersGroup = (Hashtable<String, String>) syncCache.get("membersGroup");
			Hashtable<String,String> twitterOpenBadges = (Hashtable<String, String>) syncCache.get("twitterOpenBadges");
			Hashtable<String,String> twitterEmailNotification = (Hashtable<String, String>) syncCache.get("twitterEmailNotification");
			String[] usernames = membersGroup.get(group).split(";");
		
			String urlBase = "http://openbadges-hci.appspot.com";
			for (int i=0;i<usernames.length;i++){
				Badge badge = new Badge();
				if (people>=5&&people<10)
					badge.setDescription(ChiCourse.create5PeopleInvolvedInEvaluation(twitterOpenBadges.get(usernames[i]), iteration));
				if (people>=10&&people<15)
					badge.setDescription(ChiCourse.create10PeopleInvolvedInEvaluation(twitterOpenBadges.get(usernames[i]), iteration));
				if (people>=15)
					badge.setDescription(ChiCourse.create15PeopleInvolvedInEvaluation(twitterOpenBadges.get(usernames[i]), iteration));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames[i], twitterEmailNotification.get(usernames[i]));
					PersistanceLayer.sendBadgeAsEvent(usernames[i], badge.getDescription());
				}					
			}		
					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void improvedSUSBadge(String group, String iteration){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		try {
			if (syncCache.get("membersGroup")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterOpenBadges")==null) ReadGoogleSpreadSheet.read();
			if (syncCache.get("twitterEmailNotification")==null) ReadGoogleSpreadSheet.read();
			Hashtable<String,String> membersGroup = (Hashtable<String, String>) syncCache.get("membersGroup");
			Hashtable<String,String> twitterOpenBadges = (Hashtable<String, String>) syncCache.get("twitterOpenBadges");
			Hashtable<String,String> twitterEmailNotification = (Hashtable<String, String>) syncCache.get("twitterEmailNotification");
			String[] usernames = membersGroup.get(group).split(";");
		
			String urlBase = "http://openbadges-hci.appspot.com";
			for (int i=0;i<usernames.length;i++){
				Badge badge = new Badge();
				badge.setDescription(ChiCourse.createSUSimprovementBadge(twitterOpenBadges.get(usernames[i]), iteration));
				if (!PersistanceLayer.existBadge(badge)){
					log.warning(badge.getDescription());
					PersistanceLayer.saveBadge(badge);
					Mail.sendmail("You have just earned a new Badge! ", "You have earned a new badge:" + (new JSONObject(badge.getDescription())).getJSONObject("badge").getString("description")+ "Check it out at http://navi-hci.appspot.com/badgeboard?username="+usernames[i], twitterEmailNotification.get(usernames[i]));
					PersistanceLayer.sendBadgeAsEvent(usernames[i], badge.getDescription());
				}					
			}		
					
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
