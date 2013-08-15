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

package org.broadleafcommerce.core.order.fulfillment.domain;

import java.io.Serializable;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.FulfillmentOption;


/**
 * Used in conjunction with the
 * {@link org.broadleafcommerce.core.pricing.service.fulfillment.provider.FixedPriceFulfillmentPricingProvider} to allow
 * for a single price for fulfilling an order (e.g. $5 shipping)
 *
 * @author   Phillip Verheyden
 * @see
 *           
 *           {@link org.broadleafcommerce.core.pricing.service.fulfillment.provider.FixedPriceFulfillmentPricingProvider}
 * @version  $Revision$, $Date$
 */
public interface FixedPriceFulfillmentOption extends FulfillmentOption, Serializable {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getPrice();

  /**
   * DOCUMENT ME!
   *
   * @param  price  DOCUMENT ME!
   */
  void setPrice(Money price);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  BroadleafCurrency getCurrency();

  /**
   * DOCUMENT ME!
   *
   * @param  currency  DOCUMENT ME!
   */
  void setCurrency(BroadleafCurrency currency);

} // end interface FixedPriceFulfillmentOption
