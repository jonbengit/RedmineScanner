package redmine.scanner;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * For sending the report emails
 * 
 * @author Anil Kurian
 */
public class RedmineScannerMail {

	private List<String> receipients = new ArrayList<String>();

	private String content;

	private String subject;

	public void sendMail() {


		Properties properties = System.getProperties();

		properties.setProperty("mail.smtp.host", ScannerConfig.EMAIL_HOST);

		Session session = Session.getDefaultInstance(properties);

		try {
			MimeMessage message = new MimeMessage(session);
			message.setHeader("Content-Type", "text/html");

			message.setFrom(new InternetAddress(ScannerConfig.EMAIL_FROM));

			for (String receiprient : receipients) {
				message.addRecipient(Message.RecipientType.TO,
						new InternetAddress(receiprient));
			}

			message.setSubject(subject);
			message.setContent(content + getMailFooter(), "text/html");

			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}

	private String getMailFooter() {
		return "<br><br><br><font face='Arial' size=1>This is an auto generated e-mail from Redmine Scanner developed by AGK</font>";
	}

	/**
	 * @param receiprients
	 *            the receiprients to set
	 */
	public void setReceiprients(List<String> receiprients) {
		this.receipients = receiprients;
	}

	public void setContent(String content) {
		this.content = content;
	}

	/**
	 * @param subject
	 *            the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
}