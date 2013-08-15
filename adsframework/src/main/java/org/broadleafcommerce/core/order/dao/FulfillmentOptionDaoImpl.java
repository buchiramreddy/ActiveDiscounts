/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.order.dao;

import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.core.order.domain.FulfillmentOption;
import org.broadleafcommerce.core.order.domain.FulfillmentOptionImpl;
import org.broadleafcommerce.core.order.service.type.FulfillmentType;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
@Repository("blFulfillmentOptionDao")
public class FulfillmentOptionDaoImpl implements FulfillmentOptionDao {
  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  /**
   * @see  org.broadleafcommerce.core.order.dao.FulfillmentOptionDao#readFulfillmentOptionById(java.lang.Long)
   */
  @Override public FulfillmentOption readFulfillmentOptionById(final Long fulfillmentOptionId) {
    return em.find(FulfillmentOptionImpl.class, fulfillmentOptionId);
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.FulfillmentOptionDao#save(org.broadleafcommerce.core.order.domain.FulfillmentOption)
   */
  @Override public FulfillmentOption save(FulfillmentOption option) {
    return em.merge(option);
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.FulfillmentOptionDao#readAllFulfillmentOptions()
   */
  @Override public List<FulfillmentOption> readAllFulfillmentOptions() {
    TypedQuery<FulfillmentOption> query = em.createNamedQuery("BC_READ_ALL_FULFILLMENT_OPTIONS",
        FulfillmentOption.class);

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.FulfillmentOptionDao#readAllFulfillmentOptionsByFulfillmentType(org.broadleafcommerce.core.order.service.type.FulfillmentType)
   */
  @Override public List<FulfillmentOption> readAllFulfillmentOptionsByFulfillmentType(FulfillmentType type) {
    TypedQuery<FulfillmentOption> query = em.createNamedQuery("BC_READ_ALL_FULFILLMENT_OPTIONS_BY_TYPE",
        FulfillmentOption.class);
    query.setParameter("fulfillmentType", type.getType());

    return query.getResultList();
  }
} // end class FulfillmentOptionDaoImpl
