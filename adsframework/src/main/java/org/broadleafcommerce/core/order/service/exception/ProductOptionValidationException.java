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

package org.broadleafcommerce.core.order.service.exception;

/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class ProductOptionValidationException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private String            errorCode;

  /**
   * Creates a new ProductOptionValidationException object.
   */
  public ProductOptionValidationException() {
    super();
  }

  /**
   * Creates a new ProductOptionValidationException object.
   *
   * @param  message    DOCUMENT ME!
   * @param  errorCode  DOCUMENT ME!
   * @param  cause      DOCUMENT ME!
   */
  public ProductOptionValidationException(String message, String errorCode, Throwable cause) {
    super(message, cause);
    this.setErrorCode(errorCode);
  }

  /**
   * Creates a new ProductOptionValidationException object.
   *
   * @param  message    DOCUMENT ME!
   * @param  errorCode  DOCUMENT ME!
   */
  public ProductOptionValidationException(String message, String errorCode) {
    super(message);
    this.setErrorCode(errorCode);
  }

  /**
   * Creates a new ProductOptionValidationException object.
   *
   * @param  cause  DOCUMENT ME!
   */
  public ProductOptionValidationException(Throwable cause) {
    super(cause);
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getErrorCode() {
    return errorCode;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  errorCode  DOCUMENT ME!
   */
  protected void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

} // end class ProductOptionValidationException
