package org.be.kuleuven.hci.stepup.test;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.http.HttpException;
import org.be.kuleuven.hci.stepup.persistanceLayer.EventPostgreSQL;
import org.be.kuleuven.hci.stepup.utils.RestClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;
//Used for testing purposes.
public class TestRSSGeneration {

	/**
	 * @param args
	 */
	
	public static void main(String[] args){
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("rss_2.0");
		feed.setTitle("Activity Feed of the course");
		feed.setLink("http://ariadne.cs.kuleuven.be/wiki");
		feed.setDescription("Activity Feed of the CHI course");
		StringWriter writer = new StringWriter();
		List entries = new ArrayList();
		try {
			SyndEntry entry;
			SyndContent description;
			JSONArray results = new JSONArray(RestClient.doPost("http://localhost:8888/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13'\", \"pag\": \"1\"}"));
			System.out.println(results.toString());
			for (int i=0; i<results.length();i++){
				JSONObject json = results.getJSONObject(i);
				entry = new SyndEntryImpl();
				entry.setTitle("The student "+json.getString("username")+" "+json.getString("verb"));
				entry.setAuthor(json.getString("username"));
				entry.setLink("http://twitter.com/"+json.getString("username")+"/statuses/"+json.getString("object"));
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZZ");
				entry.setPublishedDate(formatter.parse(json.getString("starttime")+"00"));
				description = new SyndContentImpl();
				description.setType("text/html");
				description.setValue("http://twitter.com/"+json.getString("username")+"/statuses/"+json.getString("object"));
				entry.setDescription(description);
				entries.add(entry);
			}
			feed.setEntries(entries);			
			SyndFeedOutput output = new SyndFeedOutput();
			output.output(feed, writer);
			System.out.println(writer);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/*public static void main(String[] args) {
		// TODO Auto-generated method stub
		SyndFeed feed = new SyndFeedImpl();
		feed.setFeedType("atom_1.0");
		feed.setTitle("Sample Feed (created with ROME)");
		feed.setLink("http://www.example.com");
		feed.setDescription("This feed has been created using ROME");

		List entries = new ArrayList();
		try {
			JSONArray json = new JSONArray(EventPostgreSQL.getOpenDB("select * from event where context='openBadges'", "0"));
			SyndEntry entry;
			SyndContent description;

			entry = new SyndEntryImpl();
			entry.setTitle("ROME v1.0");
			entry.setLink("http://wiki.java.net/bin/view/Javawsxml/Rome01");
			entry.setPublishedDate(Calendar.getInstance().getTime());
			description = new SyndContentImpl();
			description.setType("text/plain");
			description.setValue("Initial release of ROME");
			entry.setDescription(description);
			entries.add(entry);

			feed.setEntries(entries);
			
			StringWriter writer = new StringWriter();
			SyndFeedOutput output = new SyndFeedOutput();
			output.output(feed, writer);
			System.out.println(writer);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FeedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}*/

}
