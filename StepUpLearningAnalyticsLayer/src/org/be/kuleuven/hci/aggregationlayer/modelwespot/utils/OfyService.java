package org.be.kuleuven.hci.aggregationlayer.modelwespot.utils;

import java.util.Hashtable;


import org.be.kuleuven.hci.aggregationlayer.modelwespot.Course;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {
	
	private static OfyService _ofyService;
    
	OfyService (){
        //factory().register(Activity.class);
        //factory().register(Blog.class);
        //factory().register(Comments.class);
        factory().register(Course.class);
        //factory().register(Group.class);
        //factory().register(Posts.class);
        //factory().register(Student.class);
        //factory().register(Tweets.class);
    }
	
	public static synchronized OfyService getOfyService() {
		if (_ofyService == null) {
			_ofyService = new OfyService();
			
		}
		return _ofyService;
	}

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
    
}