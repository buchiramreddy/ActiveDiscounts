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

package org.broadleafcommerce.common.email.dao;

import org.broadleafcommerce.common.email.domain.EmailTarget;
import org.broadleafcommerce.common.email.domain.EmailTracking;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface EmailReportingDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  EmailTarget createTarget();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   emailAddress  DOCUMENT ME!
   * @param   type          DOCUMENT ME!
   * @param   extraValue    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long createTracking(String emailAddress, String type, String extraValue);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  emailId         DOCUMENT ME!
   * @param  customerId      DOCUMENT ME!
   * @param  destinationUri  DOCUMENT ME!
   * @param  queryString     DOCUMENT ME!
   */
  void recordClick(Long emailId, String customerId, String destinationUri, String queryString);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  emailId    DOCUMENT ME!
   * @param  userAgent  DOCUMENT ME!
   */
  void recordOpen(Long emailId, String userAgent);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   emailId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  EmailTracking retrieveTracking(Long emailId);

} // end interface EmailReportingDao
