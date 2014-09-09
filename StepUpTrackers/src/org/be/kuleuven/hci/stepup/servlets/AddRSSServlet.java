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
import org.be.kuleuven.hci.stepup.util.ReadGoogleSpreadSheetChi14;
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

public class AddRSSServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(AddRSSServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		log.log(Level.INFO, "Cron job");
		log.warning("Last RSS feed AddRSSServlet");
		Date lastUpdate = EventGoogleDataStore.getLastUpdateRss();
		if (syncCache.get("blogsfeed")==null){
			ReadGoogleSpreadSheetChi14.read();
		}

		Hashtable<String,String> feeds = (Hashtable<String, String>) syncCache.get("blogsfeed");
		Enumeration e = feeds.keys();
		String context = "chikul14";
		log.warning("Context:"+context+"last update of blogs:"+lastUpdate.toString());
		while( e.hasMoreElements()) {
			  String key = (String)e.nextElement();
			  System.out.println("URL feed: "+key);
			  //getRssFeeds(key, lastUpdate);
			  //if (key.contains("kuladi")||key.contains("pikachi")||key.contains("chi2014"))
			  if (key.contains("/comments/feed/"))
				  getRssFeedsComments(key.replace("http://", "").replace("/comments/feed/", ""), lastUpdate, context);
			  else {
				  if (key.contains("wordpress"))
					  getRssFeedsWordpress(key.replace("http://", "").replace("/feed/", ""), lastUpdate, context);
				  else
					  getRssFeeds(key, lastUpdate, context);
			  }
		}		
	}

	private void getRssFeeds(String urlString, Date lastUpdate, String context){
		
		System.out.println(lastUpdate.toString());
		
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    /*if (syncCache.get("matchingusernames")==null){
			ReadGoogleSpreadSheet.read();
		}
	    Hashtable<String,String> matchingusernames = (Hashtable<String,String>)syncCache.get("matchingusernames");*/
		SyndFeedInput sfi=new SyndFeedInput();
		URL url;
		try {
			url = new URL(urlString);
			SyndFeed feed = null;
			feed= sfi.build(new XmlReader(url));
			//System.out.println(urlString + feed.toString());
			List<SyndEntry>	entries = feed.getEntries();
			java.util.Date created = null;
			Calendar cal = Calendar.getInstance();

			for (SyndEntry entry:entries){
				//created = entry.getPublishedDate();
				System.out.println(entry.getPublishedDate().toString()+"="+lastUpdate.toString());
				if (entry.getPublishedDate().compareTo(lastUpdate)>0){
					String username = entry.getAuthor().toLowerCase();
					/*if (matchingusernames.containsKey(username)){
						username = matchingusernames.get(username);
					}*/
					Event event = new Event();
					event.setUsername(username);
					event.setStartTime(entry.getPublishedDate());
					event.setObject(entry.getLink());
					if (urlString.contains("comment")) event.setVerb(StepUpConstants.BLOGCOMMENT);
					else event.setVerb(StepUpConstants.BLOGPOST);
					event.setContext(context);
					JSONObject originalrequest = new JSONObject();
					originalrequest.put("description",entry.getDescription().getValue().replaceAll("\\P{Print}", ""));
					originalrequest.put("title",entry.getTitle().replaceAll("\\P{Print}", ""));
					event.setOriginalRequest(originalrequest);
					EventGoogleDataStore.insertEvent(event);
				}
			}
		} catch (IllegalArgumentException e) {
			log.severe(e.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			log.severe(e.toString());
		}		
	}
	
	private void getRssFeedsWordpress(String urlString, Date lastUpdate, String context){
		
		//System.out.println(lastUpdate.toString());
		urlString = "https://public-api.wordpress.com/rest/v1/sites/"+urlString+"/posts/";
		try {
			String wordpressPosts = RestClient.doGet(urlString);
			JSONArray posts = new JSONObject(wordpressPosts).getJSONArray("posts");
			DateTimeFormatter format = ISODateTimeFormat.dateTimeNoMillis();

			java.util.Date created = null;
			Calendar cal = Calendar.getInstance();
			System.out.println("Length"+posts.length());
			for (int i=0; i<posts.length(); i++){
				JSONObject post = posts.getJSONObject(i);
				System.out.println(format.parseDateTime(post.getString("date")).toDate().toString());
				if (format.parseDateTime(post.getString("date")).toDate().compareTo(lastUpdate)>0){
					String username = post.getJSONObject("author").getString("name").toLowerCase();

					Event event = new Event();
					event.setUsername(username);
					event.setStartTime(format.parseDateTime(post.getString("date")).toDate());
					event.setObject(post.getString("URL"));
					if (urlString.contains("comment")) event.setVerb(StepUpConstants.BLOGCOMMENT);
					else event.setVerb(StepUpConstants.BLOGPOST);
					event.setContext(context);
					JSONObject originalrequest = new JSONObject();
					originalrequest.put("description",post.getString("content").replaceAll("\\P{Print}", ""));
					originalrequest.put("title",post.getString("title").replaceAll("\\P{Print}", ""));
					event.setOriginalRequest(originalrequest);
					System.out.println(event.getObject());
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
	
	private void getRssFeedsComments(String urlString, Date lastUpdate, String context){
		
		//System.out.println(lastUpdate.toString());
		urlString = "https://public-api.wordpress.com/rest/v1/sites/"+urlString+"/comments/?number=100";
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
				if (post.getString("type").compareTo("pingback")!=0&&format.parseDateTime(post.getString("date")).toDate().compareTo(lastUpdate)>0){
					String username = post.getJSONObject("author").getString("name").toLowerCase();

					Event event = new Event();
					event.setUsername(username);
					event.setStartTime(format.parseDateTime(post.getString("date")).toDate());
					event.setObject(post.getString("URL"));
					if (urlString.contains("comment")) event.setVerb(StepUpConstants.BLOGCOMMENT);
					else event.setVerb(StepUpConstants.BLOGPOST);
					event.setContext(context);
					event.setOriginalRequest(new JSONObject().put("description",post.getString("content").replaceAll("\\P{Print}", "")));
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
