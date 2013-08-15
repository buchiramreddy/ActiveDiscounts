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

package org.broadleafcommerce.core.offer.service.exception;

import org.broadleafcommerce.core.checkout.service.exception.CheckoutException;
import org.broadleafcommerce.core.checkout.service.workflow.CheckoutSeed;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class OfferMaxUseExceededException extends CheckoutException {
  private static final long serialVersionUID = 1L;

  /**
   * Creates a new OfferMaxUseExceededException object.
   */
  public OfferMaxUseExceededException() {
    super();
  }

  /**
   * Creates a new OfferMaxUseExceededException object.
   *
   * @param  message  DOCUMENT ME!
   */
  public OfferMaxUseExceededException(String message) {
    super(message, null);
  }

  /**
   * Creates a new OfferMaxUseExceededException object.
   *
   * @param  message  DOCUMENT ME!
   * @param  cause    DOCUMENT ME!
   * @param  seed     DOCUMENT ME!
   */
  public OfferMaxUseExceededException(String message, Throwable cause, CheckoutSeed seed) {
    super(message, cause, seed);
  }

  /**
   * Creates a new OfferMaxUseExceededException object.
   *
   * @param  message  DOCUMENT ME!
   * @param  seed     DOCUMENT ME!
   */
  public OfferMaxUseExceededException(String message, CheckoutSeed seed) {
    super(message, seed);
  }

  /**
   * Creates a new OfferMaxUseExceededException object.
   *
   * @param  cause  DOCUMENT ME!
   * @param  seed   DOCUMENT ME!
   */
  public OfferMaxUseExceededException(Throwable cause, CheckoutSeed seed) {
    super(cause, seed);
  }

} // end class OfferMaxUseExceededException
