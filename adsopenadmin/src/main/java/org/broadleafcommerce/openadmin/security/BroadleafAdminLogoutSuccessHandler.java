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

import org.apache.commons.lang.StringUtils;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;


/**
 * Created by IntelliJ IDEA. User: jfischer Date: 10/20/11 Time: 3:43 PM To change this template use File | Settings |
 * File Templates.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class BroadleafAdminLogoutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler
  implements LogoutSuccessHandler {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.security.web.authentication.logout.LogoutSuccessHandler#onLogoutSuccess(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
   */
  @Override public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
    Authentication authentication) throws IOException, ServletException {
    String targetUrl = determineTargetUrl(request, response);

    if (response.isCommitted()) {
      if (logger.isDebugEnabled()) {
        logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
      }

      return;
    }

    String queryString = request.getQueryString();

    if (!StringUtils.isEmpty(queryString)) {
      targetUrl += "?" + queryString;
    }

    getRedirectStrategy().sendRedirect(request, response, targetUrl);
  }

} // end class BroadleafAdminLogoutSuccessHandler
