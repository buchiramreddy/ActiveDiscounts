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

package org.broadleafcommerce.core.order.service.workflow.update;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.OrderItemService;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.workflow.CartOperationContext;
import org.broadleafcommerce.core.order.service.workflow.CartOperationRequest;
import org.broadleafcommerce.core.workflow.BaseActivity;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class ValidateUpdateRequestActivity extends BaseActivity<CartOperationContext> {
  /** DOCUMENT ME! */
  @Resource(name = "blOrderItemService")
  protected OrderItemService orderItemService;

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#execute(org.broadleafcommerce.core.order.service.workflow.CartOperationContext)
   */
  @Override public CartOperationContext execute(CartOperationContext context) throws Exception {
    CartOperationRequest request             = context.getSeedData();
    OrderItemRequestDTO  orderItemRequestDTO = request.getItemRequest();

    // Throw an exception if the user did not specify an orderItemId
    if (orderItemRequestDTO.getOrderItemId() == null) {
      throw new IllegalArgumentException("OrderItemId must be specified when removing from order");
    }

    // Throw an exception if the user tried to update an item to a negative quantity
    if (orderItemRequestDTO.getQuantity() < 0) {
      throw new IllegalArgumentException("Quantity cannot be negative");
    }

    // Throw an exception if the user did not specify an order to add the item to
    if (request.getOrder() == null) {
      throw new IllegalArgumentException("Order is required when updating item quantities");
    }

    // Throw an exception if the user is trying to update an order item that is part of a bundle
    OrderItem orderItem = orderItemService.readOrderItemById(orderItemRequestDTO.getOrderItemId());

    if ((orderItem != null) && (orderItem instanceof DiscreteOrderItem)) {
      DiscreteOrderItem doi = (DiscreteOrderItem) orderItem;

      if (doi.getBundleOrderItem() != null) {
        throw new IllegalArgumentException("Cannot update an item that is part of a bundle");
      }
    }

    return context;
  } // end method execute

} // end class ValidateUpdateRequestActivity
