package org.be.kuleuven.hci.openbadges.mailnotification;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.be.kuleuven.hci.openbadges.utils.RestClient;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

public class Mail {
	
	public static String getEmail(String userId){
		String[] split = userId.split("_");
		String urlString = "http://inquiry.wespot.net/services/api/rest/json/?method=user.email&api_key=27936b77bcb9bb67df2965c6518f37a77a7ab9f8&offset=10&oauthId="+split[1]+"&oauthProvider="+split[0];
		try {
			JSONObject email = new JSONObject(RestClient.doGet(urlString));
			return email.getString("result");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getSubject(){
		String subject = "[weSPOT] You earned a new weSPOT badge!";		
		return subject;
	}
	
	public static String getBodyHTML(String file){		
		String body = "";
		body = "<img src=\"http://openbadges-wespot.appspot.com/badges/"+file+"\"/>";
		return body;		
	}

	public static void sendmail(String subject, String message, String destination) {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("info@wespot.net", "weSPOT Notifications"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(destination, destination));
            msg.addRecipient(Message.RecipientType.BCC, new InternetAddress("joseluis.santos.cs@gmail.com", "weSPOT Notifications"));
            msg.setSubject(subject);
            msg.setContent(message, "text/html");
           // msg.setText(message);
            Transport.send(msg);

        } catch (AddressException e) {
            // ...
        	e.printStackTrace();
        } catch (MessagingException e) {
            // ...
        	e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
