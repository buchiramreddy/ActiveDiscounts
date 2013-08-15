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

import java.util.Date;

import javax.annotation.Nonnull;

import org.broadleafcommerce.core.catalog.domain.Sku;


/**
 * <p>Interface for dynamically determining the activity dates.</p>
 *
 * <p>Provides an ability to set active dates programatically. Intended for use by add-on modules like the PriceList
 * module which supports activeDates dates by PriceList.</p>
 *
 * <p>Even if the dates are being overridden dynamically, the master activeStart and activeEnd dates still control the
 * global activeDates of a SKU.</p>
 *
 * <p>Rather than implementing this interface directly, consider sub-classing the
 * {@link DefaultDynamicSkuActiveDatesServiceImpl} and providing overrides to methods there.</p>
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public interface DynamicSkuActiveDatesService {
  /**
   * Returns the activeStartDate for the SKU if it has been overridden.
   *
   * @param   sku  DOCUMENT ME!
   *
   * @return  the activeStartDate for the SKU if it has been overridden.
   */
  @Nonnull
  @SuppressWarnings("rawtypes")
  Date getDynamicSkuActiveStartDate(Sku sku);

  /**
   * Returns the activeEndDate for the SKU if it has been overridden.
   *
   * @param   sku  DOCUMENT ME!
   *
   * @return  the activeEndDate for the SKU if it has been overridden.
   */
  @SuppressWarnings("rawtypes")
  Date getDynamicSkuActiveEndDate(Sku sku);
}
