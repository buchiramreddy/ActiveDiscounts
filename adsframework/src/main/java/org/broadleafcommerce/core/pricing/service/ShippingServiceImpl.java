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

import org.broadleafcommerce.common.vendor.service.exception.FulfillmentPriceException;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.pricing.service.module.ShippingModule;


/**
 * DOCUMENT ME!
 *
 * @deprecated  Should use the {@link org.broadleafcommerce.core.order.domain.FulfillmentOption} paradigm, implemented
 *              in {@link org.broadleafcommerce.core.pricing.service.FulfillmentPricingService}
 * @see         {@link org.broadleafcommerce.core.pricing.service.FulfillmentPricingService},
 *              {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}
 * @author      $author$
 * @version     $Revision$, $Date$
 */
@Deprecated public class ShippingServiceImpl implements ShippingService {
  /** DOCUMENT ME! */
  protected ShippingModule shippingModule;

  /**
   * @see  org.broadleafcommerce.core.pricing.service.ShippingService#calculateShippingForFulfillmentGroup(org.broadleafcommerce.core.order.domain.FulfillmentGroup)
   */
  @Override public FulfillmentGroup calculateShippingForFulfillmentGroup(FulfillmentGroup fulfillmentGroup)
    throws FulfillmentPriceException {
    FulfillmentGroup group = shippingModule.calculateShippingForFulfillmentGroup(fulfillmentGroup);

    return group;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ShippingModule getShippingModule() {
    return shippingModule;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  shippingModule  DOCUMENT ME!
   */
  public void setShippingModule(ShippingModule shippingModule) {
    this.shippingModule = shippingModule;
  }

} // end class ShippingServiceImpl
