package org.be.kuleuven.hci.aggregationlayer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.logging.Level;

import javax.servlet.http.*;

import org.be.kuleuven.hci.aggregationlayer.modelwespot.utils.CopyInitWeSPOTCourse;
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
public class DeleteCourses extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		PersistanceLayer.deleteCourses();
		
	}
	
	
	
	
}
