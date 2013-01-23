package org.be.kuleuven.hci.aggregationlayer.model;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

public class Comments {

	int total;
	ArrayList<Blog> blog;
	ArrayList<Integer> comments;
	ArrayList<Integer> weeks;

}
