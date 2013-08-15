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

package org.broadleafcommerce.core.payment.service;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.Referenced;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PaymentContextImpl implements PaymentContext {
  /** DOCUMENT ME! */
  protected Money       originalPaymentAmount;

  /** DOCUMENT ME! */
  protected Money       remainingPaymentAmount;

  /** DOCUMENT ME! */
  protected Money       transactionAmount;

  /** DOCUMENT ME! */
  protected Money       remainingTransactionAmount;

  /** DOCUMENT ME! */
  protected PaymentInfo paymentInfo;

  /** DOCUMENT ME! */
  protected Referenced  referencedPaymentInfo;

  /** DOCUMENT ME! */
  protected String      transactionId;

  /** DOCUMENT ME! */
  protected String      userName;

  /**
   * Creates a new PaymentContextImpl object.
   *
   * @param  transactionAmount           DOCUMENT ME!
   * @param  remainingTransactionAmount  DOCUMENT ME!
   * @param  paymentInfo                 DOCUMENT ME!
   * @param  referencedPaymentInfo       DOCUMENT ME!
   * @param  userName                    DOCUMENT ME!
   */
  public PaymentContextImpl(Money transactionAmount, Money remainingTransactionAmount, PaymentInfo paymentInfo,
    Referenced referencedPaymentInfo, String userName) {
    this.transactionAmount          = transactionAmount;
    this.remainingTransactionAmount = remainingTransactionAmount;
    this.paymentInfo                = paymentInfo;
    this.referencedPaymentInfo      = referencedPaymentInfo;
    this.userName                   = userName;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentContext#getOriginalPaymentAmount()
   */
  @Deprecated @Override public Money getOriginalPaymentAmount() {
    return originalPaymentAmount;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentContext#getRemainingPaymentAmount()
   */
  @Deprecated @Override public Money getRemainingPaymentAmount() {
    return remainingPaymentAmount;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentContext#getTransactionAmount()
   */
  @Override public Money getTransactionAmount() {
    return transactionAmount;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentContext#setTransactionAmount(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setTransactionAmount(Money transactionAmount) {
    this.transactionAmount = transactionAmount;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentContext#getRemainingTransactionAmount()
   */
  @Override public Money getRemainingTransactionAmount() {
    return remainingTransactionAmount;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentContext#setRemainingTransactionAmount(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setRemainingTransactionAmount(Money remainingTransactionAmount) {
    this.remainingTransactionAmount = remainingTransactionAmount;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentContext#getPaymentInfo()
   */
  @Override public PaymentInfo getPaymentInfo() {
    return paymentInfo;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentContext#getReferencedPaymentInfo()
   */
  @Override public Referenced getReferencedPaymentInfo() {
    return referencedPaymentInfo;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentContext#getTransactionId()
   */
  @Override public String getTransactionId() {
    return transactionId;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentContext#setTransactionId(java.lang.String)
   */
  @Override public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.PaymentContext#getUserName()
   */
  @Override public String getUserName() {
    return userName;
  }

} // end class PaymentContextImpl
