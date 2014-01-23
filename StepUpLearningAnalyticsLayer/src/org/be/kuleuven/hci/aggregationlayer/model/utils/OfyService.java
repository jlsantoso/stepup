package org.be.kuleuven.hci.aggregationlayer.model.utils;

import java.util.Hashtable;

import org.be.kuleuven.hci.aggregationlayer.model.Activity;
import org.be.kuleuven.hci.aggregationlayer.model.Blog;
import org.be.kuleuven.hci.aggregationlayer.model.Comments;
import org.be.kuleuven.hci.aggregationlayer.model.Course;
import org.be.kuleuven.hci.aggregationlayer.model.Group;
import org.be.kuleuven.hci.aggregationlayer.model.Posts;
import org.be.kuleuven.hci.aggregationlayer.model.Student;
import org.be.kuleuven.hci.aggregationlayer.model.Tweets;
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