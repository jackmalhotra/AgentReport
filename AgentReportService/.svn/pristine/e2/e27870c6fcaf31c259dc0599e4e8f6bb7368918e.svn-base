package com.allconnect.agentReport.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.log4j.Logger;

public class Utils {
	private static final Logger logger = Logger.getLogger(Utils.class);
	public static String escapeSpecialCharacters(String str){
		if(str!=null){
			str = str.replaceAll("&amp;", "&");
			str = str.replaceAll("'", "&#39;");
			str = str.replaceAll("&quot;", "&#34;");
			
			str = str.replaceAll("&#10;", "&nbsp;");
			str = str.replaceAll("\u00a0", "&nbsp;");
			//this is for - mark
			str = str.replaceAll("\u2013", "&#8211;");
			//this is for trademark
			str = str.replaceAll("\u2122", "&#8482;");
			
			//this is for Copyright mark
			str = str.replaceAll("\u00a9", "&#169;");
			//this is for Registered trade mark
			str = str.replaceAll("\u00ae", "&#174;");
			
			//this is for bullet point
			str = str.replaceAll("\u2022", "&#8226;");
			//this is for exclamation point
			str = str.replaceAll("\u0021", "&#33;");
			//this is for colon
			str = str.replaceAll("\u003a", "&#58;");
			//this is for inverted question mark
			str = str.replaceAll("\u00bf", "&#191;");
			
			//this is for right single quotation mark
			str = str.replaceAll("\u2019", "&#8217;");
			//this is for left single quotation mark
			str = str.replaceAll("\u2018", "&#8216;");
			//this is for left double quotation mark
			str = str.replaceAll("\u201C", "&#8220;");
			//this is for right double quotation mark
			str = str.replaceAll("\u201D", "&#8221;");
			
			//this is for left tag
			str = str.replaceAll("&lt;", "<");
			
			//this is for right tag
			str = str.replaceAll("&gt;", ">");
		}
		return str;
	}
	
	public static String escapeSMSSpecialCharacters(String str){
		if(str!=null){
			str = str.replaceAll("&apos;", "&#39;");
		}
		return str;
	}

	
	/**
	 * Checks if string is <code>null</code> or empty.
	 *
	 * @param str String to check.
	 * @return if string is <code>null</code> or empty, <code>false</code>
	 *         otherwise.
	 */
	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}
	
}
