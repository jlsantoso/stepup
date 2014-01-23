package org.be.kuleuven.hci.stepup.util;

import java.util.ArrayList;

import org.be.kuleuven.hci.stepup.model.RssFeeds;
import org.be.kuleuven.hci.stepup.model.TwitterHash;
import org.be.kuleuven.hci.stepup.persistancelayer.EventGoogleDataStore;

public class TwitterHashTags {
	ArrayList<String> hash;
	
	public TwitterHashTags(){
		hash = new ArrayList<String>();
		//hash.add("mume12");
		//hash.add("peno12");
		//hash.add("thesis12");
		//hash.add("gesyrt12");
		hash.add("thesis13");
		hash.add("mume13");
		
	}
	
	public void registerhashComments(){
		for (String hashtag:hash){
			TwitterHash hashTag = new TwitterHash();
			hashTag.setHash(hashtag);
			EventGoogleDataStore.insertHashTag(hashTag);
		}		
	}
	
}
