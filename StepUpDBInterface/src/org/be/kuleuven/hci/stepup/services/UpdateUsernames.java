/**
 * 
 */
package org.be.kuleuven.hci.stepup.services;


import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.be.kuleuven.hci.stepup.controller.EventController;
import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.persistanceLayer.EventPostgreSQL;
import org.json.JSONObject;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/updateusernames")


public class UpdateUsernames {
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getData(String json) {	
		EventPostgreSQL.updateEvent(json);
		return "ok";
	} 
}

