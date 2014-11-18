/*******************************************************************************
 * Copyright (c) 2014 KU Leuven
 * This library is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 *  This library is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *  
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this library.  If not, see <http://www.gnu.org/licenses/>.
 *  
 * Contributors:
 *     Jose Luis Santos
 *******************************************************************************/
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
			String badgeResult = RestClient.doPost("http://openbadgesapi.appspot.com/rest/badges/createbadge/"+appkey+"/inquiryidentifier", badge);
			//String badgeId = new JSONObject(badgeResult).getString("id");
			System.out.println("result"+badgeResult);
			String badgeId = "40001";
			//System.out.println("badgeId:"+badgeId);
			//System.out.println(RestClient.doGet("http://openbadgesapi.appspot.com/rest/badges/getbadgesdefinition?id="+badgeId+"&keyapp="+appkey));
			String recipient = "{ \"recipient\": \"earner.name@gmail.com\", \"evidence\": \"http://google.com\"}";
			//System.out.println("Badge: "+RestClient.doPost("http://openbadgesapi.appspot.com/rest/badges/awardbadge/"+appkey+"/"+badgeId, recipient));
			//String award_id = "50001";
			//System.out.println("Awarded Badges: "+RestClient.doGet("http://openbadgesapi.appspot.com/rest/badges/getawardedbadges?keyapp=hhvm2g2fgf9m7tgv3ucl5hdml7&id=50001"));
			//System.out.println(RestClient.doPost("http://openbadgesapi.appspot.com/rest/badges/appkey/testing",""));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} /*catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} */
	}

}
