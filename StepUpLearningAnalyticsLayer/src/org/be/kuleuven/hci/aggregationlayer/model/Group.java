package org.be.kuleuven.hci.aggregationlayer.model;

import java.io.Serializable;
import java.util.ArrayList;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


public class Group implements Serializable {

    
	ArrayList<Student> students;
	ArrayList<Blog> blog;
	String name;
	
	public Group(){
		students = new ArrayList<Student>();
		blog = new ArrayList<Blog>();
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void addStudent(Student s){
		students.add(s);
	}
	
	public void addBlog(Blog b){
		blog.add(b);
	}
	
	public ArrayList<Student> getStudents(){
		return this.students;
	}
	
	public Blog getFirstBlog(){
		return blog.get(0);
	}
}
