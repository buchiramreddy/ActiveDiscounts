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

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blPromotableItemFactory")
public class PromotableItemFactoryImpl implements PromotableItemFactory {
  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory#createPromotableOrder(org.broadleafcommerce.core.order.domain.Order,
   *       boolean)
   */
  @Override public PromotableOrder createPromotableOrder(Order order, boolean includeOrderAndItemAdjustments) {
    return new PromotableOrderImpl(order, this, includeOrderAndItemAdjustments);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory#createPromotableCandidateOrderOffer(org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder,
   *       org.broadleafcommerce.core.offer.domain.Offer)
   */
  @Override public PromotableCandidateOrderOffer createPromotableCandidateOrderOffer(PromotableOrder promotableOrder,
    Offer offer) {
    return new PromotableCandidateOrderOfferImpl(promotableOrder, offer);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory#createPromotableCandidateOrderOffer(org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder,
   *       org.broadleafcommerce.core.offer.domain.Offer, org.broadleafcommerce.common.money.Money)
   */
  @Override public PromotableCandidateOrderOffer createPromotableCandidateOrderOffer(PromotableOrder promotableOrder,
    Offer offer, Money potentialSavings) {
    return new PromotableCandidateOrderOfferImpl(promotableOrder, offer, potentialSavings);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory#createPromotableOrderAdjustment(org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateOrderOffer,
   *       org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder)
   */
  @Override public PromotableOrderAdjustment createPromotableOrderAdjustment(
    PromotableCandidateOrderOffer promotableCandidateOrderOffer, PromotableOrder order) {
    return new PromotableOrderAdjustmentImpl(promotableCandidateOrderOffer, order);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory#createPromotableOrderAdjustment(org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateOrderOffer,
   *       org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder,
   *       org.broadleafcommerce.common.money.Money)
   */
  @Override public PromotableOrderAdjustment createPromotableOrderAdjustment(
    PromotableCandidateOrderOffer promotableCandidateOrderOffer,
    PromotableOrder order, Money adjustmentValue) {
    return new PromotableOrderAdjustmentImpl(promotableCandidateOrderOffer, order, adjustmentValue);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory#createPromotableOrderItem(org.broadleafcommerce.core.order.domain.OrderItem,
   *       org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder, boolean)
   */
  @Override public PromotableOrderItem createPromotableOrderItem(OrderItem orderItem, PromotableOrder order,
    boolean includeAdjustments) {
    return new PromotableOrderItemImpl(orderItem, order, this, includeAdjustments);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory#createPromotableOrderItemPriceDetail(org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem,
   *       int)
   */
  @Override public PromotableOrderItemPriceDetail createPromotableOrderItemPriceDetail(
    PromotableOrderItem promotableOrderItem,
    int                 quantity) {
    return new PromotableOrderItemPriceDetailImpl(promotableOrderItem, quantity);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory#createPromotableCandidateItemOffer(org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder,
   *       org.broadleafcommerce.core.offer.domain.Offer)
   */
  @Override public PromotableCandidateItemOffer createPromotableCandidateItemOffer(PromotableOrder promotableOrder,
    Offer offer) {
    return new PromotableCandidateItemOfferImpl(promotableOrder, offer);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory#createPromotableOrderItemPriceDetailAdjustment(org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer,
   *       org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail)
   */
  @Override public PromotableOrderItemPriceDetailAdjustment createPromotableOrderItemPriceDetailAdjustment(
    PromotableCandidateItemOffer   promotableCandidateItemOffer,
    PromotableOrderItemPriceDetail orderItemPriceDetail) {
    return new PromotableOrderItemPriceDetailAdjustmentImpl(promotableCandidateItemOffer, orderItemPriceDetail);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory#createPromotableFulfillmentGroup(org.broadleafcommerce.core.order.domain.FulfillmentGroup,
   *       org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder)
   */
  @Override public PromotableFulfillmentGroup createPromotableFulfillmentGroup(
    FulfillmentGroup fulfillmentGroup,
    PromotableOrder  order) {
    return new PromotableFulfillmentGroupImpl(fulfillmentGroup, order, this);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory#createPromotableCandidateFulfillmentGroupOffer(org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroup,
   *       org.broadleafcommerce.core.offer.domain.Offer)
   */
  @Override public PromotableCandidateFulfillmentGroupOffer createPromotableCandidateFulfillmentGroupOffer(
    PromotableFulfillmentGroup fulfillmentGroup, Offer offer) {
    return new PromotableCandidateFulfillmentGroupOfferImpl(fulfillmentGroup, offer);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory#createPromotableFulfillmentGroupAdjustment(org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateFulfillmentGroupOffer,
   *       org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroup)
   */
  @Override public PromotableFulfillmentGroupAdjustment createPromotableFulfillmentGroupAdjustment(
    PromotableCandidateFulfillmentGroupOffer promotableCandidateFulfillmentGroupOffer,
    PromotableFulfillmentGroup               fulfillmentGroup) {
    return new PromotableFulfillmentGroupAdjustmentImpl(promotableCandidateFulfillmentGroupOffer, fulfillmentGroup);
  }
} // end class PromotableItemFactoryImpl
