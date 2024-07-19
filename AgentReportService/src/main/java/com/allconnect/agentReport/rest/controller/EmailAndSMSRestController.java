package com.allconnect.agentReport.rest.controller;

import java.net.URL;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.text.html.HTML;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.ui.Model;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.allconnect.agentReport.utils.Utils;
import com.allconnect.gateway.SMSManager;
import freemarker.template.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

@RestController
@RequestMapping("rest")
public class EmailAndSMSRestController {

	private static final Logger logger = LoggerFactory.getLogger(EmailAndSMSRestController.class);
	private static final String SMS_GOOGLEFIBER_MESSAGE = "Thank you for your interest in setting up Google Fiber service! Click the link below to begin the setup process. http://google.com/fiber/allconnect Msg&data rates may apply. Text 'STOP' to quit.";

	private static final String YTTV = "YouTubeTV";
	private static final String GOOGLE_FIBER = "GoogleFiber";
	private static final String VZ_TELECARTS = "vzTelecarts";
	private static final String COX_TELECARTS = "coxTelecarts";
	private static final String XFINITY_TELECARTS = "xfinityTelecarts";
	private static final String EMAIL_SUBJECT_VZ_TELECARTS = "INSIDE: Your sign-up link for Verizon service";
	private static final String HW_2_10_LINK = "https://www.2-10.com/get-a-quote-rv-demo/?subsrc=RV&sfq=00G6S000007XWLV&utm_source=sales-partner&utm_medium=DTCleads&utm_campaign=rv-demo&utm_content=$lineItemId$&firstName=$first_name$&lastName=$last_name$&phone=$phone$&address=$address_1$&zip=$postal_code$&email=$email$";
	private static final String EMAIL_SUBJECT_2_10_WARRANTY = "INSIDE: Your sign-up link for 2-10 Home Warranty service";
	private static final String EMAIL_SUBJECT_COX_TELECARTS = "INSIDE: Your sign-up link for Cox service";
	private static final String EMAIL_SUBJECT_XFINITY_TELECARTS = "INSIDE: Your sign-up link for Xfinity service";
	private static final String SMS_AC_WALMART_LINK = "https://www.walmart.com/plus/plans?veh=dsn&adid=1500000020090000050898";
	private static final String EMAIL_SUBJECT_GOOGLEFIBER = "INSIDE: Your setup link for Google Fiber";
	private static final String SMS_WALMART_MESSAGE = "Here is the text message you asked to be sent to sign up for a trial of Walmart+. We're excited to offer you a FREE 30-day trial membership of Walmart+. Signing up is easy and takes just 2-3 minutes. Join Walmart+ now to enjoy free shipping, fuel savings, unlimited grocery delivery, and more.";
	private static final String SMS_YTTV_MESSAGE = "Thank you for your interest in setting up YouTube TV service! Click the link below to begin the setup process. https://tv.youtube.com/welcome/partner/allconnect Msg&data rates may apply. Text 'STOP' to quit.";
	private static final String SMS_COX_TELECARTS_MESSAGE = "Thank you for your interest in setting up Cox service! Click the link below to begin the setup process. https://www.allconnect.com/cart-redirect?provider=cox Msg&data rates may apply. Text 'STOP' to quit.";
	private static final String SMS_VZ_TELECARTS_MESSAGE = "Thank you for your interest in setting up Verizon service! Click the link below to begin the setup process. https://www.allconnect.com/cart-redirect?provider=verizon Msg&data rates may apply. Text 'STOP' to quit.";
	private static final String SMS_XFINITY_TELECARTS_MESSAGE = "Thank you for your interest in setting up Xfinity service! Click the link below to begin the setup process. https://www.allconnect.com/cart-redirect?provider=xfinity Msg&data rates may apply. Text 'STOP' to quit.";
	//private static final String SMTP_AUTH_USER = "achauhan@charterglobal.com";
	//private static final String SMTP_AUTH_PWD = "Sama@0007";
	private static final String REPLY_TO = "noreply@allconnect.com";
	private static final String EMAIL_FROM = "jackmalhotra56@gmail.com";
	private static final String EMAIL_SUBJECT_WALMART = "INSIDE: Your sign-up link for Walmart+ trial";
	private static final String EMAIL_SUBJECT_YTTV = "INSIDE: Your setup link for YouTube TV";
	private static final String EMAIL_SUBJECT_TMOBILE = "INSIDE: Your sign-up link for HomeInternet";
	private static final String SMS_WALMART_LINK = "https://www.walmart.com/plus/plans?veh=dsn&adid=1500000020090000050889";
	private static final String EMAIL_SUBJECT_SPECTRUM_TELECARTS = "INSIDE: Your setup link for Spectrum Services";
	private static final String EMAIL_SUBJECT_ATT_TELECARTS = "INSIDE: Your setup link for At&t Services";
	private static final String EMAIL_SUBJECT_EARTHLINK_TELECARTS = "INSIDE: Your setup link for EarthLink Services";
	private static final String EMAIL_SUBJECT_FRONTIER_TELECARTS = "INSIDE: Your setup link for Frontier Services";
	private static final String EMAIL_SUBJECT_HUGHESNET_TELECARTS = "INSIDE: Your setup link for HughesNet Services";
	private static final String HUGHESNET_EMAIL_LINK_URL = "https://hughesnet-cart.hughesnet-dev.redventures.io/cart/hughesnet/";
	private static final String EMAIL_SUBJECT_VIASAT_TELECARTS = "INSIDE: Your setup link for Viasat Services";
	private static final String EMAIL_SUBJECT_CENTURYLINK_PX_TELECARTS = "INSIDE: Your setup link for CenturyLink Services";
	private static final String EMAIL_SUBJECT_DTV_TELECARTS = "INSIDE: Your setup link for DTV Services";
	private static final String EMAIL_SUBJECT_REPORT = "Report Problem";

	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired     
	private Configuration fmConfiguration;
	
	private Map< String, Object > model;
	
	private static Session session;
	
	static {
		logger.info("Inside SMS Read Twilio");
		String smtpHostServer = "localhost";
	    
	    Properties props = System.getProperties();

	    props.put("mail.smtp.host", smtpHostServer);

	    session = Session.getInstance(props, null);

	}

	@GetMapping("sendWalMartLinkSMS")
	public ResponseEntity<Object> sendWalMartLinkSMS(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("referrer") String referrer, @RequestParam("transID") String transID) {
		logger.info("In_sendWalMartLinkSMS="+phoneNumber);
		String message =null;
		if(!Utils.isBlank(referrer)&&referrer.equalsIgnoreCase("Allconnect")) {
			message = SMS_WALMART_MESSAGE+SMS_AC_WALMART_LINK+"&cn="+transID+" Msg&data rates may apply. Text 'STOP' to quit.";
		}
		else {
			message = SMS_WALMART_MESSAGE+SMS_WALMART_LINK+"&cn="+transID+" Msg&data rates may apply. Text 'STOP' to quit.";
		}
		SMSManager.sendSMS(phoneNumber, message);
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendWalMartLinkEmail")
	public ResponseEntity<Object> sendWalMartLinkEmail(@RequestParam("email") String email, @RequestParam("referrer") String referrer, @RequestParam("transID") String transID, Model wmModel) {
		try {
			logger.info("In_sendWalMartLinkForEmail="+email);
			String link = SMS_WALMART_LINK;
			if(!Utils.isBlank(referrer)&&referrer.equalsIgnoreCase("Allconnect")) {
				link = SMS_AC_WALMART_LINK;
			}
			StringBuilder sb = new StringBuilder();
			sb = sb.append(link).append("&cn=").append(transID);
			logger.info("lineItemId="+transID);
			String updatedLink = sb.toString();
			URL walmartUrl=new URL(updatedLink);
			logger.info("updatedWalmartUrl="+walmartUrl);
			wmModel.addAttribute("walmartUrl", walmartUrl);
			sendNewEmail(session, email,EMAIL_SUBJECT_WALMART, getWMContentFromTemplate(wmModel));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendEmail="+e.getMessage());
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendSMS")
	public ResponseEntity<Object> sendSMS(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("provider") String provider) {
		if(provider.equalsIgnoreCase(YTTV)) {
			logger.info("In_sendYTTVLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, SMS_YTTV_MESSAGE);
		}
		else if(provider.equalsIgnoreCase(GOOGLE_FIBER)) {
			logger.info("In_sendGoogleFiberLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, SMS_GOOGLEFIBER_MESSAGE);
		}
		else if(provider.equalsIgnoreCase(VZ_TELECARTS)) {
			logger.info("In_sendVerizonTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, SMS_VZ_TELECARTS_MESSAGE);
		}
		else if(provider.equalsIgnoreCase(COX_TELECARTS)) {
			logger.info("In_sendCoxTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, SMS_COX_TELECARTS_MESSAGE);
		}
		else if(provider.equalsIgnoreCase(XFINITY_TELECARTS)) {
			logger.info("In_sendXfinityTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, SMS_XFINITY_TELECARTS_MESSAGE);
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendEmail")
	public ResponseEntity<Object> sendEmail(@RequestParam("email") String email, @RequestParam("provider") String provider) {
		try {
			if(provider.equalsIgnoreCase(YTTV)) {
				logger.info("In_sendYTTVLinkForEmail="+email);
			    sendNewEmail(session, email,EMAIL_SUBJECT_YTTV, getYTTVContentFromTemplate(model));
				logger.info("Mail_Sent_successfully_for_email="+email);
			}
			else if(provider.equalsIgnoreCase(GOOGLE_FIBER)) {
				logger.info("In_sendGoogleFiberLinkForEmail="+email);
				sendNewEmail(session, email,EMAIL_SUBJECT_GOOGLEFIBER, getGoogleFiberContentFromTemplate(model));
				logger.info("GoogleFiberMail_Sent_successfully_for_email="+email);
			}
		}
		catch (Exception e) {
			logger.error("Exception_In_sendEmail="+e.getMessage());
		}
		return ResponseEntity.ok().body("Success");
	}
	
	public String getGoogleFiberContentFromTemplate(Map < String, Object >model)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("GoogleFiber_Email.ftl"), model));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getGoogleFiberContentFromTemplate="+e.getMessage());
        }
        return content.toString();
    }
	
	public String getYTTVContentFromTemplate(Map < String, Object >model)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("YTTV_Email.ftl"), model));
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
	
	public static void sendEmail(Session session, String toEmail, String subject, String body){
		try
	    {
	      MimeMessage msg = new MimeMessage(session);
	      BodyPart messageBodyPart = new MimeBodyPart();
	      Multipart multipart = new MimeMultipart();
	      //set message headers
	      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
	      msg.addHeader("format", "flowed");
	      msg.addHeader("Content-Transfer-Encoding", "8bit");
	      msg.setFrom(new InternetAddress(EMAIL_FROM, "Allconnect"));
	      msg.setReplyTo(InternetAddress.parse(EMAIL_FROM, false));
	      msg.setSubject(subject, "UTF-8");
	      msg.setSentDate(new Date());
	      msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail, false));
	      
	      messageBodyPart.setContent(Utils.escapeSpecialCharacters(body),"text/HTML");
	      multipart.addBodyPart(messageBodyPart);
	         
	      msg.setContent(multipart);
	      logger.info("Message is ready"+msg.toString());
    	  Transport.send(msg);  
    	  logger.info("EMail Sent Successfully!!");
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	}
	
	public void sendNewEmail(Session session, String toEmail, String subject, String body) {

		try {
			Authenticator auth = new SMTPAuthenticator();
			Properties props = new Properties();
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "25");
			props.put("mail.smtp.auth", "false");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
			session = Session.getDefaultInstance(props,auth);

			MimeMessage msg = new MimeMessage(session);
			BodyPart messageBodyPart = new MimeBodyPart();
			Multipart multipart = new MimeMultipart();

			InternetAddress[] address = { new InternetAddress(toEmail) };

			// set message headers
			msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
			msg.addHeader("format", "flowed");
			msg.addHeader("Content-Transfer-Encoding", "8bit");
			msg.setFrom(new InternetAddress(EMAIL_FROM, "Allconnect"));
			msg.setReplyTo(InternetAddress.parse(REPLY_TO, false));
			msg.setSubject(subject, "UTF-8");
			msg.setSentDate(new Date());
			msg.setRecipients(Message.RecipientType.TO, address);
			messageBodyPart.setContent(Utils.escapeSpecialCharacters(body), "text/HTML");
			multipart.addBodyPart(messageBodyPart);
			msg.setContent(multipart);
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(msg, true);
			mimeMessageHelper.setText("", body);
			logger.info("Message is ready");
			emailSender.send(mimeMessageHelper.getMimeMessage());
			logger.info("EMail Sent Successfully!!");

		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@GetMapping("send210EmailLink")
	public ResponseEntity<Object> send210EmailLink(@RequestParam("lineItemId") String lineItemId,@RequestParam("firstName") String firstName,@RequestParam("lastName") String lastName,
														 @RequestParam("phone") String phone,@RequestParam("address") String address,
														 @RequestParam("zip") String zip,@RequestParam("email") String email,Model model1){	
		try {
			String link = HW_2_10_LINK;
			logger.info("lineItemId="+lineItemId);
			String updatedLink = link.replaceAll("\\$lineItemId\\$", lineItemId).replaceAll("\\$first_name\\$", firstName).replaceAll("\\$last_name\\$", lastName).replaceAll("\\$phone\\$", phone)
				.replaceAll("\\$address_1\\$", address).replaceAll("\\$postal_code\\$", zip).replaceAll("\\$email\\$", email);
			URL updatedUrl=new URL(updatedLink);
			logger.info("210updatedUrl="+updatedUrl);
			model1.addAttribute("updatedUrl", updatedUrl);
			logger.info("In_sendWalMartLinkForEmail="+email);
			sendNewEmail(session, email,EMAIL_SUBJECT_2_10_WARRANTY, getHW210ContentFromTemplate(model1));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendEmail="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendXfinityEmailLink")
	public ResponseEntity<Object> sendXfinityEmailLink(@RequestParam("lineItemId") String lineItemId,@RequestParam("address") String address,@RequestParam("lob") String lob,
														 @RequestParam("zip") String zip, @RequestParam("email") String email, @RequestParam("ucid") String ucid, 
														 @RequestParam("trackingId") String trackingId, @RequestParam("mcid") String mcid,
														 @RequestParam("callbackNumber") String callbackNumber, Model model2){	
		try {
			String link = "https://www.jdoqocy.com/click-100407767-15253831?url=https://www.xfinity.com/learn/landing/partners?";
			//StringBuilder sb = new StringBuilder();
			//sb = sb.append(link).append("lob=").append(lob).append("&StreetAddress=%7B%7B").append(getBase64Value(address)).append("%7D%7D&ZipCode=%7B%7B").append(getBase64Value(zip)).append("%7D%7D&sid=").
			//		append(lineItemId).append("&utm_mcid=").append(mcid).append("&utm=").append("cartbutton").append("&ucid=").append(ucid).append("&trackingId=").append(trackingId);
			String link1 = "https://www.jdoqocy.com/click-100407767-15253831#url=";
			String url = "https://www.xfinity.com/learn/landing/partners#lob="
			+ URLEncoder.encode(lob, "UTF-8") + ";StreetAddress="
			+ URLEncoder.encode(getBase64Value(address.trim()), "UTF-8") + ";ZipCode="
			+ URLEncoder.encode(getBase64Value(zip), "UTF-8") ;

			logger.info("encodedString1="+url);

			link1 = link1 + URLEncoder.encode(url, "UTF-8") +";utm_mcid="
			+ URLEncoder.encode("3576904", "UTF-8") + ";utm="
			+ URLEncoder.encode("cartbutton", "UTF-8") + ";sid="
			+ URLEncoder.encode(lineItemId, "UTF-8");

			logger.info("lineItemId="+lineItemId);
			String updatedLink = link1.toString().replaceAll(" ", "%20");
			URL xfinityUrl=new URL(updatedLink);
			logger.info("updatedXfinityUrl="+xfinityUrl);
			model2.addAttribute("xfinityUrl", xfinityUrl);
			model2.addAttribute("callbackNumber", callbackNumber.replaceAll("-", ""));
			model2.addAttribute("callbackDispNumber", callbackNumber);
			logger.info("In_sendXfinityLinkForEmail="+email);
			sendNewEmail(session, email,EMAIL_SUBJECT_XFINITY_TELECARTS, getXfinityTelecartsContentFromTemplate(model2));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendEmail="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendCoxEmailLink")
	public ResponseEntity<Object> sendCoxEmailLink(@RequestParam("lineItemId") String lineItemId,@RequestParam("address") String address,@RequestParam("address2") String address2,@RequestParam("lob") String lob,
														 @RequestParam("zip") String zip, @RequestParam("email") String email, @RequestParam("ucid") String ucid, @RequestParam("trackingId") String trackingId, 
														 @RequestParam("mcid") String mcid,@RequestParam("callbackNumber") String callbackNumber, Model model2){	
		try {
			String link = "https://www.cox.com/residential-shop/wlscontroller/allconnectcart2#";
			StringBuilder sb = new StringBuilder();
			sb = sb.append(link).append("lob=").append(lob).append(";address=").append(address).append(";zip=").append(zip).append(";apt=").append(address2).append(";ac_id=").
					append(lineItemId).append(";utm_mcid=").append(mcid).append(";utm=").append("cartbutton").append(";ucid=").append(ucid).append(";trackingId=").append(trackingId);
			logger.info("lineItemId="+lineItemId);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			URL coxUrl=new URL(updatedLink);
			logger.info("updatedCoxUrl="+coxUrl);
			model2.addAttribute("coxUrl", coxUrl);
			model2.addAttribute("callbackNumber", callbackNumber.replaceAll("-", ""));
			model2.addAttribute("callbackDispNumber", callbackNumber);
			logger.info("In_sendCoxLinkForEmail="+email);
			sendNewEmail(session, email,EMAIL_SUBJECT_COX_TELECARTS, getCoxTelecartsContentFromTemplate(model2));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendCoxEmail="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendVerizonEmailLink")
	public ResponseEntity<Object> sendVerizonEmailLink(@RequestParam("lineItemId") String lineItemId, @RequestParam("orderId") String orderId,@RequestParam("address") String address,@RequestParam("unitType") String unitType,
			@RequestParam("unitValue") String unitValue,@RequestParam("city") String city,@RequestParam("state") String state,@RequestParam("zip5") String zip5,
			@RequestParam("zip9") String zip9, @RequestParam("zip9or5") String zip9or5,@RequestParam("productId") String productId, @RequestParam("categoryFilter") String categoryFilter,
			@RequestParam("ucid") String ucid, @RequestParam("trackingId") String trackingId,@RequestParam("email") String email, @RequestParam("callbackNumber") String callbackNumber, Model model2){	
		try {
			String link = "https://www.verizon.com/home/#INI=vzw-shop-home-internet;CMP=oth_h_p_rv_oth_acq_99_99_rvdigital;abr=REDCARTALCT;c=A004217;locationCode=Q707501;outletid=135906;";
			StringBuilder sb = new StringBuilder();
			sb = sb.append(link).append("street_line=").append(address).append(";unitType=").append(unitType).append(";unitValue=").append(unitValue).append(";city=").append(city).
					append(";state=").append(state).append(";zip5=").append(zip5).append(";zip9=").append(zip9).append(";zip9or5=").append(zip9or5).append(";cohesionSessionId=").
					append(lineItemId).append(";cohesionTransitId=").append(orderId).append(";productId=").append(productId).append(";categoryFilter=").append(categoryFilter)
					.append(";ucid=").append(ucid).append(";trackingId=").append(trackingId);
			logger.info("lineItemId="+lineItemId);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			URL vzUrl=new URL(updatedLink);
			logger.info("updatedVerizonUrl="+vzUrl);
			model2.addAttribute("verizonUrl", vzUrl);
			model2.addAttribute("callbackNumber", callbackNumber.replaceAll("-", ""));
			model2.addAttribute("callbackDispNumber", callbackNumber);
			logger.info("In_sendVerizonLinkForEmail="+email);
			sendNewEmail(session, email,EMAIL_SUBJECT_VZ_TELECARTS, getVZTelecartsContentFromTemplate(model2));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendVerizonEmail="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendSpectrumEmailLink")
	public ResponseEntity<Object> sendSpectrumEmailLink(@RequestParam("lineItemId") String lineItemId,@RequestParam("address") String address,@RequestParam("city") String city,
			@RequestParam("moving") String moving,@RequestParam("state") String state,@RequestParam("zip") String zip,@RequestParam("trackingId") String trackingId,
			@RequestParam("email") String email,@RequestParam("callbackNumber") String callbackNumber, Model model2){	
		try {
			String link = "https://etail.spectrum.com/address/localization.html#v=ALLCON;";
			StringBuilder sb = new StringBuilder();
			sb = sb.append(link).append("a=").append(address).append(";addrCity=").append(city).append(";addrState=").append(state).append(";z=").append(zip).append(";moving=").append(moving)
					.append(";TransID=").append(lineItemId).append(";offcmp=PSF%2CNTP;affpn=1-833-579-0038");
			logger.info("lineItemId="+lineItemId);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			URL spUrl=new URL(updatedLink);
			logger.info("updatedSpectrumUrl="+spUrl);
			model2.addAttribute("spectrumUrl", spUrl);
			model2.addAttribute("callbackNumber", callbackNumber.replaceAll("-", ""));
			model2.addAttribute("callbackDispNumber", callbackNumber);
			logger.info("In_sendSpectrumLinkForEmail="+email);
			sendNewEmail(session, email,EMAIL_SUBJECT_SPECTRUM_TELECARTS, getSpectrumTelecartsContentFromTemplate(model2));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendSpectrumEmail="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	

	@GetMapping("sendATTEmailLink")
	public ResponseEntity<Object> sendATTEmailLink(@RequestParam("SID") String lineItemId,@RequestParam("streetAddress") String address,@RequestParam("zip") String zip,
			 @RequestParam("aptUnitNum") String unitValue,@RequestParam("utm") String utm,@RequestParam("utm_mcid") String utm_mcid,@RequestParam("source") String source,
			@RequestParam("email") String email,@RequestParam("callbackNumber") String callbackNumber, Model model3){
		try {
			String link = "https://www.att.com/internet/fiber/#key=8W3bMYN8mfHXcnMS;";
			 StringBuilder sb = new StringBuilder();
			 sb = sb.append(link).append("SID=").append(lineItemId).append(";streetAddress=").append(address)
					 .append(";zip=").append(zip).append(";aptUnitNum=").append(unitValue).append(";utm=").append(utm).append(";utm_mcid=")
					 .append(utm_mcid).append(";source=").append(source);
			logger.info("lineItemId="+lineItemId);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			URL attUrl=new URL(updatedLink);
			logger.info("updatedATTUrl="+attUrl);
			model3.addAttribute("atntUrl", attUrl);
			model3.addAttribute("callbackNumber", callbackNumber.replaceAll("-", ""));
			model3.addAttribute("callbackDispNumber", callbackNumber);
			logger.info("In_sendATTLinkForEmail="+email);
			sendNewEmail(session, email,EMAIL_SUBJECT_ATT_TELECARTS, getATTTelecartsContentFromTemplate(model3));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendATTEmail="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendDTVEmailLink")
	public ResponseEntity<Object> sendDTVEmailLink(@RequestParam("SID") String lineItemId,@RequestParam("streetAddress") String address,@RequestParam("zip") String zip,
			 @RequestParam("aptUnitNum") String unitValue,@RequestParam("utm") String utm,@RequestParam("utm_mcid") String utm_mcid,@RequestParam("source") String source,
			@RequestParam("email") String email,@RequestParam("callbackNumber") String callbackNumber, Model model13){
		try {
			String link = "https://www.att.com/internet/fiber/#key=8W3bMYN8mfHXcnMS;";
			 StringBuilder sb = new StringBuilder();
			 sb = sb.append(link).append("SID=").append(lineItemId).append(";streetAddress=").append(address)
					 .append(";zip=").append(zip).append(";aptUnitNum=").append(unitValue).append(";utm=").append(utm).append(";utm_mcid=")
					 .append(utm_mcid).append(";source=").append(source);
			logger.info("lineItemId="+lineItemId);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			URL dtvUrl=new URL(updatedLink);
			logger.info("updatedDTVUrl="+dtvUrl);
			model13.addAttribute("dtvUrl", dtvUrl);
			model13.addAttribute("callbackNumber", callbackNumber.replaceAll("-", ""));
			model13.addAttribute("callbackDispNumber", callbackNumber);
			logger.info("In_sendDTVLinkForEmail="+email);
			sendNewEmail(session, email,EMAIL_SUBJECT_DTV_TELECARTS, getDTVTelecartsContentFromTemplate(model13));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendDTVEmail="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendEarthLinkEmailLink")
	public ResponseEntity<Object> sendEarthLinkEmailLink(@RequestParam("address") String address,@RequestParam("city") String city,@RequestParam("state") String state,
			 @RequestParam("zip") String zip,@RequestParam("unittype") String unittype,@RequestParam("unitvalue") String unitvalue,@RequestParam("acaid") String acaid,
			 @RequestParam("acsid") String acsid,@RequestParam("Euid") String Euid,@RequestParam("utm_mcid") String utm_mcid,@RequestParam("utm") String utm,
			 @RequestParam("promocode") String promocode,@RequestParam("email") String email,@RequestParam("callbackNumber") String callbackNumber, Model model4){
		try {
			String link = "https://checkout.earthlink.com/#/#";
			 StringBuilder sb = new StringBuilder();
			 sb = sb.append(link).append("acaddress=").append(address).append(";accity=").append(city).append(";acstate=").append(state).append(";aczip=")
					 .append(zip).append(";acunittype=").append(unittype).append(";acunitvalue=").append(unitvalue).append(";acaid=").append(acaid)
					 .append(";acsid=").append(acsid).append(";Euid=").append(Euid).append(";utm_mcid=").append(utm_mcid).append(";utm=")
					 .append(utm).append(";promocode=").append(promocode).append(";email=").append(email);
			logger.info("lineItemId="+acsid);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			URL earthLinkUrl=new URL(updatedLink);
			logger.info("updatedEarthlinkUrl="+earthLinkUrl);
			model4.addAttribute("earthLinkUrl", earthLinkUrl);
			model4.addAttribute("callbackNumber", callbackNumber.replaceAll("-", ""));
			model4.addAttribute("callbackDispNumber", callbackNumber);
			logger.info("In_sendEarthlinkLinkForEmail="+email);
			sendNewEmail(session, email,EMAIL_SUBJECT_EARTHLINK_TELECARTS, getEarthlinkTelecartsContentFromTemplate(model4));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendEarthLinkEmail="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	

	@GetMapping("sendFrontierEmailLink")
	public ResponseEntity<Object> sendFrontierEmailLink(@RequestParam("utm") String utm,@RequestParam("utm_mcid") String utm_mcid,
			@RequestParam("ref_sess") String ref_sess,@RequestParam("lineItemId") String lineItemId,@RequestParam("email") String email,@RequestParam("callbackNumber") String callbackNumber,Model model5){
		try {
			String link = "https://www.allconnect.com/cart/frontier/#";
			 StringBuilder sb = new StringBuilder();
			 sb = sb.append(link).append("utm_mcid=").append(utm_mcid).append(";utm=").append(utm).append(";ref_sess=").append(ref_sess)
					 .append(";transit_id=").append(lineItemId);
			logger.info("lineItemId="+lineItemId);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			URL fontierUrl=new URL(updatedLink);
			logger.info("updatedfontierUrl="+fontierUrl);
			model5.addAttribute("fontierUrl", fontierUrl);
			model5.addAttribute("callbackNumber", callbackNumber.replaceAll("-", ""));
			model5.addAttribute("callbackDispNumber", callbackNumber);
			logger.info("In_sendFrontierLinkForEmail="+email);
			sendNewEmail(session, email,EMAIL_SUBJECT_FRONTIER_TELECARTS, getFrontierTelecartsContentFromTemplate(model5));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendFrontierEmail="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	

	@GetMapping("sendHughesNetEmailLink")
	public ResponseEntity<Object> sendHughesNetEmailLink(@RequestParam("utm") String utm,@RequestParam("utm_mcid") String utm_mcid,
			@RequestParam("ref_sess") String ref_sess,@RequestParam("lineItemId") String lineItemId,@RequestParam("email") String email,@RequestParam("callbackNumber") String callbackNumber,Model model6){
		try {
			String link = HUGHESNET_EMAIL_LINK_URL+"#";
			 StringBuilder sb = new StringBuilder();
			 sb = sb.append(link).append("utm=").append(utm).append(";utm_mcid=").append(utm_mcid).append(";ref_sess=").append(ref_sess)
					 .append(";transit_id=").append(lineItemId);
			logger.info("lineItemId="+lineItemId);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			URL hughesNetUrl=new URL(updatedLink);
			logger.info("updatedHughesNetUrl="+hughesNetUrl);
			model6.addAttribute("hughesNetUrl", hughesNetUrl);
			model6.addAttribute("callbackNumber", callbackNumber.replaceAll("-", ""));
			model6.addAttribute("callbackDispNumber", callbackNumber);
			logger.info("In_sendHughesNetLinkForEmail="+email);
			sendNewEmail(session, email,EMAIL_SUBJECT_HUGHESNET_TELECARTS, getHughesNetTelecartsContentFromTemplate(model6));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendFrontierEmail="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	
	@GetMapping("sendViasatEmailLink")
	public ResponseEntity<Object> sendViasatEmailLink(@RequestParam("address") String address,@RequestParam("city") String city,
			@RequestParam("state") String state,@RequestParam("zip") String zip,@RequestParam("countryCode") String countryCode,
			@RequestParam("customerType") String customerType,@RequestParam("affiliateID") String affiliateID,
			@RequestParam("sessionID") String sessionID,@RequestParam("did") String did,@RequestParam("paid") String paid,
			@RequestParam("cid") String cid,@RequestParam("email") String email,@RequestParam("callbackNumber") String callbackNumber,Model model7){
		try {
			String link = "https://buy.viasat.com/en-US/residential/#";
			StringBuilder sb = new StringBuilder();
			sb = sb.append(link).append("addressLine1=").append(address).append(";municipality=").append(city).append(";region=").append(state)
				.append(";postalCode=").append(zip).append(";countryCode=").append(countryCode).append(";customerType=").append(customerType).append(";affiliateID=")
				.append(affiliateID).append(";sessionID=").append(sessionID).append(";DID=").append(did).append(";PAID=").append(paid).append(";CID=").append(cid);
			logger.info("LineItemExternalId="+sessionID);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			URL viasatUrl=new URL(updatedLink);
			logger.info("updatedViasatNetUrl="+viasatUrl);
			model7.addAttribute("viasatUrl", viasatUrl);
			model7.addAttribute("callbackNumber", callbackNumber.replaceAll("-", ""));
			model7.addAttribute("callbackDispNumber", callbackNumber);
			logger.info("In_sendViasatLinkForEmail="+email);
			sendNewEmail(session, email,EMAIL_SUBJECT_VIASAT_TELECARTS, getViasatTelecartsContentFromTemplate(model7));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendViasatEmail="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendCenturyLinkPxEmailLink")
	public ResponseEntity<Object> sendCenturyLinkPxEmailLink(@RequestParam("acfulladdress") String acfulladdress,
			@RequestParam("email") String email,Model model8){
		try {
			String link = "https://www.centurylink.com/internet/?";
			StringBuilder sb = new StringBuilder();
			sb = sb.append(link).append("affprog=").append("demo").append("&salescode=").append("wwww").append("&custType=").append("Parmanent")
					.append("&cookietime=").append("Testing").append("&acfulladdress=").append(acfulladdress).append("&acsid=").append("dfejhuf")
					.append("&utm=").append("deefe");
			//logger.info("LineItemExternalId="+acsid);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			URL centuryLinkUrl=new URL(updatedLink);
			String callbackNumber="001-055-2245";
			logger.info("updatedCenturyLinkUrl="+centuryLinkUrl);
			model8.addAttribute("centuryLinkUrl", centuryLinkUrl);
			model8.addAttribute("callbackNumber", callbackNumber.replaceAll("-", ""));
			model8.addAttribute("callbackDispNumber", callbackNumber);
			logger.info("In_sendCenturyLinkLinkForEmail="+email);
			sendNewEmail(session, email,EMAIL_SUBJECT_CENTURYLINK_PX_TELECARTS, getCenturyLinkTelecartsContentFromTemplate(model8));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendCenturyLinkEmail="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
		
	
	
	
	
	@GetMapping("sendTmobileEmailLink")
	public ResponseEntity<Object> sendTmobileEmailLink(@RequestParam("email") String email, @RequestParam("provider") String provider,
			@RequestParam("csid") String csid, Model modelHome) {
		try {
			String link = "https://tmobile.prf.hn/click/camref:1101lqydh/pubref:ref=concert/ar:";
			 StringBuilder sb = new StringBuilder();
			 sb = sb.append(link).append(csid);
			logger.info("lineItemId="+csid);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			URL tmurl=new URL(updatedLink);
			logger.info("updatedTmobileUrl="+tmurl);
			modelHome.addAttribute("tmurl", tmurl);
			logger.info("In_sendTmobileLinkForEmail="+email);
			sendNewEmail(session, email,EMAIL_SUBJECT_TMOBILE, getTMobileContentFromTemplate(modelHome));
			logger.info("Mail_Sent_successfully_for_email="+email);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendEmail="+e.getMessage());
		}
		return ResponseEntity.ok().body("Success");
	}
	
	public String getWMContentFromTemplate(Model wmModel)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("Walmart_Email.ftl"), wmModel));
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
	
	public String getWMACContentFromTemplate(Map < String, Object >model)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("Walmart_AC_Email.ftl"), model));
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
	
	public String getVZTelecartsContentFromTemplate(Model model3)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("VZ_Telecarts_Email.ftl"), model3));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getVZTelecartsContentFromTemplate="+e.getMessage());
        }
        return content.toString();
    }
	
	public String getCoxTelecartsContentFromTemplate(Model model3)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("Cox_Telecarts_Email.ftl"), model3));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getCoxTelecartsContentFromTemplate="+e.getMessage());
        }
        return content.toString();
    }
	
	public String getXfinityTelecartsContentFromTemplate(Model model2)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("Xfinity_Telecarts_Email.ftl"), model2));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getXfinityTelecartsContentFromTemplate="+e.getMessage());
        }
        return content.toString();
    }
	
	public String getHW210ContentFromTemplate(Model model1)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("2-10_warranty_Email.ftl"), model1));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_2-10_warranty="+e.getMessage());
        }
        return content.toString();
    }
	
	public String getTMobileContentFromTemplate(Model model2)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("TMobile_Telecarts_Email.ftl"), model2));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getTMobileHomeInternetContentFromTemplate="+e.getMessage());
        }
        return content.toString();
    }
	
	public String getSpectrumTelecartsContentFromTemplate(Model model3)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("Spectrum_Telecarts_Email.ftl"), model3));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getSpectrumContentFromTemplate="+e.getMessage());
        }
        return content.toString();
    }
	
	public String getATTTelecartsContentFromTemplate(Model model4)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("Att_Telecarts_Email.ftl"), model4));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getATTContentFromTemplate="+e.getMessage());
        }
        return content.toString();
    }
	public String getDTVTelecartsContentFromTemplate(Model model14)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("DTV_Telecarts_Email.ftl"), model14));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getDTVContentFromTemplate="+e.getMessage());
        }
        return content.toString();
    }
	
	
	
	
	public String getEarthlinkTelecartsContentFromTemplate(Model model5)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("Earthlink_Telecarts_Email.ftl"), model5));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getEarthlinkContentFromTemplate="+e.getMessage());
        }
        return content.toString();
    }
	
	public String getCenturyLinkTelecartsContentFromTemplate(Model model9)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("CenturyLink_Telecarts_Email.ftl"), model9));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getViasatContentFromTemplate="+e.getMessage());
        }
        return content.toString();
    }
	
	public String getViasatTelecartsContentFromTemplate(Model model8)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("Viasat_Telecarts_Email.ftl"), model8));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getViasatContentFromTemplate="+e.getMessage());
        }
        return content.toString();
    }
	
	public String getHughesNetTelecartsContentFromTemplate(Model model7)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("HughesNet_Telecarts_Email.ftl"), model7));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getFrontierContentFromTemplate="+e.getMessage());
        }
        return content.toString();
    }
	
	public String getFrontierTelecartsContentFromTemplate(Model model6)     { 
        StringBuffer content = new StringBuffer();
        try {
            content.append(FreeMarkerTemplateUtils.processTemplateIntoString(fmConfiguration.getTemplate("Frontier_Telecarts_Email.ftl"), model6));
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getFrontierContentFromTemplate="+e.getMessage());
        }
        return content.toString();
    }
	
	private String getBase64Value(String value)     { 
		String encodedString = "";
        try {
        	encodedString = Base64.getEncoder().encodeToString(value.getBytes());
        } 
        catch (Exception e) {
        	logger.error("Exception_In_getBase64Value="+e.getMessage());
        }
        return encodedString;
    }
	
	@GetMapping("sendCoxSMSLink")
	public ResponseEntity<Object> sendCoxSMSLink(@RequestParam("lineItemId") String lineItemId,@RequestParam("address") String address,@RequestParam("address2") String address2,@RequestParam("lob") String lob,
														 @RequestParam("zip") String zip, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("ucid") String ucid, @RequestParam("trackingId") String trackingId, @RequestParam("mcid") String mcid){	
		try {
			String link = "https://www.cox.com/residential-shop/wlscontroller/allconnectcart2#";
			StringBuilder sb = new StringBuilder();
			sb = sb.append(link).append("lob=").append(lob).append(";address=").append(address).append(";zip=").append(zip).append(";apt=").append(address2).append(";ac_id=").
					append(lineItemId).append(";utm_mcid=").append(mcid).append(";utm=").append("cartbutton").append(";ucid=").append(ucid).append(";trackingId=").append(trackingId);
			logger.info("lineItemId="+lineItemId);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			logger.info("updatedCoxUrl="+updatedLink);
			String message = "Thank you for your interest in setting up Cox service! Click the link below to begin the setup process. "+updatedLink+" Msg&data rates may apply. Text 'STOP' to quit.";
			logger.info("In_sendCoxTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, message);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendCoxSMS="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendXfinitySMSLink")
	public ResponseEntity<Object> sendXfinitySMSLink(@RequestParam("lineItemId") String lineItemId,@RequestParam("address") String address,@RequestParam("lob") String lob,
														 @RequestParam("zip") String zip, @RequestParam("phoneNumber") String phoneNumber, @RequestParam("ucid") String ucid, 
														 @RequestParam("trackingId") String trackingId, @RequestParam("mcid") String mcid, Model model2){	
		try {
			String link1 = "https://www.jdoqocy.com/click-100407767-15253831#url=";
			String url = "https://www.xfinity.com/learn/landing/partners#lob="
			+ URLEncoder.encode(lob, "UTF-8") + "&StreetAddress="
			+ URLEncoder.encode(getBase64Value(address.trim()), "UTF-8") + ";ZipCode="
			+ URLEncoder.encode(getBase64Value(zip), "UTF-8") ;

			logger.info("encodedString1="+url);

			link1 = link1 + URLEncoder.encode(url, "UTF-8") +";utm_mcid="
			+ URLEncoder.encode("3576904", "UTF-8") + ";utm="
			+ URLEncoder.encode("cartbutton", "UTF-8") + ";sid="
			+ URLEncoder.encode(lineItemId, "UTF-8");

			logger.info("lineItemId="+lineItemId);
			String updatedLink = link1.toString().replaceAll(" ", "%20");
			logger.info("updatedXfinityUrl="+updatedLink);
			String message = "Thank you for your interest in setting up Xfinity service! Click the link below to begin the setup process. "+updatedLink+" Msg&data rates may apply. Text 'STOP' to quit.";
			logger.info("In_sendXfinityTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, message);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendXfinitySMS="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendSpectrumSMSLink")
	public ResponseEntity<Object> sendSpectrumSMSLink(@RequestParam("lineItemId") String lineItemId,@RequestParam("address") String address,@RequestParam("city") String city,
			@RequestParam("moving") String moving,@RequestParam("state") String state,@RequestParam("zip") String zip,@RequestParam("trackingId") String trackingId,
			@RequestParam("phoneNumber") String phoneNumber){	
		try {
			String link = "https://etail.spectrum.com/address/localization.html#v=ALLCON;";
			StringBuilder sb = new StringBuilder();
			sb = sb.append(link).append("a=").append(address).append(";addrCity=").append(city).append(";addrState=").append(state).append(";z=").append(zip).append(";moving=").append(moving)
					.append(";TransID=").append(lineItemId).append(";offcmp=PSF%2CNTP;affpn=1-833-579-0038");
			logger.info("lineItemId="+lineItemId);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			logger.info("updatedSpectrumUrl="+updatedLink);
			String message = "Thank you for your interest in setting up Spectrum service! Click the link below to begin the setup process. "+updatedLink+" Msg&data rates may apply. Text 'STOP' to quit.";
			logger.info("In_sendSpectrumTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, message);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendSpectrumSMS="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendVerizonSMSLink")
	public ResponseEntity<Object> sendVerizonSMSLink(@RequestParam("lineItemId") String lineItemId, @RequestParam("orderId") String orderId,@RequestParam("address") String address,@RequestParam("unitType") String unitType,
			@RequestParam("unitValue") String unitValue,@RequestParam("city") String city,@RequestParam("state") String state,@RequestParam("zip5") String zip5,
			@RequestParam("zip9") String zip9, @RequestParam("zip9or5") String zip9or5,@RequestParam("productId") String productId, @RequestParam("categoryFilter") String categoryFilter,
			@RequestParam("ucid") String ucid, @RequestParam("trackingId") String trackingId,@RequestParam("phoneNumber") String phoneNumber){	
		try {
			String link = "https://www.verizon.com/home/#INI=vzw-shop-home-internet;CMP=oth_h_p_rv_oth_acq_99_99_rvdigital;abr=REDCARTALCT;c=A004217;locationCode=Q707501;outletid=135906;";
			StringBuilder sb = new StringBuilder();
			sb = sb.append(link).append("street_line=").append(address).append(";unitType=").append(unitType).append(";unitValue=").append(unitValue).append(";city=").append(city).
					append(";state=").append(state).append(";zip5=").append(zip5).append(";zip9=").append(zip9).append(";zip9or5=").append(zip9or5).append(";cohesionSessionId=").
					append(lineItemId).append(";cohesionTransitId=").append(orderId).append(";productId=").append(productId).append(";categoryFilter=").append(categoryFilter)
					.append(";ucid=").append(ucid).append(";trackingId=").append(trackingId);
			logger.info("lineItemId="+lineItemId);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			logger.info("updatedVerizonUrl="+updatedLink);
			String message = "Thank you for your interest in setting up Verizon service! Click the link below to begin the setup process. "+updatedLink+" Msg&data rates may apply. Text 'STOP' to quit.";
			logger.info("In_sendVerizonTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, message);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendVerizonSMS="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendATTSMSLink")
	public ResponseEntity<Object> sendATTSMSLink(@RequestParam("SID") String lineItemId,@RequestParam("streetAddress") String address,@RequestParam("zip") String zip,
			 @RequestParam("aptUnitNum") String unitValue,@RequestParam("utm") String utm,@RequestParam("utm_mcid") String utm_mcid,@RequestParam("source") String source,
			@RequestParam("phoneNumber") String phoneNumber){
		try {
			String link = "https://www.att.com/internet/fiber/#key=8W3bMYN8mfHXcnMS;";
			 StringBuilder sb = new StringBuilder();
			 sb = sb.append(link).append("SID=").append(lineItemId).append(";streetAddress=").append(address)
					 .append(";zip=").append(zip).append(";aptUnitNum=").append(unitValue).append(";utm=").append(utm).append(";utm_mcid=")
					 .append(utm_mcid).append(";source=").append(source);
			logger.info("lineItemId="+lineItemId);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			logger.info("updatedATTUrl="+updatedLink);
			String message = "Thank you for your interest in setting up AT&T service! Click the link below to begin the setup process. "+updatedLink+" Msg&data rates may apply. Text 'STOP' to quit.";
			logger.info("In_sendATTTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, message);

			logger.info("SMS_Sent_successfully_for_phoneNumber="+phoneNumber);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendATTSMS="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendEarthLinkSMSLink")
	public ResponseEntity<Object> sendEarthLinkSMSLink(@RequestParam("address") String address,@RequestParam("city") String city,@RequestParam("state") String state,
			 @RequestParam("zip") String zip,@RequestParam("unittype") String unittype,@RequestParam("unitvalue") String unitvalue,@RequestParam("acaid") String acaid,
			 @RequestParam("acsid") String acsid,@RequestParam("Euid") String Euid,@RequestParam("utm_mcid") String utm_mcid,@RequestParam("utm") String utm,
			 @RequestParam("promocode") String promocode,@RequestParam("phoneNumber") String phoneNumber){
		try {
			String link = "https://checkout.earthlink.com/#/#";
			 StringBuilder sb = new StringBuilder();
			 sb = sb.append(link).append("acaddress=").append(address).append(";accity=").append(city).append(";acstate=").append(state).append(";aczip=")
					 .append(zip).append(";acunittype=").append(unittype).append(";acunitvalue=").append(unitvalue).append(";acaid=").append(acaid)
					 .append(";acsid=").append(acsid).append(";Euid=").append(Euid).append(";utm_mcid=").append(utm_mcid).append(";utm=")
					 .append(utm).append(";promocode=").append(promocode);
			logger.info("lineItemId="+acsid);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			logger.info("updatedEarthLinkUrl="+updatedLink);
			String message = "Thank you for your interest in setting up EarthLink service! Click the link below to begin the setup process. "+updatedLink+" Msg&data rates may apply. Text 'STOP' to quit.";
			logger.info("In_sendEarthLinkTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, message);
			logger.info("SMS_Sent_successfully_for_phoneNumber="+phoneNumber);

		}
		catch (Exception e) {
			logger.error("Exception_In_sendEarthLinkSMS="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}

	@GetMapping("sendViasatSMSLink")
	public ResponseEntity<Object> sendViasatSMSLink(@RequestParam("address") String address,@RequestParam("city") String city,
			@RequestParam("state") String state,@RequestParam("zip") String zip,@RequestParam("countryCode") String countryCode,
			@RequestParam("customerType") String customerType,@RequestParam("affiliateID") String affiliateID,
			@RequestParam("sessionID") String sessionID,@RequestParam("did") String did,@RequestParam("paid") String paid,
			@RequestParam("cid") String cid,@RequestParam("phoneNumber") String phoneNumber){
		try {
			String link = "https://buy.viasat.com/en-US/residential/#";
			StringBuilder sb = new StringBuilder();
			sb = sb.append(link).append("addressLine1=").append(address).append(";municipality=").append(city).append(";region=").append(state)
				.append(";postalCode=").append(zip).append(";countryCode=").append(countryCode).append(";customerType=").append(customerType).append(";affiliateID=")
				.append(affiliateID).append(";sessionID=").append(sessionID).append(";DID=").append(did).append(";PAID=").append(paid).append(";CID=").append(cid);
			logger.info("LineItemExternalId="+sessionID);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			logger.info("updatedViasatUrl="+updatedLink);
			String message = "Thank you for your interest in setting up Viasat service! Click the link below to begin the setup process. "+updatedLink+" Msg&data rates may apply. Text 'STOP' to quit.";
			logger.info("In_sendViasatTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, message);
			logger.info("SMS_Sent_successfully_for_phoneNumber="+phoneNumber);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendViasatSMS="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendHughesNetSMSLink")
	public ResponseEntity<Object> sendHughesNetSMSLink(@RequestParam("utm") String utm,@RequestParam("utm_mcid") String utm_mcid,
			@RequestParam("ref_sess") String ref_sess,@RequestParam("lineItemId") String lineItemId,@RequestParam("phoneNumber") String phoneNumber){
		try {
			String link = HUGHESNET_EMAIL_LINK_URL+"#";
			 StringBuilder sb = new StringBuilder();
			 sb = sb.append(link).append("utm=").append(utm).append(";utm_mcid=").append(utm_mcid).append(";ref_sess=").append(ref_sess)
					 .append(";transit_id=").append(lineItemId);
			logger.info("lineItemId="+lineItemId);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			logger.info("updatedHughesNetUrl="+updatedLink);
			String message = "Thank you for your interest in setting up HughesNet service! Click the link below to begin the setup process. "+updatedLink+" Msg&data rates may apply. Text 'STOP' to quit.";
			logger.info("In_sendHughesNetTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, message);
			logger.info("SMS_Sent_successfully_for_phoneNumber="+phoneNumber);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendHughesNetSMS="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendFrontierSMSLink")
	public ResponseEntity<Object> sendFrontierSMSLink(@RequestParam("utm") String utm,@RequestParam("utm_mcid") String utm_mcid,
			@RequestParam("ref_sess") String ref_sess,@RequestParam("lineItemId") String lineItemId,@RequestParam("phoneNumber") String phoneNumber){
		try {
			String link = "https://www.allconnect.com/cart/frontier/#";
			 StringBuilder sb = new StringBuilder();
			 sb = sb.append(link).append("utm_mcid=").append(utm_mcid).append(";utm=").append(utm).append(";ref_sess=").append(ref_sess)
					 .append(";transit_id=").append(lineItemId);
			logger.info("lineItemId="+lineItemId);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			logger.info("updatedFrontierUrl="+updatedLink);
			String message = "Thank you for your interest in setting up Frontier service! Click the link below to begin the setup process. "+updatedLink+" Msg&data rates may apply. Text 'STOP' to quit.";
			logger.info("In_sendFrontierTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, message);
			logger.info("SMS_Sent_successfully_for_phoneNumber="+phoneNumber);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendFrontierSMS="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendCenturyLinkSMSLink")
	public ResponseEntity<Object> sendCenturyLinkSMSLink(@RequestParam("affprog") String affprog,@RequestParam("salescode") String salescode,
			@RequestParam("custType") String custType,@RequestParam("cookietime") String cookietime,
			@RequestParam("acfulladdress") String acfulladdress,@RequestParam("acsid") String acsid,@RequestParam("utm") String utm,
			@RequestParam("phoneNumber") String phoneNumber){
		try {
			String link = "https://www.centurylink.com/internet/#";
			StringBuilder sb = new StringBuilder();
			sb = sb.append(link).append("affprog=").append(affprog).append(";salescode=").append(salescode).append(";custType=").append(custType)
					.append(";cookietime=").append(cookietime).append(";acfulladdress=").append(acfulladdress).append(";acsid=").append(acsid)
					.append(";utm=").append(utm);
			logger.info("LineItemExternalId="+acsid);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			logger.info("updatedCLUrl="+updatedLink);
			String message = "Thank you for your interest in setting up CenturyLink service! Click the link below to begin the setup process. "+updatedLink+" Msg&data rates may apply. Text 'STOP' to quit.";
			logger.info("In_sendCenturyLinkTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, message);
			logger.info("SMS_Sent_successfully_for_phoneNumber="+phoneNumber);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendCenturyLinkSMS="+e.getMessage());
			e.printStackTrace();
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendTmobileSMSLink")
	public ResponseEntity<Object> sendTmobileSMSLink(@RequestParam("phoneNumber") String phoneNumber, @RequestParam("provider") String provider,
			@RequestParam("csid") String csid) {
		try {
			String link = "https://tmobile.prf.hn/click/camref:1101lqydh/pubref:ref=concert/ar:";
			StringBuilder sb = new StringBuilder();
			sb = sb.append(link).append(csid);
			logger.info("lineItemId="+csid);
			String updatedLink = sb.toString().replaceAll(" ", "%20");
			logger.info("updatedTMobileUrl="+updatedLink);
			String message = "Thank you for your interest in setting up T-Mobile service! Click the link below to check availability for your address. "+updatedLink+" Msg&data rates may apply. Text 'STOP' to quit.";
			logger.info("In_sendTMobileTelecartsLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, message);
			logger.info("SMS_Sent_successfully_for_phoneNumber="+phoneNumber);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendTMobileSMS="+e.getMessage());
		}
		return ResponseEntity.ok().body("Success");
	}
	
	@GetMapping("sendDukeTechProtectSMSLink")
	public ResponseEntity<Object> sendDukeTechProtectSMSLink(@RequestParam("phoneNumber") String phoneNumber) {
		try {
			String link = "https://www.sell.personaltechpro.com";
			String updatedLink = link.toString().replaceAll(" ", "%20");
			logger.info("updatedDukeTechProtectUrl="+updatedLink);
			String message = "Thank you for your interest in setting up Duke Tech Protect service! Click the link below to check availability for your address. "+updatedLink+" Msg&data rates may apply. Text 'STOP' to quit.";
			logger.info("In_sendDukeTechProtectLinkSMS="+phoneNumber);
			SMSManager.sendSMS(phoneNumber, message);
			logger.info("SMS_Sent_successfully_for_phoneNumber="+phoneNumber);
		}
		catch (Exception e) {
			logger.error("Exception_In_sendDukeTechProtectSMS="+e.getMessage());
		}
		return ResponseEntity.ok().body("Success");
	}
	
	private class SMTPAuthenticator extends javax.mail.Authenticator {

		public PasswordAuthentication getPasswordAuthentication() {
			String username = "jackmalhotra56@gmail.com";
			String password = "kxigxtlmaoarmgmb";
			return new PasswordAuthentication(username, password);
		}
	}
	
/*
	@GetMapping("tesingXLSV")
	public void tesingXLSV(@RequestParam("email") String email, Model model10) throws IOException, InterruptedException {

		try {

			FileInputStream file = new FileInputStream(new File("C:\\Users\\achauhan\\Desktop\\ticket.xlsx"));

			XSSFWorkbook workbook = new XSSFWorkbook(file);
			XSSFSheet sheet = workbook.getSheetAt(0);
			int count=1;
		    Iterator < Row > rowIterator = sheet.iterator();
		    while (rowIterator.hasNext()) {
		        Row row = rowIterator.next();

		        //For each row, iterate through each columns
		        Iterator < Cell > cellIterator = row.cellIterator();
		        while (cellIterator.hasNext()) {

		            Cell cell = cellIterator.next();
		            String errorRecord=cell.getStringCellValue();
		            model10.addAttribute("webContent", errorRecord);
					sendNewEmail(session, email, EMAIL_SUBJECT_REPORT, getUrgentEmailContentFromTemplate(model10));
		        }
		        System.out.println("Email Sent : "+count);
		        count++;
		    }
		    file.close();
		    System.out.println("*********** All Mail Sent *******************");

		} catch (FileNotFoundException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
*/
	
}
