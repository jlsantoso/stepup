package org.be.kuleuven.hci.openbadges.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


import org.json.JSONException;
import org.json.JSONObject;

public class JSONandEvent{
	
	public static Event transformFromJsonToEvent(JSONObject event) throws JSONException, ParseException{
		Event eventTransformed = new Event();
		eventTransformed.setUsername(event.getString("username"));
		eventTransformed.setVerb(event.getString("verb"));
		eventTransformed.setObject(event.getString("object"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZZ");
		eventTransformed.setStartTime(formatter.parse(event.getString("starttime")+"00"));	
		if (event.has("endtime")) eventTransformed.setEndTime(formatter.parse(event.getString("endtime")+"00"));
		if (event.has("context")) eventTransformed.setContext(event.getString("context"));
		if (event.has("target")) eventTransformed.setTarget(event.getString("target"));
		if (event.has("location")) eventTransformed.setLocation(event.getString("location"));
		eventTransformed.setOriginalRequest(event);
		return eventTransformed;
	}
	
	public static JSONObject transformFromEvemtToJson(Event event) throws JSONException, ParseException{
		JSONObject eventTransformed = new JSONObject();
		eventTransformed.put("username", event.getUsername());
		eventTransformed.put("verb", event.getVerb());
		eventTransformed.put("object", event.getObject());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ZZZZZ");
		eventTransformed.put("starttime", formatter.format(event.getStartTime()));
		if (event.getEndTime()!=null) eventTransformed.put("endtime", event.getEndTime().toString());;
		if (event.getContext()!=null) eventTransformed.put("context", event.getContext());
		if (event.getTarget()!=null) eventTransformed.put("target", event.getTarget());
		if (event.getLocation()!=null) eventTransformed.put("location", event.getLocation());
		eventTransformed.put("originalrequest", event.getOriginalRequest());
		return eventTransformed;
	}
}
