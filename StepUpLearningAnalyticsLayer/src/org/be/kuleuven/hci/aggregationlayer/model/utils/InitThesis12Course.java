package org.be.kuleuven.hci.aggregationlayer.model.utils;

import java.util.Calendar;
import java.util.logging.Level;

import org.be.kuleuven.hci.aggregationlayer.model.Activity;
import org.be.kuleuven.hci.aggregationlayer.model.Blog;
import org.be.kuleuven.hci.aggregationlayer.model.Course;
import org.be.kuleuven.hci.aggregationlayer.model.Group;
import org.be.kuleuven.hci.aggregationlayer.model.Student;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

public class InitThesis12Course {

	/**
	 * @param args
	 */
	public static Course init(){
		//Init course
		Course course = new Course();
		int blogs = 12;
		course.setName("thesis12");
		Calendar startCourse = Calendar.getInstance();
		startCourse.set(2012, 07, 01, 00, 00, 00);
		course.setStartCourse(startCourse.getTime());
		Activity activity = new Activity();
		//activity.initActivity(course.getWeeks().size());
		activity.setName("Evaluatie");
		course.addActivity(activity);
		activity = new Activity();
		//activity.initActivity(course.getWeeks().size());
		activity.setName("Meetings (bv. met begeleider, mede-studenten, etc.)");
		course.addActivity(activity);
		activity = new Activity();
		//activity.initActivity(course.getWeeks().size());
		activity.setName("Lezen (bv. literatuur)");
		course.addActivity(activity);
		activity = new Activity();
		//activity.initActivity(course.getWeeks().size());
		activity.setName("Social Network Actvities (bv.twitter, comments op andere blogs, suggesting papers met tinyarm, etc)");
		course.addActivity(activity);
		activity = new Activity();
		//activity.initActivity(course.getWeeks().size());
		activity.setName("Reflectie (bv opstellen planning, interpretatie resultaten, etc.)");
		course.addActivity(activity);
		activity = new Activity();
		//activity.initActivity(course.getWeeks().size());
		activity.setName("Ontwerp");
		course.addActivity(activity);
		activity = new Activity();
		//activity.initActivity(course.getWeeks().size());
		activity.setName("Mono");
		course.addActivity(activity);
		activity = new Activity();
		//activity.initActivity(course.getWeeks().size());
		activity.setName("Implementatie");
		course.addActivity(activity);
		activity = new Activity();
		//activity.initActivity(course.getWeeks().size());
		activity.setName("Mono4Android");
		course.addActivity(activity);
		activity = new Activity();
		//activity.initActivity(course.getWeeks().size());
		activity.setName("Schrijven (bv. thesistekst, blogposts, verslag, ...)");
		course.addActivity(activity);
		activity = new Activity();
		//activity.initActivity(course.getWeeks().size());
		activity.setName("Presentatie (bv. voorbereiding, geven)");
		course.addActivity(activity);
		
		
		//creating groups
		create1stGroup(course, blogs);
		create2ndGroup(course, blogs);
		create3rdGroup(course, blogs);
		create4thGroup(course, blogs);
		create5thGroup(course, blogs);
		create6thGroup(course, blogs);
		create7thGroup(course, blogs);
		create8thGroup(course, blogs);
		create9thGroup(course, blogs);
		create10thGroup(course, blogs);
		create11thGroup(course, blogs);
		create12thGroup(course, blogs);
		
	    return course;
		
	}
	
	static void create12thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("StijnAdams");
		s1.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
		Group group = new Group();
		group.setName(s1.getUsername());
		group.addStudent(s1);
		Blog blog = new Blog();
		blog.setName(s1.getUsername());
		blog.setUrl("http://apps4leuven.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addBlog(blog);
		course.addGroups(group);
	}
	static void create11thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("BrianGestures");
		s1.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
		Group group = new Group();
		group.setName(s1.getUsername());
		group.addStudent(s1);
		Blog blog = new Blog();
		blog.setName(s1.getUsername());
		blog.setUrl("http://bgestures.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addBlog(blog);
		course.addGroups(group);
	}
	static void create10thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("SanderVanLoock");
		s1.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
		Group group = new Group();
		group.setName(s1.getUsername());
		group.addStudent(s1);
		Blog blog = new Blog();
		blog.setName(s1.getUsername());
		blog.setUrl("http://htmobiel.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addBlog(blog);
		course.addGroups(group);
	}
	static void create9thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("miteyema");
		s1.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
		Group group = new Group();
		group.setName(s1.getUsername());
		group.addStudent(s1);
		Blog blog = new Blog();
		blog.setName(s1.getUsername());
		blog.setUrl("http://htmobiel.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addBlog(blog);
		course.addGroups(group);
	}
	static void create8thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("KevinDelval");
		s1.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
		Group group = new Group();
		group.setName(s1.getUsername());
		group.addStudent(s1);
		Blog blog = new Blog();
		blog.setName(s1.getUsername());
		blog.setUrl("http://kevindelval.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addBlog(blog);
		course.addGroups(group);
	}
	static void create7thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("Phille88");
		s1.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
		Group group = new Group();
		group.setName(s1.getUsername());
		group.addStudent(s1);
		Blog blog = new Blog();
		blog.setName(s1.getUsername());
		blog.setUrl("http://philippedecroock.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addBlog(blog);
		course.addGroups(group);
	}
	static void create6thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("robindecroon");
		s1.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
		Group group = new Group();
		group.setName(s1.getUsername());
		group.addStudent(s1);
		Blog blog = new Blog();
		blog.setName(s1.getUsername());
		blog.setUrl("http://robindecroon.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addBlog(blog);
		course.addGroups(group);
	}
	static void create5thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("s0194975");
		s1.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
		Group group = new Group();
		group.setName(s1.getUsername());
		group.addStudent(s1);
		Blog blog = new Blog();
		blog.setName(s1.getUsername());
		blog.setUrl("http://s0194975.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addBlog(blog);
		course.addGroups(group);
	}
	static void create4thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("JorisSchelfaut");
		s1.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
		Group group = new Group();
		group.setName(s1.getUsername());
		group.addStudent(s1);
		Blog blog = new Blog();
		blog.setName(s1.getUsername());
		blog.setUrl("http://soundsuggest.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addBlog(blog);
		course.addGroups(group);
	}
	static void create3rdGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("bertouttier");
		s1.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
		Group group = new Group();
		group.setName(s1.getUsername());
		group.addStudent(s1);
		Blog blog = new Blog();
		blog.setName(s1.getUsername());
		blog.setUrl("http://thesis12.bertouttier.be/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addBlog(blog);
		course.addGroups(group);
	}
	static void create2ndGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("mstaessen");
		s1.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
		Group group = new Group();
		group.setName(s1.getUsername());
		group.addStudent(s1);
		Blog blog = new Blog();
		blog.setName(s1.getUsername());
		blog.setUrl("http://thesis.mstaessen.be/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addBlog(blog);
		course.addGroups(group);
	}
	
	static void create1stGroup(Course course, int blogs){
		//Init Group of ttk311
			//Students
		Student s1 = new Student();
		s1.setUsername("tommpiot");
		s1.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
		Student s2 = new Student();
		s2.setUsername("kimmti");
		s2.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
		Student s3 = new Student();
		s3.setUsername("ttk311");
		s3.initProfile(blogs, course.getWeeks().size(), course.getNumberActivities());
			//Group
		Group group = new Group();
		group.setName("ttk311");
		group.addStudent(s1);
		group.addStudent(s2);
		group.addStudent(s3);
			//Blog
		Blog blog = new Blog();
		blog.setName("ttk311");
		blog.setUrl("http://ttk311.wordpress.com/");
		group.addBlog(blog);
		//Add Group to the Course
		course.addStudent(s1);
		course.addStudent(s2);
		course.addStudent(s3);
		course.addBlog(blog);
		course.addGroups(group);
	}
	
}
