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

package org.broadleafcommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.catalog.domain.ProductOptionValue;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "productOptionAllowedValue")
public class ProductOptionValueWrapper extends BaseWrapper implements APIWrapper<ProductOptionValue> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected String attributeValue;

  /** DOCUMENT ME! */
  @XmlElement protected Money priceAdjustment;

  /** DOCUMENT ME! */
  @XmlElement protected Long productOptionId;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.catalog.domain.ProductOptionValue,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(ProductOptionValue model, HttpServletRequest request) {
    this.attributeValue  = model.getAttributeValue();
    this.priceAdjustment = model.getPriceAdjustment();
    this.productOptionId = model.getProductOption().getId();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.catalog.domain.ProductOptionValue,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(ProductOptionValue model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class ProductOptionValueWrapper
