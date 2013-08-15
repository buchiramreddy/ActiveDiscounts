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

package org.broadleafcommerce.core.payment.service.module;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.payment.domain.PaymentResponseItem;
import org.broadleafcommerce.core.payment.service.PaymentContext;
import org.broadleafcommerce.core.payment.service.exception.PaymentException;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PaymentModule {
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
  PaymentResponseItem partialPayment(PaymentContext paymentContext) throws PaymentException;

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
   * @param   paymentContext            DOCUMENT ME!
   * @param   amountToReverseAuthorize  DOCUMENT ME!
   * @param   responseItem              DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem processReverseAuthorize(PaymentContext paymentContext, Money amountToReverseAuthorize,
    PaymentResponseItem responseItem) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext     DOCUMENT ME!
   * @param   amountToAuthorize  DOCUMENT ME!
   * @param   responseItem       DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem processAuthorize(PaymentContext paymentContext, Money amountToAuthorize,
    PaymentResponseItem responseItem) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   * @param   amountToDebit   DOCUMENT ME!
   * @param   responseItem    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem processDebit(PaymentContext paymentContext, Money amountToDebit, PaymentResponseItem responseItem)
    throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   * @param   amountToDebit   DOCUMENT ME!
   * @param   responseItem    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem processAuthorizeAndDebit(PaymentContext paymentContext, Money amountToDebit,
    PaymentResponseItem responseItem) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   * @param   amountToCredit  DOCUMENT ME!
   * @param   responseItem    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem processCredit(PaymentContext paymentContext, Money amountToCredit,
    PaymentResponseItem responseItem) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   * @param   amountToVoid    DOCUMENT ME!
   * @param   responseItem    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem processVoidPayment(PaymentContext paymentContext, Money amountToVoid,
    PaymentResponseItem responseItem) throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   * @param   responseItem    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem processBalance(PaymentContext paymentContext, PaymentResponseItem responseItem)
    throws PaymentException;

  /**
   * DOCUMENT ME!
   *
   * @param   paymentContext  DOCUMENT ME!
   * @param   amountToDebit   DOCUMENT ME!
   * @param   responseItem    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PaymentException  DOCUMENT ME!
   */
  PaymentResponseItem processPartialPayment(PaymentContext paymentContext, Money amountToDebit,
    PaymentResponseItem responseItem) throws PaymentException;
} // end interface PaymentModule
