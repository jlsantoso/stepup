package org.be.kuleuven.hci.aggregationlayer.modelwespot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.jdo.annotations.Index;

import org.be.kuleuven.hci.aggregationlayer.StepUpConstants;
import org.be.kuleuven.hci.stepup.model.Event;
import org.json.JSONException;
import org.json.JSONObject;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;
@Entity
@Index
public class Course implements Serializable {
	@Id Long id;
	String name;
	Date startcourse;
	Date endcourse;
	@Serialize ArrayList<Integer> weeks;
	@Serialize ArrayList<Group> groups;
	@Serialize Hashtable<String,Integer> groupPosition;
	@Serialize Hashtable<String,Integer> studentPosition;
	@Serialize Hashtable<String,Integer> externalsPosition;
	@Serialize Hashtable<String,Integer> phasePosition;
	@Serialize ArrayList<Phase> phases;
	int sizeGroup, sizeStudent;
	@Serialize ArrayList<Student> students;
	@Serialize ArrayList<Student> externals;
	Date today;
	
	public Course(){
		this.weeks = new ArrayList<Integer>();
		this.students = new ArrayList<Student>();
		this.groups = new ArrayList<Group>();
		this.externals = new ArrayList<Student>();
		this.today = Calendar.getInstance().getTime();
		this.groupPosition = new Hashtable<String,Integer>();
		this.studentPosition = new Hashtable<String,Integer>();
		this.externalsPosition = new Hashtable<String,Integer>();
		this.phasePosition = new Hashtable<String,Integer>();
		this.phases = new ArrayList<Phase>();
		this.sizeGroup = 0; 
		this.sizeStudent = 0;
	}
	
	public void addPhase(Phase phase){
		this.phasePosition.put(phase.getName(), phases.size());
		this.phases.add(phase);
	}
	
	public ArrayList<Phase> getPhases(){
		return this.phases;
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
	
	
	public void addExternal(String username){
		if (!this.externalsPosition.containsKey(username.toLowerCase())){
			if (username.toLowerCase().compareTo("erikduval")==0) System.out.println("Creando Erik como externo");
			int size = this.externals.size();
			this.externalsPosition.put(username.toLowerCase(), size);
			Student s = new Student();
			s.setUsername(username.toLowerCase());
			//s.initProfile(this.blogs.size(), this.weeks.size(), getNumberActivities());
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
	
	
	public void spreadEvent(Event e) throws JSONException{
		Student s; 
		if (studentPosition.containsKey(e.getUsername().toLowerCase())){
			s = students.get(studentPosition.get(e.getUsername().toLowerCase()));
		}else{
			addExternal(e.getUsername().toLowerCase().replaceAll("\\.", ""));
			s = externals.get(externalsPosition.get(e.getUsername().toLowerCase().replaceAll("\\.", "")));
		}
		if (getWeekPosition(e.getStartTime())<0) e.setStartTime(this.startcourse);
		JSONObject context = new JSONObject(e.getContext());
		//System.out.println(getWeekPosition(e.getStartTime()));
		//System.out.println(phasePosition.get(context.get("phase")));
		if (context.getString("phase").compareTo("1")!=0)
			s.addActivityPhase(phasePosition.get(context.get("phase")), phases.get(phasePosition.get(context.get("phase"))).getPositionSubPhase(context.getString("subphase")), getWeekPosition(e.getStartTime()));
		else 
			s.addActivityPhase(Integer.parseInt(context.getString("phase"))-1, 0, getWeekPosition(e.getStartTime()));
	}	
}
