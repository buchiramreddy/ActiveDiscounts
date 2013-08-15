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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;

import org.broadleafcommerce.core.catalog.domain.Category;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class CategoryLinkTag extends AbstractCatalogTag {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Category category;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.jsp.tagext.SimpleTagSupport#doTag()
   */
  @Override public void doTag() throws JspException, IOException {
    JspWriter out = getJspContext().getOut();

    if (category != null) {
      out.println(getUrl(category));
    }

    super.doTag();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Category getCategory() {
    return this.category;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  category  DOCUMENT ME!
   */
  public void setCategory(Category category) {
    this.category = category;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   category  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected String getUrl(Category category) {
    PageContext        pageContext = (PageContext) getJspContext();
    HttpServletRequest request     = (HttpServletRequest) pageContext.getRequest();
    StringBuffer       sb          = new StringBuffer();
    sb.append("<a href=\"");
    sb.append(request.getContextPath());
    sb.append("/");
    sb.append(category.getGeneratedUrl());
    sb.append("\">");
    sb.append(category.getName());
    sb.append("</a>");

    return sb.toString();
  }

} // end class CategoryLinkTag
