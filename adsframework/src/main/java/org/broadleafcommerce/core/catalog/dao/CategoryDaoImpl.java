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
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.common.persistence.Status;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryImpl;
import org.broadleafcommerce.core.catalog.domain.Product;

import org.hibernate.ejb.QueryHints;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@Repository("blCategoryDao")
public class CategoryDaoImpl implements CategoryDao {
  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#save(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public Category save(Category category) {
    return em.merge(category);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#readCategoryById(java.lang.Long)
   */
  @Override public Category readCategoryById(Long categoryId) {
    return em.find(CategoryImpl.class, categoryId);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#readCategoryByName(java.lang.String)
   */
  @Deprecated @Override public Category readCategoryByName(String categoryName) {
    Query query = em.createNamedQuery("BC_READ_CATEGORY_BY_NAME");
    query.setParameter("categoryName", categoryName);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");

    return (Category) query.getSingleResult();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#readAllParentCategories()
   */
  @Override public List<Category> readAllParentCategories() {
    CriteriaBuilder         builder  = em.getCriteriaBuilder();
    CriteriaQuery<Category> criteria = builder.createQuery(Category.class);
    Root<CategoryImpl>      category = criteria.from(CategoryImpl.class);

    criteria.select(category);
    criteria.where(builder.isNull(category.get("defaultParentCategory")));

    TypedQuery<Category> query = em.createQuery(criteria);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");

    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#readCategoriesByName(java.lang.String)
   */
  @Override public List<Category> readCategoriesByName(String categoryName) {
    TypedQuery<Category> query = em.createNamedQuery("BC_READ_CATEGORY_BY_NAME", Category.class);
    query.setParameter("categoryName", categoryName);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#readCategoriesByName(java.lang.String, int, int)
   */
  @Override public List<Category> readCategoriesByName(String categoryName, int limit, int offset) {
    TypedQuery<Category> query = em.createNamedQuery("BC_READ_CATEGORY_BY_NAME", Category.class);
    query.setParameter("categoryName", categoryName);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");
    query.setFirstResult(offset);
    query.setMaxResults(limit);

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#readAllCategories()
   */
  @Override public List<Category> readAllCategories() {
    TypedQuery<Category> query = em.createNamedQuery("BC_READ_ALL_CATEGORIES", Category.class);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#readAllCategories(int, int)
   */
  @Override public List<Category> readAllCategories(int limit, int offset) {
    TypedQuery<Category> query = em.createNamedQuery("BC_READ_ALL_CATEGORIES", Category.class);
    query.setFirstResult(offset);
    query.setMaxResults(limit);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#readAllProducts()
   */
  @Override public List<Product> readAllProducts() {
    TypedQuery<Product> query = em.createNamedQuery("BC_READ_ALL_PRODUCTS", Product.class);

    // don't cache - could take up too much memory
    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#readAllProducts(int, int)
   */
  @Override public List<Product> readAllProducts(int limit, int offset) {
    TypedQuery<Product> query = em.createNamedQuery("BC_READ_ALL_PRODUCTS", Product.class);

    // don't cache - could take up too much memory
    query.setFirstResult(offset);
    query.setMaxResults(limit);

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#readAllSubCategories(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public List<Category> readAllSubCategories(Category category) {
    TypedQuery<Category> query = em.createNamedQuery("BC_READ_ALL_SUBCATEGORIES", Category.class);
    query.setParameter("defaultParentCategory", category);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#readAllSubCategories(org.broadleafcommerce.core.catalog.domain.Category,
   *       int, int)
   */
  @Override public List<Category> readAllSubCategories(Category category, int limit, int offset) {
    TypedQuery<Category> query = em.createNamedQuery("BC_READ_ALL_SUBCATEGORIES", Category.class);
    query.setParameter("defaultParentCategory", category);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");
    query.setFirstResult(offset);
    query.setMaxResults(limit);

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#readActiveSubCategoriesByCategory(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public List<Category> readActiveSubCategoriesByCategory(Category category) {
    TypedQuery<Category> query = em.createNamedQuery("BC_READ_ACTIVE_SUBCATEGORIES_BY_CATEGORY", Category.class);
    query.setParameter("defaultParentCategoryId", category.getId());
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#readActiveSubCategoriesByCategory(org.broadleafcommerce.core.catalog.domain.Category,
   *       int, int)
   */
  @Override public List<Category> readActiveSubCategoriesByCategory(Category category, int limit, int offset) {
    TypedQuery<Category> query = em.createNamedQuery("BC_READ_ACTIVE_SUBCATEGORIES_BY_CATEGORY", Category.class);
    query.setParameter("defaultParentCategoryId", category.getId());
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");
    query.setFirstResult(offset);
    query.setMaxResults(limit);

    return query.getResultList();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#delete(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public void delete(Category category) {
    ((Status) category).setArchived('Y');
    em.merge(category);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#create()
   */
  @Override public Category create() {
    return (Category) entityConfiguration.createEntityInstance(Category.class.getName());
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.dao.CategoryDao#findCategoryByURI(java.lang.String)
   */
  @Override public Category findCategoryByURI(String uri) {
    Query query;
    query = em.createNamedQuery("BC_READ_CATEGORY_OUTGOING_URL");
    query.setParameter("url", uri);
    query.setHint(QueryHints.HINT_CACHEABLE, true);
    query.setHint(QueryHints.HINT_CACHE_REGION, "query.Catalog");

    @SuppressWarnings("unchecked")
    List<Category> results = query.getResultList();

    if ((results != null) && !results.isEmpty()) {
      return results.get(0);

    } else {
      return null;
    }
  }

} // end class CategoryDaoImpl
