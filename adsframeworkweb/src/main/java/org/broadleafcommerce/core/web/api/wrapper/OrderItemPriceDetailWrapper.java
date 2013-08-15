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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment;
import org.broadleafcommerce.core.order.domain.OrderItemPriceDetail;


/**
 * API wrapper to wrap Order Item Price Details.
 *
 * @author   Priyesh Patel
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "orderItemAttribute")
public class OrderItemPriceDetailWrapper extends BaseWrapper implements APIWrapper<OrderItemPriceDetail> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected Long        id;

  /** DOCUMENT ME! */
  @XmlElement(name = "adjustment")
  @XmlElementWrapper(name = "adjustments")
  protected List<AdjustmentWrapper> orderItemPriceDetailAdjustments = new LinkedList<AdjustmentWrapper>();

  /** DOCUMENT ME! */
  @XmlElement protected Integer quantity;

  /** DOCUMENT ME! */
  @XmlElement protected Money totalAdjustedPrice;

  /** DOCUMENT ME! */
  @XmlElement protected Money totalAdjustmentValue;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.order.domain.OrderItemPriceDetail,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(OrderItemPriceDetail model, HttpServletRequest request) {
    this.id                   = model.getId();
    this.quantity             = model.getQuantity();
    this.totalAdjustmentValue = model.getTotalAdjustmentValue();
    this.totalAdjustedPrice   = model.getTotalAdjustedPrice();

    if (!model.getOrderItemPriceDetailAdjustments().isEmpty()) {
      this.orderItemPriceDetailAdjustments = new ArrayList<AdjustmentWrapper>();

      for (OrderItemPriceDetailAdjustment orderItemPriceDetail : model.getOrderItemPriceDetailAdjustments()) {
        AdjustmentWrapper orderItemPriceDetailAdjustmentWrapper = (AdjustmentWrapper) context.getBean(
            AdjustmentWrapper.class.getName());
        orderItemPriceDetailAdjustmentWrapper.wrapSummary(orderItemPriceDetail, request);
        this.orderItemPriceDetailAdjustments.add(orderItemPriceDetailAdjustmentWrapper);
      }
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.order.domain.OrderItemPriceDetail,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(OrderItemPriceDetail model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class OrderItemPriceDetailWrapper
