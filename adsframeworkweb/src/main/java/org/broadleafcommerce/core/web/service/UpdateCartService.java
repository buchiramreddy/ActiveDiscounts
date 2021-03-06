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

package org.broadleafcommerce.core.web.service;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.call.UpdateCartResponse;


/**
 * Provides methods to facilitate order repricing.
 *
 * <p>Author: jerryocanas Date: 9/26/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface UpdateCartService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Reprices the order by removing all items and recreating the cart calling for a reprice on the new cart.
   *
   * @param   currentCart  DOCUMENT ME!
   *
   * @return  reprices the order by removing all items and recreating the cart calling for a reprice on the new cart.
   */
  UpdateCartResponse copyCartToCurrentContext(Order currentCart);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Compares the currency set in the BroadleafRequestContext with the savedCurrency. If different, returns TRUE
   *
   * @return  compares the currency set in the BroadleafRequestContext with the savedCurrency.
   */
  boolean currencyHasChanged();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the currency that was set as active on last pass through.
   *
   * @return  gets the currency that was set as active on last pass through.
   */
  BroadleafCurrency getSavedCurrency();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the currency that was set as active on last pass through.
   *
   * @param  savedCurrency  DOCUMENT ME!
   */
  void setSavedCurrency(BroadleafCurrency savedCurrency);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Validates the cart against the active price list and locale.
   *
   * @param   cart  DOCUMENT ME!
   *
   * @throws  IllegalArgumentException
   */
  void validateCart(Order cart) throws IllegalArgumentException;

} // end interface UpdateCartService
