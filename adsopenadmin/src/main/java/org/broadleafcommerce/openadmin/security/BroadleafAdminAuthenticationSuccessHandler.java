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

package org.broadleafcommerce.openadmin.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import org.springframework.util.StringUtils;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class BroadleafAdminAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private RequestCache requestCache = new HttpSessionRequestCache();

  private final String successUrlParameter = "successUrl=";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler#onAuthenticationSuccess(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
   */
  @Override public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
    Authentication authentication) throws ServletException, IOException {
    SavedRequest savedRequest = requestCache.getRequest(request, response);

    if (savedRequest == null) {
      super.onAuthenticationSuccess(request, response, authentication);

      return;
    }

    String targetUrlParameter = getTargetUrlParameter();

    if (isAlwaysUseDefaultTargetUrl()
          || ((targetUrlParameter != null) && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
      requestCache.removeRequest(request, response);
      super.onAuthenticationSuccess(request, response, authentication);

      return;
    }

    clearAuthenticationAttributes(request);

    // Use the DefaultSavedRequest URL
    String targetUrl = savedRequest.getRedirectUrl();

    // Remove the sessionTimeout flag if necessary
    targetUrl = targetUrl.replace("sessionTimeout=true", "");

    if (targetUrl.charAt(targetUrl.length() - 1) == '?') {
      targetUrl = targetUrl.substring(0, targetUrl.length() - 1);
    }

    if (targetUrl.contains(successUrlParameter)) {
      int successUrlPosistion = targetUrl.indexOf(successUrlParameter) + successUrlParameter.length();
      int nextParamPosistion  = targetUrl.indexOf("&", successUrlPosistion);

      if (nextParamPosistion == -1) {
        targetUrl = targetUrl.substring(successUrlPosistion, targetUrl.length());
      } else {
        targetUrl = targetUrl.substring(successUrlPosistion, nextParamPosistion);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
    }

    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  } // end method onAuthenticationSuccess
} // end class BroadleafAdminAuthenticationSuccessHandler
