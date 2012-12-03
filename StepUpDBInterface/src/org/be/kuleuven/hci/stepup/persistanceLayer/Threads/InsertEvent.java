package org.be.kuleuven.hci.stepup.persistanceLayer.Threads;

import java.text.ParseException;

import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.utils.JSONandEvent;
import org.be.kuleuven.hci.stepup.persistanceLayer.EventPostgreSQL;
import org.json.JSONException;
import org.json.JSONObject;

public class InsertEvent implements Runnable{
	
	JSONObject _event;
	public InsertEvent(JSONObject event){
		_event = event;
	}
	
	public void run(){
		try {
			EventPostgreSQL.insertEvent(JSONandEvent.transformFromJsonToEvent(_event));
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
