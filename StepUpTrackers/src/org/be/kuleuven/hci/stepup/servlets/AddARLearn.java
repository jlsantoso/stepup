package org.be.kuleuven.hci.stepup.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.be.kuleuven.hci.stepup.model.utils.JSONandEvent;
import org.be.kuleuven.hci.stepup.persistancelayer.EventGoogleDataStore;
import org.be.kuleuven.hci.stepup.persistancelayer.RestClient;
import org.be.kuleuven.hci.stepup.util.ReadGoogleSpreadSheet;
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

public class AddARLearn extends HttpServlet {

	private static final Logger log = Logger.getLogger(AddARLearn.class.getName());

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		long date = 1374142261536L;
		String elggRunsIds = "http://streetlearn.appspot.com/rest/response/runId/";
		String actions = "http://streetlearn.appspot.com/rest/actions/runId/";
		String response = "http://streetlearn.appspot.com/rest/response/runId/";
		Date lastUpdate = EventGoogleDataStore.getLastUpdateARLearn(); //Still to finish
		//lastUpdate = new Date(date);
		System.out.println(lastUpdate);
		ArrayList<Integer> runsARLearn = new ArrayList<Integer>();
		Hashtable<String,String> runIdsInquiryId = new Hashtable<String,String>();
		try {
			runIdsInquiryId = getARLearnRuns(elggRunsIds);
			Enumeration e = runIdsInquiryId.keys();
			while (e.hasMoreElements()){
				String runId = (String)e.nextElement();
				getUserDataFromARLearnActions(actions, lastUpdate, Integer.parseInt(runId), runIdsInquiryId.get(runId));
				getUserDataFromARLearnResponses(response, lastUpdate, Integer.parseInt(runId), runIdsInquiryId.get(runId));
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private Hashtable<String,String> getARLearnRuns(String urlElgg) throws UnsupportedEncodingException, JSONException{
		Hashtable<String,String> runIdsInquiryId = new Hashtable<String,String>();
		String final_object = RestClient.doGet("http://wespot.kmi.open.ac.uk/services/api/rest/json/?method=site.inquiries&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&minutes=4448");
		JSONObject json = new JSONObject(final_object);
		JSONArray inquiries = json.getJSONArray("result");
		ArrayList<Integer> listRuns = new ArrayList<Integer>();
		for (int i=0; i<inquiries.length();i++){
			System.out.println(inquiries.getJSONObject(i));
			String final_object2 = RestClient.doGet("http://wespot.kmi.open.ac.uk/services/api/rest/json/?method=inquiry.arlearnrun&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&inquiryId="+inquiries.getJSONObject(i).getInt("inquiryId"));
			System.out.println("http://wespot.kmi.open.ac.uk/services/api/rest/json/?method=inquiry.arlearnrun&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&inquiryId="+inquiries.getJSONObject(i).getInt("inquiryId"));
			JSONObject jsonRun = new JSONObject(final_object2);
			System.out.println(jsonRun);
			if (jsonRun.getInt("status")==0){
				runIdsInquiryId.put(jsonRun.getString("result"), inquiries.getJSONObject(i).getString("inquiryId"));
				listRuns.add(jsonRun.getInt("result"));
			}
		}
		
		//listRuns.add(3639020);
		//return listRuns;
		return runIdsInquiryId;
	}

	private void getUserDataFromARLearnActions(String urlString, Date lastUpdate, int runId, String inquiry){

		URL url;
		try {
			String final_object = RestClient.doGetAuth(urlString+runId+"?from="+lastUpdate.getTime());
			//System.out.println(final_object);
			JSONObject json = new JSONObject(final_object);
			JSONArray jsonActions = json.getJSONArray("actions");
			for (int i=0;i<jsonActions.length();i++){
				JSONObject action = jsonActions.getJSONObject(i);
				String username = modifyUsername(action.getString("userEmail"));
				
				Event event = new Event();
				event.setUsername(username);
				event.setStartTime(new Date(action.getLong("timestamp")));
				event.setContext("{\"course\":\""+inquiry+"\",\"phase\":\""+StepUpConstants.PHASE3+"\",\"subphase\":\""+StepUpConstants.PHASE3_S2+"\"}");
				event.setObject(action.getString("generalItemId"));
				event.setVerb(action.getString("action"));
				event.setOriginalRequest(action);
				//System.out.println(JSONandEvent.transformFromEvemtToJson(event));
				EventGoogleDataStore.insertEvent(event);
			}
		
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	private String modifyUsername(String username){
		username = username.replace("1:", "facebook_");
		username = username.replace("2:", "google_");
		username = username.replace("3:", "linkedin_");
		username = username.replace("4:", "twitter_");
		return username;
	}
		
	private void getUserDataFromARLearnResponses(String urlString, Date lastUpdate, int runId, String inquiry){
		
		URL url;
		try {
			String final_object = RestClient.doGetAuth(urlString+runId+"?from="+lastUpdate.getTime());
			//System.out.println(final_object);
			JSONObject json = new JSONObject(final_object);
			JSONArray jsonActions = json.getJSONArray("responses");
			for (int i=0;i<jsonActions.length();i++){
				JSONObject action = jsonActions.getJSONObject(i);
				String username = modifyUsername(action.getString("userEmail"));
				Event event = new Event();
				event.setUsername(username);
				event.setStartTime(new Date(action.getLong("timestamp")));
				event.setObject(action.getString("generalItemId"));
				event.setVerb("response");
				event.setContext("{\"course\":\""+inquiry+"\",\"phase\":\""+StepUpConstants.PHASE3+"\",\"subphase\":\""+StepUpConstants.PHASE3_S2+"\"}");
				event.setOriginalRequest(action);
				//System.out.println(JSONandEvent.transformFromEvemtToJson(event));
				EventGoogleDataStore.insertEvent(event);
			}
		
		
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
