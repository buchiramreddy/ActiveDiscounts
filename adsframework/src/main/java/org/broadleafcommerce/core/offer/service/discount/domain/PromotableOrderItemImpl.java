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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.OrderItemPriceDetailAdjustment;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.domain.OrderItemContainer;
import org.broadleafcommerce.core.order.domain.OrderItemPriceDetail;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PromotableOrderItemImpl implements PromotableOrderItem {
  private static final Log LOG = LogFactory.getLog(PromotableOrderItem.class);

  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  protected PromotableOrder                      promotableOrder;

  /** DOCUMENT ME! */
  protected OrderItem                            orderItem;

  /** DOCUMENT ME! */
  protected PromotableItemFactory                itemFactory;

  /** DOCUMENT ME! */
  protected List<PromotableOrderItemPriceDetail> itemPriceDetails   = new ArrayList<PromotableOrderItemPriceDetail>();

  /** DOCUMENT ME! */
  protected boolean                              includeAdjustments;

  /**
   * Creates a new PromotableOrderItemImpl object.
   *
   * @param  orderItem           DOCUMENT ME!
   * @param  promotableOrder     DOCUMENT ME!
   * @param  itemFactory         DOCUMENT ME!
   * @param  includeAdjustments  DOCUMENT ME!
   */
  public PromotableOrderItemImpl(OrderItem orderItem, PromotableOrder promotableOrder,
    PromotableItemFactory itemFactory,
    boolean includeAdjustments) {
    this.orderItem          = orderItem;
    this.promotableOrder    = promotableOrder;
    this.itemFactory        = itemFactory;
    this.includeAdjustments = includeAdjustments;
    initializePriceDetails();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#resetPriceDetails()
   */
  @Override public void resetPriceDetails() {
    itemPriceDetails.clear();
    initializePriceDetails();
  }

  private void initializePriceDetails() {
    if (includeAdjustments) {
      for (OrderItemPriceDetail detail : orderItem.getOrderItemPriceDetails()) {
        PromotableOrderItemPriceDetail poid = itemFactory.createPromotableOrderItemPriceDetail(this,
            detail.getQuantity());
        itemPriceDetails.add(poid);
        poid.chooseSaleOrRetailAdjustments();

        for (OrderItemPriceDetailAdjustment adjustment : detail.getOrderItemPriceDetailAdjustments()) {
          PromotableOrderItemPriceDetailAdjustment poidAdj = new PromotableOrderItemPriceDetailAdjustmentImpl(
              adjustment, poid);
          poid.addCandidateItemPriceDetailAdjustment(poidAdj);
        }
      }
    } else {
      PromotableOrderItemPriceDetail poid = itemFactory.createPromotableOrderItemPriceDetail(this,
          orderItem.getQuantity());
      itemPriceDetails.add(poid);
    }
  }

  /**
   * Adds the item to the rule variables map.
   *
   * @param  ruleVars  DOCUMENT ME!
   */
  @Override public void updateRuleVariables(Map<String, Object> ruleVars) {
    ruleVars.put("orderItem", orderItem);
    ruleVars.put("discreteOrderItem", orderItem);
    ruleVars.put("bundleOrderItem", orderItem);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#isDiscountingAllowed()
   */
  @Override public boolean isDiscountingAllowed() {
    return orderItem.isDiscountingAllowed();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#isOrderItemContainer()
   */
  @Override public boolean isOrderItemContainer() {
    return orderItem instanceof OrderItemContainer;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#getOrderItemContainer()
   */
  @Override public OrderItemContainer getOrderItemContainer() {
    if (orderItem instanceof OrderItemContainer) {
      return (OrderItemContainer) orderItem;
    }

    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#getPromotableOrderItemPriceDetails()
   */
  @Override public List<PromotableOrderItemPriceDetail> getPromotableOrderItemPriceDetails() {
    return itemPriceDetails;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#getSalePriceBeforeAdjustments()
   */
  @Override public Money getSalePriceBeforeAdjustments() {
    return orderItem.getSalePrice();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#getRetailPriceBeforeAdjustments()
   */
  @Override public Money getRetailPriceBeforeAdjustments() {
    return orderItem.getRetailPrice();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#getPriceBeforeAdjustments(boolean)
   */
  @Override public Money getPriceBeforeAdjustments(boolean applyToSalePrice) {
    if (applyToSalePrice && (getSalePriceBeforeAdjustments() != null)) {
      return getSalePriceBeforeAdjustments();
    }

    return getRetailPriceBeforeAdjustments();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#getCurrentBasePrice()
   */
  @Override public Money getCurrentBasePrice() {
    if (orderItem.getIsOnSale()) {
      return orderItem.getSalePrice();
    } else {
      return orderItem.getRetailPrice();
    }
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#getQuantity()
   */
  @Override public int getQuantity() {
    return orderItem.getQuantity();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#isOnSale()
   */
  @Override public boolean isOnSale() {
    return orderItem.getIsOnSale();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#getCurrency()
   */
  @Override public BroadleafCurrency getCurrency() {
    return orderItem.getOrder().getCurrency();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#removeAllItemAdjustments()
   */
  @Override public void removeAllItemAdjustments() {
    Iterator<PromotableOrderItemPriceDetail> detailIterator = itemPriceDetails.iterator();

    boolean first = true;

    while (detailIterator.hasNext()) {
      PromotableOrderItemPriceDetail detail = detailIterator.next();

      if (first) {
        detail.setQuantity(getQuantity());
        detail.getPromotionDiscounts().clear();
        detail.getPromotionQualifiers().clear();
        detail.removeAllAdjustments();
        first = false;
      } else {
        // Get rid of all other details
        detailIterator.remove();
      }
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param  firstDetail   DOCUMENT ME!
   * @param  secondDetail  DOCUMENT ME!
   */
  protected void mergeDetails(PromotableOrderItemPriceDetail firstDetail, PromotableOrderItemPriceDetail secondDetail) {
    int firstQty  = firstDetail.getQuantity();
    int secondQty = secondDetail.getQuantity();

    if (LOG.isDebugEnabled()) {
      LOG.trace("Merging priceDetails with quantities " + firstQty + " and " + secondQty);
    }

    firstDetail.setQuantity(firstQty + secondQty);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#mergeLikeDetails()
   */
  @Override public void mergeLikeDetails() {
    if (itemPriceDetails.size() > 1) {
      Iterator<PromotableOrderItemPriceDetail>    detailIterator = itemPriceDetails.iterator();
      Map<String, PromotableOrderItemPriceDetail> detailMap      =
        new HashMap<String, PromotableOrderItemPriceDetail>();

      while (detailIterator.hasNext()) {
        PromotableOrderItemPriceDetail currentDetail = detailIterator.next();
        String                         detailKey     = currentDetail.buildDetailKey();

        if (detailMap.containsKey(detailKey)) {
          PromotableOrderItemPriceDetail firstDetail = detailMap.get(detailKey);
          mergeDetails(firstDetail, currentDetail);
          detailIterator.remove();
        } else {
          detailMap.put(detailKey, currentDetail);
        }
      }
    }
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#getOrderItemId()
   */
  @Override public Long getOrderItemId() {
    return orderItem.getId();
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#calculateTotalWithAdjustments()
   */
  @Override public Money calculateTotalWithAdjustments() {
    Money returnTotal = new Money(getCurrency());

    for (PromotableOrderItemPriceDetail detail : itemPriceDetails) {
      returnTotal = returnTotal.add(detail.getFinalizedTotalWithAdjustments());
    }

    return returnTotal;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#calculateTotalWithoutAdjustments()
   */
  @Override public Money calculateTotalWithoutAdjustments() {
    return getCurrentBasePrice().multiply(orderItem.getQuantity());
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#calculateTotalAdjustmentValue()
   */
  @Override public Money calculateTotalAdjustmentValue() {
    Money returnTotal = new Money(getCurrency());

    for (PromotableOrderItemPriceDetail detail : itemPriceDetails) {
      returnTotal = returnTotal.add(detail.calculateTotalAdjustmentValue());
    }

    return returnTotal;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#createNewDetail(int)
   */
  @Override public PromotableOrderItemPriceDetail createNewDetail(int quantity) {
    if (includeAdjustments) {
      throw new RuntimeException("Trying to createNewDetail when adjustments have already been included.");
    }

    return itemFactory.createPromotableOrderItemPriceDetail(this, quantity);
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotableOrderItem#getOrderItem()
   */
  @Override public OrderItem getOrderItem() {
    return orderItem;
  }
} // end class PromotableOrderItemImpl
