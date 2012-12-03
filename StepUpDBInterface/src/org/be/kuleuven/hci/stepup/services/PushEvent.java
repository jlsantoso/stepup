/**
 * 
 */
package org.be.kuleuven.hci.stepup.services;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.be.kuleuven.hci.stepup.controller.EventController;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/pushEvent")
public class PushEvent {
	@POST 
    @Produces("application/json")
	public String getData(@QueryParam("filter") String json) {	
		return EventController.insertEvent(json);
	} 
}

