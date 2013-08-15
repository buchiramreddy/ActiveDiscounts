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

package org.broadleafcommerce.openadmin.security;

import java.util.ArrayList;
import java.util.List;

import org.broadleafcommerce.openadmin.server.security.remote.EntityOperationType;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class SecurityConfig {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String                    ceilingEntityFullyQualifiedName;
  private List<String>              permissions   = new ArrayList<String>();
  private List<EntityOperationType> requiredTypes;
  private List<String>              roles         = new ArrayList<String>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getCeilingEntityFullyQualifiedName() {
    return ceilingEntityFullyQualifiedName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<String> getPermissions() {
    return permissions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<EntityOperationType> getRequiredTypes() {
    return requiredTypes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<String> getRoles() {
    return roles;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ceilingEntityFullyQualifiedName  DOCUMENT ME!
   */
  public void setCeilingEntityFullyQualifiedName(
    String ceilingEntityFullyQualifiedName) {
    this.ceilingEntityFullyQualifiedName = ceilingEntityFullyQualifiedName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  permissions  DOCUMENT ME!
   */
  public void setPermissions(List<String> permissions) {
    this.permissions = permissions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  requiredTypes  DOCUMENT ME!
   */
  public void setRequiredTypes(List<EntityOperationType> requiredTypes) {
    this.requiredTypes = requiredTypes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  roles  DOCUMENT ME!
   */
  public void setRoles(List<String> roles) {
    this.roles = roles;
  }

} // end class SecurityConfig
