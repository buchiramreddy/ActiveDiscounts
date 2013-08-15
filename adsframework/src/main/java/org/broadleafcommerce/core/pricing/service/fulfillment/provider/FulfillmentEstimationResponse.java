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

package org.broadleafcommerce.core.pricing.service.fulfillment.provider;

import java.util.Map;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.FulfillmentOption;


/**
 * DTO to allow FulfillmentProcessors to respond to estimation requests for a particular FulfillmentGroup for a
 * particular FulfillmentOptions.
 *
 * @author   Phillip Verheyden
 * @see      {@link org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider},
 *           {@link org.broadleafcommerce.core.pricing.service.FulfillmentPricingService}
 * @version  $Revision$, $Date$
 */
public class FulfillmentEstimationResponse {
  /** DOCUMENT ME! */
  protected Map<? extends FulfillmentOption, Money> fulfillmentOptionPrices;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<? extends FulfillmentOption, Money> getFulfillmentOptionPrices() {
    return fulfillmentOptionPrices;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentOptionPrices  DOCUMENT ME!
   */
  public void setFulfillmentOptionPrices(Map<? extends FulfillmentOption, Money> fulfillmentOptionPrices) {
    this.fulfillmentOptionPrices = fulfillmentOptionPrices;
  }
}
