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
		hash.add("gesyrt12");
		
	}
	
	public void registerhashComments(){
		for (int i=0;i<3;i++){
			TwitterHash hashTag = new TwitterHash();
			hashTag.setHash(this.hash.get(i));
			EventGoogleDataStore.insertHashTag(hashTag);
		}		
	}
	
}
