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


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface FulfillmentGroupFee extends Serializable {
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
  Money getAmount();

  /**
   * DOCUMENT ME!
   *
   * @param  amount  DOCUMENT ME!
   */
  void setAmount(Money amount);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getName();

  /**
   * DOCUMENT ME!
   *
   * @param  name  DOCUMENT ME!
   */
  void setName(String name);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getReportingCode();

  /**
   * DOCUMENT ME!
   *
   * @param  reportingCode  DOCUMENT ME!
   */
  void setReportingCode(String reportingCode);

  /**
   * Returns whether or not this fee is taxable. If this flag is not set, it returns true by default
   *
   * @return  the taxable flag. If null, returns true
   */
  Boolean isTaxable();

  /**
   * Sets whether or not this fee is taxable.
   *
   * @param  taxable  DOCUMENT ME!
   */
  void setTaxable(Boolean taxable);

  /**
   * Gets a list of TaxDetail objects, which are taxes that apply directly to this fee.
   *
   * @return  a list of taxes that apply to this fee
   */
  List<TaxDetail> getTaxes();

  /**
   * Sets the list of TaxDetail objects, which are taxes that apply directly to this fee.
   *
   * @param  taxes  the list of taxes on this fee
   */
  void setTaxes(List<TaxDetail> taxes);

  /**
   * Gets the total tax for this fee, which is the sum of all taxes for this fee. This total is calculated in the
   * TotalActivity stage of the pricing workflow.
   *
   * @return  the total tax for this fee
   */
  Money getTotalTax();

  /**
   * Sets the total tax for this fee, which is the sum of all taxes for this fee. This total should only be set during
   * the TotalActivity stage of the pricing workflow.
   *
   * @param  totalTax  the total tax for this fee
   */
  void setTotalTax(Money totalTax);
} // end interface FulfillmentGroupFee
