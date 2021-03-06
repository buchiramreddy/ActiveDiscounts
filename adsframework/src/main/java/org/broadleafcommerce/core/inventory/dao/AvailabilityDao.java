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

package org.broadleafcommerce.core.inventory.dao;

import java.util.List;

import org.broadleafcommerce.core.inventory.domain.SkuAvailability;


/**
 * DOCUMENT ME!
 *
 * @deprecated  This is no longer required and is instead implemented as a third-party inventory module
 * @author      $author$
 * @version     $Revision$, $Date$
 */
@Deprecated public interface AvailabilityDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns a SKU Availability record for the passed in skuId. Uses a cacheable query unless the realTime flag is set
   * to true.
   *
   * @param   skuIds    DOCUMENT ME!
   * @param   realTime  DOCUMENT ME!
   *
   * @return  a SKU Availability record for the passed in skuId.
   */
  List<SkuAvailability> readSKUAvailability(List<Long> skuIds, boolean realTime);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a SKU Availability record for the passed in skuId and locationId. Uses a cacheable query unless the
   * realTime flag is set to true.
   *
   * @param   skuIds      DOCUMENT ME!
   * @param   locationId  DOCUMENT ME!
   * @param   realTime    DOCUMENT ME!
   *
   * @return  a SKU Availability record for the passed in skuId and locationId.
   */
  List<SkuAvailability> readSKUAvailabilityForLocation(List<Long> skuIds, Long locationId, boolean realTime);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  skuAvailability  DOCUMENT ME!
   */
  void save(SkuAvailability skuAvailability);
} // end interface AvailabilityDao
