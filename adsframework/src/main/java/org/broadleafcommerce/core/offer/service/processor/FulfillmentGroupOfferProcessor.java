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

package org.broadleafcommerce.core.offer.service.processor;

import java.util.List;

import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.service.discount.FulfillmentGroupOfferPotential;
import org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateFulfillmentGroupOffer;
import org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface FulfillmentGroupOfferProcessor extends OrderOfferProcessor {
  /**
   * DOCUMENT ME!
   *
   * @param  order              DOCUMENT ME!
   * @param  qualifiedFGOffers  DOCUMENT ME!
   * @param  offer              DOCUMENT ME!
   */
  void filterFulfillmentGroupLevelOffer(PromotableOrder order,
    List<PromotableCandidateFulfillmentGroupOffer> qualifiedFGOffers, Offer offer);

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  void calculateFulfillmentGroupTotal(PromotableOrder order);

  /**
   * Private method that takes a list of sorted CandidateOrderOffers and determines if each offer can be applied based
   * on the restrictions (stackable and/or combinable) on that offer. OrderAdjustments are create on the Order for each
   * applied CandidateOrderOffer. An offer with stackable equals false cannot be applied to an Order that already
   * contains an OrderAdjustment. An offer with combinable equals false cannot be applied to the Order if the Order
   * already contains an OrderAdjustment.
   *
   * @param   qualifiedFGOffers  a sorted list of CandidateOrderOffer
   * @param   order              the Order to apply the CandidateOrderOffers
   *
   * @return  true if order offer applied; otherwise false
   */
  boolean applyAllFulfillmentGroupOffers(List<PromotableCandidateFulfillmentGroupOffer> qualifiedFGOffers,
    PromotableOrder order);

  /**
   * DOCUMENT ME!
   *
   * @param   candidateOffers  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<FulfillmentGroupOfferPotential> removeTrailingNotCombinableFulfillmentGroupOffers(
    List<FulfillmentGroupOfferPotential> candidateOffers);

} // end interface FulfillmentGroupOfferProcessor
