/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.common.workflow;

import java.util.List;

import org.broadleafcommerce.core.pricing.service.workflow.TotalActivity;
import org.broadleafcommerce.core.workflow.Activity;
import org.broadleafcommerce.core.workflow.ModuleActivity;
import org.broadleafcommerce.core.workflow.ProcessContext;
import org.broadleafcommerce.core.workflow.SequenceProcessor;
import org.broadleafcommerce.core.workflow.state.test.TestExampleModuleActivity;
import org.broadleafcommerce.core.workflow.state.test.TestPassThroughActivity;
import org.broadleafcommerce.core.workflow.state.test.TestRollbackActivity;

import org.broadleafcommerce.test.BaseTest;

import org.springframework.core.Ordered;

import org.testng.Assert;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;


/**
 * DOCUMENT ME!
 *
 * @author   Phillip Verheyden (phillipuniverse)
 * @version  $Revision$, $Date$
 */
public class WorkflowTest extends BaseTest {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  static {
    getModuleContexts().add("bl-applicationContext-test-module.xml");
  }

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected List<Activity<ProcessContext>> activities;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @BeforeTest public void setup() {
    activities = ((SequenceProcessor) getContext().getBean("blCheckoutWorkflow")).getActivities();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test public void testDetectedModuleActivity() {
    List<ModuleActivity> moduleActivities = ((SequenceProcessor) getContext().getBean("blCheckoutWorkflow"))
      .getModuleActivities();
    Assert.assertEquals(moduleActivities.size(), 1);
    Assert.assertEquals(moduleActivities.get(0).getModuleName(), "integration");
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test public void testFrameworkOrderingChanged() {
    TotalActivity totalActivity = (TotalActivity) getContext().getBean("blTotalActivity");
    Assert.assertEquals(totalActivity.getOrder(), 8080);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test public void testInBetweenActivity() {
    Assert.assertEquals(activities.get(4).getClass(), TestPassThroughActivity.class);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test public void testMergedOrderedActivities() {
    Assert.assertEquals(activities.get(0).getClass(), TestPassThroughActivity.class);
    Assert.assertEquals(activities.get(0).getOrder(), 100);

    Assert.assertEquals(activities.get(4).getClass(), TestPassThroughActivity.class);
    Assert.assertEquals(activities.get(4).getOrder(), 3000);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  @Test public void testNonExplicitOrdering() {
    Assert.assertEquals(activities.get(activities.size() - 1).getClass(), TestExampleModuleActivity.class);
    Assert.assertEquals(activities.get(activities.size() - 1).getOrder(), Ordered.LOWEST_PRECEDENCE);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Tests that a merged activity can have the same order as a framework activity and come after it.
   */
  @Test public void testSameOrderingConfiguredActivity() {
    Assert.assertEquals(activities.get(7).getClass(), TestRollbackActivity.class);
  }

} // end class WorkflowTest
