package org.be.kuleuven.hci.openbadges.badges;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;

import org.be.kuleuven.hci.openbadges.utils.RestClient;

public class ChiCourseBadgesRules {
	

	Calendar startCourse(){
		Calendar startCourse = Calendar.getInstance();
		startCourse.set(2013, 1, 13);
		return startCourse;
	}
	
	Calendar endCourse(){
		Calendar endCourse = Calendar.getInstance();
		endCourse.set(2013, 4, 28);
		return endCourse;
	}
	
	int getCurrentWeekOfTheCourse(){
		Calendar today = Calendar.getInstance();
		int week = 0;
		today.add(Calendar.DAY_OF_MONTH, -7);
		while (today.after(startCourse())){
			week++;
			today.add(Calendar.DAY_OF_MONTH, -7);			
		}		
		return week;
	}
	
	void commentsOnOtherBlogs(){
		int current_week=getCurrentWeekOfTheCourse();
		int pagination = 0;
		try {
			
			String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select distinct username from event where context='chi13'\", \"pag\": \""+pagination+"\"}");
					RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context='chi13'\", \"pag\": \""+pagination+"\"}");
			
		
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
