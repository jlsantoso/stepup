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

@Singleton
@Path("/getCourses")
public class GetCoursesData {
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
	
	@POST @Path("/{course}/{student}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public String getStudentsInfo(@PathParam("course") String course, @PathParam("student") String student, String json) {	
		return EventController.getStudent(course, student, json);
	}
	
	@POST @Path("/{course}/{student}/{verb}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)	
	public String getStudentsInfo(@PathParam("course") String course, @PathParam("student") String student, @PathParam("verb") String verb, String json) {	
		return EventController.getStudent(course, student, verb, json);
	}
	
	
}