package com.allconnect.gateway;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.apache.log4j.Logger;
import static com.allconnect.agentReport.utils.ProcessConfigProperties.SMS_API_KEY;
import static com.allconnect.agentReport.utils.ProcessConfigProperties.SMS_API_SECRET;
import static com.allconnect.agentReport.utils.ProcessConfigProperties.SMS_ACCOUNT_SID;
import static com.allconnect.agentReport.utils.ProcessConfigProperties.SMS_FROM;

public class SMSManager {
	private static final Logger log = Logger.getLogger(SMSManager.class);
	static {
		log.info("Inside SMS Read Twilio");
		Twilio.init(SMS_API_KEY, SMS_API_SECRET, SMS_ACCOUNT_SID);
	}
	
	public static void sendSMS(String phoneNumber, String messageData) {
		try {
			Message message = Message.creator(new PhoneNumber("+1"+phoneNumber), SMS_FROM, messageData).create();
			log.info("SMS_Sent_successfully_for_phoneNumber="+phoneNumber);
			log.info("SMS message status="+message.getStatus());
			log.info("SMS error message="+message.getErrorMessage());
			log.info("SMS message="+message);
			Thread.sleep(333);
		} 
		catch (Exception e) {
			log.error("Exception_In_sendSMS="+e.getMessage());
		}
	}
}