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

package org.broadleafcommerce.openadmin.server.service.persistence.module;

/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class FieldNotAvailableException extends Exception {
  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new FieldNotAvailableException object.
   */
  public FieldNotAvailableException() { }

  /**
   * Creates a new FieldNotAvailableException object.
   *
   * @param  s  DOCUMENT ME!
   */
  public FieldNotAvailableException(String s) {
    super(s);
  }

  /**
   * Creates a new FieldNotAvailableException object.
   *
   * @param  throwable  DOCUMENT ME!
   */
  public FieldNotAvailableException(Throwable throwable) {
    super(throwable);
  }

  /**
   * Creates a new FieldNotAvailableException object.
   *
   * @param  s          DOCUMENT ME!
   * @param  throwable  DOCUMENT ME!
   */
  public FieldNotAvailableException(String s, Throwable throwable) {
    super(s, throwable);
  }

} // end class FieldNotAvailableException
