package org.be.kuleuven.hci.stepup.test;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.be.kuleuven.hci.stepup.persistanceLayer.EventPostgreSQL;
import org.json.JSONArray;
import org.json.JSONException;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

public class TestRSSGeneration {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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

	}

}
