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

import java.lang.reflect.Modifier;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.lang3.ArrayUtils;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;

import org.hibernate.SessionFactory;

import org.hibernate.ejb.HibernateEntityManager;

import org.hibernate.metadata.ClassMetadata;

import org.hibernate.type.Type;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class DynamicDaoHelperImpl implements DynamicDaoHelper {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final Object                    LOCK_OBJECT                            = new Object();

  /** DOCUMENT ME! */
  public static final Map<Class<?>, Class<?>[]> POLYMORPHIC_ENTITY_CACHE               = new LRUMap(100, 1000);

  /** DOCUMENT ME! */
  public static final Map<Class<?>, Class<?>[]> POLYMORPHIC_ENTITY_CACHE_WO_EXCLUSIONS = new LRUMap(100, 1000);

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.util.dao.DynamicDaoHelper#getAllPolymorphicEntitiesFromCeiling(java.lang.Class,org.hibernate.SessionFactory,
   *       boolean, boolean)
   */
  @Override public Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClass, SessionFactory sessionFactory,
    boolean includeUnqualifiedPolymorphicEntities, boolean useCache) {
    Class<?>[] cache = null;

    synchronized (LOCK_OBJECT) {
      if (useCache) {
        if (includeUnqualifiedPolymorphicEntities) {
          cache = POLYMORPHIC_ENTITY_CACHE.get(ceilingClass);
        } else {
          cache = POLYMORPHIC_ENTITY_CACHE_WO_EXCLUSIONS.get(ceilingClass);
        }
      }

      if (cache == null) {
        List<Class<?>> entities = new ArrayList<Class<?>>();

        for (Object item : sessionFactory.getAllClassMetadata().values()) {
          ClassMetadata metadata    = (ClassMetadata) item;
          Class<?>      mappedClass = metadata.getMappedClass();

          if ((mappedClass != null) && ceilingClass.isAssignableFrom(mappedClass)) {
            entities.add(mappedClass);
          }
        }

        Class<?>[] sortedEntities = sortEntities(ceilingClass, entities);

        List<Class<?>> filteredSortedEntities = new ArrayList<Class<?>>();

        for (int i = 0; i < sortedEntities.length; i++) {
          Class<?> item = sortedEntities[i];

          if (includeUnqualifiedPolymorphicEntities) {
            filteredSortedEntities.add(sortedEntities[i]);
          } else {
            if (isExcludeClassFromPolymorphism(item)) {
              continue;
            } else {
              filteredSortedEntities.add(sortedEntities[i]);
            }
          }
        }

        Class<?>[] filteredEntities = new Class<?>[filteredSortedEntities.size()];
        filteredEntities = filteredSortedEntities.toArray(filteredEntities);
        cache            = filteredEntities;

        if (includeUnqualifiedPolymorphicEntities) {
          POLYMORPHIC_ENTITY_CACHE.put(ceilingClass, filteredEntities);
        } else {
          POLYMORPHIC_ENTITY_CACHE_WO_EXCLUSIONS.put(ceilingClass, filteredEntities);
        }
      } // end if
    } // end synchronized

    return cache;
  } // end method getAllPolymorphicEntitiesFromCeiling

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.util.dao.DynamicDaoHelper#getIdMetadata(java.lang.Class, org.hibernate.ejb.HibernateEntityManager)
   */
  @Override public Map<String, Object> getIdMetadata(Class<?> entityClass, HibernateEntityManager entityManager) {
    Map<String, Object> response       = new HashMap<String, Object>();
    SessionFactory      sessionFactory = entityManager.getSession().getSessionFactory();

    ClassMetadata metadata = sessionFactory.getClassMetadata(entityClass);

    if (metadata == null) {
      return null;
    }

    String idProperty = metadata.getIdentifierPropertyName();
    response.put("name", idProperty);

    Type idType = metadata.getIdentifierType();
    response.put("type", idType);

    return response;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.util.dao.DynamicDaoHelper#getPropertyNames(java.lang.Class, org.hibernate.ejb.HibernateEntityManager)
   */
  @Override public List<String> getPropertyNames(Class<?> entityClass, HibernateEntityManager entityManager) {
    ClassMetadata metadata      = getSessionFactory(entityManager).getClassMetadata(entityClass);
    List<String>  propertyNames = new ArrayList<String>();
    Collections.addAll(propertyNames, metadata.getPropertyNames());

    return propertyNames;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.util.dao.DynamicDaoHelper#getPropertyTypes(java.lang.Class, org.hibernate.ejb.HibernateEntityManager)
   */
  @Override public List<Type> getPropertyTypes(Class<?> entityClass, HibernateEntityManager entityManager) {
    ClassMetadata metadata      = getSessionFactory(entityManager).getClassMetadata(entityClass);
    List<Type>    propertyTypes = new ArrayList<Type>();
    Collections.addAll(propertyTypes, metadata.getPropertyTypes());

    return propertyTypes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.util.dao.DynamicDaoHelper#getSessionFactory(org.hibernate.ejb.HibernateEntityManager)
   */
  @Override public SessionFactory getSessionFactory(HibernateEntityManager entityManager) {
    return entityManager.getSession().getSessionFactory();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.util.dao.DynamicDaoHelper#isExcludeClassFromPolymorphism(java.lang.Class)
   */
  @Override public boolean isExcludeClassFromPolymorphism(Class<?> clazz) {
    // We filter out abstract classes because they can't be instantiated.
    if (Modifier.isAbstract(clazz.getModifiers())) {
      return true;
    }

    // We filter out classes that are marked to exclude from polymorphism
    AdminPresentationClass adminPresentationClass = clazz.getAnnotation(AdminPresentationClass.class);

    if (adminPresentationClass == null) {
      return false;
    } else if (adminPresentationClass.excludeFromPolymorphism()) {
      return true;
    }

    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.util.dao.DynamicDaoHelper#sortEntities(java.lang.Class, java.util.List)
   */
  @Override public Class<?>[] sortEntities(Class<?> ceilingClass, List<Class<?>> entities) {
    /*
     * Sort entities with the most derived appearing first
     */
    Class<?>[]     sortedEntities = new Class<?>[entities.size()];
    List<Class<?>> stageItems     = new ArrayList<Class<?>>();
    stageItems.add(ceilingClass);

    int j = 0;

    while (j < sortedEntities.length) {
      List<Class<?>> newStageItems      = new ArrayList<Class<?>>();
      boolean        topLevelClassFound = false;

      for (Class<?> stageItem : stageItems) {
        Iterator<Class<?>> itr = entities.iterator();

        while (itr.hasNext()) {
          Class<?> entity = itr.next();

checkitem:  {
            if (ArrayUtils.contains(entity.getInterfaces(), stageItem) || entity.equals(stageItem)) {
              topLevelClassFound = true;

              break checkitem;
            }

            if (topLevelClassFound) {
              continue;
            }

            if (entity.getSuperclass().equals(stageItem) && (j > 0)) {
              break checkitem;
            }

            continue;
          }

          sortedEntities[j] = entity;
          itr.remove();
          j++;
          newStageItems.add(entity);
        } // end while
      } // end for

      if (newStageItems.isEmpty()) {
        throw new IllegalArgumentException("There was a gap in the inheritance hierarchy for (" + ceilingClass
          .getName() + ")");
      }

      stageItems = newStageItems;
    } // end while

    ArrayUtils.reverse(sortedEntities);

    return sortedEntities;
  } // end method sortEntities

} // end class DynamicDaoHelperImpl
