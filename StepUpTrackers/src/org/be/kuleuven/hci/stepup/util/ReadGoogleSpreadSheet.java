package org.be.kuleuven.hci.stepup.util;

import java.io.IOException;
import java.net.URL;
import java.util.Hashtable;
import java.util.logging.Level;

import com.google.appengine.api.memcache.ErrorHandlers;
import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CustomElementCollection;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.ListFeed;
import com.google.gdata.util.ServiceException;

public class ReadGoogleSpreadSheet {

	public static void read() {
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
	    syncCache.setErrorHandler(ErrorHandlers.getConsistentLogAndContinue(Level.INFO));
		// TODO Auto-generated method stub
		SpreadsheetService service = new SpreadsheetService("com.banshee");
	    try {
	      // Notice that the url ends
	      // with default/public/values.
	      // That wasn't obvious (at least to me)
	      // from the documentation.
	      String urlString = "https://spreadsheets.google.com/feeds/list/0AhROFoj5kwtPdHpPQXB5U2NSYmZCRmR5bHJrWlpFbVE/default/public/values";

	      // turn the string into a URL
	      URL url = new URL(urlString);

	      // You could substitute a cell feed here in place of
	      // the list feed
	      ListFeed feed = service.getFeed(url, ListFeed.class);
	      Hashtable<String,String> matchingusernames = new Hashtable<String,String>();
	      Hashtable<String,String> blogsfeeds = new Hashtable<String,String>();
	      Hashtable<String,String> diigofeeds = new Hashtable<String,String>();
	      Hashtable<String,String> membersGroup = new Hashtable<String,String>();
	      Hashtable<String,String> twitterEmailNotification = new Hashtable<String,String>();
	      Hashtable<String,String> twitterOpenBadges = new Hashtable<String,String>();
	      Hashtable<String,String> twitterusernames = new Hashtable<String,String>();
	      int i = 0;
	      for (ListEntry entry : feed.getEntries()) {
	    	if (checkingCells(entry)){	    	
		        CustomElementCollection elements = entry.getCustomElements();
		        String twitter = elements.getValue("twitter").toLowerCase();
		        String name = elements.getValue("studentname").toLowerCase();
		        String groupname = elements.getValue("groupname").toLowerCase();
		        if (membersGroup.containsKey(groupname)){
		        	String members = membersGroup.get(groupname);
		        	members += twitter+";";
		        	membersGroup.put(groupname,members);
		        }
		        String wordpressblogurl = elements.getValue("wordpressblogurl").toLowerCase();
		        String wordpresspostrssfeed = elements.getValue("wordpresspostrssfeed").toLowerCase();
		        blogsfeeds.put(wordpresspostrssfeed, groupname);
		        String wordpresscommentrssfeed = elements.getValue("wordpresscommentrssfeed").toLowerCase();
		        blogsfeeds.put(wordpresscommentrssfeed, groupname);
		        
		        //Matching usernames
		        twitterusernames.put(twitter, twitter);
		        String diigo = elements.getValue("diigo").toLowerCase();	
		        diigofeeds.put("https://www.diigo.com/rss/user/"+elements.getValue("diigo").toLowerCase(), elements.getValue("diigo").toLowerCase());
		        matchingusernames.put(diigo, twitter);
		        String openbadge = elements.getValue("openbadge").toLowerCase();
		        matchingusernames.put(openbadge,twitter);
		        twitterOpenBadges.put(twitter, openbadge);
		        String emailforstepupnotifications = elements.getValue("emailforstepupnotifications").toLowerCase();   
		        twitterEmailNotification.put(twitter,emailforstepupnotifications);
	    	}
	      }
	      syncCache.put("diigofeeds", diigofeeds);
	      syncCache.put("blogsfeed", blogsfeeds);
	      syncCache.put("matchingusernames", matchingusernames);
	      syncCache.put("membersGroup", membersGroup);
	      syncCache.put("twitterEmailNotification", twitterEmailNotification);
	      syncCache.put("twitterOpenBadges", twitterOpenBadges);
	      syncCache.put("twitterusernames", twitterusernames);
	      
	    } catch (IOException e) {
	      e.printStackTrace();
	    } catch (ServiceException e) {
	      e.printStackTrace();
	    }
	}
	
	static boolean checkingCells(ListEntry entry){
		CustomElementCollection elements = entry.getCustomElements();
        String twitter = elements.getValue("twitter");
        String name = elements.getValue("studentname");
        String groupname = elements.getValue("groupname");
        String wordpressblogurl = elements.getValue("wordpressblogurl");
        String wordpresspostrssfeed = elements.getValue("wordpresspostrssfeed");
        String wordpresscommentrssfeed = elements.getValue("wordpresscommentrssfeed");
        String diigo = elements.getValue("diigo");	
        String openbadge = elements.getValue("openbadge");
        String emailforstepupnotifications = elements.getValue("emailforstepupnotifications");   
        if (twitter!=null&&name!=null&&groupname!=null&&wordpressblogurl!=null&&wordpresspostrssfeed!=null&&wordpresscommentrssfeed!=null&&diigo!=null&&openbadge!=null&&emailforstepupnotifications!=null)
        	return true;
        else 
        	return false;

	}

}
