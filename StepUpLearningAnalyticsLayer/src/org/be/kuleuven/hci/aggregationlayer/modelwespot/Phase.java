package org.be.kuleuven.hci.aggregationlayer.modelwespot;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;

import com.googlecode.objectify.annotation.Serialize;

public class Phase implements Serializable {
	
	String name;
	int activity;
	ArrayList<Integer> activityperweek;
	ArrayList<Subphase> subphases;
	Hashtable<String,Integer> subPhasePosition;
	
	public Phase(){
		activityperweek = new ArrayList<Integer>();
		subphases = new ArrayList<Subphase>();
		subPhasePosition = new Hashtable<String,Integer>();
	}
	
	public void initweeksactivity(int numberWeeks){
		for (int i=0;i<numberWeeks; i++){
			activityperweek.add(0);
		}		
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void addWeek(int weekPosition){
		activity++;
		activityperweek.set(weekPosition, activityperweek.get(weekPosition)+1);
	}
	
	public ArrayList<Integer> getActivityWeeks(){
		return activityperweek;
	}
	
	public int getActivity(){
		return activity;
	}
	
	public void addSubphase(Subphase subPhase){
		subPhasePosition.put(subPhase.getName(), subphases.size());
		subphases.add(subPhase);
	}
	
	public ArrayList<Subphase> getSubPhases(){
		return subphases;
	}
	
	int getPositionSubPhase(String subphase){
		if (!subPhasePosition.containsKey(subphase)) System.out.println(this.name+"-Subphase does not exist: "+subphase);;
		return subPhasePosition.get(subphase);
	}
	
	void addSubphaseActivity(int activityPhases, int weekPosition){
		activity++;
		activityperweek.set(weekPosition, activityperweek.get(weekPosition)+1);
		subphases.get(activityPhases).addWeekActivity(weekPosition);
	}
}
