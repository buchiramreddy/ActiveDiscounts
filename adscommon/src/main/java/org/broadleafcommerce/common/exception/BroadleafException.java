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

package org.broadleafcommerce.common.exception;

/**
 * Base exception class for BroadleafExceptions that understands root cause messages.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public abstract class BroadleafException extends Exception implements RootCauseAccessor {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Throwable rootCause;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new BroadleafException object.
   */
  public BroadleafException() {
    super();
  }

  /**
   * Creates a new BroadleafException object.
   *
   * @param  message  DOCUMENT ME!
   */
  public BroadleafException(String message) {
    super(message);
    this.rootCause = this;

  }

  /**
   * Creates a new BroadleafException object.
   *
   * @param  cause  DOCUMENT ME!
   */
  public BroadleafException(Throwable cause) {
    super(cause);

    if (cause != null) {
      rootCause = findRootCause(cause);
    }
  }

  /**
   * Creates a new BroadleafException object.
   *
   * @param  message  DOCUMENT ME!
   * @param  cause    DOCUMENT ME!
   */
  public BroadleafException(String message, Throwable cause) {
    super(message, cause);

    if (cause != null) {
      rootCause = findRootCause(cause);
    } else {
      rootCause = this;
    }
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.exception.RootCauseAccessor#getRootCause()
   */
  @Override public Throwable getRootCause() {
    return rootCause;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.exception.RootCauseAccessor#getRootCauseMessage()
   */
  @Override public String getRootCauseMessage() {
    if (rootCause != null) {
      return rootCause.getMessage();
    } else {
      return getMessage();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  private Throwable findRootCause(Throwable cause) {
    Throwable rootCause = cause;

    while ((rootCause != null) && (rootCause.getCause() != null)) {
      rootCause = rootCause.getCause();
    }

    return rootCause;
  }

} // end class BroadleafException
