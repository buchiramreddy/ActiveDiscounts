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

package org.broadleafcommerce.common.util;

import org.springframework.web.context.request.WebRequest;


/**
 * Convenience methods for interacting with the request.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class BLCRequestUtils {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static String OK_TO_USE_SESSION = "blOkToUseSession";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Get header or url parameter. Will obtain the parameter from a header variable or a URL parameter, preferring header
   * values if they are set.
   *
   * @param   request  DOCUMENT ME!
   * @param   varName  DOCUMENT ME!
   *
   * @return  get header or url parameter.
   */
  public static String getURLorHeaderParameter(WebRequest request, String varName) {
    String returnValue = request.getHeader(varName);

    if (returnValue == null) {
      returnValue = request.getParameter(varName);
    }

    return returnValue;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Broadleaf "Resolver" and "Filter" classes may need to know if they are allowed to utilize the session. BLC uses a
   * pattern where we will store an attribute in the request indicating whether or not the session can be used. For
   * example, when using the REST APIs, we typically do not want to utilize the session.
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  broadleaf "Resolver" and "Filter" classes may need to know if they are allowed to utilize the session.
   */
  public static boolean isOKtoUseSession(WebRequest request) {
    Boolean useSessionForRequestProcessing = (Boolean) request.getAttribute(OK_TO_USE_SESSION,
        WebRequest.SCOPE_REQUEST);

    if (useSessionForRequestProcessing == null) {
      // by default we will use the session
      return true;
    } else {
      return useSessionForRequestProcessing.booleanValue();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets whether or not Broadleaf can utilize the session in request processing. Used by the REST API flow so that
   * RESTful calls do not utilize the session.
   *
   * @param  request  DOCUMENT ME!
   * @param  value    DOCUMENT ME!
   */
  public static void setOKtoUseSession(WebRequest request, Boolean value) {
    request.setAttribute(OK_TO_USE_SESSION, value, WebRequest.SCOPE_REQUEST);
  }
} // end class BLCRequestUtils
