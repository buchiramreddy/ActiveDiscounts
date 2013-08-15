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

import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderItemPriceDetail;
import org.broadleafcommerce.core.order.domain.OrderItemQualifier;
import org.broadleafcommerce.core.order.domain.PersonalMessage;
import org.broadleafcommerce.core.order.service.type.OrderItemType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface OrderItemDao {
  /**
   * DOCUMENT ME!
   *
   * @param   orderItemId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderItem readOrderItemById(Long orderItemId);

  /**
   * DOCUMENT ME!
   *
   * @param   orderItem  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderItem save(OrderItem orderItem);

  /**
   * DOCUMENT ME!
   *
   * @param  orderItem  DOCUMENT ME!
   */
  void delete(OrderItem orderItem);

  /**
   * DOCUMENT ME!
   *
   * @param   orderItemType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderItem create(OrderItemType orderItemType);

  /**
   * DOCUMENT ME!
   *
   * @param   orderItem  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderItem saveOrderItem(OrderItem orderItem);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PersonalMessage createPersonalMessage();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderItemPriceDetail createOrderItemPriceDetail();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderItemQualifier createOrderItemQualifier();

  /**
   * Sets the initial orderItemPriceDetail for the item.
   *
   * @param   item  DOCUMENT ME!
   *
   * @return  sets the initial orderItemPriceDetail for the item.
   */
  OrderItemPriceDetail initializeOrderItemPriceDetails(OrderItem item);

} // end interface OrderItemDao
