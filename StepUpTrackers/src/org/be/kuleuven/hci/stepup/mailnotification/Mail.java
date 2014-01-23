package org.be.kuleuven.hci.stepup.mailnotification;

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

	public static void sendmail(String subject, String message) {
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("stepup.hci@gmail.com", "StepUp Notifications"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress("joseluis.santos.cs@gmail.com", "Jose Luis Santos"));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);

        } catch (AddressException e) {
            // ...
        } catch (MessagingException e) {
            // ...
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
