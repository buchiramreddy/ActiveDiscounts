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
package org.broadleafcommerce.common.web.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;


/**
 * Code is largely copied from StackOverflow post made by David Rabinowitz with contributions by others in the same
 * thread. Overrides all status setting methods and retains the status.<br>
 * <br>
 *
 *
 * <p>
 * http://stackoverflow.com/questions/1302072/how-can-i-get-the-http-status-code-out-of-a-servletresponse-in-a-servletfilter
 * <br>
 * <br>
 * </p>
 *
 * <p>This won't be needed with Servlet 3.0.<br>
 * <br>
 * </p>
 *
 * <p>Addeded by bpolster.</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class StatusExposingServletResponse extends HttpServletResponseWrapper {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private int httpStatus = 200;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new StatusExposingServletResponse object.
   *
   * @param  response  DOCUMENT ME!
   */
  public StatusExposingServletResponse(HttpServletResponse response) {
    super(response);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getStatus() {
    return httpStatus;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.ServletResponseWrapper#reset()
   */
  @Override public void reset() {
    super.reset();
    this.httpStatus = SC_OK;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.http.HttpServletResponseWrapper#sendError(int)
   */
  @Override public void sendError(int sc) throws IOException {
    httpStatus = sc;
    super.sendError(sc);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.http.HttpServletResponseWrapper#sendError(int, java.lang.String)
   */
  @Override public void sendError(int sc, String msg) throws IOException {
    httpStatus = sc;
    super.sendError(sc, msg);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.http.HttpServletResponseWrapper#setStatus(int)
   */
  @Override public void setStatus(int sc) {
    httpStatus = sc;
    super.setStatus(sc);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.http.HttpServletResponseWrapper#setStatus(int, java.lang.String)
   */
  @Override public void setStatus(int status, String string) {
    super.setStatus(status, string);
    this.httpStatus = status;
  }

} // end class StatusExposingServletResponse
