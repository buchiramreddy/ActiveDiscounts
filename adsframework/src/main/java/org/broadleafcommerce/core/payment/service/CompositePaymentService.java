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

import java.util.Map;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.domain.Referenced;
import org.broadleafcommerce.core.payment.service.exception.PaymentException;
import org.broadleafcommerce.core.payment.service.module.PaymentResponse;
import org.broadleafcommerce.core.payment.service.workflow.CompositePaymentResponse;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface CompositePaymentService {
  /**
   * DOCUMENT ME!
   *
   * @param   order     DOCUMENT ME!
   * @param   payments  DOCUMENT ME!
   * @param   response  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  CompositePaymentResponse executePayment(Order order, Map<PaymentInfo, Referenced> payments, PaymentResponse response)
    throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   order     DOCUMENT ME!
   * @param   payments  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  CompositePaymentResponse executePayment(Order order, Map<PaymentInfo, Referenced> payments) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  CompositePaymentResponse executePayment(Order order) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   order               DOCUMENT ME!
   * @param   paymentInfoFactory  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  CompositePaymentResponse executePaymentForGateway(Order order, PaymentInfoFactory paymentInfoFactory)
    throws PaymentException;

} // end interface CompositePaymentService
