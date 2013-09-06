package org.be.kuleuven.hci.services;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;

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


import org.be.kuleuven.hci.aggregationlayer.model.Course;
import org.be.kuleuven.hci.aggregationlayer.model.Group;
import org.be.kuleuven.hci.aggregationlayer.model.Student;
import org.be.kuleuven.hci.aggregationlayer.model.utils.PersistanceLayer;
import org.be.kuleuven.hci.aggregationlayer.model.utils.RestClient;
import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.utils.JSONandEvent;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("/getRevealItInfo")


public class GetRevealItInfo {
	
	@GET @Path ("/chikul")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getInfoChikul(@QueryParam("last") String last) {		
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    ArrayList<String> usernameschikul = new ArrayList<String>();
		String result = "";
		result += "status:1"+"\n";
		Course course = PersistanceLayer.getCourse("bigtablechikul13");
		result += "results:"+(course.getStudents().size()*4)+"\n";
		result += "last:1368075603\n";
		ArrayList<Group> groups = course.getGroups();
		for (int i=0;i<groups.size();i++){
			ArrayList<Student> students = groups.get(i).getStudents();
			for (int j=0;j<students.size();j++){
				int comments = 0;
				int posts = 0;
				for (int z=0; z<students.get(j).getCommentActivity().size();z++){
					comments += students.get(j).getCommentActivity().get(z);
				}
				for (int z=0; z<students.get(j).getPostActivity().size();z++){
					posts += students.get(j).getPostActivity().get(z);
				}
				result += 0+":"+students.get(j).getUsername()+":0"+":"+students.get(j).getTweetActivity()+":0\n";
				result += 1+":"+students.get(j).getUsername()+":0"+":"+posts+":0\n";
				result += 2+":"+students.get(j).getUsername()+":0"+":"+comments+":0\n";
				result += 3+":"+students.get(j).getUsername()+":0"+":"+students.get(j).getBadgeActivity()+":0\n";
				usernameschikul.add(students.get(j).getUsername());
			}
			
		}

		syncCache.put("usernameschikul", usernameschikul);
	
		return result;
	}
	
	
		
	@GET @Path ("/group")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getInfo() {
		String color [] = {"FF1994FC", "ffff3333", "ff6666cc", "ff00cc99", "ff00cccc", "ffcccc66", "ffcccc99", "ffffff00", "ff00ffcc", "ffccff00"};
		String result = "";
		Course course = PersistanceLayer.getCourse("bigtablechikul13");
		ArrayList<Group> groups = course.getGroups();
		for (int i=0;i<groups.size();i++){
			result += i+","+groups.get(i).getName()+","+groups.get(i).getName()+","+color[i]+"\n";
		}	
		return result;
	} 
	
	@GET @Path ("/thesis")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getInfoThesis(@QueryParam("last") String last) {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    ArrayList<String> usernames = new ArrayList<String>();
		String result = "";
		result += "status:1"+"\n";
		try {
			JSONObject total = new JSONObject(RestClient.doGet("http://ariadne.cs.kuleuven.be/stepup/rest/getsingletonbigtable"));			
			System.out.println(total.getString("thesis12"));
			JSONArray users = total.getJSONObject("thesis12").getJSONArray("users");
			result += "results:44\n";
			result += "last:1368075603\n";
			for (int i=0;i<users.length();i++){
				JSONObject user = users.getJSONObject(i);
				JSONArray posts = user.getJSONArray("posts");
				JSONArray comments = user.getJSONArray("comments");
				JSONArray tweets = user.getJSONArray("tweets");
				int total_posts = 0;
				int total_comments = 0;
				int total_tweets = 0;
				for (int j = 0; j<posts.length();j++){
					total_posts += posts.getInt(j);
					total_comments += comments.getInt(j);
					total_tweets += tweets.getInt(j);
				}
				if (total_posts>0&&!user.getString("label").contains("brianges")&&!user.getString("label").contains("noise")&&!user.getString("label").contains("kevin")){
					
					JSONArray activity = user.getJSONObject("toggl").getJSONArray("activity");
					long hours = 0;
					for (int j=0; j<activity.length();j++){
						hours += activity.getLong(j);
					}
					hours = hours/(3600*24);
					result += 0+":"+user.getString("label")+":0"+":"+hours+":0\n";
					result += 1+":"+user.getString("label")+":0"+":"+total_posts+":0\n";
					result += 2+":"+user.getString("label")+":0"+":"+total_comments+":0\n";
					result += 3+":"+user.getString("label")+":0"+":"+total_tweets+":0\n";
					usernames.add(user.getString("label"));

				}
			}
			syncCache.put("usernames", usernames);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return result;
	} 
	
	@GET @Path ("/person")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getperson(@QueryParam("last") String last) {
		String color [] = {"FF1994FC", "ffff3333", "ff6666cc", "ff00cc99", "ff00cccc", "ffcccc66", "ffcccc99", "ffffff00", "ff00ffcc", "ffccff00"};
		String result = "";
		Course course = PersistanceLayer.getCourse("bigtablechikul13");
		ArrayList<Group> groups = course.getGroups();
		for (int i=0;i<groups.size();i++){
			result += i+","+groups.get(i).getName()+","+groups.get(i).getName()+","+color[i]+"\n";
		}	
		return "robindecroon";
	}
	
	@GET @Path ("/putperson")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String putperson(@QueryParam("person") String person) {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    syncCache.put("person", person);		
	    Event event = new Event();
	    event.setUsername(person);
	    event.setContext("thesisrevealit");
	    event.setStartTime(Calendar.getInstance().getTime());
	    event.setVerb("isselected");
	    event.setObject("reveal-it");
	    try {
	    	String result = JSONandEvent.transformFromEvemtToJson(event).toString();
	    	result += RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent",result);
			return result;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			return e.toString();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			return e.toString();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return e.toString();
		}
	}
	
	@GET @Path ("/getperson")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getpersons(@QueryParam("last") String last) {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));	    	
		return (String)syncCache.get("person");
	}
	
	@GET @Path ("/putpersonchi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String putpersonchi(@QueryParam("person") String person) {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    syncCache.put("personchi", person);		
	    Event event = new Event();
	    event.setUsername(person);
	    event.setContext("chirevealit");
	    event.setStartTime(Calendar.getInstance().getTime());
	    event.setVerb("isselected");
	    event.setObject("reveal-it");
	    try {
			RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent",JSONandEvent.transformFromEvemtToJson(event).toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ok";
	}
	
	@GET @Path ("/getpersonchi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String getpersonschi(@QueryParam("last") String last) {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));	    	
		return (String)syncCache.get("personchi");
	}
		
}
/*
 //first Badge
	@GET @Path ("/tweets")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String gettweets(@QueryParam("last") String last) {
		String result = "";
		result += "status:1"+"\n";
		Course course = PersistanceLayer.getCourse("bigtablechikul13");
		result += "results:"+course.getStudents().size()+"\n";
		result += "last:1368075603\n";
		ArrayList<Group> groups = course.getGroups();
		for (int i=0;i<groups.size();i++){
			ArrayList<Student> students = groups.get(i).getStudents();
			for (int j=0;j<students.size();j++){
				int comments = 0;
				for (int z=0; z<students.get(j).getCommentActivity().size();z++){
					comments += students.get(j).getCommentActivity().get(z);
				}
				result += i+":"+students.get(j).getUsername()+":0"+":"+students.get(j).getTweetActivity()+":"+comments+"\n";
			}
			
		}
		
		
		return result;
	} 

		@GET @Path ("/comments")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public String getcomments(@QueryParam("last") String last) {
			String result = "";
			result += "status:1"+"\n";
			Course course = PersistanceLayer.getCourse("bigtablechikul13");
			result += "results:"+course.getStudents().size()+"\n";
			result += "last:1368075603\n";
			ArrayList<Group> groups = course.getGroups();
			for (int i=0;i<groups.size();i++){
				ArrayList<Student> students = groups.get(i).getStudents();
				for (int j=0;j<students.size();j++){
					int comments = 0;
					for (int z=0; z<students.get(j).getCommentActivity().size();z++){
						comments += students.get(j).getCommentActivity().get(z);
					}
					result += i+":"+students.get(j).getUsername()+":0"+":"+comments+":"+students.get(j).getTweetActivity()+"\n";
				}
				
			}
			
			
			return result;
		} 
		
		@GET @Path ("/badges")
		@Consumes(MediaType.APPLICATION_JSON)
		@Produces(MediaType.APPLICATION_JSON)
		public String getInfo(@QueryParam("last") String last) {
			String result = "";
			result += "status:1"+"\n";
			Course course = PersistanceLayer.getCourse("bigtablechikul13");
			result += "results:"+course.getStudents().size()+"\n";
			result += "last:1368075603\n";
			ArrayList<Group> groups = course.getGroups();
			for (int i=0;i<groups.size();i++){
				ArrayList<Student> students = groups.get(i).getStudents();
				for (int j=0;j<students.size();j++){
					int comments = 0;
					for (int z=0; z<students.get(j).getCommentActivity().size();z++){
						comments += students.get(j).getCommentActivity().get(z);
					}
					result += i+":"+students.get(j).getUsername()+":0"+":"+students.get(j).getBadgeActivity()+":"+comments+"\n";
				}
				
			}
			
			
			return result;
		} 
 * */
