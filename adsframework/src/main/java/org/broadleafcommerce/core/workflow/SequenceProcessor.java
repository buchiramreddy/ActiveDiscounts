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

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.core.workflow.state.ActivityStateManager;
import org.broadleafcommerce.core.workflow.state.ActivityStateManagerImpl;
import org.broadleafcommerce.core.workflow.state.RollbackStateLocal;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class SequenceProcessor extends BaseProcessor {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(SequenceProcessor.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private ProcessContextFactory processContextFactory;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Processor#doActivities()
   */
  @Override public ProcessContext doActivities() throws WorkflowException {
    return doActivities(null);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Processor#doActivities(java.lang.Object)
   */
  @Override public ProcessContext doActivities(Object seedData) throws WorkflowException {
    if (LOG.isDebugEnabled()) {
      LOG.debug(getBeanName() + " processor is running..");
    }

    ActivityStateManager activityStateManager = ((ActivityStateManager) getBeanFactory().getBean(
          "blActivityStateManager"));

    if (activityStateManager == null) {
      throw new IllegalStateException(
        "Unable to find an instance of ActivityStateManager registered under bean id blActivityStateManager");
    }

    ProcessContext     context            = null;
    RollbackStateLocal rollbackStateLocal = RollbackStateLocal.getRollbackStateLocal();

    if (rollbackStateLocal == null) {
      rollbackStateLocal = new RollbackStateLocal();
      rollbackStateLocal.setThreadId(String.valueOf(Thread.currentThread().getId()));
      rollbackStateLocal.setWorkflowId(getBeanName());
      RollbackStateLocal.setRollbackStateLocal(rollbackStateLocal);
    }

    try {
      // retrieve injected by Spring
      List<Activity<ProcessContext>> activities = getActivities();

      // retrieve a new instance of the Workflow ProcessContext
      context = createContext(seedData);

      for (Activity<ProcessContext> activity : activities) {
        if (activity.shouldExecute(context)) {
          if (LOG.isDebugEnabled()) {
            LOG.debug("running activity:" + activity.getBeanName() + " using arguments:" + context);
          }

          try {
            context = activity.execute(context);
          } catch (Throwable th) {
            if (getAutoRollbackOnError()) {
              LOG.info(
                "Automatically rolling back state for any previously registered RollbackHandlers. RollbackHandlers may be registered for workflow activities in appContext.");
              ActivityStateManagerImpl.getStateManager().rollbackAllState();
            }

            ErrorHandler errorHandler = activity.getErrorHandler();

            if (errorHandler == null) {
              LOG.info("no error handler for this action, run default error" + "handler and abort processing ");
              getDefaultErrorHandler().handleError(context, th);

              break;
            } else {
              LOG.info("run error handler and continue");
              errorHandler.handleError(context, th);
            }
          }

          // ensure its ok to continue the process
          if (processShouldStop(context, activity)) {
            break;
          }

          // register the RollbackHandler
          if ((activity.getRollbackHandler() != null) && activity.getAutomaticallyRegisterRollbackHandler()) {
            ActivityStateManagerImpl.getStateManager().registerState(activity, context, activity.getRollbackRegion(),
              activity.getRollbackHandler(), activity.getStateConfiguration());
          }
        } else {
          if (LOG.isDebugEnabled()) {
            LOG.debug("Not executing activity: " + activity.getBeanName() + " based on the context: " + context);
          }
        } // end if-else
      } // end for
    } finally {
      rollbackStateLocal = RollbackStateLocal.getRollbackStateLocal();

      if ((rollbackStateLocal != null) && rollbackStateLocal.getWorkflowId().equals(getBeanName())) {
        activityStateManager.clearAllState();
        RollbackStateLocal.setRollbackStateLocal(null);
      }
    } // end try-finally

    if (LOG.isDebugEnabled()) {
      LOG.debug(getBeanName() + " processor is done.");
    }

    return context;
  } // end method doActivities

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Processor#setProcessContextFactory(org.broadleafcommerce.core.workflow.ProcessContextFactory)
   */
  @Override public void setProcessContextFactory(ProcessContextFactory processContextFactory) {
    this.processContextFactory = processContextFactory;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /*
   * (non-Javadoc)
   *
   * @see org.iocworkflow.BaseProcessor#supports(java.lang.Class)
   */
  @Override public boolean supports(Activity<? extends ProcessContext> activity) {
    return (activity instanceof BaseActivity);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   seedData  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  WorkflowException  DOCUMENT ME!
   */
  protected ProcessContext createContext(Object seedData) throws WorkflowException {
    return processContextFactory.createContext(seedData);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Determine if the process should stop.
   *
   * @param   context   the current process context
   * @param   activity  the current activity in the iteration
   *
   * @return  determine if the process should stop.
   */
  protected boolean processShouldStop(ProcessContext context, Activity<? extends ProcessContext> activity) {
    if ((context != null) && context.isStopped()) {
      LOG.info("Interrupted workflow as requested by:" + activity.getBeanName());

      return true;
    }

    return false;
  }

} // end class SequenceProcessor
