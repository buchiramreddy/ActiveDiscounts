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

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.Referenced;
import org.broadleafcommerce.core.payment.service.module.PaymentResponse;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PaymentSeed implements CompositePaymentResponse {
  private Order                        order;
  private Map<PaymentInfo, Referenced> infos;
  private PaymentResponse              paymentResponse;
  private Money                        transactionAmount;

  /**
   * Creates a new PaymentSeed object.
   *
   * @param  order            DOCUMENT ME!
   * @param  infos            DOCUMENT ME!
   * @param  paymentResponse  DOCUMENT ME!
   */
  public PaymentSeed(Order order, Map<PaymentInfo, Referenced> infos, PaymentResponse paymentResponse) {
    this.order           = order;
    this.infos           = infos;
    this.paymentResponse = paymentResponse;
  }

  /**
   * Creates a new PaymentSeed object.
   *
   * @param  order              DOCUMENT ME!
   * @param  infos              DOCUMENT ME!
   * @param  paymentResponse    DOCUMENT ME!
   * @param  transactionAmount  DOCUMENT ME!
   */
  public PaymentSeed(Order order, Map<PaymentInfo, Referenced> infos, PaymentResponse paymentResponse,
    Money transactionAmount) {
    this.infos             = infos;
    this.order             = order;
    this.paymentResponse   = paymentResponse;
    this.transactionAmount = transactionAmount;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.workflow.CompositePaymentResponse#getOrder()
   */
  @Override public Order getOrder() {
    return order;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.workflow.CompositePaymentResponse#getInfos()
   */
  @Override public Map<PaymentInfo, Referenced> getInfos() {
    return infos;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.workflow.CompositePaymentResponse#getPaymentResponse()
   */
  @Override public PaymentResponse getPaymentResponse() {
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

} // end class PaymentSeed
