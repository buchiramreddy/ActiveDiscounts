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
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.TaxDetail;
import org.broadleafcommerce.core.order.service.OrderItemService;
import org.broadleafcommerce.core.order.service.call.FulfillmentGroupItemRequest;

import org.springframework.context.ApplicationContext;


/**
 * This is a JAXB wrapper around FulfillmentGroupItem.
 *
 * <p>User: Elbert Bautista Date: 4/10/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "fulfillmentGroupItem")
public class FulfillmentGroupItemWrapper extends BaseWrapper implements APIWrapper<FulfillmentGroupItem>,
  APIUnwrapper<FulfillmentGroupItemRequest> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected Long fulfillmentGroupId;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected Long orderItemId;

  /** DOCUMENT ME! */
  @XmlElement protected Integer quantity;

  /** DOCUMENT ME! */
  @XmlElement(name = "taxDetail")
  @XmlElementWrapper(name = "taxDetails")
  protected List<TaxDetailWrapper> taxDetails;

  /** DOCUMENT ME! */
  @XmlElement protected Money totalItemAmount;

  /** DOCUMENT ME! */
  @XmlElement protected Money totalTax;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIUnwrapper#unwrap(javax.servlet.http.HttpServletRequest, org.springframework.context.ApplicationContext)
   */
  @Override public FulfillmentGroupItemRequest unwrap(HttpServletRequest request, ApplicationContext appContext) {
    OrderItemService orderItemService = (OrderItemService) appContext.getBean("blOrderItemService");
    OrderItem        orderItem        = orderItemService.readOrderItemById(this.orderItemId);

    if (orderItem != null) {
      FulfillmentGroupItemRequest fulfillmentGroupItemRequest = new FulfillmentGroupItemRequest();
      fulfillmentGroupItemRequest.setOrderItem(orderItem);
      fulfillmentGroupItemRequest.setQuantity(this.quantity);

      return fulfillmentGroupItemRequest;
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.order.domain.FulfillmentGroupItem,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(FulfillmentGroupItem model, HttpServletRequest request) {
    this.id = model.getId();

    if (model.getFulfillmentGroup() != null) {
      this.fulfillmentGroupId = model.getFulfillmentGroup().getId();
    }

    if (model.getOrderItem() != null) {
      this.orderItemId = model.getOrderItem().getId();
    }

    this.totalTax        = model.getTotalTax();
    this.quantity        = model.getQuantity();
    this.totalItemAmount = model.getTotalItemAmount();

    List<TaxDetail> taxes = model.getTaxes();

    if ((taxes != null) && !taxes.isEmpty()) {
      this.taxDetails = new ArrayList<TaxDetailWrapper>();

      for (TaxDetail detail : taxes) {
        TaxDetailWrapper taxDetailWrapper = (TaxDetailWrapper) context.getBean(TaxDetailWrapper.class.getName());
        taxDetailWrapper.wrapSummary(detail, request);
        this.taxDetails.add(taxDetailWrapper);
      }
    }
  } // end method wrapDetails

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.order.domain.FulfillmentGroupItem,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(FulfillmentGroupItem model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class FulfillmentGroupItemWrapper
