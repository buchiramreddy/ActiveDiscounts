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


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PaymentHostException extends PaymentException {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new PaymentHostException object.
   */
  public PaymentHostException() {
    super();
  }

  /**
   * Creates a new PaymentHostException object.
   *
   * @param  message  DOCUMENT ME!
   */
  public PaymentHostException(String message) {
    super(message);
  }

  /**
   * Creates a new PaymentHostException object.
   *
   * @param  cause  DOCUMENT ME!
   */
  public PaymentHostException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new PaymentHostException object.
   *
   * @param  message  DOCUMENT ME!
   * @param  cause    DOCUMENT ME!
   */
  public PaymentHostException(String message, Throwable cause) {
    super(message, cause);
  }

} // end class PaymentHostException
