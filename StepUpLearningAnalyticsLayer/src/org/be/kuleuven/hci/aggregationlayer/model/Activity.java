package org.be.kuleuven.hci.aggregationlayer.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


public class Activity implements Serializable{
    
	String name;
	ArrayList<Integer> activityPerWeek;
	
	public Activity(){
		activityPerWeek = new ArrayList<Integer>();
	}
	
	public void initActivity(int weeks){
		for (int i=0; i<weeks; i++){
			activityPerWeek.add(0);
		}
	}
	
	public ArrayList<Integer> getActivityWeek(){
		return this.activityPerWeek;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void addTimeWeek(int weekPosition, int duration){
		activityPerWeek.set(weekPosition, activityPerWeek.get(weekPosition)+duration);
	}

}
