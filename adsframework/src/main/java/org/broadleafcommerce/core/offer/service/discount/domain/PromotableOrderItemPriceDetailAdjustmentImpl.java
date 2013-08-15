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

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PromotableOrderItemPriceDetailAdjustmentImpl extends AbstractPromotionRounding
  implements PromotableOrderItemPriceDetailAdjustment, OfferHolder {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  protected PromotableCandidateItemOffer   promotableCandidateItemOffer;

  /** DOCUMENT ME! */
  protected PromotableOrderItemPriceDetail promotableOrderItemPriceDetail;

  /** DOCUMENT ME! */
  protected Money                          saleAdjustmentValue;

  /** DOCUMENT ME! */
  protected Money                          retailAdjustmentValue;

  /** DOCUMENT ME! */
  protected Money                          adjustmentValue;

  /** DOCUMENT ME! */
  protected boolean                        appliedToSalePrice;

  /** DOCUMENT ME! */
  protected Offer                          offer;

  /**
   * Creates a new PromotableOrderItemPriceDetailAdjustmentImpl object.
   *
   * @param  promotableCandidateItemOffer  DOCUMENT ME!
   * @param  orderItemPriceDetail          DOCUMENT ME!
   */
  public PromotableOrderItemPriceDetailAdjustmentImpl(PromotableCandidateItemOffer promotableCandidateItemOffer,
    PromotableOrderItemPriceDetail orderItemPriceDetail) {
    assert (promotableCandidateItemOffer != null);
    assert (orderItemPriceDetail != null);
    this.promotableCandidateItemOffer   = promotableCandidateItemOffer;
    this.promotableOrderItemPriceDetail = orderItemPriceDetail;
    this.offer                          = promotableCandidateItemOffer.getOffer();
    computeAdjustmentValues();
  }

  /**
   * Creates a new PromotableOrderItemPriceDetailAdjustmentImpl object.
   *
   * @param  itemAdjustment        DOCUMENT ME!
   * @param  orderItemPriceDetail  DOCUMENT ME!
   */
  public PromotableOrderItemPriceDetailAdjustmentImpl(OrderItemPriceDetailAdjustment itemAdjustment,
    PromotableOrderItemPriceDetail orderItemPriceDetail) {
    assert (orderItemPriceDetail != null);
    adjustmentValue                = itemAdjustment.getValue();
    saleAdjustmentValue            = itemAdjustment.getSalesPriceValue();
    retailAdjustmentValue          = itemAdjustment.getRetailPriceValue();
    appliedToSalePrice             = itemAdjustment.isAppliedToSalePrice();
    promotableOrderItemPriceDetail = orderItemPriceDetail;
    offer                          = itemAdjustment.getOffer();
  }

  /*
   * Calculates the value of the adjustment for both retail and sale prices.
   * If either adjustment is greater than the item value, it will be set to the
   * currentItemValue (e.g. no adjustment can cause a negative value).
   */
  /**
   * DOCUMENT ME!
   */
  protected void computeAdjustmentValues() {
    saleAdjustmentValue   = new Money(getCurrency());
    retailAdjustmentValue = new Money(getCurrency());

    Money currentPriceDetailRetailValue = promotableOrderItemPriceDetail.calculateItemUnitPriceWithAdjustments(false);
    Money currentPriceDetailSalesValue  = promotableOrderItemPriceDetail.calculateItemUnitPriceWithAdjustments(true);

    if (currentPriceDetailSalesValue == null) {
      currentPriceDetailSalesValue = currentPriceDetailRetailValue;
    }

    BigDecimal offerUnitValue = PromotableOfferUtility.determineOfferUnitValue(offer, promotableCandidateItemOffer);

    retailAdjustmentValue = PromotableOfferUtility.computeAdjustmentValue(currentPriceDetailRetailValue, offerUnitValue,
        this, this);

    if (offer.getApplyDiscountToSalePrice() == true) {
      saleAdjustmentValue = PromotableOfferUtility.computeAdjustmentValue(currentPriceDetailSalesValue, offerUnitValue,
          this, this);
    }
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetailAdjustment#getRetailAdjustmentValue()
   */
  @Override public Money getRetailAdjustmentValue() {
    return retailAdjustmentValue;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetailAdjustment#getSaleAdjustmentValue()
   */
  @Override public Money getSaleAdjustmentValue() {
    return saleAdjustmentValue;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.OfferHolder#getCurrency()
   */
  @Override public BroadleafCurrency getCurrency() {
    return promotableOrderItemPriceDetail.getPromotableOrderItem().getCurrency();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetailAdjustment#getPromotableOrderItemPriceDetail()
   */
  @Override public PromotableOrderItemPriceDetail getPromotableOrderItemPriceDetail() {
    return promotableOrderItemPriceDetail;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.OfferHolder#getOffer()
   */
  @Override public Offer getOffer() {
    return offer;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetailAdjustment#isCombinable()
   */
  @Override public boolean isCombinable() {
    Boolean combinable = offer.isCombinableWithOtherOffers();

    return ((combinable != null) && combinable);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetailAdjustment#isTotalitarian()
   */
  @Override public boolean isTotalitarian() {
    Boolean totalitarian = offer.isTotalitarianOffer();

    return ((totalitarian != null) && totalitarian.booleanValue());
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetailAdjustment#getOfferId()
   */
  @Override public Long getOfferId() {
    return offer.getId();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetailAdjustment#getAdjustmentValue()
   */
  @Override public Money getAdjustmentValue() {
    return adjustmentValue;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetailAdjustment#isAppliedToSalePrice()
   */
  @Override public boolean isAppliedToSalePrice() {
    return appliedToSalePrice;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetailAdjustment#finalizeAdjustment(boolean)
   */
  @Override public void finalizeAdjustment(boolean useSalePrice) {
    appliedToSalePrice = useSalePrice;

    if (useSalePrice) {
      adjustmentValue = saleAdjustmentValue;
    } else {
      adjustmentValue = retailAdjustmentValue;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetailAdjustment#copy()
   */
  @Override public PromotableOrderItemPriceDetailAdjustment copy() {
    PromotableOrderItemPriceDetailAdjustmentImpl newAdjustment = new PromotableOrderItemPriceDetailAdjustmentImpl(
        promotableCandidateItemOffer, promotableOrderItemPriceDetail);
    newAdjustment.adjustmentValue       = adjustmentValue;
    newAdjustment.saleAdjustmentValue   = saleAdjustmentValue;
    newAdjustment.retailAdjustmentValue = retailAdjustmentValue;
    newAdjustment.appliedToSalePrice    = appliedToSalePrice;

    return newAdjustment;
  }
} // end class PromotableOrderItemPriceDetailAdjustmentImpl
