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

package org.broadleafcommerce.profile.core.service.validator;

import org.springframework.validation.BindException;
import org.springframework.validation.Errors;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class BaseResponse {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Errors errors;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new BaseResponse object.
   *
   * @param  target      DOCUMENT ME!
   * @param  objectName  DOCUMENT ME!
   */
  public BaseResponse(Object target, String objectName) {
    errors = new BindException(target, objectName);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Errors getErrors() {
    return errors;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean hasErrors() {
    return errors.hasErrors();
  }
} // end class BaseResponse
