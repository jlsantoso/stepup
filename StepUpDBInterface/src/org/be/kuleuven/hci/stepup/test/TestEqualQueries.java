package org.be.kuleuven.hci.stepup.test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.util.ArrayList;


import org.apache.http.HttpException;
import org.be.kuleuven.hci.stepup.model.Event;
import org.be.kuleuven.hci.stepup.model.utils.JSONandEvent;
import org.be.kuleuven.hci.stepup.utils.RestClient;
import org.json.JSONArray;
import org.json.JSONException;

public class TestEqualQueries {

	static ArrayList<String> getResults(){
		// TODO Auto-generated method stub
				int pagination = 0;
				int limit_pagination = 0;
				ArrayList<String> list = new ArrayList<String>();
				try {
					String result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13'\", \"pag\": \""+pagination+"\"}");
					list.add(result);
				//System.out.println("Result: "+result);
				
					JSONArray results = new JSONArray(result);
					for (int i=0; i<results.length();i++){
						//Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
						limit_pagination = Integer.parseInt(results.getJSONObject(i).getString("full_count"));

					}
					boolean empty = false;
					
					while (!empty){
						pagination++;
						result = RestClient.doPost("http://ariadne.cs.kuleuven.be/wespot-dev-ws/rest/getEvents", "{ \"query\": \"select * from event where context='chikul13'\", \"pag\": \""+pagination+"\"}");
						list.add(result);
						//System.out.println("Result:"+result);
						results = new JSONArray(result);
						if (results.length()==0) break;
						/*System.out.println("Results: "+results.length());
						for (int i=0; i<results.length();i++){
							Event event = JSONandEvent.transformFromJsonToEvent(results.getJSONObject(i));
							limit_pagination = Integer.parseInt(results.getJSONObject(i).getString("full_count"));
							System.out.println(JSONandEvent.transformFromEvemtToJson(event));

						}*/
					}
					return list;
					
					//course.printBlogPosition();

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (HttpException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ArrayList array1 = getResults();
		ArrayList array2 = getResults();
		//array2.add("s");
		System.out.println(array1.containsAll(array2));
	}

}
