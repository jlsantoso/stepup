package org.be.kuleuven.hci.openbadges.test;

import java.io.UnsupportedEncodingException;

import org.be.kuleuven.hci.openbadges.utils.RestClient;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class TestUploadBadges {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//{"criteria":"/badges/html5-basic","description":"Well done! You scored 75% on the test.","name":"Passed phase 6!-","image":"/img/badges/wespot_phase6_badge.png","version":"1.0"}
		String badge = "{\"criteria\":\"/badges/html5-basic\",\"description\":\"Well done! You scored 75% on the test.\",\"name\":\"Passed phase 6!-\",\"image\":\"/img/badges/wespot_phase6_badge.png\",\"version\":\"1.0\"}";
		try {
			//JSONObject appkeyjson = new JSONObject(RestClient.doGet("http://openbadgesapi.appspot.com/rest/badges/getauthorizedappkey"));
			//String appkey = appkeyjson.getJSONArray("appkeys").getString(0);
			String appkey = "hhvm2g2fgf9m7tgv3ucl5hdml7";
			System.out.println("key:"+appkey);
			//String badgeResult = RestClient.doPost("http://openbadgesapi.appspot.com/rest/badges/createbadge/"+appkey+"/inquiryidentifier", badge);
			//String badgeId = new JSONObject(badgeResult).getString("id");
			//System.out.println(badgeResult);
			String badgeId = "40001";
			//System.out.println("badgeId:"+badgeId);
			//System.out.println(RestClient.doGet("http://openbadgesapi.appspot.com/rest/badges/getbadgesdefinition?id="+badgeId+"&keyapp="+appkey));
			String recipient = "{ \"recipient\": \"earner.name@gmail.com\", \"evidence\": \"http://google.com\"}";
			//System.out.println("Badge: "+RestClient.doPost("http://openbadgesapi.appspot.com/rest/badges/awardbadge/"+appkey+"/"+badgeId, recipient));
			//String award_id = "50001";
			//System.out.println("Awarded Badges: "+RestClient.doGet("http://openbadgesapi.appspot.com/rest/badges/getawardedbadges?keyapp=hhvm2g2fgf9m7tgv3ucl5hdml7&id=50001"));
			System.out.println(RestClient.doPost("http://openbadgesapi.appspot.com/rest/badges/appkey/testing",""));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	}

}
