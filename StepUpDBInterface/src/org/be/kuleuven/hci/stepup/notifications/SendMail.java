package org.be.kuleuven.hci.stepup.notifications;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.NoSuchProviderException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

 
public class SendMail {
 
	private String from;
	private String to;
	private String subject;
	private String text;
 
	public SendMail(String from, String to, String subject, String text){
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.text = text;
	}
	
	public SendMail(String subject, String text){
		this.from = "stepup.hci@gmail.com";
		this.to = "joseluis.santos.cs@gmail.com";
		this.subject = subject;
		this.text = text;
	}
	
	public void send() throws MessagingException{
		System.out.println("Sending mail...");
        Properties props = new Properties();
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.host", "smtp.gmail.com");
        props.setProperty("mail.user", from);
        props.setProperty("mail.password", "*******");
        props.put("mail.smtp.port", "587");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
 
		Session mailSession = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, "erikduval01");
			}
		});

        mailSession.setDebug(true);
        Transport transport = mailSession.getTransport();

        MimeMessage message = new MimeMessage(mailSession);
        message.setSubject(subject);
        message.setFrom(new InternetAddress(from));
        message.setContent
          (text, 
           "text/html");
        message.addRecipient(Message.RecipientType.TO,
             new InternetAddress(to));

        transport.connect();
        transport.sendMessage(message,
            message.getRecipients(Message.RecipientType.TO));
        transport.close();
	}
 
	public static void main(String[] args) throws MessagingException {
		 
		SendMail sendmail = new SendMail("stepup.hci@gmail.com", "jlsantoso@gmail.com", "Prueba","Prueba");
		sendmail.send();
		
	}
}

