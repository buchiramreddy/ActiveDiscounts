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

package org.broadleafcommerce.core.order.strategy;

import org.broadleafcommerce.core.order.service.workflow.CartOperationRequest;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;


/**
 * The methods in this class are invoked by the add and update item to cart workflows. Broadleaf provides two
 * implementations, the default FulfillmentGroupItemStrategyImpl and also a strategy that does nothing to
 * FulifllmentGroupItems, which can be configured by injecting the NullFulfillmentGroupItemStrategyImpl class as the
 * "blFulfillmentGroupItemStrategy" bean.
 *
 * <p>The null strategy would be the approach taken prior to 2.0, where the user was required to manage
 * FulfillmentGroups and FulfillmentGroupItems by themselves. However, the new default implmentation takes care of this
 * for you by ensuring that FG Items and OrderItems stay in sync.</p>
 *
 * <p>Note that even the null strategy <b>WILL</b> remove FulfillmentGroupItems if their corresponding OrderItem is
 * removed to prevent orphaned records.</p>
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public interface FulfillmentGroupItemStrategy {
  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  CartOperationRequest onItemAdded(CartOperationRequest request) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  CartOperationRequest onItemUpdated(CartOperationRequest request) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  CartOperationRequest onItemRemoved(CartOperationRequest request) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  CartOperationRequest verify(CartOperationRequest request) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param  removeEmptyFulfillmentGroups  DOCUMENT ME!
   */
  void setRemoveEmptyFulfillmentGroups(boolean removeEmptyFulfillmentGroups);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isRemoveEmptyFulfillmentGroups();

} // end interface FulfillmentGroupItemStrategy
