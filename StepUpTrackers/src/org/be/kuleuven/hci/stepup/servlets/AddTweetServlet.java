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

import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.TwitterHash;
import org.be.kuleuven.hci.stepup.persistancelayer.EventGoogleDataStore;
import org.be.kuleuven.hci.stepup.util.DateManager;
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

public class AddTweetServlet extends HttpServlet {

	private static final Logger log = Logger.getLogger(AddTweetServlet.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		log.log(Level.INFO, "Cron job");
		String since_id = "119719744281640960";
		String lastId = EventGoogleDataStore.getLastTwitterId();
		if (lastId!=null) since_id=lastId;
		System.out.println(since_id);
		List<TwitterHash> twitterHashTags = EventGoogleDataStore.getTwitterHashTags();
		for (TwitterHash t : twitterHashTags){
			createTweetEntitiesfromHastTag(t.getHash(), since_id);
		}
		
	}

	private void createTweetEntitiesfromHastTag(String hashtag, String since_id) {

		log.log(Level.INFO, "createTweetEntitiesfromHastTag");
		TwitterSearch ts = new TwitterSearch();
		JSONArray results = ts.getTweetsFromHashtag(hashtag, since_id);
		for (int i = 0; i < results.length(); i++) {
			JSONObject resultObj = null;
			try {
				resultObj = results.getJSONObject(i);
				//System.out.println(resultObj.toString());
				DateTime parseDateTime = DateManager.fmt1.parseDateTime(resultObj.getString("created_at").split(", ")[1].trim());
				JSONArray htags = resultObj.getJSONObject("entities").getJSONArray("hashtags");
				String tags = "";
				for (int j = 0; j < htags.length(); j++) {
					JSONObject htag = htags.getJSONObject(j);
					if (!tags.isEmpty())
						tags += ",";
					tags += htag.getString("text");
				}
				Event event = new Event();
				event.setUsername(resultObj.getString("from_user"));
				event.setVerb("tweet");
				event.setObject(resultObj.getString("id"));
				event.setContext(hashtag);
				event.setOriginalRequest(resultObj);
				event.setStartTime(parseDateTime.toDate());

				if (resultObj.has("to_user"))
					event.setTarget(resultObj.getString("to_user"));
				//System.out.println(EventGoogleDataStore.existEvent(event));
				EventGoogleDataStore.insertEvent(event);

			} catch (JSONException e) {
				log.log(Level.SEVERE, "Json parse exception. Json=" + resultObj, e);
			}
		}
	}

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
