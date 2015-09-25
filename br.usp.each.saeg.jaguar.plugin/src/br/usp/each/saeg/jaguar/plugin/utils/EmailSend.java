package br.usp.each.saeg.jaguar.plugin.utils;

import java.util.Properties;
import java.util.UUID;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSend {
	
	private static Properties mailServerProperties;
	private static Session mailSession;
	private static MimeMessage mailMessage;
	private static MimeMultipart multipart;
	private static MimeBodyPart messageBodyPart;
	private static String SERVER_USER = "hessvicente@gmail.com";
	private static String SERVER_PASSWORD = "Senhadovicente";
	private static String SERVER_SMTP = "smtp.gmail.com";
	private static String SMTP_PORT = "587";
	private static String TO_EMAIL = "higoramario@gmail.com";
	private static String CC_EMAIL = "hessvicente@gmail.com";
	private static String FILENAME = System.getProperty("user.dir") + "/Desktop/.jaguar_commands.log";
	private static DataSource logData;
	
	public static void generateAndSendEmail() throws AddressException, MessagingException{
		
		//setup mail server properties
		mailServerProperties = System.getProperties();
		mailServerProperties.put("mail.smtp.port", SMTP_PORT);
		mailServerProperties.put("mail.smtp.auth", "true");
		mailServerProperties.put("mail.smtp.starttls.enable", "true");
		
		//get mail session
		mailSession = Session.getDefaultInstance(mailServerProperties, null);
		mailMessage = new MimeMessage(mailSession);
		mailMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(TO_EMAIL));
		mailMessage.addRecipient(Message.RecipientType.CC, new InternetAddress(CC_EMAIL));
		mailMessage.setSubject("Jaguar experiment results");
		
		messageBodyPart = new MimeBodyPart();
		messageBodyPart.setText("Jaguar results");
		logData = new FileDataSource(FILENAME);
		messageBodyPart.setDataHandler(new DataHandler(logData));
		messageBodyPart.setFileName(FILENAME.substring(FILENAME.lastIndexOf("/")+1));
		
		multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		
		mailMessage.setContent(multipart);
		
		//send email
		Transport transport = mailSession.getTransport("smtp");
		transport.connect(SERVER_SMTP, SERVER_USER, SERVER_PASSWORD);
		transport.sendMessage(mailMessage, mailMessage.getAllRecipients());
		transport.close();
		
		
	}
	
}
