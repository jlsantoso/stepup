package org.be.kuleuven.hci.openbadges.badges;

import java.io.Serializable;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity

public class Badge implements Serializable{

	@Id Long id;
	@Index String description;
	
	public Badge(){
		
	}
	
	public Long getId(){
		return this.id;
	}
	
	public void setDescription(String description){
		this.description=description;
	}
	
	public String getDescription(){
		return this.description;
	}
	
	
}
