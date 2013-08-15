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
import java.util.Map;

import org.broadleafcommerce.core.offer.dao.OfferDao;
import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateOrderOffer;
import org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory;
import org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder;
import org.broadleafcommerce.core.order.dao.OrderItemDao;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface OrderOfferProcessor extends BaseProcessor {
  /**
   * DOCUMENT ME!
   *
   * @param  promotableOrder       DOCUMENT ME!
   * @param  qualifiedOrderOffers  DOCUMENT ME!
   * @param  offer                 DOCUMENT ME!
   */
  void filterOrderLevelOffer(PromotableOrder promotableOrder, List<PromotableCandidateOrderOffer> qualifiedOrderOffers,
    Offer offer);

  /**
   * DOCUMENT ME!
   *
   * @param   expression  DOCUMENT ME!
   * @param   vars        DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean executeExpression(String expression, Map<String, Object> vars);

  /**
   * Executes the appliesToOrderRules in the Offer to determine if this offer can be applied to the Order, OrderItem, or
   * FulfillmentGroup.
   *
   * @param   offer            DOCUMENT ME!
   * @param   promotableOrder  order
   *
   * @return  true if offer can be applied, otherwise false
   */
  boolean couldOfferApplyToOrder(Offer offer, PromotableOrder promotableOrder);

  /**
   * DOCUMENT ME!
   *
   * @param   candidateOffers  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<PromotableCandidateOrderOffer> removeTrailingNotCombinableOrderOffers(
    List<PromotableCandidateOrderOffer> candidateOffers);

  /**
   * Takes a list of sorted CandidateOrderOffers and determines if each offer can be applied based on the restrictions
   * (stackable and/or combinable) on that offer. OrderAdjustments are create on the Order for each applied
   * CandidateOrderOffer. An offer with stackable equals false cannot be applied to an Order that already contains an
   * OrderAdjustment. An offer with combinable equals false cannot be applied to the Order if the Order already contains
   * an OrderAdjustment.
   *
   * @param  orderOffers      a sorted list of CandidateOrderOffer
   * @param  promotableOrder  order the Order to apply the CandidateOrderOffers
   */
  void applyAllOrderOffers(List<PromotableCandidateOrderOffer> orderOffers, PromotableOrder promotableOrder);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableItemFactory getPromotableItemFactory();

  /**
   * DOCUMENT ME!
   *
   * @param  promotableItemFactory  DOCUMENT ME!
   */
  void setPromotableItemFactory(PromotableItemFactory promotableItemFactory);

  /**
   * Takes the adjustments and PriceDetails from the passed in PromotableOrder and transfers them to the actual order
   * first checking to see if they already exist.
   *
   * @param  promotableOrder  DOCUMENT ME!
   */
  void synchronizeAdjustmentsAndPrices(PromotableOrder promotableOrder);

  /**
   * Set the offerDao (primarily for unit testing).
   *
   * @param  offerDao  DOCUMENT ME!
   */
  void setOfferDao(OfferDao offerDao);

  /**
   * Set the orderItemDao (primarily for unit testing).
   *
   * @param  orderItemDao  DOCUMENT ME!
   */
  void setOrderItemDao(OrderItemDao orderItemDao);
} // end interface OrderOfferProcessor
