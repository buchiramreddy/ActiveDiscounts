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

import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.broadleafcommerce.core.offer.domain.OfferCodeImpl;

import org.hibernate.ejb.QueryHints;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blOfferCodeDao")
public class OfferCodeDaoImpl implements OfferCodeDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferCodeDao#create()
   */
  @Override public OfferCode create() {
    return ((OfferCode) entityConfiguration.createEntityInstance(OfferCode.class.getName()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferCodeDao#delete(org.broadleafcommerce.core.offer.domain.OfferCode)
   */
  @Override public void delete(OfferCode offerCode) {
    if (!em.contains(offerCode)) {
      offerCode = readOfferCodeById(offerCode.getId());
    }

    em.remove(offerCode);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferCodeDao#readOfferCodeByCode(java.lang.String)
   */
  @Override
  @SuppressWarnings("unchecked")
  public OfferCode readOfferCodeByCode(String code) {
    OfferCode offerCode = null;
    Query     query     = em.createNamedQuery("BC_READ_OFFER_CODE_BY_CODE");
    query.setParameter("code", code);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");

    List<OfferCode> result = query.getResultList();

    if (result.size() > 0) {
      offerCode = result.get(0);
    }

    return offerCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferCodeDao#readOfferCodeById(java.lang.Long)
   */
  @Override public OfferCode readOfferCodeById(Long offerCodeId) {
    return (OfferCode) em.find(OfferCodeImpl.class, offerCodeId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.dao.OfferCodeDao#save(org.broadleafcommerce.core.offer.domain.OfferCode)
   */
  @Override public OfferCode save(OfferCode offerCode) {
    return em.merge(offerCode);
  }

} // end class OfferCodeDaoImpl
