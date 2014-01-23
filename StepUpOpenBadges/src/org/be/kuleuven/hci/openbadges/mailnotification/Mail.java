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

public class Mail {

	public static void sendmail(String subject, String message, String destination) {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("joseluis.santos.cs@gmail.com", "StepUp Notifications"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(destination, destination));
            //msg.addRecipient(Message.RecipientType.BCC, new InternetAddress("joseluis.santos.cs@gmail.com", "StepUp Notifications"));
            msg.setSubject(subject);
            msg.setText(message+"\n Are you interested who earned the last awarded badges? Check them out at: http://chi13course.appspot.com/#awarded");
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
