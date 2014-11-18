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
import java.util.Calendar;

import org.be.kuleuven.hci.openbadges.utils.RestClient;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class TestWeSPOTBadgeCreation {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 * @throws JSONException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException, JSONException {
		// TODO Auto-generated method stub
		//RestClient.doPostAuth("http://localhost:8890/rest/badges/wespot/11111111111", "", "1dhpkotcf3skosvso0mel9veq7");
		
		/*String inquiries = RestClient.doGet("http://inquiry.wespot.net/services/api/rest/json/?method=site.inquiries&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8");
		JSONObject jinquiries = new JSONObject(inquiries);
		JSONArray ainquiries = jinquiries.getJSONArray("result");
		for (int i=0;i<ainquiries.length();i++){
			System.out.println(ainquiries.getJSONObject(i).getString("inquiryId"));
			System.out.println(RestClient.doPostAuth("http://openbadges-wespot.appspot.com/rest/badges/wespot/"+ainquiries.getJSONObject(i).getString("inquiryId"), "", "m4llklqcc0i2mk3usfucqdfcg5"));
		}*/
		System.out.println(RestClient.doPostAuth("http://openbadges-wespot.appspot.com/rest/badges/wespot/43219", "", "m4llklqcc0i2mk3usfucqdfcg5"));
		//ystem.out.println(RestClient.doPostAuth("http://openbadges-wespot.appspot.com/rest/badges/context/43219", "", "m4llklqcc0i2mk3usfucqdfcg5"));
	}

}
