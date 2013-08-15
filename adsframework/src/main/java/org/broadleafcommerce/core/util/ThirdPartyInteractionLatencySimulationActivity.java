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

package org.broadleafcommerce.core.util;

import org.broadleafcommerce.core.workflow.BaseActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;


/**
 * Simple workflow activity to simulate an amount of latency introduced by communicating with a third party provider
 * (e.g. credit card processing). Useful for load testing.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class ThirdPartyInteractionLatencySimulationActivity extends BaseActivity<ProcessContext> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private long waitTime = 1000L;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#execute(org.broadleafcommerce.core.workflow.ProcessContext)
   */
  @Override public ProcessContext execute(ProcessContext context) throws Exception {
    try {
      Thread.sleep(waitTime);
    } catch (Throwable e) {
      // do nothing
    }

    return context;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public long getWaitTime() {
    return waitTime;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  waitTime  DOCUMENT ME!
   */
  public void setWaitTime(long waitTime) {
    this.waitTime = waitTime;
  }
} // end class ThirdPartyInteractionLatencySimulationActivity
