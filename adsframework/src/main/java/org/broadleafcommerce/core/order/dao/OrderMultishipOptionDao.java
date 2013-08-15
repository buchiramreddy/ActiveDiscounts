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

package org.broadleafcommerce.core.order.dao;

import java.util.List;

import org.broadleafcommerce.core.order.domain.OrderMultishipOption;


/**
 * Provides support for reading OrderMultishipOptions. The default Broadleaf implementation uses Hibernate to perform
 * the reading.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public interface OrderMultishipOptionDao {
  /**
   * Saves a given OrderMultishipOption. Note that the method will return the new saved instance from Hibernate
   *
   * @param   orderMultishipOption  the OrderMultishipOption to save
   *
   * @return  the saved instance from Hibernate
   */
  OrderMultishipOption save(final OrderMultishipOption orderMultishipOption);

  /**
   * Returns all associated OrderMultishipOptions to the given order.
   *
   * @param   orderId  the order's id to find OrderMultishipOptions for
   *
   * @return  the associated OrderMultishipOptions
   */
  List<OrderMultishipOption> readOrderMultishipOptions(Long orderId);

  /**
   * Returns all associated OrderMultishipOptions to the given OrderItem.
   *
   * @param   orderItemId  the order item's id to find OrderMultishipOptions for
   *
   * @return  the associated OrderMultishipOptions
   */
  List<OrderMultishipOption> readOrderItemOrderMultishipOptions(Long orderItemId);

  /**
   * Creates a new OrderMultishipOption instance.
   *
   * <p>The default Broadleaf implemntation uses the EntityConfiguration to create the appropriate implementation class
   * based on the current configuration</p>
   *
   * @return  the OrderMultishipOption that was just created
   */
  OrderMultishipOption create();

  /**
   * Removes all of the OrderMultishipOptions in the list permanently.
   *
   * @param  options  the options to delete
   */
  void deleteAll(List<OrderMultishipOption> options);


} // end interface OrderMultishipOptionDao
