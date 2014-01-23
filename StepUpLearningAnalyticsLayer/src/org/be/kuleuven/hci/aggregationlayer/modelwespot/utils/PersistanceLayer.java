package org.be.kuleuven.hci.aggregationlayer.modelwespot.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;

import org.be.kuleuven.hci.aggregationlayer.model.utils.RestClient;
import org.be.kuleuven.hci.aggregationlayer.modelwespot.Course;

import com.google.appengine.api.datastore.EntityTranslator;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.googlecode.objectify.cmd.Query;



public class PersistanceLayer {

	public static void saveCourse(Course course, String name){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    syncCache.put(name, course);
	    OfyService.getOfyService();
		//EntityTranslator.convertToPb(course);
	    OfyService.ofy().save().entity(course).now(); 	    
	}
	
	public static void deleteCourses(){
		List<Course> courses = OfyService.getOfyService().ofy().load().type(Course.class).list();
    	OfyService.getOfyService().ofy().delete().entities(courses);
	}
	
	public static Course getCourse(String name){
		System.out.println("access getCourse");
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    Course course = new Course();
	    if (syncCache.get(name)!=null){
	    	System.out.println("access cache");
	    	course = (Course) syncCache.get(name);	  
	    	
	    }else{
	    	System.out.println("access db:"+name);
	    	List<Course> courses = OfyService.getOfyService().ofy().load().type(Course.class).list();
	    	System.out.println(courses.size());
	    	for (Course c: courses){
	    		syncCache.put(c.getName(), c);
	    		if (c.getName().compareTo(name)==0) course = c;
	    	}
	    	//course = courses.get(courses.size()-1);
	    	
	    }
	    System.out.println("El curso es: "+course.getName());
	    return course;
	}
	
	//query db at our server (REST Services)
	public static String query(){
		try {
			String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event\", \"pag\": \"0\"}");
			return result;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}
