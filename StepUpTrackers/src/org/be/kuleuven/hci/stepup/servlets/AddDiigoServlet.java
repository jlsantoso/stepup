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

public class AddDiigoServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(AddDiigoServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		log.log(Level.INFO, "Cron job");
		Date lastUpdate = EventGoogleDataStore.getLastUpdateDiigo();
		if (syncCache.get("diigofeeds")==null){
			ReadGoogleSpreadSheet.read();
		}
		Hashtable<String,String> feeds = (Hashtable<String, String>) syncCache.get("diigofeeds");
		Enumeration e = feeds.keys();
		int i = 0;
		while( e.hasMoreElements()) {
			i++;
			  String key = (String)e.nextElement();
			  System.out.println("URL feed: "+key);
			  getDiigoFeeds(key, lastUpdate);
		}
		/*List<RssFeeds> rssFeeds = EventGoogleDataStore.getRssFeeds();
		for (RssFeeds r : rssFeeds){
			getRssFeeds(r.getURL(), lastUpdate);
		}*/
		
	}

	private void getDiigoFeeds(String urlString, Date lastUpdate){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    if (syncCache.get("matchingusernames")==null){
			ReadGoogleSpreadSheet.read();
		}
	    Hashtable<String,String> matchingusernames = (Hashtable<String,String>)syncCache.get("matchingusernames");
		
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
				System.out.println("Date:"+entry.getPublishedDate()+"-"+lastUpdate); 
				if (entry.getPublishedDate().compareTo(lastUpdate)>0){
					String username = urlString.replaceAll("https://www.diigo.com/rss/user/", "");
					
					if (matchingusernames.containsKey(username)){
						username = matchingusernames.get(username);
					}
					Event event = new Event();
					event.setUsername(username);
					event.setStartTime(entry.getPublishedDate());
					event.setObject(entry.getLink());
					event.setVerb(StepUpConstants.DIIGOVERB);
					event.setContext("chikul13");
					event.setOriginalRequest(new JSONObject().put("description",entry.getDescription().getValue()));
					//EventGoogleDataStore.insertEvent(event);
					ActivityStream as = new ActivityStream();
					as.setActor(username);
					as.setVerb(StepUpConstants.DIIGOVERB);
					as.setPublishedDate(entry.getPublishedDate());
					as.setObject(entry.getLink(),entry.getDescription().getValue());
					System.out.println(lastUpdate.toString());
					System.out.println(as.getActivityStream().toString());
					log.warning(RestClient.doPost("http://chi13course.appspot.com/api/activities/add", as.getActivityStream().toString()));
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
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
