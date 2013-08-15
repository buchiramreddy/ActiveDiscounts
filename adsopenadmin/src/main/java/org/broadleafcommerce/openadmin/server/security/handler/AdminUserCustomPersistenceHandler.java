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

package org.broadleafcommerce.openadmin.server.security.handler;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.exception.ServiceException;

import org.broadleafcommerce.openadmin.dto.Entity;
import org.broadleafcommerce.openadmin.dto.FieldMetadata;
import org.broadleafcommerce.openadmin.dto.PersistencePackage;
import org.broadleafcommerce.openadmin.dto.PersistencePerspective;
import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.remote.EntityOperationType;
import org.broadleafcommerce.openadmin.server.security.remote.SecurityVerifier;
import org.broadleafcommerce.openadmin.server.security.service.AdminSecurityService;
import org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter;
import org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class AdminUserCustomPersistenceHandler extends CustomPersistenceHandlerAdapter {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(AdminUserCustomPersistenceHandler.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blAdminSecurityRemoteService")
  protected SecurityVerifier adminRemoteSecurityService;

  /** DOCUMENT ME! */
  @Resource(name = "blAdminSecurityService")
  protected AdminSecurityService adminSecurityService;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#add(org.broadleafcommerce.openadmin.dto.PersistencePackage,
   *       org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper)
   */
  @Override public Entity add(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao,
    RecordHelper helper) throws ServiceException {
    adminRemoteSecurityService.securityCheck(persistencePackage.getCeilingEntityFullyQualifiedClassname(),
      EntityOperationType.ADD);

    Entity entity = persistencePackage.getEntity();

    try {
      PersistencePerspective     persistencePerspective = persistencePackage.getPersistencePerspective();
      AdminUser                  adminInstance          = (AdminUser) Class.forName(entity.getType()[0]).newInstance();
      Map<String, FieldMetadata> adminProperties        = helper.getSimpleMergedProperties(AdminUser.class.getName(),
          persistencePerspective);
      adminInstance = (AdminUser) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);
      adminInstance.setUnencodedPassword(adminInstance.getPassword());
      adminInstance.setPassword(null);

      adminInstance = adminSecurityService.saveAdminUser(adminInstance);

      Entity adminEntity = helper.getRecord(adminProperties, adminInstance, null, null);

      return adminEntity;
    } catch (Exception e) {
      throw new ServiceException("Unable to add entity for " + entity.getType()[0], e);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#canHandleAdd(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean canHandleAdd(PersistencePackage persistencePackage) {
    try {
      return (persistencePackage.getCeilingEntityFullyQualifiedClassname() != null)
        && AdminUser.class.isAssignableFrom(Class.forName(
            persistencePackage.getCeilingEntityFullyQualifiedClassname()));
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#canHandleUpdate(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean canHandleUpdate(PersistencePackage persistencePackage) {
    return canHandleAdd(persistencePackage);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#update(org.broadleafcommerce.openadmin.dto.PersistencePackage,
   *       org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       org.broadleafcommerce.openadmin.server.service.persistence.module.RecordHelper)
   */
  @Override public Entity update(PersistencePackage persistencePackage, DynamicEntityDao dynamicEntityDao,
    RecordHelper helper) throws ServiceException {
    Entity entity = persistencePackage.getEntity();

    try {
      PersistencePerspective     persistencePerspective = persistencePackage.getPersistencePerspective();
      Map<String, FieldMetadata> adminProperties        = helper.getSimpleMergedProperties(AdminUser.class.getName(),
          persistencePerspective);
      Object                     primaryKey             = helper.getPrimaryKey(entity, adminProperties);
      AdminUser                  adminInstance          = (AdminUser) dynamicEntityDao.retrieve(Class.forName(
            entity.getType()[0]), primaryKey);
      dynamicEntityDao.detach(adminInstance);
      adminInstance = (AdminUser) helper.createPopulatedInstance(adminInstance, entity, adminProperties, false);

      if (StringUtils.isNotEmpty(adminInstance.getPassword())) {
        adminInstance.setUnencodedPassword(adminInstance.getPassword());
        adminInstance.setPassword(null);
      }

      // The current user can update their data, but they cannot update other user's data.
      if (!adminRemoteSecurityService.getPersistentAdminUser().getId().equals(adminInstance.getId())) {
        adminRemoteSecurityService.securityCheck(persistencePackage.getCeilingEntityFullyQualifiedClassname(),
          EntityOperationType.UPDATE);
      }

      adminInstance = adminSecurityService.saveAdminUser(adminInstance);

      Entity adminEntity = helper.getRecord(adminProperties, adminInstance, null, null);

      return adminEntity;

    } catch (Exception e) {
      throw new ServiceException("Unable to update entity for " + entity.getType()[0], e);
    } // end try-catch
  } // end method update

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.handler.CustomPersistenceHandlerAdapter#willHandleSecurity(org.broadleafcommerce.openadmin.dto.PersistencePackage)
   */
  @Override public Boolean willHandleSecurity(PersistencePackage persistencePackage) {
    return true;
  }
} // end class AdminUserCustomPersistenceHandler
