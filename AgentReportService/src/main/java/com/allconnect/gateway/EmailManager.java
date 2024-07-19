/**
 * File:        EmailManager.java
 * Copyright:   Copyright (c) 2006
 * Created:     25.10.2006
 * Company:     Allconnect, Inc.
 *
 * @author: amagdenko
 * @version $Id: EmailManager.java,v 1.14 2011/02/28 14:05:04 tmartin Exp $
 */

package com.allconnect.gateway;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.Logger;

public class EmailManager
{
  /** */
  private static final Logger log = Logger.getLogger(EmailManager.class);


  /**
   * @param proto
   * @param connType
   * @param smtpHost
   * @param smtpPort
   * @param username
   * @param passwd
   * @param from
   * @param to
   * @param cc
   * @param subject
   * @param mime
   * @param content
   * @param attachments
   * @throws EmailException
   */
  public static void sendEmail(String proto, String connType, String smtpHost, int smtpPort,
    final String username, final String passwd, String from, List to, List cc,
    String subject, String mime, Object content,String emailEnabled) throws EmailException
  {
    
	  // State checking.
    assert proto != null;
    assert connType != null;
    assert smtpHost != null;
    assert from != null;
    assert(to != null && to.isEmpty() == false) || (cc != null && cc.isEmpty() == false);
    assert content != null;
    assert subject != null;

    if (emailEnabled.equalsIgnoreCase("false"))
    {
      
      if (log.isDebugEnabled() == true)
      {
        log.debug("Message was not send. Sending disabled.");
      }

      return;
    }

    try
    {
      Authenticator auth = null;

      Properties props = new Properties();

      // Session properties.
      props.setProperty("mail.transport.protocol", proto);

      String mailProto = "mail." + proto;

      props.setProperty(mailProto + ".host", smtpHost);
      props.setProperty(mailProto + ".port", String.valueOf(smtpPort));
      
      connType = connType.toUpperCase();

      if ("STARTTLS".equals(connType))
      {
        props.setProperty(mailProto + ".starttls.enable", "true");
      }
      else if ("SSL".equals(connType))
      {
        props.setProperty(mailProto + ".ssl", "true");
      }
      log.info("username " + username);
      // Add property for authentication by username
      if (!isBlank(username))
      {
        props.setProperty(mailProto + ".auth", "true");

        auth = new Authenticator()
        {
          /**
           * @return FIXDOC
           */
        	
          public PasswordAuthentication getPasswordAuthentication()
          {
        	  log.info("Email authentication: UserName: "+username+" Password: "+passwd);
        	  return new PasswordAuthentication(username, passwd);
          }
        };
      }
      
      log.info("Email Properties : " + props.toString());
      
      Session ses = Session.getInstance(props, auth);
      
      // Create MIME message.
      Message msg = new MimeMessage(ses);

      // Set up the message.
      
      msg.setFrom(new InternetAddress(from));
      
      log.info("Mail subject: "+subject);
      
      
      msg.setSubject(subject);
      
      
      msg.setSentDate(new Date());
      
      log.info("Sending Email To : " + to.toString());
      // Add TO:
      if (isEmpty(to) == false)
      {
        Iterator iter = to.iterator();

        while (iter.hasNext() == true)
        {
          String email = (String)iter.next();
         
          msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        }
      }
      log.info("msg getAllRecipients :"+ msg.getAllRecipients().toString());
      
      // Add CC:
      if (isEmpty(cc) == false)
      {
      	log.debug("CC Email To : " + cc.toString());
        Iterator iter = cc.iterator();

        while (iter.hasNext() == true)
        {
          String email = (String)iter.next();
          
          msg.addRecipient(Message.RecipientType.CC, new InternetAddress(email));
        }
      }
      log.info("msg getAllRecipients :"+ msg.getAllRecipients().toString());

      MimeBodyPart main = new MimeBodyPart();
      log.info("content in EmailManager: "+content);
      
      //main.setDataHandler(new DataHandler(content, mime));
      
      main.setContent(content, "text/html");
      
      Multipart body = new MimeMultipart();

      body.addBodyPart(main);
      log.info("Multipart body: "+body);
      // Set the message body.
     msg.setContent(body);
     
     Transport.send(msg);

      // Ack.
      if (log.isDebugEnabled() == true)
      {
        log.debug("Email message was sent.");
      }
    }
    catch (MessagingException e)
    {
      log.error("EmailManager Failed to send message: " , e);
    }
    catch (Exception e)
    {
    	log.error("Exception while sending message: " , e);
    }
  }

  /**
   * @param from
   * @param to
   * @param cc
   * @param subject
   * @param mime
   * @param content
   * @param attachments
   * @throws EmailException
   */
  /*public static void sendEmail(String from, List to, List cc, String subject, String mime,
    String content, List attachments) throws EmailException
  {
	  
	  sendEmail(PropertiesCache.getInstance().getProperty("EMAIL_PROTOCOL"),
	    		PropertiesCache.getInstance().getProperty("EMAIL_SECURE_CONNECTION_TYPE"),
	    		PropertiesCache.getInstance().getProperty("EMAIL_HOST"),
	    		PropertiesCache.getInstance().getProperty("EMAIL_PORT"),
	    		PropertiesCache.getInstance().getProperty("EMAIL_USERNAME"),
	    		PropertiesCache.getInstance().getProperty("EMAIL_PASSWORD"), from, to, cc, subject, mime, content, attachments);  
	  
    sendEmail(smCfg.getString(PropertyConstants.EMAIL_PROTOCOL),
      smCfg.getString(PropertyConstants.EMAIL_SECURE_CONNECTION_TYPE),
      smCfg.getString(PropertyConstants.EMAIL_HOST),
      smCfg.getInt(PropertyConstants.EMAIL_PORT),
      smCfg.getString(PropertyConstants.EMAIL_USERNAME),
      smCfg.getString(PropertyConstants.EMAIL_PASSWORD), from, to, cc, subject, mime, content, attachments);
  }*/

  /**
   * @param from
   * @param to
   * @param subject
   * @param content
   * @throws EmailException
   */
  /*public static void sendEmail(String from, List to, String subject, String content)
    throws EmailException
  {

    // No CC list, default mime type, and no attachments.
    sendEmail(from, to, null, subject, null, content, null);
  }*/

  /**
   * @param from
   * @param to
   * @param cc
   * @param subject
   * @param content
   * @throws EmailException
   */
 /* public static void sendEmail(String from, List to, List cc, String subject, String content)
    throws EmailException
  {

    // No CC list, default mime type, and no attachments.
    sendEmail(from, to, cc, subject, null, content, null);
  }*/

  /**
   * @param to
   * @param subject
   * @param content
   * @throws EmailException
   */
  /*public static void sendEmail(List to, String subject, String content)
    throws EmailException
  {
    // No CC list, default mime type, and no attachments.
    sendEmail(PropertiesCache.getInstance().getProperty("EMAIL_DFLT_FROM_EMAIL"), to, null, subject, null, content, null);
  }*/

  /**
   * @param to
   * @param cc
   * @param subject
   * @param content
   * @throws EmailException
   */
 /* public static void sendEmail(List to, List cc, String subject, String content)
    throws EmailException
  {
    // Default mime type, and no attachments.
    sendEmail(PropertiesCache.getInstance().getProperty("EMAIL_DFLT_FROM_EMAIL"), to, cc, subject, null, content, null);
  }
*/
  /**
   * @param from
   * @param cc
   * @param subject
   * @param content
   * @throws EmailException
   */
  /*public static void sendErrorEmail(String from, List cc, String subject, String content)
    throws EmailException
  {
    sendEmail(from, Collections.singletonList(smCfg.getString(PropertyConstants.EMAIL_ERROR_EMAIL)), cc, subject, null,
      content, null);
  }*/

  /**
   * 
   * @param from
   * @param to
   * @param subject
   * @param content
   * @param attachments
   * @throws EmailException
   */
  /*public static void sendErrorEmail(String from, List to, String subject, String content, List attachments)
  throws EmailException
  {
	  sendEmail(from, to, null, subject, null, content, attachments);	
  }*/
   
 /**
  * 
  * @param from
  * @param to
  * @param cc
  * @param subject
  * @param content
  * @param attachments
  * @throws EmailException
  */
  /*public static void sendErrorEmail(String from,List to,List cc, String subject, String content, List attachments)
  throws EmailException
  {     // No mime type.
	     sendEmail(from,to,cc, subject, null, content, attachments);
  }*/
  
  
  /**
   * @param from
   * @param to
   * @param subject
   * @throws EmailException
   */
 /* public static void sendErrorEmail(String from, List to, String subject)
    throws EmailException
  {
    sendEmail(from, to, null, subject, null, "", null);
  }*/

  /**
   * @param from
   * @param subject
   * @param content
   * @throws EmailException
   */
  /*public static void sendErrorEmail(String from, String subject, String content)
    throws EmailException
  {
     sendEmail(from, Collections.singletonList(smCfg.getString(PropertyConstants.EMAIL_ERROR_EMAIL)), null, subject,
      null, content, null);
  }*/

  /**
   * @param cc
   * @param subject
   * @param content
   * @throws EmailException
   */
  /*public static void sendErrorEmail(List cc, String subject, String content)
    throws EmailException
  {
    // Default mime type, and no attachments.
      sendEmail(PropertiesCache.getInstance().getProperty("EMAIL_DFLT_FROM_EMAIL"),
      Collections.singletonList(smCfg.getString(PropertyConstants.EMAIL_ERROR_EMAIL)),
      cc, subject, null, content, null);
  }*/
  /**
   * @param cc
   * @param subject
   * @param content
   * @param attachments
   * @throws EmailException
   */
  /*public static void sendErrorEmail(List cc, String subject, String content, List attachments)
  throws EmailException
  {     // No mime type.
	     sendEmail(PropertiesCache.getInstance().getProperty("EMAIL_DFLT_FROM_EMAIL"),
		 Collections.singletonList(smCfg.getString(PropertyConstants.EMAIL_ERROR_EMAIL)),
		 cc, subject, null, content, attachments);
  }*/

  /**
   * @param subject
   * @param content
   * @param attachments
   * @throws EmailException
   */
  /*public static void sendErrorEmail(String subject, String content, List attachments)
    throws EmailException
  {
    // No CC list, default mime type, and no attachments.
    sendEmail(PropertiesCache.getInstance().getProperty("EMAIL_DFLT_FROM_EMAIL"),
      Collections.singletonList(smCfg.getString(PropertyConstants.EMAIL_ERROR_EMAIL)),
      null, subject, null, content, attachments);
  }*/
  /**
   * @param subject
   * @param content
   * @throws EmailException
   */
  /*public static void sendErrorEmail(String subject, String content)
    throws EmailException
  {
    // No CC list, default mime type, and no attachments.
	  List<String> errorEmailsList = new ArrayList<String>(Arrays.asList(PropertiesCache.getInstance().getProperty("EMAIL_ERROR_EMAIL").split(",")));
		log.info("Sending Error Email To : " + errorEmailsList.toString());
    sendEmail(PropertiesCache.getInstance().getProperty("EMAIL_DFLT_FROM_EMAIL"),
    		errorEmailsList,
      null, subject, null, content, null);
  }*/

  /**
   * @param path
   * @return FIXDOC
   * @throws MessagingException
   */
  public static BodyPart bodyPartFromFile(String path) throws EmailException
  {
    assert path != null;

    BodyPart attach = new MimeBodyPart();

    try
    {
      // Add attachment.
      attach.setDataHandler(new DataHandler(new FileDataSource(path)));
      attach.setFileName(new File(path).getName());
    }
    catch (MessagingException e)
    {
      throw new EmailException("Failed to create body part from file.", e);
    }

    return attach;
  }

  /**
   * @param file
   * @return FIXDOC
   * @throws MessagingException
   */
  public static BodyPart bodyPartFromFile(File file) throws EmailException
  {
    assert file != null;

    BodyPart attach = new MimeBodyPart();

    try
    {
      // Add attachment.
      attach.setDataHandler(new DataHandler(new FileDataSource(file)));
      attach.setFileName(file.getName());
    }
    catch (MessagingException e)
    {
      throw new EmailException("Failed to create body part from file.", e);
    }

    return attach;
  }

  /**
   * @param name
   * @param arr
   * @return FIXDOC
   * @throws MessagingException
   */
  public static BodyPart bodyPartByteArray(String name, byte[] arr) throws EmailException
  {
    assert arr != null;

    BodyPart attach = new MimeBodyPart();

    try
    {
      // Add attachment.
      attach.setDataHandler(new DataHandler(new EmailByteArrayDataSource(name, arr)));
      attach.setFileName(name);
    }
    catch (IOException e)
    {
      throw new EmailException("Failed to create body part from byte array.", e);
    }
    catch (MessagingException e)
    {
      throw new EmailException("Failed to create body part from byte array.", e);
    }

    return attach;
  }
  
  /**
	 *
	 * @param col
	 * @return FIXDOC
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Collection col) {
		return col == null || col.isEmpty() == true;
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
