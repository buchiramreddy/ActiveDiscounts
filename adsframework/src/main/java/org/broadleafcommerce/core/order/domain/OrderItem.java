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

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.offer.domain.CandidateItemOffer;
import org.broadleafcommerce.core.offer.domain.OrderItemAdjustment;
import org.broadleafcommerce.core.order.service.type.OrderItemType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface OrderItem extends Serializable, Cloneable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns a boolean indicating whether this sku is active. This is used to determine whether a user the sku can add
   * the sku to their cart.
   *
   * @return  a boolean indicating whether this sku is active.
   */
  boolean isSkuActive();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Used by the promotion engine to add offers that might apply to this orderItem.
   *
   * @param  candidateItemOffer  DOCUMENT ME!
   */
  void addCandidateItemOffer(CandidateItemOffer candidateItemOffer);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Used to set the final price of the item and corresponding details.
   */
  void assignFinalPrice();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderItem clone();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Called by the pricing engine after prices have been computed. Allows the system to set the averagePrice that is
   * stored for the item.
   */
  void finalizePrice();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Delegates to {@link #getAverageAdjustmentValue()} instead but this method is of little or no value in
   *              most practical applications unless only simple promotions are being used.
   *
   * @return      DOCUMENT ME!
   */
  Money getAdjustmentValue();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the average unit item adjustments.
   *
   * <p>For example, if this item has a quantity of 2 at a base-price of $10 and a 10% discount applies to both then
   * this method would return $1.</p>
   *
   * <p>Some implementations may choose to show this on a view cart screen. Due to fractional discounts, the display
   * could represent a unit adjustment value that is off due to rounding. See {@link #getAveragePrice()} for an example
   * of this.</p>
   *
   * <p>Implementations wishing to show unit prices may choose instead to show the individual OrderItemPriceDetails
   * instead of this value to avoid the rounding problem. This would result in multiple cart item display rows for each
   * OrderItem.</p>
   *
   * <p>Alternatively, the cart display should use {@link #getTotalAdjustmentValue()}.</p>
   *
   * @return  the average unit item adjustments.
   */
  Money getAverageAdjustmentValue();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the average unit display price for the item.
   *
   * <p>Some implementations may choose to show this on a view cart screen. Due to fractional discounts, it the display
   * could represent a unit price that is off.</p>
   *
   * <p>For example, if this OrderItem had 3 items at $1 each and also received a $1 discount. The net effect under the
   * default rounding scenario would be an average price of $0.666666</p>
   *
   * <p>Most systems would represent this as $0.67 as the discounted unit price; however, this amount times 3 ($2.01)
   * would not equal the getTotalPrice() which would be $2.00. For this reason, it is not recommended that
   * implementations utilize this field. Instead, they may choose not to show the unit price or show multiple prices by
   * looping through the OrderItemPriceDetails.</p>
   *
   * @return  the average unit display price for the item.
   */
  Money getAveragePrice();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<CandidateItemOffer> getCandidateItemOffers();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Category getCategory();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  GiftWrapOrderItem getGiftWrapOrderItem();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The unique identifier of this OrderItem.
   *
   * @return  the unique identifier of this OrderItem.
   */
  Long getId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns true if this item received a discount.
   *
   * @return  true if this item received a discount.
   */
  boolean getIsDiscounted();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Default implementation uses {@link #getSalePrice()} &lt; {@link #getRetailPrice()}.
   *
   * @return  default implementation uses {@link #getSalePrice()} &lt; {@link #getRetailPrice()}.
   */
  boolean getIsOnSale();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Generally copied from the Sku.getName().
   *
   * @return  generally copied from the Sku.getName().
   */
  String getName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Reference back to the containing order.
   *
   * @return  reference back to the containing order.
   */
  Order getOrder();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns item level adjustment for versions of Broadleaf Commerce prior to 2.3.0 which replaced this concept with
   * OrderItemPriceDetail adjustments.
   *
   * @return  a List of OrderItemAdjustment
   */
  List<OrderItemAdjustment> getOrderItemAdjustments();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * A list of arbitrary attributes added to this item.
   *
   * @return  a list of arbitrary attributes added to this item.
   */
  Map<String, OrderItemAttribute> getOrderItemAttributes();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Collection of priceDetails for this orderItem.
   *
   * <p>Without discounts, an orderItem would have exactly 1 ItemPriceDetail. When orderItem discounting or
   * tax-calculations result in an orderItem having multiple prices like in a buy-one-get-one free example, the
   * orderItem will get an additional ItemPriceDetail.</p>
   *
   * <p>Generally, an OrderItem will have 1 ItemPriceDetail record for each uniquely priced version of the item.</p>
   *
   * @return  collection of priceDetails for this orderItem.
   */
  List<OrderItemPriceDetail> getOrderItemPriceDetails();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * If any quantity of this item was used to qualify for an offer, then this returned list will indicate the offer and
   * the relevant quantity.
   *
   * <p>As an example, a BuyOneGetOneFree offer would have 1 qualifier and 1 adjustment.</p>
   *
   * @return  a List of OrderItemAdjustment
   */
  List<OrderItemQualifier> getOrderItemQualifiers();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderItemType getOrderItemType();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PersonalMessage getPersonalMessage();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Delegates to {@link #getAveragePrice()}
   *
   * @return      DOCUMENT ME!
   */
  Money getPrice();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the unit price of this item. If the parameter allowSalesPrice is true, will return the sale price if one
   * exists.
   *
   * @param   allowSalesPrice  DOCUMENT ME!
   *
   * @return  the unit price of this item.
   */
  Money getPriceBeforeAdjustments(boolean allowSalesPrice);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The quantity of this {@link org.broadleafcommerce.core.order.domain.OrderItem}.
   *
   * @return  the quantity of this {@link org.broadleafcommerce.core.order.domain.OrderItem}.
   */
  int getQuantity();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The retail price of the item that was added to the {@link org.broadleafcommerce.core.order.domain.Order} at the
   * time that this was added. This is preferable to use as opposed to checking the price of the item that was added
   * from the catalog domain (like in {@link org.broadleafcommerce.core.order.domain.DiscreteOrderItem}, using
   * {@link org.broadleafcommerce.core.order.domain.DiscreteOrderItem#getSku()}'s retail price) since the price in the
   * catalog domain could have changed since the item was added to the
   * {@link org.broadleafcommerce.core.order.domain.Order}.
   *
   * @return  the retail price of the item that was added to the {@link org.broadleafcommerce.core.order.domain.Order}
   *          at the time that this was added.
   */
  Money getRetailPrice();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the salePrice for this item. Note this method will return the lower of the retailPrice or salePrice. It
   * will return the retailPrice instead of null.
   *
   * <p>For SKU based pricing, a call to {@link #updateSaleAndRetailPrices()} will ensure that the retailPrice being
   * used is current.</p>
   *
   * @return  the salePrice for this item.
   */
  Money getSalePrice();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @deprecated  If the item is taxable, returns {@link #getAveragePrice()}
   *
   *              <p>It is recommended instead that tax calculation engines use the {@link #getTotalTaxableAmount()}
   *              which provides the taxable total for all quantities of this item. This method suffers from penny
   *              rounding errors in some situations.</p>
   *
   * @return      DOCUMENT ME!
   */
  Money getTaxablePrice();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the total for all item level adjustments.
   *
   * <p>For example, if the item has a 2 items priced at $10 a piece and a 10% discount applies to both quantities. This
   * method would return $2.</p>
   *
   * @return  the total for all item level adjustments.
   */
  Money getTotalAdjustmentValue();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the total price to be paid for this order item including item-level adjustments.
   *
   * <p>It does not include the effect of order level adjustments. Calculated by looping through the
   * orderItemPriceDetails</p>
   *
   * @return  the total price to be paid for this order item including item-level adjustments.
   */
  Money getTotalPrice();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the total price to be paid before adjustments.
   *
   * @param   allowSalesPrice  DOCUMENT ME!
   *
   * @return  the total price to be paid before adjustments.
   */
  Money getTotalPriceBeforeAdjustments(boolean allowSalesPrice);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * If true, this item can be discounted..
   *
   * @return  if true, this item can be discounted..
   */
  boolean isDiscountingAllowed();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   categoryName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isInCategory(String categoryName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns true if the retail price was manually set. If the retail price is manually set, calls to updatePrices()
   * will not do anything.
   *
   * @return  true if the retail price was manually set.
   */
  boolean isRetailPriceOverride();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns true if the sale price was manually set. If the retail price is manually set, calls to updatePrices() will
   * not do anything.
   *
   * @return  true if the sale price was manually set.
   */
  boolean isSalePriceOverride();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns whether or not this item is taxable. If this flag is not set, it returns true by default
   *
   * @return  the taxable flag. If null, returns true
   */
  Boolean isTaxable();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Removes all adjustment for this order item and reset the adjustment price.
   *
   * @return  removes all adjustment for this order item and reset the adjustment price.
   */
  int removeAllAdjustments();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Removes all candidate offers. Used by the promotion engine which subsequently adds the candidate offers that might
   * apply back to this item.
   */
  void removeAllCandidateItemOffers();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  candidateItemOffers  DOCUMENT ME!
   */
  void setCandidateItemOffers(List<CandidateItemOffer> candidateItemOffers);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  category  DOCUMENT ME!
   */
  void setCategory(Category category);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Turns off discount processing for this line item.
   *
   * @param  discountingAllowed  disableDiscounts
   */
  void setDiscountingAllowed(boolean discountingAllowed);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  giftWrapOrderItem  DOCUMENT ME!
   */
  void setGiftWrapOrderItem(GiftWrapOrderItem giftWrapOrderItem);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the unique id of the OrderItem. Typically left null for new items and Broadleaf will set using the next
   * sequence number.
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the name of this order item.
   *
   * @param  name  DOCUMENT ME!
   */
  void setName(String name);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the order for this orderItem.
   *
   * @param  order  DOCUMENT ME!
   */
  void setOrder(Order order);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param       orderItemAdjustments  DOCUMENT ME!
   *
   * @deprecated  Item level adjustments are now stored at the OrderItemPriceDetail level instead to prevent unnecessary
   *              item splitting of OrderItems when evaluating promotions in the pricing engine.
   */
  void setOrderItemAdjustments(List<OrderItemAdjustment> orderItemAdjustments);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the map of order item attributes.
   *
   * @param  orderItemAttributes  DOCUMENT ME!
   */
  void setOrderItemAttributes(Map<String, OrderItemAttribute> orderItemAttributes);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the list of orderItem price details.
   *
   * @see    {@link #getOrderItemPriceDetails()}
   *
   * @param  orderItemPriceDetails  DOCUMENT ME!
   */
  void setOrderItemPriceDetails(List<OrderItemPriceDetail> orderItemPriceDetails);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the list of OrderItemQualifiers.
   *
   * @param  orderItemQualifiers  DOCUMENT ME!
   */
  void setOrderItemQualifiers(List<OrderItemQualifier> orderItemQualifiers);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  orderItemType  DOCUMENT ME!
   */
  void setOrderItemType(OrderItemType orderItemType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  personalMessage  DOCUMENT ME!
   */
  void setPersonalMessage(PersonalMessage personalMessage);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @deprecated  Calling this method is the same as calling the following:
   *              {@link #setRetailPrice(org.broadleafcommerce.common.money.Money)}
   *              {@link #setSalePrice(org.broadleafcommerce.common.money.Money)} {@link #setRetailPriceOverride(true)}
   *              {@link #setSalePriceOverride(true)} {@link #setDiscountingAllowed(false)}
   *
   *              <p>This has the effect of setting the price in a way that no discounts or adjustments can be made.</p>
   *
   * @param       price  DOCUMENT ME!
   */
  void setPrice(Money price);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the quantity of this item.
   *
   * @param  quantity  DOCUMENT ME!
   */
  void setQuantity(int quantity);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Calling this method will manually set the retailPrice. To avoid the pricing engine resetting this price, you should
   * also make a call to {@link #setRetailPriceOverride(true)}
   *
   * <p>Consider also calling {@link #setDiscountingAllowed(boolean)} with a value of false to restrict discounts after
   * manually setting the retail price.</p>
   *
   * @param  retailPrice  DOCUMENT ME!
   */
  void setRetailPrice(Money retailPrice);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Indicates that the retail price was manually set. A typical usage might be for a CSR who has override privileges to
   * control setting the price. This will automatically be set by calling
   * {@link #setRetailPrice(org.broadleafcommerce.common.money.Money)}
   *
   * @param  override  DOCUMENT ME!
   */
  void setRetailPriceOverride(boolean override);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Calling this method will manually set the salePrice. It will also make a call to
   * {@link #setSalePriceSetManually(true)}
   *
   * <p>To avoid the pricing engine resetting this price, you should also make a call to
   * {@link #setSalePriceOverride(true)}</p>
   *
   * <p>Typically for {@link org.broadleafcommerce.core.order.domain.DiscreteOrderItem}s, the prices will be set with a
   * call to {@link #updateSaleAndRetailPrices()} which will use the Broadleaf dynamic pricing engine or the values
   * directly tied to the SKU.</p>
   *
   * @param  salePrice  DOCUMENT ME!
   */
  void setSalePrice(Money salePrice);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Indicates that the sale price was manually set. A typical usage might be for a CSR who has override privileges to
   * control setting the price.
   *
   * <p>Consider also calling {@link #setDiscountingAllowed(boolean)} with a value of false to restrict discounts after
   * manually setting the retail price.</p>
   *
   * <p>If the salePrice is not lower than the retailPrice, the system will return the retailPrice when a call to
   * {@link #getSalePrice()} is made.</p>
   *
   * @param  override  DOCUMENT ME!
   */
  void setSalePriceOverride(boolean override);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets whether or not this item is taxable. Generally, this has been copied from the setting of the relevant SKU at
   * the time it was added.
   *
   * @param  taxable  DOCUMENT ME!
   */
  void setTaxable(Boolean taxable);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Used to reset the base price of the item that the pricing engine uses.
   *
   * <p>Generally, this will update the retailPrice and salePrice based on the corresponding value in the SKU.</p>
   *
   * <p>If the retail or sale price was manually set, this method will not change those prices.</p>
   *
   * <p>For non-manually set prices, prices can change based on system activities such as locale changes and customer
   * authentication, this method is used to ensure that all cart items reflect the current base price before executing
   * other pricing / adjustment operations.</p>
   *
   * <p>Other known scenarios that can effect the base prices include the automatic bundling or loading a stale cart
   * from the database.</p>
   *
   * <p>See notes in subclasses for specific behavior of this method.</p>
   *
   * @return  true if the base prices changed as a result of this call
   */
  boolean updateSaleAndRetailPrices();

} // end interface OrderItem
