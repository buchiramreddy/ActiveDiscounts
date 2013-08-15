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
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Transformer;

import org.broadleafcommerce.common.logging.LifeCycleEvent;
import org.broadleafcommerce.common.logging.SupportLogManager;
import org.broadleafcommerce.common.logging.SupportLogger;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import org.springframework.core.OrderComparator;


/**
 * Base class for all Workflow Processors. Responsible of keeping track of an ordered collection of
 * {@link org.broadleafcommerce.core.workflow.Activity Activities}
 *
 * @since    March 1, 2005
 * @see      org.broadleafcommerce.core.workflow.Activity
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public abstract class BaseProcessor implements InitializingBean, BeanNameAware, BeanFactoryAware, Processor {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected List<Activity<ProcessContext>> activities = new ArrayList<Activity<ProcessContext>>();

  /** If set to true, this will allow an empty set of activities, thus creating a 'do-nothing' workflow. */
  protected boolean allowEmptyActivities = false;

  /** DOCUMENT ME! */
  protected BeanFactory beanFactory;

  /** DOCUMENT ME! */
  protected String      beanName;

  /** DOCUMENT ME! */
  protected ErrorHandler         defaultErrorHandler;

  /** DOCUMENT ME! */
  protected List<ModuleActivity> moduleActivities = new ArrayList<ModuleActivity>();

  /** DOCUMENT ME! */
  protected SupportLogger supportLogger = SupportLogManager.getLogger("Workflows", BaseProcessor.class);

  @Value("${workflow.auto.rollback.on.error}")
  private boolean autoRollbackOnError = true;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Called after the properties have been set, Ensures the list of activities is not empty and each activity is
   * supported by this Workflow Processor.
   *
   * @throws  Exception                       DOCUMENT ME!
   * @throws  BeanInitializationException     DOCUMENT ME!
   * @throws  UnsatisfiedDependencyException  DOCUMENT ME!
   *
   * @see     org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
   */
  @Override public void afterPropertiesSet() throws Exception {
    if (!(beanFactory instanceof ListableBeanFactory)) {
      throw new BeanInitializationException("The workflow processor [" + beanName + "] "
        + "is not managed by a ListableBeanFactory, please re-deploy using some derivative of ListableBeanFactory such as"
        + "ClassPathXmlApplicationContext ");
    }

    if (CollectionUtils.isEmpty(activities) && !isAllowEmptyActivities()) {
      throw new UnsatisfiedDependencyException(getBeanDesc(), beanName, "activities",
        "No activities were wired for this workflow");
    }

    // sort the activities based on their configured order
    OrderComparator.sort(activities);

    HashSet<String> moduleNames = new HashSet<String>();

    for (Iterator<Activity<ProcessContext>> iter = activities.iterator(); iter.hasNext();) {
      Activity<? extends ProcessContext> activity = iter.next();

      if (!supports(activity)) {
        throw new BeanInitializationException("The workflow processor [" + beanName + "] does "
          + "not support the activity of type" + activity.getClass().getName());
      }

      if (activity instanceof ModuleActivity) {
        moduleActivities.add((ModuleActivity) activity);
        moduleNames.add(((ModuleActivity) activity).getModuleName());
      }
    }

    if (CollectionUtils.isNotEmpty(moduleActivities)) {
      // log the fact that we've got some modifications to the workflow
      StringBuffer message = new StringBuffer();
      message.append("The following modules have made changes to the " + getBeanName() + " workflow: ");
      message.append(Arrays.toString(moduleNames.toArray()));
      message.append("\n");
      message.append("The final ordering of activities for the " + getBeanName() + " workflow is: \n");

      ArrayList<String> activityNames = new ArrayList<String>();
      CollectionUtils.collect(activities, new Transformer() {
          @Override public Object transform(Object input) {
            return ((Activity) input).getBeanName();
          }
        }, activityNames);
      message.append(Arrays.toString(activityNames.toArray()));

      supportLogger.lifecycle(LifeCycleEvent.CONFIG, message.toString());
    }

  } // end method afterPropertiesSet

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<Activity<ProcessContext>> getActivities() {
    return activities;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Whether or not the ActivityStateManager should automatically attempt to rollback any RollbackHandlers registered.
   * If false, rolling back will require an explicit call to
   * ActivityStateManagerImpl.getStateManager().rollbackAllState(). The default value is true.
   *
   * @return  Whether or not the ActivityStateManager should automatically attempt to rollback
   */
  public boolean getAutoRollbackOnError() {
    return autoRollbackOnError;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public BeanFactory getBeanFactory() {
    return beanFactory;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getBeanName() {
    return beanName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ErrorHandler getDefaultErrorHandler() {
    return defaultErrorHandler;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a filtered set of {@link #getActivities()} that have implemented the
   * {@link org.broadleafcommerce.core.workflow.ModuleActivity} interface. This set of module activities is only set
   * once during {@link #afterPropertiesSet()}, so if you invoke {@link #setActivities(java.util.List)} after the bean
   * has been initialized you will need to manually reset the list of module activities as well (which could be achieved
   * by manually invoking {@link #afterPropertiesSet()}).
   *
   * @return  a filtered set of {@link #getActivities()} that have implemented the
   *          {@link org.broadleafcommerce.core.workflow.ModuleActivity} interface.
   */
  public List<ModuleActivity> getModuleActivities() {
    return moduleActivities;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Defaults to 'false'. This will prevent an exception from being thrown when no activities have been configured for a
   * processor, and thus will create a 'do-nothing' workflow.
   *
   * @return  the allowEmptyActivities
   */
  public boolean isAllowEmptyActivities() {
    return allowEmptyActivities;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the collection of Activities to be executed by the Workflow Process.
   *
   * @param  activities  ordered collection (List) of activities to be executed by the processor
   */
  @Override public void setActivities(List<Activity<ProcessContext>> activities) {
    this.activities = activities;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  allowEmptyActivities  the allowEmptyActivities to set
   */
  public void setAllowEmptyActivities(boolean allowEmptyActivities) {
    this.allowEmptyActivities = allowEmptyActivities;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Set whether or not the ActivityStateManager should automatically attempt to rollback any RollbackHandlers
   * registered. If false, rolling back will require an explicit call to
   * ActivityStateManagerImpl.getStateManager().rollbackAllState(). The default value is true.
   *
   * @param  autoRollbackOnError  Whether or not the ActivityStateManager should automatically attempt to rollback
   */
  public void setAutoRollbackOnError(boolean autoRollbackOnError) {
    this.autoRollbackOnError = autoRollbackOnError;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the spring bean factroy bean that is responsible for this processor.
   *
   * @param   beanFactory  DOCUMENT ME!
   *
   * @throws  BeansException  DOCUMENT ME!
   *
   * @see     org.springframework.beans.factory.BeanFactoryAware#setBeanFactory(org.springframework.beans.factory.BeanFactory)
   */
  @Override public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets name of the spring bean in the application context that this processor is configured under.
   *
   * @param  beanName  DOCUMENT ME!
   *
   * @see    org.springframework.beans.factory.BeanNameAware#setBeanName(String)
   */
  @Override public void setBeanName(String beanName) {
    this.beanName = beanName;

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.workflow.Processor#setDefaultErrorHandler(org.broadleafcommerce.core.workflow.ErrorHandler)
   */
  @Override public void setDefaultErrorHandler(ErrorHandler defaultErrorHandler) {
    this.defaultErrorHandler = defaultErrorHandler;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the bean description if the current bean factory allows it.
   *
   * @return  spring bean description configure via the spring description tag
   */
  protected String getBeanDesc() {
    return (beanFactory instanceof ConfigurableListableBeanFactory)
      ? ((ConfigurableListableBeanFactory) beanFactory).getBeanDefinition(beanName).getResourceDescription()
      : ("Workflow Processor: " + beanName);
  }
} // end class BaseProcessor
