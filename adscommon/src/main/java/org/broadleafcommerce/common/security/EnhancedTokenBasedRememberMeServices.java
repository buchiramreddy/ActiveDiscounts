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

package org.broadleafcommerce.common.security;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.Locale;

import javax.annotation.Resource;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.common.security.util.CookieUtils;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;


/**
 * This class adds additional features to the token based remember me services provided by Spring security.
 * Specifically, we would like to be able to include the httpOnly parameter to cookie values that are generated by
 * Broadleaf Commerce. Since the default implementation provided by Spring Security does not provide this additional
 * functionality, we override here to use the CookieUtils in Broadleaf that will include the httpOnly value.
 *
 * <p>Note - this class does not add httpOnly protection for session cookies. Adding httpOnly for session cookies is
 * handled at the application container configuration level, if supported.</p>
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class EnhancedTokenBasedRememberMeServices extends TokenBasedRememberMeServices {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCookieUtils")
  protected CookieUtils cookieUtils;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new EnhancedTokenBasedRememberMeServices object.
   */
  @Deprecated public EnhancedTokenBasedRememberMeServices() { }

  /**
   * Creates a new EnhancedTokenBasedRememberMeServices object.
   *
   * @param  key                 DOCUMENT ME!
   * @param  userDetailsService  DOCUMENT ME!
   */
  public EnhancedTokenBasedRememberMeServices(String key, UserDetailsService userDetailsService) {
    super(key, userDetailsService);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices#setCookie(java.lang.String[],
   *       int, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  @Override protected void setCookie(String[] tokens, int maxAge, HttpServletRequest request,
    HttpServletResponse response) {
    MockResponse mockResponse = new MockResponse();
    super.setCookie(tokens, maxAge, request, mockResponse);

    Cookie myCookie = mockResponse.getTempCookie();
    cookieUtils.setCookieValue(response, myCookie.getName(), myCookie.getValue(), myCookie.getPath(),
      myCookie.getMaxAge(), myCookie.getSecure());
  }

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  private class MockResponse implements HttpServletResponse {
    //~ Instance fields ------------------------------------------------------------------------------------------------

    private Cookie tempCookie;

    //~ Methods --------------------------------------------------------------------------------------------------------

    @Override public void addCookie(Cookie arg0) {
      this.tempCookie = arg0;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void addDateHeader(String arg0, long arg1) {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void addHeader(String arg0, String arg1) {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void addIntHeader(String arg0, int arg1) {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public boolean containsHeader(String arg0) {
      return false;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public String encodeRedirectUrl(String arg0) {
      return null;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public String encodeRedirectURL(String arg0) {
      return null;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public String encodeUrl(String arg0) {
      return null;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public String encodeURL(String arg0) {
      return null;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void flushBuffer() throws IOException {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public int getBufferSize() {
      return 0;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public String getCharacterEncoding() {
      return null;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public String getContentType() {
      return null;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public Locale getLocale() {
      return null;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public ServletOutputStream getOutputStream() throws IOException {
      return null;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    public Cookie getTempCookie() {
      return tempCookie;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public PrintWriter getWriter() throws IOException {
      return null;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public boolean isCommitted() {
      return false;
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void reset() {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void resetBuffer() {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void sendError(int arg0) throws IOException {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void sendError(int arg0, String arg1) throws IOException {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void sendRedirect(String arg0) throws IOException {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void setBufferSize(int arg0) {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void setCharacterEncoding(String arg0) {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void setContentLength(int arg0) {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void setContentType(String arg0) {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void setDateHeader(String arg0, long arg1) {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void setHeader(String arg0, String arg1) {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void setIntHeader(String arg0, int arg1) {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void setLocale(Locale arg0) {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void setStatus(int arg0) {
      // do nothing
    }

    //~ ----------------------------------------------------------------------------------------------------------------

    @Override public void setStatus(int arg0, String arg1) {
      // do nothing
    }

  } // end class MockResponse
} // end class EnhancedTokenBasedRememberMeServices
