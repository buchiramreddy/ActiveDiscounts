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
import org.broadleafcommerce.common.site.domain.ThemeDTO;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;


/**
 * Returns null for the Site (typical for non-multi-site implementations of Broadleaf Commerce.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class NullBroadleafThemeResolver implements BroadleafThemeResolver {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private final Theme theme = new ThemeDTO();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.web.BroadleafThemeResolver#resolveTheme(org.springframework.web.context.request.WebRequest)
   */
  @Override public Theme resolveTheme(WebRequest request) {
    return theme;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.web.BroadleafThemeResolver#resolveTheme(javax.servlet.http.HttpServletRequest, org.broadleafcommerce.common.site.domain.Site)
   */
  @Override public Theme resolveTheme(HttpServletRequest request, Site site) {
    return resolveTheme(new ServletWebRequest(request));
  }
}
