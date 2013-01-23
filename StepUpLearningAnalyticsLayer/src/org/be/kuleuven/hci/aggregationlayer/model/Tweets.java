package org.be.kuleuven.hci.aggregationlayer.model;

import java.util.ArrayList;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

public class Tweets {

	int total;
	ArrayList<Blog> blog;
	ArrayList<Integer> tweets;
	ArrayList<Integer> weeks;
	
}
