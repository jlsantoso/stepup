package org.be.kuleuven.hci.openbadges;

import java.io.IOException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.*;


import org.be.kuleuven.hci.openbadges.badges.Badge;
import org.be.kuleuven.hci.openbadges.badges.Badges;
import org.be.kuleuven.hci.openbadges.badges.GiveBadges;
import org.be.kuleuven.hci.openbadges.persistanlayer.PersistanceLayer;
import org.be.kuleuven.hci.openbadges.utils.Event;
import org.be.kuleuven.hci.openbadges.utils.JSONandEvent;
import org.be.kuleuven.hci.openbadges.utils.RestClient;

import org.json.JSONArray;
import org.json.JSONException;



@SuppressWarnings("serial")
public class StepUpOpenBadgesServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		int pagination = 0;
		int limit_pagination = 0;
		Hashtable<String,String> matchUsers = new Hashtable<String,String>();
		matchUsers.put("jlsantoso", "joseluis.santos.cs@gmail.com");
		matchUsers.put("gaposx", "gonzalo.parra.cs@gmail.com");
		matchUsers.put("svencharleer", "sven.charleer@gmail.com");
		matchUsers.put("jkofmsk", "joris.klerkx@gmail.com");
		matchUsers.put("erikduval", "erik.duval@cs.kuleuven.be");
		matchUsers.put("samagten", "sam.agten@cs.kuleuven.be");
		Hashtable<String,Integer> users = new Hashtable<String,Integer>();
		String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context='openBadges'\", \"pag\": \""+pagination+"\"}");
		try {
			JSONArray results = new JSONArray(result);
			System.out.println("Result:"+result);
			for (int i=0; i<results.length();i++){
				Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
				if (users.containsKey(matchUsers.get(event.getUsername().toLowerCase()))){
					users.put(matchUsers.get(event.getUsername().toLowerCase()), users.get(matchUsers.get(event.getUsername().toLowerCase()))+1);
				}else{
					users.put(matchUsers.get(event.getUsername().toLowerCase()), 1);
				}

			}
			while (results.length()>0){
				pagination++;
				result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where verb='tweet' and context='openBadges'\", \"pag\": \""+pagination+"\"}");
				System.out.println("Result:"+result);
				results = new JSONArray(result);
				for (int i=0; i<results.length();i++){
					Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
					if (users.containsKey(matchUsers.get(event.getUsername().toLowerCase()))){
						users.put(matchUsers.get(event.getUsername().toLowerCase()), users.get(matchUsers.get(event.getUsername().toLowerCase()))+1);
					}else{
						users.put(matchUsers.get(event.getUsername().toLowerCase()), 1);
					}
				}
			}
			giveBadges(users);
			
			//course.printBlogPosition();
			//PersistanceLayer.saveCourse(course, "bigtablethesis12");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	void giveBadges(Hashtable<String,Integer> users){		
		Enumeration e = users.keys();
		while(e.hasMoreElements()){
			System.out.println("HE entrado");
			String username = (String) e.nextElement();
			Integer activity = users.get(username);
			if (activity>=1){				
				Badge badge = new Badge();
				badge.setDescription(Badges.firstBadge(username));
				if (!PersistanceLayer.existBadge(badge)){
					PersistanceLayer.saveBadge(badge);
					GiveBadges.giveFirstBadge(username);
					
				}
			}
			if (activity>=5){
				Badge badge = new Badge();
				badge.setDescription(Badges.secondBadge(username));
				if (!PersistanceLayer.existBadge(badge)){
					PersistanceLayer.saveBadge(badge);
					GiveBadges.giveSecondBadge(username);
				}
			}
			if (activity>=10){
				Badge badge = new Badge();
				badge.setDescription(Badges.thirdBadge(username));
				if (!PersistanceLayer.existBadge(badge)){
					PersistanceLayer.saveBadge(badge);
					GiveBadges.giveThirdBadge(username);
				}
			}
			if (activity>=15){
				Badge badge = new Badge();
				badge.setDescription(Badges.fourthBadge(username));
				if (!PersistanceLayer.existBadge(badge)){
					PersistanceLayer.saveBadge(badge);
					GiveBadges.giveFourthBadge(username);
				}
			}
			if (activity>=20){
				Badge badge = new Badge();
				badge.setDescription(Badges.fifthBadge(username));
				if (!PersistanceLayer.existBadge(badge)){
					PersistanceLayer.saveBadge(badge);
					GiveBadges.giveFifthBadge(username);
				}
			}
		}
		      
		
	}
}
