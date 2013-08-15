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

package org.broadleafcommerce.core.workflow;

import java.util.Map;

import org.broadleafcommerce.core.workflow.state.RollbackHandler;

import org.springframework.beans.factory.BeanNameAware;

import org.springframework.core.Ordered;


/**
 * <p>Interface to be used for workflows in Broadleaf. Usually implementations will subclass
 * {@link org.broadleafcommerce.core.workflow.BaseActivity}.</p>
 *
 * <p>Important note: if you are writing a 3rd-party integration module or adding a module outside of the Broadleaf
 * core, your activity should implement the {@link org.broadleafcommerce.core.workflow.ModuleActivity} interface as
 * well. This ensures that there is proper logging for users that are using your module so that they know exactly what
 * their final workflow configuration looks like.</p>
 *
 * @author   Phillip Verheyden (phillipuniverse)
 *
 * @param    <T>
 *
 * @see      {@link org.broadleafcommerce.core.workflow.BaseActivity}
 * @see      {@link org.broadleafcommerce.core.workflow.ModuleActivity}
 * @see      {@link org.broadleafcommerce.core.workflow.BaseProcessor}
 * @see      {@link org.broadleafcommerce.core.workflow.SequenceProcessor}
 * @version  $Revision$, $Date$
 */
public interface Activity<T extends ProcessContext> extends BeanNameAware, Ordered {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Called by the encompassing processor to activate the execution of the Activity.
   *
   * @param   context  - process context for this workflow
   *
   * @return  resulting process context
   *
   * @throws  Exception
   */
  T execute(T context) throws Exception;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Whether or not this activity should automatically register a configured RollbackHandler with the
   * ActivityStateManager. It is useful to adjust this value if you plan on using the ActivityStateManager API to
   * register RollbackHandlers explicitly in your code. The default value is true.
   *
   * @return  Whether or not to automatically register a RollbackHandler with the ActivityStateManager
   */
  boolean getAutomaticallyRegisterRollbackHandler();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getBeanName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Get the fine-grained error handler wired up for this Activity.
   *
   * @return  get the fine-grained error handler wired up for this Activity.
   */
  ErrorHandler getErrorHandler();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Retrieve the RollbackHandler instance that should be called by the ActivityStateManager in the event of a workflow
   * execution problem. This RollbackHandler will presumably perform some compensating operation to revert state for the
   * activity.
   *
   * @return  the handler responsible for reverting state for the activity
   */
  RollbackHandler getRollbackHandler();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Retrieve the optional region label for the RollbackHandler. Setting a region allows partitioning of groups of
   * RollbackHandlers for more fine grained control of rollback behavior. Explicit calls to the ActivityStateManager API
   * in an ErrorHandler instance allows explicit rollback of specific rollback handler regions. Note, to disable
   * automatic rollback behavior and enable explicit rollbacks via the API, the workflow.auto.rollback.on.error property
   * should be set to false in your implementation's runtime property configuration.
   *
   * @return  the rollback region label for the RollbackHandler instance
   */
  String getRollbackRegion();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Retrieve any user-defined state that should accompany the RollbackHandler. This configuration will be passed to the
   * RollbackHandler implementation at runtime.
   *
   * @return  any user-defined state configuratio necessary for the execution of the RollbackHandler
   */
  Map<String, Object> getStateConfiguration();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Whether or not this activity should automatically register a configured RollbackHandler with the
   * ActivityStateManager. It is useful to adjust this value if you plan on using the ActivityStateManager API to
   * register RollbackHandlers explicitly in your code. The default value is true.
   *
   * @param  automaticallyRegisterRollbackHandler  Whether or not to automatically register a RollbackHandler with the
   *                                               ActivityStateManager
   */
  void setAutomaticallyRegisterRollbackHandler(boolean automaticallyRegisterRollbackHandler);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  errorHandler  DOCUMENT ME!
   */
  void setErrorHandler(final ErrorHandler errorHandler);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Set the RollbackHandler instance that should be called by the ActivityStateManager in the event of a workflow
   * execution problem. This RollbackHandler will presumably perform some compensating operation to revert state for the
   * activity.
   *
   * @param  rollbackHandler  the handler responsible for reverting state for the activity
   */
  void setRollbackHandler(RollbackHandler rollbackHandler);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Set the optional region label for the RollbackHandler. Setting a region allows partitioning of groups of
   * RollbackHandlers for more fine grained control of rollback behavior. Explicit calls to the ActivityStateManager API
   * in an ErrorHandler instance allows explicit rollback of specific rollback handler regions. Note, to disable
   * automatic rollback behavior and enable explicit rollbacks via the API, the workflow.auto.rollback.on.error property
   * should be set to false in your implementation's runtime property configuration.
   *
   * @param  rollbackRegion  the rollback region label for the RollbackHandler instance
   */
  void setRollbackRegion(String rollbackRegion);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Set any user-defined state that should accompany the RollbackHandler. This configuration will be passed to the
   * RollbackHandler implementation at runtime.
   *
   * @param  stateConfiguration  any user-defined state configuration necessary for the execution of the RollbackHandler
   */
  void setStateConfiguration(Map<String, Object> stateConfiguration);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Determines if an activity should execute based on the current values in the
   * {@link org.broadleafcommerce.core.workflow.ProcessContext}. For example, a context might have both an
   * {@link org.broadleafcommerce.core.order.domain.Order} as well as a String 'status' of what the order should be
   * changed to. It is possible that an activity in a workflow could only deal with a particular status change, and thus
   * could return false from this method.
   *
   * @param   context  DOCUMENT ME!
   *
   * @return  determines if an activity should execute based on the current values in the
   *          {@link org.broadleafcommerce.core.workflow.ProcessContext}.
   */
  boolean shouldExecute(T context);
} // end interface Activity
