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

package org.broadleafcommerce.core.catalog.service.dynamic;

import java.util.HashMap;

import javax.annotation.Nonnull;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.catalog.domain.ProductOptionValueImpl;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.domain.SkuBundleItem;


/**
 * <p>Interface for calculating dynamic pricing for a {@link org.broadleafcommerce.core.catalog.domain.Sku}. This should
 * be hooked up via a custom subclass of {@link org.broadleafcommerce.core.web.catalog.DefaultDynamicSkuPricingFilter}
 * where an implementation of this class should be injected and returned in the getPricing() method.</p>
 *
 * <p>Rather than implementing this interface directly, consider subclassing the
 * {@link org.broadleafcommerce.core.catalog.service.dynamic.DefaultDynamicSkuPricingServiceImpl} and providing
 * overrides to methods there.</p>
 *
 * @author   jfischer
 * @see      {@link org.broadleafcommerce.core.catalog.service.dynamic.DefaultDynamicSkuPricingServiceImpl}
 * @see      {@link org.broadleafcommerce.core.web.catalog.DefaultDynamicSkuPricingFilter}
 * @see      {@link org.broadleafcommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext}
 * @version  $Revision$, $Date$
 */
public interface DynamicSkuPricingService {
  /**
   * While this method should return a {@link org.broadleafcommerce.core.catalog.service.dynamic.DynamicSkuPrices} (and
   * not just null) the members of the result can all be null; they do not have to be set.
   *
   * @param   sku                       DOCUMENT ME!
   * @param   skuPricingConsiderations  DOCUMENT ME!
   *
   * @return  while this method should return a
   *          {@link org.broadleafcommerce.core.catalog.service.dynamic.DynamicSkuPrices} (and not just null) the
   *          members of the result can all be null; they do not have to be set.
   */
  @Nonnull
  @SuppressWarnings("rawtypes")
  DynamicSkuPrices getSkuPrices(Sku sku, HashMap skuPricingConsiderations);

  /**
   * Used for t.
   *
   * @param   sku                       DOCUMENT ME!
   * @param   skuPricingConsiderations  DOCUMENT ME!
   *
   * @return  used for t.
   */
  @SuppressWarnings("rawtypes")
  DynamicSkuPrices getSkuBundleItemPrice(SkuBundleItem sku, HashMap skuPricingConsiderations);

  /**
   * Execute dynamic pricing on the price of a product option value.
   *
   * @param   productOptionValueImpl          DOCUMENT ME!
   * @param   priceAdjustment                 DOCUMENT ME!
   * @param   skuPricingConsiderationContext  DOCUMENT ME!
   *
   * @return  execute dynamic pricing on the price of a product option value.
   */
  @SuppressWarnings("rawtypes")
  DynamicSkuPrices getPriceAdjustment(ProductOptionValueImpl productOptionValueImpl, Money priceAdjustment,
    HashMap skuPricingConsiderationContext);

} // end interface DynamicSkuPricingService
