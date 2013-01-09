package org.be.kuleuven.hci.aggregationlayer;

import java.io.IOException;
import java.text.ParseException;
import java.util.logging.Level;

import javax.servlet.http.*;

import org.be.kuleuven.hci.aggregationlayer.model.Course;
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
public class StepUpLearningAnalyticsLayerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		int pagination = 0;
		int limit_pagination = 0;
		String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context='thesis12'\", \"pag\": \""+pagination+"\"}");
		Course course = InitThesis12Course.init();
		System.out.println("Result: "+result);
		try {
			JSONArray results = new JSONArray(result);
			for (int i=0; i<results.length();i++){
				Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
				limit_pagination = Integer.parseInt(results.getJSONObject(i).getString("full_count"));
				EventController.spreadEvent(event, course);
			}
			for (pagination=1; pagination<(limit_pagination/10)+1; pagination++){
				result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event\", \"pag\": \""+pagination+"\"}");
				System.out.println("Result:"+result);
				results = new JSONArray(result);
				for (int i=0; i<results.length();i++){
					Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
					limit_pagination = Integer.parseInt(results.getJSONObject(i).getString("full_count"));
					EventController.spreadEvent(event, course);
				}
			}
			
		PersistanceLayer.saveCourse(course, "bigtablethesis12");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
