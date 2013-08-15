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

package org.broadleafcommerce.openadmin.server.security.dao;

import java.util.List;

import org.broadleafcommerce.openadmin.server.security.domain.AdminPermission;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.service.type.PermissionType;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface AdminPermissionDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  permission  DOCUMENT ME!
   */
  void deleteAdminPermission(AdminPermission permission);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   permissionType                   DOCUMENT ME!
   * @param   ceilingEntityFullyQualifiedName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean doesOperationExistForCeilingEntity(PermissionType permissionType, String ceilingEntityFullyQualifiedName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   adminUser                        DOCUMENT ME!
   * @param   permissionType                   DOCUMENT ME!
   * @param   ceilingEntityFullyQualifiedName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean isUserQualifiedForOperationOnCeilingEntity(AdminUser adminUser, PermissionType permissionType,
    String ceilingEntityFullyQualifiedName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminPermission readAdminPermissionById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   name  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminPermission readAdminPermissionByName(String name);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<AdminPermission> readAllAdminPermissions();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   permission  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminPermission saveAdminPermission(AdminPermission permission);

} // end interface AdminPermissionDao
