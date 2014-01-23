package org.be.kuleuven.hci.stepup.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;

import org.apache.http.HttpException;
import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.utils.JSONandEvent;
import org.be.kuleuven.hci.stepup.utils.RestClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UpdateBadges {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			JSONArray array = new JSONArray(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select originalrequest from event where verb='awarded' and originalrequest like '%biweekly%' and object like '%tweets%' and context='chikul13'\", \"pag\": \"0\"}"));
			for (int i=0;i<array.length();i++){
				System.out.println(array.getJSONObject(i).getString("originalrequest"));
				Event event = JSONandEvent.transformFromJsonToEvent(new JSONObject(array.getJSONObject(i).getString("originalrequest")));
				String username = event.getUsername();
				JSONObject originalrequest = event.getOriginalRequest();
				//System.out.println("Original request "+originalrequest.toString());
				//System.out.println("{ \"query\": \"select * from event where context='chikul13' and DATE(starttime)>='"+originalrequest.getString("startdate")+"' and DATE(starttime)<='"+originalrequest.getString("enddate")+"'\", \"pag\": \"0\"}");
				JSONArray activity = new JSONArray(RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13' and DATE(starttime)>='"+originalrequest.getString("startdate")+"' and DATE(starttime)<='"+originalrequest.getString("enddate")+"'\", \"pag\": \"0\"}"));
				System.out.println("---------------------------------------------");
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
