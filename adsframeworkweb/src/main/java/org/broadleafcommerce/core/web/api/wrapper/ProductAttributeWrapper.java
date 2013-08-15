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

import org.broadleafcommerce.core.catalog.domain.ProductAttribute;


/**
 * To change this template use File | Settings | File Templates.
 *
 * <p>User: Kelly Tisdell Date: 4/10/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "productAttribute")
public class ProductAttributeWrapper extends BaseWrapper implements APIWrapper<ProductAttribute> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected String attributeName;

  /** DOCUMENT ME! */
  @XmlElement protected String attributeValue;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected Long productId;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.catalog.domain.ProductAttribute,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(ProductAttribute model, HttpServletRequest request) {
    this.id             = model.getId();
    this.productId      = model.getProduct().getId();
    this.attributeName  = model.getName();
    this.attributeValue = model.getValue();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.catalog.domain.ProductAttribute,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(ProductAttribute model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class ProductAttributeWrapper
