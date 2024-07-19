/**
 * File:        ManagerException.java
 * Copyright:   Copyright (c) 2006
 * Created:     23.11.2006
 * Company:     Allconnect, Inc.
 *
 * @author: amagdenko
 * @version $Id: ManagerException.java,v 1.2 2007/11/01 18:27:54 tmartin Exp $
 */

package com.allconnect.gateway;

public class ManagerException extends Exception
{
  /**
   *
   */
  public ManagerException()
  {
    // No-op.
  }

  /**
   * @param msg
   */
  public ManagerException(String msg)
  {
    super(msg);
  }

  /**
   * @param msg
   * @param cause
   */
  public ManagerException(String msg, Throwable cause)
  {
    super(msg, cause);
  }

  /**
   * @see Object#toString()
   */
  public String toString()
  {
    return "Exception [error=" + getLocalizedMessage() + ']';
  }
}
