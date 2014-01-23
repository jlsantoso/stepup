package org.be.kuleuven.hci.stepup.util;

import java.util.ArrayList;

import org.be.kuleuven.hci.stepup.model.RssFeeds;
import org.be.kuleuven.hci.stepup.persistancelayer.EventGoogleDataStore;

public class BlogsFeeds {
	ArrayList<String> blogs;
	
	public BlogsFeeds(){
		blogs = new ArrayList<String>();
		
		//blogs.add("http://ttk311.wordpress.com/");
		//blogs.add("http://findingemos.wordpress.com/");
		//blogs.add("http://apps4leuven.wordpress.com/");
		//blogs.add("http://peno3cwa3.wordpress.com/");
		//blogs.add("http://soundsuggest.wordpress.com/");
		//blogs.add("http://kevindelval.wordpress.com/");
		//blogs.add("http://kulcwb3.wordpress.com/");
		//blogs.add("http://penocwb1.wordpress.com/");
		//blogs.add("http://mumemood.wordpress.com/");
		//blogs.add("http://moodifier.wordpress.com/");
		//blogs.add("http://quantifythismume.wordpress.com/");
		//blogs.add("http://htmobiel.wordpress.com/");
		//blogs.add("http://bgestures.wordpress.com/");
		//blogs.add("http://robindecroon.wordpress.com/");
		//blogs.add("http://cwawesome2.wordpress.com/");
		//blogs.add("http://followapp.wordpress.com/");
		//blogs.add("http://philippedecroock.wordpress.com/");
		//blogs.add("http://tkindmoeteennaamhebben.wordpress.com/");
		//blogs.add("http://thesis.mstaessen.be/");
		//blogs.add("http://s0194975.wordpress.com/");
		//blogs.add("http://peno3cwb2.wordpress.com/");
		//blogs.add("http://mume12.wordpress.com/");
		//blogs.add("http://thesis12.bertouttier.be/");
		//blogs.add("http://cwa1dashboard.wordpress.com/");	
		blogs.add("http://peterbosmansthesis.wordpress.com/");
		blogs.add("http://annespersonalhealth.wordpress.com/");
		blogs.add("http://oneironaut.be/");
		blogs.add("http://vrdatavis.wordpress.com/");
		blogs.add("http://thesisquantifiedself.wordpress.com/");
		blogs.add("http://tomdebuyser.wordpress.com/");
		blogs.add("http://wardcools.wordpress.com/");
		blogs.add("http://thesisthomasdemoor.wordpress.com/");
		blogs.add("http://marjoleingeldofaci.wordpress.com/");
		blogs.add("http://williamjens.wordpress.com/");
		blogs.add("http://thesis.raflermitte.be/");
		blogs.add("http://anpirlotaci.wordpress.com/");
		blogs.add("http://michelschevernels.wordpress.com/");
		blogs.add("https://mume13.wordpress.com/");
		blogs.add("http://topija.wordpress.com/");
		blogs.add("https://mumepeg.wordpress.com/");
		blogs.add("https://pestad.wordpress.com/");
		blogs.add("https://mumemamoth.wordpress.com/");
		//blogs.add("");
		
	}
	
	public void registerBlogsComments(){
		String commentPath="comments/feed/";
		for (String blog:blogs){
			RssFeeds rssfeeds = new RssFeeds();
			rssfeeds.setURL(blog+commentPath);
			EventGoogleDataStore.insertRssFeeds(rssfeeds);
		}		
	}
	
	public void registerBlogsPosts(){
		String postPath="feed/";
		for (String blog:blogs){
			RssFeeds rssfeeds = new RssFeeds();
			rssfeeds.setURL(blog+postPath);
			EventGoogleDataStore.insertRssFeeds(rssfeeds);
		}		
	}
}
