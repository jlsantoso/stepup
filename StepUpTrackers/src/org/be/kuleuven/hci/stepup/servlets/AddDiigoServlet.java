package org.be.kuleuven.hci.stepup.servlets;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
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
import org.be.kuleuven.hci.stepup.util.StepUpConstants;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class AddDiigoServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(AddDiigoServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.log(Level.INFO, "Cron job");
		Date lastUpdate = EventGoogleDataStore.getLastUpdateDiigo();
		/*List<RssFeeds> rssFeeds = EventGoogleDataStore.getRssFeeds();
		for (RssFeeds r : rssFeeds){
			getRssFeeds(r.getURL(), lastUpdate);
		}*/
		getDiigoFeeds("https://www.diigo.com/rss/user/erikduval", lastUpdate);
		
	}

	private void getDiigoFeeds(String urlString, Date lastUpdate){

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
					Event event = new Event();
					event.setUsername(entry.getAuthor());
					event.setStartTime(entry.getPublishedDate());
					event.setObject(entry.getLink());
					event.setVerb(StepUpConstants.DIIGOVERB);
					event.setContext("openBadges");
					event.setOriginalRequest(new JSONObject().put("description",entry.getDescription()));
					EventGoogleDataStore.insertEvent(event);
					ActivityStream as = new ActivityStream();
					as.setActor(entry.getAuthor());
					as.setVerb(StepUpConstants.DIIGOVERB);
					as.setPublishedDate(entry.getPublishedDate());
					as.setObject("<a href=\""+entry.getLink()+"\">"+entry.getDescription().getValue()+"</a>");
					
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
