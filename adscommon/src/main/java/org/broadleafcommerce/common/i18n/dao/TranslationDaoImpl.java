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

package org.broadleafcommerce.common.i18n.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.broadleafcommerce.common.i18n.domain.TranslatedEntity;
import org.broadleafcommerce.common.i18n.domain.Translation;
import org.broadleafcommerce.common.i18n.domain.TranslationImpl;
import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.common.util.dao.DynamicDaoHelper;
import org.broadleafcommerce.common.util.dao.DynamicDaoHelperImpl;

import org.hibernate.ejb.HibernateEntityManager;
import org.hibernate.ejb.QueryHints;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blTranslationDao")
public class TranslationDaoImpl implements TranslationDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected DynamicDaoHelper dynamicDaoHelper = new DynamicDaoHelperImpl();

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.dao.TranslationDao#create()
   */
  @Override public Translation create() {
    return (Translation) entityConfiguration.createEntityInstance(Translation.class.getName());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.dao.TranslationDao#delete(org.broadleafcommerce.common.i18n.domain.Translation)
   */
  @Override public void delete(Translation translation) {
    em.remove(translation);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public DynamicDaoHelper getDynamicDaoHelper() {
    return dynamicDaoHelper;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.dao.TranslationDao#getIdPropertyMetadata(org.broadleafcommerce.common.i18n.domain.TranslatedEntity)
   */
  @Override public Map<String, Object> getIdPropertyMetadata(TranslatedEntity entity) {
    Class<?> implClass = entityConfiguration.lookupEntityClass(entity.getType());

    return dynamicDaoHelper.getIdMetadata(implClass, (HibernateEntityManager) em);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.dao.TranslationDao#readTranslation(org.broadleafcommerce.common.i18n.domain.TranslatedEntity,
   *       java.lang.String, java.lang.String, java.lang.String)
   */
  @Override public Translation readTranslation(TranslatedEntity entity, String entityId, String fieldName,
    String localeCode) {
    CriteriaBuilder            builder     = em.getCriteriaBuilder();
    CriteriaQuery<Translation> criteria    = builder.createQuery(Translation.class);
    Root<TranslationImpl>      translation = criteria.from(TranslationImpl.class);

    criteria.select(translation);
    criteria.where(builder.equal(translation.get("entityType"), entity.getFriendlyType()),
      builder.equal(translation.get("entityId"), entityId),
      builder.equal(translation.get("fieldName"), fieldName),
      builder.equal(translation.get("localeCode"), localeCode));

    TypedQuery<Translation> query = em.createQuery(criteria);
    query.setHint(QueryHints.HINT_CACHEABLE, true);

    try {
      return query.getSingleResult();
    } catch (NoResultException e) {
      return null;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.dao.TranslationDao#readTranslationById(java.lang.Long)
   */
  @Override public Translation readTranslationById(Long translationId) {
    return em.find(TranslationImpl.class, translationId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.dao.TranslationDao#readTranslations(org.broadleafcommerce.common.i18n.domain.TranslatedEntity,
   *       java.lang.String, java.lang.String)
   */
  @Override public List<Translation> readTranslations(TranslatedEntity entity, String entityId, String fieldName) {
    CriteriaBuilder            builder     = em.getCriteriaBuilder();
    CriteriaQuery<Translation> criteria    = builder.createQuery(Translation.class);
    Root<TranslationImpl>      translation = criteria.from(TranslationImpl.class);

    criteria.select(translation);
    criteria.where(builder.equal(translation.get("entityType"), entity.getFriendlyType()),
      builder.equal(translation.get("entityId"), entityId),
      builder.equal(translation.get("fieldName"), fieldName));

    TypedQuery<Translation> query = em.createQuery(criteria);
    query.setHint(QueryHints.HINT_CACHEABLE, true);

    try {
      return query.getResultList();
    } catch (NoResultException e) {
      return null;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.i18n.dao.TranslationDao#save(org.broadleafcommerce.common.i18n.domain.Translation)
   */
  @Override public Translation save(Translation translation) {
    return em.merge(translation);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  dynamicDaoHelper  DOCUMENT ME!
   */
  public void setDynamicDaoHelper(DynamicDaoHelper dynamicDaoHelper) {
    this.dynamicDaoHelper = dynamicDaoHelper;
  }

} // end class TranslationDaoImpl
