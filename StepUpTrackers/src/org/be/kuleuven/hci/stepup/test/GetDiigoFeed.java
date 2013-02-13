package org.be.kuleuven.hci.stepup.test;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.persistancelayer.EventGoogleDataStore;
import org.json.JSONException;
import org.json.XML;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

public class GetDiigoFeed {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Calendar lastUpdate = Calendar.getInstance();
		lastUpdate.add(Calendar.DAY_OF_MONTH, -90);
		getDiigoFeeds("https://www.diigo.com/rss/user/erikduval", lastUpdate.getTime());
	}
	
	private static void getDiigoFeeds(String urlString, Date lastUpdate){

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
				System.out.println("Date:"+entry.getPublishedDate()+"-"+lastUpdate+"-"+entry.getUpdatedDate()); 
				/*if (entry.getPublishedDate().compareTo(lastUpdate)>0){
					Event event = new Event();
					event.setUsername(entry.getAuthor());
					event.setStartTime(entry.getPublishedDate());
					event.setObject(entry.getLink());
					event.setVerb("bookmark");
					event.setOriginalRequest(XML.toJSONObject(entry.toString()));
					EventGoogleDataStore.insertEvent(event);
				}*/
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/ catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}

}
