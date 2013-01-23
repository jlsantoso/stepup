package org.be.kuleuven.hci.stepup.model;

import java.util.Date;

import org.json.JSONObject;

import javax.xml.bind.annotation.XmlRootElement; 


public class Event {
	
	String username;
	String verb;
	Date starttime;
	Date endtime;
	String object;
	String target;
	String location;
	String context;
	JSONObject originalrequest;
	
	public Event(){
		this.username=null;
		this.verb=null;
		this.starttime=null;
		this.endtime=null;
		this.object=null;
		this.target=null;
		this.location=null;
		this.context=null;
		this.originalrequest=null;
	}
	
	public void setUsername(String username){
		this.username=username;
	}
	
	public void setVerb(String verb){
		this.verb=verb;
	}
	
	public void setStartTime(Date starttime){
		this.starttime=starttime;
	}
	
	public void setEndTime(Date endtime){
		this.endtime=endtime;
	}
	
	public void setObject(String object){
		this.object=object;
	}
	
	public void setTarget(String target){
		this.target=target;
	}
	
	public void setLocation(String location){
		this.location=location;
	}
	
	public void setContext(String context){
		this.context=context;
	}
	
	public void setOriginalRequest(JSONObject originalrequest){
		this.originalrequest=originalrequest;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getVerb(){
		return this.verb;
	}
	
	public Date getStartTime(){
		return this.starttime;
	}
	
	public Date getEndTime(){
		return this.endtime;
	}
	
	public String getObject(){
		return this.object;
	}
	
	public String getTarget(){
		return this.target;
	}
	
	public String getLocation(){
		return this.location;
	}
	
	public String getContext(){
		return this.context;
	}
	
	public JSONObject getOriginalRequest(){
		return this.originalrequest;
	}
	
}
