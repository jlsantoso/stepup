package org.be.kuleuven.hci.openbadges.badges.rules.utils;

import java.util.Calendar;

public class Dates {

	public static Calendar startCourse(){
		Calendar startCourse = Calendar.getInstance();
		startCourse.set(2013, 1, 19);
		return startCourse;
	}
	
	public static Calendar endCourse(){
		Calendar endCourse = Calendar.getInstance();
		endCourse.set(2013, 4, 28);
		return endCourse;
	}
	
	public static int getCurrentBiWeekOfTheCourse(){
		Calendar today = Calendar.getInstance();
		int biweek = 0;
		today.add(Calendar.DAY_OF_MONTH, -14);
		while (today.after(startCourse())){
			biweek++;
			today.add(Calendar.DAY_OF_MONTH, -14);			
		}		
		return biweek;
		//return 1;
	}
	
	public static String getQueryBetweenDates(){
		int week = getCurrentBiWeekOfTheCourse();
		Calendar start = startCourse();
		start.add(Calendar.DAY_OF_MONTH, week*14);
		String query = " and DATE(starttime)>'"+start.get(Calendar.YEAR)+"-"+(start.get(Calendar.MONTH)+1)+"-"+start.get(Calendar.DAY_OF_MONTH)+"' ";
		start.add(Calendar.DAY_OF_MONTH, 14);
		query = query+" and DATE(starttime)<='"+start.get(Calendar.YEAR)+"-"+(start.get(Calendar.MONTH)+1)+"-"+start.get(Calendar.DAY_OF_MONTH)+"' ";
		return query;
	}
	
	public static String getFilterURLComments(){
		int week = getCurrentBiWeekOfTheCourse();
		Calendar start = startCourse();
		Calendar end = startCourse();
		start.add(Calendar.DAY_OF_MONTH, week*14);
		end.add(Calendar.DAY_OF_MONTH, week*14);
		end.add(Calendar.DAY_OF_MONTH, 13);
		String query = " and (";
		while (!start.after(end)){
			start.add(Calendar.DAY_OF_MONTH, 1);
			query += "object like '%/"+start.get(Calendar.YEAR)+"/"+(start.get(Calendar.MONTH)+1)+"/"+start.get(Calendar.DAY_OF_MONTH)+"/%' or ";
		}
		start.add(Calendar.DAY_OF_MONTH, 1);
		query += "object like '%/"+start.get(Calendar.YEAR)+"/"+(start.get(Calendar.MONTH)+1)+"/"+start.get(Calendar.DAY_OF_MONTH)+"/%'";
		query+=")";
		return query;		
	}
	
	public static String getStartDate(){
		int week = getCurrentBiWeekOfTheCourse();
		Calendar start = startCourse();
		start.add(Calendar.DAY_OF_MONTH, week*14);
		String query = start.get(Calendar.YEAR)+"-"+(start.get(Calendar.MONTH)+1)+"-"+start.get(Calendar.DAY_OF_MONTH);
		return query;
	}
	
	public static String getEndDate(){
		int week = getCurrentBiWeekOfTheCourse();
		Calendar start = startCourse();
		start.add(Calendar.DAY_OF_MONTH, week*14);
		start.add(Calendar.DAY_OF_MONTH, 14);
		String query = start.get(Calendar.YEAR)+"-"+(start.get(Calendar.MONTH)+1)+"-"+start.get(Calendar.DAY_OF_MONTH);
		return query;
	}

}
