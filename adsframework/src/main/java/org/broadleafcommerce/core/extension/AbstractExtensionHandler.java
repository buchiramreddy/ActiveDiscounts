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
 * Base {@link org.broadleafcommerce.core.extension.ExtensionHandler} class that provide basic extension handler
 * properties including priority (which drives the execution order of handlers) and enabled (which if false informs the
 * manager to skip this handler).
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public abstract class AbstractExtensionHandler implements ExtensionHandler {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private boolean enabled = true;

  private int priority;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Determines the priority of this extension handler.
   *
   * @return  determines the priority of this extension handler.
   */
  @Override public int getPriority() {
    return priority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.extension.ExtensionHandler#isEnabled()
   */
  @Override public boolean isEnabled() {
    return enabled;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  enabled  DOCUMENT ME!
   */
  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  priority  DOCUMENT ME!
   */
  public void setPriority(int priority) {
    this.priority = priority;
  }
} // end class AbstractExtensionHandler
