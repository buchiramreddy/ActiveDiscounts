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

package org.broadleafcommerce.common;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.common.presentation.AdminPresentation;

import org.springframework.web.context.request.WebRequest;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class RequestDTOImpl implements RequestDTO, Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  @AdminPresentation(friendlyName = "RequestDTOImpl_Full_Url")
  private String fullUrlWithQueryString;

  @AdminPresentation(friendlyName = "RequestDTOImpl_Request_URI")
  private String requestURI;

  @AdminPresentation(friendlyName = "RequestDTOImpl_Is_Secure")
  private Boolean secure;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new RequestDTOImpl object.
   *
   * @param  request  DOCUMENT ME!
   */
  public RequestDTOImpl(HttpServletRequest request) {
    requestURI             = request.getRequestURI();
    fullUrlWithQueryString = request.getRequestURL().toString();
    secure                 = ("HTTPS".equalsIgnoreCase(request.getScheme()) || request.isSecure());
  }

  /**
   * Creates a new RequestDTOImpl object.
   *
   * @param  request  DOCUMENT ME!
   */
  public RequestDTOImpl(WebRequest request) {
    // Page level targeting does not work for WebRequest.
    secure = request.isSecure();
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns the URL and parameters.
   *
   * @return  Returns the URL and parameters.
   */
  @Override public String getFullUrLWithQueryString() {
    return fullUrlWithQueryString;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getFullUrlWithQueryString() {
    return fullUrlWithQueryString;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the request not including the protocol, domain, or query string.
   *
   * @return  returns the request not including the protocol, domain, or query string
   */
  @Override public String getRequestURI() {
    return requestURI;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getSecure() {
    return secure;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * True if this request came in through HTTPS.
   *
   * @return  true if this request came in through HTTPS
   */
  @Override public Boolean isSecure() {
    return secure;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fullUrlWithQueryString  DOCUMENT ME!
   */
  public void setFullUrlWithQueryString(String fullUrlWithQueryString) {
    this.fullUrlWithQueryString = fullUrlWithQueryString;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  requestURI  DOCUMENT ME!
   */
  public void setRequestURI(String requestURI) {
    this.requestURI = requestURI;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  secure  DOCUMENT ME!
   */
  public void setSecure(Boolean secure) {
    this.secure = secure;
  }

} // end class RequestDTOImpl
