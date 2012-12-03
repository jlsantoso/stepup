package org.be.kuleuven.hci.stepup.model.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.be.kuleuven.hci.stepup.model.Event;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONandEvent{
	
	public static Event transformFromJsonToEvent(JSONObject event) throws JSONException, ParseException{
		Event eventTransformed = new Event();
		eventTransformed.setUsername(event.getString("username"));
		eventTransformed.setVerb(event.getString("verb"));
		eventTransformed.setObject(event.getString("object"));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss ZZZZZ");
		eventTransformed.setStartTime(formatter.parse(event.getString("starttime")));	
		if (event.has("endtime")) eventTransformed.setEndTime(formatter.parse(event.getString("endtime")));
		if (event.has("context")) eventTransformed.setContext(event.getString("context"));
		if (event.has("target")) eventTransformed.setTarget(event.getString("target"));
		if (event.has("location")) eventTransformed.setLocation(event.getString("location"));
		eventTransformed.setOriginalRequest(event);
		return eventTransformed;
	}
}
