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

package org.broadleafcommerce.common.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;


/**
 * This controller works in conjunction with the broadleaf-ajax style redirect.
 *
 * <p>The logic is quite complex but solves a problem related to redirects and an Ajax form.</p>
 *
 * <p>It is intended to solve a problem with using an Ajax style login modal along with Spring Security.</p>
 *
 * <p>Spring Security wants to redirect after a successful login. Unfortunately, we can reliably redirect from Spring
 * Security to a page within the BLC system when the login modal is presented in Ajax.</p>
 *
 * <p>To solve this problem, Spring Security can be configured to use the BroadleafWindowLocationRedirectStrategy. That
 * strategy will add an attribute to session for the page you want to redirect to if the request is coming in from an
 * Ajax call. It will then cause a redirect that should be picked up by this controller. This controller will then
 * render a page with the blc-redirect-div. The client-side javaScript (BLC.js) will intercept this code and force the
 * browser to load the new page (e.g. via window.location)</p>
 *
 * @see      BroadleafRedirectStrategy
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class BroadleafRedirectController {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request   DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   * @param   model     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String redirect(HttpServletRequest request, HttpServletResponse response, Model model) {
    String path = (String) request.getSession().getAttribute("BLC_REDIRECT_URL");

    if (path == null) {
      path = request.getContextPath();
    }

    return "ajaxredirect:" + path;
  }
}
