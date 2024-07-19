package com.allconnect.gateway;

import org.apache.log4j.Logger;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import static com.allconnect.agentReport.utils.ProcessConfigProperties.EMAIL_FROM;
import static com.allconnect.agentReport.utils.ProcessConfigProperties.EMAIL_SUBJECT_WALMART;
import javax.mail.internet.MimeMessage;

@Controller
public class EmailSendManager {
	private static final Logger log = Logger.getLogger(EmailSendManager.class);
	
	public static void sendMail(String email, JavaMailSender javaMailSender, String content) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(EMAIL_FROM);
			mimeMessageHelper.setTo(email);
			mimeMessageHelper.setSubject(EMAIL_SUBJECT_WALMART);            
			mimeMessageHelper.setText("", content);
			javaMailSender.send(mimeMessageHelper.getMimeMessage());
			log.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			log.error("Exception_In_sendEmail="+e.getMessage());
			e.printStackTrace();
		}
	}
	
}