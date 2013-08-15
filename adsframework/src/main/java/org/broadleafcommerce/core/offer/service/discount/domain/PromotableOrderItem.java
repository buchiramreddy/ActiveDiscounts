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

import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderItemContainer;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PromotableOrderItem extends Serializable {
  /**
   * Adds the item to the rule variables map.
   *
   * @param  ruleVars  DOCUMENT ME!
   */
  void updateRuleVariables(Map<String, Object> ruleVars);

  /**
   * Called by pricing engine to reset the state of this item.
   */
  void resetPriceDetails();

  /**
   * Returns true if this item can receive item level discounts.
   *
   * @return  true if this item can receive item level discounts.
   */
  boolean isDiscountingAllowed();

  /**
   * Returns true if this PromotableOrderItem contains other items.
   *
   * @return  true if this PromotableOrderItem contains other items.
   */
  boolean isOrderItemContainer();

  /**
   * Returns an OrderItemContainer for this OrderItem or null if this item is not an instance of OrderItemContainer.
   *
   * @return  an OrderItemContainer for this OrderItem or null if this item is not an instance of OrderItemContainer.
   */
  OrderItemContainer getOrderItemContainer();

  /**
   * Returns the salePrice without adjustments.
   *
   * @return  the salePrice without adjustments.
   */
  Money getSalePriceBeforeAdjustments();

  /**
   * Returns the retailPrice without adjustments.
   *
   * @return  the retailPrice without adjustments.
   */
  Money getRetailPriceBeforeAdjustments();

  /**
   * Returns true if the item has a sale price that is lower than the retail price.
   *
   * @return  true if the item has a sale price that is lower than the retail price.
   */
  boolean isOnSale();

  /**
   * Returns the list of priceDetails associated with this item.
   *
   * @return  the list of priceDetails associated with this item.
   */
  List<PromotableOrderItemPriceDetail> getPromotableOrderItemPriceDetails();

  /**
   * Return the salePriceBeforeAdjustments if the passed in param is true. Otherwise return the
   * retailPriceBeforeAdjustments.
   *
   * @param   applyToSalePrice  DOCUMENT ME!
   *
   * @return  return the salePriceBeforeAdjustments if the passed in param is true.
   */
  Money getPriceBeforeAdjustments(boolean applyToSalePrice);

  /**
   * Returns the basePrice of the item (baseSalePrice or baseRetailPrice).
   *
   * @return  the basePrice of the item (baseSalePrice or baseRetailPrice).
   */
  Money getCurrentBasePrice();

  /**
   * Returns the quantity for this orderItem.
   *
   * @return  the quantity for this orderItem.
   */
  int getQuantity();

  /**
   * Returns the currency of the related order.
   *
   * @return  the currency of the related order.
   */
  BroadleafCurrency getCurrency();

  /**
   * Effectively deletes all priceDetails associated with this item and r.
   */
  void removeAllItemAdjustments();

  /**
   * Merges any priceDetails that share the same adjustments.
   */
  void mergeLikeDetails();

  /**
   * Returns the id of the contained OrderItem.
   *
   * @return  the id of the contained OrderItem.
   */
  Long getOrderItemId();

  /**
   * Returns the value of all adjustments.
   *
   * @return  the value of all adjustments.
   */
  Money calculateTotalAdjustmentValue();

  /**
   * Returns the final total for this item taking into account the finalized adjustments. Intended to be called after
   * the adjustments have been finalized.
   *
   * @return  the final total for this item taking into account the finalized adjustments.
   */
  Money calculateTotalWithAdjustments();

  /**
   * Returns the total for this item if not adjustments applied.
   *
   * @return  the total for this item if not adjustments applied.
   */
  Money calculateTotalWithoutAdjustments();

  /**
   * Creates a new detail with the associated quantity. Intended for use as part of the PriceDetail split.
   *
   * @param   quantity  DOCUMENT ME!
   *
   * @return  creates a new detail with the associated quantity.
   */
  PromotableOrderItemPriceDetail createNewDetail(int quantity);

  /**
   * Returns the underlying orderItem. Manipulation of the underlying orderItem is not recommended. This method is
   * intended for unit test and read only access although that is not strictly enforced.
   *
   * @return  the underlying orderItem.
   */
  OrderItem getOrderItem();
} // end interface PromotableOrderItem
