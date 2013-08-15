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

package org.broadleafcommerce.core.order.service.call;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class FulfillmentGroupItemRequest {
  /** DOCUMENT ME! */
  protected Order            order;

  /** DOCUMENT ME! */
  protected FulfillmentGroup fulfillmentGroup;

  /** DOCUMENT ME! */
  protected OrderItem        orderItem;

  /** DOCUMENT ME! */
  protected int              quantity;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Order getOrder() {
    return order;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FulfillmentGroup getFulfillmentGroup() {
    return fulfillmentGroup;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroup  DOCUMENT ME!
   */
  public void setFulfillmentGroup(FulfillmentGroup fulfillmentGroup) {
    this.fulfillmentGroup = fulfillmentGroup;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OrderItem getOrderItem() {
    return orderItem;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  orderItem  DOCUMENT ME!
   */
  public void setOrderItem(OrderItem orderItem) {
    this.orderItem = orderItem;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  quantity  DOCUMENT ME!
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

} // end class FulfillmentGroupItemRequest
