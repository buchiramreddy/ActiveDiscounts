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

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * Form of ServiceException thrown when their is a security failure while attempting to execute the operation.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@ResponseStatus(
  value  = HttpStatus.FORBIDDEN,
  reason = "Access is denied"
)
public class SecurityServiceException extends ServiceException {
  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new SecurityServiceException object.
   */
  public SecurityServiceException() {
    super();
  }

  /**
   * Creates a new SecurityServiceException object.
   *
   * @param  cause  DOCUMENT ME!
   */
  public SecurityServiceException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new SecurityServiceException object.
   *
   * @param  message  DOCUMENT ME!
   */
  public SecurityServiceException(String message) {
    super(message);
  }

  /**
   * Creates a new SecurityServiceException object.
   *
   * @param  message  DOCUMENT ME!
   * @param  cause    DOCUMENT ME!
   */
  public SecurityServiceException(String message, Throwable cause) {
    super(message, cause);
  }
} // end class SecurityServiceException
