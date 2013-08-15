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

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.broadleafcommerce.core.pricing.dao.ShippingRateDao;
import org.broadleafcommerce.core.pricing.domain.ShippingRate;

import org.springframework.stereotype.Service;


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
@Deprecated
@Service("blShippingRateService")
public class ShippingRateServiceImpl implements ShippingRateService {
  /** DOCUMENT ME! */
  @Resource(name = "blShippingRatesDao")
  protected ShippingRateDao shippingRateDao;

  /**
   * @see  org.broadleafcommerce.core.pricing.service.ShippingRateService#readShippingRateByFeeTypesUnityQty(java.lang.String,
   *       java.lang.String, java.math.BigDecimal)
   */
  @Override public ShippingRate readShippingRateByFeeTypesUnityQty(String feeType, String feeSubType,
    BigDecimal unitQuantity) {
    return shippingRateDao.readShippingRateByFeeTypesUnityQty(feeType, feeSubType, unitQuantity);
  }

  /**
   * @see  org.broadleafcommerce.core.pricing.service.ShippingRateService#readShippingRateById(java.lang.Long)
   */
  @Override public ShippingRate readShippingRateById(Long id) {
    return shippingRateDao.readShippingRateById(id);
  }

  /**
   * @see  org.broadleafcommerce.core.pricing.service.ShippingRateService#save(org.broadleafcommerce.core.pricing.domain.ShippingRate)
   */
  @Override public ShippingRate save(ShippingRate shippingRate) {
    return shippingRateDao.save(shippingRate);
  }

} // end class ShippingRateServiceImpl
