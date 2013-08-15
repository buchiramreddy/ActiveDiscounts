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

package org.broadleafcommerce.core.offer.dao;

import org.broadleafcommerce.core.offer.domain.OfferAudit;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface OfferAuditDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   customerId  DOCUMENT ME!
   * @param   offerId     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long countUsesByCustomer(Long customerId, Long offerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferAudit create();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  offerAudit  DOCUMENT ME!
   */
  void delete(OfferAudit offerAudit);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   offerAuditId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferAudit readAuditById(Long offerAuditId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   offerAudit  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferAudit save(OfferAudit offerAudit);
} // end interface OfferAuditDao
