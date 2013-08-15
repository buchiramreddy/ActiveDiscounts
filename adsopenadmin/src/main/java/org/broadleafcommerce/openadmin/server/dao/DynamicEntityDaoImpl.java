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
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.math.BigDecimal;
import java.math.BigInteger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import javax.persistence.EntityManager;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.persistence.EntityConfiguration;
import org.broadleafcommerce.common.persistence.Status;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.client.PersistencePerspectiveItemType;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.util.dao.DynamicDaoHelper;
import org.broadleafcommerce.common.util.dao.DynamicDaoHelperImpl;

import org.broadleafcommerce.openadmin.dto.BasicFieldMetadata;
import org.broadleafcommerce.openadmin.dto.ClassTree;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.ForeignKey;
import org.broadleafcommerce.openadmin.dto.MergedPropertyType;
import org.broadleafcommerce.openadmin.dto.PersistencePerspective;
import org.broadleafcommerce.openadmin.server.dao.provider.metadata.FieldMetadataProvider;
import org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.AddMetadataFromFieldTypeRequest;
import org.broadleafcommerce.openadmin.server.dao.provider.metadata.request.LateStageAddMetadataRequest;
import org.broadleafcommerce.openadmin.server.service.AppConfigurationService;
import org.broadleafcommerce.openadmin.server.service.persistence.module.FieldManager;
import org.broadleafcommerce.openadmin.server.service.type.FieldProviderResponse;

import org.codehaus.jackson.map.util.LRUMap;

import org.hibernate.Criteria;
import org.hibernate.MappingException;
import org.hibernate.SessionFactory;

import org.hibernate.ejb.HibernateEntityManager;

import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Property;

import org.hibernate.type.ComponentType;
import org.hibernate.type.Type;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Scope;

import org.springframework.stereotype.Component;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@Component("blDynamicEntityDao")
@Scope("prototype")
public class DynamicEntityDaoImpl implements DynamicEntityDao {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(DynamicEntityDaoImpl.class);

  /** DOCUMENT ME! */
  protected static final Map<String, Map<String, FieldMetadata>> METADATA_CACHE =
    new LRUMap<String, Map<String, FieldMetadata>>(100, 1000);
  /*
   * This is the same as POLYMORPHIC_ENTITY_CACHE, except that it does not contain classes that are abstract or have been marked for exclusion
   * from polymorphism
   */

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAppConfigurationRemoteService")
  protected AppConfigurationService appConfigurationRemoteService;

  /** DOCUMENT ME! */
  @Value("${cache.entity.dao.metadata.ttl}")
  protected int cacheEntityMetaDataTtl;

  /** DOCUMENT ME! */
  @Resource(name = "blDefaultFieldMetadataProvider")
  protected FieldMetadataProvider defaultFieldMetadataProvider;

  /** DOCUMENT ME! */
  protected DynamicDaoHelper dynamicDaoHelper = new DynamicDaoHelperImpl();

  /** DOCUMENT ME! */
  @Resource(name = "blEJB3ConfigurationDao")
  protected EJB3ConfigurationDao ejb3ConfigurationDao;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  /** DOCUMENT ME! */
  @Resource(name = "blMetadataProviders")
  protected List<FieldMetadataProvider> fieldMetadataProviders = new ArrayList<FieldMetadataProvider>();

  /** DOCUMENT ME! */
  protected long lastCacheFlushTime = System.currentTimeMillis();

  /** DOCUMENT ME! */
  @Resource(name = "blMetadata")
  protected Metadata metadata;

  /** DOCUMENT ME! */
  protected EntityManager standardEntityManager;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#clear()
   */
  @Override public void clear() {
    standardEntityManager.clear();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#createCriteria(java.lang.Class)
   */
  @Override public Criteria createCriteria(Class<?> entityClass) {
    return ((HibernateEntityManager) getStandardEntityManager()).getSession().createCriteria(entityClass);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#detach(java.io.Serializable)
   */
  @Override public void detach(Serializable entity) {
    standardEntityManager.detach(entity);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#flush()
   */
  @Override public void flush() {
    standardEntityManager.flush();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getAllFields(java.lang.Class)
   */
  @Override public Field[] getAllFields(Class<?> targetClass) {
    Field[]  allFields    = new Field[] {};
    boolean  eof          = false;
    Class<?> currentClass = targetClass;

    while (!eof) {
      Field[] fields = currentClass.getDeclaredFields();
      allFields = (Field[]) ArrayUtils.addAll(allFields, fields);

      if (currentClass.getSuperclass() != null) {
        currentClass = currentClass.getSuperclass();
      } else {
        eof = true;
      }
    }

    return allFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getAllPolymorphicEntitiesFromCeiling(java.lang.Class)
   */
  @Override public Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClass) {
    return getAllPolymorphicEntitiesFromCeiling(ceilingClass, true);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* (non-Javadoc)
   * @see org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getAllPolymorphicEntitiesFromCeiling(java.lang.Class)
   */
  @Override public Class<?>[] getAllPolymorphicEntitiesFromCeiling(Class<?> ceilingClass,
    boolean includeUnqualifiedPolymorphicEntities) {
    return dynamicDaoHelper.getAllPolymorphicEntitiesFromCeiling(ceilingClass, getSessionFactory(),
        includeUnqualifiedPolymorphicEntities, useCache());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getClassTree(java.lang.Class[])
   */
  @Override public ClassTree getClassTree(Class<?>[] polymorphicClasses) {
    String ceilingClass = null;

    for (Class<?> clazz : polymorphicClasses) {
      AdminPresentationClass classPresentation = clazz.getAnnotation(AdminPresentationClass.class);

      if (classPresentation != null) {
        String ceilingEntity = classPresentation.ceilingDisplayEntity();

        if (!StringUtils.isEmpty(ceilingEntity)) {
          ceilingClass = ceilingEntity;

          break;
        }
      }
    }

    if (ceilingClass != null) {
      int pos = -1;
      int j   = 0;

      for (Class<?> clazz : polymorphicClasses) {
        if (clazz.getName().equals(ceilingClass)) {
          pos = j;

          break;
        }

        j++;
      }

      if (pos >= 0) {
        Class<?>[] temp = new Class<?>[pos + 1];
        System.arraycopy(polymorphicClasses, 0, temp, 0, j + 1);
        polymorphicClasses = temp;
      }
    }

    ClassTree classTree = null;

    if (!ArrayUtils.isEmpty(polymorphicClasses)) {
      Class<?> topClass = polymorphicClasses[polymorphicClasses.length - 1];
      classTree = new ClassTree(topClass.getName(), isExcludeClassFromPolymorphism(topClass));
      createClassTreeFromAnnotation(topClass, classTree);

      for (int j = polymorphicClasses.length - 1; j >= 0; j--) {
        addClassToTree(polymorphicClasses[j], classTree);
      }

      classTree.finalizeStructure(1);
    }

    return classTree;
  } // end method getClassTree

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getClassTreeFromCeiling(java.lang.Class)
   */
  @Override public ClassTree getClassTreeFromCeiling(Class<?> ceilingClass) {
    Class<?>[] sortedEntities = getAllPolymorphicEntitiesFromCeiling(ceilingClass);

    return getClassTree(sortedEntities);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getDefaultFieldMetadataProvider()
   */
  @Override public FieldMetadataProvider getDefaultFieldMetadataProvider() {
    return defaultFieldMetadataProvider;
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
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EJB3ConfigurationDao getEjb3ConfigurationDao() {
    return ejb3ConfigurationDao;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getEntityConfiguration()
   */
  @Override public EntityConfiguration getEntityConfiguration() {
    return entityConfiguration;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getFieldManager()
   */
  @Override public FieldManager getFieldManager() {
    return new FieldManager(entityConfiguration, this);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<FieldMetadataProvider> getFieldMetadataProviders() {
    return fieldMetadataProviders;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getIdMetadata(java.lang.Class)
   */
  @Override public Map<String, Object> getIdMetadata(Class<?> entityClass) {
    return dynamicDaoHelper.getIdMetadata(entityClass, (HibernateEntityManager) standardEntityManager);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getMergedProperties(java.lang.String,java.lang.Class[],
   *       org.broadleafcommerce.openadmin.dto.ForeignKey, java.lang.String[],
   *       org.broadleafcommerce.openadmin.dto.ForeignKey[], org.broadleafcommerce.openadmin.dto.MergedPropertyType,
   *       java.lang.Boolean, java.lang.String[], java.lang.String[], java.lang.String, java.lang.String)
   */
  @Override public Map<String, FieldMetadata> getMergedProperties(
    String ceilingEntityFullyQualifiedClassname, Class<?>[] entities,
    ForeignKey foreignField,
    String[] additionalNonPersistentProperties,
    ForeignKey[] additionalForeignFields,
    MergedPropertyType mergedPropertyType,
    Boolean populateManyToOneFields,
    String[] includeFields,
    String[] excludeFields,
    String configurationKey,
    String prefix) {
    Map<String, FieldMetadata> mergedProperties = getMergedPropertiesRecursively(
        ceilingEntityFullyQualifiedClassname,
        entities,
        foreignField,
        additionalNonPersistentProperties,
        additionalForeignFields,
        mergedPropertyType,
        populateManyToOneFields,
        includeFields,
        excludeFields,
        configurationKey,
        new ArrayList<Class<?>>(),
        prefix,
        false);

    final List<String> removeKeys = new ArrayList<String>();

    for (final String key : mergedProperties.keySet()) {
      if ((mergedProperties.get(key).getExcluded() != null) && mergedProperties.get(key).getExcluded()) {
        removeKeys.add(key);
      }
    }

    for (String removeKey : removeKeys) {
      mergedProperties.remove(removeKey);
    }

    // Allow field metadata providers to contribute additional fields here. These latestage handlers take place
    // after any cached lookups occur, and are ideal for adding in dynamic properties that are not globally cacheable
    // like properties gleaned from reflection typically are.
    Set<String> keys = new HashSet<String>(mergedProperties.keySet());

    for (Class<?> targetClass : entities) {
      for (String key : keys) {
        LateStageAddMetadataRequest amr = new LateStageAddMetadataRequest(key, null, targetClass, this, "");

        boolean foundOneOrMoreHandlers = false;

        for (FieldMetadataProvider fieldMetadataProvider : fieldMetadataProviders) {
          FieldProviderResponse response = fieldMetadataProvider.lateStageAddMetadata(amr, mergedProperties);

          if (FieldProviderResponse.NOT_HANDLED != response) {
            foundOneOrMoreHandlers = true;
          }

          if (FieldProviderResponse.HANDLED_BREAK == response) {
            break;
          }
        }

        if (!foundOneOrMoreHandlers) {
          defaultFieldMetadataProvider.lateStageAddMetadata(amr, mergedProperties);
        }
      }
    }

    return mergedProperties;
  } // end method getMergedProperties

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getMetadata()
   */
  @Override public Metadata getMetadata() {
    return metadata;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getPersistentClass(java.lang.String)
   */
  @Override public PersistentClass getPersistentClass(String targetClassName) {
    return ejb3ConfigurationDao.getConfiguration().getClassMapping(targetClassName);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getPropertiesForPrimitiveClass(java.lang.String,java.lang.String,
   *       java.lang.Class, java.lang.Class, org.broadleafcommerce.openadmin.dto.MergedPropertyType)
   */
  @Override public Map<String, FieldMetadata> getPropertiesForPrimitiveClass(
    String propertyName,
    String friendlyPropertyName, Class<?> targetClass, Class<?> parentClass,
    MergedPropertyType mergedPropertyType) {
    Map<String, FieldMetadata> fields                = new HashMap<String, FieldMetadata>();
    BasicFieldMetadata         presentationAttribute = new BasicFieldMetadata();
    presentationAttribute.setFriendlyName(friendlyPropertyName);

    if (String.class.isAssignableFrom(targetClass)) {
      presentationAttribute.setExplicitFieldType(SupportedFieldType.STRING);
      presentationAttribute.setVisibility(VisibilityEnum.VISIBLE_ALL);
      fields.put(propertyName,
        metadata.getFieldMetadata("", propertyName, null, SupportedFieldType.STRING, null, parentClass,
          presentationAttribute, mergedPropertyType, this));
    } else if (Boolean.class.isAssignableFrom(targetClass)) {
      presentationAttribute.setExplicitFieldType(SupportedFieldType.BOOLEAN);
      presentationAttribute.setVisibility(VisibilityEnum.VISIBLE_ALL);
      fields.put(propertyName,
        metadata.getFieldMetadata("", propertyName, null, SupportedFieldType.BOOLEAN, null, parentClass,
          presentationAttribute, mergedPropertyType, this));
    } else if (Date.class.isAssignableFrom(targetClass)) {
      presentationAttribute.setExplicitFieldType(SupportedFieldType.DATE);
      presentationAttribute.setVisibility(VisibilityEnum.VISIBLE_ALL);
      fields.put(propertyName,
        metadata.getFieldMetadata("", propertyName, null, SupportedFieldType.DATE, null, parentClass,
          presentationAttribute, mergedPropertyType, this));
    } else if (Money.class.isAssignableFrom(targetClass)) {
      presentationAttribute.setExplicitFieldType(SupportedFieldType.MONEY);
      presentationAttribute.setVisibility(VisibilityEnum.VISIBLE_ALL);
      fields.put(propertyName,
        metadata.getFieldMetadata("", propertyName, null, SupportedFieldType.MONEY, null, parentClass,
          presentationAttribute, mergedPropertyType, this));
    } else if (Byte.class.isAssignableFrom(targetClass)
          || Integer.class.isAssignableFrom(targetClass)
          || Long.class.isAssignableFrom(targetClass)
          || Short.class.isAssignableFrom(targetClass)) {
      presentationAttribute.setExplicitFieldType(SupportedFieldType.INTEGER);
      presentationAttribute.setVisibility(VisibilityEnum.VISIBLE_ALL);
      fields.put(propertyName,
        metadata.getFieldMetadata("", propertyName, null, SupportedFieldType.INTEGER, null, parentClass,
          presentationAttribute, mergedPropertyType, this));
    } else if (Double.class.isAssignableFrom(targetClass)
          || BigDecimal.class.isAssignableFrom(targetClass)) {
      presentationAttribute.setExplicitFieldType(SupportedFieldType.DECIMAL);
      presentationAttribute.setVisibility(VisibilityEnum.VISIBLE_ALL);
      fields.put(propertyName,
        metadata.getFieldMetadata("", propertyName, null, SupportedFieldType.DECIMAL, null, parentClass,
          presentationAttribute, mergedPropertyType, this));
    } // end if-else

    ((BasicFieldMetadata) fields.get(propertyName)).setLength(255);
    ((BasicFieldMetadata) fields.get(propertyName)).setForeignKeyCollection(false);
    ((BasicFieldMetadata) fields.get(propertyName)).setRequired(true);
    ((BasicFieldMetadata) fields.get(propertyName)).setUnique(true);
    ((BasicFieldMetadata) fields.get(propertyName)).setScale(100);
    ((BasicFieldMetadata) fields.get(propertyName)).setPrecision(100);

    return fields;
  } // end method getPropertiesForPrimitiveClass

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getPropertyNames(java.lang.Class)
   */
  @Override public List<String> getPropertyNames(Class<?> entityClass) {
    return dynamicDaoHelper.getPropertyNames(entityClass, (HibernateEntityManager) standardEntityManager);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getPropertyTypes(java.lang.Class)
   */
  @Override public List<Type> getPropertyTypes(Class<?> entityClass) {
    return dynamicDaoHelper.getPropertyTypes(entityClass, (HibernateEntityManager) standardEntityManager);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getSessionFactory()
   */
  @Override public SessionFactory getSessionFactory() {
    return dynamicDaoHelper.getSessionFactory((HibernateEntityManager) standardEntityManager);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getSimpleMergedProperties(java.lang.String, org.broadleafcommerce.openadmin.dto.PersistencePerspective)
   */
  @Override public Map<String, FieldMetadata> getSimpleMergedProperties(String entityName,
    PersistencePerspective persistencePerspective) {
    Class<?>[] entityClasses;

    try {
      entityClasses = getAllPolymorphicEntitiesFromCeiling(Class.forName(entityName));
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    if (!ArrayUtils.isEmpty(entityClasses)) {
      return getMergedProperties(
          entityName,
          entityClasses,
          (ForeignKey) persistencePerspective.getPersistencePerspectiveItems().get(
            PersistencePerspectiveItemType.FOREIGNKEY),
          persistencePerspective.getAdditionalNonPersistentProperties(),
          persistencePerspective.getAdditionalForeignKeys(),
          MergedPropertyType.PRIMARY,
          persistencePerspective.getPopulateToOneFields(),
          persistencePerspective.getIncludeFields(),
          persistencePerspective.getExcludeFields(),
          persistencePerspective.getConfigurationKey(),
          "");
    } else {
      Map<String, FieldMetadata> mergedProperties = new HashMap<String, FieldMetadata>();
      Class<?>                   targetClass;

      try {
        targetClass = Class.forName(entityName);
      } catch (ClassNotFoundException e) {
        throw new RuntimeException(e);
      }

      Map<String, FieldMetadata> attributesMap = metadata.getFieldPresentationAttributes(null, targetClass, this, "");

      for (String property : attributesMap.keySet()) {
        FieldMetadata presentationAttribute = attributesMap.get(property);

        if (!presentationAttribute.getExcluded()) {
          Field field = FieldManager.getSingleField(targetClass, property);

          if (!Modifier.isStatic(field.getModifiers())) {
            boolean handled = false;

            for (FieldMetadataProvider provider : fieldMetadataProviders) {
              FieldProviderResponse response = provider.addMetadataFromFieldType(
                  new AddMetadataFromFieldTypeRequest(field, targetClass, null, new ForeignKey[] {},
                    MergedPropertyType.PRIMARY, null, null, "",
                    property, null, false, 0, attributesMap, presentationAttribute,
                    ((BasicFieldMetadata) presentationAttribute).getExplicitFieldType(), field.getType(), this),
                  mergedProperties);

              if (FieldProviderResponse.NOT_HANDLED != response) {
                handled = true;
              }

              if (FieldProviderResponse.HANDLED_BREAK == response) {
                break;
              }
            }

            if (!handled) {
              // this provider is not included in the provider list on purpose - it is designed to handle basic
              // AdminPresentation fields, and those fields not admin presentation annotated at all
              defaultFieldMetadataProvider.addMetadataFromFieldType(
                new AddMetadataFromFieldTypeRequest(field, targetClass, null, new ForeignKey[] {},
                  MergedPropertyType.PRIMARY, null, null, "", property,
                  null, false, 0, attributesMap, presentationAttribute,
                  ((BasicFieldMetadata) presentationAttribute).getExplicitFieldType(),
                  field.getType(), this), mergedProperties);
            }
          } // end if
        } // end if
      } // end for

      return mergedProperties;
    } // end if-else
  } // end method getSimpleMergedProperties

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#getStandardEntityManager()
   */
  @Override public EntityManager getStandardEntityManager() {
    return standardEntityManager;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#merge(java.io.Serializable)
   */
  @Override public Serializable merge(Serializable entity) {
    Serializable response = standardEntityManager.merge(entity);
    standardEntityManager.flush();

    return response;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#persist(java.io.Serializable)
   */
  @Override public Serializable persist(Serializable entity) {
    standardEntityManager.persist(entity);
    standardEntityManager.flush();

    return entity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#refresh(java.io.Serializable)
   */
  @Override public void refresh(Serializable entity) {
    standardEntityManager.refresh(entity);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#remove(java.io.Serializable)
   */
  @Override public void remove(Serializable entity) {
    boolean isArchivable = Status.class.isAssignableFrom(entity.getClass());

    if (isArchivable) {
      ((Status) entity).setArchived('Y');
      merge(entity);
    } else {
      standardEntityManager.remove(entity);
      standardEntityManager.flush();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#retrieve(java.lang.Class, java.lang.Object)
   */
  @Override public Serializable retrieve(Class<?> entityClass, Object primaryKey) {
    return (Serializable) standardEntityManager.find(entityClass, primaryKey);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  defaultFieldMetadataProvider  DOCUMENT ME!
   */
  public void setDefaultFieldMetadataProvider(FieldMetadataProvider defaultFieldMetadataProvider) {
    this.defaultFieldMetadataProvider = defaultFieldMetadataProvider;
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

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ejb3ConfigurationDao  DOCUMENT ME!
   */
  public void setEjb3ConfigurationDao(EJB3ConfigurationDao ejb3ConfigurationDao) {
    this.ejb3ConfigurationDao = ejb3ConfigurationDao;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#setEntityConfiguration(org.broadleafcommerce.common.persistence.EntityConfiguration)
   */
  @Override public void setEntityConfiguration(EntityConfiguration entityConfiguration) {
    this.entityConfiguration = entityConfiguration;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fieldMetadataProviders  DOCUMENT ME!
   */
  public void setFieldMetadataProviders(List<FieldMetadataProvider> fieldMetadataProviders) {
    this.fieldMetadataProviders = fieldMetadataProviders;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#setMetadata(org.broadleafcommerce.openadmin.server.dao.Metadata)
   */
  @Override public void setMetadata(Metadata metadata) {
    this.metadata = metadata;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao#setStandardEntityManager(javax.persistence.EntityManager)
   */
  @Override public void setStandardEntityManager(EntityManager entityManager) {
    this.standardEntityManager = entityManager;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingClass  DOCUMENT ME!
   * @param   entities      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Class<?>[] sortEntities(Class<?> ceilingClass, List<Class<?>> entities) {
    return dynamicDaoHelper.sortEntities(ceilingClass, entities);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   presentationAttribute  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean testPropertyInclusion(FieldMetadata presentationAttribute) {
    setExcludedBasedOnShowIfProperty(presentationAttribute);

    return !((presentationAttribute != null)
        && (((presentationAttribute.getExcluded() != null) && presentationAttribute.getExcluded())
          || ((presentationAttribute.getChildrenExcluded() != null) && presentationAttribute.getChildrenExcluded())));

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   clazz  DOCUMENT ME!
   * @param   tree   DOCUMENT ME!
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  protected void addClassToTree(Class<?> clazz, ClassTree tree) {
    Class<?> testClass;

    try {
      testClass = Class.forName(tree.getFullyQualifiedClassname());
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    if (clazz.equals(testClass)) {
      return;
    }

    if (clazz.getSuperclass().equals(testClass)) {
      ClassTree myTree = new ClassTree(clazz.getName(), isExcludeClassFromPolymorphism(clazz));
      createClassTreeFromAnnotation(clazz, myTree);
      tree.setChildren((ClassTree[]) ArrayUtils.add(tree.getChildren(), myTree));
    } else {
      for (ClassTree child : tree.getChildren()) {
        addClassToTree(clazz, child);
      }
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  foreignField             DOCUMENT ME!
   * @param  additionalForeignFields  DOCUMENT ME!
   * @param  mergedProperties         DOCUMENT ME!
   */
  protected void applyForeignKeyPrecedence(ForeignKey foreignField, ForeignKey[] additionalForeignFields,
    Map<String, FieldMetadata> mergedProperties) {
    for (String key : mergedProperties.keySet()) {
      boolean isForeign = false;

      if (foreignField != null) {
        isForeign = foreignField.getManyToField().equals(key);
      }

      if (!isForeign && !ArrayUtils.isEmpty(additionalForeignFields)) {
        for (ForeignKey foreignKey : additionalForeignFields) {
          isForeign = foreignKey.getManyToField().equals(key);

          if (isForeign) {
            break;
          }
        }
      }

      if (isForeign) {
        FieldMetadata metadata = mergedProperties.get(key);
        metadata.setExcluded(false);
      }
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  includeFields     DOCUMENT ME!
   * @param  excludeFields     DOCUMENT ME!
   * @param  prefix            DOCUMENT ME!
   * @param  isParentExcluded  DOCUMENT ME!
   * @param  mergedProperties  DOCUMENT ME!
   */
  protected void applyIncludesAndExcludes(String[] includeFields, String[] excludeFields, String prefix,
    Boolean isParentExcluded, Map<String, FieldMetadata> mergedProperties) {
    // check includes
    if (!ArrayUtils.isEmpty(includeFields)) {
      for (String include : includeFields) {
        for (String key : mergedProperties.keySet()) {
          String testKey = prefix + key;

          if (!(testKey.startsWith(include + ".") || testKey.equals(include))) {
            FieldMetadata metadata = mergedProperties.get(key);

            if (LOG.isDebugEnabled()) {
              LOG.debug("applyIncludesAndExcludes:Excluding " + key
                + " because this field did not appear in the explicit includeFields list");
            }

            metadata.setExcluded(true);
          } else {
            FieldMetadata metadata = mergedProperties.get(key);

            if (!isParentExcluded) {
              if (LOG.isDebugEnabled()) {
                LOG.debug("applyIncludesAndExcludes:Showing " + key
                  + " because this field appears in the explicit includeFields list");
              }

              metadata.setExcluded(false);
            }
          }
        } // end for
      } // end for
    } else if (!ArrayUtils.isEmpty(excludeFields)) {
      // check excludes
      for (String exclude : excludeFields) {
        for (String key : mergedProperties.keySet()) {
          String testKey = prefix + key;

          if (testKey.startsWith(exclude + ".") || testKey.equals(exclude)) {
            FieldMetadata metadata = mergedProperties.get(key);

            if (LOG.isDebugEnabled()) {
              LOG.debug("applyIncludesAndExcludes:Excluding " + key
                + " because this field appears in the explicit excludeFields list");
            }

            metadata.setExcluded(true);
          } else {
            FieldMetadata metadata = mergedProperties.get(key);

            if (!isParentExcluded) {
              if (LOG.isDebugEnabled()) {
                LOG.debug("applyIncludesAndExcludes:Showing " + key
                  + " because this field did not appear in the explicit excludeFields list");
              }

              metadata.setExcluded(false);
            }
          }
        } // end for
      } // end for
    } // end if-else
  } // end method applyIncludesAndExcludes

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  field                                 DOCUMENT ME!
   * @param  targetClass                           DOCUMENT ME!
   * @param  foreignField                          DOCUMENT ME!
   * @param  additionalForeignFields               DOCUMENT ME!
   * @param  additionalNonPersistentProperties     DOCUMENT ME!
   * @param  mergedPropertyType                    DOCUMENT ME!
   * @param  presentationAttributes                DOCUMENT ME!
   * @param  componentProperties                   DOCUMENT ME!
   * @param  fields                                DOCUMENT ME!
   * @param  idProperty                            DOCUMENT ME!
   * @param  populateManyToOneFields               DOCUMENT ME!
   * @param  includeFields                         DOCUMENT ME!
   * @param  excludeFields                         DOCUMENT ME!
   * @param  configurationKey                      DOCUMENT ME!
   * @param  ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   * @param  parentClasses                         DOCUMENT ME!
   * @param  prefix                                DOCUMENT ME!
   * @param  isParentExcluded                      DOCUMENT ME!
   * @param  propertyName                          DOCUMENT ME!
   * @param  type                                  DOCUMENT ME!
   * @param  propertyForeignKey                    DOCUMENT ME!
   * @param  additionalForeignKeyIndexPosition     DOCUMENT ME!
   */
  protected void buildBasicProperty(
    Field field, Class<?> targetClass,
    ForeignKey foreignField,
    ForeignKey[] additionalForeignFields,
    String[] additionalNonPersistentProperties,
    MergedPropertyType mergedPropertyType, Map<String, FieldMetadata> presentationAttributes,
    List<Property> componentProperties, Map<String, FieldMetadata> fields,
    String idProperty,
    Boolean populateManyToOneFields,
    String[] includeFields,
    String[] excludeFields,
    String configurationKey,
    String ceilingEntityFullyQualifiedClassname, List<Class<?>> parentClasses,
    String prefix,
    Boolean isParentExcluded,
    String propertyName,
    Type type,
    boolean propertyForeignKey,
    int additionalForeignKeyIndexPosition) {
    FieldMetadata presentationAttribute = presentationAttributes.get(propertyName);
    Boolean       amIExcluded           = isParentExcluded || !testPropertyInclusion(presentationAttribute);
    Boolean       includeField          = testPropertyRecursion(prefix, parentClasses, propertyName, targetClass,
        ceilingEntityFullyQualifiedClassname);

    SupportedFieldType explicitType = null;

    if ((presentationAttribute != null) && (presentationAttribute instanceof BasicFieldMetadata)) {
      explicitType = ((BasicFieldMetadata) presentationAttribute).getExplicitFieldType();
    }

    Class<?> returnedClass = type.getReturnedClass();

checkProp:  {
      if (type.isComponentType() && includeField) {
        buildComponentProperties(
          targetClass,
          foreignField,
          additionalForeignFields,
          additionalNonPersistentProperties,
          mergedPropertyType,
          fields,
          idProperty,
          populateManyToOneFields,
          includeFields,
          excludeFields,
          configurationKey,
          ceilingEntityFullyQualifiedClassname,
          propertyName,
          type,
          returnedClass,
          parentClasses,
          amIExcluded,
          prefix);

        break checkProp;
      }

      /*
       * Currently we do not support ManyToOne fields whose class type is the same
       * as the target type, since this forms an infinite loop and will cause a stack overflow.
       */
      if (type.isEntityType()
            && !returnedClass.isAssignableFrom(targetClass)
            && populateManyToOneFields
            && includeField) {
        buildEntityProperties(
          fields,
          foreignField,
          additionalForeignFields,
          additionalNonPersistentProperties,
          populateManyToOneFields,
          includeFields,
          excludeFields,
          configurationKey,
          ceilingEntityFullyQualifiedClassname,
          propertyName,
          returnedClass,
          targetClass,
          parentClasses,
          prefix,
          amIExcluded);

        break checkProp;
      }
    }

    // Don't include this property if it failed manyToOne inclusion and is not a specified foreign key
    if (includeField || propertyForeignKey || (additionalForeignKeyIndexPosition >= 0)) {
      defaultFieldMetadataProvider.addMetadataFromFieldType(
        new AddMetadataFromFieldTypeRequest(field, targetClass, foreignField, additionalForeignFields,
          mergedPropertyType, componentProperties, idProperty, prefix, propertyName, type,
          propertyForeignKey, additionalForeignKeyIndexPosition, presentationAttributes,
          presentationAttribute, explicitType, returnedClass, this), fields);
    }
  } // end method buildBasicProperty

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  targetClass                           DOCUMENT ME!
   * @param  foreignField                          DOCUMENT ME!
   * @param  additionalForeignFields               DOCUMENT ME!
   * @param  additionalNonPersistentProperties     DOCUMENT ME!
   * @param  mergedPropertyType                    DOCUMENT ME!
   * @param  fields                                DOCUMENT ME!
   * @param  idProperty                            DOCUMENT ME!
   * @param  populateManyToOneFields               DOCUMENT ME!
   * @param  includeFields                         DOCUMENT ME!
   * @param  excludeFields                         DOCUMENT ME!
   * @param  configurationKey                      DOCUMENT ME!
   * @param  ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   * @param  propertyName                          DOCUMENT ME!
   * @param  type                                  DOCUMENT ME!
   * @param  returnedClass                         DOCUMENT ME!
   * @param  parentClasses                         DOCUMENT ME!
   * @param  isParentExcluded                      DOCUMENT ME!
   * @param  prefix                                DOCUMENT ME!
   */
  protected void buildComponentProperties(Class<?> targetClass,
    ForeignKey foreignField,
    ForeignKey[] additionalForeignFields,
    String[] additionalNonPersistentProperties,
    MergedPropertyType mergedPropertyType, Map<String, FieldMetadata> fields,
    String idProperty,
    Boolean populateManyToOneFields,
    String[] includeFields,
    String[] excludeFields,
    String configurationKey,
    String ceilingEntityFullyQualifiedClassname,
    String propertyName,
    Type type, Class<?> returnedClass, List<Class<?>> parentClasses,
    Boolean isParentExcluded,
    String prefix) {
    String[]     componentProperties    = ((ComponentType) type).getPropertyNames();
    List<String> componentPropertyNames = Arrays.asList(componentProperties);
    Type[]       componentTypes         = ((ComponentType) type).getSubtypes();
    List<Type>   componentPropertyTypes = Arrays.asList(componentTypes);
    String       tempPrefix             = "";
    int          pos                    = prefix.indexOf(".");

    if ((pos > 0) && (pos < (prefix.length() - 1))) {
      // only use part of the prefix if it's more than one layer deep
      tempPrefix = prefix.substring(pos + 1, prefix.length());
    }

    Map<String, FieldMetadata> componentPresentationAttributes = metadata.getFieldPresentationAttributes(targetClass,
        returnedClass, this, tempPrefix + propertyName + ".");

    if (isParentExcluded) {
      for (String key : componentPresentationAttributes.keySet()) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("buildComponentProperties:Excluding " + key + " because the parent was excluded");
        }

        componentPresentationAttributes.get(key).setExcluded(true);
      }
    }

    PersistentClass persistentClass = getPersistentClass(targetClass.getName());
    Property        property;

    try {
      property = persistentClass.getProperty(propertyName);
    } catch (MappingException e) {
      property = persistentClass.getProperty(prefix + propertyName);
    }

    Iterator       componentPropertyIterator = ((org.hibernate.mapping.Component) property.getValue())
      .getPropertyIterator();
    List<Property> componentPropertyList     = new ArrayList<Property>();

    while (componentPropertyIterator.hasNext()) {
      componentPropertyList.add((Property) componentPropertyIterator.next());
    }

    Map<String, FieldMetadata> newFields = new HashMap<String, FieldMetadata>();
    buildProperties(
      targetClass,
      foreignField,
      additionalForeignFields,
      additionalNonPersistentProperties,
      mergedPropertyType,
      componentPresentationAttributes,
      componentPropertyList,
      newFields,
      componentPropertyNames,
      componentPropertyTypes,
      idProperty,
      populateManyToOneFields,
      includeFields,
      excludeFields,
      configurationKey,
      ceilingEntityFullyQualifiedClassname,
      parentClasses,
      propertyName + ".",
      isParentExcluded);

    Map<String, FieldMetadata> convertedFields = new HashMap<String, FieldMetadata>();

    for (String key : newFields.keySet()) {
      convertedFields.put(propertyName + "." + key, newFields.get(key));
    }

    fields.putAll(convertedFields);
  } // end method buildComponentProperties

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fields                                DOCUMENT ME!
   * @param  foreignField                          DOCUMENT ME!
   * @param  additionalForeignFields               DOCUMENT ME!
   * @param  additionalNonPersistentProperties     DOCUMENT ME!
   * @param  populateManyToOneFields               DOCUMENT ME!
   * @param  includeFields                         DOCUMENT ME!
   * @param  excludeFields                         DOCUMENT ME!
   * @param  configurationKey                      DOCUMENT ME!
   * @param  ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   * @param  propertyName                          DOCUMENT ME!
   * @param  returnedClass                         DOCUMENT ME!
   * @param  targetClass                           DOCUMENT ME!
   * @param  parentClasses                         DOCUMENT ME!
   * @param  prefix                                DOCUMENT ME!
   * @param  isParentExcluded                      DOCUMENT ME!
   */
  protected void buildEntityProperties(Map<String, FieldMetadata> fields,
    ForeignKey foreignField,
    ForeignKey[] additionalForeignFields,
    String[] additionalNonPersistentProperties,
    Boolean populateManyToOneFields,
    String[] includeFields,
    String[] excludeFields,
    String configurationKey,
    String ceilingEntityFullyQualifiedClassname,
    String propertyName, Class<?> returnedClass, Class<?> targetClass, List<Class<?>> parentClasses,
    String prefix,
    Boolean isParentExcluded) {
    Class<?>[]     polymorphicEntities = getAllPolymorphicEntitiesFromCeiling(returnedClass);
    List<Class<?>> clonedParentClasses = new ArrayList<Class<?>>();

    for (Class<?> parentClass : parentClasses) {
      clonedParentClasses.add(parentClass);
    }

    clonedParentClasses.add(targetClass);

    Map<String, FieldMetadata> newFields = getMergedPropertiesRecursively(
        ceilingEntityFullyQualifiedClassname,
        polymorphicEntities,
        foreignField,
        additionalNonPersistentProperties,
        additionalForeignFields,
        MergedPropertyType.PRIMARY,
        populateManyToOneFields,
        includeFields,
        excludeFields,
        configurationKey,
        clonedParentClasses,
        prefix + propertyName + '.',
        isParentExcluded);

    for (FieldMetadata newMetadata : newFields.values()) {
      newMetadata.setInheritedFromType(targetClass.getName());
      newMetadata.setAvailableToTypes(new String[] { targetClass.getName() });
    }

    Map<String, FieldMetadata> convertedFields = new HashMap<String, FieldMetadata>(newFields.size());

    for (Map.Entry<String, FieldMetadata> key : newFields.entrySet()) {
      convertedFields.put(propertyName + '.' + key.getKey(), key.getValue());
    }

    fields.putAll(convertedFields);
  } // end method buildEntityProperties

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  targetClass                           DOCUMENT ME!
   * @param  foreignField                          DOCUMENT ME!
   * @param  additionalForeignFields               DOCUMENT ME!
   * @param  additionalNonPersistentProperties     DOCUMENT ME!
   * @param  mergedPropertyType                    DOCUMENT ME!
   * @param  presentationAttributes                DOCUMENT ME!
   * @param  componentProperties                   DOCUMENT ME!
   * @param  fields                                DOCUMENT ME!
   * @param  propertyNames                         DOCUMENT ME!
   * @param  propertyTypes                         DOCUMENT ME!
   * @param  idProperty                            DOCUMENT ME!
   * @param  populateManyToOneFields               DOCUMENT ME!
   * @param  includeFields                         DOCUMENT ME!
   * @param  excludeFields                         DOCUMENT ME!
   * @param  configurationKey                      DOCUMENT ME!
   * @param  ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   * @param  parentClasses                         DOCUMENT ME!
   * @param  prefix                                DOCUMENT ME!
   * @param  isParentExcluded                      DOCUMENT ME!
   */
  protected void buildProperties(Class<?> targetClass,
    ForeignKey foreignField,
    ForeignKey[] additionalForeignFields,
    String[] additionalNonPersistentProperties,
    MergedPropertyType mergedPropertyType, Map<String, FieldMetadata> presentationAttributes,
    List<Property> componentProperties, Map<String, FieldMetadata> fields, List<String> propertyNames,
    List<Type> propertyTypes,
    String idProperty,
    Boolean populateManyToOneFields,
    String[] includeFields,
    String[] excludeFields,
    String configurationKey,
    String ceilingEntityFullyQualifiedClassname, List<Class<?>> parentClasses,
    String prefix,
    Boolean isParentExcluded) {
    int                j                   = 0;
    Comparator<String> propertyComparator  = new Comparator<String>() {
      @Override public int compare(String o1, String o2) {
        // check for property name equality and for map field properties
        if (o1.equals(o2) || o1.startsWith(o2 + FieldManager.MAPFIELDSEPARATOR)
              || o2.startsWith(o1 + FieldManager.MAPFIELDSEPARATOR)) {
          return 0;
        }

        return o1.compareTo(o2);
      }
    };

    List<String> presentationKeyList       = new ArrayList<String>(presentationAttributes.keySet());
    Collections.sort(presentationKeyList);

    for (String propertyName : propertyNames) {
      final Type type                              = propertyTypes.get(j);
      boolean    isPropertyForeignKey              = testForeignProperty(foreignField, prefix, propertyName);
      int        additionalForeignKeyIndexPosition = findAdditionalForeignKeyIndex(additionalForeignFields, prefix,
          propertyName);
      j++;

      Field myField = getFieldManager().getField(targetClass, propertyName);

      if (myField == null) {
        // try to get the field with the prefix - needed for advanced collections that appear in @Embedded classes
        myField = getFieldManager().getField(targetClass, prefix + propertyName);
      }

      if ((!type.isAnyType() && !type.isCollectionType())
            || isPropertyForeignKey
            || (additionalForeignKeyIndexPosition >= 0)
            || (Collections.binarySearch(presentationKeyList, propertyName, propertyComparator) >= 0)) {
        if (myField != null) {
          boolean handled = false;

          for (FieldMetadataProvider provider : fieldMetadataProviders) {
            FieldMetadata presentationAttribute = presentationAttributes.get(propertyName);

            if (presentationAttribute != null) {
              setExcludedBasedOnShowIfProperty(presentationAttribute);
            }

            FieldProviderResponse response = provider.addMetadataFromFieldType(
                new AddMetadataFromFieldTypeRequest(myField, targetClass, foreignField, additionalForeignFields,
                  mergedPropertyType, componentProperties, idProperty, prefix,
                  propertyName, type, isPropertyForeignKey, additionalForeignKeyIndexPosition,
                  presentationAttributes, presentationAttribute, null, type.getReturnedClass(), this), fields);

            if (FieldProviderResponse.NOT_HANDLED != response) {
              handled = true;
            }

            if (FieldProviderResponse.HANDLED_BREAK == response) {
              break;
            }
          }

          if (!handled) {
            buildBasicProperty(myField, targetClass, foreignField, additionalForeignFields,
              additionalNonPersistentProperties, mergedPropertyType, presentationAttributes,
              componentProperties, fields, idProperty, populateManyToOneFields, includeFields,
              excludeFields, configurationKey, ceilingEntityFullyQualifiedClassname, parentClasses,
              prefix, isParentExcluded, propertyName, type, isPropertyForeignKey, additionalForeignKeyIndexPosition);
          }
        } // end if
      } // end if
    } // end for
  } // end method buildProperties

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entities                              DOCUMENT ME!
   * @param   foreignField                          DOCUMENT ME!
   * @param   additionalNonPersistentProperties     DOCUMENT ME!
   * @param   additionalForeignFields               DOCUMENT ME!
   * @param   mergedPropertyType                    DOCUMENT ME!
   * @param   populateManyToOneFields               DOCUMENT ME!
   * @param   includeFields                         DOCUMENT ME!
   * @param   excludeFields                         DOCUMENT ME!
   * @param   configurationKey                      DOCUMENT ME!
   * @param   ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   * @param   mergedProperties                      DOCUMENT ME!
   * @param   parentClasses                         DOCUMENT ME!
   * @param   prefix                                DOCUMENT ME!
   * @param   isParentExcluded                      DOCUMENT ME!
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  protected void buildPropertiesFromPolymorphicEntities(Class<?>[] entities,
    ForeignKey foreignField,
    String[] additionalNonPersistentProperties,
    ForeignKey[] additionalForeignFields,
    MergedPropertyType mergedPropertyType,
    Boolean populateManyToOneFields,
    String[] includeFields,
    String[] excludeFields,
    String configurationKey,
    String ceilingEntityFullyQualifiedClassname, Map<String, FieldMetadata> mergedProperties,
    List<Class<?>> parentClasses,
    String prefix,
    Boolean isParentExcluded) {
    for (Class<?> clazz : entities) {
      String cacheKey = getCacheKey(foreignField, additionalNonPersistentProperties, additionalForeignFields,
          mergedPropertyType, populateManyToOneFields, clazz, configurationKey, isParentExcluded);

      Map<String, FieldMetadata> cacheData = null;

      synchronized (DynamicDaoHelperImpl.LOCK_OBJECT) {
        if (useCache()) {
          cacheData = METADATA_CACHE.get(cacheKey);
        }

        if (cacheData == null) {
          Map<String, FieldMetadata> props = getPropertiesForEntityClass(
              clazz,
              foreignField,
              additionalNonPersistentProperties,
              additionalForeignFields,
              mergedPropertyType,
              populateManyToOneFields,
              includeFields,
              excludeFields,
              configurationKey,
              ceilingEntityFullyQualifiedClassname,
              parentClasses,
              prefix,
              isParentExcluded);

          // first check all the properties currently in there to see if my entity inherits from them
          for (Class<?> clazz2 : entities) {
            if (!clazz2.getName().equals(clazz.getName())) {
              for (Map.Entry<String, FieldMetadata> entry : props.entrySet()) {
                FieldMetadata metadata = entry.getValue();

                try {
                  if (Class.forName(metadata.getInheritedFromType()).isAssignableFrom(clazz2)) {
                    String[] both = (String[]) ArrayUtils.addAll(metadata.getAvailableToTypes(),
                        new String[] { clazz2.getName() });
                    metadata.setAvailableToTypes(both);
                  }
                } catch (ClassNotFoundException e) {
                  throw new RuntimeException(e);
                }
              }
            }
          }

          METADATA_CACHE.put(cacheKey, props);
          cacheData = props;
        } // end if
      } // end synchronized

      // clone the metadata before passing to the system
      Map<String, FieldMetadata> clonedCache = new HashMap<String, FieldMetadata>(cacheData.size());

      for (Map.Entry<String, FieldMetadata> entry : cacheData.entrySet()) {
        clonedCache.put(entry.getKey(), entry.getValue().cloneFieldMetadata());
      }

      mergedProperties.putAll(clonedCache);
    } // end for
  } // end method buildPropertiesFromPolymorphicEntities

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  clazz   DOCUMENT ME!
   * @param  myTree  DOCUMENT ME!
   */
  protected void createClassTreeFromAnnotation(Class<?> clazz, ClassTree myTree) {
    AdminPresentationClass classPresentation = clazz.getAnnotation(AdminPresentationClass.class);

    if (classPresentation != null) {
      String friendlyName = classPresentation.friendlyName();

      if (!StringUtils.isEmpty(friendlyName)) {
        myTree.setFriendlyName(friendlyName);
      }
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   additionalForeignFields  DOCUMENT ME!
   * @param   prefix                   DOCUMENT ME!
   * @param   propertyName             DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected int findAdditionalForeignKeyIndex(ForeignKey[] additionalForeignFields, String prefix,
    String propertyName) {
    int additionalForeignKeyIndexPosition = -1;

    if (additionalForeignFields != null) {
      additionalForeignKeyIndexPosition = Arrays.binarySearch(additionalForeignFields,
          new ForeignKey(prefix + propertyName, null, null), new Comparator<ForeignKey>() {
            @Override public int compare(ForeignKey o1, ForeignKey o2) {
              return o1.getManyToField().compareTo(o2.getManyToField());
            }
          });
    }

    return additionalForeignKeyIndexPosition;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   foreignField                       DOCUMENT ME!
   * @param   additionalNonPersistentProperties  DOCUMENT ME!
   * @param   additionalForeignFields            DOCUMENT ME!
   * @param   mergedPropertyType                 DOCUMENT ME!
   * @param   populateManyToOneFields            DOCUMENT ME!
   * @param   clazz                              DOCUMENT ME!
   * @param   configurationKey                   DOCUMENT ME!
   * @param   isParentExcluded                   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  protected String getCacheKey(ForeignKey foreignField, String[] additionalNonPersistentProperties,
    ForeignKey[] additionalForeignFields, MergedPropertyType mergedPropertyType, Boolean populateManyToOneFields,
    Class<?> clazz, String configurationKey, Boolean isParentExcluded) {
    StringBuilder sb = new StringBuilder(150);
    sb.append(clazz.hashCode());
    sb.append((foreignField == null) ? "" : foreignField.toString());
    sb.append(configurationKey);
    sb.append(isParentExcluded);

    if (additionalNonPersistentProperties != null) {
      for (String prop : additionalNonPersistentProperties) {
        sb.append(prop);
      }
    }

    if (additionalForeignFields != null) {
      for (ForeignKey key : additionalForeignFields) {
        sb.append(key.toString());
      }
    }

    sb.append(mergedPropertyType);
    sb.append(populateManyToOneFields);

    String digest;

    try {
      MessageDigest md            = MessageDigest.getInstance("MD5");
      byte[]        messageDigest = md.digest(sb.toString().getBytes());
      BigInteger    number        = new BigInteger(1, messageDigest);
      digest = number.toString(16);
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }

    return pad(digest, 32, '0');
  } // end method getCacheKey

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
   * @param   includeFields                         DOCUMENT ME!
   * @param   excludeFields                         DOCUMENT ME!
   * @param   configurationKey                      DOCUMENT ME!
   * @param   parentClasses                         DOCUMENT ME!
   * @param   prefix                                DOCUMENT ME!
   * @param   isParentExcluded                      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Map<String, FieldMetadata> getMergedPropertiesRecursively(
    final String             ceilingEntityFullyQualifiedClassname,
    final Class<?>[]         entities,
    final ForeignKey         foreignField,
    final String[]           additionalNonPersistentProperties,
    final ForeignKey[]       additionalForeignFields,
    final MergedPropertyType mergedPropertyType,
    final Boolean            populateManyToOneFields,
    final String[]           includeFields,
    final String[]           excludeFields,
    final String             configurationKey,
    final List<Class<?>>     parentClasses,
    final String             prefix,
    final Boolean            isParentExcluded) {
    PropertyBuilder propertyBuilder = new PropertyBuilder() {
      @Override public Map<String, FieldMetadata> execute(Boolean overridePopulateManyToOne) {
        Map<String, FieldMetadata> mergedProperties                      = new HashMap<String, FieldMetadata>();
        Boolean                    classAnnotatedPopulateManyToOneFields;

        if (overridePopulateManyToOne != null) {
          classAnnotatedPopulateManyToOneFields = overridePopulateManyToOne;
        } else {
          classAnnotatedPopulateManyToOneFields = populateManyToOneFields;
        }

        buildPropertiesFromPolymorphicEntities(
          entities,
          foreignField,
          additionalNonPersistentProperties,
          additionalForeignFields,
          mergedPropertyType,
          classAnnotatedPopulateManyToOneFields,
          includeFields,
          excludeFields,
          configurationKey,
          ceilingEntityFullyQualifiedClassname,
          mergedProperties,
          parentClasses,
          prefix,
          isParentExcluded);

        return mergedProperties;
      } // end method execute
    };

    Map<String, FieldMetadata> mergedProperties = metadata.overrideMetadata(entities, propertyBuilder, prefix,
        isParentExcluded, ceilingEntityFullyQualifiedClassname, configurationKey, this);
    applyIncludesAndExcludes(includeFields, excludeFields, prefix, isParentExcluded, mergedProperties);
    applyForeignKeyPrecedence(foreignField, additionalForeignFields, mergedProperties);

    return mergedProperties;
  } // end method getMergedPropertiesRecursively

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   targetClass                           DOCUMENT ME!
   * @param   foreignField                          DOCUMENT ME!
   * @param   additionalNonPersistentProperties     DOCUMENT ME!
   * @param   additionalForeignFields               DOCUMENT ME!
   * @param   mergedPropertyType                    DOCUMENT ME!
   * @param   populateManyToOneFields               DOCUMENT ME!
   * @param   includeFields                         DOCUMENT ME!
   * @param   excludeFields                         DOCUMENT ME!
   * @param   configurationKey                      DOCUMENT ME!
   * @param   ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   * @param   parentClasses                         DOCUMENT ME!
   * @param   prefix                                DOCUMENT ME!
   * @param   isParentExcluded                      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  IllegalArgumentException  DOCUMENT ME!
   */
  protected Map<String, FieldMetadata> getPropertiesForEntityClass(Class<?> targetClass,
    ForeignKey foreignField,
    String[] additionalNonPersistentProperties,
    ForeignKey[] additionalForeignFields,
    MergedPropertyType mergedPropertyType,
    Boolean populateManyToOneFields,
    String[] includeFields,
    String[] excludeFields,
    String configurationKey,
    String ceilingEntityFullyQualifiedClassname, List<Class<?>> parentClasses,
    String prefix,
    Boolean isParentExcluded) {
    Map<String, FieldMetadata> presentationAttributes = metadata.getFieldPresentationAttributes(null, targetClass, this,
        "");

    if (isParentExcluded) {
      for (String key : presentationAttributes.keySet()) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("getPropertiesForEntityClass:Excluding " + key + " because parent is excluded.");
        }

        presentationAttributes.get(key).setExcluded(true);
      }
    }

    Map                        idMetadata    = getIdMetadata(targetClass);
    Map<String, FieldMetadata> fields        = new HashMap<String, FieldMetadata>();
    String                     idProperty    = (String) idMetadata.get("name");
    List<String>               propertyNames = getPropertyNames(targetClass);
    propertyNames.add(idProperty);

    Type       idType        = (Type) idMetadata.get("type");
    List<Type> propertyTypes = getPropertyTypes(targetClass);
    propertyTypes.add(idType);

    PersistentClass persistentClass = getPersistentClass(targetClass.getName());
    Iterator        testIter        = persistentClass.getPropertyIterator();
    List<Property>  propertyList    = new ArrayList<Property>();

    // check the properties for problems
    while (testIter.hasNext()) {
      Property property = (Property) testIter.next();

      if (property.getName().contains(".")) {
        throw new IllegalArgumentException(
          "Properties from entities that utilize a period character ('.') in their name are incompatible with this system. The property name in question is: ("
          + property.getName() + ") from the class: (" + targetClass.getName() + ")");
      }

      propertyList.add(property);
    }

    buildProperties(
      targetClass,
      foreignField,
      additionalForeignFields,
      additionalNonPersistentProperties,
      mergedPropertyType,
      presentationAttributes,
      propertyList,
      fields,
      propertyNames,
      propertyTypes,
      idProperty,
      populateManyToOneFields,
      includeFields,
      excludeFields,
      configurationKey,
      ceilingEntityFullyQualifiedClassname,
      parentClasses,
      prefix,
      isParentExcluded);

    BasicFieldMetadata presentationAttribute = new BasicFieldMetadata();
    presentationAttribute.setExplicitFieldType(SupportedFieldType.STRING);
    presentationAttribute.setVisibility(VisibilityEnum.HIDDEN_ALL);

    if (!ArrayUtils.isEmpty(additionalNonPersistentProperties)) {
      Class<?>[] entities = getAllPolymorphicEntitiesFromCeiling(targetClass);

      for (String additionalNonPersistentProperty : additionalNonPersistentProperties) {
        if (StringUtils.isEmpty(prefix)
              || (!StringUtils.isEmpty(prefix) && additionalNonPersistentProperty.startsWith(prefix))) {
          String myAdditionalNonPersistentProperty = additionalNonPersistentProperty;

          // get final property if this is a dot delimited property
          int finalDotPos = additionalNonPersistentProperty.lastIndexOf('.');

          if (finalDotPos >= 0) {
            myAdditionalNonPersistentProperty = myAdditionalNonPersistentProperty.substring(finalDotPos + 1,
                myAdditionalNonPersistentProperty.length());
          }

          // check all the polymorphic types on this target class to see if the end property exists
          Field  testField  = null;
          Method testMethod = null;

          for (Class<?> clazz : entities) {
            try {
              testMethod = clazz.getMethod(myAdditionalNonPersistentProperty);

              if (testMethod != null) {
                break;
              }
            } catch (NoSuchMethodException e) {
              // do nothing - method does not exist
            }

            testField = getFieldManager().getField(clazz, myAdditionalNonPersistentProperty);

            if (testField != null) {
              break;
            }
          }

          // if the property exists, add it to the metadata for this class
          if ((testField != null) || (testMethod != null)) {
            fields.put(additionalNonPersistentProperty,
              metadata.getFieldMetadata(prefix, additionalNonPersistentProperty, propertyList,
                SupportedFieldType.STRING, null, targetClass, presentationAttribute, mergedPropertyType, this));
          }
        } // end if
      } // end for
    } // end if

    return fields;
  } // end method getPropertiesForEntityClass

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   clazz  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected boolean isExcludeClassFromPolymorphism(Class<?> clazz) {
    return dynamicDaoHelper.isExcludeClassFromPolymorphism(clazz);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   s       DOCUMENT ME!
   * @param   length  DOCUMENT ME!
   * @param   pad     DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected String pad(String s, int length, char pad) {
    StringBuilder buffer = new StringBuilder(s);

    while (buffer.length() < length) {
      buffer.insert(0, pad);
    }

    return buffer.toString();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fieldMetadata  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected boolean setExcludedBasedOnShowIfProperty(FieldMetadata fieldMetadata) {
    if ((fieldMetadata != null) && (fieldMetadata.getShowIfProperty() != null)
          && !fieldMetadata.getShowIfProperty().equals("")
          && (appConfigurationRemoteService.getBooleanPropertyValue(fieldMetadata.getShowIfProperty()) != null)
          && !appConfigurationRemoteService.getBooleanPropertyValue(fieldMetadata.getShowIfProperty())) {
      // do not include this in the display if it returns false.
      fieldMetadata.setExcluded(true);

      return false;
    }

    return true;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   foreignField  DOCUMENT ME!
   * @param   prefix        DOCUMENT ME!
   * @param   propertyName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected boolean testForeignProperty(ForeignKey foreignField, String prefix, String propertyName) {
    boolean isPropertyForeignKey = false;

    if (foreignField != null) {
      isPropertyForeignKey = foreignField.getManyToField().equals(prefix + propertyName);
    }

    return isPropertyForeignKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   prefix                                DOCUMENT ME!
   * @param   parentClasses                         DOCUMENT ME!
   * @param   propertyName                          DOCUMENT ME!
   * @param   targetClass                           DOCUMENT ME!
   * @param   ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  protected Boolean testPropertyRecursion(String prefix, List<Class<?>> parentClasses, String propertyName,
    Class<?> targetClass,
    String ceilingEntityFullyQualifiedClassname) {
    Boolean includeField = true;

    if (!StringUtils.isEmpty(prefix)) {
      Field testField = getFieldManager().getField(targetClass, propertyName);

      if (testField == null) {
        Class<?>[] entities;

        try {
          entities = getAllPolymorphicEntitiesFromCeiling(Class.forName(ceilingEntityFullyQualifiedClassname));
        } catch (ClassNotFoundException e) {
          throw new RuntimeException(e);
        }

        for (Class<?> clazz : entities) {
          testField = getFieldManager().getField(clazz, propertyName);

          if (testField != null) {
            break;
          }
        }

        String testProperty = prefix + propertyName;

        if (testField == null) {
          testField = getFieldManager().getField(targetClass, testProperty);
        }

        if (testField == null) {
          for (Class<?> clazz : entities) {
            testField = getFieldManager().getField(clazz, testProperty);

            if (testField != null) {
              break;
            }
          }
        }
      } // end if

      if (testField != null) {
        Class<?> testType = testField.getType();

        for (Class<?> parentClass : parentClasses) {
          if (parentClass.isAssignableFrom(testType) || testType.isAssignableFrom(parentClass)) {
            includeField = false;

            break;
          }
        }

        if (includeField && (targetClass.isAssignableFrom(testType) || testType.isAssignableFrom(targetClass))) {
          includeField = false;
        }
      }
    } // end if

    return includeField;
  } // end method testPropertyRecursion

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected boolean useCache() {
    if (cacheEntityMetaDataTtl < 0) {
      return true;
    }

    if (cacheEntityMetaDataTtl == 0) {
      return false;
    } else {
      if ((System.currentTimeMillis() - lastCacheFlushTime) > cacheEntityMetaDataTtl) {
        lastCacheFlushTime = System.currentTimeMillis();
        METADATA_CACHE.clear();
        DynamicDaoHelperImpl.POLYMORPHIC_ENTITY_CACHE.clear();
        DynamicDaoHelperImpl.POLYMORPHIC_ENTITY_CACHE_WO_EXCLUSIONS.clear();

        return true; // cache is empty
      } else {
        return true;
      }
    }
  }

} // end class DynamicEntityDaoImpl
