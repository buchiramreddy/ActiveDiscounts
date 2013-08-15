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
import java.util.Set;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferItemCriteria;
import org.broadleafcommerce.core.offer.service.discount.PromotionDiscount;
import org.broadleafcommerce.core.offer.service.discount.PromotionQualifier;
import org.broadleafcommerce.core.offer.service.type.OfferItemRestrictionRuleType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PromotableOrderItemPriceDetailImpl implements PromotableOrderItemPriceDetail {
  /** DOCUMENT ME! */
  protected PromotableOrderItem                            promotableOrderItem;

  /** DOCUMENT ME! */
  protected List<PromotableOrderItemPriceDetailAdjustment> promotableOrderItemPriceDetailAdjustments =
    new ArrayList<PromotableOrderItemPriceDetailAdjustment>();

  /** DOCUMENT ME! */
  protected List<PromotionDiscount>                        promotionDiscounts   = new ArrayList<PromotionDiscount>();

  /** DOCUMENT ME! */
  protected List<PromotionQualifier>                       promotionQualifiers  = new ArrayList<PromotionQualifier>();

  /** DOCUMENT ME! */
  protected int                                            quantity;

  /** DOCUMENT ME! */
  protected boolean                                        useSaleAdjustments   = false;

  /** DOCUMENT ME! */
  protected boolean                                        adjustmentsFinalized = false;

  /** DOCUMENT ME! */
  protected Money                                          adjustedTotal;

  /**
   * Creates a new PromotableOrderItemPriceDetailImpl object.
   *
   * @param  promotableOrderItem  DOCUMENT ME!
   * @param  quantity             DOCUMENT ME!
   */
  public PromotableOrderItemPriceDetailImpl(PromotableOrderItem promotableOrderItem, int quantity) {
    this.promotableOrderItem = promotableOrderItem;
    this.quantity            = quantity;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#addCandidateItemPriceDetailAdjustment(org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetailAdjustment)
   */
  @Override public void addCandidateItemPriceDetailAdjustment(PromotableOrderItemPriceDetailAdjustment itemAdjustment) {
    promotableOrderItemPriceDetailAdjustments.add(itemAdjustment);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#getCandidateItemAdjustments()
   */
  @Override public List<PromotableOrderItemPriceDetailAdjustment> getCandidateItemAdjustments() {
    return Collections.unmodifiableList(promotableOrderItemPriceDetailAdjustments);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#hasNonCombinableAdjustments()
   */
  @Override public boolean hasNonCombinableAdjustments() {
    for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
      if (!adjustment.isCombinable()) {
        return true;
      }
    }

    return false;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected boolean hasOrderItemAdjustments() {
    return promotableOrderItemPriceDetailAdjustments.size() > 0;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#isTotalitarianOfferApplied()
   */
  @Override public boolean isTotalitarianOfferApplied() {
    for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
      if (adjustment.isTotalitarian()) {
        return true;
      }
    }

    return false;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#isNonCombinableOfferApplied()
   */
  @Override public boolean isNonCombinableOfferApplied() {
    for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
      if (!adjustment.isCombinable()) {
        return true;
      }
    }

    return false;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money calculateSaleAdjustmentUnitPrice() {
    Money returnPrice = promotableOrderItem.getSalePriceBeforeAdjustments();

    if (returnPrice == null) {
      returnPrice = promotableOrderItem.getRetailPriceBeforeAdjustments();
    }

    for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
      returnPrice = returnPrice.subtract(adjustment.getSaleAdjustmentValue());
    }

    return returnPrice;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money calculateRetailAdjustmentUnitPrice() {
    Money returnPrice = promotableOrderItem.getRetailPriceBeforeAdjustments();

    for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
      returnPrice = returnPrice.subtract(adjustment.getRetailAdjustmentValue());
    }

    return returnPrice;
  }

  /**
   * This method will check to see if the salePriceAdjustments or retailPriceAdjustments are better and remove those
   * that should not apply.
   */
  @Override public void chooseSaleOrRetailAdjustments() {
    adjustmentsFinalized    = true;
    adjustedTotal           = null;
    this.useSaleAdjustments = Boolean.FALSE;

    Money salePriceBeforeAdjustments   = promotableOrderItem.getSalePriceBeforeAdjustments();
    Money retailPriceBeforeAdjustments = promotableOrderItem.getRetailPriceBeforeAdjustments();

    if (hasOrderItemAdjustments()) {
      Money saleAdjustmentPrice   = calculateSaleAdjustmentUnitPrice();
      Money retailAdjustmentPrice = calculateRetailAdjustmentUnitPrice();

      if (promotableOrderItem.isOnSale()) {
        if (saleAdjustmentPrice.lessThan(retailAdjustmentPrice)) {
          this.useSaleAdjustments = Boolean.TRUE;
          adjustedTotal           = saleAdjustmentPrice;
        } else {
          adjustedTotal = retailAdjustmentPrice;
        }

        if (!adjustedTotal.lessThan(salePriceBeforeAdjustments)) {
          // Adjustments are not as good as the sale price.  So, clear them and use the sale price.
          promotableOrderItemPriceDetailAdjustments.clear();
          adjustedTotal = salePriceBeforeAdjustments;
        }
      } else {
        if (!retailAdjustmentPrice.lessThan(promotableOrderItem.getRetailPriceBeforeAdjustments())) {
          // Adjustments are not as good as the retail price.
          promotableOrderItemPriceDetailAdjustments.clear();
          adjustedTotal = retailPriceBeforeAdjustments;
        } else {
          adjustedTotal = retailAdjustmentPrice;
        }
      }

      if (useSaleAdjustments) {
        removeRetailOnlyAdjustments();
      }

      removeZeroDollarAdjustments(useSaleAdjustments);

      finalizeAdjustments(useSaleAdjustments);
    } // end if

    if (adjustedTotal == null) {
      if (salePriceBeforeAdjustments != null) {
        this.useSaleAdjustments = true;
        adjustedTotal           = salePriceBeforeAdjustments;
      } else {
        adjustedTotal = retailPriceBeforeAdjustments;
      }
    }

    adjustedTotal = adjustedTotal.multiply(quantity);

  } // end method chooseSaleOrRetailAdjustments

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#removeAllAdjustments()
   */
  @Override public void removeAllAdjustments() {
    promotableOrderItemPriceDetailAdjustments.clear();
    chooseSaleOrRetailAdjustments();
  }

  /**
   * DOCUMENT ME!
   *
   * @param  useSaleAdjustments  DOCUMENT ME!
   */
  protected void finalizeAdjustments(boolean useSaleAdjustments) {
    for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
      adjustment.finalizeAdjustment(useSaleAdjustments);
    }
  }

  /**
   * Removes retail only adjustments.
   */
  protected void removeRetailOnlyAdjustments() {
    Iterator<PromotableOrderItemPriceDetailAdjustment> adjustments =
      promotableOrderItemPriceDetailAdjustments.iterator();

    while (adjustments.hasNext()) {
      PromotableOrderItemPriceDetailAdjustment adjustment = adjustments.next();

      if (adjustment.getOffer().getApplyDiscountToSalePrice() == false) {
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
    Iterator<PromotableOrderItemPriceDetailAdjustment> adjustments =
      promotableOrderItemPriceDetailAdjustments.iterator();

    while (adjustments.hasNext()) {
      PromotableOrderItemPriceDetailAdjustment adjustment = adjustments.next();

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
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#getPromotableOrderItem()
   */
  @Override public PromotableOrderItem getPromotableOrderItem() {
    return promotableOrderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#getPromotionDiscounts()
   */
  @Override public List<PromotionDiscount> getPromotionDiscounts() {
    return promotionDiscounts;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#getPromotionQualifiers()
   */
  @Override public List<PromotionQualifier> getPromotionQualifiers() {
    return promotionQualifiers;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#getQuantity()
   */
  @Override public int getQuantity() {
    return quantity;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#setQuantity(int)
   */
  @Override public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  private boolean restrictTarget(Offer offer, boolean targetType) {
    OfferItemRestrictionRuleType qualifierType;

    if (targetType) {
      qualifierType = offer.getOfferItemTargetRuleType();
    } else {
      qualifierType = offer.getOfferItemQualifierRuleType();
    }

    return OfferItemRestrictionRuleType.NONE.equals(qualifierType)
      || OfferItemRestrictionRuleType.QUALIFIER.equals(qualifierType);
  }

  private boolean restrictQualifier(Offer offer, boolean targetType) {
    OfferItemRestrictionRuleType qualifierType;

    if (targetType) {
      qualifierType = offer.getOfferItemTargetRuleType();
    } else {
      qualifierType = offer.getOfferItemQualifierRuleType();
    }

    return OfferItemRestrictionRuleType.NONE.equals(qualifierType)
      || OfferItemRestrictionRuleType.TARGET.equals(qualifierType);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#getQuantityAvailableToBeUsedAsTarget(org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer)
   */
  @Override public int getQuantityAvailableToBeUsedAsTarget(PromotableCandidateItemOffer itemOffer) {
    int   qtyAvailable = quantity;
    Offer promotion    = itemOffer.getOffer();

    // 1. Any quantities of this item that have already received the promotion are not eligible.
    // 2. If this promotion is not combinable then any quantities that have received discounts
    // from other promotions cannot receive this discount
    // 3. If this promotion is combinable then any quantities that have received discounts from
    // other combinable promotions are eligible to receive this discount as well
    boolean combinable = promotion.isCombinableWithOtherOffers();

    if (!combinable && isNonCombinableOfferApplied()) {
      return 0;
    }

    // Any quantities of this item that have already received the promotion are not eligible.
    // Also, any quantities of this item that have received another promotion are not eligible
    // if this promotion cannot be combined with another discount.
    for (PromotionDiscount promotionDiscount : promotionDiscounts) {
      if (promotionDiscount.getPromotion().equals(promotion) || restrictTarget(promotion, true)) {
        qtyAvailable = qtyAvailable - promotionDiscount.getQuantity();
      } else {
        // The other promotion is Combinable, but we must make sure that the item qualifier also allows
        // it to be reused as a target.
        if (restrictTarget(promotionDiscount.getPromotion(), true)) {
          qtyAvailable = qtyAvailable - promotionDiscount.getQuantity();
        }
      }
    }

    // 4.  Any quantities of this item that have been used as a qualifier for this promotion are not eligible as targets
    // 5.  Any quantities of this item that have been used as a qualifier for another promotion that does
    // not allow the qualifier to be reused must be deduced from the qtyAvailable.
    for (PromotionQualifier promotionQualifier : promotionQualifiers) {
      if (promotionQualifier.getPromotion().equals(promotion) || restrictQualifier(promotion, true)) {
        qtyAvailable = qtyAvailable - promotionQualifier.getQuantity();
      } else {
        if (restrictTarget(promotionQualifier.getPromotion(), false)) {
          qtyAvailable = qtyAvailable - promotionQualifier.getQuantity();
        }
      }
    }

    return qtyAvailable;
  } // end method getQuantityAvailableToBeUsedAsTarget

  /**
   * DOCUMENT ME!
   *
   * @param   candidatePromotion  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PromotionQualifier lookupOrCreatePromotionQualifier(PromotableCandidateItemOffer candidatePromotion) {
    Offer promotion = candidatePromotion.getOffer();

    for (PromotionQualifier pq : promotionQualifiers) {
      if (pq.getPromotion().equals(promotion)) {
        return pq;
      }
    }

    PromotionQualifier pq = new PromotionQualifier();
    pq.setPromotion(promotion);
    promotionQualifiers.add(pq);

    return pq;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   candidatePromotion  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PromotionDiscount lookupOrCreatePromotionDiscount(PromotableCandidateItemOffer candidatePromotion) {
    Offer promotion = candidatePromotion.getOffer();

    for (PromotionDiscount pd : promotionDiscounts) {
      if (pd.getPromotion().equals(promotion)) {
        return pd;
      }
    }

    PromotionDiscount pd = new PromotionDiscount();
    pd.setPromotion(promotion);

    promotionDiscounts.add(pd);

    return pd;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#addPromotionQualifier(org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer,
   *       org.broadleafcommerce.core.offer.domain.OfferItemCriteria, int)
   */
  @Override public void addPromotionQualifier(PromotableCandidateItemOffer itemOffer, OfferItemCriteria itemCriteria,
    int qtyToMarkAsQualifier) {
    PromotionQualifier pq = lookupOrCreatePromotionQualifier(itemOffer);
    pq.incrementQuantity(qtyToMarkAsQualifier);
    pq.setItemCriteria(itemCriteria);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#addPromotionDiscount(org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer,
   *       java.util.Set, int)
   */
  @Override public void addPromotionDiscount(PromotableCandidateItemOffer itemOffer,
    Set<OfferItemCriteria> itemCriteria, int qtyToMarkAsTarget) {
    PromotionDiscount pd = lookupOrCreatePromotionDiscount(itemOffer);

    if (pd == null) {
      return;
    }

    pd.incrementQuantity(qtyToMarkAsTarget);
    pd.setItemCriteria(itemCriteria);
    pd.setCandidateItemOffer(itemOffer);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#finalizeQuantities()
   */
  @Override public void finalizeQuantities() {
    for (PromotionDiscount promotionDiscount : promotionDiscounts) {
      promotionDiscount.setFinalizedQuantity(promotionDiscount.getQuantity());
    }

    for (PromotionQualifier promotionQualifier : promotionQualifiers) {
      promotionQualifier.setFinalizedQuantity(promotionQualifier.getQuantity());
    }
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#clearAllNonFinalizedQuantities()
   */
  @Override public void clearAllNonFinalizedQuantities() {
    Iterator<PromotionQualifier> promotionQualifierIterator = promotionQualifiers.iterator();

    while (promotionQualifierIterator.hasNext()) {
      PromotionQualifier promotionQualifier = promotionQualifierIterator.next();

      if (promotionQualifier.getFinalizedQuantity() == 0) {
        // If there are no quantities of this item that are finalized, then remove the item.
        promotionQualifierIterator.remove();
      } else {
        // Otherwise, set the quantity to the number of finalized items.
        promotionQualifier.setQuantity(promotionQualifier.getFinalizedQuantity());
      }
    }

    Iterator<PromotionDiscount> promotionDiscountIterator = promotionDiscounts.iterator();

    while (promotionDiscountIterator.hasNext()) {
      PromotionDiscount promotionDiscount = promotionDiscountIterator.next();

      if (promotionDiscount.getFinalizedQuantity() == 0) {
        // If there are no quantities of this item that are finalized, then remove the item.
        promotionDiscountIterator.remove();
      } else {
        // Otherwise, set the quantity to the number of finalized items.
        promotionDiscount.setQuantity(promotionDiscount.getFinalizedQuantity());
      }
    }

  } // end method clearAllNonFinalizedQuantities

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#getQuantityAvailableToBeUsedAsQualifier(org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer)
   */
  @Override public int getQuantityAvailableToBeUsedAsQualifier(PromotableCandidateItemOffer itemOffer) {
    int   qtyAvailable = quantity;
    Offer promotion    = itemOffer.getOffer();

    // Any quantities of this item that have already received the promotion are not eligible.
    for (PromotionDiscount promotionDiscount : promotionDiscounts) {
      if (promotionDiscount.getPromotion().equals(promotion) || restrictTarget(promotion, false)) {
        qtyAvailable = qtyAvailable - promotionDiscount.getQuantity();
      } else {
        // Item's that receive other discounts might still be allowed to be qualifiers
        if (restrictQualifier(promotionDiscount.getPromotion(), true)) {
          qtyAvailable = qtyAvailable - promotionDiscount.getQuantity();
        }
      }
    }

    // Any quantities of this item that have already been used as a qualifier for this promotion or for
    // another promotion that has a qualifier type of NONE or TARGET_ONLY cannot be used for this promotion
    for (PromotionQualifier promotionQualifier : promotionQualifiers) {
      if (promotionQualifier.getPromotion().equals(promotion) || restrictQualifier(promotion, false)) {
        qtyAvailable = qtyAvailable - promotionQualifier.getQuantity();
      } else {
        if (restrictQualifier(promotionQualifier.getPromotion(), false)) {
          qtyAvailable = qtyAvailable - promotionQualifier.getQuantity();
        }
      }
    }

    return qtyAvailable;
  } // end method getQuantityAvailableToBeUsedAsQualifier

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#calculateItemUnitPriceWithAdjustments(boolean)
   */
  @Override public Money calculateItemUnitPriceWithAdjustments(boolean allowSalePrice) {
    Money priceWithAdjustments = null;

    if (allowSalePrice) {
      priceWithAdjustments = promotableOrderItem.getSalePriceBeforeAdjustments();

      if (priceWithAdjustments == null) {
        return promotableOrderItem.getRetailPriceBeforeAdjustments();
      }
    } else {
      priceWithAdjustments = promotableOrderItem.getRetailPriceBeforeAdjustments();
    }

    for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
      if (allowSalePrice) {
        priceWithAdjustments = priceWithAdjustments.subtract(adjustment.getSaleAdjustmentValue());
      } else {
        priceWithAdjustments = priceWithAdjustments.subtract(adjustment.getRetailAdjustmentValue());
      }
    }

    return priceWithAdjustments;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Money calculateAdjustmentsUnitValue() {
    Money adjustmentUnitValue = new Money(promotableOrderItem.getCurrency());

    for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
      adjustmentUnitValue = adjustmentUnitValue.add(adjustment.getAdjustmentValue());
    }

    return adjustmentUnitValue;
  }

  /**
   * Creates a key that represents a unique priceDetail.
   *
   * @return  creates a key that represents a unique priceDetail.
   */
  @Override public String buildDetailKey() {
    List<Long> offerIds = new ArrayList<Long>();

    for (PromotableOrderItemPriceDetailAdjustment adjustment : promotableOrderItemPriceDetailAdjustments) {
      Long offerId = adjustment.getOffer().getId();
      offerIds.add(offerId);
    }

    Collections.sort(offerIds);

    return promotableOrderItem.getOrderItem().toString() + offerIds.toString() + useSaleAdjustments;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#getFinalizedTotalWithAdjustments()
   */
  @Override public Money getFinalizedTotalWithAdjustments() {
    chooseSaleOrRetailAdjustments();

    return adjustedTotal;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#calculateTotalAdjustmentValue()
   */
  @Override public Money calculateTotalAdjustmentValue() {
    return calculateAdjustmentsUnitValue().multiply(quantity);
  }

  /**
   * DOCUMENT ME!
   *
   * @param   discountQty  DOCUMENT ME!
   * @param   offerId      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected PromotableOrderItemPriceDetail split(int discountQty, Long offerId) {
    int originalQty = quantity;
    quantity = discountQty;

    int splitItemQty = originalQty - discountQty;

    // Create the new item with the correct quantity
    PromotableOrderItemPriceDetail newDetail = promotableOrderItem.createNewDetail(splitItemQty);

    // copy discounts
    for (PromotionDiscount existingDiscount : promotionDiscounts) {
      PromotionDiscount newDiscount = existingDiscount.split(discountQty);

      if (newDiscount != null) {
        newDetail.getPromotionDiscounts().add(newDiscount);
      }
    }

    Iterator<PromotionQualifier> qualifiers = promotionQualifiers.iterator();

    while (qualifiers.hasNext()) {
      PromotionQualifier currentQualifier = qualifiers.next();
      Long               qualifierOfferId = currentQualifier.getPromotion().getId();

      if (qualifierOfferId.equals(offerId) && (currentQualifier.getQuantity() <= splitItemQty)) {
        // Remove this one from the original detail
        qualifiers.remove();
        newDetail.getPromotionQualifiers().add(currentQualifier);
      } else {
        PromotionQualifier newQualifier = currentQualifier.split(splitItemQty);
        newDetail.getPromotionQualifiers().add(newQualifier);
      }
    }

    for (PromotableOrderItemPriceDetailAdjustment existingAdjustment : promotableOrderItemPriceDetailAdjustments) {
      PromotableOrderItemPriceDetailAdjustment newAdjustment = existingAdjustment.copy();
      newDetail.addCandidateItemPriceDetailAdjustment(newAdjustment);
    }

    return newDetail;
  } // end method split

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#splitIfNecessary()
   */
  @Override public PromotableOrderItemPriceDetail splitIfNecessary() {
    PromotableOrderItemPriceDetail returnDetail = null;

    for (PromotionDiscount discount : promotionDiscounts) {
      if (discount.getQuantity() != quantity) {
        Long offerId = discount.getCandidateItemOffer().getOffer().getId();

        return this.split(discount.getQuantity(), offerId);
      }
    }

    return returnDetail;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItemPriceDetail#useSaleAdjustments()
   */
  @Override public boolean useSaleAdjustments() {
    return useSaleAdjustments;
  }

} // end class PromotableOrderItemPriceDetailImpl
