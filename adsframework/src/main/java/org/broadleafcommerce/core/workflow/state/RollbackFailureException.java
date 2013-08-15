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

import java.util.HashMap;

import org.broadleafcommerce.core.workflow.Activity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.broadleafcommerce.core.workflow.WorkflowException;


/**
 * This exception is thrown to indicate a problem while trying to rollback state for any and all activities during a
 * failed workflow. Only those activities that register their state with the ProcessContext will have their state rolled
 * back.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class RollbackFailureException extends WorkflowException {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Activity<? extends ProcessContext> activity;
  private ProcessContext                     processContext;
  private HashMap<String, ?>                 stateItems;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new RollbackFailureException object.
   */
  public RollbackFailureException() { }

  /**
   * Creates a new RollbackFailureException object.
   *
   * @param  cause  DOCUMENT ME!
   */
  public RollbackFailureException(Throwable cause) {
    super(cause);
  }

  /**
   * Creates a new RollbackFailureException object.
   *
   * @param  message  DOCUMENT ME!
   */
  public RollbackFailureException(String message) {
    super(message);
  }

  /**
   * Creates a new RollbackFailureException object.
   *
   * @param  message  DOCUMENT ME!
   * @param  cause    DOCUMENT ME!
   */
  public RollbackFailureException(String message, Throwable cause) {
    super(message, cause);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Activity<? extends ProcessContext> getActivity() {
    return activity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ProcessContext getProcessContext() {
    return processContext;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public HashMap<String, ?> getStateItems() {
    return stateItems;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  activity  DOCUMENT ME!
   */
  public void setActivity(Activity<? extends ProcessContext> activity) {
    this.activity = activity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  processContext  DOCUMENT ME!
   */
  public void setProcessContext(ProcessContext processContext) {
    this.processContext = processContext;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  stateItems  DOCUMENT ME!
   */
  public void setStateItems(HashMap<String, ?> stateItems) {
    this.stateItems = stateItems;
  }
} // end class RollbackFailureException
