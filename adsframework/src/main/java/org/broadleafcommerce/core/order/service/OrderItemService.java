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

package org.broadleafcommerce.core.order.service;

import java.util.HashMap;

import org.broadleafcommerce.core.order.domain.BundleOrderItem;
import org.broadleafcommerce.core.order.domain.DiscreteOrderItem;
import org.broadleafcommerce.core.order.domain.GiftWrapOrderItem;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.PersonalMessage;
import org.broadleafcommerce.core.order.service.call.BundleOrderItemRequest;
import org.broadleafcommerce.core.order.service.call.DiscreteOrderItemRequest;
import org.broadleafcommerce.core.order.service.call.GiftWrapOrderItemRequest;
import org.broadleafcommerce.core.order.service.call.OrderItemRequestDTO;
import org.broadleafcommerce.core.order.service.call.ProductBundleOrderItemRequest;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface OrderItemService {
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
  OrderItem saveOrderItem(OrderItem orderItem);

  /**
   * DOCUMENT ME!
   *
   * @param  item  DOCUMENT ME!
   */
  void delete(OrderItem item);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PersonalMessage createPersonalMessage();

  /**
   * DOCUMENT ME!
   *
   * @param   itemRequest  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  DiscreteOrderItem createDiscreteOrderItem(DiscreteOrderItemRequest itemRequest);

  /**
   * DOCUMENT ME!
   *
   * @param   itemRequest               DOCUMENT ME!
   * @param   skuPricingConsiderations  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  DiscreteOrderItem createDynamicPriceDiscreteOrderItem(final DiscreteOrderItemRequest itemRequest,
    @SuppressWarnings("rawtypes") HashMap skuPricingConsiderations);

  /**
   * DOCUMENT ME!
   *
   * @param   itemRequest  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  GiftWrapOrderItem createGiftWrapOrderItem(GiftWrapOrderItemRequest itemRequest);

  /**
   * Used to create "manual" product bundles. Manual product bundles are primarily designed for grouping items in the
   * cart display. Typically ProductBundle will be used to achieve non programmer related bundles.
   *
   * @param   itemRequest  DOCUMENT ME!
   *
   * @return  used to create "manual" product bundles.
   */
  BundleOrderItem createBundleOrderItem(BundleOrderItemRequest itemRequest);

  /**
   * DOCUMENT ME!
   *
   * @param   itemRequest  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  BundleOrderItem createBundleOrderItem(ProductBundleOrderItemRequest itemRequest);

  /**
   * Creates an OrderItemRequestDTO object that most closely resembles the given OrderItem. That is, it will copy the
   * SKU and quantity and attempt to copy the product and category if they exist.
   *
   * @param   item  the item to copy
   *
   * @return  the OrderItemRequestDTO that mirrors the item
   */
  OrderItemRequestDTO buildOrderItemRequestDTOFromOrderItem(OrderItem item);


} // end interface OrderItemService
