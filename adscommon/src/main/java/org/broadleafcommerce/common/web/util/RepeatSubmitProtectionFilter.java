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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class RepeatSubmitProtectionFilter implements Filter {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Map<String, List<String>> requests = new HashMap<String, List<String>>(100);

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.Filter#destroy()
   */
  @Override public void destroy() {
    // do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
   *       javax.servlet.FilterChain)
   */
  @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
    throws IOException, ServletException {
    String sessionId;
    String requestURI;

    synchronized (requests) {
      sessionId  = ((HttpServletRequest) request).getSession().getId();
      requestURI = ((HttpServletRequest) request).getRequestURI();

      if (requests.containsKey(sessionId) && requests.get(sessionId).contains(requestURI)) {
        // we are currently already processing this request
        ((HttpServletResponse) response).setStatus(HttpServletResponse.SC_NO_CONTENT);

        return;
      }

      List<String> myRequests = requests.get(sessionId);

      if (myRequests == null) {
        myRequests = new ArrayList<String>();
        requests.put(sessionId, myRequests);
      }

      myRequests.add(requestURI);
    }

    try {
      chain.doFilter(request, response);
    } finally {
      synchronized (requests) {
        List<String> myRequests = requests.get(sessionId);
        myRequests.remove(requestURI);

        if (myRequests.isEmpty()) {
          requests.remove(sessionId);
        }
      }
    }
  } // end method doFilter

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.Filter#init(javax.servlet.FilterConfig)
   */
  @Override public void init(FilterConfig arg0) throws ServletException {
    // do nothing
  }

} // end class RepeatSubmitProtectionFilter
