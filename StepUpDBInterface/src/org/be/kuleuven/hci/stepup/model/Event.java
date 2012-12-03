package org.be.kuleuven.hci.stepup.model;

import java.util.Date;

import org.json.JSONObject;

/*CREATE TABLE event
(
  event_id serial NOT NULL,
  username character varying(100) NOT NULL,
  verb character varying(100) NOT NULL,
  starttime timestamp with time zone NOT NULL,
  endtime timestamp with time zone,
  object character varying(300),
  target character varying(300),
  location text,
  context character varying(100),
  original_message text,
  CONSTRAINT event_pkey PRIMARY KEY (event_id )
)
WITH (
  OIDS=FALSE
);
ALTER TABLE event
  OWNER TO bigbro;*/

public class Event {
	
	String _username;
	String _verb;
	Date _starttime;
	Date _endtime;
	String _object;
	String _target;
	String _location;
	String _context;
	JSONObject _originalrequest;
	
	public Event(){
		_username=null;
		_verb=null;
		_starttime=null;
		_endtime=null;
		_object=null;
		_target=null;
		_location=null;
		_context=null;
		_originalrequest=null;
	}
	
	public void setUsername(String username){
		_username=username;
	}
	
	public void setVerb(String verb){
		_verb=verb;
	}
	
	public void setStartTime(Date starttime){
		_starttime=starttime;
	}
	
	public void setEndTime(Date endtime){
		_endtime=endtime;
	}
	
	public void setObject(String object){
		_object=object;
	}
	
	public void setTarget(String target){
		_target=target;
	}
	
	public void setLocation(String location){
		_location=location;
	}
	
	public void setContext(String context){
		_context=context;
	}
	
	public void setOriginalRequest(JSONObject originalrequest){
		_originalrequest=originalrequest;
	}
	
	public String getUsername(){
		return _username;
	}
	
	public String getVerb(){
		return _verb;
	}
	
	public Date getStartTime(){
		return _starttime;
	}
	
	public Date getEndTime(){
		return _endtime;
	}
	
	public String getObject(){
		return _object;
	}
	
	public String getTarget(){
		return _target;
	}
	
	public String getLocation(){
		return _location;
	}
	
	public String getContext(){
		return _context;
	}
	
	public JSONObject getOriginalRequest(){
		return _originalrequest;
	}
	
}
