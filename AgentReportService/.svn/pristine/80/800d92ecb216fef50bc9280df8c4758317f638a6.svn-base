/**
 * File:        GenericEmailHelper.java
 * Copyright:   Copyright (c) 2009
 * Created:     15-Apr-2009
 * Company:     Allconnect, Inc.
 *
 * @author: Zahir Shaikh
 * @version $Id:
 */

package com.allconnect.gateway;

import static com.allconnect.agentReport.utils.ProcessConfigProperties.EMAIL_DEFAULTMIMETYPE;
import static com.allconnect.agentReport.utils.ProcessConfigProperties.EMAIL_HOST;
import static com.allconnect.agentReport.utils.ProcessConfigProperties.EMAIL_PORT;
import static com.allconnect.agentReport.utils.ProcessConfigProperties.EMAIL_PROTOCOL;
import static com.allconnect.agentReport.utils.ProcessConfigProperties.EMAIL_SECURECONNECTIONTYPE;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

public class GenericEmailHelper {

	private static final Logger log = Logger.getLogger(GenericEmailHelper.class);

	public static final String HOST_NAME_PREFIX_LEFT_PAR = "(";

	public static final String HOST_NAME_PREFIX_RIGHT_PAR = ")";

	public static final String STRING_SEPARATOR = " ";

	public static final String BLANK_STRING = "";
	
	


	private boolean prefixHostStr = true;
	 
	//Reading properties from External Properties
	private String emailHost;
	
	private String emailPort;
	
	private String emailUsername;
	
	private String emailPassword;
	
	private String emailProtocol;
	
	private String emailSecureConnectionType;
	
	private String emailDefaultMimeType;
	
	private String emailEnabled;
	
	// mMailConfig can be set if the default configuration used by EmailManager needs to be overridden
	// this contains host, port, secureConnectionType, protocol, user, password, defaultMimeType
	private EmailManagerConfiguration mMailConfig = null;
	private String mimeType = "text/plain";
	private String hostNameKey = "HOST_NAME";


	// for success email
	private String fromKey = "EMAIL_FROM";
	private String toKey = "EMAIL_TO";
	private String ccKey = "EMAIL_CC";

	// private String bccKey = "EMAIL_BCC";

	private String subjectKey = "EMAIL_SUBJECT";
	private String contentKey = "EMAIL_CONTENT";

	// for info email (when no records found)
	private String infoFromKey = null; // "INFO_EMAIL_FROM";
	private String infoToKey = null; // "INFO_EMAIL_TO";
	private String infoCcKey = null; // "INFO_EMAIL_CC";

	// private String infoBccKey = null;

	private String infoSubjectKey = null; // "INFO_EMAIL_SUBJECT";
	private String infoContentKey = null; // "INFO_EMAIL_CONTENT";

	//Externalize the Email Properties
	@Value("${email.from}")
	private String errFromKey;
    
    @Value("${email.to}")
	private String errToKey;
    
    @Value("${email.cc}")
	private String errCcKey;
    
    @Value("${email.emailSubject}")
	private String errSubjectKey;
    
    @Value("${email.emailErrorContent}")
	private String errContentKey;
	
	private String toPartnerKey = "to";
	private String fromPartnerKey = "from";
	private String partnerSubjectKey = "emailSubject";
	private String partnerContentKey = "emailErrorContent";

	public GenericEmailHelper (){
	}


	//code for sending errorEmail
	public void sendErrorEmail(String[] subjectParams, String[] contentParams,File[] files) throws Exception {

		//sendEmail(errFromKey, errToKey, errCcKey, errSubjectKey,subjectParams, errContentKey, contentParams, files);

	}

	//private static void sendEmail(String fromKey, String toKey, String ccKey, String subjectKey,String[] subjectParams, String contentKey, String[] contentParams, File[] files)
	public static void sendEmail(String contentVal, String  toEmail, String emailSubj, String emailFrom){

		String from = getStringValue(emailFrom);
		log.info("Email From=" + from);
		List to = getListValues(toEmail);
		log.info("Email To=" + to);
		List cc = getListValues("");
		log.info("Email cc=" + cc);
		String subject = getStringValue(emailSubj);
		subject = MessageFormat.format(emailSubj, new Object[]{});
		log.info("Email Subject=" + subject);
		String content = getStringValue(contentVal);
		log.info("content=" +StringEscapeUtils.escapeHtml(content));
		content =  MessageFormat.format(content, new Object[]{});
		log.info("Email Content=" + StringEscapeUtils.escapeHtml(content));
		//List attachments = getBodyPart(files);
		sendEmail(from, to, cc,subject,content); 
	}

	private static void sendEmail(String from, List to, List cc, String subject, String content) {

		//debug(from, to, cc, subject, content);
		try{
		EmailManager.sendEmail(EMAIL_PROTOCOL, EMAIL_SECURECONNECTIONTYPE, EMAIL_HOST, Integer.valueOf(EMAIL_PORT), "",
				"", from, to, cc, subject, EMAIL_DEFAULTMIMETYPE, content,EMAIL_DEFAULTMIMETYPE);
		
		}
		catch(Exception e){
			log.error("Exception while sending Mail:" + e.getMessage());
		}
	}

	/*private List getBodyPart(File[] files) {

		if (Utils.isEmpty(files)) 
			return null;

		List list = new ArrayList();
		BodyPart part = null;

		for (int i = 0; i < files.length; i++) {
			part = null;
			try {
				part = EmailManager.bodyPartFromFile(files[i]);
				if (part != null) {
					// attachment = Collections.singletonList(part);
					list.add(part);
				}
			}
			catch (Exception ex) {
				log.info("Exception while attaching file in sendMail:" + ex.getMessage());
				log.error(ex);
			}
		}

		return list;
	}*/

	private static String getStringValue(String key) {
		if (!isBlank(key)) 
			return key;

		return BLANK_STRING;
	}

	private static String getStringValue(String key, String[] args) {
		String val = null;

		if (!isBlank(val)) return val;

		return BLANK_STRING;
	}

	private static List getListValues(String key) {
		if(!isBlank(key)){
			List<String> errorEmailsList = new ArrayList<String>(Arrays.asList(key.split(",")));
			return errorEmailsList;
		}
		return null;


	}

	/*private static void debug(String from, List to, List cc, String subject, String content) {

		if (log.isDebugEnabled()) {
			//Intializing the Email configuration Properties
			initializeMailConfig();
			if (mMailConfig != null) {
				log.debug("mMailConfig.getHost():" + mMailConfig.getHost());
				log.debug("mMailConfig.getPort():" + getEmail_PORT(EMAIL_PORT));
				log.debug("mMailConfig.getProtocol():" +mMailConfig.getProtocol());
				log.debug("mMailConfig.getSecureConnectionType():" + mMailConfig.getSecureConnectionType());
				log.debug("mMailConfig.getUserName():" + mMailConfig.getUserName());
				log.debug("mMailConfig.getPassword():" + mMailConfig.getPassword());
				log.debug("mMailConfig.getDefaultMimeType():" + mMailConfig.getDefaultMimeType());
				log.debug("mMailConfig.getEmailEnabled():" + mMailConfig.getEmailEnabled());
			}

			log.debug("from:" + from);
			log.debug("to:" + to);
			log.debug("cc:" + cc);
			log.debug("subject:" + subject);
			log.debug("content:" + content);
		}

	}
*/
	/*private static void initializeMailConfig() {
		try{
			log.info("Intializing the Email configuration Properties from External properties Path ");
			mMailConfig = new EmailManagerConfiguration();
			emailHost=getStringValue(EMAIL_HOST);
			mMailConfig.setHost(emailHost);
			log.info("EMAIL_HOST: "+mMailConfig.getHost());
			emailPort=getStringValue(EMAIL_PORT);
			mMailConfig.setPort(getEmail_PORT(emailPort));
			log.info("EMAIL_PORT: "+mMailConfig.getPort());
			emailUsername=getStringValue(EMAIL_USERNAME);
			mMailConfig.setUserName(emailUsername );
			emailPassword=getStringValue(EMAIL_PASSWORD);
			mMailConfig.setPassword(emailPassword );
			emailProtocol=getStringValue(EMAIL_PROTOCOL);
			mMailConfig.setProtocol(emailProtocol);
			log.info("EMAIL_PROTOCOL: "+mMailConfig.getProtocol());
			emailSecureConnectionType=getStringValue(EMAIL_SECURECONNECTIONTYPE);
			mMailConfig.setSecureConnectionType(emailSecureConnectionType );
			log.info("EMAIL_SECURE_CONNECTION_TYPE: "+mMailConfig.getSecureConnectionType());
			String mime = EMAIL_DEFAULTMIMETYPE;
			if (!Utils.isBlank(mime)){ 
				mMailConfig.setDefaultMimeType(mime);
			}
			else{
				mMailConfig.setDefaultMimeType(mimeType);
			}
			log.info("EMAIL_DFLT_MIME_TYPE: "+mMailConfig.getDefaultMimeType());
			emailEnabled = getStringValue(EMAIL_ENABLED);
			mMailConfig.setEmailEnabled(emailEnabled);
			log.info("EMAIL_ENABLED :" + mMailConfig.getEmailEnabled());
		}
		catch(Exception e){
			log.error("Exception : "+e.getMessage());
		}
	}*/

	public void setEmailManagerConfiguration(EmailManagerConfiguration mailConfig) {
		mMailConfig = mailConfig;
	}

	public EmailManagerConfiguration getEmailManagerConfiguration() {
		return mMailConfig;
	}

	public String getMimeType() {
		return mimeType;
	}

	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	public String getHostNameKey() {
		return hostNameKey;
	}

	public void setHostNameKey(String hostNameKey) {
		this.hostNameKey = hostNameKey;
	}

	public String getCcKey() {
		return ccKey;
	}

	public void setCcKey(String ccKey) {
		this.ccKey = ccKey;
	}

	public String getContentKey() {
		return contentKey;
	}

	public void setContentKey(String contentKey) {
		this.contentKey = contentKey;
	}

	public String getErrCcKey() {
		return errCcKey;
	}

	public void setErrCcKey(String errCcKey) {
		this.errCcKey = errCcKey;
	}

	public String getErrContentKey() {
		return errContentKey;
	}

	public void setErrContentKey(String errContentKey) {
		this.errContentKey = errContentKey;
	}

	public String getErrFromKey() {
		return errFromKey;
	}

	public void setErrFromKey(String errFromKey) {
		this.errFromKey = errFromKey;
	}

	public String getErrSubjectKey() {
		return errSubjectKey;
	}

	public void setErrSubjectKey(String errSubjectKey) {
		this.errSubjectKey = errSubjectKey;
	}

	public String getErrToKey() {
		return errToKey;
	}

	public void setErrToKey(String errToKey) {
		this.errToKey = errToKey;
	}

	public String getFromKey() {
		return fromKey;
	}

	public void setFromKey(String fromKey) {
		this.fromKey = fromKey;
	}

	public String getInfoCcKey() {
		return infoCcKey;
	}

	public void setInfoCcKey(String infoCcKey) {
		this.infoCcKey = infoCcKey;
	}

	public String getInfoContentKey() {
		return infoContentKey;
	}

	public void setInfoContentKey(String infoContentKey) {
		this.infoContentKey = infoContentKey;
	}

	public String getInfoFromKey() {
		return infoFromKey;
	}

	public void setInfoFromKey(String infoFromKey) {
		this.infoFromKey = infoFromKey;
	}

	public String getInfoSubjectKey() {
		return infoSubjectKey;
	}

	public void setInfoSubjectKey(String infoSubjectKey) {
		this.infoSubjectKey = infoSubjectKey;
	}

	public String getInfoToKey() {
		return infoToKey;
	}

	public void setInfoToKey(String infoToKey) {
		this.infoToKey = infoToKey;
	}

	public String getSubjectKey() {
		return subjectKey;
	}

	public void setSubjectKey(String subjectKey) {
		this.subjectKey = subjectKey;
	}

	public String getToKey() {
		return toKey;
	}

	public void setToKey(String toKey) {
		this.toKey = toKey;
	}

	public boolean isPrefixHostStr() {
		return prefixHostStr;
	}

	public void setPrefixHostStr(boolean prefixHostStr) {
		this.prefixHostStr = prefixHostStr;
	}

	//
	public String getToPartnerKey() {
		return toPartnerKey;
	}

	public void setToPartnerKey(String toPartnerKey) {
		this.toPartnerKey = toPartnerKey;
	}

	public String getFromPartnerKey() {
		return fromPartnerKey;
	}

	public void setFromPartnerKey(String fromPartnerKey) {
		this.fromPartnerKey = fromPartnerKey;
	}

	public String getPartnerContentKey() {
		return partnerContentKey;
	}

	public void setContentPartnerKey(String contentPartnerKey) {
		this.partnerContentKey = partnerContentKey;
	}

	/* public void sendEmailToPartner(String[] subjectParams, String[] contentParams, File[] files) throws Exception {
	sendEmail(fromPartnerKey, toPartnerKey, ccKey, partnerSubjectKey,subjectParams, partnerContentKey, contentParams, files);
    }*/

	/**
	 * @return the partnerSubjectKey
	 */
	public String getPartnerSubjectKey() {
		return partnerSubjectKey;
	}

	/**
	 * @param partnerSubjectKey
	 *            the partnerSubjectKey to set
	 */
	public void setPartnerSubjectKey(String partnerSubjectKey) {
		this.partnerSubjectKey = partnerSubjectKey;
	}

	private void sendEmail(String fromKey, String toKey, String toPartnerKey, String ccKey, String subjectKey,String broadcastId,String[] subjectParams, String contentKey, String[] contentParams,
			File[] files) throws Exception {

		String from = getStringValue(fromKey);
		List to = getListValues(toKey);
		List toPartner = getListValues(toPartnerKey);
		to.addAll(toPartner);
		Set allTo = new HashSet(to);
		to = new ArrayList(allTo);
		log.info("List tos::" + to);
		List cc = getListValues(ccKey);
		String subject = getStringValue(subjectKey, subjectParams);
		String content = getStringValue(contentKey, contentParams);
		//List attachments = getBodyPart(files);

		sendEmail(from, to, cc, subject, content);
	}

	public void sendEmailToPartner(String[] subjectParams) throws Exception {
		String from = getStringValue(fromPartnerKey);
		List to = getListValues(toPartnerKey);
		List cc = getListValues(ccKey);
		String subject = getStringValue(partnerSubjectKey, subjectParams);
		String content = "No records changed.";

		sendEmail(from, to, cc, subject, content);
	}

	private int getEmail_PORT(String EMAIL_PORT){
		if(NumberUtils.isNumber(EMAIL_PORT)){
			return Integer.valueOf(EMAIL_PORT);
		}    	
		return 0;    	
	}
	
	/**
	 * Checks if string is <code>null</code> or empty.
	 *
	 * @param str String to check.
	 * @return if string is <code>null</code> or empty, <code>false</code> otherwise.
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}
}
