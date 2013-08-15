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

package org.broadleafcommerce.core.web.catalog.taglib;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.broadleafcommerce.core.catalog.service.CatalogService;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public abstract class AbstractCatalogTag extends SimpleTagSupport {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  // TODO scc: test if @Resource will somehow work with this reference
  /** DOCUMENT ME! */
  protected CatalogService catalogService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   key  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static String toVariableName(String key) {
    return key.replace('.', '_');
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  catalogService  DOCUMENT ME!
   */
  public void setCatalogService(CatalogService catalogService) {
    this.catalogService = catalogService;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected CatalogService getCatalogService() {
    if (catalogService == null) {
      PageContext           pageContext        = (PageContext) getJspContext();
      WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(
          pageContext.getServletContext());
      catalogService = (CatalogService) applicationContext.getBean("blCatalogService");
    }

    return catalogService;
  }
} // end class AbstractCatalogTag
