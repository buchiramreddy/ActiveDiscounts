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

package org.broadleafcommerce.core.order.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.broadleafcommerce.common.audit.Auditable;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.offer.domain.CandidateOrderOffer;
import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.broadleafcommerce.core.offer.domain.OfferInfo;
import org.broadleafcommerce.core.offer.domain.OrderAdjustment;
import org.broadleafcommerce.core.order.service.type.OrderStatus;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * NullOrderImpl is a class that represents an unmodifiable, empty order. This class is used as the default order for a
 * customer. It is a shared class between customers, and serves as a placeholder order until an item is initially added
 * to cart, at which point a real Order gets created. This prevents creating individual orders for customers that are
 * just browsing the site.
 *
 * @author   apazzolini
 * @version  $Revision$, $Date$
 */
public class NullOrderImpl implements Order {
  private static final long serialVersionUID = 1L;

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getId()
   */
  @Override public Long getId() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getName()
   */
  @Override public String getName() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getAuditable()
   */
  @Override public Auditable getAuditable() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setAuditable(org.broadleafcommerce.common.audit.Auditable)
   */
  @Override public void setAuditable(Auditable auditable) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getSubTotal()
   */
  @Override public Money getSubTotal() {
    return Money.ZERO;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setSubTotal(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setSubTotal(Money subTotal) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#assignOrderItemsFinalPrice()
   */
  @Override public void assignOrderItemsFinalPrice() {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getTotal()
   */
  @Override public Money getTotal() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getRemainingTotal()
   */
  @Override public Money getRemainingTotal() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getCapturedTotal()
   */
  @Override public Money getCapturedTotal() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setTotal(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotal(Money orderTotal) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getCustomer()
   */
  @Override public Customer getCustomer() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setCustomer(org.broadleafcommerce.profile.core.domain.Customer)
   */
  @Override public void setCustomer(Customer customer) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getStatus()
   */
  @Override public OrderStatus getStatus() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setStatus(org.broadleafcommerce.core.order.service.type.OrderStatus)
   */
  @Override public void setStatus(OrderStatus status) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getOrderItems()
   */
  @Override public List<OrderItem> getOrderItems() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setOrderItems(java.util.List)
   */
  @Override public void setOrderItems(List<OrderItem> orderItems) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#addOrderItem(org.broadleafcommerce.core.order.domain.OrderItem)
   */
  @Override public void addOrderItem(OrderItem orderItem) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getFulfillmentGroups()
   */
  @Override public List<FulfillmentGroup> getFulfillmentGroups() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setFulfillmentGroups(java.util.List)
   */
  @Override public void setFulfillmentGroups(List<FulfillmentGroup> fulfillmentGroups) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setCandidateOrderOffers(java.util.List)
   */
  @Override public void setCandidateOrderOffers(List<CandidateOrderOffer> candidateOrderOffers) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getCandidateOrderOffers()
   */
  @Override public List<CandidateOrderOffer> getCandidateOrderOffers() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getSubmitDate()
   */
  @Override public Date getSubmitDate() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setSubmitDate(java.util.Date)
   */
  @Override public void setSubmitDate(Date submitDate) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getTotalTax()
   */
  @Override public Money getTotalTax() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setTotalTax(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalTax(Money totalTax) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getTotalShipping()
   */
  @Override public Money getTotalShipping() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setTotalShipping(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalShipping(Money totalShipping) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getPaymentInfos()
   */
  @Override public List<PaymentInfo> getPaymentInfos() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setPaymentInfos(java.util.List)
   */
  @Override public void setPaymentInfos(List<PaymentInfo> paymentInfos) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#hasCategoryItem(java.lang.String)
   */
  @Override public boolean hasCategoryItem(String categoryName) {
    return false;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getOrderAdjustments()
   */
  @Override public List<OrderAdjustment> getOrderAdjustments() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getDiscreteOrderItems()
   */
  @Override public List<DiscreteOrderItem> getDiscreteOrderItems() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#containsSku(org.broadleafcommerce.core.catalog.domain.Sku)
   */
  @Override public boolean containsSku(Sku sku) {
    return false;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getAddedOfferCodes()
   */
  @Override public List<OfferCode> getAddedOfferCodes() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getFulfillmentStatus()
   */
  @Override public String getFulfillmentStatus() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getOrderNumber()
   */
  @Override public String getOrderNumber() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setOrderNumber(java.lang.String)
   */
  @Override public void setOrderNumber(String orderNumber) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getEmailAddress()
   */
  @Override public String getEmailAddress() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setEmailAddress(java.lang.String)
   */
  @Override public void setEmailAddress(String emailAddress) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getAdditionalOfferInformation()
   */
  @Override public Map<Offer, OfferInfo> getAdditionalOfferInformation() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setAdditionalOfferInformation(java.util.Map)
   */
  @Override public void setAdditionalOfferInformation(Map<Offer, OfferInfo> additionalOfferInformation) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getItemAdjustmentsValue()
   */
  @Override public Money getItemAdjustmentsValue() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getOrderAdjustmentsValue()
   */
  @Override public Money getOrderAdjustmentsValue() {
    return Money.ZERO;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getTotalAdjustmentsValue()
   */
  @Override public Money getTotalAdjustmentsValue() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#updatePrices()
   */
  @Override public boolean updatePrices() {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getFulfillmentGroupAdjustmentsValue()
   */
  @Override public Money getFulfillmentGroupAdjustmentsValue() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#addOfferCode(org.broadleafcommerce.core.offer.domain.OfferCode)
   */
  @Override public void addOfferCode(OfferCode addedOfferCode) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#addAddedOfferCode(org.broadleafcommerce.core.offer.domain.OfferCode)
   */
  @Override public void addAddedOfferCode(OfferCode offerCode) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getOrderAttributes()
   */
  @Override public Map<String, OrderAttribute> getOrderAttributes() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setOrderAttributes(java.util.Map)
   */
  @Override public void setOrderAttributes(Map<String, OrderAttribute> orderAttributes) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getItemCount()
   */
  @Override public int getItemCount() {
    return 0;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getCurrency()
   */
  @Override public BroadleafCurrency getCurrency() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setCurrency(org.broadleafcommerce.common.currency.domain.BroadleafCurrency)
   */
  @Override public void setCurrency(BroadleafCurrency currency) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getLocale()
   */
  @Override public Locale getLocale() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setLocale(org.broadleafcommerce.common.locale.domain.Locale)
   */
  @Override public void setLocale(Locale locale) { }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#calculateSubTotal()
   */
  @Override public Money calculateSubTotal() {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getTotalFulfillmentCharges()
   */
  @Override public Money getTotalFulfillmentCharges() {
    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#setTotalFulfillmentCharges(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTotalFulfillmentCharges(Money totalFulfillmentCharges) {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#finalizeItemPrices()
   */
  @Override public boolean finalizeItemPrices() {
    throw new UnsupportedOperationException("NullOrder does not support any modification operations.");
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.Order#getHasOrderAdjustments()
   */
  @Override public boolean getHasOrderAdjustments() {
    return false;
  }

} // end class NullOrderImpl
