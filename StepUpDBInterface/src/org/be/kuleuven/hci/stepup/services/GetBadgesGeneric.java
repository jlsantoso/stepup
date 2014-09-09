/**
 * 
 */
package org.be.kuleuven.hci.stepup.services;


import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Consumes;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.be.kuleuven.hci.stepup.controller.EventController;
import org.be.kuleuven.hci.stepup.model.Event;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/getBadges")

//Specific service for a specific course

public class GetBadgesGeneric {
	@GET @Path("/{useridentifier}")
	@Produces(MediaType.APPLICATION_JSON)	
	public String getStudents(
			@PathParam("useridentifier") String useridentifier, 
			@DefaultValue("2012-12-12 00:22:24 +0100") @QueryParam("from") String from, 
			@DefaultValue("2015-12-12 00:22:24 +0100") @QueryParam("until") String until, 
			@DefaultValue("0") @QueryParam("pag") int pag) throws JSONException {
		System.out.println(useridentifier+"-"+from+"-"+until+"-"+pag);
		return EventController.getBadges(useridentifier, from, until, pag).toString();
	} 

}

