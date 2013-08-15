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
 * Exception thrown when a {@link EntityService service} method fails.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class ServiceException extends Exception {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = -7084792578727995587L;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new ServiceException object.
   *
   * @param  message  DOCUMENT ME!
   */
  public ServiceException(String message) {
    super(message);
  }

  /**
   * Creates a new ServiceException object.
   *
   * @param  cause  DOCUMENT ME!
   */
  public ServiceException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new ServiceException object.
   *
   * @param  message  DOCUMENT ME!
   * @param  cause    DOCUMENT ME!
   */
  public ServiceException(String message, Throwable cause) {
    super(message, cause);
  }

  // for serialization purposes
  /**
   * Creates a new ServiceException object.
   */
  protected ServiceException() {
    super();
  }

} // end class ServiceException
