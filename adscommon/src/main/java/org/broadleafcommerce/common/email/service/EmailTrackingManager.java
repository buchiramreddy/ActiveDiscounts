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

package org.broadleafcommerce.common.email.service;

import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface EmailTrackingManager {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   emailAddress  DOCUMENT ME!
   * @param   type          DOCUMENT ME!
   * @param   extraValue    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long createTrackedEmail(String emailAddress, String type, String extraValue);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  emailId       DOCUMENT ME!
   * @param  parameterMap  DOCUMENT ME!
   * @param  customerId    DOCUMENT ME!
   * @param  extraValues   DOCUMENT ME!
   */
  void recordClick(Long emailId, Map<String, String> parameterMap, String customerId, Map<String, String> extraValues);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  emailId      DOCUMENT ME!
   * @param  extraValues  DOCUMENT ME!
   */
  void recordOpen(Long emailId, Map<String, String> extraValues);

} // end interface EmailTrackingManager
