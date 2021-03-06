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

import org.apache.commons.lang3.StringUtils;


/**
 * An abstract controller that provides convenience methods and resource declarations for its children.
 *
 * <p>Operations that are shared between all controllers belong here. To use composition rather than extension,
 * implementors can utilize BroadleafControllerUtility.</p>
 *
 * @see      org.broadleafcommerce.common.web.controller.BroadleafControllerUtility
 * @author   apazzolini
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public abstract class BroadleafAbstractController {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns the current servlet context path. This will return a "/" if the application is deployed as root. If it's
   * not deployed as root, it will return the context path BOTH a leading slash but without a trailing slash.
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  the context path
   */
  protected String getContextPath(HttpServletRequest request) {
    String ctxPath = request.getContextPath();

    if (StringUtils.isBlank(ctxPath)) {
      return "/";
    } else {
      if (ctxPath.charAt(0) != '/') {
        ctxPath = '/' + ctxPath;
      }

      if (ctxPath.charAt(ctxPath.length() - 1) != '/') {
        ctxPath = ctxPath + '/';
      }

      return ctxPath;
    }

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * A helper method that returns whether or not the given request was invoked via an AJAX call.
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  - whether or not it was an AJAX request
   */
  protected boolean isAjaxRequest(HttpServletRequest request) {
    return BroadleafControllerUtility.isAjaxRequest(request);
  }

} // end class BroadleafAbstractController
