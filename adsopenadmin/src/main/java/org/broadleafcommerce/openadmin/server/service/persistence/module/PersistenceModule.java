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

package org.broadleafcommerce.openadmin.server.service.persistence.module;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.presentation.client.OperationType;

import org.broadleafcommerce.openadmin.dto.CriteriaTransferObject;
import org.broadleafcommerce.openadmin.dto.DynamicResultSet;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.MergedPropertyType;
import org.broadleafcommerce.openadmin.dto.PersistencePackage;
import org.broadleafcommerce.openadmin.dto.Property;
import org.broadleafcommerce.openadmin.server.service.persistence.PersistenceManager;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface PersistenceModule {
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
   * @param  inheritanceLine   DOCUMENT ME!
   * @param  mergedProperties  DOCUMENT ME!
   * @param  properties        DOCUMENT ME!
   */
  void extractProperties(Class<?>[] inheritanceLine,
    Map<MergedPropertyType, Map<String, FieldMetadata>> mergedProperties, List<Property> properties);

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
   * @param   operationType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isCompatible(OperationType operationType);

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
   * @param  persistenceManager  DOCUMENT ME!
   */
  void setPersistenceManager(PersistenceManager persistenceManager);

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

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage   DOCUMENT ME!
   * @param   allMergedProperties  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  void updateMergedProperties(PersistencePackage persistencePackage,
    Map<MergedPropertyType, Map<String, FieldMetadata>> allMergedProperties) throws ServiceException;

} // end interface PersistenceModule
