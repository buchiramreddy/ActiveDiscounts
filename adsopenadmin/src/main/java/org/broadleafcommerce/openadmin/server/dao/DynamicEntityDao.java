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

package org.broadleafcommerce.openadmin.server.dao;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.openadmin.dto.ClassTree;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.ForeignKey;
import org.broadleafcommerce.openadmin.dto.MergedPropertyType;
import org.broadleafcommerce.openadmin.dto.PersistencePerspective;
import org.broadleafcommerce.openadmin.server.dao.provider.metadata.FieldMetadataProvider;
import org.broadleafcommerce.openadmin.server.service.persistence.module.FieldManager;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;

import org.hibernate.mapping.PersistentClass;

import org.hibernate.type.Type;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface DynamicEntityDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  void clear();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingClass  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClass);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingClass                           DOCUMENT ME!
   * @param   includeUnqualifiedPolymorphicEntities  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClass, boolean includeUnqualifiedPolymorphicEntities);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   * @param   entities                              DOCUMENT ME!
   * @param   foreignField                          DOCUMENT ME!
   * @param   additionalNonPersistentProperties     DOCUMENT ME!
   * @param   additionalForeignFields               DOCUMENT ME!
   * @param   mergedPropertyType                    DOCUMENT ME!
   * @param   populateManyToOneFields               DOCUMENT ME!
   * @param   includeManyToOneFields                DOCUMENT ME!
   * @param   excludeManyToOneFields                DOCUMENT ME!
   * @param   configurationKey                      DOCUMENT ME!
   * @param   prefix                                DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, FieldMetadata> getMergedProperties(String ceilingEntityFullyQualifiedClassname, Class<?>[] entities,
    ForeignKey foreignField, String[] additionalNonPersistentProperties, ForeignKey[] additionalForeignFields,
    MergedPropertyType mergedPropertyType, Boolean populateManyToOneFields, String[] includeManyToOneFields,
    String[] excludeManyToOneFields, String configurationKey, String prefix);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   propertyName          DOCUMENT ME!
   * @param   friendlyPropertyName  DOCUMENT ME!
   * @param   targetClass           DOCUMENT ME!
   * @param   parentClass           DOCUMENT ME!
   * @param   mergedPropertyType    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, FieldMetadata> getPropertiesForPrimitiveClass(String propertyName, String friendlyPropertyName,
    Class<?> targetClass, Class<?> parentClass, MergedPropertyType mergedPropertyType);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entity  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Serializable merge(Serializable entity);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entity  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Serializable persist(Serializable entity);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  entity  DOCUMENT ME!
   */
  void remove(Serializable entity);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entityClass  DOCUMENT ME!
   * @param   primaryKey   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Serializable retrieve(Class<?> entityClass, Object primaryKey);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entityClass  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Criteria createCriteria(Class<?> entityClass);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  entity  DOCUMENT ME!
   */
  void detach(Serializable entity);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  void flush();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   targetClass  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Field[] getAllFields(Class<?> targetClass);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   polymorphicClasses  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ClassTree getClassTree(Class<?>[] polymorphicClasses);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingClass  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ClassTree getClassTreeFromCeiling(Class<?> ceilingClass);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FieldMetadataProvider getDefaultFieldMetadataProvider();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  EntityConfiguration getEntityConfiguration();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FieldManager getFieldManager();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entityClass  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, Object> getIdMetadata(Class<?> entityClass);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Metadata getMetadata();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Get the Hibernate PersistentClass instance associated with the fully-qualified class name. Will return null if no
   * persistent class is associated with this name.
   *
   * @param   targetClassName  DOCUMENT ME!
   *
   * @return  The PersistentClass instance
   */
  PersistentClass getPersistentClass(String targetClassName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entityClass  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<String> getPropertyNames(Class<?> entityClass);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entityClass  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<Type> getPropertyTypes(Class<?> entityClass);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SessionFactory getSessionFactory();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entityName              DOCUMENT ME!
   * @param   persistencePerspective  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, FieldMetadata> getSimpleMergedProperties(String entityName,
    PersistencePerspective persistencePerspective);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  EntityManager getStandardEntityManager();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  entity  DOCUMENT ME!
   */
  void refresh(Serializable entity);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  entityConfiguration  DOCUMENT ME!
   */
  void setEntityConfiguration(EntityConfiguration entityConfiguration);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  metadata  DOCUMENT ME!
   */
  void setMetadata(Metadata metadata);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  entityManager  DOCUMENT ME!
   */
  void setStandardEntityManager(EntityManager entityManager);

} // end interface DynamicEntityDao
