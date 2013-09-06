package org.be.kuleuven.hci.aggregationlayer;

import java.io.IOException;
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
		int pagination = 0;
		int limit_pagination = 0;
		String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context like '%elgg%'\", \"pag\": \""+pagination+"\"}");
		Course course = new Course();
		try {
			course = InitWeSPOTCourse.wespot();
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
				course.spreadEvent(event);
				System.out.println(JSONandEvent.transformFromEvemtToJson(event));
			}
			boolean empty = false;
			
			while (!empty){
				pagination++;
				result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context like '%elgg%'\", \"pag\": \""+pagination+"\"}");
				//System.out.println("Result:"+result);
				results = new JSONArray(result);
				if (results.length()==0) break;
				System.out.println("Results: "+results.length());
				for (int i=0; i<results.length();i++){
					Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
					limit_pagination = Integer.parseInt(results.getJSONObject(i).getString("full_count"));
					System.out.println(JSONandEvent.transformFromEvemtToJson(event));
					course.spreadEvent(event);
				}
			}
			
			//course.printBlogPosition();
			PersistanceLayer.saveCourse(course, "wespotdata");

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
