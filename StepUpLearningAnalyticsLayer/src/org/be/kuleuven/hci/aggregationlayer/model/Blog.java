package org.be.kuleuven.hci.aggregationlayer.model;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Embed;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;


public class Blog implements Serializable {

    
	String name;
	String url;
	
	public Blog(){
		
	}
	
	public void setName(String name){
		this.name=name;
	}
	
	public void setUrl(String url){
		this.url=url;
	}
	
	public String getName(){
		return this.name;
	}
	
	public String getUrl(){
		return this.url;
	}
}
