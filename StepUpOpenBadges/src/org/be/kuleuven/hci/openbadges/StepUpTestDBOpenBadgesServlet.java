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
import org.json.JSONObject;



@SuppressWarnings("serial")
public class StepUpTestDBOpenBadgesServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		//Testing if that Badge exists
		/*Badge badge = new Badge();
		badge.setDescription(Badges.firstBadge("gonzalo.parra.cs@gmail.com"));
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
		resp.getWriter().println(PersistanceLayer.existInDbBadge(badge));
		badge.setDescription(Badges.firstBadge("joseluis.santos.cs@gmail.com"));
		resp.getWriter().println(PersistanceLayer.existInDbBadge(badge));*/
		//Migrating DB
		JSONArray badges = PersistanceLayer.badgesrewarded();
		for (int i=0; i<badges.length(); i++){
			try {
				JSONObject json = badges.getJSONObject(i);
				Event e = new Event();
				resp.setContentType("text/plain");
				resp.getWriter().println(PersistanceLayer.sendBadgeAsEvent(json.getString("recipient"), json.toString()));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
