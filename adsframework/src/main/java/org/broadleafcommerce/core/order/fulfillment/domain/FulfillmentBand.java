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

import java.math.BigDecimal;

import org.broadleafcommerce.core.order.service.type.FulfillmentBandResultAmountType;


/**
 * This entity is a collection of properties shared between different band implementations. Out of the box, Broadleaf
 * provides implementations for banded weight and banded price.
 *
 * @author   Phillip Verheyden
 * @see      {@link org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentPriceBand},
 *           {@link org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentWeightBand}
 * @version  $Revision$, $Date$
 */
public interface FulfillmentBand extends Serializable {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  /**
   * Gets the amount that should be applied to the fulfillment cost for the
   * {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup}. This could be applied as a percentage or as a
   * flat rate, depending on the result of calling {@link #getResultType()}. This is required and should never be null
   *
   * @return  the amount to apply for this band
   */
  BigDecimal getResultAmount();

  /**
   * Sets the amount that should be applied to the fulfillment cost for this band. This can be either a flat rate or a
   * percentage depending on {@link #getResultType()}.
   *
   * @param  resultAmount  - the percentage or flat rate that should be applied as a fulfillment cost for this band
   */
  void setResultAmount(BigDecimal resultAmount);

  /**
   * Gets how {@link #getResultAmount} should be applied to the fulfillment cost.
   *
   * @return  the type of {@link #getResultAmount()} which determines how that value should be calculated into the cost
   */
  FulfillmentBandResultAmountType getResultAmountType();

  /**
   * Sets how {@link #getResultAmount()} should be applied to the fulfillment cost.
   *
   * @param  resultAmountType  - how the value from {@link #getResultAmount()} should be applied to the cost of the
   *                           {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup}
   */
  void setResultAmountType(FulfillmentBandResultAmountType resultAmountType);

} // end interface FulfillmentBand
