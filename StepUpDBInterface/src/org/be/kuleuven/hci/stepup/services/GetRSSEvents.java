/**
 * 
 */
package org.be.kuleuven.hci.stepup.services;


import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.be.kuleuven.hci.stepup.controller.EventController;
import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.persistanceLayer.EventPostgreSQL;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.spi.resource.Singleton;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedOutput;

@Singleton
@Path("/getRSSEvents")


public class GetRSSEvents {
	@GET @Path("/{context}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getRSSevents(@PathParam("context") String context) {	
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
			JSONArray results = new JSONArray(EventPostgreSQL.getOpenDB("select * from event where context='"+context+"'", "0"));
			for (int i=0; i<results.length();i++){
				JSONObject json = results.getJSONObject(i);
				entry = new SyndEntryImpl();
				entry.setTitle("The student "+json.getString("username")+" "+json.getString("verb"));
				entry.setAuthor(json.getString("username"));
				entry.setLink("http://twitter.com/"+json.getString("username")+"/statuses/"+json.getString("object"));
				entry.setPublishedDate(Calendar.getInstance().getTime());
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
		}
		return writer.toString();
	} 
}

