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

package org.broadleafcommerce.core.order.dao;

import java.util.List;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.type.OrderStatus;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface OrderDao {
  /**
   * DOCUMENT ME!
   *
   * @param   orderId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Order readOrderById(Long orderId);

  /**
   * DOCUMENT ME!
   *
   * @param   customer     DOCUMENT ME!
   * @param   orderStatus  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Order> readOrdersForCustomer(Customer customer, OrderStatus orderStatus);

  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Order> readOrdersForCustomer(Long id);

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   * @param   name      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Order readNamedOrderForCustomer(Customer customer, String name);

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Order readCartForCustomer(Customer customer);

  /**
   * DOCUMENT ME!
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Order save(Order order);

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  void delete(Order order);

  /**
   * DOCUMENT ME!
   *
   * @param   cartOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Order submitOrder(Order cartOrder);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Order create();

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Order createNewCartForCustomer(Customer customer);

  /**
   * DOCUMENT ME!
   *
   * @param   orderNumber  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Order readOrderByOrderNumber(String orderNumber);

  /**
   * DOCUMENT ME!
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Order updatePrices(Order order);

  // removed methods
  // List<Order> readNamedOrdersForcustomer(Customer customer);
  //
  // Order readOrderForCustomer(Long customerId, Long orderId);
  //
  // List<Order> readSubmittedOrdersForCustomer(Customer customer);
  //

} // end interface OrderDao
