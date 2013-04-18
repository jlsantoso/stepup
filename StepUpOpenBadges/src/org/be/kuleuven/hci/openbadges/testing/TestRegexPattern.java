package org.be.kuleuven.hci.openbadges.testing;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.be.kuleuven.hci.openbadges.badges.rules.utils.Dates;
import org.be.kuleuven.hci.openbadges.utils.RestClient;

public class TestRegexPattern {


	    public static void main(String[] args) throws UnsupportedEncodingException {
	        String postContent = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select originalRequest from event where verb ='posted' and context='chikul13'\", \"pag\": \"0\"}");
	        System.out.println(postContent);
	        Pattern link = Pattern.compile("<a\\s");
	        Matcher  matcher = link.matcher(postContent);

	        int count = 0;
	        while (matcher.find())
	            count++;

	        System.out.println(count);    // prints 3
	    }
}

