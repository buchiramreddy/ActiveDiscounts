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

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.stereotype.Component;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Component("blDefaultErrorHandler")
public class DefaultErrorHandler implements ErrorHandler {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(DefaultErrorHandler.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected List<String> unloggedExceptionClasses = new ArrayList<String>();
  @SuppressWarnings("unused")
  private String         name;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<String> getUnloggedExceptionClasses() {
    return unloggedExceptionClasses;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.workflow.ErrorHandler#handleError(org.broadleafcommerce.core.workflow.ProcessContext, java.lang.Throwable)
   */
  @Override public void handleError(ProcessContext context, Throwable th) throws WorkflowException {
    context.stopProcess();

    boolean   shouldLog = true;
    Throwable cause     = th;

    while (true) {
      if (unloggedExceptionClasses.contains(cause.getClass().getName())) {
        shouldLog = false;

        break;
      }

      cause = cause.getCause();

      if (cause == null) {
        break;
      }
    }

    if (shouldLog) {
      LOG.error("An error occurred during the workflow", th);
    }

    throw new WorkflowException(th);
  } // end method handleError

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
   */
  @Override public void setBeanName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  unloggedExceptionClasses  DOCUMENT ME!
   */
  public void setUnloggedExceptionClasses(List<String> unloggedExceptionClasses) {
    this.unloggedExceptionClasses = unloggedExceptionClasses;
  }

} // end class DefaultErrorHandler
