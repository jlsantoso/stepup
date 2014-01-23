package org.be.kuleuven.hci.aggregationlayer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.logging.Level;

import javax.servlet.http.*;

import org.be.kuleuven.hci.aggregationlayer.modelwespot.utils.PersistanceLayer;
import org.be.kuleuven.hci.aggregationlayer.model.utils.RestClient;
import org.be.kuleuven.hci.aggregationlayer.modelwespot.Course;

import org.be.kuleuven.hci.aggregationlayer.modelwespot.utils.InitWeSPOTCourse;
import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.utils.JSONandEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;



@SuppressWarnings("serial")
public class WeSPOTCourseAnalyticsLayerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		PersistanceLayer.deleteCourses();
		String inquiries_URL = "http://wespot.kmi.open.ac.uk/services/api/rest/json/?method=site.inquiries&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8";
		String result_inquiries = RestClient.doGet(inquiries_URL);		
		try {
			JSONObject inquiries = new JSONObject(result_inquiries);
			JSONArray inquiries_array = inquiries.getJSONArray("result");
			for (int i=0; i<inquiries_array.length(); i++){
				JSONObject inquiry = inquiries_array.getJSONObject(i);
				buildCourse(inquiry.getString("inquiryId"));
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	void buildCourse(String inquiryId) throws UnsupportedEncodingException{
		int pagination = 0;
		int limit_pagination = 0;
		String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context like '%course___"+inquiryId+"%'\", \"pag\": \""+pagination+"\"}");
		Course course = new Course();
		try {
			course = InitWeSPOTCourse.wespot("wespot"+inquiryId);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		System.out.println("Result: "+result);
		try {
			JSONArray results = new JSONArray(result);
			for (int i=0; i<results.length();i++){
				Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
				limit_pagination = Integer.parseInt(results.getJSONObject(i).getString("full_count"));
				//System.out.println(JSONandEvent.transformFromEvemtToJson(event));
				course.spreadEvent(event);
			}
			boolean empty = false;
			
			while (!empty){
				pagination++;
				result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context like '%course___"+inquiryId+"%'\", \"pag\": \""+pagination+"\"}");
				//System.out.println("Result:"+result);
				results = new JSONArray(result);
				if (results.length()==0) empty=true;
				//System.out.println("Results: "+results.length());
				for (int i=0; i<results.length();i++){
					Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
					limit_pagination = Integer.parseInt(results.getJSONObject(i).getString("full_count"));
					//System.out.println(JSONandEvent.transformFromEvemtToJson(event));
					course.spreadEvent(event);
				}
			}
			
			//course.printBlogPosition();
			PersistanceLayer.saveCourse(course, "wespot"+inquiryId);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
