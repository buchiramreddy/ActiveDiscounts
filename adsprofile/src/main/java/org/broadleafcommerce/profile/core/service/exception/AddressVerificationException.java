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

package org.broadleafcommerce.profile.core.service.exception;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class AddressVerificationException extends Exception {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new AddressVerificationException object.
   */
  public AddressVerificationException() {
    super();
  }

  /**
   * Creates a new AddressVerificationException object.
   *
   * @param  arg0  DOCUMENT ME!
   */
  public AddressVerificationException(String arg0) {
    super(arg0);
  }

  /**
   * Creates a new AddressVerificationException object.
   *
   * @param  arg0  DOCUMENT ME!
   */
  public AddressVerificationException(Throwable arg0) {
    super(arg0);
  }

  /**
   * Creates a new AddressVerificationException object.
   *
   * @param  arg0  DOCUMENT ME!
   * @param  arg1  DOCUMENT ME!
   */
  public AddressVerificationException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

} // end class AddressVerificationException
