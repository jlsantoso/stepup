package org.be.kuleuven.hci.stepup.test;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpException;

public class Testing {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws URISyntaxException, HttpException, IOException {
		// TODO Auto-generated method stub
		System.out.println(RestClient.doPost("http://localhost:8080/wespot-services/rest/pushEvent", "{ \"username\": \"jlsantos\", \"verb\": \"test\", \"object\": \"twitter\",\"starttime\": \"2012-11-12 00:22:24 +0100\"}"));
		System.out.println(RestClient.doPost("http://localhost:8080/wespot-services/rest/getEvents", "{ \"query\": \"select * from event\", \"pag\": \"0\"}"));
		
	}

}
