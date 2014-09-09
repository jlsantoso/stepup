/**
 * 
 */
package org.be.kuleuven.hci.stepup.services;


import javax.ws.rs.FormParam;
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
@Path("/getEvents")


public class GetEvents {
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public JSONArray getData(String json) throws JSONException {	
		return EventController.getEvents(json);
	} 
	
	@POST @Path("/{inquiryid}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public String getEventsByInquiryId(@PathParam("inquiryid") String inquiryid, String json) throws JSONException {	
		JSONArray result = EventController.getEventsByInquiryId(inquiryid, json);
		/*if (result.contains("\"subphase\":\"ARLearn\"")){
			result = result.replaceAll("\\\\\"", "\"").replaceAll("\"\\{", "{").replaceAll("}\"", "}");
			JSONArray result_array = new JSONArray(result);
			result = result_array.toString();
		}else{
			result = result.replaceAll("\\\\\\\\\\\\", "\\\\\\\\");
			result = result.replaceAll("\\\\\"", "\"").replaceAll("\"\\{\\\"", "{\\\"").replaceAll("}\"", "}");
			JSONArray result_array = new JSONArray(result);
			result = result_array.toString();
		}*/        
			
		return result.toString();
	} 
}

