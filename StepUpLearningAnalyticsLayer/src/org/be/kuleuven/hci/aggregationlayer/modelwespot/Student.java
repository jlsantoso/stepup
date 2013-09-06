package org.be.kuleuven.hci.aggregationlayer.modelwespot;

import java.io.Serializable;
import java.util.ArrayList;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Serialize;


public class Student implements Serializable {
    
	String username;
	ArrayList<Phase> activityPhases;
	
	public Student(){
		activityPhases = new ArrayList<Phase>();
	}	

	public void setUsername(String username){
		this.username=username.toLowerCase();
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public ArrayList<Phase> getActivity(){
		return this.activityPhases;
	}
	
	public void addPhase(Phase phase){
		this.activityPhases.add(phase);
	}
	
	public void addActivityPhase(int positionPhase, int positionsubPhase, int weekPosition){
		System.out.println(positionPhase+"-"+activityPhases.size());
		this.activityPhases.get(positionPhase).addSubphaseActivity(positionsubPhase, weekPosition);
	}
	
}
