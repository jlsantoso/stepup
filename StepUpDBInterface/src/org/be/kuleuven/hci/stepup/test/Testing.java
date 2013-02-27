package org.be.kuleuven.hci.stepup.test;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpException;
import org.be.kuleuven.hci.stepup.utils.RestClient;

public class Testing {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws HttpException 
	 * @throws URISyntaxException 
	 */
	public static void main(String[] args) throws URISyntaxException, HttpException, IOException {
		// TODO Auto-generated method stub
		//System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", "{ \"username\": \"jlsantos\", \"verb\": \"test\", \"object\": \"twitter\",\"starttime\": \"2012-12-12 00:22:24 +0100\"}"));
		//System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context='openBadges'\", \"pag\": \"1\"}"));
		//System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses", "{ \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses/thesis12", "{ \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses/thesis12/gr0uch0", "{ \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses", "{ \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses/openBadges", "{ \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses/openBadges/erik.duval@cs.kuleuven.be", "{ \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select count(*) from event where verb ='tweet' and username='jlsantoso' and context='openBadges'\", \"pag\": \"0\"}"));
		//System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getCourses/chi13", "{ \"pag\": \"0\"}"));
		System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13'\", \"pag\": \"1\"}"));
	}

}
