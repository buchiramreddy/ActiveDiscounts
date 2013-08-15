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

package org.broadleafcommerce.core.workflow.state;

/**
 * Handles the identification of the outermost workflow and the current thread so that the StateManager can operate on
 * the appropriate RollbackHandlers.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class RollbackStateLocal {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final ThreadLocal<RollbackStateLocal> THREAD_LOCAL = new ThreadLocal<RollbackStateLocal>();

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String threadId;
  private String workflowId;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static RollbackStateLocal getRollbackStateLocal() {
    return THREAD_LOCAL.get();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  rollbackStateLocal  DOCUMENT ME!
   */
  public static void setRollbackStateLocal(RollbackStateLocal rollbackStateLocal) {
    THREAD_LOCAL.set(rollbackStateLocal);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getThreadId() {
    return threadId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getWorkflowId() {
    return workflowId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  threadId  DOCUMENT ME!
   */
  public void setThreadId(String threadId) {
    this.threadId = threadId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  workflowId  DOCUMENT ME!
   */
  public void setWorkflowId(String workflowId) {
    this.workflowId = workflowId;
  }
} // end class RollbackStateLocal
