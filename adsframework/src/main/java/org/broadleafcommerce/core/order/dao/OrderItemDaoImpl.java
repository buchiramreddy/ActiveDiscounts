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

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.core.order.domain.GiftWrapOrderItem;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderItemImpl;
import org.broadleafcommerce.core.order.domain.OrderItemPriceDetail;
import org.broadleafcommerce.core.order.domain.OrderItemPriceDetailImpl;
import org.broadleafcommerce.core.order.domain.OrderItemQualifier;
import org.broadleafcommerce.core.order.domain.OrderItemQualifierImpl;
import org.broadleafcommerce.core.order.domain.PersonalMessage;
import org.broadleafcommerce.core.order.service.type.OrderItemType;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blOrderItemDao")
public class OrderItemDaoImpl implements OrderItemDao {
  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderItemDao#save(org.broadleafcommerce.core.order.domain.OrderItem)
   */
  @Override public OrderItem save(final OrderItem orderItem) {
    return em.merge(orderItem);
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderItemDao#readOrderItemById(java.lang.Long)
   */
  @Override public OrderItem readOrderItemById(final Long orderItemId) {
    return em.find(OrderItemImpl.class, orderItemId);
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderItemDao#delete(org.broadleafcommerce.core.order.domain.OrderItem)
   */
  @Override public void delete(OrderItem orderItem) {
    if (!em.contains(orderItem)) {
      orderItem = readOrderItemById(orderItem.getId());
    }

    if (GiftWrapOrderItem.class.isAssignableFrom(orderItem.getClass())) {
      final GiftWrapOrderItem giftItem = (GiftWrapOrderItem) orderItem;

      for (OrderItem wrappedItem : giftItem.getWrappedItems()) {
        wrappedItem.setGiftWrapOrderItem(null);
        wrappedItem = save(wrappedItem);
      }
    }

    em.remove(orderItem);
    em.flush();
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderItemDao#create(org.broadleafcommerce.core.order.service.type.OrderItemType)
   */
  @Override public OrderItem create(final OrderItemType orderItemType) {
    final OrderItem item = (OrderItem) entityConfiguration.createEntityInstance(orderItemType.getType());
    item.setOrderItemType(orderItemType);

    return item;
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderItemDao#createPersonalMessage()
   */
  @Override public PersonalMessage createPersonalMessage() {
    PersonalMessage personalMessage = (PersonalMessage) entityConfiguration.createEntityInstance(PersonalMessage.class
        .getName());

    return personalMessage;
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderItemDao#saveOrderItem(org.broadleafcommerce.core.order.domain.OrderItem)
   */
  @Override public OrderItem saveOrderItem(final OrderItem orderItem) {
    return em.merge(orderItem);
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderItemDao#createOrderItemPriceDetail()
   */
  @Override public OrderItemPriceDetail createOrderItemPriceDetail() {
    return new OrderItemPriceDetailImpl();
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderItemDao#createOrderItemQualifier()
   */
  @Override public OrderItemQualifier createOrderItemQualifier() {
    return new OrderItemQualifierImpl();
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderItemDao#initializeOrderItemPriceDetails(org.broadleafcommerce.core.order.domain.OrderItem)
   */
  @Override public OrderItemPriceDetail initializeOrderItemPriceDetails(OrderItem item) {
    OrderItemPriceDetail detail = createOrderItemPriceDetail();
    detail.setOrderItem(item);
    detail.setQuantity(item.getQuantity());
    detail.setUseSalePrice(item.getIsOnSale());
    item.getOrderItemPriceDetails().add(detail);

    return detail;
  }
} // end class OrderItemDaoImpl
