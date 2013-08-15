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

package org.broadleafcommerce.core.checkout.service.workflow;

import java.util.Map;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.Referenced;
import org.broadleafcommerce.core.payment.service.module.PaymentResponse;
import org.broadleafcommerce.core.payment.service.module.PaymentResponseImpl;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class CheckoutSeed implements CheckoutResponse {
  private Map<PaymentInfo, Referenced> infos;
  private Order                        order;
  private PaymentResponse              paymentResponse   = new PaymentResponseImpl();
  private Map<String, Object>          userDefinedFields;

  /**
   * Creates a new CheckoutSeed object.
   *
   * @param  order              DOCUMENT ME!
   * @param  infos              DOCUMENT ME!
   * @param  userDefinedFields  DOCUMENT ME!
   */
  public CheckoutSeed(Order order, Map<PaymentInfo, Referenced> infos, Map<String, Object> userDefinedFields) {
    this.order             = order;
    this.infos             = infos;
    this.userDefinedFields = userDefinedFields;
  }

  /**
   * @see  org.broadleafcommerce.core.checkout.service.workflow.CheckoutResponse#getInfos()
   */
  @Override public Map<PaymentInfo, Referenced> getInfos() {
    return infos;
  }

  /**
   * @see  org.broadleafcommerce.core.checkout.service.workflow.CheckoutResponse#getOrder()
   */
  @Override public Order getOrder() {
    return order;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * @see  org.broadleafcommerce.core.checkout.service.workflow.CheckoutResponse#getPaymentResponse()
   */
  @Override public PaymentResponse getPaymentResponse() {
    return paymentResponse;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, Object> getUserDefinedFields() {
    return userDefinedFields;
  }
} // end class CheckoutSeed
