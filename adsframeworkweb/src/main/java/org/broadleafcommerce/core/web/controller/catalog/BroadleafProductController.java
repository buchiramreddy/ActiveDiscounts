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

package org.broadleafcommerce.core.web.controller.catalog;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.broadleafcommerce.common.web.controller.BroadleafAbstractController;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.web.catalog.ProductHandlerMapping;

import org.hibernate.tool.hbm2x.StringUtils;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


/**
 * This class works in combination with the CategoryHandlerMapping which finds a category based upon the passed in URL.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class BroadleafProductController extends BroadleafAbstractController implements Controller {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static String MODEL_ATTRIBUTE_NAME = "product";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String defaultProductView = "catalog/product";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getDefaultProductView() {
    return defaultProductView;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.web.servlet.mvc.Controller#handleRequest(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
   */
  @Override public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
    throws Exception {
    ModelAndView model   = new ModelAndView();
    Product      product = (Product) request.getAttribute(ProductHandlerMapping.CURRENT_PRODUCT_ATTRIBUTE_NAME);
    assert (product != null);

    model.addObject(MODEL_ATTRIBUTE_NAME, product);

    if (StringUtils.isNotEmpty(product.getDisplayTemplate())) {
      model.setViewName(product.getDisplayTemplate());
    } else {
      model.setViewName(getDefaultProductView());
    }

    return model;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  defaultProductView  DOCUMENT ME!
   */
  public void setDefaultProductView(String defaultProductView) {
    this.defaultProductView = defaultProductView;
  }

} // end class BroadleafProductController
