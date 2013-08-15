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

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.order.domain.OrderItemPriceDetail;


/**
 * Records the actual adjustments that were made to an OrderItemPriceDetail.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public interface OrderItemPriceDetailAdjustment extends Adjustment {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Stores the offer name at the time the adjustment was made. Primarily to simplify display within the admin.
   *
   * @return  stores the offer name at the time the adjustment was made.
   */
  String getOfferName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderItemPriceDetail getOrderItemPriceDetail();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Value of this adjustment relative to the retail price.
   *
   * @return  value of this adjustment relative to the retail price.
   */
  Money getRetailPriceValue();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Value of this adjustment relative to the sale price.
   *
   * @return  value of this adjustment relative to the sale price.
   */
  Money getSalesPriceValue();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  orderItemPriceDetail  DOCUMENT ME!
   * @param  offer                 DOCUMENT ME!
   * @param  reason                DOCUMENT ME!
   */
  void init(OrderItemPriceDetail orderItemPriceDetail, Offer offer, String reason);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Even for items that are on sale, it is possible that an adjustment was made to the retail price that gave the
   * customer a better offer.
   *
   * <p>Since some offers can be applied to the sale price and some only to the retail price, this setting provides the
   * required value.</p>
   *
   * @return  true if this adjustment was applied to the sale price
   */
  boolean isAppliedToSalePrice();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  appliedToSalePrice  DOCUMENT ME!
   */
  void setAppliedToSalePrice(boolean appliedToSalePrice);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the name of the offer at the time the adjustment was made.
   *
   * @param  offerName  DOCUMENT ME!
   */
  void setOfferName(String offerName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  orderItemPriceDetail  DOCUMENT ME!
   */
  void setOrderItemPriceDetail(OrderItemPriceDetail orderItemPriceDetail);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  retailPriceValue  DOCUMENT ME!
   */
  void setRetailPriceValue(Money retailPriceValue);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  salesPriceValue  DOCUMENT ME!
   */
  void setSalesPriceValue(Money salesPriceValue);
} // end interface OrderItemPriceDetailAdjustment
