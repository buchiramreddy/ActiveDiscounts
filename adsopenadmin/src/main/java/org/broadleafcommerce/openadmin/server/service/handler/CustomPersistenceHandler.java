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

import org.springframework.core.Ordered;


/**
 * Custom Persistence Handlers provide a hook to override the normal persistence behavior of the admin application. This
 * is useful when an alternate pathway for working with persisted data is desirable. For example, if you want to work
 * directly with a service API, rather than go through the standard admin persistence pipeline. In such a case, you can
 * use Spring to inject an instance of your service into your custom persistence handler and utilize that service to
 * work with your entity. The implementation is responsible for converting domain object into the return type required
 * by the admin. Helper classes are passed in to assist with conversion operations.
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public interface CustomPersistenceHandler extends Ordered {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  int DEFAULT_ORDER = Integer.MAX_VALUE;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage  DOCUMENT ME!
   * @param   dynamicEntityDao    DOCUMENT ME!
   * @param   helper              DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper)
    throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Is this persistence handler capable of dealing with an add request from the admin.
   *
   * @param   persistencePackage  details about the add operation
   *
   * @return  whether or not this handler supports adds
   */
  Boolean canHandleAdd(PersistencePackage persistencePackage);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Is this persistence handler capable of dealing with an fetch request from the admin.
   *
   * @param   persistencePackage  details about the fetch operation
   *
   * @return  whether or not this handler supports fetches
   */
  Boolean canHandleFetch(PersistencePackage persistencePackage);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Is this persistence handler capable of dealing with an inspect request from the admin.
   *
   * @param   persistencePackage  details about the inspect operation
   *
   * @return  whether or not this handler supports inspects
   */
  Boolean canHandleInspect(PersistencePackage persistencePackage);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Is this persistence handler capable of dealing with a remove request from the admin.
   *
   * @param   persistencePackage  details about the remove operation
   *
   * @return  whether or not this handler supports remove
   */
  Boolean canHandleRemove(PersistencePackage persistencePackage);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Is this persistence handler capable of dealing with an update request from the admin.
   *
   * @param   persistencePackage  details about the update operation
   *
   * @return  whether or not this handler supports updatess
   */
  Boolean canHandleUpdate(PersistencePackage persistencePackage);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage  DOCUMENT ME!
   * @param   cto                 DOCUMENT ME!
   * @param   dynamicEntityDao    DOCUMENT ME!
   * @param   helper              DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  DynamicResultSet fetch(PersistencePackage persistencePackage, CriteriaTransferObject cto,
    DynamicEntityDao dynamicEntityDao, RecordHelper helper) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage  DOCUMENT ME!
   * @param   dynamicEntityDao    DOCUMENT ME!
   * @param   helper              DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  DynamicResultSet inspect(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao,
    InspectHelper helper) throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage  DOCUMENT ME!
   * @param   dynamicEntityDao    DOCUMENT ME!
   * @param   helper              DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  void remove(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper)
    throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage  DOCUMENT ME!
   * @param   dynamicEntityDao    DOCUMENT ME!
   * @param   helper              DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  ServiceException  DOCUMENT ME!
   */
  Entity update(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao, RecordHelper helper)
    throws ServiceException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   persistencePackage  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean willHandleSecurity(PersistencePackage persistencePackage);

} // end interface CustomPersistenceHandler
