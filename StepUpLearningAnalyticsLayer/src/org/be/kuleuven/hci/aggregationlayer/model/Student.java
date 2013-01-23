package org.be.kuleuven.hci.aggregationlayer.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;


public class Student implements Serializable {
    
	String username;
	String togglKey;
	ArrayList<Integer> postsActivityPerBlog;
	ArrayList<Integer> commentsActivityPerBlog;
	int tweetActivity;
	ArrayList<Integer> postsActivityPerWeek;
	ArrayList<Integer> commentsActivityPerWeek;
	ArrayList<Integer> tweetActivityPerWeek;
	int duration;
	ArrayList<Activity> timeSpentByActivities;
	int timeSpent;
	
	public Student(){
		postsActivityPerBlog = new ArrayList<Integer>();
		commentsActivityPerBlog = new ArrayList<Integer>();
		tweetActivity = 0;
		postsActivityPerWeek = new ArrayList<Integer>();
		commentsActivityPerWeek = new ArrayList<Integer>();
		tweetActivityPerWeek = new ArrayList<Integer>(); 
		timeSpentByActivities = new ArrayList<Activity>();
		timeSpent = 0;
	}
	
	public void initProfile(int blogs, int weeks, int activities){
		for (int i=0; i<blogs; i++){
			postsActivityPerBlog.add(0);
			commentsActivityPerBlog.add(0);
		}
		for (int i=0; i<weeks; i++){
			postsActivityPerWeek.add(0);
			commentsActivityPerWeek.add(0);
			tweetActivityPerWeek.add(0);
		}
		for (int i=0; i<activities; i++){
			Activity a = new Activity();
			a.initActivity(weeks);
			timeSpentByActivities.add(a);
		}
	}
	public void setUsername(String username){
		this.username=username.toLowerCase();
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public ArrayList<Integer> getPostActivity(){
		return this.postsActivityPerBlog;
	}
	
	public ArrayList<Integer> getCommentActivity(){
		return this.commentsActivityPerBlog;
	}
	public int getTimeSpentByActivities(){
		return this.timeSpent;
	}
	public int getTweetActivity(){
		return this.tweetActivity;
	}
	
	/*ArrayList<Integer> postsActivityPerWeek;
	ArrayList<Integer> commentsActivityPerWeek;
	ArrayList<Integer> tweetActivityPerWeek;*/
	public ArrayList<Integer> getPostsActivityPerWeek(){
		return this.postsActivityPerWeek;
	}
	
	public ArrayList<Integer> getTweetsActivityPerWeek(){
		return this.tweetActivityPerWeek;
	}
	
	public ArrayList<Integer> getCommentsActivityPerWeek(){
		return this.commentsActivityPerWeek;
	}	
	
	public ArrayList<Activity> getListTimeSpentByActivity(){
		return this.timeSpentByActivities;
	}
	
	public void addPostBlog(int positionBlog, int week){
		postsActivityPerBlog.set(positionBlog, (postsActivityPerBlog.get(positionBlog)+1));
		postsActivityPerWeek.set(week, (postsActivityPerWeek.get(week)+1));
	}
	
	public void addCommentBlog(int positionBlog, int week){
		commentsActivityPerBlog.set(positionBlog, (commentsActivityPerBlog.get(positionBlog)+1));
		commentsActivityPerWeek.set(week, (commentsActivityPerWeek.get(week)+1));
	}
	
	public void addTweet(int week){
		tweetActivity++;
		tweetActivityPerWeek.set(week, tweetActivityPerWeek.get(week)+1);
	}
	
	public void addTimeSpentByActivity(int week, int activityPosition, int duration){
		timeSpent += duration;
		
		Activity activity = timeSpentByActivities.get(activityPosition);
		if(username.compareTo("stijnadams")==0&&duration<0) System.out.println(activity.getName()+"-"+duration);
		activity.addTimeWeek(week, duration);
		timeSpentByActivities.set(activityPosition, activity);
	}
	
	public void setKey(String togglKey){
		this.togglKey=togglKey;
	}
	
}
