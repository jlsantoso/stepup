package org.be.kuleuven.hci.aggregationlayer.model.test;

import java.util.ArrayList;
import java.util.Calendar;

import org.be.kuleuven.hci.aggregationlayer.model.Course;

public class testCourse {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Course course = new Course();
		Calendar start = Calendar.getInstance();
		start.set(Calendar.DAY_OF_MONTH, 1);
		start.set(Calendar.MONTH, 8);
		start.set(Calendar.YEAR, 2012);
		course.setStartCourse(start.getTime());
		course.generateWeeks();
		ArrayList<Integer> weeks = course.getWeeks();
		for (Integer week : weeks){
			System.out.println(week);
		}	
		
		Calendar testing = Calendar.getInstance();
		testing.add(Calendar.DAY_OF_MONTH, -7);
		System.out.println("week position: "+course.getWeekPosition(testing.getTime()));
	}

	
	
}
