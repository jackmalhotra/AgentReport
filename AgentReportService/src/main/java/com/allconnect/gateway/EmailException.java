/**
 * File:        EmailException.java
 * Copyright:   Copyright (c) 2006
 * Created:     25.10.2006
 * Company:     Allconnect, Inc.
 *
 * @author: amagdenko
 * @version $Id: EmailException.java,v 1.2 2007/11/01 18:27:55 tmartin Exp $
 */

package com.allconnect.gateway;

import com.allconnect.gateway.ManagerException;;


public class EmailException extends ManagerException
{
  /** */
  private static final long serialVersionUID = -914698344367225309L;

  /**
   * @param msg
   */
  public EmailException(String msg)
  {
    super(msg);
  }

  /**
   * @param msg
   * @param cause
   */
  public EmailException(String msg, Throwable cause)
  {
    super(msg, cause);
  }

  /**
   * @return FIXDOC
   */
  public String toString()
  {
    return "Email exception: " + getMessage();
  }
}
