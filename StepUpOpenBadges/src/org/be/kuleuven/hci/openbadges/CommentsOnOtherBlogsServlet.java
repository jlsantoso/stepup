package org.be.kuleuven.hci.openbadges;

import java.io.IOException;
import java.text.ParseException;
import java.util.Enumeration;
import java.util.Hashtable;

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
public class CommentsOnOtherBlogsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		ChiCourseBadgesRules.commentsOnOtherBlogs(9);
	}
}
