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
import org.json.JSONObject;

import com.sun.jersey.spi.resource.Singleton;


//Specific service used and consumed by Navi
@Singleton
@Path("/getCoursesSven")
public class GetCoursesSvenData {
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public String getCourses(String json) {	
		return EventController.getCourses(json);
	} 
	
	@POST @Path("/{course}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public String getStudents(@PathParam("course") String course, String json) {	
		return EventController.getStudents(course, json);
	} 
	

	@POST @Path("/{course}/{verb}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public String getStudentsInfoPerVerb(@PathParam("course") String course, @PathParam("verb") String verb, String json) {	
		return EventController.getStudentPerVerb(course, verb, json);
	}
}