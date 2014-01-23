package org.be.kuleuven.hci.aggregationlayer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.regex.Pattern;

import javax.servlet.http.*;

import org.be.kuleuven.hci.aggregationlayer.model.Blog;
import org.be.kuleuven.hci.aggregationlayer.model.Course;
import org.be.kuleuven.hci.aggregationlayer.model.Group;
import org.be.kuleuven.hci.aggregationlayer.model.Student;
import org.be.kuleuven.hci.aggregationlayer.model.utils.InitChiKul13Course;
import org.be.kuleuven.hci.aggregationlayer.model.utils.InitThesis12Course;
import org.be.kuleuven.hci.aggregationlayer.model.utils.OfyService;
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



@SuppressWarnings("serial")
public class ChiCourseAnalyticsLayerServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		int pagination = 0;
		int limit_pagination = 0;
		String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13'\", \"pag\": \""+pagination+"\"}");
		Course course = InitChiKul13Course.chikul13();
		//System.out.println("Result: "+result);
		try {
			JSONArray results = new JSONArray(result);
			for (int i=0; i<results.length();i++){
				Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
				limit_pagination = Integer.parseInt(results.getJSONObject(i).getString("full_count"));
				EventController.spreadEvent(event, course);
			}
			boolean empty = false;
			
			
			while (!empty){
				pagination++;
				result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13'\", \"pag\": \""+pagination+"\"}");
				//System.out.println("Result:"+result);
				results = new JSONArray(result);
				if (results.length()==0) break;
				//System.out.println("Results: "+results.length());
				for (int i=0; i<results.length();i++){
					Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
					limit_pagination = Integer.parseInt(results.getJSONObject(i).getString("full_count"));
					//System.out.println(JSONandEvent.transformFromEvemtToJson(event));
					EventController.spreadEvent(event, course);
				}
			}
			
			//course.printBlogPosition();
			PersistanceLayer.saveCourse(course, "bigtablechikul13");
			//print_course(course);
			//print_relation_gotcomments_donecomments(course);
			//print_relation_comments_on_other_blogs(course);
			//print_mention_twitter(course);
			print_comment_post_tweet(course);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	void print_course(Course course){
		System.out.println("*********************************Imprimimos tabla--------------------------------------------");
		ArrayList<Blog> blogs = course.getBlogs();
		System.out.print("username");
		for (Blog b: blogs){
			System.out.print(","+b.getName());
		}
		System.out.println();
		ArrayList<Group> groups = course.getGroups();
		for (Group g:groups){
			ArrayList<Student> students = g.getStudents();
			for (Student s: students){
				ArrayList<Integer> commentBlogActivity = s.getCommentActivity();	
				System.out.print(s.getUsername());
				for (Integer i:commentBlogActivity){
					System.out.print(","+i);
				}			
				System.out.println();
			}
		}
	}
	
	void print_comment_post_tweet(Course course){
		System.out.println("*********************************Imprimimos tabla--------------------------------------------");
		ArrayList<Blog> blogs = course.getBlogs();
		System.out.println("students,tweets,posts,comments");

		ArrayList<Group> groups = course.getGroups();
		for (Group g:groups){
			ArrayList<Student> students = g.getStudents();
			for (Student s: students){
				int post=0;
				int comment=0;
				System.out.print(s.getUsername()+","+s.getTweetActivity()+",");
				for (int i=0;i<s.getCommentActivity().size();i++){
					comment+=s.getCommentActivity().get(i);
					post+=s.getPostActivity().get(i)
;				}
				System.out.println(post+","+comment);				
			}
		}
	}
	
	
	
	void print_relation_gotcomments_donecomments(Course course){
		System.out.println("*********************************print_relation_gotcomments_donecomments--------------------------------------------");
		ArrayList<Group> groups = course.getGroups();
		ArrayList<Blog> blogs = course.getBlogs();
		for (int j=0; j < blogs.size(); j++){
			int external_activity_own_blog = 0;
			int external_activity_from_group = 0;
			int own_acticity_own_blog = 0;
			for (Group g:groups){
				ArrayList<Student> students = g.getStudents();
				for (Student s: students){
					ArrayList<Integer> commentBlogActivity = s.getCommentActivity();
					if (g.getFirstBlog().getName() == blogs.get(j).getName()){		
							
						for (int i=0; i<commentBlogActivity.size();i++){
							if (i==j){
								own_acticity_own_blog+=commentBlogActivity.get(i);
							}else{
								external_activity_from_group+=commentBlogActivity.get(i);
							}
						}			
									
					}else{
						external_activity_own_blog+=commentBlogActivity.get(j);						
					}	
				}
			}
			System.out.println(blogs.get(j).getName()+","+own_acticity_own_blog+","+external_activity_own_blog+","+external_activity_from_group);		
		}
	}
	
	void print_relation_comments_on_other_blogs(Course course){
		System.out.println("*********************************print_relation_comments_on_other_blogs--------------------------------------------");
		ArrayList<Group> groups = course.getGroups();
		ArrayList<Blog> blogs = course.getBlogs();
		Hashtable<Integer,String> positionblogHash = new Hashtable<Integer,String>();
		Hashtable<String,Integer> blogpositionHash = new Hashtable<String,Integer>();
		Hashtable<String,ArrayList<String>> blogstudentsHash = new Hashtable<String,ArrayList<String>>();
		for (int j=0; j < blogs.size(); j++){
			positionblogHash.put(j, blogs.get(j).getName());
			blogpositionHash.put(blogs.get(j).getName(), j);
		}
		for (Group g:groups){
			ArrayList<Student> students = g.getStudents();
			ArrayList<String> names = new ArrayList<String>();
			for (Student s: students){
				names.add(s.getUsername());
			}
			blogstudentsHash.put(g.getFirstBlog().getName(), names);
		}
		System.out.println("source,target,value");
		//Person-Blog-source and person-person-source
		/*for (Group g:groups){
			ArrayList<Student> students = g.getStudents();
			for (Student s: students){
				ArrayList<Integer> commentBlogActivity = s.getCommentActivity();
				for (int i=0; i<commentBlogActivity.size();i++){
					if (i != blogpositionHash.get(g.getFirstBlog().getName())&&commentBlogActivity.get(i)>0){
						//ArrayList<String> names = blogstudentsHash.get(positionblogHash.get(i));
						//for (String name:names)
							System.out.println(s.getUsername()+","+positionblogHash.get(i)+","+commentBlogActivity.get(i));
					}										
				}	
			}
		}*/
		for (Group g:groups){
			for (int i=0;i<blogs.size();i++){
				int commentactivity = 0;
				for (int j=0; j<g.getStudents().size();j++){
					commentactivity+=g.getStudents().get(j).getCommentActivity().get(i);
				}
				if (commentactivity>0&&i != blogpositionHash.get(g.getFirstBlog().getName()))
					System.out.println(g.getName()+","+positionblogHash.get(i)+","+commentactivity);
			}
		}
		
	}
	
	void print_mention_twitter(Course course){
		System.out.println("*********************************print_mention_twitter--------------------------------------------");
		ArrayList<Group> groups = course.getGroups();
		ArrayList<Blog> blogs = course.getBlogs();
		System.out.println("source,target,value");
		for (Group g:groups){
			ArrayList<Student> students = g.getStudents();
			for (Student s:students){
				try {
					Hashtable<String, Integer> mentions = new Hashtable<String, Integer>();
					String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13' and originalrequest like '%"+s.getUsername()+"%'\", \"pag\": \"0\"}");
					//System.out.println(result);
					JSONArray results = new JSONArray(result);
					for (int i=0; i<results.length();i++){
						Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
						if (mentions.containsKey(event.getUsername()))
							mentions.put(event.getUsername(), mentions.get(event.getUsername())+1);
						else					
							mentions.put(event.getUsername(), 1);
					}
					Enumeration<String> keys = mentions.keys();
					while(keys.hasMoreElements()){
						String username = keys.nextElement();
						if (s.getUsername().compareTo(username)!=0)
							System.out.println(username+","+s.getUsername()+","+mentions.get(username));
					}
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
			}
		}		
	}
}
