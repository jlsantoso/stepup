/**
 * 
 */
package org.be.kuleuven.hci.stepup.services;


import java.util.Calendar;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.be.kuleuven.hci.stepup.controller.EventController;
import org.be.kuleuven.hci.stepup.model.Event;
import org.json.JSONObject;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/pushEvent")

//Trackers use this service to push events.
public class PushEvent {
	
	static final Logger logger = Logger.getLogger(PushEvent.class);
	
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getData(String json) {
		BasicConfigurator.configure();
		logger.warn(json);
		//System.out.println("Object: "+json);
		return EventController.insertEvent(json);
	} 
}

