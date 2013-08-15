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

package org.broadleafcommerce.openadmin.server.service.handler;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.openadmin.dto.CriteriaTransferObject;
import org.broadleafcommerce.openadmin.dto.DynamicResultSet;
import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.PersistencePackage;
import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;
import org.broadleafcommerce.openadmin.server.service.persistence.module.InspectHelper;
import org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class CustomPersistenceHandlerAdapter implements CustomPersistenceHandler {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandler#add(org.broadleafcommerce.openadmin.dto.PersistencePackage,
   *       org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper)
   */
  @Override public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao,
    RecordHelper helper) throws ServiceException {
    throw new ServiceException("Add not supported");
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandler#canHandleAdd(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean canHandleAdd(PersistencePackage persistencePackage) {
    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandler#canHandleFetch(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean canHandleFetch(PersistencePackage persistencePackage) {
    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandler#canHandleInspect(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean canHandleInspect(PersistencePackage persistencePackage) {
    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandler#canHandleRemove(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean canHandleRemove(PersistencePackage persistencePackage) {
    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandler#canHandleUpdate(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean canHandleUpdate(PersistencePackage persistencePackage) {
    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandler#fetch(org.broadleafcommerce.openadmin.dto.PersistencePackage,
   *       org.broadleafcommerce.openadmin.dto.CriteriaTransferObject,
   *       org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper)
   */
  @Override public DynamicResultSet fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto,
    DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException {
    throw new ServiceException("Fetch not supported");
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.core.Ordered#getOrder()
   */
  @Override public int getOrder() {
    return CustomPersistenceHandler.DEFAULT_ORDER;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandler#inspect(org.broadleafcommerce.openadmin.dto.PersistencePackage,
   *       org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       org.broadleafcommerce.openadmin.server.service.persistence.module.InspectHelper)
   */
  @Override public DynamicResultSet inspect(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao,
    InspectHelper helper) throws ServiceException {
    throw new ServiceException("Inspect not supported");
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandler#remove(org.broadleafcommerce.openadmin.dto.PersistencePackage,
   *       org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper)
   */
  @Override public void remove(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao,
    RecordHelper helper) throws ServiceException {
    throw new ServiceException("Remove not supported");
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandler#update(org.broadleafcommerce.openadmin.dto.PersistencePackage,
   *       org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper)
   */
  @Override public Entity update(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao,
    RecordHelper helper) throws ServiceException {
    throw new ServiceException("Update not supported");
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandler#willHandleSecurity(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean willHandleSecurity(PersistencePackage persistencePackage) {
    return false;
  }
} // end class CustomPersistenceHandlerAdapter
