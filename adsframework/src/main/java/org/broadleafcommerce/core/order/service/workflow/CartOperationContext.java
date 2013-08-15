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

package org.broadleafcommerce.core.order.service.workflow;

import org.broadleafcommerce.core.workflow.ProcessContext;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class CartOperationContext implements ProcessContext {
  /** DOCUMENT ME! */
  public static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  protected boolean              stopEntireProcess = false;

  /** DOCUMENT ME! */
  protected CartOperationRequest seedData;

  /**
   * @see  org.broadleafcommerce.core.workflow.ProcessContext#setSeedData(java.lang.Object)
   */
  @Override public void setSeedData(Object seedObject) {
    seedData = (CartOperationRequest) seedObject;
  }

  /**
   * @see  org.broadleafcommerce.core.workflow.ProcessContext#stopProcess()
   */
  @Override public boolean stopProcess() {
    this.stopEntireProcess = true;

    return stopEntireProcess;
  }

  /**
   * @see  org.broadleafcommerce.core.workflow.ProcessContext#isStopped()
   */
  @Override public boolean isStopped() {
    return stopEntireProcess;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public CartOperationRequest getSeedData() {
    return seedData;
  }

} // end class CartOperationContext
