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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrencyImpl;
import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;

import org.broadleafcommerce.core.payment.service.type.PaymentLogEventType;
import org.broadleafcommerce.core.payment.service.type.TransactionType;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerImpl;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PAYMENT_LOG")
public class PaymentLogImpl implements PaymentLog {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "PAYMENT_LOG_ID")
  @GeneratedValue(generator = "PaymentLogId")
  @GenericGenerator(
    name       = "PaymentLogId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "PaymentLogImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.payment.domain.PaymentLogImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentLogImpl_User_Name",
    order        = 1,
    group        = "PaymentLogImpl_Payment_Log",
    readOnly     = true
  )
  @Column(
    name     = "USER_NAME",
    nullable = false
  )
  @Index(
    name        = "PAYMENTLOG_USER_INDEX",
    columnNames = { "USER_NAME" }
  )
  protected String userName;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentLogImpl_Transaction_Time",
    order        = 3,
    group        = "PaymentLogImpl_Payment_Log",
    readOnly     = true
  )
  @Column(
    name     = "TRANSACTION_TIMESTAMP",
    nullable = false
  )
  @Temporal(TemporalType.TIMESTAMP)
  protected Date transactionTimestamp;

  /** DOCUMENT ME! */
  @AdminPresentation(
    excluded = true,
    readOnly = true
  )
  @Column(name = "ORDER_PAYMENT_ID")
  @Index(
    name        = "PAYMENTLOG_ORDERPAYMENT_INDEX",
    columnNames = { "ORDER_PAYMENT_ID" }
  )
  protected Long paymentInfoId;

  /** DOCUMENT ME! */
  @Index(
    name        = "PAYMENTLOG_CUSTOMER_INDEX",
    columnNames = { "CUSTOMER_ID" }
  )
  @JoinColumn(name = "CUSTOMER_ID")
  @ManyToOne(targetEntity = CustomerImpl.class)
  protected Customer customer;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentLogImpl_Payment_Ref_Number",
    order        = 4,
    group        = "PaymentLogImpl_Payment_Log",
    readOnly     = true
  )
  @Column(name = "PAYMENT_INFO_REFERENCE_NUMBER")
  @Index(
    name        = "PAYMENTLOG_REFERENCE_INDEX",
    columnNames = { "PAYMENT_INFO_REFERENCE_NUMBER" }
  )
  protected String paymentInfoReferenceNumber;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentLogImpl_Transaction_Type",
    order        = 5,
    group        = "PaymentLogImpl_Payment_Log",
    readOnly     = true
  )
  @Column(
    name     = "TRANSACTION_TYPE",
    nullable = false
  )
  @Index(
    name        = "PAYMENTLOG_TRANTYPE_INDEX",
    columnNames = { "TRANSACTION_TYPE" }
  )
  protected String transactionType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentLogImpl_Transaction_Successfule",
    order        = 6,
    group        = "PaymentLogImpl_Payment_Log",
    readOnly     = true
  )
  @Column(name = "TRANSACTION_SUCCESS")
  protected Boolean transactionSuccess = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentLogImpl_Exception_Message",
    order        = 7,
    group        = "PaymentLogImpl_Payment_Log",
    readOnly     = true
  )
  @Column(name = "EXCEPTION_MESSAGE")
  protected String exceptionMessage;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentLogImpl_Type",
    order        = 8,
    group        = "PaymentLogImpl_Payment_Log",
    readOnly     = true
  )
  @Column(
    name     = "LOG_TYPE",
    nullable = false
  )
  @Index(
    name        = "PAYMENTLOG_LOGTYPE_INDEX",
    columnNames = { "LOG_TYPE" }
  )
  protected String logType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentLogImpl_Amount",
    order        = 2,
    group        = "PaymentLogImpl_Payment_Log",
    readOnly     = true
  )
  @Column(
    name      = "AMOUNT_PAID",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal amountPaid;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentLogImpl_currency",
    order        = 2,
    group        = "PaymentLogImpl_Payment_Log",
    readOnly     = true
  )
  @JoinColumn(name = "CURRENCY_CODE")
  @ManyToOne(targetEntity = BroadleafCurrencyImpl.class)
  protected BroadleafCurrency currency;


  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#getUserName()
   */
  @Override public String getUserName() {
    return userName;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#setUserName(java.lang.String)
   */
  @Override public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#getTransactionTimestamp()
   */
  @Override public Date getTransactionTimestamp() {
    return transactionTimestamp;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#setTransactionTimestamp(java.util.Date)
   */
  @Override public void setTransactionTimestamp(Date transactionTimestamp) {
    this.transactionTimestamp = transactionTimestamp;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#getPaymentInfoId()
   */
  @Override public Long getPaymentInfoId() {
    return paymentInfoId;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#setPaymentInfoId(java.lang.Long)
   */
  @Override public void setPaymentInfoId(Long paymentInfoId) {
    this.paymentInfoId = paymentInfoId;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#getCustomer()
   */
  @Override public Customer getCustomer() {
    return customer;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#setCustomer(org.broadleafcommerce.profile.core.domain.Customer)
   */
  @Override public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#getPaymentInfoReferenceNumber()
   */
  @Override public String getPaymentInfoReferenceNumber() {
    return paymentInfoReferenceNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#setPaymentInfoReferenceNumber(java.lang.String)
   */
  @Override public void setPaymentInfoReferenceNumber(String paymentInfoReferenceNumber) {
    this.paymentInfoReferenceNumber = paymentInfoReferenceNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#getTransactionType()
   */
  @Override public TransactionType getTransactionType() {
    return TransactionType.getInstance(transactionType);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#setTransactionType(org.broadleafcommerce.core.payment.service.type.TransactionType)
   */
  @Override public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#getLogType()
   */
  @Override public PaymentLogEventType getLogType() {
    return PaymentLogEventType.getInstance(logType);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#setLogType(org.broadleafcommerce.core.payment.service.type.PaymentLogEventType)
   */
  @Override public void setLogType(PaymentLogEventType logType) {
    this.logType = logType.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#getTransactionSuccess()
   */
  @Override public Boolean getTransactionSuccess() {
    if (transactionSuccess == null) {
      return Boolean.FALSE;
    } else {
      return transactionSuccess;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#setTransactionSuccess(java.lang.Boolean)
   */
  @Override public void setTransactionSuccess(Boolean transactionSuccess) {
    this.transactionSuccess = transactionSuccess;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#getExceptionMessage()
   */
  @Override public String getExceptionMessage() {
    return exceptionMessage;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#setExceptionMessage(java.lang.String)
   */
  @Override public void setExceptionMessage(String exceptionMessage) {
    this.exceptionMessage = exceptionMessage;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#getAmountPaid()
   */
  @Override public Money getAmountPaid() {
    return BroadleafCurrencyUtils.getMoney(amountPaid, currency);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#setAmountPaid(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setAmountPaid(Money amountPaid) {
    this.amountPaid = Money.toAmount(amountPaid);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#getCurrency()
   */
  @Override public BroadleafCurrency getCurrency() {
    return currency;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentLog#setCurrency(org.broadleafcommerce.common.currency.domain.BroadleafCurrency)
   */
  @Override public void setCurrency(BroadleafCurrency currency) {
    this.currency = currency;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((customer == null) ? 0 : customer.hashCode());
    result = (prime * result) + ((paymentInfoId == null) ? 0 : paymentInfoId.hashCode());
    result = (prime * result) + ((paymentInfoReferenceNumber == null) ? 0 : paymentInfoReferenceNumber.hashCode());
    result = (prime * result) + ((transactionTimestamp == null) ? 0 : transactionTimestamp.hashCode());
    result = (prime * result) + ((userName == null) ? 0 : userName.hashCode());

    return result;
  }

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    PaymentLogImpl other = (PaymentLogImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (customer == null) {
      if (other.customer != null) {
        return false;
      }
    } else if (!customer.equals(other.customer)) {
      return false;
    }

    if (paymentInfoId == null) {
      if (other.paymentInfoId != null) {
        return false;
      }
    } else if (!paymentInfoId.equals(other.paymentInfoId)) {
      return false;
    }

    if (paymentInfoReferenceNumber == null) {
      if (other.paymentInfoReferenceNumber != null) {
        return false;
      }
    } else if (!paymentInfoReferenceNumber.equals(other.paymentInfoReferenceNumber)) {
      return false;
    }

    if (transactionTimestamp == null) {
      if (other.transactionTimestamp != null) {
        return false;
      }
    } else if (!transactionTimestamp.equals(other.transactionTimestamp)) {
      return false;
    }

    if (userName == null) {
      if (other.userName != null) {
        return false;
      }
    } else if (!userName.equals(other.userName)) {
      return false;
    }

    return true;
  } // end method equals
} // end class PaymentLogImpl
