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

package org.broadleafcommerce.core.payment.service.exception;

import org.broadleafcommerce.core.payment.domain.PaymentResponseItem;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PaymentProcessorException extends PaymentException {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  protected PaymentResponseItem paymentResponseItem;

  /**
   * Creates a new PaymentProcessorException object.
   *
   * @param  paymentResponseItem  DOCUMENT ME!
   */
  public PaymentProcessorException(PaymentResponseItem paymentResponseItem) {
    super();
    this.paymentResponseItem = paymentResponseItem;
  }

  /**
   * Creates a new PaymentProcessorException object.
   *
   * @param  message              DOCUMENT ME!
   * @param  cause                DOCUMENT ME!
   * @param  paymentResponseItem  DOCUMENT ME!
   */
  public PaymentProcessorException(String message, Throwable cause, PaymentResponseItem paymentResponseItem) {
    super(message, cause);
    this.paymentResponseItem = paymentResponseItem;
  }

  /**
   * Creates a new PaymentProcessorException object.
   *
   * @param  message              DOCUMENT ME!
   * @param  paymentResponseItem  DOCUMENT ME!
   */
  public PaymentProcessorException(String message, PaymentResponseItem paymentResponseItem) {
    super(message);
    this.paymentResponseItem = paymentResponseItem;
  }

  /**
   * Creates a new PaymentProcessorException object.
   *
   * @param  cause                DOCUMENT ME!
   * @param  paymentResponseItem  DOCUMENT ME!
   */
  public PaymentProcessorException(Throwable cause, PaymentResponseItem paymentResponseItem) {
    super(cause);
    this.paymentResponseItem = paymentResponseItem;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PaymentResponseItem getPaymentResponseItem() {
    return paymentResponseItem;
  }

} // end class PaymentProcessorException
