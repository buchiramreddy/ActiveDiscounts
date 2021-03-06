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

import java.io.Serializable;

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
 * Defines an order in Broadleaf. There are several key items to be aware of with the BLC Order.
 *
 * <p>1. Carts are also Orders that are in a Pending status</p>
 *
 * <p>2. Wishlists (and similar) are "NamedOrders"</p>
 *
 * <p>3. Orders have several price related methods that are useful when displaying totals on the cart. 3a. getSubTotal()
 * : The total of all order items and their adjustments exclusive of taxes 3b. getOrderAdjustmentsValue() : The total of
 * all order adjustments 3c. getTotalTax() : The total taxes being charged for the order 3d. getTotal() : The order
 * total (equivalent of getSubTotal() - getOrderAdjustmentsValue() + getTotalTax())</p>
 *
 * <p>4. Order payments are represented with PaymentInfo objects.</p>
 *
 * <p>5. Order shipping (e.g. fulfillment) are represented with Fulfillment objects.</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface Order extends Serializable {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  /**
   * Gets the name of the order, mainly in order to support wishlists.
   *
   * @return  the name of the order
   */
  String getName();

  /**
   * Sets the name of the order in the context of a wishlist. In this fashion, a
   * {@link org.broadleafcommerce.profile.core.domain.Customer} can have multiple wishlists like "Christmas" or "Gaming
   * Computer" etc.
   *
   * @param  name  DOCUMENT ME!
   */
  void setName(String name);

  /**
   * Gets the auditable associated with this Order instance which tracks changes made to this Order (creation/update).
   *
   * @return  gets the auditable associated with this Order instance which tracks changes made to this Order
   *          (creation/update).
   */
  Auditable getAuditable();

  /**
   * DOCUMENT ME!
   *
   * @param  auditable  DOCUMENT ME!
   */
  void setAuditable(Auditable auditable);

  /**
   * Returns the subtotal price for the order. The subtotal price is the price of all order items with item offers
   * applied. The subtotal does not take into account the order promotions, shipping costs or any taxes that apply to
   * this order.
   *
   * @return  the total item price with offers applied
   */
  Money getSubTotal();

  /**
   * Sets the subtotal price for the order. The subtotal price is the price of all order items with item offers applied.
   * The subtotal does not take into account the order offers or any taxes that apply to this order.
   *
   * @param  subTotal  DOCUMENT ME!
   */
  void setSubTotal(Money subTotal);

  /**
   * Assigns a final price to all the order items.
   */
  void assignOrderItemsFinalPrice();

  /**
   * Returns the sum of the item totals.
   *
   * @return  the sum of the item totals.
   */
  Money calculateSubTotal();

  /**
   * The grand total of this {@link org.broadleafcommerce.core.order.domain.Order} which includes all shipping costs and
   * taxes, as well as any adjustments from promotions.
   *
   * @return  the grand total price of this {@link org.broadleafcommerce.core.order.domain.Order}
   */
  Money getTotal();

  /**
   * Used in {@link org.broadleafcommerce.core.pricing.service.workflow.TotalActivity} to set the grand total of this
   * {@link org.broadleafcommerce.core.order.domain.Order}. This includes the prices of all of the
   * {@link org.broadleafcommerce.core.order.domain.OrderItem}s as well as any taxes, fees, shipping and adjustments for
   * all 3.
   *
   * @param  orderTotal  the total cost of this {@link org.broadleafcommerce.core.order.domain.Order}
   */
  void setTotal(Money orderTotal);

  /**
   * Convenience method for determining how much is left on the Order based on the payments that have already been
   * applied. This takes {@link #getTotal()} and subtracts the sum of all the
   * {@link org.broadleafcommerce.core.payment.domain.PaymentInfo}s associated with this Order. Note that if an order
   * has been fully paid for, this method will return zero.
   *
   * @return  {@link #getTotal()} minus the {@link org.broadleafcommerce.core.payment.domain.PaymentInfo#getAmount()}
   *          for each {@link org.broadleafcommerce.core.payment.domain.PaymentInfo} on this Order
   */
  Money getRemainingTotal();

  /**
   * Convenience method for determining how much of the order total has been captured. This takes the
   * {@link org.broadleafcommerce.core.payment.domain.PaymentInfo}s and checks the
   * {@link org.broadleafcommerce.core.payment.domain.PaymentInfoDetailType} for captured records.
   *
   * @return  convenience method for determining how much of the order total has been captured.
   */
  Money getCapturedTotal();

  /**
   * Gets the {@link org.broadleafcommerce.profile.core.domain.Customer} for this
   * {@link org.broadleafcommerce.core.order.domain.Order}.
   *
   * @return  gets the {@link org.broadleafcommerce.profile.core.domain.Customer} for this
   *          {@link org.broadleafcommerce.core.order.domain.Order}.
   */
  Customer getCustomer();

  /**
   * Sets the associated {@link org.broadleafcommerce.profile.core.domain.Customer} for this Order.
   *
   * @param  customer  DOCUMENT ME!
   */
  void setCustomer(Customer customer);

  /**
   * Gets the status of the Order.
   *
   * @return  gets the status of the Order.
   */
  OrderStatus getStatus();

  /**
   * Sets the status of the Order.
   *
   * @param  status  DOCUMENT ME!
   */
  void setStatus(OrderStatus status);

  /**
   * Gets all the {@link org.broadleafcommerce.core.order.domain.OrderItem}s included in this
   * {@link org.broadleafcommerce.core.order.domain.Order}.
   *
   * @return  gets all the {@link org.broadleafcommerce.core.order.domain.OrderItem}s included in this
   *          {@link org.broadleafcommerce.core.order.domain.Order}.
   */
  List<OrderItem> getOrderItems();

  /**
   * DOCUMENT ME!
   *
   * @param  orderItems  DOCUMENT ME!
   */
  void setOrderItems(List<OrderItem> orderItems);

  /**
   * Adds an {@link org.broadleafcommerce.core.order.domain.OrderItem} to the list of
   * {@link org.broadleafcommerce.core.order.domain.OrderItem}s already associated with this
   * {@link org.broadleafcommerce.core.order.domain.Order}.
   *
   * @param  orderItem  the {@link org.broadleafcommerce.core.order.domain.OrderItem} to add to this
   *                    {@link org.broadleafcommerce.core.order.domain.Order}
   */
  void addOrderItem(OrderItem orderItem);

  /**
   * Gets the {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup}s associated with this
   * {@link org.broadleafcommerce.core.order.domain.Order}. An {@link org.broadleafcommerce.core.order.domain.Order} can
   * have many {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup}s associated with it in order to support
   * multi-address (and multi-type) shipping.
   *
   * @return  the {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup}s associated with this
   *          {@link org.broadleafcommerce.core.order.domain.Order}
   */
  List<FulfillmentGroup> getFulfillmentGroups();

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroups  DOCUMENT ME!
   */
  void setFulfillmentGroups(List<FulfillmentGroup> fulfillmentGroups);

  /**
   * Sets the {@link org.broadleafcommerce.core.offer.domain.Offer}s that could potentially apply to this
   * {@link org.broadleafcommerce.core.order.domain.Order}.
   *
   * @param  candidateOrderOffers  DOCUMENT ME!
   */
  void setCandidateOrderOffers(List<CandidateOrderOffer> candidateOrderOffers);

  /**
   * Gets the {@link org.broadleafcommerce.core.offer.domain.Offer}s that could potentially apply to this
   * {@link org.broadleafcommerce.core.order.domain.Order}. Used in the promotion engine.
   *
   * @return  gets the {@link org.broadleafcommerce.core.offer.domain.Offer}s that could potentially apply to this
   *          {@link org.broadleafcommerce.core.order.domain.Order}.
   */
  List<CandidateOrderOffer> getCandidateOrderOffers();

  /**
   * Gets the date that this {@link org.broadleafcommerce.core.order.domain.Order} was submitted. Note that if this date
   * is non-null, then the following should also be true:
   *
   * <ul>
   *   <li>{@link #getStatus()} should return
   *     {@link org.broadleafcommerce.core.order.service.type.OrderStatus#SUBMITTED}</li>
   *   <li>{@link #getOrderNumber()} should return a non-null value</li>
   * </ul>
   *
   * @return  gets the date that this {@link org.broadleafcommerce.core.order.domain.Order} was submitted.
   */
  Date getSubmitDate();

  /**
   * Set the date that this {@link org.broadleafcommerce.core.order.domain.Order} was submitted. Used in the
   * blCheckoutWorkflow as the last step after everything else has been completed (payments charged, integration systems
   * notified, etc).
   *
   * @param  submitDate  the date that this {@link org.broadleafcommerce.core.order.domain.Order} was submitted.
   */
  void setSubmitDate(Date submitDate);

  /**
   * Gets the total tax for this order, which is the sum of the taxes on all fulfillment groups. This total is
   * calculated in the TotalActivity stage of the pricing workflow.
   *
   * @return  the total tax for the order
   */
  Money getTotalTax();

  /**
   * Sets the total tax of this order, which is the sum of the taxes on all fulfillment groups. This total should only
   * be set during the TotalActivity stage of the pricing workflow.
   *
   * @param  totalTax  the total tax for this order
   */
  void setTotalTax(Money totalTax);

  /**
   *
   * DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @deprecated  - use {@link #getTotalFulfillmentCharges()} instead.
   */
  Money getTotalShipping();

  /**
   *
   * DOCUMENT ME!
   *
   * @deprecated  - Use {@link #setTotalFulfillmentCharges(org.broadleafcommerce.common.money.Money)} instead.
   *
   * @param       totalShipping  DOCUMENT ME!
   */
  void setTotalShipping(Money totalShipping);

  /**
   * Gets the total fulfillment costs that should be charged for this
   * {@link org.broadleafcommerce.core.order.domain.Order}. This value should be equivalent to the summation of
   * {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup#getTotal()} for each
   * {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup} associated with this
   * {@link org.broadleafcommerce.core.order.domain.Order}
   *
   * @return  the total fulfillment cost of this {@link org.broadleafcommerce.core.order.domain.Order}
   */
  Money getTotalFulfillmentCharges();

  /**
   * Set the total fulfillment cost of this {@link org.broadleafcommerce.core.order.domain.Order}. Used in the
   * {@link org.broadleafcommerce.core.pricing.service.workflow.FulfillmentGroupPricingActivity} after the cost of each
   * {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup} has been calculated.
   *
   * @param  totalFulfillmentCharges  totalShipping
   */
  void setTotalFulfillmentCharges(Money totalFulfillmentCharges);

  /**
   * Gets all the {@link org.broadleafcommerce.core.payment.domain.PaymentInfo}s associated with this
   * {@link org.broadleafcommerce.core.order.domain.Order}. An {@link org.broadleafcommerce.core.order.domain.Order} can
   * have many {@link org.broadleafcommerce.core.payment.domain.PaymentInfo}s associated with it to support things like
   * paying with multiple cards or perhaps paying some of this {@link org.broadleafcommerce.core.order.domain.Order}
   * with a gift card and some with a credit card.
   *
   * @return  the {@link org.broadleafcommerce.core.payment.domain.PaymentInfo}s associated with this
   *          {@link org.broadleafcommerce.core.order.domain.Order}.
   */
  List<PaymentInfo> getPaymentInfos();

  /**
   * Sets the various payment types associated with this {@link org.broadleafcommerce.core.order.domain.Order}.
   *
   * @param  paymentInfos  DOCUMENT ME!
   */
  void setPaymentInfos(List<PaymentInfo> paymentInfos);

  /**
   * Determines if this {@link org.broadleafcommerce.core.order.domain.Order} has an item in the given category.
   *
   * @param   categoryName  the {@link org.broadleafcommerce.core.catalog.domain.Category#getName} to check
   *
   * @return  <b>true</b> if at least one {@link org.broadleafcommerce.core.order.domain.OrderItem} is in the given
   *          category, <b>false</b> otherwise.
   *
   * @see     {@link org.broadleafcommerce.core.order.domain.OrderItem#isInCategory(String)}
   */
  boolean hasCategoryItem(String categoryName);

  /**
   * Returns a unmodifiable List of OrderAdjustment. To modify the List of OrderAdjustment, please use the
   * addOrderAdjustments or removeAllOrderAdjustments methods.
   *
   * @return  a unmodifiable List of OrderItemAdjustment
   */
  List<OrderAdjustment> getOrderAdjustments();

  /**
   * Returns all of the {@link org.broadleafcommerce.core.order.domain.OrderItem}s in this
   * {@link org.broadleafcommerce.core.order.domain.Order} that are an instanceof
   * {@link org.broadleafcommerce.core.order.domain.DiscreteOrderItem}. This will also go into each
   * {@link org.broadleafcommerce.core.order.domain.BundleOrderItem} (if there are any) and return all of the
   * {@link org.broadleafcommerce.core.order.domain.BundleOrderItem#getDiscreteOrderItems()} from each of those as well.
   *
   * @return  all of the {@link org.broadleafcommerce.core.order.domain.OrderItem}s in this
   *          {@link org.broadleafcommerce.core.order.domain.Order} that are an instanceof
   *          {@link org.broadleafcommerce.core.order.domain.DiscreteOrderItem}.
   */
  List<DiscreteOrderItem> getDiscreteOrderItems();

  /**
   * Checks the DiscreteOrderItems in the cart and returns whether or not the given SKU was found. The equality of the
   * SKUs is based on the .equals() method in SkuImpl. This includes checking the
   * {@link org.broadleafcommerce.core.order.domain.DiscreteOrderItem}s from {link
   * {@link org.broadleafcommerce.core.order.domain.BundleOrderItem#getDiscreteOrderItems()}
   *
   * @param   sku  The sku to check for
   *
   * @return  whether or not the given SKU exists in the cart
   */
  boolean containsSku(Sku sku);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<OfferCode> getAddedOfferCodes();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getFulfillmentStatus();

  /**
   * The unique number associated with this {@link org.broadleafcommerce.core.order.domain.Order}. Generally preferred
   * to use instead of just using {@link #getId()} since that exposes unwanted information about your database.
   *
   * @return  the unique order number for this {@link org.broadleafcommerce.core.order.domain.Order}
   */
  String getOrderNumber();

  /**
   * Set the unique order number for this {@link org.broadleafcommerce.core.order.domain.Order}.
   *
   * @param  orderNumber  DOCUMENT ME!
   */
  void setOrderNumber(String orderNumber);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getEmailAddress();

  /**
   * DOCUMENT ME!
   *
   * @param  emailAddress  DOCUMENT ME!
   */
  void setEmailAddress(String emailAddress);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<Offer, OfferInfo> getAdditionalOfferInformation();

  /**
   * DOCUMENT ME!
   *
   * @param  additionalOfferInformation  DOCUMENT ME!
   */
  void setAdditionalOfferInformation(Map<Offer, OfferInfo> additionalOfferInformation);

  /**
   * Returns the discount value of all the applied item offers for this order. This value is already deducted from the
   * order subTotal.
   *
   * @return  the discount value of all the applied item offers for this order
   */
  Money getItemAdjustmentsValue();

  /**
   * Returns the discount value of all the applied order offers. The value returned from this method should be
   * subtracted from the getSubTotal() to get the order price with all item and order offers applied.
   *
   * @return  the discount value of all applied order offers.
   */
  Money getOrderAdjustmentsValue();

  /**
   * Returns the total discount value for all applied item and order offers in the order. The return value should not be
   * used with getSubTotal() to calculate the final price, since getSubTotal() already takes into account the applied
   * item offers.
   *
   * @return  the total discount of all applied item and order offers
   */
  Money getTotalAdjustmentsValue();

  /**
   * Updates all of the prices of the {@link org.broadleafcommerce.core.order.domain.OrderItem}s in this
   * {@link org.broadleafcommerce.core.order.domain.Order}.
   *
   * @return  <b>true</b> if at least 1 {@link org.broadleafcommerce.core.order.domain.OrderItem} returned true from
   *          {@link org.broadleafcommerce.core.order.domain.OrderItem#updatePrices}, <b>false</b> otherwise.
   *
   * @see     {@link org.broadleafcommerce.core.order.domain.OrderItem#updatePrices()}
   */
  boolean updatePrices();

  /**
   * Updates the averagePriceField for all order items.
   *
   * @return  updates the averagePriceField for all order items.
   */
  boolean finalizeItemPrices();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getFulfillmentGroupAdjustmentsValue();

  /**
   * DOCUMENT ME!
   *
   * @param  addedOfferCode  DOCUMENT ME!
   */
  void addOfferCode(OfferCode addedOfferCode);

  /**
   * DOCUMENT ME!
   *
   * @param  offerCode  DOCUMENT ME!
   */
  @Deprecated void addAddedOfferCode(OfferCode offerCode);

  /**
   * A list of arbitrary attributes added to this order.
   *
   * @return  a list of arbitrary attributes added to this order.
   */
  Map<String, OrderAttribute> getOrderAttributes();

  /**
   * Sets the map of order attributes.
   *
   * @param  orderAttributes  DOCUMENT ME!
   */
  void setOrderAttributes(Map<String, OrderAttribute> orderAttributes);

  /**
   * This method returns the total number of items in this order. It iterates through all of the discrete order items
   * and sums up the quantity. This method is useful for display to the customer the current number of "physical" items
   * in the cart
   *
   * @return  the number of items in the order
   */
  int getItemCount();

  /**
   * The currency that the {@link org.broadleafcommerce.core.order.domain.Order} is priced in. Note that this is only on
   * {@link org.broadleafcommerce.core.order.domain.Order} since all of the other entities that are related (like
   * {@link org.broadleafcommerce.core.order.domain.FulfillmentGroup} and
   * {@link org.broadleafcommerce.core.order.domain.OrderItem} have a link back to here. This also has the side effect
   * that an {@link org.broadleafcommerce.core.order.domain.Order} can only be priced in a single currency.
   *
   * @return  the currency that the {@link org.broadleafcommerce.core.order.domain.Order} is priced in.
   */
  BroadleafCurrency getCurrency();

  /**
   * Set the currency that the {@link org.broadleafcommerce.core.order.domain.Order} is priced in.
   *
   * @param  currency  DOCUMENT ME!
   */
  void setCurrency(BroadleafCurrency currency);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Locale getLocale();

  /**
   * DOCUMENT ME!
   *
   * @param  locale  DOCUMENT ME!
   */
  void setLocale(Locale locale);

  /**
   * Returns true if this item has order adjustments.
   *
   * @return  true if this item has order adjustments.
   */
  boolean getHasOrderAdjustments();
} // end interface Order
