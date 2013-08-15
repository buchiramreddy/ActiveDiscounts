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

import org.springframework.core.Ordered;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public abstract class BaseActivity<T extends ProcessContext> implements Activity<T> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected boolean automaticallyRegisterRollbackHandler = false;

  /** DOCUMENT ME! */
  protected String  beanName;

  /** DOCUMENT ME! */
  protected ErrorHandler errorHandler;

  /** DOCUMENT ME! */
  protected int          order = Ordered.LOWEST_PRECEDENCE;

  /** DOCUMENT ME! */
  protected RollbackHandler     rollbackHandler;

  /** DOCUMENT ME! */
  protected String              rollbackRegion;

  /** DOCUMENT ME! */
  protected Map<String, Object> stateConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#getAutomaticallyRegisterRollbackHandler()
   */
  @Override public boolean getAutomaticallyRegisterRollbackHandler() {
    return automaticallyRegisterRollbackHandler;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#getBeanName()
   */
  @Override public String getBeanName() {
    return beanName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#getErrorHandler()
   */
  @Override public ErrorHandler getErrorHandler() {
    return errorHandler;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.core.Ordered#getOrder()
   */
  @Override public int getOrder() {
    return order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#getRollbackHandler()
   */
  @Override public RollbackHandler getRollbackHandler() {
    return rollbackHandler;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#getRollbackRegion()
   */
  @Override public String getRollbackRegion() {
    return rollbackRegion;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#getStateConfiguration()
   */
  @Override public Map<String, Object> getStateConfiguration() {
    return stateConfiguration;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#setAutomaticallyRegisterRollbackHandler(boolean)
   */
  @Override public void setAutomaticallyRegisterRollbackHandler(boolean automaticallyRegisterRollbackHandler) {
    this.automaticallyRegisterRollbackHandler = automaticallyRegisterRollbackHandler;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.beans.factory.BeanNameAware#setBeanName(java.lang.String)
   */
  @Override public void setBeanName(final String beanName) {
    this.beanName = beanName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#setErrorHandler(org.broadleafcommerce.core.workflow.ErrorHandler)
   */
  @Override public void setErrorHandler(final ErrorHandler errorHandler) {
    this.errorHandler = errorHandler;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  public void setOrder(int order) {
    this.order = order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#setRollbackHandler(org.broadleafcommerce.core.workflow.state.RollbackHandler)
   */
  @Override public void setRollbackHandler(RollbackHandler rollbackHandler) {
    this.rollbackHandler = rollbackHandler;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#setRollbackRegion(java.lang.String)
   */
  @Override public void setRollbackRegion(String rollbackRegion) {
    this.rollbackRegion = rollbackRegion;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#setStateConfiguration(java.util.Map)
   */
  @Override public void setStateConfiguration(Map<String, Object> stateConfiguration) {
    this.stateConfiguration = stateConfiguration;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Activity#shouldExecute(org.broadleafcommerce.core.workflow.ProcessContext)
   */
  @Override public boolean shouldExecute(T context) {
    return true;
  }

} // end class BaseActivity
