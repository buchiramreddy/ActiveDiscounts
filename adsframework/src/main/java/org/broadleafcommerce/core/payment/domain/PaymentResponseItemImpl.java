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
import java.util.HashMap;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrencyImpl;
import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.broadleafcommerce.core.payment.service.type.TransactionType;

import org.broadleafcommerce.profile.core.domain.Customer;
import org.broadleafcommerce.profile.core.domain.CustomerImpl;

import org.hibernate.annotations.BatchSize;
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
@Table(name = "BLC_PAYMENT_RESPONSE_ITEM")
public class PaymentResponseItemImpl implements PaymentResponseItem {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "PAYMENT_RESPONSE_ITEM_ID")
  @GeneratedValue(generator = "PaymentResponseItemId")
  @GenericGenerator(
    name       = "PaymentResponseItemId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "PaymentResponseItemImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.payment.domain.PaymentResponseItemImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_User_Name",
    order        = 1,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(
    name     = "USER_NAME",
    nullable = false
  )
  protected String userName;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Amount",
    order        = 2,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true,
    prominent    = true,
    gridOrder    = 200,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "AMOUNT_PAID",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal amountPaid;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Transaction_Amount",
    order        = 2,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(
    name      = "TRANSACTION_AMOUNT",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal transactionAmount;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Authorization_Code",
    order        = 3,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(name = "AUTHORIZATION_CODE")
  protected String authorizationCode;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Middleware_Response_Code",
    order        = 4,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(name = "MIDDLEWARE_RESPONSE_CODE")
  protected String middlewareResponseCode;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Middleware_Response_Text",
    order        = 5,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(name = "MIDDLEWARE_RESPONSE_TEXT")
  protected String middlewareResponseText;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Processor_Response_Code",
    order        = 6,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(name = "PROCESSOR_RESPONSE_CODE")
  protected String processorResponseCode;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Processor_Response_Text",
    order        = 7,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(name = "PROCESSOR_RESPONSE_TEXT")
  protected String processorResponseText;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Implementer_Response_Code",
    order        = 8,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(name = "IMPLEMENTOR_RESPONSE_CODE")
  protected String implementorResponseCode;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Implementer_Response_Text",
    order        = 9,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(name = "IMPLEMENTOR_RESPONSE_TEXT")
  protected String implementorResponseText;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Response_Ref_Number",
    order        = 10,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(name = "REFERENCE_NUMBER")
  @Index(
    name        = "PAYRESPONSE_REFERENCE_INDEX",
    columnNames = { "REFERENCE_NUMBER" }
  )
  protected String referenceNumber;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Transaction_Successful",
    order        = 11,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true,
    prominent    = true,
    gridOrder    = 300
  )
  @Column(name = "TRANSACTION_SUCCESS")
  protected Boolean transactionSuccess = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Transaction_Time",
    order        = 12,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true,
    prominent    = true,
    gridOrder    = 100
  )
  @Column(
    name     = "TRANSACTION_TIMESTAMP",
    nullable = false
  )
  @Temporal(TemporalType.TIMESTAMP)
  protected Date transactionTimestamp;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Transaction_Id",
    order        = 13,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(name = "TRANSACTION_ID")
  protected String transactionId;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_AVS_Code",
    order        = 14,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(name = "AVS_CODE")
  protected String avsCode;

  /** DOCUMENT ME! */
  @Transient protected String cvvCode;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Remaining_Balance",
    order        = 15,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(
    name      = "REMAINING_BALANCE",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal remainingBalance;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Transaction_Type",
    order        = 16,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true,
    prominent    = true,
    gridOrder    = 400
  )
  @Column(
    name     = "TRANSACTION_TYPE",
    nullable = false
  )
  @Index(
    name        = "PAYRESPONSE_TRANTYPE_INDEX",
    columnNames = { "TRANSACTION_TYPE" }
  )
  protected String transactionType;

  /** DOCUMENT ME! */
  @BatchSize(size = 50)
  @CollectionTable(
    name        = "BLC_PAYMENT_ADDITIONAL_FIELDS",
    joinColumns = @JoinColumn(name = "PAYMENT_RESPONSE_ITEM_ID")
  )
  @Column(name = "FIELD_VALUE")
  @ElementCollection
  @MapKeyColumn(name = "FIELD_NAME")
  protected Map<String, String> additionalFields = new HashMap<String, String>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    excluded = true,
    readOnly = true
  )
  @Column(name = "ORDER_PAYMENT_ID")
  @Index(
    name        = "PAYRESPONSE_ORDERPAYMENT_INDEX",
    columnNames = { "ORDER_PAYMENT_ID" }
  )
  protected Long paymentInfoId;

  /** DOCUMENT ME! */
  @Index(
    name        = "PAYRESPONSE_CUSTOMER_INDEX",
    columnNames = { "CUSTOMER_ID" }
  )
  @JoinColumn(name = "CUSTOMER_ID")
  @ManyToOne(targetEntity = CustomerImpl.class)
  protected Customer customer;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_Payment_Ref_Number",
    order        = 17,
    group        = "PaymentResponseItemImpl_Payment_Response",
    readOnly     = true
  )
  @Column(name = "PAYMENT_INFO_REFERENCE_NUMBER")
  @Index(
    name        = "PAYRESPONSE_REFERENCE_INDEX",
    columnNames = { "PAYMENT_INFO_REFERENCE_NUMBER" }
  )
  protected String paymentInfoReferenceNumber;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PaymentResponseItemImpl_currency",
    order        = 2,
    group        = "PaymentLogImpl_Payment_Log",
    readOnly     = true
  )
  @JoinColumn(name = "CURRENCY_CODE")
  @ManyToOne(targetEntity = BroadleafCurrencyImpl.class)
  protected BroadleafCurrency currency;

  /** DOCUMENT ME! */
  @JoinColumn(
    name                 = "PAYMENT_INFO_REFERENCE_NUMBER",
    referencedColumnName = "REFERENCE_NUMBER",
    insertable           = false,
    updatable            = false
  )
  @ManyToOne(targetEntity = PaymentInfoImpl.class)
  protected PaymentInfo paymentInfo;

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getAuthorizationCode()
   */
  @Override public String getAuthorizationCode() {
    return authorizationCode;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setAuthorizationCode(java.lang.String)
   */
  @Override public void setAuthorizationCode(String authorizationCode) {
    this.authorizationCode = authorizationCode;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getMiddlewareResponseCode()
   */
  @Override public String getMiddlewareResponseCode() {
    return middlewareResponseCode;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setMiddlewareResponseCode(java.lang.String)
   */
  @Override public void setMiddlewareResponseCode(String middlewareResponseCode) {
    this.middlewareResponseCode = middlewareResponseCode;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getMiddlewareResponseText()
   */
  @Override public String getMiddlewareResponseText() {
    return middlewareResponseText;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setMiddlewareResponseText(java.lang.String)
   */
  @Override public void setMiddlewareResponseText(String middlewareResponseText) {
    this.middlewareResponseText = middlewareResponseText;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getProcessorResponseCode()
   */
  @Override public String getProcessorResponseCode() {
    return processorResponseCode;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setProcessorResponseCode(java.lang.String)
   */
  @Override public void setProcessorResponseCode(String processorResponseCode) {
    this.processorResponseCode = processorResponseCode;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getProcessorResponseText()
   */
  @Override public String getProcessorResponseText() {
    return processorResponseText;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setProcessorResponseText(java.lang.String)
   */
  @Override public void setProcessorResponseText(String processorResponseText) {
    this.processorResponseText = processorResponseText;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getReferenceNumber()
   */
  @Override public String getReferenceNumber() {
    return referenceNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setReferenceNumber(java.lang.String)
   */
  @Override public void setReferenceNumber(String referenceNumber) {
    this.referenceNumber = referenceNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getAmountPaid()
   */
  @Deprecated @Override public Money getAmountPaid() {
    return BroadleafCurrencyUtils.getMoney(amountPaid, getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setAmountPaid(org.broadleafcommerce.common.money.Money)
   */
  @Deprecated @Override public void setAmountPaid(Money amountPaid) {
    this.amountPaid = Money.toAmount(amountPaid);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getTransactionAmount()
   */
  @Override public Money getTransactionAmount() {
    return BroadleafCurrencyUtils.getMoney(transactionAmount, getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setTransactionAmount(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTransactionAmount(Money transactionAmount) {
    this.transactionAmount = Money.toAmount(transactionAmount);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getTransactionSuccess()
   */
  @Override public Boolean getTransactionSuccess() {
    if (transactionSuccess == null) {
      return Boolean.FALSE;
    } else {
      return transactionSuccess;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setTransactionSuccess(java.lang.Boolean)
   */
  @Override public void setTransactionSuccess(Boolean transactionSuccess) {
    this.transactionSuccess = transactionSuccess;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getTransactionTimestamp()
   */
  @Override public Date getTransactionTimestamp() {
    return transactionTimestamp;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setTransactionTimestamp(java.util.Date)
   */
  @Override public void setTransactionTimestamp(Date transactionTimestamp) {
    this.transactionTimestamp = transactionTimestamp;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getImplementorResponseCode()
   */
  @Override public String getImplementorResponseCode() {
    return implementorResponseCode;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setImplementorResponseCode(java.lang.String)
   */
  @Override public void setImplementorResponseCode(String implementorResponseCode) {
    this.implementorResponseCode = implementorResponseCode;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getImplementorResponseText()
   */
  @Override public String getImplementorResponseText() {
    return implementorResponseText;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setImplementorResponseText(java.lang.String)
   */
  @Override public void setImplementorResponseText(String implementorResponseText) {
    this.implementorResponseText = implementorResponseText;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getTransactionId()
   */
  @Override public String getTransactionId() {
    return transactionId;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setTransactionId(java.lang.String)
   */
  @Override public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getAvsCode()
   */
  @Override public String getAvsCode() {
    return avsCode;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setAvsCode(java.lang.String)
   */
  @Override public void setAvsCode(String avsCode) {
    this.avsCode = avsCode;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getCvvCode()
   */
  @Override public String getCvvCode() {
    return cvvCode;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setCvvCode(java.lang.String)
   */
  @Override public void setCvvCode(String cvvCode) {
    this.cvvCode = cvvCode;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getRemainingBalance()
   */
  @Override public Money getRemainingBalance() {
    return (remainingBalance == null) ? null : BroadleafCurrencyUtils.getMoney(remainingBalance, getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setRemainingBalance(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setRemainingBalance(Money remainingBalance) {
    this.remainingBalance = (remainingBalance == null) ? null : Money.toAmount(remainingBalance);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getTransactionType()
   */
  @Override public TransactionType getTransactionType() {
    return TransactionType.getInstance(transactionType);
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setTransactionType(org.broadleafcommerce.core.payment.service.type.TransactionType)
   */
  @Override public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getAdditionalFields()
   */
  @Override public Map<String, String> getAdditionalFields() {
    return additionalFields;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setAdditionalFields(java.util.Map)
   */
  @Override public void setAdditionalFields(Map<String, String> additionalFields) {
    this.additionalFields = additionalFields;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getId() {
    return id;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getPaymentInfoId()
   */
  @Override public Long getPaymentInfoId() {
    return paymentInfoId;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setPaymentInfoId(java.lang.Long)
   */
  @Override public void setPaymentInfoId(Long paymentInfoId) {
    this.paymentInfoId = paymentInfoId;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getUserName()
   */
  @Override public String getUserName() {
    return userName;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setUserName(java.lang.String)
   */
  @Override public void setUserName(String userName) {
    this.userName = userName;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getCustomer()
   */
  @Override public Customer getCustomer() {
    return customer;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setCustomer(org.broadleafcommerce.profile.core.domain.Customer)
   */
  @Override public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getCurrency()
   */
  @Override public BroadleafCurrency getCurrency() {
    return currency;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setCurrency(org.broadleafcommerce.common.currency.domain.BroadleafCurrency)
   */
  @Override public void setCurrency(BroadleafCurrency currency) {
    this.currency = currency;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getPaymentInfoReferenceNumber()
   */
  @Override public String getPaymentInfoReferenceNumber() {
    return paymentInfoReferenceNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setPaymentInfoReferenceNumber(java.lang.String)
   */
  @Override public void setPaymentInfoReferenceNumber(String paymentInfoReferenceNumber) {
    this.paymentInfoReferenceNumber = paymentInfoReferenceNumber;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#getPaymentInfo()
   */
  @Override public PaymentInfo getPaymentInfo() {
    return paymentInfo;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.PaymentResponseItem#setPaymentInfo(org.broadleafcommerce.core.payment.domain.PaymentInfo)
   */
  @Override public void setPaymentInfo(PaymentInfo paymentInfo) {
    this.paymentInfo = paymentInfo;
  }

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(PaymentResponseItem.class.getName()).append("\n");
    sb.append("auth code: ").append(this.getAuthorizationCode()).append("\n");
    sb.append("implementor response code: ").append(this.getImplementorResponseCode()).append("\n");
    sb.append("implementor response text: ").append(this.getImplementorResponseText()).append("\n");
    sb.append("middleware response code: ").append(this.getMiddlewareResponseCode()).append("\n");
    sb.append("middleware response text: ").append(this.getMiddlewareResponseText()).append("\n");
    sb.append("processor response code: ").append(this.getProcessorResponseCode()).append("\n");
    sb.append("processor response text: ").append(this.getProcessorResponseText()).append("\n");
    sb.append("reference number: ").append(this.getReferenceNumber()).append("\n");
    sb.append("transaction id: ").append(this.getTransactionId()).append("\n");
    sb.append("avs code: ").append(this.getAvsCode()).append("\n");

    if (remainingBalance != null) {
      sb.append("remaining balance: ").append(this.getRemainingBalance());
    }

    return sb.toString();
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((transactionId == null) ? 0 : transactionId.hashCode());

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

    PaymentResponseItemImpl other = (PaymentResponseItemImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (transactionId == null) {
      if (other.transactionId != null) {
        return false;
      }
    } else if (!transactionId.equals(other.transactionId)) {
      return false;
    }

    return true;
  } // end method equals

} // end class PaymentResponseItemImpl
