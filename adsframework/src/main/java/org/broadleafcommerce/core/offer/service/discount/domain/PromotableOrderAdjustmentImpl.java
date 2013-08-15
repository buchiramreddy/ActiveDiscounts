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
import java.math.RoundingMode;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.service.type.OfferDiscountType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PromotableOrderAdjustmentImpl implements PromotableOrderAdjustment {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  protected PromotableCandidateOrderOffer promotableCandidateOrderOffer;

  /** DOCUMENT ME! */
  protected PromotableOrder               promotableOrder;

  /** DOCUMENT ME! */
  protected Money                         adjustmentValue;

  /** DOCUMENT ME! */
  protected Offer                         offer;

  /** DOCUMENT ME! */
  protected boolean      roundOfferValues = true;

  /** DOCUMENT ME! */
  protected int          roundingScale = 2;

  /** DOCUMENT ME! */
  protected RoundingMode roundingMode = RoundingMode.HALF_EVEN;

  /**
   * Creates a new PromotableOrderAdjustmentImpl object.
   *
   * @param  promotableCandidateOrderOffer  DOCUMENT ME!
   * @param  promotableOrder                DOCUMENT ME!
   */
  public PromotableOrderAdjustmentImpl(PromotableCandidateOrderOffer promotableCandidateOrderOffer,
    PromotableOrder promotableOrder) {
    assert (promotableOrder != null);
    assert (promotableCandidateOrderOffer != null);

    this.promotableCandidateOrderOffer = promotableCandidateOrderOffer;
    this.promotableOrder               = promotableOrder;
    this.offer                         = promotableCandidateOrderOffer.getOffer();
    computeAdjustmentValue();
  }

  /**
   * Creates a new PromotableOrderAdjustmentImpl object.
   *
   * @param  promotableCandidateOrderOffer  DOCUMENT ME!
   * @param  promotableOrder                DOCUMENT ME!
   * @param  adjustmentValue                DOCUMENT ME!
   */
  public PromotableOrderAdjustmentImpl(PromotableCandidateOrderOffer promotableCandidateOrderOffer,
    PromotableOrder promotableOrder, Money adjustmentValue) {
    this(promotableCandidateOrderOffer, promotableOrder);

    if (promotableOrder.isIncludeOrderAndItemAdjustments()) {
      this.adjustmentValue = adjustmentValue;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderAdjustment#getPromotableOrder()
   */
  @Override public PromotableOrder getPromotableOrder() {
    return promotableOrder;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderAdjustment#getOffer()
   */
  @Override public Offer getOffer() {
    return offer;
  }

  /*
   * Calculates the value of the adjustment by first getting the current value of the order and then
   * calculating the value of this adjustment.
   *
   * If this adjustment value is greater than the currentOrderValue (e.g. would make the order go negative
   * then the adjustment value is set to the value of the order).
   */
  /**
   * DOCUMENT ME!
   */
  protected void computeAdjustmentValue() {
    adjustmentValue = new Money(promotableOrder.getOrderCurrency());

    Money currentOrderValue = promotableOrder.calculateSubtotalWithAdjustments();

    // Note: FIXED_PRICE not calculated as this is not a valid option for offers.
    if (offer.getDiscountType().equals(OfferDiscountType.AMOUNT_OFF)) {
      adjustmentValue = new Money(offer.getValue(), promotableOrder.getOrderCurrency());
    } else if (offer.getDiscountType().equals(OfferDiscountType.PERCENT_OFF)) {
      BigDecimal offerValue = currentOrderValue.getAmount().multiply(offer.getValue().divide(new BigDecimal("100"), 5,
            RoundingMode.HALF_EVEN));

      if (isRoundOfferValues()) {
        offerValue = offerValue.setScale(roundingScale, roundingMode);
      }

      adjustmentValue = new Money(offerValue, promotableOrder.getOrderCurrency(), 5);
    }

    if (currentOrderValue.lessThan(adjustmentValue)) {
      adjustmentValue = currentOrderValue;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderAdjustment#getAdjustmentValue()
   */
  @Override public Money getAdjustmentValue() {
    return adjustmentValue;
  }

  /**
   * It is sometimes problematic to offer percentage-off offers with regards to rounding. For example, consider an item
   * that costs 9.99 and has a 50% promotion. To be precise, the offer value is 4.995, but this may be a strange value
   * to display to the user depending on the currency being used.
   *
   * @return  it is sometimes problematic to offer percentage-off offers with regards to rounding.
   */
  public boolean isRoundOfferValues() {
    return roundOfferValues;
  }

  /**
   *
   * DOCUMENT ME!
   *
   * @see    #isRoundOfferValues()
   *
   * @param  roundingScale  DOCUMENT ME!
   */
  public void setRoundingScale(int roundingScale) {
    this.roundingScale = roundingScale;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getRoundingScale() {
    return roundingScale;
  }

  /**
   *
   * DOCUMENT ME!
   *
   * @see    #isRoundOfferValues()
   *
   * @param  roundingMode  DOCUMENT ME!
   */
  public void setRoundingMode(RoundingMode roundingMode) {
    this.roundingMode = roundingMode;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public RoundingMode getRoundingMode() {
    return roundingMode;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderAdjustment#isCombinable()
   */
  @Override public boolean isCombinable() {
    Boolean combinable = offer.isCombinableWithOtherOffers();

    return ((combinable != null) && combinable);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderAdjustment#isTotalitarian()
   */
  @Override public boolean isTotalitarian() {
    Boolean totalitarian = offer.isTotalitarianOffer();

    return ((totalitarian != null) && totalitarian.booleanValue());
  }

} // end class PromotableOrderAdjustmentImpl
