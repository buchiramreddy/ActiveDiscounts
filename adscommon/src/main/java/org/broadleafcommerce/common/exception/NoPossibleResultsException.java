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
 * The admin will throw this exception when a query is attempted across multiple class hierarchies because it is
 * impossible for such a query to produce any results.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class NoPossibleResultsException extends RuntimeException {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 2422275745139590462L;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new NoPossibleResultsException object.
   *
   * @param  message  DOCUMENT ME!
   */
  public NoPossibleResultsException(String message) {
    super(message);
  }

  /**
   * Creates a new NoPossibleResultsException object.
   *
   * @param  cause  DOCUMENT ME!
   */
  public NoPossibleResultsException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new NoPossibleResultsException object.
   *
   * @param  message  DOCUMENT ME!
   * @param  cause    DOCUMENT ME!
   */
  public NoPossibleResultsException(String message, Throwable cause) {
    super(message, cause);
  }

  // for serialization purposes
  /**
   * Creates a new NoPossibleResultsException object.
   */
  protected NoPossibleResultsException() {
    super();
  }

} // end class NoPossibleResultsException
