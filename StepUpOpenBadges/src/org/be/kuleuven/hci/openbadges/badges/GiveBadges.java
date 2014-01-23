package org.be.kuleuven.hci.openbadges.badges;

import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.be.kuleuven.hci.openbadges.mailnotification.Mail;
import org.be.kuleuven.hci.openbadges.persistanlayer.PersistanceLayer;
import org.be.kuleuven.hci.openbadges.persistanlayer.RestClient;
import org.be.kuleuven.hci.openbadges.utils.Event;
import org.be.kuleuven.hci.openbadges.utils.StepUpConstants;
import org.json.JSONException;
import org.json.JSONObject;


public class GiveBadges {

	private static final Logger log = Logger.getLogger(GiveBadges.class.getName());
	
	public static void giveFirstBadge(String username){
		String urlBase = "http://openbadges-hci.appspot.com";
		Mail.sendmail("Get your badge", "<a href=\"http://openbadges-hci.appspot.com/index.jsp?username="+username+"&badgeID=first\">Get Your badge!<a/>", username);
		PersistanceLayer.sendBadgeAsEvent(username, Badges.firstBadge(username));
	}
	
	public static void giveSecondBadge(String username){
		String urlBase = "http://openbadges-hci.appspot.com";
		Mail.sendmail("Get your badge", "<a href=\"http://openbadges-hci.appspot.com/index.jsp?username="+username+"&badgeID=second\">Get Your badge!<a/>", username);
		PersistanceLayer.sendBadgeAsEvent(username, Badges.secondBadge(username));
	}
	
	public static void giveThirdBadge(String username){
		String urlBase = "http://openbadges-hci.appspot.com";
		Mail.sendmail("Get your badge", "<a href=\"http://openbadges-hci.appspot.com/index.jsp?username="+username+"&badgeID=third\">Get Your badge!<a/>", username);
		PersistanceLayer.sendBadgeAsEvent(username, Badges.thirdBadge(username));
	}
	
	public static void giveFourthBadge(String username){
		String urlBase = "http://openbadges-hci.appspot.com";
		Mail.sendmail("Get your badge", "<a href=\"http://openbadges-hci.appspot.com/index.jsp?username="+username+"&badgeID=fourth\">Get Your badge!<a/>", username);
		PersistanceLayer.sendBadgeAsEvent(username, Badges.fourthBadge(username));
	}
	
	public static void giveFifthBadge(String username){
		String urlBase = "http://openbadges-hci.appspot.com";
		Mail.sendmail("Get your badge", "<a href=\"http://openbadges-hci.appspot.com/index.jsp?username="+username+"&badgeID=fifth\">Get Your badge!<a/>", username);
		PersistanceLayer.sendBadgeAsEvent(username, Badges.fifthBadge(username));
	}
	
}
