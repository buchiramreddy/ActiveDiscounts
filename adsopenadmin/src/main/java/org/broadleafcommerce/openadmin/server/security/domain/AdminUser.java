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

package org.broadleafcommerce.openadmin.server.security.domain;

import java.util.Set;

import org.broadleafcommerce.common.sandbox.domain.SandBox;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface AdminUser extends AdminSecurityContext {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#clone()
   */
  @Override AdminUser clone();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the users active status. A user whose active status is set to false will not be able to login.
   *
   * @return  the users active status.
   */
  Boolean getActiveStatusFlag();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSecurityContext#getAllPermissions()
   */
  @Override Set<AdminPermission> getAllPermissions();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSecurityContext#getAllRoles()
   */
  @Override Set<AdminRole> getAllRoles();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getEmail();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getLogin();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getName();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The current sandbox associated with this user. This is primarily intended to be used by the BLC-CMS workflow
   * processes.
   *
   * <p>If null, the user is using their own SandBox.</p>
   *
   * @return  the current sandbox associated with this user.
   */
  SandBox getOverrideSandBox();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getPassword();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the users phone number.
   *
   * @return  the users phone number.
   */
  String getPhoneNumber();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getUnencodedPassword();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the users active status. A user whose active status is set to false will not be able to login.
   *
   * @param  activeStatus  DOCUMENT ME!
   */
  void setActiveStatusFlag(Boolean activeStatus);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSecurityContext#setAllPermissions(java.util.Set)
   */
  @Override void setAllPermissions(Set<AdminPermission> allPermissions);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSecurityContext#setAllRoles(java.util.Set)
   */
  @Override void setAllRoles(Set<AdminRole> allRoles);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  email  DOCUMENT ME!
   */
  void setEmail(String email);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  login  DOCUMENT ME!
   */
  void setLogin(String login);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  name  DOCUMENT ME!
   */
  void setName(String name);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Overrides the user's sandbox. This could be used to setup shared sandboxes. Setting to null will mean that the user
   * is setup to use the sandbox associated with their user.
   *
   * @param  sandbox  DOCUMENT ME!
   */
  void setOverrideSandBox(SandBox sandbox);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  password  DOCUMENT ME!
   */
  void setPassword(String password);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Stores the user's phone number.
   *
   * @param  phone  DOCUMENT ME!
   */
  void setPhoneNumber(String phone);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  unencodedPassword  DOCUMENT ME!
   */
  void setUnencodedPassword(String unencodedPassword);
} // end interface AdminUser
