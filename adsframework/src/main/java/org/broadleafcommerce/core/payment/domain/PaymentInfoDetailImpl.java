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

package org.broadleafcommerce.core.payment.domain;

import java.math.BigDecimal;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrencyImpl;
import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   Jerry Ocanas (jocanas)
 * @version  $Revision$, $Date$
 */
@AdminPresentationMergeOverrides(
  {
    @AdminPresentationMergeOverride(
      name = "",
      mergeEntries =
        @AdminPresentationMergeEntry(
          propertyType         = PropertyType.AdminPresentation.READONLY,
          booleanOverrideValue = true
        )
    )
  }
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ORDER_PAYMENT_DETAILS")
public class PaymentInfoDetailImpl implements PaymentInfoDetail, CurrencyCodeIdentifiable {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "PAYMENT_DETAIL_ID")
  @GeneratedValue(generator = "PaymentInfoDetailId")
  @GenericGenerator(
    name       = "PaymentInfoDetailId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "PaymentInfoDetailImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.payment.domain.PaymentInfoDetailImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "PAYMENT_INFO")
  @ManyToOne(
    targetEntity = PaymentInfoImpl.class,
    optional     = false
  )
  protected PaymentInfo paymentInfo;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "PaymentInfoDetailTypeImpl_Type",
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.payment.domain.PaymentInfoDetailType",
    prominent            = true,
    gridOrder            = 1000
  )
  @Column(name = "PAYMENT_INFO_DETAIL_TYPE")
  protected String type;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentInfoDetailTypeImpl_Amount",
    fieldType    = SupportedFieldType.MONEY,
    prominent    = true,
    gridOrder    = 2000
  )
  @Column(name = "PAYMENT_AMOUNT")
  protected BigDecimal amount;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "CURRENCY_CODE")
  @ManyToOne(targetEntity = BroadleafCurrencyImpl.class)
  protected BroadleafCurrency currency;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentInfoDetailTypeImpl_Date",
    prominent    = true,
    gridOrder    = 3000
  )
  @Column(name = "DATE_RECORDED")
  protected Date date;

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfoDetail#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfoDetail#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfoDetail#getPaymentInfo()
   */
  @Override public PaymentInfo getPaymentInfo() {
    return paymentInfo;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfoDetail#setPaymentInfo(org.broadleafcommerce.core.payment.domain.PaymentInfo)
   */
  @Override public void setPaymentInfo(PaymentInfo paymentInfo) {
    this.paymentInfo = paymentInfo;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfoDetail#getType()
   */
  @Override public PaymentInfoDetailType getType() {
    return PaymentInfoDetailType.getInstance(type);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfoDetail#setType(org.broadleafcommerce.core.payment.domain.PaymentInfoDetailType)
   */
  @Override public void setType(PaymentInfoDetailType type) {
    this.type = (type == null) ? null : type.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfoDetail#getAmount()
   */
  @Override public Money getAmount() {
    return (amount == null) ? BroadleafCurrencyUtils.getMoney(BigDecimal.ZERO, getCurrency())
                            : BroadleafCurrencyUtils.getMoney(amount, getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfoDetail#setAmount(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setAmount(Money amount) {
    this.amount = amount.getAmount();
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfoDetail#getCurrency()
   */
  @Override public BroadleafCurrency getCurrency() {
    return currency;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfoDetail#setCurrency(org.broadleafcommerce.common.currency.domain.BroadleafCurrency)
   */
  @Override public void setCurrency(BroadleafCurrency currency) {
    this.currency = currency;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfoDetail#getDate()
   */
  @Override public Date getDate() {
    return date;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentInfoDetail#setDate(java.util.Date)
   */
  @Override public void setDate(Date date) {
    this.date = date;
  }

  /**
   * @see  org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable#getCurrencyCode()
   */
  @Override public String getCurrencyCode() {
    if (currency != null) {
      return currency.getCurrencyCode();
    }

    return ((CurrencyCodeIdentifiable) paymentInfo).getCurrencyCode();
  }
} // end class PaymentInfoDetailImpl
