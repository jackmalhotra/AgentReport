/**
 * File:        EmailByteArrayDataSource.java
 * Copyright:   Copyright (c) 2006
 * Created:     12.11.2006
 * Company:     Allconnect, Inc.
 *
 * @author: amagdenko
 * @version $Id: EmailByteArrayDataSource.java,v 1.2 2007/11/01 18:27:55 tmartin Exp $
 */

package com.allconnect.gateway;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.ByteArrayInputStream;
import javax.activation.DataSource;

class EmailByteArrayDataSource  implements DataSource
{
  /** */
  private static final String CONTENT_TYPE = "application/octet-stream";

  /** */
  private String name = null;

  /** */
  private byte[] arr = null;

  /**
   * @param name
   * @param arr
   * @throws IOException
   */
  EmailByteArrayDataSource(String name, byte[] arr) throws IOException
  {
    assert arr != null;
    assert name != null;

    this.name = name;
    this.arr = arr;
  }

  /**
   * @see DataSource#getInputStream()
   */
  public InputStream getInputStream() throws IOException
  {
    return new ByteArrayInputStream(arr);
  }

  /**
   * @see DataSource#getOutputStream()
   */
  public OutputStream getOutputStream() throws IOException
  {
    throw new IOException("Unsupported operation.");
  }

  /**
   * @see DataSource#getContentType()
   */
  public String getContentType()
  {
    return CONTENT_TYPE;
  }

  /**
   * @see DataSource#getName()
   */
  public String getName()
  {
    return name;
  }

  /**
   * @see Object#toString()
   */
  public String toString()
  {
    final StringBuffer buf = new StringBuffer();

    buf.append(getClass().getName());
    buf.append(" [name='").append(name).append('\'');
    buf.append(", content-type='").append(CONTENT_TYPE).append('\'');
    buf.append(']');

    return buf.toString();
  }
}
