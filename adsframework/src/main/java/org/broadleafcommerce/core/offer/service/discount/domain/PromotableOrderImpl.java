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
import java.util.List;
import java.util.Map;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.OrderAdjustment;
import org.broadleafcommerce.core.offer.service.discount.OrderItemPriceComparator;
import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderItemContainer;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PromotableOrderImpl implements PromotableOrder {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  protected PromotableItemFactory            itemFactory;

  /** DOCUMENT ME! */
  protected Order                            order;

  /** DOCUMENT ME! */
  protected List<PromotableOrderItem>        allOrderItems;

  /** DOCUMENT ME! */
  protected List<PromotableOrderItem>        discountableOrderItems;

  /** DOCUMENT ME! */
  protected boolean                          currentSortParam               = false;

  /** DOCUMENT ME! */
  protected List<PromotableFulfillmentGroup> fulfillmentGroups;

  /** DOCUMENT ME! */
  protected List<PromotableOrderAdjustment>  candidateOrderOfferAdjustments =
    new ArrayList<PromotableOrderAdjustment>();

  /** DOCUMENT ME! */
  protected boolean                          includeOrderAndItemAdjustments = false;

  /**
   * Creates a new PromotableOrderImpl object.
   *
   * @param  order                           DOCUMENT ME!
   * @param  itemFactory                     DOCUMENT ME!
   * @param  includeOrderAndItemAdjustments  DOCUMENT ME!
   */
  public PromotableOrderImpl(Order order, PromotableItemFactory itemFactory, boolean includeOrderAndItemAdjustments) {
    this.order                          = order;
    this.itemFactory                    = itemFactory;
    this.includeOrderAndItemAdjustments = includeOrderAndItemAdjustments;

    if (includeOrderAndItemAdjustments) {
      createExistingOrderAdjustments();
    }
  }

  /**
   * Bring over the order adjustments. Intended to be used when processing fulfillment orders.
   */
  protected void createExistingOrderAdjustments() {
    if (order.getOrderAdjustments() != null) {
      for (OrderAdjustment adjustment : order.getOrderAdjustments()) {
        if (adjustment.getOffer() != null) {
          PromotableCandidateOrderOffer pcoo = itemFactory.createPromotableCandidateOrderOffer(this,
              adjustment.getOffer(), adjustment.getValue());
          PromotableOrderAdjustment     adj  = itemFactory.createPromotableOrderAdjustment(pcoo, this,
              adjustment.getValue());
          candidateOrderOfferAdjustments.add(adj);
        }
      }
    }
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#setOrderSubTotalToPriceWithoutAdjustments()
   */
  @Override public void setOrderSubTotalToPriceWithoutAdjustments() {
    Money calculatedSubTotal = calculateOrderSubTotalWithoutOrderAdjustments();
    order.setSubTotal(calculatedSubTotal);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#setOrderSubTotalToPriceWithAdjustments()
   */
  @Override public void setOrderSubTotalToPriceWithAdjustments() {
    Money calculatedSubTotal = calculateSubtotalWithAdjustments();
    order.setSubTotal(calculatedSubTotal);
  }

  private Money calculateOrderSubTotalWithoutOrderAdjustments() {
    Money calculatedSubTotal = BroadleafCurrencyUtils.getMoney(order.getCurrency());

    for (OrderItem orderItem : order.getOrderItems()) {
      calculatedSubTotal = calculatedSubTotal.add(orderItem.getTotalPrice());
    }

    return calculatedSubTotal;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#getAllOrderItems()
   */
  @Override public List<PromotableOrderItem> getAllOrderItems() {
    if (allOrderItems == null) {
      allOrderItems = new ArrayList<PromotableOrderItem>();

      for (OrderItem orderItem : order.getOrderItems()) {
        addPromotableOrderItem(orderItem, allOrderItems);
      }
    }

    return allOrderItems;
  }

  // Return the discountableOrderItems in the current order.
  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#getDiscountableOrderItems()
   */
  @Override public List<PromotableOrderItem> getDiscountableOrderItems() {
    return getDiscountableOrderItems(currentSortParam);
  }


  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#getDiscountableOrderItems(boolean)
   */
  @Override public List<PromotableOrderItem> getDiscountableOrderItems(boolean applyDiscountToSalePrice) {
    if ((discountableOrderItems == null) || discountableOrderItems.isEmpty()) {
      discountableOrderItems = buildPromotableOrderItemsList();

      OrderItemPriceComparator priceComparator = new OrderItemPriceComparator(applyDiscountToSalePrice);

      // Sort the items so that the highest priced ones are at the top
      Collections.sort(discountableOrderItems, priceComparator);
      currentSortParam = applyDiscountToSalePrice;
    }

    if (currentSortParam != applyDiscountToSalePrice) {
      // Resort
      OrderItemPriceComparator priceComparator = new OrderItemPriceComparator(applyDiscountToSalePrice);
      Collections.sort(discountableOrderItems, priceComparator);

      currentSortParam = applyDiscountToSalePrice;
    }

    return discountableOrderItems;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected List<PromotableOrderItem> buildPromotableOrderItemsList() {
    List<PromotableOrderItem> discountableOrderItems = new ArrayList<PromotableOrderItem>();

    for (PromotableOrderItem promotableOrderItem : getAllOrderItems()) {
      if (promotableOrderItem.isDiscountingAllowed()) {
        discountableOrderItems.add(promotableOrderItem);
      } else {
        if (promotableOrderItem.isOrderItemContainer()) {
          OrderItemContainer orderItemContainer = promotableOrderItem.getOrderItemContainer();

          if (orderItemContainer.getAllowDiscountsOnChildItems()) {
            for (OrderItem containedOrderItem : orderItemContainer.getOrderItems()) {
              if (containedOrderItem.isDiscountingAllowed()) {
                addPromotableOrderItem(containedOrderItem, discountableOrderItems);
              }
            }
          }
        }
      }
    }

    return discountableOrderItems;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  orderItem               DOCUMENT ME!
   * @param  discountableOrderItems  DOCUMENT ME!
   */
  protected void addPromotableOrderItem(OrderItem orderItem, List<PromotableOrderItem> discountableOrderItems) {
    PromotableOrderItem item = itemFactory.createPromotableOrderItem(orderItem, PromotableOrderImpl.this,
        includeOrderAndItemAdjustments);
    discountableOrderItems.add(item);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#getFulfillmentGroups()
   */
  @Override public List<PromotableFulfillmentGroup> getFulfillmentGroups() {
    if (fulfillmentGroups == null) {
      fulfillmentGroups = new ArrayList<PromotableFulfillmentGroup>();

      for (FulfillmentGroup fulfillmentGroup : order.getFulfillmentGroups()) {
        fulfillmentGroups.add(itemFactory.createPromotableFulfillmentGroup(fulfillmentGroup, this));
      }
    }

    return Collections.unmodifiableList(fulfillmentGroups);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#isHasOrderAdjustments()
   */
  @Override public boolean isHasOrderAdjustments() {
    return candidateOrderOfferAdjustments.size() > 0;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#getCandidateOrderAdjustments()
   */
  @Override public List<PromotableOrderAdjustment> getCandidateOrderAdjustments() {
    return candidateOrderOfferAdjustments;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#addCandidateOrderAdjustment(org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderAdjustment)
   */
  @Override public void addCandidateOrderAdjustment(PromotableOrderAdjustment orderAdjustment) {
    candidateOrderOfferAdjustments.add(orderAdjustment);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#removeAllCandidateOfferAdjustments()
   */
  @Override public void removeAllCandidateOfferAdjustments() {
    removeAllCandidateItemOfferAdjustments();
    removeAllCandidateFulfillmentOfferAdjustments();
    removeAllCandidateOrderOfferAdjustments();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#removeAllCandidateOrderOfferAdjustments()
   */
  @Override public void removeAllCandidateOrderOfferAdjustments() {
    candidateOrderOfferAdjustments.clear();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#removeAllCandidateItemOfferAdjustments()
   */
  @Override public void removeAllCandidateItemOfferAdjustments() {
    for (PromotableOrderItem promotableOrderItem : getDiscountableOrderItems()) {
      promotableOrderItem.removeAllItemAdjustments();
    }
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#removeAllCandidateFulfillmentOfferAdjustments()
   */
  @Override public void removeAllCandidateFulfillmentOfferAdjustments() {
    for (PromotableFulfillmentGroup fulfillmentGroup : getFulfillmentGroups()) {
      fulfillmentGroup.removeAllCandidateAdjustments();
    }
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#updateRuleVariables(java.util.Map)
   */
  @Override public void updateRuleVariables(Map<String, Object> ruleVars) {
    ruleVars.put("order", order);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#getOrder()
   */
  @Override public Order getOrder() {
    return order;
  }


  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#isTotalitarianOfferApplied()
   */
  @Override public boolean isTotalitarianOfferApplied() {
    boolean totalitarianOfferApplied = false;

    for (PromotableOrderAdjustment adjustment : candidateOrderOfferAdjustments) {
      if (adjustment.isTotalitarian()) {
        totalitarianOfferApplied = true;

        break;
      }
    }

    if (!totalitarianOfferApplied) {
      for (PromotableOrderItemPriceDetail itemPriceDetail : getAllPromotableOrderItemPriceDetails()) {
        totalitarianOfferApplied = itemPriceDetail.isTotalitarianOfferApplied();
      }
    }

    if (!totalitarianOfferApplied) {
      for (PromotableFulfillmentGroup fg : getFulfillmentGroups()) {
        if (fg.isTotalitarianOfferApplied()) {
          return true;
        }
      }
    }

    return totalitarianOfferApplied;
  } // end method isTotalitarianOfferApplied

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#calculateOrderAdjustmentTotal()
   */
  @Override public Money calculateOrderAdjustmentTotal() {
    Money orderAdjustmentTotal = BroadleafCurrencyUtils.getMoney(order.getCurrency());

    for (PromotableOrderAdjustment adjustment : candidateOrderOfferAdjustments) {
      orderAdjustmentTotal = orderAdjustmentTotal.add(adjustment.getAdjustmentValue());
    }

    return orderAdjustmentTotal;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#calculateItemAdjustmentTotal()
   */
  @Override public Money calculateItemAdjustmentTotal() {
    Money itemAdjustmentTotal = BroadleafCurrencyUtils.getMoney(order.getCurrency());

    for (PromotableOrderItem item : getDiscountableOrderItems()) {
      itemAdjustmentTotal = itemAdjustmentTotal.add(item.calculateTotalAdjustmentValue());
    }

    return itemAdjustmentTotal;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#getAllPromotableOrderItemPriceDetails()
   */
  @Override public List<PromotableOrderItemPriceDetail> getAllPromotableOrderItemPriceDetails() {
    List<PromotableOrderItemPriceDetail> allPriceDetails = new ArrayList<PromotableOrderItemPriceDetail>();

    for (PromotableOrderItem item : getDiscountableOrderItems()) {
      allPriceDetails.addAll(item.getPromotableOrderItemPriceDetails());
    }

    return allPriceDetails;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#getOrderCurrency()
   */
  @Override public BroadleafCurrency getOrderCurrency() {
    return this.order.getCurrency();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#setTotalFufillmentCharges(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalFufillmentCharges(Money totalFulfillmentCharges) {
    order.setTotalFulfillmentCharges(totalFulfillmentCharges);
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected boolean isNotCombinableOrderOfferApplied() {
    for (PromotableOrderAdjustment adjustment : candidateOrderOfferAdjustments) {
      if (!adjustment.isCombinable()) {
        return true;
      }
    }

    return false;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#canApplyOrderOffer(org.broadleafcommerce.core.offer.service.discount.domain.PromotableCandidateOrderOffer)
   */
  @Override public boolean canApplyOrderOffer(PromotableCandidateOrderOffer offer) {
    if (isTotalitarianOfferApplied()) {
      return false;
    }

    if (isNotCombinableOrderOfferApplied()) {
      return false;
    }

    if (!offer.isCombinable() || offer.isTotalitarian()) {
      // Only allow a combinable or totalitarian offer if this is the first adjustment.
      return candidateOrderOfferAdjustments.size() == 0;
    }

    return true;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#calculateSubtotalWithoutAdjustments()
   */
  @Override public Money calculateSubtotalWithoutAdjustments() {
    Money calculatedSubTotal = BroadleafCurrencyUtils.getMoney(order.getCurrency());

    for (PromotableOrderItem orderItem : getAllOrderItems()) {
      calculatedSubTotal = calculatedSubTotal.add(orderItem.calculateTotalWithoutAdjustments());
    }

    return calculatedSubTotal;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#calculateSubtotalWithAdjustments()
   */
  @Override public Money calculateSubtotalWithAdjustments() {
    Money calculatedSubTotal = BroadleafCurrencyUtils.getMoney(order.getCurrency());

    for (PromotableOrderItem orderItem : getAllOrderItems()) {
      calculatedSubTotal = calculatedSubTotal.add(orderItem.calculateTotalWithAdjustments());
    }

    return calculatedSubTotal;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrder#isIncludeOrderAndItemAdjustments()
   */
  @Override public boolean isIncludeOrderAndItemAdjustments() {
    return includeOrderAndItemAdjustments;
  }
} // end class PromotableOrderImpl
