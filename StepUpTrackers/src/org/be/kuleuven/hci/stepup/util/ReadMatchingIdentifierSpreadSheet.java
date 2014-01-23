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

public class ReadMatchingIdentifierSpreadSheet {

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
	      String urlString = "https://spreadsheets.google.com/feeds/list/0AhNdznUnSazTdEQ3Y2lvZkRTZlVmS1UwSVdRQW8yWVE/default/public/values";

	      // turn the string into a URL
	      URL url = new URL(urlString);

	      // You could substitute a cell feed here in place of
	      // the list feed
	      ListFeed feed = service.getFeed(url, ListFeed.class);
	      Hashtable<String,String> matchingusernames = new Hashtable<String,String>();
	      
	      int i = 0;
	      for (ListEntry entry : feed.getEntries()) {
	    	  CustomElementCollection elements = entry.getCustomElements();
	    	  matchingusernames.put(elements.getValue("matchedid").toLowerCase(), elements.getValue("twitterid").toLowerCase());
	      }
	      syncCache.put("updatingusernames", matchingusernames);

	    } catch (IOException e) {
	      e.printStackTrace();
	    } catch (ServiceException e) {
	      e.printStackTrace();
	    }
	}
}
