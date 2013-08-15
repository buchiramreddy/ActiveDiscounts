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

package org.broadleafcommerce.common.util.dao;

import java.util.List;
import java.util.Map;

import org.hibernate.SessionFactory;

import org.hibernate.ejb.HibernateEntityManager;

import org.hibernate.type.Type;


/**
 * Provides utility methods for interacting with dynamic entities.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public interface DynamicDaoHelper {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingClass                           DOCUMENT ME!
   * @param   sessionFactory                         DOCUMENT ME!
   * @param   includeUnqualifiedPolymorphicEntities  DOCUMENT ME!
   * @param   useCache                               DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClass, SessionFactory sessionFactory,
    boolean includeUnqualifiedPolymorphicEntities, boolean useCache);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entityClass    DOCUMENT ME!
   * @param   entityManager  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, Object> getIdMetadata(Class<?> entityClass, HibernateEntityManager entityManager);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entityClass    DOCUMENT ME!
   * @param   entityManager  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<String> getPropertyNames(Class<?> entityClass, HibernateEntityManager entityManager);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entityClass    DOCUMENT ME!
   * @param   entityManager  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Type> getPropertyTypes(Class<?> entityClass, HibernateEntityManager entityManager);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entityManager  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SessionFactory getSessionFactory(HibernateEntityManager entityManager);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   clazz  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isExcludeClassFromPolymorphism(Class<?> clazz);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingClass  DOCUMENT ME!
   * @param   entities      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Class<?>[] sortEntities(Class<?> ceilingClass, List<Class<?>> entities);

} // end interface DynamicDaoHelper
