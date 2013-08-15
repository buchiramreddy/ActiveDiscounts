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

package org.broadleafcommerce.core.offer.service.discount.domain;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PromotableItemFactory {
  /**
   * DOCUMENT ME!
   *
   * @param   order                           DOCUMENT ME!
   * @param   includeOrderAndItemAdjustments  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableOrder createPromotableOrder(Order order, boolean includeOrderAndItemAdjustments);

  /**
   * DOCUMENT ME!
   *
   * @param   promotableOrder  DOCUMENT ME!
   * @param   offer            DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableCandidateOrderOffer createPromotableCandidateOrderOffer(PromotableOrder promotableOrder, Offer offer);

  /**
   * DOCUMENT ME!
   *
   * @param   promotableOrder   DOCUMENT ME!
   * @param   offer             DOCUMENT ME!
   * @param   potentialSavings  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableCandidateOrderOffer createPromotableCandidateOrderOffer(PromotableOrder promotableOrder,
    Offer offer, Money potentialSavings);

  /**
   * DOCUMENT ME!
   *
   * @param   promotableCandidateOrderOffer  DOCUMENT ME!
   * @param   order                          DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableOrderAdjustment createPromotableOrderAdjustment(
    PromotableCandidateOrderOffer promotableCandidateOrderOffer,
    PromotableOrder               order);

  /**
   * DOCUMENT ME!
   *
   * @param   promotableCandidateOrderOffer  DOCUMENT ME!
   * @param   order                          DOCUMENT ME!
   * @param   value                          DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableOrderAdjustment createPromotableOrderAdjustment(
    PromotableCandidateOrderOffer promotableCandidateOrderOffer,
    PromotableOrder order, Money value);

  /**
   * DOCUMENT ME!
   *
   * @param   orderItem           DOCUMENT ME!
   * @param   order               DOCUMENT ME!
   * @param   includeAdjustments  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableOrderItem createPromotableOrderItem(OrderItem orderItem, PromotableOrder order,
    boolean includeAdjustments);

  /**
   * DOCUMENT ME!
   *
   * @param   promotableOrderItem  DOCUMENT ME!
   * @param   quantity             DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableOrderItemPriceDetail createPromotableOrderItemPriceDetail(PromotableOrderItem promotableOrderItem,
    int quantity);

  /**
   * DOCUMENT ME!
   *
   * @param   promotableOrder  DOCUMENT ME!
   * @param   offer            DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableCandidateItemOffer createPromotableCandidateItemOffer(PromotableOrder promotableOrder, Offer offer);

  /**
   * DOCUMENT ME!
   *
   * @param   promotableCandidateItemOffer    DOCUMENT ME!
   * @param   promotableOrderItemPriceDetail  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableOrderItemPriceDetailAdjustment createPromotableOrderItemPriceDetailAdjustment(
    PromotableCandidateItemOffer   promotableCandidateItemOffer,
    PromotableOrderItemPriceDetail promotableOrderItemPriceDetail);

  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroup  DOCUMENT ME!
   * @param   order             DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableFulfillmentGroup createPromotableFulfillmentGroup(FulfillmentGroup fulfillmentGroup, PromotableOrder order);

  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroup  DOCUMENT ME!
   * @param   offer             DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableCandidateFulfillmentGroupOffer createPromotableCandidateFulfillmentGroupOffer(
    PromotableFulfillmentGroup fulfillmentGroup,
    Offer                      offer);

  /**
   * DOCUMENT ME!
   *
   * @param   promotableCandidateFulfillmentGroupOffer  DOCUMENT ME!
   * @param   fulfillmentGroup                          DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableFulfillmentGroupAdjustment createPromotableFulfillmentGroupAdjustment(
    PromotableCandidateFulfillmentGroupOffer promotableCandidateFulfillmentGroupOffer,
    PromotableFulfillmentGroup               fulfillmentGroup);
} // end interface PromotableItemFactory
