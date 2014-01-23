package org.be.kuleuven.hci.stepup.test;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpException;
import org.be.kuleuven.hci.stepup.utils.RestClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestThesisStudents {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			JSONObject total = new JSONObject(RestClient.doGet("http://ariadne.cs.kuleuven.be/stepup/rest/getsingletonbigtable"));
			System.out.println(total.getString("thesis12"));
			JSONArray users = total.getJSONObject("thesis12").getJSONArray("users");
			
			for (int i=0;i<users.length();i++){
				JSONObject user = users.getJSONObject(i);
				JSONArray posts = user.getJSONArray("posts");
				JSONArray comments = user.getJSONArray("comments");
				JSONArray tweets = user.getJSONArray("tweets");
				int total_posts = 0;
				int total_comments = 0;
				int total_tweets = 0;
				for (int j = 0; j<posts.length();j++){
					total_posts += posts.getInt(j);
					total_comments += comments.getInt(j);
					total_tweets += tweets.getInt(j);
				}
				if (total_posts>0&&!user.getString("label").contains("brianges")&&!user.getString("label").contains("noise")){
					JSONArray activity = user.getJSONObject("toggl").getJSONArray("activity");
					long hours = 0;
					for (int j=0; j<activity.length();j++){
						hours += activity.getLong(j);
					}
					hours = hours/3600;
					
					System.out.println(user.getString("label")+": "+hours);
				}
			}
			
			
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
