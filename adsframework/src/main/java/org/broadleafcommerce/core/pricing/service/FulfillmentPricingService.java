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

package org.broadleafcommerce.core.pricing.service;

import java.util.List;
import java.util.Set;

import org.broadleafcommerce.common.vendor.service.exception.FulfillmentPriceException;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentOption;
import org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentEstimationResponse;
import org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider;


/**
 * This service can be used in a couple of different ways. First, this is used in the pricing workflow and specifically
 * {@link org.broadleafcommerce.core.pricing.service.workflow.FulfillmentGroupMerchandiseTotalActivity} to calculate
 * costs for {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup}s in an {@link Order}. This can also be
 * injected in a controller to provide estimations for various
 * {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}s to display to the user before an option is
 * actually selected.
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
public interface FulfillmentPricingService {
  /**
   * Called during the Pricing workflow to determine the cost for the
   * {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup}. This will loop through {@link #getProcessors()}
   * and call
   * {@link org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider#calculateCostForFulfillmentGroup(org.broadleafcommerce.core.order.domain.FulfillmentGroup)}
   * on the first processor that returns true from
   * {@link org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider#canCalculateCostForFulfillmentGroup(org.broadleafcommerce.core.order.domain.FulfillmentGroup)}
   *
   * @param   fulfillmentGroup  DOCUMENT ME!
   *
   * @return  the updated <b>fulfillmentGroup</b> with its shippingPrice set
   *
   * @throws  org.broadleafcommerce.common.vendor.service.exception.FulfillmentPriceException  if <b>
   *                                                                                           fulfillmentGroup</b> does
   *                                                                                           not have a
   *                                                                                           FulfillmentOption
   *                                                                                           associated to it or if
   *                                                                                           there was no processor
   *                                                                                           found to calculate costs
   *                                                                                           for <b>
   *                                                                                           fulfillmentGroup</b>
   *
   * @see     {@link org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider}
   */
  FulfillmentGroup calculateCostForFulfillmentGroup(FulfillmentGroup fulfillmentGroup) throws FulfillmentPriceException;

  /**
   * This provides an estimation for a {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup} with a
   * {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}. The main use case for this method is in a view
   * cart controller that wants to provide estimations for different
   * {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}s before the user actually selects one. This uses
   * {@link #getProviders()} to allow third-party integrations to respond to estimations, and returns the first
   * processor that returns true from
   * {@link org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider#canCalculateCostForFulfillmentGroup(org.broadleafcommerce.core.order.domain.FulfillmentGroup, org.broadleafcommerce.core.order.domain.FulfillmentOption)}.
   *
   * @param   fulfillmentGroup  DOCUMENT ME!
   * @param   options           DOCUMENT ME!
   *
   * @return  the price estimation for a particular {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup}
   *          with a candidate {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}
   *
   * @throws  org.broadleafcommerce.common.vendor.service.exception.FulfillmentPriceException  if no processor was found
   *                                                                                           to estimate costs for <b>
   *                                                                                           fulfillmentGroup</b> with
   *                                                                                           the given <b>option</b>
   *
   * @see     {@link org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider}
   */
  FulfillmentEstimationResponse estimateCostForFulfillmentGroup(FulfillmentGroup fulfillmentGroup,
    Set<FulfillmentOption> options) throws FulfillmentPriceException;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<FulfillmentPricingProvider> getProviders();

} // end interface FulfillmentPricingService
