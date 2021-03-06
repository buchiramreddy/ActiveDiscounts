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

package org.broadleafcommerce.common.web;

import java.io.IOException;

import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class BroadleafGWTModuleURLMappingResourceHttpRequestHandler extends ResourceHttpRequestHandler {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.web.servlet.resource.ResourceHttpRequestHandler#handleRequest(javax.servlet.http.HttpServletRequest,
   *       javax.servlet.http.HttpServletResponse)
   */
  @Override public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
    IOException {
    Map<String, String> urlPatternDispatchMap = (Map<String, String>) getApplicationContext().getBean(
        "blResourceUrlPatternRequestDispatchMap");

    for (Map.Entry<String, String> entry : urlPatternDispatchMap.entrySet()) {
      RequestMatcher matcher = new AntPathRequestMatcher(entry.getKey());

      if (matcher.matches(request)) {
        request.getRequestDispatcher(entry.getValue()).forward(request, response);

        return;
      }
    }

    super.handleRequest(request, response);
  }

} // end class BroadleafGWTModuleURLMappingResourceHttpRequestHandler
