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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderItemContainer;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PromotableFulfillmentGroupImpl implements PromotableFulfillmentGroup {
  private static final long           serialVersionUID       = 1L;

  /** DOCUMENT ME! */
  protected FulfillmentGroup          fulfillmentGroup;

  /** DOCUMENT ME! */
  protected PromotableOrder           promotableOrder;

  /** DOCUMENT ME! */
  protected PromotableItemFactory     itemFactory;

  /** DOCUMENT ME! */
  protected List<PromotableOrderItem> discountableOrderItems;

  /** DOCUMENT ME! */
  protected boolean                   useSaleAdjustments = false;

  /** DOCUMENT ME! */
  protected Money                     adjustedPrice;

  /** DOCUMENT ME! */
  public List<PromotableFulfillmentGroupAdjustment> candidateFulfillmentGroupAdjustments =
    new ArrayList<PromotableFulfillmentGroupAdjustment>();

  /**
   * Creates a new PromotableFulfillmentGroupImpl object.
   *
   * @param  fulfillmentGroup  DOCUMENT ME!
   * @param  promotableOrder   DOCUMENT ME!
   * @param  itemFactory       DOCUMENT ME!
   */
  public PromotableFulfillmentGroupImpl(FulfillmentGroup fulfillmentGroup,
    PromotableOrder promotableOrder,
    PromotableItemFactory itemFactory) {
    this.fulfillmentGroup = fulfillmentGroup;
    this.promotableOrder  = promotableOrder;
    this.itemFactory      = itemFactory;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroup#getFulfillmentGroup()
   */
  @Override public FulfillmentGroup getFulfillmentGroup() {
    return fulfillmentGroup;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroup#updateRuleVariables(java.util.Map)
   */
  @Override public void updateRuleVariables(Map<String, Object> ruleVars) {
    ruleVars.put("fulfillmentGroup", fulfillmentGroup);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroup#getDiscountableOrderItems()
   */
  @Override public List<PromotableOrderItem> getDiscountableOrderItems() {
    if (discountableOrderItems != null) {
      return discountableOrderItems;
    }

    discountableOrderItems = new ArrayList<PromotableOrderItem>();

    List<Long> discountableOrderItemIds = new ArrayList<Long>();

    for (FulfillmentGroupItem fgItem : fulfillmentGroup.getFulfillmentGroupItems()) {
      OrderItem orderItem = fgItem.getOrderItem();

      if (orderItem.isDiscountingAllowed()) {
        discountableOrderItemIds.add(fgItem.getOrderItem().getId());
      } else {
        if (orderItem instanceof OrderItemContainer) {
          OrderItemContainer orderItemContainer = (OrderItemContainer) orderItem;

          if (orderItemContainer.getAllowDiscountsOnChildItems()) {
            for (OrderItem containedOrderItem : orderItemContainer.getOrderItems()) {
              if (!containedOrderItem.isDiscountingAllowed()) {
                discountableOrderItemIds.add(containedOrderItem.getId());
              }
            }
          }
        }
      }
    }

    for (PromotableOrderItem item : promotableOrder.getDiscountableOrderItems()) {
      if (discountableOrderItemIds.contains(item.getOrderItemId())) {
        discountableOrderItems.add(item);
      }
    }

    return discountableOrderItems;
  } // end method getDiscountableOrderItems

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Money getSalePriceBeforeAdjustments() {
    Money salePrice = fulfillmentGroup.getSaleFulfillmentPrice();

    if (salePrice == null) {
      return fulfillmentGroup.getRetailFulfillmentPrice();
    } else {
      return salePrice;
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Money calculateSaleAdjustmentPrice() {
    Money returnPrice = getSalePriceBeforeAdjustments();

    for (PromotableFulfillmentGroupAdjustment adjustment : candidateFulfillmentGroupAdjustments) {
      returnPrice = returnPrice.subtract(adjustment.getSaleAdjustmentValue());
    }

    return returnPrice;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Money calculateRetailAdjustmentPrice() {
    Money returnPrice = fulfillmentGroup.getRetailFulfillmentPrice();

    for (PromotableFulfillmentGroupAdjustment adjustment : candidateFulfillmentGroupAdjustments) {
      returnPrice = returnPrice.subtract(adjustment.getRetailAdjustmentValue());
    }

    return returnPrice;
  }

  /**
   * This method will check to see if the salePriceAdjustments or retailPriceAdjustments are better and remove those
   * that should not apply.
   */
  @Override public void chooseSaleOrRetailAdjustments() {
    this.useSaleAdjustments = Boolean.FALSE;

    Money saleAdjustmentPrice   = calculateSaleAdjustmentPrice();
    Money retailAdjustmentPrice = calculateRetailAdjustmentPrice();

    if (saleAdjustmentPrice.lessThan(retailAdjustmentPrice)) {
      this.useSaleAdjustments = Boolean.TRUE;
      adjustedPrice           = saleAdjustmentPrice;
    } else {
      adjustedPrice = retailAdjustmentPrice;
    }

    if (useSaleAdjustments) {
      removeRetailOnlyAdjustments();
    }

    removeZeroDollarAdjustments(useSaleAdjustments);

    finalizeAdjustments(useSaleAdjustments);

  }

  /**
   * DOCUMENT ME!
   *
   * @param  useSaleAdjustments  DOCUMENT ME!
   */
  protected void finalizeAdjustments(boolean useSaleAdjustments) {
    for (PromotableFulfillmentGroupAdjustment adjustment : candidateFulfillmentGroupAdjustments) {
      adjustment.finalizeAdjustment(useSaleAdjustments);
    }
  }

  /**
   * Removes retail only adjustments.
   */
  protected void removeRetailOnlyAdjustments() {
    Iterator<PromotableFulfillmentGroupAdjustment> adjustments = candidateFulfillmentGroupAdjustments.iterator();

    while (adjustments.hasNext()) {
      PromotableFulfillmentGroupAdjustment adjustment = adjustments.next();

      if (adjustment.getPromotableCandidateFulfillmentGroupOffer().getOffer().getApplyDiscountToSalePrice() == false) {
        adjustments.remove();
      }
    }
  }

  /**
   * If removeUnusedAdjustments is s.
   *
   * @param  useSalePrice  useSaleAdjustments
   */
  protected void removeZeroDollarAdjustments(boolean useSalePrice) {
    Iterator<PromotableFulfillmentGroupAdjustment> adjustments = candidateFulfillmentGroupAdjustments.iterator();

    while (adjustments.hasNext()) {
      PromotableFulfillmentGroupAdjustment adjustment = adjustments.next();

      if (useSalePrice) {
        if (adjustment.getSaleAdjustmentValue().isZero()) {
          adjustments.remove();
        }
      } else {
        if (adjustment.getRetailAdjustmentValue().isZero()) {
          adjustments.remove();
        }
      }
    }
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroup#getFinalizedPriceWithAdjustments()
   */
  @Override public Money getFinalizedPriceWithAdjustments() {
    chooseSaleOrRetailAdjustments();

    return adjustedPrice;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroup#calculatePriceWithoutAdjustments()
   */
  @Override public Money calculatePriceWithoutAdjustments() {
    if (fulfillmentGroup.getSaleFulfillmentPrice() != null) {
      return fulfillmentGroup.getSaleFulfillmentPrice();
    } else {
      return fulfillmentGroup.getRetailFulfillmentPrice();
    }
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroup#addCandidateFulfillmentGroupAdjustment(org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroupAdjustment)
   */
  @Override public void addCandidateFulfillmentGroupAdjustment(PromotableFulfillmentGroupAdjustment adjustment) {
    candidateFulfillmentGroupAdjustments.add(adjustment);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroup#getCandidateFulfillmentGroupAdjustments()
   */
  @Override public List<PromotableFulfillmentGroupAdjustment> getCandidateFulfillmentGroupAdjustments() {
    return Collections.unmodifiableList(candidateFulfillmentGroupAdjustments);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroup#canApplyOffer(org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateFulfillmentGroupOffer)
   */
  @Override public boolean canApplyOffer(PromotableCandidateFulfillmentGroupOffer fulfillmentGroupOffer) {
    if (candidateFulfillmentGroupAdjustments.size() > 0) {
      if (!fulfillmentGroupOffer.getOffer().isCombinableWithOtherOffers()) {
        return false;
      }

      for (PromotableFulfillmentGroupAdjustment adjustment : candidateFulfillmentGroupAdjustments) {
        if (!adjustment.isCombinable()) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroup#calculatePriceWithAdjustments(boolean)
   */
  @Override public Money calculatePriceWithAdjustments(boolean useSalePrice) {
    if (useSalePrice) {
      return calculateSaleAdjustmentPrice();
    } else {
      return calculateRetailAdjustmentPrice();
    }
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroup#isTotalitarianOfferApplied()
   */
  @Override public boolean isTotalitarianOfferApplied() {
    for (PromotableFulfillmentGroupAdjustment adjustment : candidateFulfillmentGroupAdjustments) {
      if (adjustment.getPromotableCandidateFulfillmentGroupOffer().getOffer().isTotalitarianOffer()) {
        return true;
      }
    }

    return false;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableFulfillmentGroup#removeAllCandidateAdjustments()
   */
  @Override public void removeAllCandidateAdjustments() {
    candidateFulfillmentGroupAdjustments.clear();
  }
} // end class PromotableFulfillmentGroupImpl
