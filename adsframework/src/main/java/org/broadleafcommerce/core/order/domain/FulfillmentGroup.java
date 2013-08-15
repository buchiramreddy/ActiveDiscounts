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

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.CandidateFulfillmentGroupOffer;
import org.broadleafcommerce.core.offer.domain.FulfillmentGroupAdjustment;
import org.broadleafcommerce.core.order.service.type.FulfillmentGroupStatusType;
import org.broadleafcommerce.core.order.service.type.FulfillmentType;

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.Phone;


/**
 * This is the main entity used to hold fulfillment information about an Order. An Order can have multiple
 * FulfillmentGroups to support shipping items to multiple addresses along with fulfilling items multiple ways (ship
 * some overnight, deliver some with digital download). This constraint means that a FulfillmentGroup is unique based on
 * an Address and FulfillmentOption combination. This also means that in the common case for Orders that are being
 * delivered to a single Address and a single way (shipping everything express; ie a single FulfillmentOption) then
 * there will be only 1 FulfillmentGroup for that Order.
 *
 * @author   Phillip Verheyden
 * @see      {@link org.broadleafcommerce.core.order.domain.Order},
 *           {@link org.broadleafcommerce.core.order.domain.FulfillmentOption},
 *           {@link org.broadleafcommerce.profile.core.domain.Address},
 *           {@link org.broadleafcommerce.core.order.domain.FulfillmentGroupItem}
 * @version  $Revision$, $Date$
 */
public interface FulfillmentGroup extends Serializable {
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
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Order getOrder();

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  void setOrder(Order order);

  /**
   * DOCUMENT ME!
   *
   * @param  sequence  DOCUMENT ME!
   */
  void setSequence(Integer sequence);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Integer getSequence();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentOption getFulfillmentOption();

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentOption  DOCUMENT ME!
   */
  void setFulfillmentOption(FulfillmentOption fulfillmentOption);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Address getAddress();

  /**
   * DOCUMENT ME!
   *
   * @param  address  DOCUMENT ME!
   */
  void setAddress(Address address);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Phone getPhone();

  /**
   * DOCUMENT ME!
   *
   * @param  phone  DOCUMENT ME!
   */
  void setPhone(Phone phone);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<FulfillmentGroupItem> getFulfillmentGroupItems();

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroupItems  DOCUMENT ME!
   */
  void setFulfillmentGroupItems(List<FulfillmentGroupItem> fulfillmentGroupItems);

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroupItem  DOCUMENT ME!
   */
  void addFulfillmentGroupItem(FulfillmentGroupItem fulfillmentGroupItem);

  /**
   *
   * DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @deprecated  Should use {@link #getFulfillmentOption()} instead
   * @see         {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}
   */
  @Deprecated String getMethod();

  /**
   *
   * DOCUMENT ME!
   *
   * @param       fulfillmentMethod  DOCUMENT ME!
   *
   * @deprecated  Should use {@link #setFulfillmentOption()} instead
   * @see         {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}
   */
  @Deprecated void setMethod(String fulfillmentMethod);

  /**
   * Returns the retail price for this fulfillmentGroup. The retail and sale concepts used for item pricing are not
   * generally used with fulfillmentPricing but supported nonetheless. Typically only a retail price would be set on a
   * fulfillment group.
   *
   * @return  the retail price for this fulfillmentGroup.
   */
  Money getRetailFulfillmentPrice();

  /**
   * Sets the retail price for this fulfillmentGroup.
   *
   * @param  fulfillmentPrice  DOCUMENT ME!
   */
  void setRetailFulfillmentPrice(Money fulfillmentPrice);

  /**
   * Returns the sale price for this fulfillmentGroup. Typically this will be null or equal to the
   * retailFulfillmentPrice
   *
   * @return  the sale price for this fulfillmentGroup.
   */
  Money getSaleFulfillmentPrice();

  /**
   * Sets the sale price for this fulfillmentGroup. Typically not used.
   *
   * @see    #setRetailFulfillmentPrice(org.broadleafcommerce.common.money.Money)
   *
   * @param  fulfillmentPrice  DOCUMENT ME!
   */
  void setSaleFulfillmentPrice(Money fulfillmentPrice);

  /**
   * Gets the price to charge for this fulfillmentGroup. Includes the effects of any adjustments such as those that
   * might have been applied by the promotion engine (e.g. free shipping)
   *
   * @return  gets the price to charge for this fulfillmentGroup.
   */
  Money getFulfillmentPrice();

  /**
   * Sets the price to charge for this fulfillmentGroup. Typically set internally by the Broadleaf pricing and promotion
   * engines.
   *
   * @param  fulfillmentPrice  DOCUMENT ME!
   */
  void setFulfillmentPrice(Money fulfillmentPrice);

  /**
   * DOCUMENT ME!
   *
   * @deprecated  - use {@link #getRetailFulfillmentPrice()} instead. Deprecated as the price might be for other
   *              fulfillment types such as PickUpAtStore fees or download fees.
   *
   * @return      DOCUMENT ME!
   */
  Money getRetailShippingPrice();

  /**
   * DOCUMENT ME!
   *
   * @param       retailShippingPrice  DOCUMENT ME!
   *
   * @deprecated  - use {@link #setRetailFulfillmentPrice(org.broadleafcommerce.common.money.Money)} instead.
   */
  void setRetailShippingPrice(Money retailShippingPrice);

  /**
   * DOCUMENT ME!
   *
   * @deprecated  - use {@link #getSaleFulfillmentPrice()} instead.
   *
   * @return      DOCUMENT ME!
   */
  Money getSaleShippingPrice();

  /**
   *
   * DOCUMENT ME!
   *
   * @deprecated  - use {@link #setSaleFulfillmentPrice(org.broadleafcommerce.common.money.Money)} instead.
   *
   * @param       saleShippingPrice  DOCUMENT ME!
   */
  void setSaleShippingPrice(Money saleShippingPrice);

  /**
   * DOCUMENT ME!
   *
   * @deprecated  - use {@link #getFulfillmentPrice()} instead.
   *
   * @return      DOCUMENT ME!
   */
  Money getShippingPrice();

  /**
   *
   * DOCUMENT ME!
   *
   * @deprecated  - use {@link #setRetailFulfillmentPrice(org.broadleafcommerce.common.money.Money)} instead.
   *
   * @param       shippingPrice  DOCUMENT ME!
   */
  void setShippingPrice(Money shippingPrice);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getReferenceNumber();

  /**
   * DOCUMENT ME!
   *
   * @param  referenceNumber  DOCUMENT ME!
   */
  void setReferenceNumber(String referenceNumber);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentType getType();

  /**
   * DOCUMENT ME!
   *
   * @param  type  DOCUMENT ME!
   */
  void setType(FulfillmentType type);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<CandidateFulfillmentGroupOffer> getCandidateFulfillmentGroupOffers();

  /**
   * DOCUMENT ME!
   *
   * @param  candidateOffers  DOCUMENT ME!
   */
  void setCandidateFulfillmentGroupOffer(List<CandidateFulfillmentGroupOffer> candidateOffers);

  /**
   * DOCUMENT ME!
   *
   * @param  candidateOffer  DOCUMENT ME!
   */
  void addCandidateFulfillmentGroupOffer(CandidateFulfillmentGroupOffer candidateOffer);

  /**
   * DOCUMENT ME!
   */
  void removeAllCandidateOffers();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<FulfillmentGroupAdjustment> getFulfillmentGroupAdjustments();

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroupAdjustments  DOCUMENT ME!
   */
  void setFulfillmentGroupAdjustments(List<FulfillmentGroupAdjustment> fulfillmentGroupAdjustments);

  /**
   * DOCUMENT ME!
   */
  void removeAllAdjustments();

  /**
   * Gets a list of TaxDetail objects, which are taxes that apply directly to this fulfillment group. An example of a
   * such a tax would be a shipping tax.
   *
   * @return  a list of taxes that apply to this fulfillment group
   */
  List<TaxDetail> getTaxes();

  /**
   * Gets the list of TaxDetail objects, which are taxes that apply directly to this fulfillment group. An example of a
   * such a tax would be a shipping tax.
   *
   * @param  taxes  the list of taxes on this fulfillment group
   */
  void setTaxes(List<TaxDetail> taxes);

  /**
   * Gets the total tax for this fulfillment group, which is the sum of the taxes on all fulfillment group items, fees,
   * and taxes on this fulfillment group itself (such as a shipping tax). This total is calculated in the TotalActivity
   * stage of the pricing workflow.
   *
   * @return  the total tax for the fulfillment group
   */
  Money getTotalTax();

  /**
   * Sets the total tax for this fulfillment group, which is the sum of the taxes on all fulfillment group items, fees,
   * and taxes on this fulfillment group itself (such as a shipping tax). This total should only be set during the
   * TotalActivity stage of the pricing workflow.
   *
   * @param  totalTax  the total tax for this fulfillment group
   */
  void setTotalTax(Money totalTax);

  /**
   * Gets the total item tax for this fulfillment group, which is the sum of the taxes on all fulfillment group items.
   * This total is calculated in the TotalActivity stage of the pricing workflow.
   *
   * @return  the total tax for this fulfillment group
   */
  Money getTotalItemTax();

  /**
   * Sets the total item tax for this fulfillment group, which is the sum of the taxes on all fulfillment group items.
   * This total should only be set during the TotalActivity stage of the pricing workflow.
   *
   * @param  totalItemTax  the total tax for this fulfillment group
   */
  void setTotalItemTax(Money totalItemTax);

  /**
   * Gets the total fee tax for this fulfillment group, which is the sum of the taxes on all fulfillment group fees.
   * This total is calculated in the TotalActivity stage of the pricing workflow.
   *
   * @return  the total tax for this fulfillment group
   */
  Money getTotalFeeTax();

  /**
   * Sets the total fee tax for this fulfillment group, which is the sum of the taxes on all fulfillment group fees.
   * This total should only be set during the TotalActivity stage of the pricing workflow.
   *
   * @param  totalFeeTax  the total tax for this fulfillment group
   */
  void setTotalFeeTax(Money totalFeeTax);

  /**
   * Gets the total fulfillment group tax for this fulfillment group, which is the sum of the taxes on this fulfillment
   * group itself (such as a shipping tax) only. It does not include the taxes on items or fees in this fulfillment
   * group. This total is calculated in the TotalActivity stage of the pricing workflow.
   *
   * @return  the total tax for this fulfillment group
   */
  Money getTotalFulfillmentGroupTax();

  /**
   * Sets the total fulfillment group tax for this fulfillment group, which is the sum of the taxes on this fulfillment
   * group itself (such as a shipping tax) only. It does not include the taxes on items or fees in this fulfillment
   * group. This total should only be set during the TotalActivity stage of the pricing workflow.
   *
   * @param  totalFulfillmentGroupTax  the total tax for this fulfillment group
   */
  void setTotalFulfillmentGroupTax(Money totalFulfillmentGroupTax);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getDeliveryInstruction();

  /**
   * DOCUMENT ME!
   *
   * @param  deliveryInstruction  DOCUMENT ME!
   */
  void setDeliveryInstruction(String deliveryInstruction);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PersonalMessage getPersonalMessage();

  /**
   * DOCUMENT ME!
   *
   * @param  personalMessage  DOCUMENT ME!
   */
  void setPersonalMessage(PersonalMessage personalMessage);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isPrimary();

  /**
   * DOCUMENT ME!
   *
   * @param  primary  DOCUMENT ME!
   */
  void setPrimary(boolean primary);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getMerchandiseTotal();

  /**
   * DOCUMENT ME!
   *
   * @param  merchandiseTotal  DOCUMENT ME!
   */
  void setMerchandiseTotal(Money merchandiseTotal);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getTotal();

  /**
   * DOCUMENT ME!
   *
   * @param  orderTotal  DOCUMENT ME!
   */
  void setTotal(Money orderTotal);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroupStatusType getStatus();

  /**
   * DOCUMENT ME!
   *
   * @param  status  DOCUMENT ME!
   */
  void setStatus(FulfillmentGroupStatusType status);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<FulfillmentGroupFee> getFulfillmentGroupFees();

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroupFees  DOCUMENT ME!
   */
  void setFulfillmentGroupFees(List<FulfillmentGroupFee> fulfillmentGroupFees);

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroupFee  DOCUMENT ME!
   */
  void addFulfillmentGroupFee(FulfillmentGroupFee fulfillmentGroupFee);

  /**
   * DOCUMENT ME!
   */
  void removeAllFulfillmentGroupFees();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean isShippingPriceTaxable();

  /**
   * DOCUMENT ME!
   *
   * @param  isShippingPriceTaxable  DOCUMENT ME!
   */
  void setIsShippingPriceTaxable(Boolean isShippingPriceTaxable);

  /**
   *
   * DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @deprecated  Should use {@link #getFulfillmentOption()} instead
   * @see         {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}
   */
  @Deprecated String getService();

  /**
   *
   * DOCUMENT ME!
   *
   * @param       service  DOCUMENT ME!
   *
   * @deprecated  Should use {@link #setFulfillmentOption()} instead
   * @see         {@link org.broadleafcommerce.core.order.domain.FulfillmentOption}
   */
  @Deprecated void setService(String service);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<DiscreteOrderItem> getDiscreteOrderItems();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getFulfillmentGroupAdjustmentsValue();

} // end interface FulfillmentGroup
