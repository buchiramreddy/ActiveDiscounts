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
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl;
import org.broadleafcommerce.core.catalog.domain.CategoryXref;
import org.broadleafcommerce.core.catalog.domain.CategoryXrefImpl;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@Repository("blCategoryXrefDao")
public class CategoryXrefDaoImpl implements CategoryXrefDao {
  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryXrefDao#readXrefsByCategoryId(java.lang.Long)
   */
  @Override public List<CategoryXrefImpl> readXrefsByCategoryId(Long categoryId) {
    TypedQuery<CategoryXrefImpl> query = em.createNamedQuery("BC_READ_CATEGORY_XREF_BY_CATEGORYID",
        CategoryXrefImpl.class);
    query.setParameter("categoryId", categoryId);

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryXrefDao#readXrefsBySubCategoryId(java.lang.Long)
   */
  @Override public List<CategoryXrefImpl> readXrefsBySubCategoryId(Long subCategoryId) {
    TypedQuery<CategoryXrefImpl> query = em.createNamedQuery("BC_READ_CATEGORY_XREF_BY_SUBCATEGORYID",
        CategoryXrefImpl.class);
    query.setParameter("subCategoryId", subCategoryId);

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryXrefDao#readXrefByIds(java.lang.Long, java.lang.Long)
   */
  @Override public CategoryXrefImpl readXrefByIds(Long categoryId, Long subCategoryId) {
    Query query = em.createNamedQuery("BC_READ_CATEGORY_XREF_BY_IDS");
    query.setParameter("categoryId", categoryId);
    query.setParameter("subCategoryId", subCategoryId);

    return (CategoryXrefImpl) query.getSingleResult();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryXrefDao#save(org.broadleafcommerce.core.catalog.domain.CategoryXrefImpl)
   */
  @Override public CategoryXref save(CategoryXrefImpl categoryXref) {
    return em.merge(categoryXref);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryXrefDao#delete(org.broadleafcommerce.core.catalog.domain.CategoryXrefImpl)
   */
  @Override public void delete(CategoryXrefImpl categoryXref) {
    if (!em.contains(categoryXref)) {
      categoryXref = readXrefByIds(categoryXref.getCategory().getId(), categoryXref.getSubCategory().getId());
    }

    em.remove(categoryXref);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryXrefDao#save(org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl)
   */
  @Override public CategoryProductXrefImpl save(CategoryProductXrefImpl categoryProductXref) {
    return em.merge(categoryProductXref);
  }

} // end class CategoryXrefDaoImpl
