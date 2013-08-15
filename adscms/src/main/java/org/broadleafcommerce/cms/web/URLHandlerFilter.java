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

package org.broadleafcommerce.cms.web;

import java.io.IOException;

import javax.annotation.Resource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.cms.url.domain.URLHandler;
import org.broadleafcommerce.cms.url.service.URLHandlerService;
import org.broadleafcommerce.cms.url.type.URLRedirectType;

import org.broadleafcommerce.common.util.UrlUtil;

import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;


/**
 * Responsible for setting up the site and locale used by Broadleaf Commerce components.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
@Component("blURLHandlerFilter")
public class URLHandlerFilter extends OncePerRequestFilter {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  @Resource(name = "blURLHandlerService")
  private URLHandlerService urlHandlerService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse,
   *       javax.servlet.FilterChain)
   */
  @Override protected void doFilterInternal(HttpServletRequest request,
    HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    String contextPath              = request.getContextPath();
    String requestURIWithoutContext;

    if (request.getContextPath() != null) {
      requestURIWithoutContext = request.getRequestURI().substring(request.getContextPath().length());
    } else {
      requestURIWithoutContext = request.getRequestURI();
    }

    URLHandler handler = urlHandlerService.findURLHandlerByURI(requestURIWithoutContext);

    if (handler != null) {
      if (URLRedirectType.FORWARD == handler.getUrlRedirectType()) {
        request.getRequestDispatcher(handler.getNewURL()).forward(request, response);
      } else if (URLRedirectType.REDIRECT_PERM == handler.getUrlRedirectType()) {
        String url = UrlUtil.fixRedirectUrl(contextPath, handler.getNewURL());
        response.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
        response.setHeader("Location", url);
        response.setHeader("Connection", "close");
      } else if (URLRedirectType.REDIRECT_TEMP == handler.getUrlRedirectType()) {
        String url = UrlUtil.fixRedirectUrl(contextPath, handler.getNewURL());
        response.sendRedirect(url);
      }
    } else {
      filterChain.doFilter(request, response);
    }
  } // end method doFilterInternal

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * If the url does not include "//" then the system will ensure that the application context is added to the start of
   * the URL.
   *
   * @param   contextPath  DOCUMENT ME!
   * @param   url          DOCUMENT ME!
   *
   * @return  if the url does not include "//" then the system will ensure that the application context is added to the
   *          start of the URL.
   */
  protected String fixRedirectUrl(String contextPath, String url) {
    if (url.indexOf("//") < 0) {
      if ((contextPath != null) && (!"".equals(contextPath))) {
        if (!url.startsWith("/")) {
          url = "/" + url;
        }

        if (!url.startsWith(contextPath)) {
          url = contextPath + url;
        }
      }
    }

    return url;

  }
} // end class URLHandlerFilter
