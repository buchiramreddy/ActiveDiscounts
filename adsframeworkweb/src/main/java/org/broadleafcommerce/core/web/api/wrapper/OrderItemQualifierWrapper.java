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

import org.broadleafcommerce.core.order.domain.OrderItemQualifier;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "orderItemQualifier")
public class OrderItemQualifierWrapper extends BaseWrapper implements APIWrapper<OrderItemQualifier> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected Long offerId;

  /** DOCUMENT ME! */
  @XmlElement protected Long quantity;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.order.domain.OrderItemQualifier,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(OrderItemQualifier model, HttpServletRequest request) {
    this.offerId  = model.getOffer().getId();
    this.quantity = model.getQuantity();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.order.domain.OrderItemQualifier,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(OrderItemQualifier model, HttpServletRequest request) {
    wrapDetails(model, request);
  }

} // end class OrderItemQualifierWrapper
