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

package org.broadleafcommerce.core.order.dao;

import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.core.order.domain.OrderMultishipOption;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blOrderMultishipOptionDao")
public class OrderMultishipOptionDaoImpl implements OrderMultishipOptionDao {
  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderMultishipOptionDao#save(org.broadleafcommerce.core.order.domain.OrderMultishipOption)
   */
  @Override public OrderMultishipOption save(final OrderMultishipOption orderMultishipOption) {
    return em.merge(orderMultishipOption);
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderMultishipOptionDao#readOrderMultishipOptions(java.lang.Long)
   */
  @Override public List<OrderMultishipOption> readOrderMultishipOptions(final Long orderId) {
    TypedQuery<OrderMultishipOption> query = em.createNamedQuery("BC_READ_MULTISHIP_OPTIONS_BY_ORDER_ID",
        OrderMultishipOption.class);
    query.setParameter("orderId", orderId);

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderMultishipOptionDao#readOrderItemOrderMultishipOptions(java.lang.Long)
   */
  @Override public List<OrderMultishipOption> readOrderItemOrderMultishipOptions(final Long orderItemId) {
    TypedQuery<OrderMultishipOption> query = em.createNamedQuery("BC_READ_MULTISHIP_OPTIONS_BY_ORDER_ITEM_ID",
        OrderMultishipOption.class);
    query.setParameter("orderItemId", orderItemId);

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderMultishipOptionDao#create()
   */
  @Override public OrderMultishipOption create() {
    return (OrderMultishipOption) entityConfiguration.createEntityInstance(OrderMultishipOption.class.getName());
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.OrderMultishipOptionDao#deleteAll(java.util.List)
   */
  @Override public void deleteAll(List<OrderMultishipOption> options) {
    for (OrderMultishipOption option : options) {
      em.remove(option);
    }
  }
} // end class OrderMultishipOptionDaoImpl
