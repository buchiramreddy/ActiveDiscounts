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

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.Order;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PromotableOrder extends Serializable {
  /**
   * Sets the order subTotal to the sum of item total prices without adjustments.
   */
  void setOrderSubTotalToPriceWithoutAdjustments();

  /**
   * Sets the order subTotal to the sum of item total prices without adjustments.
   */
  void setOrderSubTotalToPriceWithAdjustments();

  /**
   * Returns all OrderItems for the order wrapped with PromotableOrderItem.
   *
   * @return  all OrderItems for the order wrapped with PromotableOrderItem.
   */
  List<PromotableOrderItem> getAllOrderItems();

  /**
   * Returns all OrderItems that can receive discounts. Sorts the results by SalePrice or RetailPrice depending upon the
   * passed in variable.
   *
   * @param   sortBySalePrice  DOCUMENT ME!
   *
   * @return  all OrderItems that can receive discounts.
   */
  List<PromotableOrderItem> getDiscountableOrderItems(boolean sortBySalePrice);

  /**
   * Returns all OrderItems that can receive discounts.
   *
   * @return  all OrderItems that can receive discounts.
   */
  List<PromotableOrderItem> getDiscountableOrderItems();

  /**
   * Returns the fulfillmentGroups associated with the order after converting them to promotableFulfillmentGroups.
   *
   * @return  the fulfillmentGroups associated with the order after converting them to promotableFulfillmentGroups.
   */
  List<PromotableFulfillmentGroup> getFulfillmentGroups();

  /**
   * Returns true if this promotableOrder has any order adjustments.
   *
   * @return  true if this promotableOrder has any order adjustments.
   */
  boolean isHasOrderAdjustments();

  /**
   * Returns the list of orderAdjustments being proposed for the order. This will be converted to actual order
   * adjustments on completion of the offer processing.
   *
   * @return  the list of orderAdjustments being proposed for the order.
   */
  List<PromotableOrderAdjustment> getCandidateOrderAdjustments();

  /**
   * Adds the adjustment to the order's adjustment list and discounts the order's adjustment price by the value of the
   * adjustment.
   *
   * @param  orderAdjustment  DOCUMENT ME!
   */
  void addCandidateOrderAdjustment(PromotableOrderAdjustment orderAdjustment);

  /**
   * Removes all order, order item, and fulfillment adjustments from the order and resets the adjustment price.
   */
  void removeAllCandidateOfferAdjustments();

  /**
   * Removes all order adjustments from the order and resets the adjustment price.
   */
  void removeAllCandidateOrderOfferAdjustments();

  /**
   * Removes all adjustments from the order's order items and resets the adjustment price for each item.
   */
  void removeAllCandidateItemOfferAdjustments();

  /**
   * Removes all adjustments from the order's fulfillment items and resets the adjustment price for each item.
   */
  void removeAllCandidateFulfillmentOfferAdjustments();

  /**
   * Adds the underlying order to the rule variable map.
   *
   * @param  ruleVars  DOCUMENT ME!
   */
  void updateRuleVariables(Map<String, Object> ruleVars);

  /**
   * Returns the associated order.
   *
   * @return  the associated order.
   */
  Order getOrder();

  /**
   * Returns true if a totalitarian offer has been applied. A totalitarian offer is an offer that does not allow any
   * other offers to be used at the same time. As opposed to a "non-combinable" offer which can't be used with other
   * offers of the same type but can be used with other offers of a different type (e.g. a non-combinable order offer
   * can be used with a non-combinable item offer).
   *
   * @return  true if a totalitarian offer has been applied.
   */
  boolean isTotalitarianOfferApplied();

  /**
   * Calculates the total adjustment to be received from the order adjustments.
   *
   * @return  calculates the total adjustment to be received from the order adjustments.
   */
  Money calculateOrderAdjustmentTotal();

  /**
   * Calculates the total adjustment to be received from the item adjustments.
   *
   * @return  calculates the total adjustment to be received from the item adjustments.
   */
  Money calculateItemAdjustmentTotal();

  /**
   * Returns all of the price detail items for this order.
   *
   * @return  all of the price detail items for this order.
   */
  List<PromotableOrderItemPriceDetail> getAllPromotableOrderItemPriceDetails();

  /**
   * Returns true if this order can apply another order promotion. Returns false if a totalitarian or not-combinable
   * offer has already been applied Returns false if the passed in order is not-combinable or totalitarian and this
   * order already has adjustments
   *
   * @param   offer  DOCUMENT ME!
   *
   * @return  true if this order can apply another order promotion.
   */
  boolean canApplyOrderOffer(PromotableCandidateOrderOffer offer);

  /**
   * Returns the {@link org.broadleafcommerce.common.currency.domain.BroadleafCurrency} for the current order.
   *
   * @return  the {@link org.broadleafcommerce.common.currency.domain.BroadleafCurrency} for the current order.
   */
  BroadleafCurrency getOrderCurrency();

  /**
   * Sets the total fulfillmentCharges the order.
   *
   * @param  totalFulfillmentCharges  DOCUMENT ME!
   */
  void setTotalFufillmentCharges(Money totalFulfillmentCharges);

  /**
   * Returns the price of the order without adjustments.
   *
   * @return  the price of the order without adjustments.
   */
  Money calculateSubtotalWithoutAdjustments();

  /**
   * Returns the price of the order with adjustments.
   *
   * @return  the price of the order with adjustments.
   */
  Money calculateSubtotalWithAdjustments();

  /**
   * Returns true if this order was created in a way that existing order and item adjustments were copied over to this
   * item.
   *
   * @return  true if this order was created in a way that existing order and item adjustments were copied over to this
   *          item.
   */
  boolean isIncludeOrderAndItemAdjustments();
} // end interface PromotableOrder
