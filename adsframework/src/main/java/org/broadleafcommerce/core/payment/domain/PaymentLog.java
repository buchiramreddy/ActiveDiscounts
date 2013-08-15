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

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.payment.service.type.PaymentLogEventType;
import org.broadleafcommerce.core.payment.service.type.TransactionType;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PaymentLog extends Serializable {
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
  String getExceptionMessage();

  /**
   * DOCUMENT ME!
   *
   * @param  exceptionMessage  DOCUMENT ME!
   */
  void setExceptionMessage(String exceptionMessage);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PaymentLogEventType getLogType();

  /**
   * DOCUMENT ME!
   *
   * @param  logType  DOCUMENT ME!
   */
  void setLogType(PaymentLogEventType logType);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getAmountPaid();

  /**
   * DOCUMENT ME!
   *
   * @param  amountPaid  DOCUMENT ME!
   */
  void setAmountPaid(Money amountPaid);

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

} // end interface PaymentLog
