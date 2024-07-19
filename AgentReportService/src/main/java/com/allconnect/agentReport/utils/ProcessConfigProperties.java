/**
 * 
 */
package com.allconnect.agentReport.utils;

import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.allconnect.property.management.cache.PropertyManager;

/**
 * @author 
 *
 */
public class ProcessConfigProperties {
	
	private static final Logger logger = LogManager.getLogger(ProcessConfigProperties.class);

	public static String SMS_ACCOUNT_SID = null;
	public static String SMS_API_KEY = null;
	public static String SMS_API_SECRET = null;
	public static String SMS_FROM = null;
	public static String SMS_WALMART_MESSAGE = null;
	public static String SMS_WALMART_LINK = null;
	public static String SMS_AC_WALMART_LINK = null;
	
	public static String EMAIL_FROM = null;
	public static String EMAIL_TO = null;
	public static String EMAIL_SUBJECT_WALMART = null;
	public static String EMAIL_ENABLED = null;
	public static String EMAIL_HOST = null;
	public static String EMAIL_PORT = null;
	public static String EMAIL_PROTOCOL = null;
	public static String EMAIL_SECURECONNECTIONTYPE = null;
	public static String EMAIL_DEFAULTMIMETYPE = null;
	
	public static String SMS_YTTV_MESSAGE = null;
	public static String EMAIL_SUBJECT_YTTV = null;
	
	public static String SMS_GOOGLEFIBER_MESSAGE = null;
	public static String EMAIL_SUBJECT_GOOGLEFIBER = null;
	public static String SMS_VZ_TELECARTS_MESSAGE = null;
	public static String SMS_COX_TELECARTS_MESSAGE = null;
	public static String EMAIL_SUBJECT_VZ_TELECARTS = null;
	public static String EMAIL_SUBJECT_COX_TELECARTS = null;
	public static String SMS_XFINITY_TELECARTS_MESSAGE = null;
	public static String EMAIL_SUBJECT_XFINITY_TELECARTS = null;
	public static String HW_2_10_LINK = null;
	public static String EMAIL_SUBJECT_2_10_WARRANTY = null;
	public static String EMAIL_SUBJECT_SPECTRUM_TELECARTS = null;
	public static String EMAIL_SUBJECT_ATT_TELECARTS = null;
	public static String EMAIL_SUBJECT_EARTHLINK_TELECARTS = null;
	public static String EMAIL_SUBJECT_TMOBILE = null;
	public static String EMAIL_SUBJECT_DTV_TELECARTS = null;
	public static String EMAIL_SUBJECT_FRONTIER_TELECARTS = null;
	public static String EMAIL_SUBJECT_VIASAT_TELECARTS = null;
	public static String EMAIL_SUBJECT_CENTURYLINK_PX_TELECARTS = null;
	public static String EMAIL_SUBJECT_HUGHESNET_TELECARTS = null;
	public static String HUGHESNET_EMAIL_LINK_URL = null;
	
	

	public static Properties props = null;

	static {

		props = PropertyManager.getPropertiesFile("micro-agentreportdata/AgentReportService");

		if (props!= null && !props.isEmpty()) {
			try {
				logger.info("Inside setting Agent Report Service properties");
				SMS_ACCOUNT_SID = props.getProperty("SMS_ACCOUNT_SID");
				SMS_API_KEY = props.getProperty("SMS_API_KEY");
				SMS_API_SECRET = props.getProperty("SMS_API_SECRET");
				SMS_FROM = props.getProperty("SMS_FROM");
				SMS_WALMART_MESSAGE = props.getProperty("SMS_WALMART_MESSAGE");
				SMS_WALMART_LINK = props.getProperty("SMS_WALMART_LINK");
				SMS_AC_WALMART_LINK = props.getProperty("SMS_AC_WALMART_LINK");
				
				EMAIL_FROM = props.getProperty("EMAIL_FROM");
				EMAIL_SUBJECT_WALMART = props.getProperty("EMAIL_SUBJECT_WALMART");
				EMAIL_ENABLED = props.getProperty("EMAIL_ENABLED");
				EMAIL_HOST = props.getProperty("EMAIL_HOST");
				EMAIL_PORT = props.getProperty("EMAIL_PORT");
				EMAIL_PROTOCOL = props.getProperty("EMAIL_PROTOCOL");
				EMAIL_SECURECONNECTIONTYPE = props.getProperty("EMAIL_SECURECONNECTIONTYPE");
				EMAIL_DEFAULTMIMETYPE = props.getProperty("EMAIL_DEFAULTMIMETYPE");
				SMS_YTTV_MESSAGE = props.getProperty("SMS_YTTV_MESSAGE");
				EMAIL_SUBJECT_YTTV = props.getProperty("EMAIL_SUBJECT_YTTV");
				SMS_GOOGLEFIBER_MESSAGE = props.getProperty("SMS_GOOGLEFIBER_MESSAGE");
				EMAIL_SUBJECT_GOOGLEFIBER = props.getProperty("EMAIL_SUBJECT_GOOGLEFIBER");
				
				SMS_VZ_TELECARTS_MESSAGE = props.getProperty("SMS_VZ_TELECARTS_MESSAGE");
				SMS_COX_TELECARTS_MESSAGE = props.getProperty("SMS_COX_TELECARTS_MESSAGE");
				EMAIL_SUBJECT_VZ_TELECARTS = props.getProperty("EMAIL_SUBJECT_VZ_TELECARTS");
				EMAIL_SUBJECT_COX_TELECARTS = props.getProperty("EMAIL_SUBJECT_COX_TELECARTS");
				SMS_XFINITY_TELECARTS_MESSAGE = props.getProperty("SMS_XFINITY_TELECARTS_MESSAGE");
				EMAIL_SUBJECT_XFINITY_TELECARTS = props.getProperty("EMAIL_SUBJECT_XFINITY_TELECARTS");
				HW_2_10_LINK = props.getProperty("HW_2_10_LINK");
				EMAIL_SUBJECT_2_10_WARRANTY = props.getProperty("EMAIL_SUBJECT_2_10_WARRANTY");
				EMAIL_SUBJECT_SPECTRUM_TELECARTS = "INSIDE: Your sign-up link for Spectrum service";
				EMAIL_SUBJECT_TMOBILE = "INSIDE: Your sign-up link for T-Mobile service";
				EMAIL_SUBJECT_ATT_TELECARTS = "INSIDE: Your sign-up link for AT&T Services";
				EMAIL_SUBJECT_EARTHLINK_TELECARTS = "INSIDE: Your sign-up link for EarthLink Services";
				EMAIL_SUBJECT_HUGHESNET_TELECARTS = "INSIDE: Your sign-up link for HughesNet Services";
				EMAIL_SUBJECT_DTV_TELECARTS = "INSIDE: Your sign-up link for DTV Services";
				EMAIL_SUBJECT_FRONTIER_TELECARTS = "INSIDE: Your sign-up link for Frontier Services";
				EMAIL_SUBJECT_VIASAT_TELECARTS = "INSIDE: Your sign-up link for Viasat Services";
				EMAIL_SUBJECT_CENTURYLINK_PX_TELECARTS = "INSIDE: Your sign-up link for CenturyLink Services";
				HUGHESNET_EMAIL_LINK_URL = props.getProperty("HUGHESNET_EMAIL_LINK_URL");
			} 
			catch (Exception e) {
				logger.error("Exception {}",e);
			}
		} 
		else {
			logger.error("No properties are found to process Intra Day Data Push Service");
		}

	}
}