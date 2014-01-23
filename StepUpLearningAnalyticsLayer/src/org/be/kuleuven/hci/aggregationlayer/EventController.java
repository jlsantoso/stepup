package org.be.kuleuven.hci.aggregationlayer;

import java.util.logging.Level;

import org.be.kuleuven.hci.aggregationlayer.model.Course;
import org.be.kuleuven.hci.stepup.model.Event;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class EventController {

	public static void spreadEvent(Event event, Course course){
		course.spreadEvent(event);
	}

}
