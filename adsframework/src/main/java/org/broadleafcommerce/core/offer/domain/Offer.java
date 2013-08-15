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

package org.broadleafcommerce.core.offer.domain;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.service.type.OfferDeliveryType;
import org.broadleafcommerce.core.offer.service.type.OfferDiscountType;
import org.broadleafcommerce.core.offer.service.type.OfferItemRestrictionRuleType;
import org.broadleafcommerce.core.offer.service.type.OfferType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface Offer extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @Deprecated String getAppliesToCustomerRules();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @Deprecated String getAppliesToOrderRules();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean getApplyDiscountToSalePrice();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Replaced by isAutomaticallyApplied property. In prior versions of Broadleaf deliveryType was used to
   *              differentiate "automatic" orders from those requiring a code. If the underlying property is null, this
   *              method will return a delivery type based on the isAutomatic property.
   *
   * @return      DOCUMENT ME!
   */
  OfferDeliveryType getDeliveryType();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getDescription();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferDiscountType getDiscountType();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Date getEndDate();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the maximum number of times that this offer can be used in the current order.
   *
   * <p>0 indicates unlimited usage.</p>
   *
   * @return  the maximum number of times that this offer can be used in the current order.
   */
  int getMaxUses();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the maximum number of times that this offer can be used by the same customer. This field tracks the number
   * of times the offer can be used and not how many times it is applied.
   *
   * <p>0 or null indicates unlimited usage per customer.</p>
   *
   * @return  the maximum number of times that this offer can be used by the same customer.
   */
  Long getMaxUsesPerCustomer();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferItemRestrictionRuleType getOfferItemQualifierRuleType();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferItemRestrictionRuleType getOfferItemTargetRuleType();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, OfferRule> getOfferMatchRules();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  int getPriority();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Set<OfferItemCriteria> getQualifyingItemCriteria();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Indicates the amount of items that must be purchased for this offer to be considered for this order.
   *
   * <p>The system will find all qualifying items for the given offer and sum their prices before any discounts are
   * applied to make the determination.</p>
   *
   * <p>If the sum of the qualifying items is not greater than this value the offer is not considered by the offer
   * processing algorithm.</p>
   *
   * @return  indicates the amount of items that must be purchased for this offer to be considered for this order.
   */
  Money getQualifyingItemSubTotal();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Date getStartDate();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Set<OfferItemCriteria> getTargetItemCriteria();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getTargetSystem();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean getTreatAsNewFormat();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferType getType();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @Deprecated int getUses();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  BigDecimal getValue();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @Deprecated boolean isApplyDiscountToMarkedItems();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns true if the offer system should automatically add this offer for consideration (versus requiring a code or
   * other delivery mechanism). This does not guarantee that the offer will qualify. All rules associated with this
   * offer must still pass. A true value here just means that the offer will be considered.
   *
   * <p>For backwards compatibility, if the underlying property is null, this method will check the
   * {@link #getDeliveryType()} method and return true if that value is set to AUTOMATIC.</p>
   *
   * <p>If still null, this value will return false.</p>
   *
   * @return  true if the offer system should automatically add this offer for consideration (versus requiring a code or
   *          other delivery mechanism).
   */
  boolean isAutomaticallyAdded();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns false if this offer is not combinable with other offers of the same type. For example, if this is an Item
   * offer it could be combined with other Order or FG offers but it cannot be combined with other Item offers.
   *
   * @return  false if this offer is not combinable with other offers of the same type.
   */
  boolean isCombinableWithOtherOffers();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @deprecated  Use isCombinable instead.
   *
   * @return      DOCUMENT ME!
   */
  boolean isStackable();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean isTotalitarianOffer();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  appliesToCustomerRules  DOCUMENT ME!
   */
  @Deprecated void setAppliesToCustomerRules(String appliesToCustomerRules);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  appliesToRules  DOCUMENT ME!
   */
  @Deprecated void setAppliesToOrderRules(String appliesToRules);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  applyDiscountToMarkedItems  DOCUMENT ME!
   */
  @Deprecated void setApplyDiscountToMarkedItems(boolean applyDiscountToMarkedItems);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  applyToSalePrice  DOCUMENT ME!
   */
  void setApplyDiscountToSalePrice(boolean applyToSalePrice);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets whether or not this offer should be automatically considered for consideration (versus requiring a code or
   * other delivery mechanism).
   *
   * @param  automaticallyAdded  DOCUMENT ME!
   *
   * @see    #isAutomaticallyAdded()
   */
  void setAutomaticallyAdded(boolean automaticallyAdded);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  combinableWithOtherOffers  DOCUMENT ME!
   */
  void setCombinableWithOtherOffers(boolean combinableWithOtherOffers);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @deprecated  Replaced by setAutomaticallyApplied(boolean val).
   *
   * @param       deliveryType  DOCUMENT ME!
   */
  void setDeliveryType(OfferDeliveryType deliveryType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  description  DOCUMENT ME!
   */
  void setDescription(String description);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  type  DOCUMENT ME!
   */
  void setDiscountType(OfferDiscountType type);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  endDate  DOCUMENT ME!
   */
  void setEndDate(Date endDate);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the maximum number of times that this offer can be used in the current order.
   *
   * <p>0 indicates unlimited usage.</p>
   *
   * @param  maxUses  DOCUMENT ME!
   */
  void setMaxUses(int maxUses);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the maximum number of times that this offer can be used by the same customer. Intended as a transient field
   * that gets derived from the other persisted max uses fields including maxUsesPerOrder and maxUsesPerCustomer.
   *
   * <p>0 or null indicates unlimited usage.</p>
   *
   * @param  maxUses  DOCUMENT ME!
   */
  void setMaxUsesPerCustomer(Long maxUses);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  name  DOCUMENT ME!
   */
  void setName(String name);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  restrictionRuleType  DOCUMENT ME!
   */
  void setOfferItemQualifierRuleType(OfferItemRestrictionRuleType restrictionRuleType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  restrictionRuleType  DOCUMENT ME!
   */
  void setOfferItemTargetRuleType(OfferItemRestrictionRuleType restrictionRuleType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  offerMatchRules  DOCUMENT ME!
   */
  void setOfferMatchRules(Map<String, OfferRule> offerMatchRules);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  priority  DOCUMENT ME!
   */
  void setPriority(int priority);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  qualifyingItemCriteria  DOCUMENT ME!
   */
  void setQualifyingItemCriteria(Set<OfferItemCriteria> qualifyingItemCriteria);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  qualifyingItemSubtotal  DOCUMENT ME!
   */
  void setQualifyingItemSubTotal(Money qualifyingItemSubtotal);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @deprecated  calls {@link #setCombinableWithOtherOffers(boolean)}
   *
   * @param       stackable  DOCUMENT ME!
   */
  void setStackable(boolean stackable);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  startDate  DOCUMENT ME!
   */
  void setStartDate(Date startDate);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  targetItemCriteria  DOCUMENT ME!
   */
  void setTargetItemCriteria(Set<OfferItemCriteria> targetItemCriteria);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  targetSystem  DOCUMENT ME!
   */
  void setTargetSystem(String targetSystem);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  totalitarianOffer  DOCUMENT ME!
   */
  void setTotalitarianOffer(Boolean totalitarianOffer);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  treatAsNewFormat  DOCUMENT ME!
   */
  void setTreatAsNewFormat(Boolean treatAsNewFormat);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  offerType  DOCUMENT ME!
   */
  void setType(OfferType offerType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  uses  DOCUMENT ME!
   */
  @Deprecated void setUses(int uses);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  value  DOCUMENT ME!
   */
  void setValue(BigDecimal value);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getMarketingMessage();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  marketingMessage  DOCUMENT ME!
   */
  void setMarketingMessage(String marketingMessage);

} // end interface Offer
