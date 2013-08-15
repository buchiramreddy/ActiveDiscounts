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

import org.broadleafcommerce.core.order.service.type.FulfillmentGroupStatusType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface FulfillmentGroupItem extends Serializable {
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
  FulfillmentGroup getFulfillmentGroup();

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroup  DOCUMENT ME!
   */
  void setFulfillmentGroup(FulfillmentGroup fulfillmentGroup);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderItem getOrderItem();

  /**
   * DOCUMENT ME!
   *
   * @param  orderItem  DOCUMENT ME!
   */
  void setOrderItem(OrderItem orderItem);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  int getQuantity();

  /**
   * DOCUMENT ME!
   *
   * @param  quantity  DOCUMENT ME!
   */
  void setQuantity(int quantity);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getRetailPrice();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getSalePrice();

  /**
   *
   * DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @deprecated  Use {@link #getTotalItemAmount()} or {@link #getTotalItemTaxableAmount()}
   */
  Money getPrice();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getTotalItemAmount();

  /**
   * DOCUMENT ME!
   *
   * @param  amount  DOCUMENT ME!
   */
  void setTotalItemAmount(Money amount);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getProratedOrderAdjustmentAmount();

  /**
   * DOCUMENT ME!
   *
   * @param  amount  DOCUMENT ME!
   */
  void setProratedOrderAdjustmentAmount(Money amount);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getTotalItemTaxableAmount();

  /**
   * DOCUMENT ME!
   *
   * @param  amount  DOCUMENT ME!
   */
  void setTotalItemTaxableAmount(Money amount);

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
   */
  void removeAssociations();

  /**
   * @see  java.lang.Object#clone()
   */
  @Override FulfillmentGroupItem clone();

  /**
   * Gets a list of TaxDetail objects, which are taxes that apply directly to this item. The amount in each TaxDetail
   * takes into account the quantity of this item
   *
   * @return  a list of taxes that apply to this item
   */
  List<TaxDetail> getTaxes();

  /**
   * Sets the list of TaxDetail objects, which are taxes that apply directly to this item. The amount in each TaxDetail
   * must take into account the quantity of this item
   *
   * @param  taxes  the list of taxes on this item
   */
  void setTaxes(List<TaxDetail> taxes);

  /**
   * Gets the total tax for this item, which is the sum of all taxes for this item. This total is calculated in the
   * TotalActivity stage of the pricing workflow.
   *
   * @return  the total tax for this item
   */
  Money getTotalTax();

  /**
   * Sets the total tax for this item, which is the sum of all taxes for this item. This total should only be set during
   * the TotalActivity stage of the pricing workflow.
   *
   * @param  totalTax  the total tax for this item
   */
  void setTotalTax(Money totalTax);

  /**
   * Returns true if this item has pro-rated order adjustments.
   *
   * @return  true if this item has pro-rated order adjustments.
   */
  boolean getHasProratedOrderAdjustments();

} // end interface FulfillmentGroupItem
