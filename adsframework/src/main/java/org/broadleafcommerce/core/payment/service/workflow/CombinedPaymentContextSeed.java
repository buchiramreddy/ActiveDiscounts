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

package org.broadleafcommerce.core.payment.service.workflow;

import java.util.Map;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.Referenced;
import org.broadleafcommerce.core.payment.service.module.PaymentResponse;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class CombinedPaymentContextSeed {
  private Map<PaymentInfo, Referenced> infos;
  private PaymentActionType            actionType;
  private Money                        orderTotal;
  private PaymentResponse              paymentResponse;
  private Money                        transactionAmount;

  /**
   * Creates a new CombinedPaymentContextSeed object.
   *
   * @param  infos            DOCUMENT ME!
   * @param  actionType       DOCUMENT ME!
   * @param  orderTotal       DOCUMENT ME!
   * @param  paymentResponse  DOCUMENT ME!
   */
  public CombinedPaymentContextSeed(Map<PaymentInfo, Referenced> infos, PaymentActionType actionType, Money orderTotal,
    PaymentResponse paymentResponse) {
    this.infos           = infos;
    this.actionType      = actionType;
    this.orderTotal      = orderTotal;
    this.paymentResponse = paymentResponse;
  }

  /**
   * Creates a new CombinedPaymentContextSeed object.
   *
   * @param  infos              DOCUMENT ME!
   * @param  actionType         DOCUMENT ME!
   * @param  orderTotal         DOCUMENT ME!
   * @param  paymentResponse    DOCUMENT ME!
   * @param  transactionAmount  DOCUMENT ME!
   */
  public CombinedPaymentContextSeed(Map<PaymentInfo, Referenced> infos, PaymentActionType actionType, Money orderTotal,
    PaymentResponse paymentResponse, Money transactionAmount) {
    this.infos             = infos;
    this.actionType        = actionType;
    this.orderTotal        = orderTotal;
    this.paymentResponse   = paymentResponse;
    this.transactionAmount = transactionAmount;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<PaymentInfo, Referenced> getInfos() {
    return infos;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PaymentActionType getActionType() {
    return actionType;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getOrderTotal() {
    return orderTotal;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PaymentResponse getPaymentResponse() {
    return paymentResponse;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getTransactionAmount() {
    return transactionAmount;
  }

} // end class CombinedPaymentContextSeed
