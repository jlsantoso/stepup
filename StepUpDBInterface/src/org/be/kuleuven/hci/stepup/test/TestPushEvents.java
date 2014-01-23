package org.be.kuleuven.hci.stepup.test;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpException;
import org.be.kuleuven.hci.stepup.utils.RestClient;

public class TestPushEvents {

	public static void main(String[] args) throws URISyntaxException, HttpException, IOException {
		// TODO Auto-generated method stub
		System.out.println(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/pushEvent", "{ \"username\": \"jlsantos\", \"context\": \"test\", \"verb\": \"test\", \"object\": \"twitter\",\"starttime\": \"2012-12-12 00:22:24 +0100\",\"originalrequest\": {\"value\":\"<p>moo\\\"o\"ooo</p>\"}}"));

	}

}
