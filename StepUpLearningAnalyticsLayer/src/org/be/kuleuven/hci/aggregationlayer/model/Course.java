package org.be.kuleuven.hci.aggregationlayer.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.jdo.annotations.Index;

import org.be.kuleuven.hci.aggregationlayer.StepUpConstants;
import org.be.kuleuven.hci.stepup.model.Event;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;
@Entity
public class Course implements Serializable {
	@Id Long id;
	@Index String name;
	Date startcourse;
	Date endcourse;
	@Serialize ArrayList<Integer> weeks;
	@Serialize ArrayList<Blog> blogs;
	@Serialize ArrayList<Group> groups;
	@Serialize Hashtable<String,Integer> groupPosition;
	@Serialize Hashtable<String,Integer> studentPosition;
	@Serialize Hashtable<String,Integer> blogPosition;
	@Serialize Hashtable<String,Integer> activitiesPosition;
	@Serialize Hashtable<String,Integer> externalsPosition;
	int sizeGroup, sizeStudent;
	@Serialize ArrayList<Activity> activities;
	@Serialize ArrayList<Student> students;
	@Serialize ArrayList<Student> externals;
	Date today;
	
	public Course(){
		this.weeks = new ArrayList<Integer>();
		this.blogs = new ArrayList<Blog>();
		this.activities = new ArrayList<Activity>();
		this.students = new ArrayList<Student>();
		this.groups = new ArrayList<Group>();
		this.externals = new ArrayList<Student>();
		this.today = Calendar.getInstance().getTime();
		this.groupPosition = new Hashtable<String,Integer>();
		this.studentPosition = new Hashtable<String,Integer>();
		this.blogPosition = new Hashtable<String,Integer>();
		this.activitiesPosition = new Hashtable<String,Integer>();
		this.externalsPosition = new Hashtable<String,Integer>();
		
		this.sizeGroup = 0; 
		this.sizeStudent = 0;
	}
	
	public ArrayList<Blog> getBlogs(){
		return this.blogs;
	}
	
	public ArrayList<Group> getGroups(){
		return this.groups;
	}
	
	public ArrayList<Integer> getWeeks(){
		return this.weeks;
	}
	
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name=name;
	}
	
	
	public void setStartCourse(Date startcourse){
		this.startcourse=startcourse;
		generateWeeks();
	}
	
	public int getNumberActivities(){
		return this.activities.size();
	}
	
	public void generateWeeks(){
		Calendar startOfTheCourse = Calendar.getInstance();
		Calendar today = Calendar.getInstance();
		today.setTime(this.today);
		startOfTheCourse.setTime(this.startcourse);
		int week = 0;
		while(today.after(startOfTheCourse)){
			this.weeks.add(week--);
			today.add(Calendar.DAY_OF_MONTH, -7);
		}
		
	}
	
	public int getWeekPosition(Date date){
		if (this.startcourse.after(date)) return -1;
		long difference = this.today.getTime() - date.getTime();
		return (int)(difference/(1000*60*60*24*7));
		
	}
	
	public ArrayList<Student> getStudents(){
		return this.students;
	}
	
	public void addBlog(Blog b){
		int size = this.blogs.size();
		blogPosition.put(b.getUrl().replaceAll("\\.", "").replaceAll("http:","").replaceAll("/", ""), size);
		this.blogs.add(b);
	}
	
	public void printBlogPosition(){
		Enumeration name = blogPosition.keys();
		Enumeration position = blogPosition.elements();
		while (name.hasMoreElements()){
			System.out.println(position.nextElement()+"-"+name.nextElement());
		}
	}
	
	public void addExternal(String username){
		if (!this.externalsPosition.containsKey(username.toLowerCase())){
			if (username.toLowerCase().compareTo("erikduval")==0) System.out.println("Creando Erik como externo");
			int size = this.externals.size();
			this.externalsPosition.put(username.toLowerCase(), size);
			Student s = new Student();
			s.setUsername(username.toLowerCase());
			s.initProfile(this.blogs.size(), this.weeks.size(), getNumberActivities());
			this.externals.add(s);
		}
	}
	
	public ArrayList<Student> getExternals(){
		return this.externals;
	}
	
	public void addStudent(Student s){
		int size = students.size();
		studentPosition.put(s.getUsername(), size);
		this.students.add(s);
	}
	
	public void addGroups(Group g){
		int size = this.groups.size();
		groupPosition.put(g.getName(), size);
		this.groups.add(g);
	}
	
	public void addActivity(Activity a){
		int size = activities.size();
		activitiesPosition.put(a.getName().replaceAll("\\.", ""), size);
		this.activities.add(a);
	}
	
	public void spreadEvent(Event e){
		Student s; 
		if (studentPosition.containsKey(e.getUsername().toLowerCase())){
			s = students.get(studentPosition.get(e.getUsername().toLowerCase()));
		}else{
			addExternal(e.getUsername().toLowerCase().replaceAll("\\.", ""));
			s = externals.get(externalsPosition.get(e.getUsername().toLowerCase().replaceAll("\\.", "")));
		}
		if (getWeekPosition(e.getStartTime())<0) e.setStartTime(this.startcourse);
		if (e.getVerb().compareTo(StepUpConstants.BLOGCOMMENT)==0){
			String url = e.getObject();
			url = url.substring(0, url.substring(8,url.length()).indexOf("/")+8).replaceAll("\\.", "").replaceAll("http:","").replaceAll("/", "");
			s.addCommentBlog(blogPosition.get(url), getWeekPosition(e.getStartTime()));
		}else if (e.getVerb().compareTo(StepUpConstants.BLOGPOST)==0){
			String url = e.getObject();
			url = url.substring(0, url.substring(8,url.length()).indexOf("/")+8).replaceAll("\\.", "").replaceAll("http:","").replaceAll("/", "");
			s.addPostBlog(blogPosition.get(url), getWeekPosition(e.getStartTime()));
		}else if (e.getVerb().compareTo(StepUpConstants.TWITTER)==0){			
			s.addTweet(getWeekPosition(e.getStartTime()));
		}else if (e.getVerb().compareTo(StepUpConstants.BADGES)==0){			
			s.addBadge(getWeekPosition(e.getStartTime()));
		}else if (e.getVerb().compareTo("spend")==0){
			int duration = (int)((e.getEndTime().getTime()-e.getStartTime().getTime())/1000);
			if (s.getUsername().compareTo("stijnadams")==0&&e.getEndTime().compareTo(e.getStartTime())<0) System.out.println(e.getUsername()+"-"+e.getStartTime()+"-"+e.getEndTime()+"-"+e.getObject());
			s.addTimeSpentByActivity(getWeekPosition(e.getStartTime()), activitiesPosition.get(e.getObject().replaceAll("\\.", "")), duration);
			//if(s.getUsername().compareTo("stijnadams")==0) System.out.println((int)((e.getEndTime().getTime()-e.getStartTime().getTime())/1000)+"-"+e.getEndTime().getTime()+"-"+e.getStartTime().getTime()+activitiesPosition.get(e.getObject().replaceAll("\\.", ""))+"-"+duration);
		}
	}
	
}
