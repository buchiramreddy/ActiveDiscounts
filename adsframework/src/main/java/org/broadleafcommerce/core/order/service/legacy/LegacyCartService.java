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

package org.broadleafcommerce.core.order.service.legacy;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.call.MergeCartResponse;
import org.broadleafcommerce.core.order.service.call.ReconstructCartResponse;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * This legacy interface should no longer be used as of 2.0.
 *
 * <p>The new interface and implementation are OrderService and OrderServiceImpl</p>
 *
 * @deprecated  DOCUMENT ME!
 * @author      $author$
 * @version     $Revision$, $Date$
 */
@Deprecated public interface LegacyCartService extends LegacyOrderService {
  /**
   * DOCUMENT ME!
   *
   * @param   namedOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  Order addAllItemsToCartFromNamedOrder(Order namedOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   namedOrder  DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  Order addAllItemsToCartFromNamedOrder(Order namedOrder, boolean priceOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order      DOCUMENT ME!
   * @param   orderItem  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  OrderItem moveItemToCartFromNamedOrder(Order order, OrderItem orderItem) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order       DOCUMENT ME!
   * @param   orderItem   DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  OrderItem moveItemToCartFromNamedOrder(Order order, OrderItem orderItem, boolean priceOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   customerId   DOCUMENT ME!
   * @param   orderName    DOCUMENT ME!
   * @param   orderItemId  DOCUMENT ME!
   * @param   quantity     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  OrderItem moveItemToCartFromNamedOrder(Long customerId, String orderName, Long orderItemId, Integer quantity)
    throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   customerId   DOCUMENT ME!
   * @param   orderName    DOCUMENT ME!
   * @param   orderItemId  DOCUMENT ME!
   * @param   quantity     DOCUMENT ME!
   * @param   priceOrder   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  OrderItem moveItemToCartFromNamedOrder(Long customerId, String orderName, Long orderItemId, Integer quantity,
    boolean priceOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   namedOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  Order moveAllItemsToCartFromNamedOrder(Order namedOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   namedOrder  DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  Order moveAllItemsToCartFromNamedOrder(Order namedOrder, boolean priceOrder) throws PricingException;

  /**
   * Merge the anonymous cart with the customer's cart taking into consideration sku activation.
   *
   * @param   customer       the customer whose cart is to be merged
   * @param   anonymousCart  the anonymous cart id
   * @param   priceOrder     DOCUMENT ME!
   *
   * @return  the response containing the cart, any items added to the cart, and any items removed from the cart
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  MergeCartResponse mergeCart(Customer customer, Order anonymousCart, boolean priceOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   customer       DOCUMENT ME!
   * @param   anonymousCart  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  MergeCartResponse mergeCart(Customer customer, Order anonymousCart) throws PricingException;

  /**
   * Reconstruct the cart using previous stored state taking into consideration sku activation.
   *
   * @param   customer    the customer whose cart is to be reconstructed
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  the response containing the cart and any items removed from the cart
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  ReconstructCartResponse reconstructCart(Customer customer, boolean priceOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   customer  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  ReconstructCartResponse reconstructCart(Customer customer) throws PricingException;

} // end interface LegacyCartService
