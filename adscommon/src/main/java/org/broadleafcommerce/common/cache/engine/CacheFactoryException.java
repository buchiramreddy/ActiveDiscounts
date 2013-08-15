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

package org.broadleafcommerce.common.cache.engine;

/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class CacheFactoryException extends Exception {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new CacheFactoryException object.
   */
  public CacheFactoryException() {
    super();
  }

  /**
   * Creates a new CacheFactoryException object.
   *
   * @param  message  DOCUMENT ME!
   */
  public CacheFactoryException(String message) {
    super(message);
  }

  /**
   * Creates a new CacheFactoryException object.
   *
   * @param  cause  DOCUMENT ME!
   */
  public CacheFactoryException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new CacheFactoryException object.
   *
   * @param  message  DOCUMENT ME!
   * @param  cause    DOCUMENT ME!
   */
  public CacheFactoryException(String message, Throwable cause) {
    super(message, cause);
  }

} // end class CacheFactoryException
