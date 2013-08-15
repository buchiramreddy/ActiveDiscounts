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

package org.broadleafcommerce.core.web.order;

import java.util.HashMap;

import javax.annotation.Resource;

import org.broadleafcommerce.core.order.dao.OrderDao;
import org.broadleafcommerce.core.order.domain.Order;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * This class is used as a request-scope container for the current orderid. As a result, items that need the order
 * during the control flow of a single request may retrieve the order from this object. OrderState utilizes the DAO to
 * retrieve the full order from its dehydrated state in the Hibernate cache.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class OrderState {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  @Resource(name = "blOrderDao")
  private OrderDao orderDao;

  private final HashMap<Long, Long> orders       = new HashMap<Long, Long>();
  private boolean                   updatePrices = true;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Order getOrder(Customer customer) {
    if (orders.get(customer.getId()) == null) {
      return null;
    }

    Order order = orderDao.readOrderById(orders.get(customer.getId()));

    return order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isUpdatePrices() {
    return updatePrices;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   * @param   order     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Order setOrder(Customer customer, Order order) {
    if ((customer != null) && (order != null)) {
      orders.put(customer.getId(), order.getId());

      if (updatePrices) {
        order = orderDao.updatePrices(order);
      }
    }

    return order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  updatePrices  DOCUMENT ME!
   */
  public void setUpdatePrices(boolean updatePrices) {
    this.updatePrices = updatePrices;
  }

} // end class OrderState
