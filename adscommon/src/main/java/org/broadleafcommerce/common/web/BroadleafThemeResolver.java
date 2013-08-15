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

import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.common.site.domain.Site;
import org.broadleafcommerce.common.site.domain.Theme;

import org.springframework.web.context.request.WebRequest;


/**
 * Responsible for returning the theme used by Broadleaf Commerce for the current request. For a single site
 * installation, this should return a theme whose path and name are empty string.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public interface BroadleafThemeResolver {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Theme resolveTheme(WebRequest request);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param       request  DOCUMENT ME!
   * @param       site     DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @deprecated  Use {@link #resolveTheme(org.springframework.web.context.request.WebRequest)} instead
   */
  @Deprecated Theme resolveTheme(HttpServletRequest request, Site site);
} // end interface BroadleafThemeResolver
