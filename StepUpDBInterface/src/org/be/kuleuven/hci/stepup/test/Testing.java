package org.be.kuleuven.hci.stepup.test;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpException;
import org.be.kuleuven.hci.stepup.utils.RestClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Testing {
//Used for testing purposes.
	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws URISyntaxException, HttpException, IOException {
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13'\", \"pag\": \"1\"}"));
		// TODO Auto-generated method stub
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/pushEvent", "{ \"username\": \"jlsantos\", \"verb\": \"test\", \"object\": \"twitter\",\"starttime\": \"2012-12-12 00:22:24 +0100\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getEvents", "{ \"query\": \"select * from event where context='openBadges'\", \"pag\": \"1\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getCourses", "{ \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getCourses/thesis12", "{ \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getCourses/thesis12/gr0uch0", "{ \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getCourses/chikul13/verb/awarded", "{ \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getCourses/openBadges", "{ \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getCourses/openBadges/erik.duval@cs.kuleuven.be", "{ \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getEvents", "{ \"query\": \"select count(*) from event where verb ='tweet' and username='jlsantoso' and context='openBadges'\", \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getCourses/chi13", "{ \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13'\", \"pag\": \"1\"}"));
		/*String reply = RestClient.doGet("https://public-api.wordpress.com/rest/v1/sites/chiconcarne.wordpress.com/posts/");
		System.out.println(reply);
		try {
			JSONObject json = new JSONObject(reply);
			JSONArray posts = json.getJSONArray("posts");
			System.out.println(posts.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getEvents", "{ \"query\": \"select substring(object,8, position('/' in substring(object,8))-1) as url,starttime from event where context='chikul13' and verb='posted' and starttime>'2013-2-19'  and starttime<'2013-3-5' order by starttime asc \", \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13' and DATE(starttime)>='2013-2-19' and DATE(starttime)<='2013-3-5'\", \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getActivityRelatedBadges", "{\"username\":\"gertvanwijn\",\"starttime\":\"2013-03-04 07:59:50 +0000\",\"verb\":\"awarded\",\"context\":\"chikul13\",\"object\":\"+15 tweets badge-0\",\"originalrequest\":{\"startdate\":\"2013-2-19\",\"connotation\":\"positive\",\"badge\":{\"criteria\":\"/badges/html5-basic\",\"issuer\":{\"name\":\"hci-kuleuven\",\"origin\":\"http://openbadges-hci.appspot.com\"},\"description\":\"Well done! You have done 15 tweets! (This badge is assigned biweekly and individually)\",\"name\":\"+15 tweets badge-0\",\"image\":\"/img/badges/pos_tweets_gold.png\",\"version\":\"1.0\"},\"enddate\":\"2013-3-5\",\"type\":\"individual\",\"recipient\":\"gert_vanwijn11@me.com\"}}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13'\", \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getEvents", "{ \"query\": \"select distinct username from event where context='elgg'\", \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://localhost:8888/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13' and originalrequest like '%mercelis%'\", \"pag\": \"0\"}"));
		System.out.println(RestClient.doPost("http://localhost:8888/rest/getCourses/arLearn-fake/google_116743449349920850150/awarded", "{ \"pag\": \"0\"}"));
		
	}

}
