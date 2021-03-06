/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.extension;

/**
 * If a service extension using the {@link org.broadleafcommerce.core.extension.ExtensionManager} pattern expects a
 * result from the extension, it should pass in an instance of this class into the method call.
 *
 * <p>The extension points can examine or update this class with response information.</p>
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class ExtensionResultHolder {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  Object    result;

  /** DOCUMENT ME! */
  Throwable throwable;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Object getResult() {
    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Throwable getThrowable() {
    return throwable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  result  DOCUMENT ME!
   */
  public void setResult(Object result) {
    this.result = result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  throwable  DOCUMENT ME!
   */
  public void setThrowable(Throwable throwable) {
    this.throwable = throwable;
  }
} // end class ExtensionResultHolder
