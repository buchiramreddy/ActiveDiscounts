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

package org.broadleafcommerce.core.pricing.dao;

import java.math.BigDecimal;

import org.broadleafcommerce.core.pricing.domain.ShippingRate;


/**
 * DOCUMENT ME!
 *
 * @deprecated  Superceded in functionality by
 *              {@link org.broadleafcommerce.core.order.fulfillment.domain.BandedPriceFulfillmentOption} and
 *              {@link org.broadleafcommerce.core.pricing.service.fulfillment.provider.BandedFulfillmentPricingProvider}
 * @see         {@link org.broadleafcommerce.core.order.domain.FulfillmentOption},
 *              {@link org.broadleafcommerce.core.pricing.service.FulfillmentPricingService}
 * @author      $author$
 * @version     $Revision$, $Date$
 */
@Deprecated public interface ShippingRateDao {
  /**
   * DOCUMENT ME!
   *
   * @param   shippingRate  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ShippingRate save(ShippingRate shippingRate);

  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ShippingRate readShippingRateById(Long id);

  /**
   * DOCUMENT ME!
   *
   * @param   feeType       DOCUMENT ME!
   * @param   feeSubType    DOCUMENT ME!
   * @param   unitQuantity  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ShippingRate readShippingRateByFeeTypesUnityQty(String feeType, String feeSubType, BigDecimal unitQuantity);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ShippingRate create();

} // end interface ShippingRateDao
