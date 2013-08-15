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

import java.io.Serializable;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PromotableFulfillmentGroup extends Serializable {
  /**
   * Adds a fulfillmentGroupAdjustment.
   *
   * @param  adjustment  DOCUMENT ME!
   */
  void addCandidateFulfillmentGroupAdjustment(PromotableFulfillmentGroupAdjustment adjustment);

  /**
   * Adds a fulfillmentGroupAdjustment.
   *
   * @return  adds a fulfillmentGroupAdjustment.
   */
  List<PromotableFulfillmentGroupAdjustment> getCandidateFulfillmentGroupAdjustments();

  /**
   * Removes all candidate adjustments.
   */
  void removeAllCandidateAdjustments();

  /**
   * This method will check to see if the saleAdjustments or retail only adjustments are better and finalize the set
   * that achieves the best result for the customer.
   */
  void chooseSaleOrRetailAdjustments();

  /**
   * Returns the decorated FulfillmentGroup.
   *
   * @return  the decorated FulfillmentGroup.
   */
  FulfillmentGroup getFulfillmentGroup();

  /**
   * Adds the underlying fulfillmentGroup to the rule variable map.
   *
   * @param  ruleVars  DOCUMENT ME!
   */
  void updateRuleVariables(Map<String, Object> ruleVars);

  /**
   * Calculates the price with adjustments. Uses the sale or retail adjustments based on the passed in parameter.
   *
   * @param   useSalePrice  DOCUMENT ME!
   *
   * @return  calculates the price with adjustments.
   */
  Money calculatePriceWithAdjustments(boolean useSalePrice);

  /**
   * Calculates the price with all adjustments. May error in the case where adjustments have not been finalized with a
   * call to chooseSaleOrRetailAdjustments.
   *
   * @return  calculates the price with all adjustments.
   */
  Money getFinalizedPriceWithAdjustments();

  /**
   * Return list of discountable discrete order items contained in this fulfillmentGroup.
   *
   * @return  return list of discountable discrete order items contained in this fulfillmentGroup.
   */
  List<PromotableOrderItem> getDiscountableOrderItems();

  /**
   * Checks to see if the offer can be added to this fulfillmentGroup based on whether or not it is combinable or if
   * this fulfillmentGroup already has a non-combinable offer applied.
   *
   * @param   fulfillmentGroupOffer  DOCUMENT ME!
   *
   * @return  checks to see if the offer can be added to this fulfillmentGroup based on whether or not it is combinable
   *          or if this fulfillmentGroup already has a non-combinable offer applied.
   */
  boolean canApplyOffer(PromotableCandidateFulfillmentGroupOffer fulfillmentGroupOffer);

  /**
   * Returns the price of this fulfillment group if no adjustments were applied.
   *
   * @return  the price of this fulfillment group if no adjustments were applied.
   */
  Money calculatePriceWithoutAdjustments();

  /**
   * Returns true if totalitarian offer was applied to this promotable fulfillment group.
   *
   * @return  true if totalitarian offer was applied to this promotable fulfillment group.
   */
  boolean isTotalitarianOfferApplied();

} // end interface PromotableFulfillmentGroup
