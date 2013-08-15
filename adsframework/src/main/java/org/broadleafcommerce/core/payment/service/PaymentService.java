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

import org.broadleafcommerce.core.payment.domain.PaymentResponseItem;
import org.broadleafcommerce.core.payment.service.exception.PaymentException;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PaymentService {
  /**
   * DOCUMENT ME!
   *
   * @param   paymentType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean isValidCandidate(PaymentInfoType paymentType);

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem authorize(PaymentContext paymentContext) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem debit(PaymentContext paymentContext) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem authorizeAndDebit(PaymentContext paymentContext) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem credit(PaymentContext paymentContext) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem voidPayment(PaymentContext paymentContext) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem balance(PaymentContext paymentContext) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem reverseAuthorize(PaymentContext paymentContext) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem partialPayment(PaymentContext paymentContext) throws PaymentException;

} // end interface PaymentService
