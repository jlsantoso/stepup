package org.be.kuleuven.hci.openbadges;

import java.io.IOException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Logger;

import javax.servlet.http.*;


import org.be.kuleuven.hci.openbadges.badges.Badge;
import org.be.kuleuven.hci.openbadges.badges.Badges;
import org.be.kuleuven.hci.openbadges.badges.ChiCourseBadgesRules;
import org.be.kuleuven.hci.openbadges.badges.GiveBadges;
import org.be.kuleuven.hci.openbadges.persistanlayer.PersistanceLayer;
import org.be.kuleuven.hci.openbadges.utils.Event;
import org.be.kuleuven.hci.openbadges.utils.JSONandEvent;
import org.be.kuleuven.hci.openbadges.utils.RestClient;

import org.json.JSONArray;
import org.json.JSONException;



@SuppressWarnings("serial")
public class TweetsServlet extends HttpServlet {
	
	private static final Logger log = Logger.getLogger(TweetsServlet.class.getName());
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		ChiCourseBadgesRules.tweets();
		ChiCourseBadgesRules.commentsOnOtherBlogs(9);
		ChiCourseBadgesRules.commentsOnOwnBlogs();
		ChiCourseBadgesRules.diigo();
	}
}
