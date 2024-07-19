/**
 * File:        EmailManagerConfiguration.java
 * Copyright:   Copyright (c) 2009
 * Created:     15-Apr-09
 * Company:     Allconnect, Inc.
 *
 * @author: zshaikh
 */

package com.allconnect.gateway;

public class EmailManagerConfiguration
{
  /** */
  private String mHost = null;

  /** */
  private int mPort = -1;

  /** */
  private String mUserName = null;

  /** */
  private String mPasswd = null;

  /** */
  private String mProto = null;

  /** */
  private String mSecureConnType = null;

  /** */
  private String mDefaultMimeType = null;
  
  /** */
  private String mEmailEnabled = null;
  /**
   *
   * @return
   */
  public String getHost() {
      return mHost;
  }

  /**
   *
   * @param host
   */
  public void setHost(String host) {
      mHost = host;
  }

  /**
   *
   * @return
   */
  public int getPort() {
      return mPort;
  }

  /**
   *
   * @param port
   */
  public void setPort(int port) {
      mPort = port;
  }

  /**
   *
   * @return
   */
  public String getUserName() {
      return mUserName;
  }

  /**
   *
   * @param userName
   */
  public void setUserName(String userName) {
      if (userName != null && userName.trim().length() != 0) {
          mUserName = userName;
      }
  }

  /**
   *
   * @return
   */
  public String getPassword() {
      return mPasswd;
  }

  /**
   *
   * @param passwd
   */
  public void setPassword(String passwd) {
      if (passwd != null && passwd.trim().length() != 0) {
          mPasswd = passwd;
      }
  }

  /**
   *
   * @return
   */
  public String getProtocol() {
      return mProto;
  }

  /**
   *
   * @param prot
   */
  public void setProtocol(String proto) {
      mProto = proto;
  }

  /**
   *
   * @return
   */
  public String getSecureConnectionType() {
      return mSecureConnType;
  }

  /**
   *
   * @param secureConnType
   */
  public void setSecureConnectionType(String secureConnType) {
      mSecureConnType = secureConnType;
  }

  /**
   *
   * @return
   */
  public String getDefaultMimeType() {
      return mDefaultMimeType;
  }

  /**
   *
   * @param dfltMimeType
   */
  public void setDefaultMimeType(String defaultMimeType) {
      mDefaultMimeType = defaultMimeType;
  }
  
  
  /**
  *
  * @param emailEnabledType
  */
  public String getEmailEnabled() {
	return mEmailEnabled;
  }
  
  /**
  *
  * @return
  */
  public void setEmailEnabled(String emailEnabled) {
		this.mEmailEnabled = emailEnabled;
  }

/**
   * @see Object#toString()
   */
  public String toString()
  {
    final StringBuffer buf = new StringBuffer();

    buf.append(getClass().getName());
    buf.append(" [host='").append(mHost).append('\'');
    buf.append(", port=").append(mPort);
    buf.append(", userName='").append(mUserName).append('\'');
    buf.append(", passwd='").append(mPasswd).append('\'');
    buf.append(", defaultMimeType='").append(mDefaultMimeType).append('\'');
    buf.append(", proto='").append(mProto).append('\'');
    buf.append(", secureConnType='").append(mSecureConnType).append('\'');
    buf.append(", emailEnabled='").append(mEmailEnabled).append('\'');
    buf.append(']');

    return buf.toString();
  }
}
