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

import org.broadleafcommerce.openadmin.server.security.domain.AdminRole;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public interface AdminRoleDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

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
   * @param   id  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminRole readAdminRoleById(Long id);

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
   * @param   role  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminRole saveAdminRole(AdminRole role);
} // end interface AdminRoleDao
