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

package org.broadleafcommerce.openadmin.server.service.persistence;

import java.lang.reflect.InvocationTargetException;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.openadmin.dto.ClassMetadata;
import org.broadleafcommerce.openadmin.dto.CriteriaTransferObject;
import org.broadleafcommerce.openadmin.dto.DynamicResultSet;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.MergedPropertyType;
import org.broadleafcommerce.openadmin.dto.PersistencePackage;
import org.broadleafcommerce.openadmin.dto.PersistencePerspective;
import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;
import org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandler;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface PersistenceManager {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  Entity add(PersistencePackage persistencePackage) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage  DOCUMENT ME!
   * @param   cto                 DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  DynamicResultSet fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto) throws ServiceException;

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
   * @return  DOCUMENT ME!
   */
  List<CustomPersistenceHandler> getCustomPersistenceHandlers();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  DynamicEntityDao getDynamicEntityDao();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entities          DOCUMENT ME!
   * @param   mergedProperties  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ClassNotFoundException    DOCUMENT ME!
   * @throws  IllegalArgumentException  DOCUMENT ME!
   */
  ClassMetadata getMergedClassMetadata(Class<?>[] entities,
    Map<MergedPropertyType, Map<String, FieldMetadata>> mergedProperties) throws ClassNotFoundException,
    IllegalArgumentException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   ceilingEntityFullyQualifiedClassname  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ClassNotFoundException  DOCUMENT ME!
   */
  Class<?>[] getPolymorphicEntities(String ceilingEntityFullyQualifiedClassname) throws ClassNotFoundException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   entityName              DOCUMENT ME!
   * @param   persistencePerspective  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ClassNotFoundException     DOCUMENT ME!
   * @throws  SecurityException          DOCUMENT ME!
   * @throws  IllegalArgumentException   DOCUMENT ME!
   * @throws  NoSuchMethodException      DOCUMENT ME!
   * @throws  IllegalAccessException     DOCUMENT ME!
   * @throws  InvocationTargetException  DOCUMENT ME!
   * @throws  NoSuchFieldException       DOCUMENT ME!
   */
  Map<String, FieldMetadata> getSimpleMergedProperties(String entityName, PersistencePerspective persistencePerspective)
    throws ClassNotFoundException, SecurityException, IllegalArgumentException, NoSuchMethodException,
      IllegalAccessException, InvocationTargetException, NoSuchFieldException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Map<String, String> getTargetEntityManagers();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  TargetModeType getTargetMode();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   testClass  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Class<?>[] getUpDownInheritance(Class<?> testClass);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   testClassname  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ClassNotFoundException  DOCUMENT ME!
   */
  Class<?>[] getUpDownInheritance(String testClassname) throws ClassNotFoundException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException        DOCUMENT ME!
   * @throws  ClassNotFoundException  DOCUMENT ME!
   */
  DynamicResultSet inspect(PersistencePackage persistencePackage) throws ServiceException, ClassNotFoundException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  void remove(PersistencePackage persistencePackage) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customPersistenceHandlers  DOCUMENT ME!
   */
  void setCustomPersistenceHandlers(List<CustomPersistenceHandler> customPersistenceHandlers);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  dynamicEntityDao  DOCUMENT ME!
   */
  void setDynamicEntityDao(DynamicEntityDao dynamicEntityDao);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  targetEntityManagers  DOCUMENT ME!
   */
  void setTargetEntityManagers(Map<String, String> targetEntityManagers);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  targetMode  DOCUMENT ME!
   */
  void setTargetMode(TargetModeType targetMode);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  Entity update(PersistencePackage persistencePackage) throws ServiceException;

  // public abstract void close() throws Exception;

} // end interface PersistenceManager
