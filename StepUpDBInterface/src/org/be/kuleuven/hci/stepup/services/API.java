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
import org.be.kuleuven.hci.stepup.controller.EventControllerV2;
import org.be.kuleuven.hci.stepup.utils.StepUpConstants;
import org.be.kuleuven.hci.stepup.utils.UnauthorizedException;

import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/api")

public class API {
	
	private static final Logger log = Logger.getLogger(API.class.getName());
	
	boolean checkAuthorizationKey(String key){
		if (key.compareTo(StepUpConstants.KEY_AUTH)==0) return true;
		else return false;
	}
	
	String cleanMessage(String result){
		if (result.contains("\"subphase\":\"ARLearn\""))
			result = result.replaceAll("\\\\\"", "\"").replaceAll("\"\\{", "{").replaceAll("}\"", "}");
		else{
			result = result.replaceAll("\\\\\\\\\\\\", "\\\\\\\\");
			result = result.replaceAll("\\\\\"", "\"").replaceAll("\"\\{", "{").replaceAll("}\"", "}");
		}
		return result;
	}
	
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/event")
	public String pushevent(@DefaultValue("0") @HeaderParam("Authorization") String authorization, String json) {		 
		if (!checkAuthorizationKey(authorization)) throw new UnauthorizedException();
		log.info("Authorized Pushed Event");		
		return EventControllerV2.insertEvent(json);
	}
	
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/events")
	public String pushArrayEvents(@DefaultValue("0") @HeaderParam("Authorization") String authorization, String json) {		 
		if (!checkAuthorizationKey(authorization)) throw new UnauthorizedException();
		log.info("Authorized Pushed Event");		
		return EventControllerV2.insertEvents(json);
	}
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/course/{course}/students")
	public String getStudents(@DefaultValue("0") @HeaderParam("Authorization") String authorization, @PathParam("course") String course, @DefaultValue("") @QueryParam("from") String from, @DefaultValue("") @QueryParam("to") String to, @DefaultValue("0") @QueryParam("pag") String pag){
		if (!checkAuthorizationKey(authorization)) throw new UnauthorizedException();
		log.info("Authorized Get Students By Course identifier");
		return cleanMessage(EventControllerV2.getStudentsIds(course, from, to, pag));		
	}
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/course/{course}/events")
	public String getEvents(@DefaultValue("0") @HeaderParam("Authorization") String authorization, @PathParam("course") String course, @DefaultValue("") @QueryParam("from") String from, @DefaultValue("") @QueryParam("to") String to, @DefaultValue("0") @QueryParam("pag") String pag){
		if (!checkAuthorizationKey(authorization)) throw new UnauthorizedException();
		log.info("Authorized Get Events By Course identifier");
		return cleanMessage(EventControllerV2.getEventsPerCourseIds(course, from, to, pag));		
	}
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/course/{course}/student/{student}/events")
	public String getEvents(@DefaultValue("0") @HeaderParam("Authorization") String authorization, @PathParam("course") String course, @PathParam("student") String student, @DefaultValue("") @QueryParam("from") String from, @DefaultValue("") @QueryParam("to") String to, @DefaultValue("0") @QueryParam("pag") String pag){
		if (!checkAuthorizationKey(authorization)) throw new UnauthorizedException();
		log.info("Authorized Get Events By Course and Student identifier");
		return cleanMessage(EventControllerV2.getEventsPerStudentsIdsAndCourse(course, student, from, to, pag));		
	}
	
	@GET 
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/course/{course}/verb/{verb}/events")
	public String getEventsByVerb(@DefaultValue("0") @HeaderParam("Authorization") String authorization, @PathParam("course") String course, @PathParam("verb") String verb, @DefaultValue("") @QueryParam("from") String from, @DefaultValue("") @QueryParam("to") String to, @DefaultValue("0") @QueryParam("pag") String pag){
		if (!checkAuthorizationKey(authorization)) throw new UnauthorizedException();
		log.info("Authorized Get Events By Course and Student identifier");
		return cleanMessage(EventControllerV2.getEventsPerCourseIdsAndVerbs(course, verb, from, to, pag));		
	}

}
