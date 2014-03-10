package org.be.kuleuven.hci.stepup.servlets;

import java.io.IOException;
import java.net.URL;
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

public class UpdateObjectValuesServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(UpdateObjectValuesServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.warning("Updating Objects");
		ArrayList<String> objects = new ArrayList<String>();
		//objects.add("http://chi2014.wordpress.com/2014/03/07/session-3-review/");
		//objects.add("http://chi2014.wordpress.com/2014/03/07/hci-story-of-the-week-33-103/");
		//objects.add("http://dsminterfaces.wordpress.com/2014/03/07/paper-prototype/");
		//objects.add("http://chi2014.wordpress.com/2014/03/07/analyzing-similar-applications/");
		for (String o:objects)
			EventGoogleDataStore.updatedObjectValues(o);			
	}

	
	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
