package org.be.kuleuven.hci.stepup.servlets;

import java.io.IOException;
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

public class AddWordPressServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(AddWordPressServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
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
		}
		
	}

	private void getRssFeedsPosts(String urlString, Date lastUpdate){
		
		//System.out.println(lastUpdate.toString());
		
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    if (syncCache.get("matchingusernames")==null){
			ReadGoogleSpreadSheet.read();
		}
	    Hashtable<String,String> matchingusernames = (Hashtable<String,String>)syncCache.get("matchingusernames");
		
		try {
			String wordpressPosts = RestClient.doGet(urlString);
			JSONArray posts = new JSONObject(wordpressPosts).getJSONArray("posts");
			DateTimeFormatter format = ISODateTimeFormat.dateTimeNoMillis();

			java.util.Date created = null;
			Calendar cal = Calendar.getInstance();
			System.out.println("Length"+posts.length());
			for (int i=0; i<posts.length(); i++){
				JSONObject post = posts.getJSONObject(i);
				
				if (format.parseDateTime(post.getString("date")).toDate().compareTo(lastUpdate)>0){
					String username = post.getJSONObject("author").getString("name").toLowerCase();
					if (matchingusernames.containsKey(username)){
						username = matchingusernames.get(username);
					}
					Event event = new Event();
					event.setUsername(username);
					event.setStartTime(format.parseDateTime(post.getString("date")).toDate());
					event.setObject(post.getString("URL"));
					if (urlString.contains("comment")) event.setVerb(StepUpConstants.BLOGCOMMENT);
					else event.setVerb(StepUpConstants.BLOGPOST);
					event.setContext("chikul13");
					event.setOriginalRequest(post);
					ActivityStream as = new ActivityStream();
					as.setActor(username);
					if (urlString.contains("comment")) as.setVerb(StepUpConstants.BLOGCOMMENT);
					else as.setVerb(StepUpConstants.BLOGPOST);
					as.setPublishedDate(format.parseDateTime(post.getString("date")).toDate());
					if (post.getString("content").length()>144)
						as.setObject(post.getString("URL"),post.getString("content").substring(0, 144));
					else as.setObject(post.getString("URL"),post.getString("content"));
					System.out.println(as.getActivityStream().toString());
					System.out.println(RestClient.doPost("http://chi13course.appspot.com/api/activities/add", as.getActivityStream().toString()));
					EventGoogleDataStore.insertEvent(event);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 	
	}

	
	private void getRssFeedsComments(String urlString, Date lastUpdate){
		
		//System.out.println(lastUpdate.toString());
		
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    if (syncCache.get("matchingusernames")==null){
			ReadGoogleSpreadSheet.read();
		}
	    Hashtable<String,String> matchingusernames = (Hashtable<String,String>)syncCache.get("matchingusernames");
		
		try {
			String wordpressPosts = RestClient.doGet(urlString);
			JSONArray posts = new JSONObject(wordpressPosts).getJSONArray("comments");
			DateTimeFormatter format = ISODateTimeFormat.dateTimeNoMillis();

			java.util.Date created = null;
			Calendar cal = Calendar.getInstance();
			System.out.println("Length"+posts.length());
			for (int i=0; i<posts.length(); i++){
				JSONObject post = posts.getJSONObject(i);
				System.out.println(format.parseDateTime(post.getString("date")).toDate().toString());
				if (format.parseDateTime(post.getString("date")).toDate().compareTo(lastUpdate)>0){
					String username = post.getJSONObject("author").getString("name").toLowerCase();
					if (matchingusernames.containsKey(username)){
						username = matchingusernames.get(username);
					}
					Event event = new Event();
					event.setUsername(username);
					event.setStartTime(format.parseDateTime(post.getString("date")).toDate());
					event.setObject(post.getString("URL"));
					if (urlString.contains("comment")) event.setVerb(StepUpConstants.BLOGCOMMENT);
					else event.setVerb(StepUpConstants.BLOGPOST);
					event.setContext("chikul13");
					event.setOriginalRequest(post);
					ActivityStream as = new ActivityStream();
					as.setActor(username);
					if (urlString.contains("comment")) as.setVerb(StepUpConstants.BLOGCOMMENT);
					else as.setVerb(StepUpConstants.BLOGPOST);
					as.setPublishedDate(format.parseDateTime(post.getString("date")).toDate());
					if (post.getString("content").length()>144)
						as.setObject(post.getString("URL"),post.getString("content").substring(0, 144));
					else as.setObject(post.getString("URL"),post.getString("content"));
					System.out.println(as.getActivityStream().toString());
					System.out.println(RestClient.doPost("http://chi13course.appspot.com/api/activities/add", as.getActivityStream().toString()));
					EventGoogleDataStore.insertEvent(event);
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
