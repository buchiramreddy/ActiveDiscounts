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

import org.broadleafcommerce.core.catalog.dao.SkuDao;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.order.OrderItemDataProvider;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.GiftWrapOrderItem;
import org.broadleafcommerce.core.order.domain.OrderItem;

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
public class OrderItemDaoTest extends BaseTest {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Long giftWrapItemId;

  @Resource private OrderItemDao orderItemDao;

  private Long orderItemId;

  @Resource private SkuDao skuDao;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  orderItem  DOCUMENT ME!
   */
  @Rollback(false)
  @Test(
    groups            = { "createDiscreteOrderItem" },
    dataProvider      = "basicDiscreteOrderItem",
    dataProviderClass = OrderItemDataProvider.class,
    dependsOnGroups   = { "createOrder", "createSku" }
  )
  @Transactional public void createDiscreteOrderItem(DiscreteOrderItem orderItem) {
    Sku si = skuDao.readFirstSku();
    assert si.getId() != null;
    orderItem.setSku(si);
    assert orderItem.getId() == null;

    orderItem = (DiscreteOrderItem) orderItemDao.save(orderItem);
    assert orderItem.getId() != null;
    orderItemId = orderItem.getId();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  orderItem  DOCUMENT ME!
   */
  @Rollback(false)
  @Test(
    groups            = { "createGiftWrapOrderItem" },
    dataProvider      = "basicGiftWrapOrderItem",
    dataProviderClass = OrderItemDataProvider.class,
    dependsOnGroups   = { "readOrderItemsById" }
  )
  @Transactional public void createGiftWrapOrderItem(GiftWrapOrderItem orderItem) {
    Sku si = skuDao.readFirstSku();
    assert si.getId() != null;
    orderItem.setSku(si);
    assert orderItem.getId() == null;

    OrderItem discreteItem = orderItemDao.readOrderItemById(orderItemId);
    orderItem.getWrappedItems().add(discreteItem);
    discreteItem.setGiftWrapOrderItem(orderItem);

    orderItem = (GiftWrapOrderItem) orderItemDao.save(orderItem);
    assert orderItem.getId() != null;
    giftWrapItemId = orderItem.getId();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Rollback(false)
  @Test(
    groups          = { "deleteGiftWrapOrderItemsById" },
    dependsOnGroups = { "readGiftWrapOrderItemsById" }
  )
  public void deleteGiftWrapOrderItemsById() {
    OrderItem result = orderItemDao.readOrderItemById(giftWrapItemId);
    orderItemDao.delete(result);
    assert orderItemDao.readOrderItemById(giftWrapItemId) == null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = { "readGiftWrapOrderItemsById" },
    dependsOnGroups = { "createGiftWrapOrderItem" }
  )
  @Transactional public void readGiftWrapOrderItemsById() {
    assert giftWrapItemId != null;

    OrderItem result = orderItemDao.readOrderItemById(giftWrapItemId);
    assert result != null;
    assert result.getId().equals(giftWrapItemId);
    assert ((GiftWrapOrderItem) result).getWrappedItems().get(0).getId().equals(orderItemId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = { "readOrderItemsById" },
    dependsOnGroups = { "createDiscreteOrderItem" }
  )
  public void readOrderItemsById() {
    assert orderItemId != null;

    OrderItem result = orderItemDao.readOrderItemById(orderItemId);
    assert result != null;
    assert result.getId().equals(orderItemId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test(
    groups          = { "readOrderItemsByIdAfterGiftWrapDeletion" },
    dependsOnGroups = { "deleteGiftWrapOrderItemsById" }
  )
  public void readOrderItemsByIdAfterGiftWrapDeletion() {
    assert orderItemId != null;

    OrderItem result = orderItemDao.readOrderItemById(orderItemId);
    assert result != null;
    assert result.getId().equals(orderItemId);
    assert result.getGiftWrapOrderItem() == null;
  }
} // end class OrderItemDaoTest
