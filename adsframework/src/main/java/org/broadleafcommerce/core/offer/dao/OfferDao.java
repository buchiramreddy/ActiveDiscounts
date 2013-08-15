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

package org.broadleafcommerce.core.offer.dao;

import java.util.List;

import org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOffer;
import org.broadleafcommerce.core.offer.domain.CandidateItemOffer;
import org.broadleafcommerce.core.offer.domain.CandidateOrderOffer;
import org.broadleafcommerce.core.offer.domain.FulfillmentGroupAdjustment;
import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferInfo;
import org.broadleafcommerce.core.offer.domain.OrderAdjustment;
import org.broadleafcommerce.core.offer.domain.OrderItemAdjustment;
import org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface OfferDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns the number of milliseconds that the current date/time will be cached for queries before refreshing. This
   * aids in query caching, otherwise every query that utilized current date would be different and caching would be
   * ineffective.
   *
   * @return  the milliseconds to cache the current date/time
   */
  Long getCurrentDateResolution();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the number of milliseconds that the current date/time will be cached for queries before refreshing. This aids
   * in query caching, otherwise every query that utilized current date would be different and caching would be
   * ineffective.
   *
   * @param  currentDateResolution  the milliseconds to cache the current date/time
   */
  void setCurrentDateResolution(Long currentDateResolution);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Offer create();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CandidateFulfillmentGroupOffer createCandidateFulfillmentGroupOffer();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CandidateItemOffer createCandidateItemOffer();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CandidateOrderOffer createCandidateOrderOffer();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroupAdjustment createFulfillmentGroupAdjustment();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferInfo createOfferInfo();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderAdjustment createOrderAdjustment();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderItemAdjustment createOrderItemAdjustment();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderItemPriceDetailAdjustment createOrderItemPriceDetailAdjustment();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  offer  DOCUMENT ME!
   */
  void delete(Offer offer);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  offerInfo  DOCUMENT ME!
   */
  void delete(OfferInfo offerInfo);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Offer> readAllOffers();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   offerId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Offer readOfferById(Long offerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Offer> readOffersByAutomaticDeliveryType();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   offer  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Offer save(Offer offer);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   offerInfo  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferInfo save(OfferInfo offerInfo);

} // end interface OfferDao
