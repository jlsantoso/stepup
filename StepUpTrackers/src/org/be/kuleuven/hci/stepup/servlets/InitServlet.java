package org.be.kuleuven.hci.stepup.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.be.kuleuven.hci.stepup.persistancelayer.EventGoogleDataStore;
import org.be.kuleuven.hci.stepup.util.BlogsFeeds;
import org.be.kuleuven.hci.stepup.util.DateManager;
import org.be.kuleuven.hci.stepup.util.TwitterHashTags;
import org.be.kuleuven.hci.stepup.util.TwitterSearch;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Text;

public class InitServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(InitServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.log(Level.INFO, "Cron job");
		/*BlogsFeeds blogsFeeds = new BlogsFeeds();
		blogsFeeds.registerBlogsComments();
		blogsFeeds.registerBlogsPosts();	*/	
		TwitterHashTags twitterhash = new TwitterHashTags();
		twitterhash.registerhashComments();
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
