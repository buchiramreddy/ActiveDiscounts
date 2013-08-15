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

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.vendor.service.exception.FulfillmentPriceException;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentOption;
import org.broadleafcommerce.core.order.service.FulfillmentGroupService;
import org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentEstimationResponse;
import org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider;

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blFulfillmentPricingService")
public class FulfillmentPricingServiceImpl implements FulfillmentPricingService {
  /** DOCUMENT ME! */
  protected static final Log LOG = LogFactory.getLog(FulfillmentPricingServiceImpl.class);

  /** DOCUMENT ME! */
  @Resource(name = "blFulfillmentPricingProviders")
  protected List<FulfillmentPricingProvider> providers;

  /** DOCUMENT ME! */
  @Resource(name = "blFulfillmentGroupService")
  protected FulfillmentGroupService fulfillmentGroupService;

  /**
   * @see  org.broadleafcommerce.core.pricing.service.FulfillmentPricingService#calculateCostForFulfillmentGroup(org.broadleafcommerce.core.order.domain.FulfillmentGroup)
   */
  @Override public FulfillmentGroup calculateCostForFulfillmentGroup(FulfillmentGroup fulfillmentGroup)
    throws FulfillmentPriceException {
    if (fulfillmentGroup.getFulfillmentOption() == null) {
      // There is no shipping option yet. We'll simply set the shipping price to zero for now, and continue.
      fulfillmentGroup.setRetailFulfillmentPrice(Money.ZERO);
      fulfillmentGroup.setFulfillmentPrice(Money.ZERO);
      fulfillmentGroup.setSaleFulfillmentPrice(Money.ZERO);

      return fulfillmentGroup;
    }

    for (FulfillmentPricingProvider provider : providers) {
      if (provider.canCalculateCostForFulfillmentGroup(fulfillmentGroup, fulfillmentGroup.getFulfillmentOption())) {
        return provider.calculateCostForFulfillmentGroup(fulfillmentGroup);
      }
    }

    throw new FulfillmentPriceException("No valid processor was found to calculate the FulfillmentGroup cost with "
      + "FulfillmentOption id: " + fulfillmentGroup.getFulfillmentOption().getId()
      + " and name: " + fulfillmentGroup.getFulfillmentOption().getName());
  }

  /**
   * @see  org.broadleafcommerce.core.pricing.service.FulfillmentPricingService#estimateCostForFulfillmentGroup(org.broadleafcommerce.core.order.domain.FulfillmentGroup,
   *       java.util.Set)
   */
  @Override public FulfillmentEstimationResponse estimateCostForFulfillmentGroup(FulfillmentGroup fulfillmentGroup,
    Set<FulfillmentOption> options) throws FulfillmentPriceException {
    FulfillmentEstimationResponse     response = new FulfillmentEstimationResponse();
    HashMap<FulfillmentOption, Money> prices   = new HashMap<FulfillmentOption, Money>();
    response.setFulfillmentOptionPrices(prices);

    for (FulfillmentPricingProvider provider : providers) {
      // Leave it up to the providers to determine if they can respond to a pricing estimate.  If they can't, or if one or more of the options that are passed in can't be responded
      // to, then the response from the pricing provider should not include the options that it could not respond to.
      try {
        FulfillmentEstimationResponse processorResponse = provider.estimateCostForFulfillmentGroup(fulfillmentGroup,
            options);

        if ((processorResponse != null)
              && (processorResponse.getFulfillmentOptionPrices() != null)
              && (processorResponse.getFulfillmentOptionPrices().size() > 0)) {
          prices.putAll(processorResponse.getFulfillmentOptionPrices());
        }
      } catch (FulfillmentPriceException e) {
        // Shouldn't completely fail the rest of the estimation on a pricing exception. Another provider might still
        // be able to respond
        String errorMessage = "FulfillmentPriceException thrown when trying to estimate fulfillment costs from ";
        errorMessage += provider.getClass().getName();
        errorMessage += ". Underlying message was: " + e.getMessage();
        LOG.error(errorMessage);
      }
    }

    return response;
  } // end method estimateCostForFulfillmentGroup

  /**
   * @see  org.broadleafcommerce.core.pricing.service.FulfillmentPricingService#getProviders()
   */
  @Override public List<FulfillmentPricingProvider> getProviders() {
    return providers;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  providers  DOCUMENT ME!
   */
  public void setProviders(List<FulfillmentPricingProvider> providers) {
    this.providers = providers;
  }

} // end class FulfillmentPricingServiceImpl
