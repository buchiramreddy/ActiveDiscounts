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
public class DefaultModule extends AbstractModule {
  /**
   * @see  org.broadleafcommerce.core.payment.service.module.PaymentModule#processAuthorize(org.broadleafcommerce.core.payment.service.PaymentContext,
   *       org.broadleafcommerce.common.money.Money, org.broadleafcommerce.core.payment.domain.PaymentResponseItem)
   */
  @Override public PaymentResponseItem processAuthorize(PaymentContext paymentContext, Money amountToAuthorize,
    PaymentResponseItem responseItem) throws PaymentException {
    throw new PaymentException("authorize not implemented.");
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.module.PaymentModule#processReverseAuthorize(org.broadleafcommerce.core.payment.service.PaymentContext,
   *       org.broadleafcommerce.common.money.Money, org.broadleafcommerce.core.payment.domain.PaymentResponseItem)
   */
  @Override public PaymentResponseItem processReverseAuthorize(PaymentContext paymentContext,
    Money amountToReverseAuthorize, PaymentResponseItem responseItem) throws PaymentException {
    throw new PaymentException("reverse authorize not implemented.");
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.module.PaymentModule#processAuthorizeAndDebit(org.broadleafcommerce.core.payment.service.PaymentContext,
   *       org.broadleafcommerce.common.money.Money, org.broadleafcommerce.core.payment.domain.PaymentResponseItem)
   */
  @Override public PaymentResponseItem processAuthorizeAndDebit(PaymentContext paymentContext,
    Money amountToReverseAuthorize, PaymentResponseItem responseItem) throws PaymentException {
    throw new PaymentException("authorizeAndDebit not implemented.");
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.module.PaymentModule#processDebit(org.broadleafcommerce.core.payment.service.PaymentContext,
   *       org.broadleafcommerce.common.money.Money, org.broadleafcommerce.core.payment.domain.PaymentResponseItem)
   */
  @Override public PaymentResponseItem processDebit(PaymentContext paymentContext, Money amountToReverseAuthorize,
    PaymentResponseItem responseItem) throws PaymentException {
    throw new PaymentException("debit not implemented.");
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.module.PaymentModule#processCredit(org.broadleafcommerce.core.payment.service.PaymentContext,
   *       org.broadleafcommerce.common.money.Money, org.broadleafcommerce.core.payment.domain.PaymentResponseItem)
   */
  @Override public PaymentResponseItem processCredit(PaymentContext paymentContext, Money amountToReverseAuthorize,
    PaymentResponseItem responseItem) throws PaymentException {
    throw new PaymentException("credit not implemented.");
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.module.PaymentModule#processVoidPayment(org.broadleafcommerce.core.payment.service.PaymentContext,
   *       org.broadleafcommerce.common.money.Money, org.broadleafcommerce.core.payment.domain.PaymentResponseItem)
   */
  @Override public PaymentResponseItem processVoidPayment(PaymentContext paymentContext, Money amountToReverseAuthorize,
    PaymentResponseItem responseItem) throws PaymentException {
    throw new PaymentException("voidPayment not implemented.");
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.module.PaymentModule#processBalance(org.broadleafcommerce.core.payment.service.PaymentContext,
   *       org.broadleafcommerce.core.payment.domain.PaymentResponseItem)
   */
  @Override public PaymentResponseItem processBalance(PaymentContext paymentContext, PaymentResponseItem responseItem)
    throws PaymentException {
    throw new PaymentException("balance not implemented.");
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.module.PaymentModule#processPartialPayment(org.broadleafcommerce.core.payment.service.PaymentContext,
   *       org.broadleafcommerce.common.money.Money, org.broadleafcommerce.core.payment.domain.PaymentResponseItem)
   */
  @Override public PaymentResponseItem processPartialPayment(PaymentContext paymentContext, Money amountToDebit,
    PaymentResponseItem responseItem) throws PaymentException {
    throw new PaymentException("partial payment not implemented.");
  }

  /**
   * @see  org.broadleafcommerce.core.payment.service.module.PaymentModule#isValidCandidate(org.broadleafcommerce.core.payment.service.type.PaymentInfoType)
   */
  @Override public Boolean isValidCandidate(PaymentInfoType paymentType) {
    return false;
  }

} // end class DefaultModule
