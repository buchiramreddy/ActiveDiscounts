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

import org.broadleafcommerce.core.order.domain.OrderItemAttribute;


/**
 * API wrapper to wrap Order Item Attributes.
 *
 * @author   Kelly Tisdell
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "orderItemAttribute")
public class OrderItemAttributeWrapper extends BaseWrapper implements APIWrapper<OrderItemAttribute> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected String name;

  /** DOCUMENT ME! */
  @XmlElement protected Long orderItemId;

  /** DOCUMENT ME! */
  @XmlElement protected String value;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.order.domain.OrderItemAttribute,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(OrderItemAttribute model, HttpServletRequest request) {
    this.id          = model.getId();
    this.name        = model.getName();
    this.value       = model.getValue();
    this.orderItemId = model.getOrderItem().getId();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.order.domain.OrderItemAttribute,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(OrderItemAttribute model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class OrderItemAttributeWrapper
