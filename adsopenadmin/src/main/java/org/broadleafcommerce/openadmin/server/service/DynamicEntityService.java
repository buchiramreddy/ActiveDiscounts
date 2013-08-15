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

package org.broadleafcommerce.openadmin.server.service;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.openadmin.dto.CriteriaTransferObject;
import org.broadleafcommerce.openadmin.dto.DynamicResultSet;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.PersistencePackage;

import org.springframework.security.access.annotation.Secured;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface DynamicEntityService {
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
  @Secured("PERMISSION_OTHER_DEFAULT")
  Entity add(PersistencePackage persistencePackage) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  // @Secured("PERMISSION_OTHER_DEFAULT")
  // public BatchDynamicResultSet batchInspect(BatchPersistencePackage batchPersistencePackage) throws ServiceException;

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
  @Secured("PERMISSION_OTHER_DEFAULT")
  DynamicResultSet fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto) throws ServiceException;

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
  @Secured("PERMISSION_OTHER_DEFAULT")
  DynamicResultSet inspect(PersistencePackage persistencePackage) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  @Secured("PERMISSION_OTHER_DEFAULT")
  void remove(PersistencePackage persistencePackage) throws ServiceException;

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
  @Secured("PERMISSION_OTHER_DEFAULT")
  Entity update(PersistencePackage persistencePackage) throws ServiceException;

} // end interface DynamicEntityService
