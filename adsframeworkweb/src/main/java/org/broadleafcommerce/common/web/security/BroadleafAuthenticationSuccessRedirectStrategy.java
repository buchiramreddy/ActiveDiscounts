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
package org.broadleafcommerce.common.web.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.common.web.controller.BroadleafControllerUtility;

import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

import org.springframework.stereotype.Component;


/**
 * If the incoming request is an ajax request, the system will add the desired redirect path to the session and then
 * redirect to the path configured for the redirectPath property.
 *
 * <p>It is assumed that the redirectPath will be picked up by the BroadleafRedirectController.</p>
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
@Component("blAuthenticationSuccessRedirectStrategy")
public class BroadleafAuthenticationSuccessRedirectStrategy implements RedirectStrategy {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String           redirectPath     = "/redirect";
  private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getRedirectPath() {
    return redirectPath;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public RedirectStrategy getRedirectStrategy() {
    return redirectStrategy;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.security.web.RedirectStrategy#sendRedirect(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,
   *       java.lang.String)
   */
  @Override public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url)
    throws IOException {
    if (BroadleafControllerUtility.isAjaxRequest(request)) {
      request.getSession().setAttribute("BLC_REDIRECT_URL", url);
      url = getRedirectPath();
    }

    redirectStrategy.sendRedirect(request, response, url);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  redirectPath  DOCUMENT ME!
   */
  public void setRedirectPath(String redirectPath) {
    this.redirectPath = redirectPath;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  redirectStrategy  DOCUMENT ME!
   */
  public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
    this.redirectStrategy = redirectStrategy;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   url  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String updateLoginErrorUrlForAjax(String url) {
    String blcAjax = BroadleafControllerUtility.BLC_AJAX_PARAMETER;

    if ((url != null) && (url.indexOf("?") > 0)) {
      url = url + "&" + blcAjax + "=true";
    } else {
      url = url + "?" + blcAjax + "=true";
    }

    return url;
  }
} // end class BroadleafAuthenticationSuccessRedirectStrategy
