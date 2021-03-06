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

package org.broadleafcommerce.core.order.service;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.call.MergeCartResponse;
import org.broadleafcommerce.core.order.service.call.ReconstructCartResponse;
import org.broadleafcommerce.core.order.service.exception.RemoveFromCartException;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public interface MergeCartService {
  /**
   * Merges the anonymous cart with the customer's current cart, taking into consideration the active status of the SKUs
   * to merge. For example, if the customer had a SKU in their anonymous cart that is no longer active, it will not be
   * merged into the new cart.
   *
   * @param   customer       the customer whose cart is to be merged
   * @param   anonymousCart  the anonymous cart id
   * @param   priceOrder     whether or not to price the order
   *
   * @return  the response containing the cart, any items added to the cart, and any items removed from the cart
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   * @throws  org.broadleafcommerce.core.order.service.exception.RemoveFromCartException
   */
  MergeCartResponse mergeCart(Customer customer, Order anonymousCart, boolean priceOrder) throws PricingException,
    RemoveFromCartException;

  /**
   * Delegates to mergeCart(Customer, Order, boolean) with priceOrder set to true.
   *
   * <p>Merges the anonymous cart with the customer's current cart, taking into consideration the active status of the
   * SKUs to merge. For example, if the customer had a SKU in their anonymous cart that is no longer active, it will not
   * be merged into the new cart.</p>
   *
   * @param   customer       the customer whose cart is to be merged
   * @param   anonymousCart  the anonymous cart id
   *
   * @return  the response containing the cart, any items added to the cart, and any items removed from the cart
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   * @throws  org.broadleafcommerce.core.order.service.exception.RemoveFromCartException
   */
  MergeCartResponse mergeCart(Customer customer, Order anonymousCart) throws PricingException, RemoveFromCartException;

  /**
   * Reconstruct the cart using previous stored state taking into consideration sku activation.
   *
   * @param   customer    the customer whose cart is to be reconstructed
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  the response containing the cart and any items removed from the cart
   *
   * @throws  org.broadleafcommerce.core.order.service.exception.RemoveFromCartException
   * @throws  PricingException                                                            DOCUMENT ME!
   */
  ReconstructCartResponse reconstructCart(Customer customer, boolean priceOrder) throws PricingException,
    RemoveFromCartException;

  /**
   * Delegates to reconstructCart(Customer, boolean) with priceOrder set to true
   *
   * <p>Reconstruct the cart using previous stored state taking into consideration sku activation</p>
   *
   * @param   customer  the customer whose cart is to be reconstructed
   *
   * @return  the response containing the cart and any items removed from the cart
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   * @throws  org.broadleafcommerce.core.order.service.exception.RemoveFromCartException
   */
  ReconstructCartResponse reconstructCart(Customer customer) throws PricingException, RemoveFromCartException;

} // end interface MergeCartService
