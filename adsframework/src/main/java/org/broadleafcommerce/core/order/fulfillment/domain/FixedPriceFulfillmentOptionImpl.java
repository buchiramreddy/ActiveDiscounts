/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.order.fulfillment.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrencyImpl;
import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;

import org.broadleafcommerce.core.order.domain.FulfillmentOptionImpl;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * DOCUMENT ME!
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "Fixed Price Fulfillment")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_FULFILLMENT_OPTION_FIXED")
public class FixedPriceFulfillmentOptionImpl extends FulfillmentOptionImpl implements FixedPriceFulfillmentOption {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(
    name      = "PRICE",
    precision = 19,
    scale     = 5,
    nullable  = false
  )
  protected BigDecimal price;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "CURRENCY_CODE")
  @ManyToOne(targetEntity = BroadleafCurrencyImpl.class)
  protected BroadleafCurrency currency;

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FixedPriceFulfillmentOption#getPrice()
   */
  @Override public Money getPrice() {
    return (price == null) ? null : BroadleafCurrencyUtils.getMoney(price, getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FixedPriceFulfillmentOption#setPrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setPrice(Money price) {
    this.price = Money.toAmount(price);
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FixedPriceFulfillmentOption#getCurrency()
   */
  @Override public BroadleafCurrency getCurrency() {
    return currency;
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FixedPriceFulfillmentOption#setCurrency(org.broadleafcommerce.common.currency.domain.BroadleafCurrency)
   */
  @Override public void setCurrency(BroadleafCurrency currency) {
    this.currency = currency;
  }

} // end class FixedPriceFulfillmentOptionImpl
