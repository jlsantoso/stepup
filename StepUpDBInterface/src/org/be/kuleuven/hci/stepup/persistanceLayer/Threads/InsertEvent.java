package org.be.kuleuven.hci.stepup.persistanceLayer.Threads;

import java.text.ParseException;

import javax.mail.MessagingException;

import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.utils.JSONandEvent;
import org.be.kuleuven.hci.stepup.notifications.SendMail;
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
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.persistanceLayer.Threads.InsertEvent", e.toString()).send();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		} catch (ParseException e) {
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.persistanceLayer.Threads.InsertEvent", e.toString()).send();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (Exception e) {
			try {
				new SendMail("[StepUp][Database] Problem @ org.be.kuleuven.hci.stepup.persistanceLayer.Threads.InsertEvent", e.toString()).send();
			} catch (MessagingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
	}
}
