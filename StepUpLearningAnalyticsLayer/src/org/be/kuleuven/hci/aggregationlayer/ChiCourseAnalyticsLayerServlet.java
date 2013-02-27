package org.be.kuleuven.hci.aggregationlayer;

import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;

import javax.servlet.http.*;

import org.be.kuleuven.hci.aggregationlayer.model.Course;
import org.be.kuleuven.hci.aggregationlayer.model.utils.InitChiKul13Course;
import org.be.kuleuven.hci.aggregationlayer.model.utils.InitThesis12Course;
import org.be.kuleuven.hci.aggregationlayer.model.utils.OfyService;
import org.be.kuleuven.hci.aggregationlayer.model.utils.PersistanceLayer;
import org.be.kuleuven.hci.aggregationlayer.model.utils.RestClient;
import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.utils.JSONandEvent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;



@SuppressWarnings("serial")
public class ChiCourseAnalyticsLayerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		int pagination = 0;
		int limit_pagination = 0;
		String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13'\", \"pag\": \""+pagination+"\"}");
		Course course = InitChiKul13Course.chikul13();
		//System.out.println("Result: "+result);
		try {
			JSONArray results = new JSONArray(result);
			for (int i=0; i<results.length();i++){
				Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
				limit_pagination = Integer.parseInt(results.getJSONObject(i).getString("full_count"));
				EventController.spreadEvent(event, course);
			}
			boolean empty = false;
			
			while (!empty){
				pagination++;
				result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13'\", \"pag\": \""+pagination+"\"}");
				//System.out.println("Result:"+result);
				results = new JSONArray(result);
				if (results.length()==0) break;
				System.out.println("Results: "+results.length());
				for (int i=0; i<results.length();i++){
					Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
					limit_pagination = Integer.parseInt(results.getJSONObject(i).getString("full_count"));
					System.out.println(JSONandEvent.transformFromEvemtToJson(event));
					EventController.spreadEvent(event, course);
				}
			}
			
			//course.printBlogPosition();
			PersistanceLayer.saveCourse(course, "bigtablechikul13");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
