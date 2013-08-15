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

package org.broadleafcommerce.common.email.domain;

import java.io.Serializable;

import java.util.Date;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface EmailTrackingOpens extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The dateOpened.
   *
   * @return  the dateOpened
   */
  Date getDateOpened();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The emailTracking.
   *
   * @return  the emailTracking
   */
  EmailTracking getEmailTracking();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The id.
   *
   * @return  the id
   */
  Long getId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The userAgent.
   *
   * @return  the userAgent
   */
  String getUserAgent();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  dateOpened  the dateOpened to set
   */
  void setDateOpened(Date dateOpened);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  emailTracking  the emailTracking to set
   */
  void setEmailTracking(EmailTracking emailTracking);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  id  the id to set
   */
  void setId(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  userAgent  the userAgent to set
   */
  void setUserAgent(String userAgent);

} // end interface EmailTrackingOpens
