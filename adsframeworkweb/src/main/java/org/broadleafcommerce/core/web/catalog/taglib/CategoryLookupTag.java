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

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.core.catalog.domain.Category;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class CategoryLookupTag extends AbstractCatalogTag {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log  LOG              = LogFactory.getLog(CategoryTag.class);
  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String categoryName;
  private String var;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
   */
  @Override public void doTag() throws JspException {
    catalogService = super.getCatalogService();

    Category category = catalogService.findCategoryByName(categoryName);

    if ((category == null) && LOG.isDebugEnabled()) {
      LOG.debug("The category returned was null for categoryName: " + categoryName);
    }

    getJspContext().setAttribute(var, category);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCategoryName() {
    return categoryName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getVar() {
    return var;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  categoryName  DOCUMENT ME!
   */
  public void setCategoryName(String categoryName) {
    this.categoryName = categoryName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  var  DOCUMENT ME!
   */
  public void setVar(String var) {
    this.var = var;
  }

} // end class CategoryLookupTag
