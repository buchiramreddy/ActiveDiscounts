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

package org.broadleafcommerce.common.security.service;

import org.broadleafcommerce.common.exception.ServiceException;

import org.owasp.validator.html.CleanResults;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class CleanStringException extends ServiceException {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected CleanResults cleanResults;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new CleanStringException object.
   *
   * @param  cleanResults  DOCUMENT ME!
   */
  public CleanStringException(CleanResults cleanResults) {
    this.cleanResults = cleanResults;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public CleanResults getCleanResults() {
    return cleanResults;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  cleanResults  DOCUMENT ME!
   */
  public void setCleanResults(CleanResults cleanResults) {
    this.cleanResults = cleanResults;
  }
} // end class CleanStringException
