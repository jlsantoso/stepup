package org.be.kuleuven.hci.test;

import java.io.UnsupportedEncodingException;

import org.be.kuleuven.hci.aggregationlayer.model.utils.RestClient;

public class TestServices {

	/**
	 * @param args
	 * @throws UnsupportedEncodingException 
	 */
	public static void main(String[] args) throws UnsupportedEncodingException {
		// TODO Auto-generated method stub
		String inquiries_URL = "http://wespot.kmi.open.ac.uk/services/api/rest/json/?method=user.inquiries&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&oauthId=105939139551108473521&oauthProvider=google";
		System.out.println(inquiries_URL);
		String result_inquiries = RestClient.doGet(inquiries_URL);
		System.out.println(result_inquiries);
	}

}
