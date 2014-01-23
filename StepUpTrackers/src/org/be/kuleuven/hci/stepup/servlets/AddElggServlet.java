package org.be.kuleuven.hci.stepup.servlets;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import org.be.kuleuven.hci.stepup.util.StepUpConstants;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.json.JSONArray;
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

public class AddElggServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(AddElggServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		/*MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		log.log(Level.INFO, "Cron job");
		Date lastUpdate = EventGoogleDataStore.getLastUpdateRss();
		if (syncCache.get("urlsBlogs")==null){
			ReadGoogleSpreadSheet.read();
		}
		ArrayList<String> urlsBlogs = (ArrayList<String>)syncCache.get("urlsBlogs");
		System.out.println(lastUpdate.toString());
		for ( String url : urlsBlogs ) {
			  System.out.println("https://public-api.wordpress.com/rest/v1/sites/"+url+"/comments/");
			  getRssFeedsPosts("https://public-api.wordpress.com/rest/v1/sites/"+url+"/posts/", lastUpdate);
			  getRssFeedsComments("https://public-api.wordpress.com/rest/v1/sites/"+url+"/comments/", lastUpdate);
		}*/
		long date = 44480;
		String urlString = "http://wespot.kmi.open.ac.uk/services/api/rest/json/?method=user.activity&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&minutes=2";
		Date lastUpdate = new Date((long)date*1000);
		getUserDataFromElgg(urlString, lastUpdate);
		
	}

	private void getUserDataFromElgg(String urlString, Date lastUpdate){
		

			String elggUsersInfo;
			try {
				elggUsersInfo = RestClient.doGet(urlString);
				log.warning("Events back:"+elggUsersInfo);
				System.out.println(elggUsersInfo);
				JSONArray results = new JSONObject(elggUsersInfo).getJSONArray("result");

				for (int i=0; i<results.length(); i++){
					JSONObject post = results.getJSONObject(i);
					String username = post.getString("subject_guid").toLowerCase();
					Event event = new Event();
					event.setUsername(username);
					event.setStartTime(new Date(post.getLong("posted")*1000));
					event.setObject(post.getString("object_guid"));
					event.setVerb(post.getString("action_type"));
					event.setContext("{\"course\":\"elgg\",\"phase\":\"Hypothesis\",\"subphase\":\"Reflection\"}");
					event.setOriginalRequest(post);
					EventGoogleDataStore.insertEvent(event);
				}
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	}
		
	
	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
