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

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.core.offer.domain.OfferAudit;
import org.broadleafcommerce.core.offer.domain.OfferAuditImpl;

import org.hibernate.ejb.QueryHints;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blOfferAuditDao")
public class OfferAuditDaoImpl implements OfferAuditDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferAuditDao#countUsesByCustomer(java.lang.Long, java.lang.Long)
   */
  @Override public Long countUsesByCustomer(Long customerId, Long offerId) {
    CriteriaBuilder     cb = em.getCriteriaBuilder();
    CriteriaQuery<Long> cq = cb.createQuery(Long.class);
    cq.select(cb.count(cq.from(OfferAuditImpl.class)));

    Root<OfferAuditImpl> from             = cq.from(OfferAuditImpl.class);
    Predicate            customerIdClause = cb.equal(from.get("customerId"), customerId);

    Predicate offerIdClause = cb.equal(from.get("offerId"), offerId);
    cq.where(cb.and(customerIdClause, offerIdClause));

    TypedQuery<Long> query = em.createQuery(cq);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");

    Long result = query.getSingleResult();

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferAuditDao#create()
   */
  @Override public OfferAudit create() {
    return ((OfferAudit) entityConfiguration.createEntityInstance(OfferAudit.class.getName()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferAuditDao#delete(org.broadleafcommerce.core.offer.domain.OfferAudit)
   */
  @Override public void delete(final OfferAudit offerAudit) {
    OfferAudit loa = offerAudit;

    if (!em.contains(loa)) {
      loa = readAuditById(offerAudit.getId());
    }

    em.remove(loa);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferAuditDao#readAuditById(java.lang.Long)
   */
  @Override public OfferAudit readAuditById(final Long offerAuditId) {
    return (OfferAudit) em.find(OfferAuditImpl.class, offerAuditId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferAuditDao#save(org.broadleafcommerce.core.offer.domain.OfferAudit)
   */
  @Override public OfferAudit save(final OfferAudit offerAudit) {
    return em.merge(offerAudit);
  }

} // end class OfferAuditDaoImpl
