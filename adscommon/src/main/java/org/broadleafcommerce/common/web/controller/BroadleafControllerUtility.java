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


/**
 * Commonly used Broadleaf Controller operations. - ajaxRedirects - isAjaxRequest - ajaxRender
 *
 * <p>BroadleafAbstractController provides convenience methods for this functionality. Implementors who are not able (or
 * willing) to have their Controllers extend BroadleafAbstractController can utilize this utility class to achieve some
 * of the same benefits.</p>
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class BroadleafControllerUtility {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final String BLC_REDIRECT_ATTRIBUTE = "blc_redirect";

  /** DOCUMENT ME! */
  public static final String BLC_AJAX_PARAMETER = "blcAjax";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * A helper method that returns whether or not the given request was invoked via an AJAX call.
   *
   * <p>Returns true if the request contains the XMLHttpRequest header or a blcAjax=true parameter.</p>
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  - whether or not it was an AJAX request
   */
  public static boolean isAjaxRequest(HttpServletRequest request) {
    String ajaxParameter = request.getParameter(BLC_AJAX_PARAMETER);

    return ((ajaxParameter != null) && "true".equals(ajaxParameter))
      || "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
  }

} // end class BroadleafControllerUtility
