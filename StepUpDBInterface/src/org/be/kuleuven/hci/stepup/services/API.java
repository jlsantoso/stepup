package org.be.kuleuven.hci.stepup.services;

import java.util.Calendar;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.be.kuleuven.hci.stepup.controller.EventController;
import org.be.kuleuven.hci.stepup.utils.StepUpConstants;
import org.be.kuleuven.hci.stepup.utils.UnauthorizedException;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/events")

public class API {
	
	private static final Logger log = Logger.getLogger(API.class.getName());
	
	boolean checkAuthorizationKey(String key){
		if (key.compareTo(StepUpConstants.KEY_AUTH)==0)
			 return true;
		else
			return false;
	}
	
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	//The JSON should contain an Open Badge +context field
	// Ex:
	//{ "version": "1.0.0", "name": "Explorer", "image": "http://urlofyourbadge.com/testbadgeimage.png", "description": "Has displayed expertise in hand-made badge creation.", "criteria": "http://etec.hawaii.edu"}
	public String pushevent(@HeaderParam("Authorization") String authorization, String json) {		 
		if (!checkAuthorizationKey(authorization)) throw new UnauthorizedException();
		log.info("Authorized Pushed Event");
		
		return EventController.insertEvent(json);
	}
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/contexts")
	public String getContext(@HeaderParam("Authorization") String authorization, @DefaultValue("1354615295762") @QueryParam("from") String starttime, @DefaultValue("") @QueryParam("to") String endtime){
		
		return "";
	}

}
