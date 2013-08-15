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

package org.broadleafcommerce.core.order.service.exception;

/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class RemoveFromCartException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Creates a new RemoveFromCartException object.
   */
  public RemoveFromCartException() {
    super();
  }

  /**
   * Creates a new RemoveFromCartException object.
   *
   * @param  message  DOCUMENT ME!
   * @param  cause    DOCUMENT ME!
   */
  public RemoveFromCartException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Creates a new RemoveFromCartException object.
   *
   * @param  message  DOCUMENT ME!
   */
  public RemoveFromCartException(String message) {
    super(message);
  }

  /**
   * Creates a new RemoveFromCartException object.
   *
   * @param  cause  DOCUMENT ME!
   */
  public RemoveFromCartException(Throwable cause) {
    super(cause);
  }

} // end class RemoveFromCartException
