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

package org.broadleafcommerce.core.catalog.dao;

import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.domain.SkuFee;
import org.broadleafcommerce.core.catalog.domain.SkuImpl;

import org.springframework.stereotype.Repository;


/**
 * {@inheritDoc}
 *
 * @author  Jeff Fischer
 */
@Repository("blSkuDao")
public class SkuDaoImpl implements SkuDao {
  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.SkuDao#save(org.broadleafcommerce.core.catalog.domain.Sku)
   */
  @Override public Sku save(Sku sku) {
    return em.merge(sku);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.SkuDao#saveSkuFee(org.broadleafcommerce.core.catalog.domain.SkuFee)
   */
  @Override public SkuFee saveSkuFee(SkuFee fee) {
    return em.merge(fee);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.SkuDao#readSkuById(java.lang.Long)
   */
  @Override public Sku readSkuById(Long skuId) {
    return (Sku) em.find(SkuImpl.class, skuId);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.SkuDao#readFirstSku()
   */
  @Override public Sku readFirstSku() {
    TypedQuery<Sku> query = em.createNamedQuery("BC_READ_FIRST_SKU", Sku.class);

    return query.getSingleResult();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.SkuDao#readAllSkus()
   */
  @Override public List<Sku> readAllSkus() {
    TypedQuery<Sku> query = em.createNamedQuery("BC_READ_ALL_SKUS", Sku.class);

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.SkuDao#readSkusById(java.util.List)
   */
  @Override public List<Sku> readSkusById(List<Long> ids) {
    TypedQuery<Sku> query = em.createNamedQuery("BC_READ_SKUS_BY_ID", Sku.class);
    query.setParameter("skuIds", ids);

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.SkuDao#delete(org.broadleafcommerce.core.catalog.domain.Sku)
   */
  @Override public void delete(Sku sku) {
    if (!em.contains(sku)) {
      sku = readSkuById(sku.getId());
    }

    em.remove(sku);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.SkuDao#create()
   */
  @Override public Sku create() {
    return (Sku) entityConfiguration.createEntityInstance(Sku.class.getName());
  }
} // end class SkuDaoImpl
