/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.profile.web.core.security;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.web.BroadleafRequestContext;


/**
 * Cookie used to protected against session fixation attacks.
 *
 * @see      org.broadleafcommerce.profile.web.core.security.SessionFixationProtectionFilter
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class SessionFixationProtectionCookie {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final String COOKIE_NAME = "ActiveID";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected final Log logger = LogFactory.getLog(getClass());

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  response  DOCUMENT ME!
   */
  public static void forceRemove(HttpServletResponse response) {
    Cookie cookie = new Cookie(COOKIE_NAME, "");
    cookie.setMaxAge(0);
    cookie.setPath("/");
    cookie.setSecure(true);
    cookie.setValue("-1");
    response.addCookie(cookie);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static String readActiveID(HttpServletRequest request) {
    String cookieData = null;

    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if (cookie.getName().equals(COOKIE_NAME)) {
          cookieData = cookie.getValue();

          break;
        }
      }
    }

    return cookieData;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  response  DOCUMENT ME!
   */
  public static void remove(HttpServletResponse response) {
    HttpServletRequest request = BroadleafRequestContext.getBroadleafRequestContext().getRequest();

    if ((request != null) && (request.getCookies() != null)) {
      for (Cookie cookie : request.getCookies()) {
        if (cookie.getName().equals(COOKIE_NAME)) {
          cookie.setMaxAge(0);
          cookie.setPath("/");
          cookie.setSecure(true);
          cookie.setValue("-1");
          response.addCookie(cookie);
        }
      }
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  response  DOCUMENT ME!
   * @param  data      DOCUMENT ME!
   */
  public static void writeActiveID(HttpServletResponse response, String data) {
    if (data != null) {
      Cookie cookie = new Cookie(COOKIE_NAME, data);
      cookie.setMaxAge(-1);
      cookie.setSecure(true);
      cookie.setPath("/");
      response.addCookie(cookie);
    }
  }

} // end class SessionFixationProtectionCookie
