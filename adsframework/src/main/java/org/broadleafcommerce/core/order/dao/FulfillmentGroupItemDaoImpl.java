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
import javax.persistence.Query;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItemImpl;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blFulfillmentGroupItemDao")
public class FulfillmentGroupItemDaoImpl implements FulfillmentGroupItemDao {
  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  /**
   * @see  org.broadleafcommerce.core.order.dao.FulfillmentGroupItemDao#delete(org.broadleafcommerce.core.order.domain.FulfillmentGroupItem)
   */
  @Override public void delete(FulfillmentGroupItem fulfillmentGroupItem) {
    if (!em.contains(fulfillmentGroupItem)) {
      fulfillmentGroupItem = readFulfillmentGroupItemById(fulfillmentGroupItem.getId());
    }

    em.remove(fulfillmentGroupItem);
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.FulfillmentGroupItemDao#save(org.broadleafcommerce.core.order.domain.FulfillmentGroupItem)
   */
  @Override public FulfillmentGroupItem save(final FulfillmentGroupItem fulfillmentGroupItem) {
    return em.merge(fulfillmentGroupItem);
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.FulfillmentGroupItemDao#readFulfillmentGroupItemById(java.lang.Long)
   */
  @Override public FulfillmentGroupItem readFulfillmentGroupItemById(final Long fulfillmentGroupItemId) {
    return (FulfillmentGroupItem) em.find(FulfillmentGroupItemImpl.class, fulfillmentGroupItemId);
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.FulfillmentGroupItemDao#readFulfillmentGroupItemsForFulfillmentGroup(org.broadleafcommerce.core.order.domain.FulfillmentGroup)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<FulfillmentGroupItem> readFulfillmentGroupItemsForFulfillmentGroup(
    final FulfillmentGroup fulfillmentGroup) {
    final Query query = em.createNamedQuery("BC_READ_FULFILLMENT_GROUP_ITEM_BY_FULFILLMENT_GROUP_ID");
    query.setParameter("fulfillmentGroupId", fulfillmentGroup.getId());

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.order.dao.FulfillmentGroupItemDao#create()
   */
  @Override public FulfillmentGroupItem create() {
    return ((FulfillmentGroupItem) entityConfiguration.createEntityInstance(
          "org.broadleafcommerce.core.order.domain.FulfillmentGroupItem"));
  }
} // end class FulfillmentGroupItemDaoImpl
