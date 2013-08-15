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

import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.core.pricing.domain.ShippingRate;
import org.broadleafcommerce.core.pricing.domain.ShippingRateImpl;

import org.springframework.stereotype.Repository;


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
@Repository("blShippingRatesDao")
public class ShippingRateDaoImpl implements ShippingRateDao {
  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  /**
   * @see  org.broadleafcommerce.core.pricing.dao.ShippingRateDao#save(org.broadleafcommerce.core.pricing.domain.ShippingRate)
   */
  @Override public ShippingRate save(ShippingRate shippingRate) {
    return em.merge(shippingRate);
  }

  /**
   * @see  org.broadleafcommerce.core.pricing.dao.ShippingRateDao#readShippingRateById(java.lang.Long)
   */
  @Override public ShippingRate readShippingRateById(Long id) {
    return em.find(ShippingRateImpl.class, id);
  }

  /**
   * @see  org.broadleafcommerce.core.pricing.dao.ShippingRateDao#readShippingRateByFeeTypesUnityQty(java.lang.String,java.lang.String,
   *       java.math.BigDecimal)
   */
  @Override
  @SuppressWarnings("unchecked")
  public ShippingRate readShippingRateByFeeTypesUnityQty(String feeType, String feeSubType, BigDecimal unitQuantity) {
    Query query = em.createNamedQuery("BC_READ_FIRST_SHIPPING_RATE_BY_FEE_TYPES");
    query.setParameter("feeType", feeType);
    query.setParameter("feeSubType", feeSubType);
    query.setParameter("bandUnitQuantity", unitQuantity);

    List<ShippingRate> returnedRates = query.getResultList();

    if (returnedRates.size() > 0) {
      return returnedRates.get(0);
    } else {
      return null;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.pricing.dao.ShippingRateDao#create()
   */
  @Override public ShippingRate create() {
    return (ShippingRate) entityConfiguration.createEntityInstance(ShippingRate.class.getName());
  }
} // end class ShippingRateDaoImpl
