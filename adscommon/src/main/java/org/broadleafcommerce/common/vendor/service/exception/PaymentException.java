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

package org.broadleafcommerce.common.vendor.service.exception;

import org.broadleafcommerce.common.exception.BroadleafException;
import org.broadleafcommerce.common.vendor.service.message.PaymentResponse;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PaymentException extends BroadleafException {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected PaymentResponse paymentResponse;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new PaymentException object.
   */
  public PaymentException() {
    super();
  }

  /**
   * Creates a new PaymentException object.
   *
   * @param  message  DOCUMENT ME!
   */
  public PaymentException(String message) {
    super(message);
  }

  /**
   * Creates a new PaymentException object.
   *
   * @param  cause  DOCUMENT ME!
   */
  public PaymentException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new PaymentException object.
   *
   * @param  message  DOCUMENT ME!
   * @param  cause    DOCUMENT ME!
   */
  public PaymentException(String message, Throwable cause) {
    super(message, cause);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PaymentResponse getPaymentResponse() {
    return paymentResponse;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  paymentResponse  DOCUMENT ME!
   */
  public void setPaymentResponse(PaymentResponse paymentResponse) {
    this.paymentResponse = paymentResponse;
  }
} // end class PaymentException
