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

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.OrderDataProvider;
import org.broadleafcommerce.core.order.domain.Order;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.service.CustomerService;

import org.broadleafcommerce.test.BaseTest;

import org.springframework.test.annotation.Rollback;

import org.springframework.transaction.annotation.Transactional;

import org.testng.annotations.Test;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class OrderDaoTest extends BaseTest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  Long orderId;

  /** DOCUMENT ME! */
  String userName = new String();

  @Resource private CustomerService customerService;

  @Resource private OrderDao orderDao;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  @Rollback(false)
  @Test(
    groups            = { "createOrder" },
    dataProvider      = "basicOrder",
    dataProviderClass = OrderDataProvider.class,
    dependsOnGroups   = { "readCustomer", "createPhone" }
  )
  @Transactional public void createOrder(Order order) {
    userName = "customer1";

    Customer customer = customerService.readCustomerByUsername(userName);
    assert order.getId() == null;
    order.setCustomer(customer);
    order = orderDao.save(order);
    assert order.getId() != null;
    orderId = order.getId();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = { "deleteOrderForCustomer" },
    dependsOnGroups = { "readOrder" }
  )
  @Transactional public void deleteOrderForCustomer() {
    Order order = orderDao.readOrderById(orderId);
    assert order != null;
    assert order.getId() != null;
    orderDao.delete(order);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = { "readOrder" },
    dependsOnGroups = { "createOrder" }
  )
  public void readOrderById() {
    Order result = orderDao.readOrderById(orderId);
    assert result != null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = { "readOrdersForCustomer" },
    dependsOnGroups = { "readCustomer", "createOrder" }
  )
  @Transactional public void readOrdersForCustomer() {
    userName = "customer1";

    Customer    user   = customerService.readCustomerByUsername(userName);
    List<Order> orders = orderDao.readOrdersForCustomer(user.getId());
    assert orders.size() > 0;
  }
} // end class OrderDaoTest
