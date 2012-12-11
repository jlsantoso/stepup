package org.be.kuleuven.hci.stepup.persistancelayer;
import com.google.appengine.api.datastore.DatastoreNeedIndexException;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.RssFeeds;
import org.be.kuleuven.hci.stepup.model.TwitterHash;


public class EventGoogleDataStore {
	
	public static void insertEvent(Event event){
	    if (event.getObject().compareTo("tweet")==0) updateLastTwitterId(event);
	    if (event.getObject().compareTo("comment")==0||event.getObject().compareTo("post")==0) updateLastUpdateRss(event.getStartTime());
		OfyService.getOfyService().ofy().save().entity(event); 
	}
	
	public static void insertTwitterHash(TwitterHash twitterHash){
		OfyService.getOfyService().ofy().save().entity(twitterHash); 
	}
	
	public static void insertRssFeeds(RssFeeds rssFeeds){
		OfyService.getOfyService().ofy().save().entity(rssFeeds); 
	}
	
	public static void insertHashTag(TwitterHash twitterHash){
		OfyService.getOfyService().ofy().save().entity(twitterHash); 
	}
	
	public static void clearCache(){
		OfyService.getOfyService().ofy().clear();
	}
	
	public static List<TwitterHash> getTwitterHashTags(){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    if (syncCache.get("rssfeeds")==null){
	    	List<TwitterHash> twitterhashtags = OfyService.getOfyService().ofy().load().type(TwitterHash.class).list();
	    	syncCache.put("twitterhashtags", ( new ArrayList<TwitterHash>(twitterhashtags)));
	    	return twitterhashtags;
	    }else{
	    	return ((List<TwitterHash>)syncCache.get("twitterhashtags"));
	    }
	}
	
	public static List<RssFeeds> getRssFeeds(){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    if (syncCache.get("rssfeeds")==null){
	    	List<RssFeeds> rssFeeds = OfyService.getOfyService().ofy().load().type(RssFeeds.class).list();
	    	syncCache.put("rssfeeds", new ArrayList<RssFeeds>(rssFeeds));
	    	return rssFeeds;
	    }else{
	    	return ((List<RssFeeds>)syncCache.get("rssfeeds"));
	    }
	}
	
	public static String getLastTwitterId(){
		try{
			MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		    if (syncCache.get("lastTwitterId")==null){
		    	Event event = OfyService.getOfyService().ofy().load().type(Event.class).order("-object").filter("verb", "tweet").first().get();
		    	if (event==null){
		    		syncCache.put("lastTwitterId","119719744281640960");
		    		return "119719744281640960";
		    	}
		    	syncCache.put("lastTwitterId", event.getObject());
		    	return event.getObject();
		    }else{
		    	return syncCache.get("lastTwitterId").toString();
		    }
		}catch (DatastoreNeedIndexException e){
			MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
			syncCache.put("lastTwitterId","119719744281640960");
    		return "119719744281640960";
		}
	}
	
	public static Date getLastUpdateRss(){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    if (syncCache.get("lastUpdateRss")==null){
	    	ArrayList<String> verbs = new ArrayList<String>();
	    	verbs.add("comment");
	    	verbs.add("post");
	    	Event event = OfyService.getOfyService().ofy().load().type(Event.class).order("-starttime").filter("verb in", verbs).first().get();
	    	if (event==null){
	    		Calendar lastUpdate = Calendar.getInstance();
	    		lastUpdate.add(Calendar.DAY_OF_MONTH, -90);
	    		return lastUpdate.getTime();
	    	}
	    	syncCache.put("lastUpdateRss", event.getStartTime());
	    	return event.getStartTime();
	    }else{
	    	return ((Date)syncCache.get("lastUpdateRss"));
	    }
	}
	
	public static void updateLastUpdateRss(Date lastUpdateRss){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    if (syncCache.get("lastUpdateRss")!=null){
	    	if (((Date)syncCache.get("lastUpdateRss")).compareTo(lastUpdateRss)<0){
	    		syncCache.put("lastTwitterId",lastUpdateRss);
	    	}
	    }
	}
	
	public static void updateLastTwitterId(Event event){
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
	    if (syncCache.get("lastTwitterId")!=null){
	    	if (Integer.parseInt(syncCache.get("lastTwitterId").toString())<Integer.parseInt(event.getObject())){
	    		syncCache.put("lastTwitterId",event.getObject());
	    	}
	    }
	}
}
