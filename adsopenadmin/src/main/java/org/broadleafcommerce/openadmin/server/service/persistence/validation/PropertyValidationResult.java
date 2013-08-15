/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.server.service.persistence.validation;


/**
 * DTO representing a boolean whether or not it passed validation and String error message. An error message is not
 * required if the result is not an error
 *
 * @author   Phillip Verheyden (phillipuniverse)
 * @see      {@link org.broadleafcommerce.openadmin.server.service.persistence.validation.PropertyValidator}
 * @version  $Revision$, $Date$
 */
public class PropertyValidationResult {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String errorMessage;

  /** DOCUMENT ME! */
  protected boolean valid;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new PropertyValidationResult object.
   *
   * @param  valid  DOCUMENT ME!
   */
  public PropertyValidationResult(boolean valid) {
    setValid(valid);
  }

  /**
   * Creates a new PropertyValidationResult object.
   *
   * @param  valid         DOCUMENT ME!
   * @param  errorMessage  DOCUMENT ME!
   */
  public PropertyValidationResult(boolean valid, String errorMessage) {
    setValid(valid);
    setErrorMessage(errorMessage);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The error message (or key in a message bundle) for the validation failure.
   *
   * @return  the error message (or key in a message bundle) for the validation failure
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Whether or not this property passed validation.
   *
   * @return  Whether or not this property passed validation.
   */
  public boolean isValid() {
    return valid;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the error message (or key in a message bundle) for the validation failure. If you have some sort of custom
   * error message for the validation failure it should be set here
   *
   * @param  errorMessage  DOCUMENT ME!
   */
  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Set the validation result for this property.
   *
   * @param  valid  DOCUMENT ME!
   */
  public void setValid(boolean valid) {
    this.valid = valid;
  }

} // end class PropertyValidationResult
