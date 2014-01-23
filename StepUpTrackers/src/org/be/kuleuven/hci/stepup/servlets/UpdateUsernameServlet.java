package org.be.kuleuven.hci.stepup.servlets;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.be.kuleuven.hci.stepup.model.ActivityStream;
import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.RssFeeds;
import org.be.kuleuven.hci.stepup.persistancelayer.EventGoogleDataStore;
import org.be.kuleuven.hci.stepup.persistancelayer.RestClient;
import org.be.kuleuven.hci.stepup.util.ReadGoogleSpreadSheet;
import org.be.kuleuven.hci.stepup.util.ReadMatchingIdentifierSpreadSheet;
import org.be.kuleuven.hci.stepup.util.StepUpConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class UpdateUsernameServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(UpdateUsernameServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		if (syncCache.get("updatingusernames")==null){
			ReadMatchingIdentifierSpreadSheet.read();
		}
		Hashtable<String,String> usernames = (Hashtable<String, String>) syncCache.get("updatingusernames");
		Enumeration e = usernames.keys();

		while( e.hasMoreElements()) {
			  String key = (String)e.nextElement();
			  JSONObject json = new JSONObject();
			  try {
				json.put("username", usernames.get(key));
				json.put("matchedusername", key);
				log.warning(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/updateusernames", json.toString()));
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			  
		}
		/*List<RssFeeds> rssFeeds = EventGoogleDataStore.getRssFeeds();
		for (RssFeeds r : rssFeeds){
			getRssFeeds(r.getURL(), lastUpdate);
		}*/
		
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
