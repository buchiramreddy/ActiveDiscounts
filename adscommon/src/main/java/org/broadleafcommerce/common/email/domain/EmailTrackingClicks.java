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
public interface EmailTrackingClicks extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getCustomerId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The dateClicked.
   *
   * @return  the dateClicked
   */
  Date getDateClicked();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The destinationUri.
   *
   * @return  the destinationUri
   */
  String getDestinationUri();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The emailTracking.
   *
   * @return  the emailTracking
   */
  EmailTracking getEmailTracking();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The emailId.
   *
   * @return  the emailId
   */
  Long getId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The queryString.
   *
   * @return  the queryString
   */
  String getQueryString();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  customerId  the customer to set
   */
  void setCustomerId(String customerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  dateClicked  the dateClicked to set
   */
  void setDateClicked(Date dateClicked);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  destinationUri  the destinationUri to set
   */
  void setDestinationUri(String destinationUri);

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
   * @param  id  the i to set
   */
  void setId(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  queryString  the queryString to set
   */
  void setQueryString(String queryString);

} // end interface EmailTrackingClicks
