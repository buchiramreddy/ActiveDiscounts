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

package org.broadleafcommerce.core.order.service.workflow.remove;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.domain.BundleOrderItem;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.OrderItemService;
import org.broadleafcommerce.core.order.service.OrderMultishipOptionService;
import org.broadleafcommerce.core.order.service.workflow.CartOperationContext;
import org.broadleafcommerce.core.order.service.workflow.CartOperationRequest;
import org.broadleafcommerce.core.workflow.BaseActivity;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class RemoveOrderMultishipOptionActivity extends BaseActivity<CartOperationContext> {
  /** DOCUMENT ME! */
  @Resource(name = "blOrderMultishipOptionService")
  protected OrderMultishipOptionService orderMultishipOptionService;

  /** DOCUMENT ME! */
  @Resource(name = "blOrderItemService")
  protected OrderItemService orderItemService;

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#execute(org.broadleafcommerce.core.order.service.workflow.CartOperationContext)
   */
  @Override public CartOperationContext execute(CartOperationContext context) throws Exception {
    CartOperationRequest request     = context.getSeedData();
    Long                 orderItemId = request.getItemRequest().getOrderItemId();

    OrderItem orderItem = orderItemService.readOrderItemById(orderItemId);

    if (orderItem instanceof BundleOrderItem) {
      for (OrderItem discrete : ((BundleOrderItem) orderItem).getDiscreteOrderItems()) {
        orderMultishipOptionService.deleteOrderItemOrderMultishipOptions(discrete.getId());
      }
    } else {
      orderMultishipOptionService.deleteOrderItemOrderMultishipOptions(orderItemId);
    }

    return context;
  }

} // end class RemoveOrderMultishipOptionActivity
