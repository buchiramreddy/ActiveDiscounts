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

package org.broadleafcommerce.openadmin.server.service.persistence;

/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class PersistenceException extends RuntimeException {
  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new PersistenceException object.
   */
  public PersistenceException() {
    super();
  }

  /**
   * Creates a new PersistenceException object.
   *
   * @param  cause  DOCUMENT ME!
   */
  public PersistenceException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new PersistenceException object.
   *
   * @param  message  DOCUMENT ME!
   */
  public PersistenceException(String message) {
    super(message);
  }

  /**
   * Creates a new PersistenceException object.
   *
   * @param  message  DOCUMENT ME!
   * @param  cause    DOCUMENT ME!
   */
  public PersistenceException(String message, Throwable cause) {
    super(message, cause);
  }
} // end class PersistenceException
