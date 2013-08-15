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

package org.broadleafcommerce.test;

import java.lang.management.ManagementFactory;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.broadleafcommerce.common.extensibility.context.MergeClassPathXMLApplicationContext;
import org.broadleafcommerce.common.extensibility.context.StandardConfigLocations;

import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.transaction.TransactionConfiguration;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@TestExecutionListeners(
  inheritListeners = false,
  value            = {
    MergeDependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    MergeTransactionalTestExecutionListener.class
  }
)
@TransactionConfiguration(
  transactionManager = "blTransactionManager",
  defaultRollback    = true
)
public abstract class BaseTest extends AbstractTestNGSpringContextTests {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected static MergeClassPathXMLApplicationContext mergeContext = null;

  /** DOCUMENT ME! */
  protected static List<String> moduleContexts = new ArrayList<String>();

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  public static MergeClassPathXMLApplicationContext getContext() {
    try {
      if (mergeContext == null) {
        // Note that as of 2.2.0, this array will no longer include "bl-applicationContext-test", as we want that to
        // be the very last context loaded.
        String[] contexts = StandardConfigLocations.retrieveAll(StandardConfigLocations.TESTCONTEXTTYPE);

        // After the framework applicationContexts are loaded, we want the module ones
        List<String> additionalContexts = new ArrayList<String>(moduleContexts);

        // Lastly, we want the test applicationContext
        additionalContexts.add("bl-applicationContext-test.xml");

        // If we're running in legacy test mode, we need that one too
        if (ManagementFactory.getRuntimeMXBean().getInputArguments().contains("-Dlegacy=true")) {
          additionalContexts.add("bl-applicationContext-test-legacy.xml");
        }

        String[] strArray = new String[additionalContexts.size()];
        mergeContext = new MergeClassPathXMLApplicationContext(contexts, additionalContexts.toArray(strArray));
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return mergeContext;
  } // end method getContext

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected static List<String> getModuleContexts() {
    return moduleContexts;
  }

} // end class BaseTest
