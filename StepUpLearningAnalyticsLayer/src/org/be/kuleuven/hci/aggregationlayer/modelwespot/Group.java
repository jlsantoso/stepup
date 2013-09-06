package org.be.kuleuven.hci.aggregationlayer.modelwespot;

import java.io.Serializable;
import java.util.ArrayList;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


public class Group implements Serializable {

    
	ArrayList<Student> students;
	String name;
	
	public Group(){
		students = new ArrayList<Student>();

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
	
	public ArrayList<Student> getStudents(){
		return this.students;
	}
	
}
