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

import org.broadleafcommerce.core.catalog.domain.Product;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class ProductLinkTag extends CategoryLinkTag {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Product product;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.catalog.taglib.CategoryLinkTag#doTag()
   */
  @Override public void doTag() throws JspException, IOException {
    JspWriter out = getJspContext().getOut();
    out.println(getUrl(product));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  product  DOCUMENT ME!
   */
  public void setProduct(Product product) {
    this.product = product;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   product  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected String getUrl(Product product) {
    PageContext        pageContext = (PageContext) getJspContext();
    HttpServletRequest request     = (HttpServletRequest) pageContext.getRequest();
    StringBuffer       sb          = new StringBuffer();
    sb.append("<a class=\"noTextUnderline\"  href=\"");
    sb.append(request.getContextPath());
    sb.append("/");
    sb.append((getCategory() == null) ? product.getDefaultCategory().getGeneratedUrl()
                                      : getCategory().getGeneratedUrl());
    sb.append("?productId=" + product.getId());
    sb.append("\">");
    sb.append(product.getName());
    sb.append("</a>");

    return sb.toString();
  }

} // end class ProductLinkTag
