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

package org.broadleafcommerce.core.offer.domain;


import java.util.List;

import org.broadleafcommerce.core.offer.service.type.OfferTimeZoneType;


/**
 * Add advanced offer support to an Offer.
 *
 * @author   Priyesh Patel
 * @version  $Revision$, $Date$
 */
public interface AdvancedOffer {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferTimeZoneType getOfferTimeZoneType();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  offerTimeZoneType  DOCUMENT ME!
   */
  void setOfferTimeZoneType(OfferTimeZoneType offerTimeZoneType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<OfferTier> getOfferTiers();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isTieredOffer();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  offerTiers  DOCUMENT ME!
   */
  void setOfferTiers(List<OfferTier> offerTiers);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  isTieredOffer  DOCUMENT ME!
   */
  void setTieredOffer(boolean isTieredOffer);


} // end interface AdvancedOffer
