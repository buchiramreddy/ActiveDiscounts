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

package org.broadleafcommerce.core.pricing.service.module;

import org.broadleafcommerce.common.vendor.service.exception.FulfillmentPriceException;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;


/**
 * DOCUMENT ME!
 *
 * @deprecated  Superceded by functionality given by {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}
 *              and {@link org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider}
 * @see         {@link org.broadleafcommerce.core.pricing.service.fulfillment.provider.FulfillmentPricingProvider},
 *              {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}
 * @author      $author$
 * @version     $Revision$, $Date$
 */
@Deprecated public interface ShippingModule {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getName();

  /**
   * DOCUMENT ME!
   *
   * @param  name  DOCUMENT ME!
   */
  void setName(String name);

  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroup  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  FulfillmentPriceException  DOCUMENT ME!
   */
  FulfillmentGroup calculateShippingForFulfillmentGroup(FulfillmentGroup fulfillmentGroup)
    throws FulfillmentPriceException;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getServiceName();

  /**
   * DOCUMENT ME!
   *
   * @param   serviceName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean isValidModuleForService(String serviceName);

  /**
   * DOCUMENT ME!
   *
   * @param  isDefaultModule  DOCUMENT ME!
   */
  void setDefaultModule(Boolean isDefaultModule);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean isDefaultModule();

} // end interface ShippingModule
