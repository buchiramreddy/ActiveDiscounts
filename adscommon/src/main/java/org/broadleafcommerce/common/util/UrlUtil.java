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

package org.broadleafcommerce.common.util;

import org.apache.commons.lang.StringUtils;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class UrlUtil {
  //~ Methods ----------------------------------------------------------------------------------------------------------

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
  public static String fixRedirectUrl(String contextPath, String url) {
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

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   toConvert  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static String generateUrlKey(String toConvert) {
    if (toConvert.matches(".*?\\W.*?")) {
      // remove all non-word characters
      String result = toConvert.replaceAll("\\W", "");

      // uncapitalizes the first letter of the url key
      return StringUtils.uncapitalize(result);
    } else {
      return StringUtils.uncapitalize(toConvert);
    }
  }

} // end class UrlUtil
