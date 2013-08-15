/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.web;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class BroadleafResponseWrapper implements HttpServletResponse {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private HttpServletResponse response;
  private int                 status;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new BroadleafResponseWrapper object.
   *
   * @param  response  DOCUMENT ME!
   */
  public BroadleafResponseWrapper(HttpServletResponse response) {
    this.response = response;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  arg0  DOCUMENT ME!
   *
   * @see    javax.servlet.http.HttpServletResponse#addCookie(javax.servlet.http.Cookie)
   */
  @Override public void addCookie(Cookie arg0) {
    response.addCookie(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  arg0  DOCUMENT ME!
   * @param  arg1  DOCUMENT ME!
   *
   * @see    javax.servlet.http.HttpServletResponse#addDateHeader(String, long)
   */
  @Override public void addDateHeader(String arg0, long arg1) {
    response.addDateHeader(arg0, arg1);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  arg0  DOCUMENT ME!
   * @param  arg1  DOCUMENT ME!
   *
   * @see    javax.servlet.http.HttpServletResponse#addHeader(String, String)
   */
  @Override public void addHeader(String arg0, String arg1) {
    response.addHeader(arg0, arg1);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  arg0  DOCUMENT ME!
   * @param  arg1  DOCUMENT ME!
   *
   * @see    javax.servlet.http.HttpServletResponse#addIntHeader(String, int)
   */
  @Override public void addIntHeader(String arg0, int arg1) {
    response.addIntHeader(arg0, arg1);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   arg0  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @see     javax.servlet.http.HttpServletResponse#containsHeader(String)
   */
  @Override public boolean containsHeader(String arg0) {
    return response.containsHeader(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param       arg0  DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @deprecated  DOCUMENT ME!
   * @see         javax.servlet.http.HttpServletResponse#encodeRedirectUrl(String)
   */
  @Override public String encodeRedirectUrl(String arg0) {
    return response.encodeRedirectUrl(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   arg0  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @see     javax.servlet.http.HttpServletResponse#encodeRedirectURL(String)
   */
  @Override public String encodeRedirectURL(String arg0) {
    return response.encodeRedirectURL(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param       arg0  DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @deprecated  DOCUMENT ME!
   * @see         javax.servlet.http.HttpServletResponse#encodeUrl(String)
   */
  @Override public String encodeUrl(String arg0) {
    return response.encodeUrl(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   arg0  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @see     javax.servlet.http.HttpServletResponse#encodeURL(String)
   */
  @Override public String encodeURL(String arg0) {
    return response.encodeURL(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @throws  java.io.IOException
   *
   * @see     javax.servlet.ServletResponse#flushBuffer()
   */
  @Override public void flushBuffer() throws IOException {
    response.flushBuffer();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @see     javax.servlet.ServletResponse#getBufferSize()
   */
  @Override public int getBufferSize() {
    return response.getBufferSize();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @see     javax.servlet.ServletResponse#getCharacterEncoding()
   */
  @Override public String getCharacterEncoding() {
    return response.getCharacterEncoding();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @see     javax.servlet.ServletResponse#getContentType()
   */
  @Override public String getContentType() {
    return response.getContentType();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @see     javax.servlet.ServletResponse#getLocale()
   */
  @Override public Locale getLocale() {
    return response.getLocale();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  java.io.IOException
   *
   * @see     javax.servlet.ServletResponse#getOutputStream()
   */
  @Override public ServletOutputStream getOutputStream() throws IOException {
    return response.getOutputStream();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getStatus() {
    return status;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  java.io.IOException
   *
   * @see     javax.servlet.ServletResponse#getWriter()
   */
  @Override public PrintWriter getWriter() throws IOException {
    return response.getWriter();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @see     javax.servlet.ServletResponse#isCommitted()
   */
  @Override public boolean isCommitted() {
    return response.isCommitted();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.ServletResponse#reset()
   */
  @Override public void reset() {
    response.reset();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.ServletResponse#resetBuffer()
   */
  @Override public void resetBuffer() {
    response.resetBuffer();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param   arg0  DOCUMENT ME!
   *
   * @throws  java.io.IOException
   *
   * @see     javax.servlet.http.HttpServletResponse#sendError(int)
   */
  @Override public void sendError(int arg0) throws IOException {
    response.sendError(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param   arg0  DOCUMENT ME!
   * @param   arg1  DOCUMENT ME!
   *
   * @throws  java.io.IOException
   *
   * @see     javax.servlet.http.HttpServletResponse#sendError(int, String)
   */
  @Override public void sendError(int arg0, String arg1) throws IOException {
    response.sendError(arg0, arg1);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param   arg0  DOCUMENT ME!
   *
   * @throws  java.io.IOException
   *
   * @see     javax.servlet.http.HttpServletResponse#sendRedirect(String)
   */
  @Override public void sendRedirect(String arg0) throws IOException {
    response.sendRedirect(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  arg0  DOCUMENT ME!
   *
   * @see    javax.servlet.ServletResponse#setBufferSize(int)
   */
  @Override public void setBufferSize(int arg0) {
    response.setBufferSize(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  arg0  DOCUMENT ME!
   *
   * @see    javax.servlet.ServletResponse#setCharacterEncoding(String)
   */
  @Override public void setCharacterEncoding(String arg0) {
    response.setCharacterEncoding(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  arg0  DOCUMENT ME!
   *
   * @see    javax.servlet.ServletResponse#setContentLength(int)
   */
  @Override public void setContentLength(int arg0) {
    response.setContentLength(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  arg0  DOCUMENT ME!
   *
   * @see    javax.servlet.ServletResponse#setContentType(String)
   */
  @Override public void setContentType(String arg0) {
    response.setContentType(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  arg0  DOCUMENT ME!
   * @param  arg1  DOCUMENT ME!
   *
   * @see    javax.servlet.http.HttpServletResponse#setDateHeader(String, long)
   */
  @Override public void setDateHeader(String arg0, long arg1) {
    response.setDateHeader(arg0, arg1);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  arg0  DOCUMENT ME!
   * @param  arg1  DOCUMENT ME!
   *
   * @see    javax.servlet.http.HttpServletResponse#setHeader(String, String)
   */
  @Override public void setHeader(String arg0, String arg1) {
    response.setHeader(arg0, arg1);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  arg0  DOCUMENT ME!
   * @param  arg1  DOCUMENT ME!
   *
   * @see    javax.servlet.http.HttpServletResponse#setIntHeader(String, int)
   */
  @Override public void setIntHeader(String arg0, int arg1) {
    response.setIntHeader(arg0, arg1);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  arg0  DOCUMENT ME!
   *
   * @see    javax.servlet.ServletResponse#setLocale(java.util.Locale)
   */
  @Override public void setLocale(Locale arg0) {
    response.setLocale(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  arg0  DOCUMENT ME!
   *
   * @see    javax.servlet.http.HttpServletResponse#setStatus(int)
   */
  @Override public void setStatus(int arg0) {
    this.status = arg0;
    response.setStatus(arg0);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param       arg0  DOCUMENT ME!
   * @param       arg1  DOCUMENT ME!
   *
   * @deprecated  DOCUMENT ME!
   * @see         javax.servlet.http.HttpServletResponse#setStatus(int, String)
   */
  @Override public void setStatus(int arg0, String arg1) {
    this.status = arg0;
    response.setStatus(arg0, arg1);
  }

} // end class BroadleafResponseWrapper
