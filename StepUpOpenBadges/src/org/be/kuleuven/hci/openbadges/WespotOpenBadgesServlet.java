package org.be.kuleuven.hci.openbadges;

import java.io.IOException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.*;


import org.be.kuleuven.hci.openbadges.badges.Badge;
import org.be.kuleuven.hci.openbadges.badges.Badges;
import org.be.kuleuven.hci.openbadges.badges.GiveBadges;
import org.be.kuleuven.hci.openbadges.badges.WeSPOTCourse;
import org.be.kuleuven.hci.openbadges.mailnotification.Mail;
import org.be.kuleuven.hci.openbadges.persistanlayer.PersistanceLayer;
import org.be.kuleuven.hci.openbadges.utils.Event;
import org.be.kuleuven.hci.openbadges.utils.JSONandEvent;
import org.be.kuleuven.hci.openbadges.utils.RestClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



@SuppressWarnings("serial")
public class WespotOpenBadgesServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		int pagination = 0;
		int limit_pagination = 0;
		
		String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context like '%assessment%'\", \"pag\": \"0\"}");		
		try {
			JSONArray results = new JSONArray(result);
			for (int i=0;i<results.length();i++){
				JSONObject user = results.getJSONObject(i);
				System.out.println();
				String result_test = new JSONObject(user.getString("originalrequest")).getString("value");
				String phase = new JSONObject(user.getString("context")).getString("phase");
				if (Integer.parseInt(result_test)>=50){
					String openbadge = WeSPOTCourse.createPassedPhaseBadge(user.getString("username").toLowerCase(), new JSONObject(user.getString("context")).getString("course"), new JSONObject(user.getString("context")).getString("phase"), new JSONObject(user.getString("originalrequest")).getString("value"));
					Badge badge = new Badge();
					badge.setDescription(openbadge);					
					if (!PersistanceLayer.existBadge(badge)){
						System.out.println(badge.getDescription());
						PersistanceLayer.saveBadge(badge);
						Mail.sendmail("StepUp has awarded a badge", "The badge is:\n " + badge.getDescription(), "joseluis.santos.cs@gmail.com");
						PersistanceLayer.sendBadgeAsEvent(user.getString("username").toLowerCase(), badge.getDescription(),user.getString("context").replaceAll("assessment", "reinforcement"));
					}
				}
			}		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
