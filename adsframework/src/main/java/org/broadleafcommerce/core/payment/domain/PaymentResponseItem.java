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

import java.io.Serializable;

import java.util.Date;
import java.util.Map;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.payment.service.type.TransactionType;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PaymentResponseItem extends Serializable {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getAuthorizationCode();

  /**
   * DOCUMENT ME!
   *
   * @param  authorizationCode  DOCUMENT ME!
   */
  void setAuthorizationCode(String authorizationCode);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getMiddlewareResponseCode();

  /**
   * DOCUMENT ME!
   *
   * @param  middlewareResponseCode  DOCUMENT ME!
   */
  void setMiddlewareResponseCode(String middlewareResponseCode);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getMiddlewareResponseText();

  /**
   * DOCUMENT ME!
   *
   * @param  middlewareResponseText  DOCUMENT ME!
   */
  void setMiddlewareResponseText(String middlewareResponseText);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getProcessorResponseCode();

  /**
   * DOCUMENT ME!
   *
   * @param  processorResponseCode  DOCUMENT ME!
   */
  void setProcessorResponseCode(String processorResponseCode);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getProcessorResponseText();

  /**
   * DOCUMENT ME!
   *
   * @param  processorResponseText  DOCUMENT ME!
   */
  void setProcessorResponseText(String processorResponseText);

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
   *
   * DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @deprecated  DOCUMENT ME!
   * @see         #getTransactionAmount()
   */
  Money getAmountPaid();

  /**
   *
   * DOCUMENT ME!
   *
   * @param       amount  DOCUMENT ME!
   *
   * @deprecated  setTransactionAmount() instead.
   * @see         #setTransactionAmount(org.broadleafcommerce.common.money.Money)
   */
  void setAmountPaid(Money amount);

  /**
   * The amount that the system processed. For example, when submitting an order, this would be the order.getTotal. If
   * refunding $10, this would be 10.
   *
   * @return  the amount that the system processed.
   */
  Money getTransactionAmount();

  /**
   * Sets the transaction amount.
   *
   * @param  amount  DOCUMENT ME!
   */
  void setTransactionAmount(Money amount);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean getTransactionSuccess();

  /**
   * DOCUMENT ME!
   *
   * @param  transactionSuccess  DOCUMENT ME!
   */
  void setTransactionSuccess(Boolean transactionSuccess);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Date getTransactionTimestamp();

  /**
   * DOCUMENT ME!
   *
   * @param  transactionTimestamp  DOCUMENT ME!
   */
  void setTransactionTimestamp(Date transactionTimestamp);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getImplementorResponseCode();

  /**
   * DOCUMENT ME!
   *
   * @param  implementorResponseCode  DOCUMENT ME!
   */
  void setImplementorResponseCode(String implementorResponseCode);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getImplementorResponseText();

  /**
   * DOCUMENT ME!
   *
   * @param  implementorResponseText  DOCUMENT ME!
   */
  void setImplementorResponseText(String implementorResponseText);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getTransactionId();

  /**
   * DOCUMENT ME!
   *
   * @param  transactionId  DOCUMENT ME!
   */
  void setTransactionId(String transactionId);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getAvsCode();

  /**
   * DOCUMENT ME!
   *
   * @param  avsCode  DOCUMENT ME!
   */
  void setAvsCode(String avsCode);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getCvvCode();

  /**
   * DOCUMENT ME!
   *
   * @param  cvvCode  DOCUMENT ME!
   */
  void setCvvCode(String cvvCode);

  // TODO: Rename to getRemainingTransactionAmount
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getRemainingBalance();

  /**
   * DOCUMENT ME!
   *
   * @param  remainingBalance  DOCUMENT ME!
   */
  void setRemainingBalance(Money remainingBalance);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  TransactionType getTransactionType();

  /**
   * DOCUMENT ME!
   *
   * @param  transactionType  DOCUMENT ME!
   */
  void setTransactionType(TransactionType transactionType);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, String> getAdditionalFields();

  /**
   * DOCUMENT ME!
   *
   * @param  additionalFields  DOCUMENT ME!
   */
  void setAdditionalFields(Map<String, String> additionalFields);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getPaymentInfoId();

  /**
   * DOCUMENT ME!
   *
   * @param  paymentInfoId  DOCUMENT ME!
   */
  void setPaymentInfoId(Long paymentInfoId);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getUserName();

  /**
   * DOCUMENT ME!
   *
   * @param  userName  DOCUMENT ME!
   */
  void setUserName(String userName);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Customer getCustomer();

  /**
   * DOCUMENT ME!
   *
   * @param  customer  DOCUMENT ME!
   */
  void setCustomer(Customer customer);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getPaymentInfoReferenceNumber();

  /**
   * DOCUMENT ME!
   *
   * @param  paymentInfoReferenceNumber  DOCUMENT ME!
   */
  void setPaymentInfoReferenceNumber(String paymentInfoReferenceNumber);

  /**
   * DOCUMENT ME!
   *
   * @param  currency  DOCUMENT ME!
   */
  void setCurrency(BroadleafCurrency currency);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  BroadleafCurrency getCurrency();

  /**
   * DOCUMENT ME!
   *
   * @param  paymentInfo  DOCUMENT ME!
   */
  void setPaymentInfo(PaymentInfo paymentInfo);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PaymentInfo getPaymentInfo();

} // end interface PaymentResponseItem
