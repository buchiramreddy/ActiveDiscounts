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

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.broadleafcommerce.common.email.domain.EmailTarget;
import org.broadleafcommerce.common.email.domain.EmailTracking;
import org.broadleafcommerce.common.email.domain.EmailTrackingClicks;
import org.broadleafcommerce.common.email.domain.EmailTrackingOpens;
import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.common.time.SystemTime;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@Repository("blEmailReportingDao")
public class EmailReportingDaoImpl implements EmailReportingDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.email.dao.EmailReportingDao#createTarget()
   */
  @Override public EmailTarget createTarget() {
    EmailTarget target = (EmailTarget) entityConfiguration.createEntityInstance(
        "org.broadleafcommerce.common.email.domain.EmailTarget");

    return target;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see WebReportingDao#createTracking(java.lang.String, java.lang.String, java.lang.String)
   */
  @Override public Long createTracking(String emailAddress, String type, String extraValue) {
    EmailTracking tracking = (EmailTracking) entityConfiguration.createEntityInstance(
        "org.broadleafcommerce.common.email.domain.EmailTracking");
    tracking.setDateSent(SystemTime.asDate());
    tracking.setEmailAddress(emailAddress);
    tracking.setType(type);

    em.persist(tracking);

    return tracking.getId();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.email.dao.EmailReportingDao#recordClick(java.lang.Long, java.lang.String,
   *       java.lang.String, java.lang.String)
   */
  @Override public void recordClick(Long emailId, String customerId, String destinationUri, String queryString) {
    EmailTrackingClicks clicks = (EmailTrackingClicks) entityConfiguration.createEntityInstance(
        "org.broadleafcommerce.common.email.domain.EmailTrackingClicks");
    clicks.setEmailTracking(retrieveTracking(emailId));
    clicks.setDateClicked(SystemTime.asDate());
    clicks.setDestinationUri(destinationUri);
    clicks.setQueryString(queryString);
    clicks.setCustomerId(customerId);

    em.persist(clicks);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.email.dao.EmailReportingDao#recordOpen(java.lang.Long, java.lang.String)
   */
  @Override public void recordOpen(Long emailId, String userAgent) {
    EmailTrackingOpens opens = (EmailTrackingOpens) entityConfiguration.createEntityInstance(
        "org.broadleafcommerce.common.email.domain.EmailTrackingOpens");
    opens.setEmailTracking(retrieveTracking(emailId));
    opens.setDateOpened(SystemTime.asDate());
    opens.setUserAgent(userAgent);

    em.persist(opens);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.email.dao.EmailReportingDao#retrieveTracking(java.lang.Long)
   */
  @Override
  @SuppressWarnings("unchecked")
  public EmailTracking retrieveTracking(Long emailId) {
    return (EmailTracking) em.find(entityConfiguration.lookupEntityClass(
          "org.broadleafcommerce.common.email.domain.EmailTracking"), emailId);
  }
} // end class EmailReportingDaoImpl
