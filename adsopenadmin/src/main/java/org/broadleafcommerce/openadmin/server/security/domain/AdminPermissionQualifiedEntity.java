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

/**
 * Created by IntelliJ IDEA. User: jfischer Date: 9/24/11 Time: 4:38 PM To change this template use File | Settings |
 * File Templates.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface AdminPermissionQualifiedEntity {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#clone()
   */
  @Override AdminPermissionQualifiedEntity clone();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  AdminPermission getAdminPermission();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  adminPermission  DOCUMENT ME!
   */
  void setAdminPermission(AdminPermission adminPermission);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getCeilingEntityFullyQualifiedName();

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
   * @param  ceilingEntityFullyQualifiedName  DOCUMENT ME!
   */
  void setCeilingEntityFullyQualifiedName(String ceilingEntityFullyQualifiedName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);
} // end interface AdminPermissionQualifiedEntity
