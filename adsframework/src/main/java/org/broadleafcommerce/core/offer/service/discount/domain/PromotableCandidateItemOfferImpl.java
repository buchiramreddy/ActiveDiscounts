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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferItemCriteria;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PromotableCandidateItemOfferImpl extends AbstractPromotionRounding implements PromotableCandidateItemOffer,
  OfferHolder {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  protected Offer           offer;

  /** DOCUMENT ME! */
  protected PromotableOrder promotableOrder;

  /** DOCUMENT ME! */
  protected Money           potentialSavings;

  /** DOCUMENT ME! */
  protected int             uses = 0;

  /** DOCUMENT ME! */
  protected HashMap<OfferItemCriteria, List<PromotableOrderItem>> candidateQualifiersMap =
    new HashMap<OfferItemCriteria, List<PromotableOrderItem>>();

  /** DOCUMENT ME! */
  protected List<PromotableOrderItem>                             candidateTargets =
    new ArrayList<PromotableOrderItem>();


  /**
   * Creates a new PromotableCandidateItemOfferImpl object.
   *
   * @param  promotableOrder  DOCUMENT ME!
   * @param  offer            DOCUMENT ME!
   */
  public PromotableCandidateItemOfferImpl(PromotableOrder promotableOrder, Offer offer) {
    assert (offer != null);
    assert (promotableOrder != null);
    this.offer           = offer;
    this.promotableOrder = promotableOrder;
  }

  /**
   * This method determines how much the customer might save using this promotion for the purpose of sorting promotions
   * with the same priority. The assumption is that any possible target specified for BOGO style offers are of equal or
   * lesser value. We are using a calculation based on the qualifiers here strictly for rough comparative purposes.
   *
   * <p>If two promotions have the same priority, the one with the highest potential savings will be used as the
   * tie-breaker to determine the order to apply promotions.</p>
   *
   * <p>This method makes a good approximation of the promotion value as determining the exact value would require all
   * permutations of promotions to be run resulting in a costly operation.</p>
   *
   * @return  this method determines how much the customer might save using this promotion for the purpose of sorting
   *          promotions with the same priority.
   */
  public Money calculatePotentialSavings() {
    Money savings      = new Money(0D);
    int   maxUses      = calculateMaximumNumberOfUses();
    int   appliedCount = 0;

    for (PromotableOrderItem chgItem : candidateTargets) {
      // TODO:  BCP - Transferred the original logic but it looks like there is a bug here
      // when a targetItemCriteria has a quantity > 1.
      int qtyToReceiveSavings = Math.min(chgItem.getQuantity(), maxUses);
      savings = savings.add(calculateSavingsForOrderItem(chgItem, qtyToReceiveSavings));

      appliedCount = appliedCount + qtyToReceiveSavings;

      if (appliedCount >= maxUses) {
        return savings;
      }
    }

    return savings;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.OfferHolder#getCurrency()
   */
  @Override public BroadleafCurrency getCurrency() {
    return promotableOrder.getOrderCurrency();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#calculateSavingsForOrderItem(org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem,
   *       int)
   */
  @Override public Money calculateSavingsForOrderItem(PromotableOrderItem orderItem, int qtyToReceiveSavings) {
    Money savings = new Money(promotableOrder.getOrderCurrency());
    Money price   = orderItem.getPriceBeforeAdjustments(getOffer().getApplyDiscountToSalePrice());

    BigDecimal offerUnitValue = PromotableOfferUtility.determineOfferUnitValue(offer, this);
    savings = PromotableOfferUtility.computeAdjustmentValue(price, offerUnitValue, this, this);

    return savings.multiply(qtyToReceiveSavings);
  }

  /**
   * Returns the number of items that potentially could be targets for the offer. Due to combination or bogo logic, they
   * may not all get the tiered offer price.
   *
   * @return  the number of items that potentially could be targets for the offer.
   */
  @Override public int calculateTargetQuantityForTieredOffer() {
    int returnQty = 0;

    for (PromotableOrderItem promotableOrderItem : candidateTargets) {
      returnQty += promotableOrderItem.getQuantity();
    }

    return returnQty;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#getPotentialSavings()
   */
  @Override public Money getPotentialSavings() {
    if (potentialSavings == null) {
      return new Money(promotableOrder.getOrderCurrency());
    }

    return potentialSavings;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#setPotentialSavings(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setPotentialSavings(Money potentialSavings) {
    this.potentialSavings = potentialSavings;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#hasQualifyingItemCriteria()
   */
  @Override public boolean hasQualifyingItemCriteria() {
    return ((offer.getQualifyingItemCriteria() != null) && !offer.getQualifyingItemCriteria().isEmpty());
  }

  /**
   * Determines the maximum number of times this promotion can be used based on the ItemCriteria and promotion's maxQty
   * setting.
   *
   * @return  determines the maximum number of times this promotion can be used based on the ItemCriteria and
   *          promotion's maxQty setting.
   */
  @Override public int calculateMaximumNumberOfUses() {
    int maxMatchesFound = 9999; // set arbitrarily high / algorithm will adjust down

    // iterate through the target criteria and find the least amount of max uses. This will be the overall
    // max usage, since the target criteria are grouped together in "and" style.
    int numberOfUsesForThisItemCriteria = maxMatchesFound;

    for (OfferItemCriteria targetCriteria : getOffer().getTargetItemCriteria()) {
      int temp = calculateMaxUsesForItemCriteria(targetCriteria, getOffer());
      numberOfUsesForThisItemCriteria = Math.min(numberOfUsesForThisItemCriteria, temp);
    }

    maxMatchesFound = Math.min(maxMatchesFound, numberOfUsesForThisItemCriteria);

    int offerMaxUses = (getOffer().getMaxUses() == 0) ? maxMatchesFound : getOffer().getMaxUses();

    return Math.min(maxMatchesFound, offerMaxUses);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#calculateMaxUsesForItemCriteria(org.broadleafcommerce.core.offer.domain.OfferItemCriteria,
   *       org.broadleafcommerce.core.offer.domain.Offer)
   */
  @Override public int calculateMaxUsesForItemCriteria(OfferItemCriteria itemCriteria, Offer promotion) {
    int numberOfTargets                 = 0;
    int numberOfUsesForThisItemCriteria = 9999;

    if (itemCriteria != null) {
      for (PromotableOrderItem potentialTarget : candidateTargets) {
        numberOfTargets += potentialTarget.getQuantity();
      }

      numberOfUsesForThisItemCriteria = numberOfTargets / itemCriteria.getQuantity();
    }

    return numberOfUsesForThisItemCriteria;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#getCandidateQualifiersMap()
   */
  @Override public HashMap<OfferItemCriteria, List<PromotableOrderItem>> getCandidateQualifiersMap() {
    return candidateQualifiersMap;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#setCandidateQualifiersMap(java.util.HashMap)
   */
  @Override public void setCandidateQualifiersMap(
    HashMap<OfferItemCriteria, List<PromotableOrderItem>> candidateItemsMap) {
    this.candidateQualifiersMap = candidateItemsMap;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#getCandidateTargets()
   */
  @Override public List<PromotableOrderItem> getCandidateTargets() {
    return candidateTargets;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#setCandidateTargets(java.util.List)
   */
  @Override public void setCandidateTargets(List<PromotableOrderItem> candidateTargets) {
    this.candidateTargets = candidateTargets;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#getPriority()
   */
  @Override public int getPriority() {
    return offer.getPriority();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#getOffer()
   */
  @Override public Offer getOffer() {
    return offer;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#getUses()
   */
  @Override public int getUses() {
    return uses;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#addUse()
   */
  @Override public void addUse() {
    uses++;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateItemOffer#isLegacyOffer()
   */
  @Override public boolean isLegacyOffer() {
    return offer.getQualifyingItemCriteria().isEmpty() && offer.getTargetItemCriteria().isEmpty();
  }
} // end class PromotableCandidateItemOfferImpl
