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

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.List;

import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferItemCriteria;
import org.broadleafcommerce.core.offer.service.type.OfferDiscountType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PromotableCandidateFulfillmentGroupOfferImpl implements PromotableCandidateFulfillmentGroupOffer {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  protected HashMap<OfferItemCriteria, List<PromotableOrderItem>> candidateQualifiersMap     =
    new HashMap<OfferItemCriteria, List<PromotableOrderItem>>();

  /** DOCUMENT ME! */
  protected Offer                                                 offer;

  /** DOCUMENT ME! */
  protected PromotableFulfillmentGroup                            promotableFulfillmentGroup;

  /**
   * Creates a new PromotableCandidateFulfillmentGroupOfferImpl object.
   *
   * @param  promotableFulfillmentGroup  DOCUMENT ME!
   * @param  offer                       DOCUMENT ME!
   */
  public PromotableCandidateFulfillmentGroupOfferImpl(PromotableFulfillmentGroup promotableFulfillmentGroup,
    Offer offer) {
    assert (offer != null);
    assert (promotableFulfillmentGroup != null);
    this.offer                      = offer;
    this.promotableFulfillmentGroup = promotableFulfillmentGroup;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateFulfillmentGroupOffer#getCandidateQualifiersMap()
   */
  @Override public HashMap<OfferItemCriteria, List<PromotableOrderItem>> getCandidateQualifiersMap() {
    return candidateQualifiersMap;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateFulfillmentGroupOffer#setCandidateQualifiersMap(java.util.HashMap)
   */
  @Override public void setCandidateQualifiersMap(
    HashMap<OfferItemCriteria, List<PromotableOrderItem>> candidateItemsMap) {
    this.candidateQualifiersMap = candidateItemsMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Money getBasePrice() {
    Money priceToUse = null;

    if (promotableFulfillmentGroup.getFulfillmentGroup().getRetailFulfillmentPrice() != null) {
      priceToUse = promotableFulfillmentGroup.getFulfillmentGroup().getRetailFulfillmentPrice();

      if ((offer.getApplyDiscountToSalePrice())
            && (promotableFulfillmentGroup.getFulfillmentGroup().getSaleFulfillmentPrice() != null)) {
        priceToUse = promotableFulfillmentGroup.getFulfillmentGroup().getSaleFulfillmentPrice();
      }
    }

    return priceToUse;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateFulfillmentGroupOffer#computeDiscountedAmount()
   */
  @Override public Money computeDiscountedAmount() {
    Money discountedAmount = new Money(0);
    Money priceToUse       = getBasePrice();

    if (priceToUse != null) {
      if (offer.getDiscountType().equals(OfferDiscountType.AMOUNT_OFF)) {
        discountedAmount = BroadleafCurrencyUtils.getMoney(offer.getValue(),
            promotableFulfillmentGroup.getFulfillmentGroup().getOrder().getCurrency());
      } else if (offer.getDiscountType().equals(OfferDiscountType.FIX_PRICE)) {
        discountedAmount = priceToUse.subtract(BroadleafCurrencyUtils.getMoney(offer.getValue(),
              promotableFulfillmentGroup.getFulfillmentGroup().getOrder().getCurrency()));
      } else if (offer.getDiscountType().equals(OfferDiscountType.PERCENT_OFF)) {
        discountedAmount = priceToUse.multiply(offer.getValue().divide(new BigDecimal("100")));
      }

      if (discountedAmount.greaterThan(priceToUse)) {
        discountedAmount = priceToUse;
      }
    }

    return discountedAmount;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateFulfillmentGroupOffer#getDiscountedPrice()
   */
  @Override public Money getDiscountedPrice() {
    return getBasePrice().subtract(computeDiscountedAmount());
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateFulfillmentGroupOffer#getDiscountedAmount()
   */
  @Override public Money getDiscountedAmount() {
    return computeDiscountedAmount();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateFulfillmentGroupOffer#getOffer()
   */
  @Override public Offer getOffer() {
    return offer;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateFulfillmentGroupOffer#getFulfillmentGroup()
   */
  @Override public PromotableFulfillmentGroup getFulfillmentGroup() {
    return promotableFulfillmentGroup;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getPriority() {
    return offer.getPriority();
  }
} // end class PromotableCandidateFulfillmentGroupOfferImpl
