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

package org.broadleafcommerce.openadmin.server.security.service;

import java.util.List;

import org.broadleafcommerce.common.security.util.PasswordChange;
import org.broadleafcommerce.common.service.GenericResponse;

import org.broadleafcommerce.openadmin.server.security.domain.AdminPermission;
import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;
import org.broadleafcommerce.openadmin.server.security.domain.AdminUser;
import org.broadleafcommerce.openadmin.server.security.service.type.PermissionType;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface AdminSecurityService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   passwordChange  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminUser changePassword(PasswordChange passwordChange);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   username         DOCUMENT ME!
   * @param   oldPassword      DOCUMENT ME!
   * @param   password         DOCUMENT ME!
   * @param   confirmPassword  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  GenericResponse changePassword(String username, String oldPassword, String password, String confirmPassword);

  //~ ------------------------------------------------------------------------------------------------------------------

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
   * @param  role  DOCUMENT ME!
   */
  void deleteAdminRole(AdminRole role);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  user  DOCUMENT ME!
   */
  void deleteAdminUser(AdminUser user);

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
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminRole readAdminRoleById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminUser readAdminUserById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   userName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminUser readAdminUserByUserName(String userName);

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
   * @return  DOCUMENT ME!
   */
  List<AdminRole> readAllAdminRoles();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<AdminUser> readAllAdminUsers();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Updates the password for the passed in user only if the passed in token is valid for that user.
   *
   * @param   username         Name of the user
   * @param   token            Valid reset token
   * @param   password         new password
   * @param   confirmPassword  DOCUMENT ME!
   *
   * @return  Response can contain errors including (invalidUsername, inactiveUser, invalidToken, invalidPassword,
   *          tokenExpired, passwordMismatch)
   */
  GenericResponse resetPasswordUsingToken(String username, String token, String password, String confirmPassword);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   permission  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminPermission saveAdminPermission(AdminPermission permission);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   role  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminRole saveAdminRole(AdminRole role);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   user  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminUser saveAdminUser(AdminUser user);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Looks up the corresponding AdminUser and emails the address on file with the associated username.
   *
   * @param   emailAddress  DOCUMENT ME!
   *
   * @return  Response can contain errors including (notFound)
   */
  GenericResponse sendForgotUsernameNotification(String emailAddress);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Generates an access token and then emails the user.
   *
   * @param   userName  DOCUMENT ME!
   *
   * @return  Response can contain errors including (invalidEmail, invalidUsername, inactiveUser)
   */
  GenericResponse sendResetPasswordNotification(String userName);
} // end interface AdminSecurityService
