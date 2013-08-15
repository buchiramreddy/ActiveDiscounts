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

import java.math.BigDecimal;

import org.broadleafcommerce.common.util.WeightUnitOfMeasureType;


/**
 * <p>This entity defines the bands that can be specified for
 * {@link org.broadleafcommerce.core.order.fulfillment.domain.BandedWeightFulfillmentOption}. Bands work on the
 * cumulated weight of an {@link org.broadleafcommerce.core.order.domain.Order} and should be calculated as follows:</p>
 *
 * <ol>
 *   <li>The weight of all of the {@link org.broadleafcommerce.core.order.domain.OrderItem}s (via the relationship to
 *     {@link org.broadleafcommerce.core.catalog.domain.Sku}) in a
 *     {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup} (which is obtained through their relationship
 *     with {@link org.broadleafcommerce.core.order.domain.FulfillmentGroupItem} are summed together</li>
 *   <li>The {@link org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentWeightBand} should be looked up by
 *     getting the closest band less than the sum of the weights</li>
 *   <li>If {@link #getResultAmountType()} returns
 *     {@link org.broadleafcommerce.core.order.service.type.FulfillmentBandResultAmountType#RATE}, then the cost for the
 *     fulfillment group is whatever is defined in {@link #getResultAmount()}</li>
 *   <li>If {@link #getResultAmountType()} returns
 *     {@link org.broadleafcommerce.core.order.service.type.FulfillmentBandResultAmountType#PERCENTAGE}, then the
 *     fulfillment cost is the percentage obtained by {@link #getResultAmount()} * retailPriceTotal</li>
 *   <li>If two bands have the same weight, the cheapest resulting amount is used</li>
 * </ol>
 *
 * <p>Note: this implementation assumes that units of measurement (lb, kg, etc) are the same across the site
 * implementation</p>
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
public interface FulfillmentWeightBand extends FulfillmentBand {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  BigDecimal getMinimumWeight();

  /**
   * DOCUMENT ME!
   *
   * @param  weight  DOCUMENT ME!
   */
  void setMinimumWeight(BigDecimal weight);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  BandedWeightFulfillmentOption getOption();

  /**
   * DOCUMENT ME!
   *
   * @param  option  DOCUMENT ME!
   */
  void setOption(BandedWeightFulfillmentOption option);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  WeightUnitOfMeasureType getWeightUnitOfMeasure();

  /**
   * DOCUMENT ME!
   *
   * @param  weightUnitOfMeasure  DOCUMENT ME!
   */
  void setWeightUnitOfMeasure(WeightUnitOfMeasureType weightUnitOfMeasure);

} // end interface FulfillmentWeightBand
