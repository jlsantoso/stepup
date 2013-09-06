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

public class InitChiKul13Course {

	/**
	 * @param args
	 */
	public static Course chikul13(){
		Course course = new Course();
		int blogs = 9;
		course.setName("chikul12");
		Calendar startCourse = Calendar.getInstance();
		startCourse.set(2013, 1, 19, 00, 00, 00);
		course.setStartCourse(startCourse.getTime());
		
		//creating groups
		create1stGroup(course, blogs);
		
		create2ndGroup(course, blogs);

		create3rdGroup(course, blogs);

		create4thGroup(course, blogs);
		System.out.println(course.getGroups().size());
		create5thGroup(course, blogs);
		System.out.println(course.getGroups().size());
		create6thGroup(course, blogs);
		System.out.println(course.getGroups().size());
		create7thGroup(course, blogs);
		System.out.println(course.getGroups().size());
		create8thGroup(course, blogs);
		System.out.println(course.getGroups().size());
		create9thGroup(course, blogs);
		System.out.println(course.getGroups().size());
		
		return course;
	}
	

	static void create9thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("gill_vdb");
		s1.initProfile(blogs, course.getWeeks().size(), 0);
		Student s2 = new Student();
		s2.setUsername("thomasdv2");
		s2.initProfile(blogs, course.getWeeks().size(), 0);
		Student s3 = new Student();
		s3.setUsername("vincent_gv");
		s3.initProfile(blogs, course.getWeeks().size(), 0);

		Group group = new Group();
		group.setName("team TGV");
		group.addStudent(s1);
		group.addStudent(s2);
		group.addStudent(s3);
		Blog blog = new Blog();
		blog.setName("team TGV");
		blog.setUrl("http://teamtgv.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addStudent(s2);
		course.addStudent(s3);
		course.addBlog(blog);
		course.addGroups(group);
	}
	static void create8thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("gertvanwijn");
		s1.initProfile(blogs, course.getWeeks().size(), 0);
		Student s2 = new Student();
		s2.setUsername("laurenssion");
		s2.initProfile(blogs, course.getWeeks().size(), 0);
		Student s3 = new Student();
		s3.setUsername("peterbosmanspb");
		s3.initProfile(blogs, course.getWeeks().size(), 0);

		Group group = new Group();
		group.setName("Team LPG");
		group.addStudent(s1);
		group.addStudent(s2);
		group.addStudent(s3);
		Blog blog = new Blog();
		blog.setName("Team LPG");
		blog.setUrl("http://lpg13.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addStudent(s2);
		course.addStudent(s3);
		course.addBlog(blog);
		course.addGroups(group);
	}
	static void create7thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("pj_verbruggen");
		s1.initProfile(blogs, course.getWeeks().size(), 0);
		Student s2 = new Student();
		s2.setUsername("mercelis");
		s2.initProfile(blogs, course.getWeeks().size(), 0);
		Student s3 = new Student();
		s3.setUsername("srousseeuw");
		s3.initProfile(blogs, course.getWeeks().size(), 0);

		Group group = new Group();
		group.setName("CHInterest");
		group.addStudent(s1);
		group.addStudent(s2);
		group.addStudent(s3);
		Blog blog = new Blog();
		blog.setName("CHInterest");
		blog.setUrl("http://chinterest.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addStudent(s2);
		course.addStudent(s3);
		course.addBlog(blog);
		course.addGroups(group);
		
	}
	static void create6thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("tommekevdb");
		s1.initProfile(blogs, course.getWeeks().size(), 0);
		Student s2 = new Student();
		s2.setUsername("simonchuptys");
		s2.initProfile(blogs, course.getWeeks().size(), 0);
		Student s3 = new Student();
		s3.setUsername("jyyrcddc");
		s3.initProfile(blogs, course.getWeeks().size(), 0);

		Group group = new Group();
		group.setName("JeTS-CHI");
		group.addStudent(s1);
		group.addStudent(s2);
		group.addStudent(s3);
		Blog blog = new Blog();
		blog.setName("JeTS-CHI");
		blog.setUrl("http://jetschi.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addStudent(s2);
		course.addStudent(s3);
		course.addBlog(blog);
		course.addGroups(group);		
	}
	
	static void create5thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("dvargemidis");
		s1.initProfile(blogs, course.getWeeks().size(), 0);
		Student s2 = new Student();
		s2.setUsername("willemmat");
		s2.initProfile(blogs, course.getWeeks().size(), 0);
		Student s3 = new Student();
		s3.setUsername("benjaminwtv");
		s3.initProfile(blogs, course.getWeeks().size(), 0);

		Group group = new Group();
		group.setName("HatCHI");
		group.addStudent(s1);
		group.addStudent(s2);
		group.addStudent(s3);
		Blog blog = new Blog();
		blog.setName("HatCHI");
		blog.setUrl("http://hatchi13.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addStudent(s2);
		course.addStudent(s3);
		course.addBlog(blog);
		course.addGroups(group);	
	}
	
	static void create4thGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("jorisschelfaut");
		s1.initProfile(blogs, course.getWeeks().size(), 0);
		Student s2 = new Student();
		s2.setUsername("woutermoermans");
		s2.initProfile(blogs, course.getWeeks().size(), 0);
		Student s3 = new Student();
		s3.setUsername("kevindelval");
		s3.initProfile(blogs, course.getWeeks().size(), 0);

		Group group = new Group();
		group.setName("IGCHI");
		group.addStudent(s1);
		group.addStudent(s2);
		group.addStudent(s3);
		Blog blog = new Blog();
		blog.setName("IGCHI");
		blog.setUrl("http://igchi.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addStudent(s2);
		course.addStudent(s3);
		course.addBlog(blog);
		course.addGroups(group);
	}
	
	static void create3rdGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("tdbuyser");
		s1.initProfile(blogs, course.getWeeks().size(), 0);
		Student s2 = new Student();
		s2.setUsername("witteveenalex");
		s2.initProfile(blogs, course.getWeeks().size(), 0);
		Student s3 = new Student();
		s3.setUsername("tommpiot");
		s3.initProfile(blogs, course.getWeeks().size(), 0);

		Group group = new Group();
		group.setName("Chi Con Carne");
		group.addStudent(s1);
		group.addStudent(s2);
		group.addStudent(s3);
		Blog blog = new Blog();
		blog.setName("Chi Con Carne");
		blog.setUrl("http://chiconcarne.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addStudent(s2);
		course.addStudent(s3);
		course.addBlog(blog);
		course.addGroups(group);
		System.out.println(course.getGroups().size());
	}
	
	static void create2ndGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("apirlot4kul");
		s1.initProfile(blogs, course.getWeeks().size(), 0);
		Student s2 = new Student();
		s2.setUsername("thomas_demoor");
		s2.initProfile(blogs, course.getWeeks().size(), 0);
		Student s3 = new Student();
		s3.setUsername("sdehaes");
		s3.initProfile(blogs, course.getWeeks().size(), 0);

		Group group = new Group();
		group.setName("Team S-CHI");
		group.addStudent(s1);
		group.addStudent(s2);
		group.addStudent(s3);
		Blog blog = new Blog();
		blog.setName("Team S-CHI");
		blog.setUrl("http://teamschi.wordpress.com");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addStudent(s2);
		course.addStudent(s3);
		course.addBlog(blog);
		course.addGroups(group);
		System.out.println(course.getGroups().size());
	}
	
	static void create1stGroup(Course course, int blogs){
		Student s1 = new Student();
		s1.setUsername("sandervoeten");
		s1.initProfile(blogs, course.getWeeks().size(), 0);
		Student s2 = new Student();
		s2.setUsername("michaelgobbers");
		s2.initProfile(blogs, course.getWeeks().size(), 0);
		Student s3 = new Student();
		s3.setUsername("niktorfs");
		s3.initProfile(blogs, course.getWeeks().size(), 0);

		Group group = new Group();
		group.setName("Chi Movez");
		group.addStudent(s1);
		group.addStudent(s2);
		group.addStudent(s3);
		Blog blog = new Blog();
		blog.setName("Chi Movez");
		blog.setUrl("http://chimovez.wordpress.com/");
		group.addBlog(blog);
		course.addStudent(s1);
		course.addStudent(s2);
		course.addStudent(s3);
		course.addBlog(blog);
		course.addGroups(group);
		System.out.println(course.getGroups().size());
	}
	
}
