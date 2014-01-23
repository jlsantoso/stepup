package org.be.kuleuven.hci.aggregationlayer.modelwespot;

import java.io.Serializable;
import java.util.ArrayList;

public class Subphase implements Serializable{
	String name;
	int activity;
	ArrayList<Integer> activityperweek;

	
	public Subphase(){
		activityperweek = new ArrayList<Integer>();
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void initweeksactivity(int numberWeeks){
		for (int i=0;i<numberWeeks; i++){
			activityperweek.add(0);
		}
	}
	
	public void addWeekActivity(int weekPosition){
		activity++;
		activityperweek.set(weekPosition, activityperweek.get(weekPosition)+1);
	}
	
	public ArrayList<Integer> getActivityWeeks(){
		return activityperweek;
	}
	
	public int getActivity(){
		return activity;
	}
}
