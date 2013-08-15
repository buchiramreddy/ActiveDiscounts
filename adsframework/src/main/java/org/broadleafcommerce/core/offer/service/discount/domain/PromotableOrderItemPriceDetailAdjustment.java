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

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.Offer;


/**
 * This class holds adjustment records during the discount calculation processing. This and other disposable objects
 * avoid churn on the database while the offer engine determines the best offer(s) for the order being priced.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public interface PromotableOrderItemPriceDetailAdjustment extends Serializable {
  /**
   * Returns the associated promotableOrderItemPriceDetail.
   *
   * @return  the associated promotableOrderItemPriceDetail.
   */
  PromotableOrderItemPriceDetail getPromotableOrderItemPriceDetail();

  /**
   * Returns the associated promotableCandidateItemOffer.
   *
   * @return  the associated promotableCandidateItemOffer.
   */
  Offer getOffer();

  /**
   * Returns the value of this adjustment if only retail prices can be used.
   *
   * @return  the value of this adjustment if only retail prices can be used.
   */
  Money getRetailAdjustmentValue();

  /**
   * Returns the value of this adjustment if sale prices can be used.
   *
   * @return  the value of this adjustment if sale prices can be used.
   */
  Money getSaleAdjustmentValue();

  /**
   * Returns the value of this adjustment. can be used.
   *
   * @return  the value of this adjustment.
   */
  Money getAdjustmentValue();

  /**
   * Returns true if the value was applied to the sale price.
   *
   * @return  true if the value was applied to the sale price.
   */
  boolean isAppliedToSalePrice();

  /**
   * Returns true if this adjustment represents a combinable offer.
   *
   * @return  true if this adjustment represents a combinable offer.
   */
  boolean isCombinable();

  /**
   * Returns true if this adjustment represents a totalitarian offer.
   *
   * @return  true if this adjustment represents a totalitarian offer.
   */
  boolean isTotalitarian();

  /**
   * Returns the id of the contained offer.
   *
   * @return  the id of the contained offer.
   */
  Long getOfferId();

  /**
   * Sets the adjustment price based on the passed in parameter.
   *
   * @param  useSalePrice  DOCUMENT ME!
   */
  void finalizeAdjustment(boolean useSalePrice);

  /**
   * Copy this adjustment. Used when a detail that contains this adjustment needs to be split.
   *
   * @return  copy this adjustment.
   */
  PromotableOrderItemPriceDetailAdjustment copy();

} // end interface PromotableOrderItemPriceDetailAdjustment
