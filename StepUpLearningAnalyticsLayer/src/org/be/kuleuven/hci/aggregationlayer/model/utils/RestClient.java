package org.be.kuleuven.hci.aggregationlayer.model.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLEncoder;

public class RestClient {
	public static final int HTTP_OK = 200;
	public static final int TIMEOUT = 20000;


	
	public static String doPost(final String urlString, final String POSTText) throws UnsupportedEncodingException{
		String message = URLEncoder.encode(POSTText, "UTF-8");
		BufferedReader rd  = null;
	    StringBuilder sb = null;
	    String line = null;
	    try {
	        URL url = new URL(urlString);
	        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	        connection.setRequestProperty("Content-Type", "application/json");
	        connection.setDoOutput(true);
	        connection.setRequestMethod("POST");

	        OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
	        writer.write(POSTText);
	        writer.close();
	        
	        return read(connection.getInputStream());
	    } catch (MalformedURLException e) {
	        // ...
	    } catch (IOException e) {
	        // ...
	    }
		return "";
	}


	private static String read(InputStream in) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader r = new BufferedReader(new InputStreamReader(in), TIMEOUT);
		for (String line = r.readLine(); line != null; line = r.readLine()) {
			sb.append(line);
		}
		in.close();
		return sb.toString();
	}
}
