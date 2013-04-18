/**
 * 
 */
package org.be.kuleuven.hci.openbadges.services;


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

import org.be.kuleuven.hci.openbadges.badges.rules.ChiManualBadges;
import org.json.JSONObject;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/manual")
public class ManualBadges {
	
	@GET @Path("/iteration/{group}/{iteration}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public String manualiteration(@PathParam("group") String group, @PathParam("iteration") String iteration) {	
		ChiManualBadges.iterationBadge(group, iteration);
		return "awarded!";
	} 
	
	@GET @Path("/peopleeval/{group}/{iteration}/{people}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public String peopleEvaluated(@PathParam("group") String group, @PathParam("iteration") String iteration, @PathParam("people") int people) {	
		ChiManualBadges.involvedPeopleEvaluationBadge(group, iteration, people);
		return "awarded!";
	} 
	
	@GET @Path("/improved/{group}/{iteration}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public String improvedSUS(@PathParam("group") String group, @PathParam("iteration") String iteration) {	
		ChiManualBadges.improvedSUSBadge(group, iteration);
		return "awarded!";
	} 
}