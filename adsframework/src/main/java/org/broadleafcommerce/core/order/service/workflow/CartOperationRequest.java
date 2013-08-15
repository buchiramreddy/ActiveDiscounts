/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.order.service.workflow;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;


/**
 * This class represents the basic context necessary for the execution of a particular order process workflow operation.
 *
 * @author   apazzolini
 * @version  $Revision$, $Date$
 */
public class CartOperationRequest {
  /** DOCUMENT ME! */
  protected OrderItemRequestDTO itemRequest;

  /** DOCUMENT ME! */
  protected Order order;

  /** DOCUMENT ME! */
  protected boolean priceOrder;

  // Set during the course of the workflow for use in subsequent workflow steps
  /** DOCUMENT ME! */
  protected OrderItem addedOrderItem;

  // Set during the course of the workflow for use in subsequent workflow steps
  /** DOCUMENT ME! */
  protected Integer orderItemQuantityDelta;

  /**
   * Creates a new CartOperationRequest object.
   *
   * @param  order        DOCUMENT ME!
   * @param  itemRequest  DOCUMENT ME!
   * @param  priceOrder   DOCUMENT ME!
   */
  public CartOperationRequest(Order order, OrderItemRequestDTO itemRequest, boolean priceOrder) {
    setOrder(order);
    setItemRequest(itemRequest);
    setPriceOrder(priceOrder);
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OrderItemRequestDTO getItemRequest() {
    return itemRequest;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  itemRequest  DOCUMENT ME!
   */
  public void setItemRequest(OrderItemRequestDTO itemRequest) {
    this.itemRequest = itemRequest;
  }

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
  public boolean isPriceOrder() {
    return priceOrder;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  priceOrder  DOCUMENT ME!
   */
  public void setPriceOrder(boolean priceOrder) {
    this.priceOrder = priceOrder;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OrderItem getAddedOrderItem() {
    return addedOrderItem;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  addedOrderItem  DOCUMENT ME!
   */
  public void setAddedOrderItem(OrderItem addedOrderItem) {
    this.addedOrderItem = addedOrderItem;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getOrderItemQuantityDelta() {
    return orderItemQuantityDelta;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  orderItemQuantityDelta  DOCUMENT ME!
   */
  public void setOrderItemQuantityDelta(Integer orderItemQuantityDelta) {
    this.orderItemQuantityDelta = orderItemQuantityDelta;
  }

} // end class CartOperationRequest
