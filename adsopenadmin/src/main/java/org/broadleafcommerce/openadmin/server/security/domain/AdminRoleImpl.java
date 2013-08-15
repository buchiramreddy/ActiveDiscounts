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

import java.lang.reflect.Method;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.admin.domain.AdminMainEntity;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.AdminPresentationOperationTypes;
import org.broadleafcommerce.common.presentation.ConfigurationItem;
import org.broadleafcommerce.common.presentation.ValidationConfiguration;
import org.broadleafcommerce.common.presentation.client.AddMethodType;
import org.broadleafcommerce.common.presentation.client.OperationType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "AdminRoleImpl_baseAdminRole")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ADMIN_ROLE")
public class AdminRoleImpl implements AdminRole, AdminMainEntity {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log  LOG              = LogFactory.getLog(AdminRoleImpl.class);
  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminRoleImpl_Description",
    order        = 2,
    group        = "AdminRoleImpl_Role",
    prominent    = true
  )
  @Column(
    name     = "DESCRIPTION",
    nullable = false
  )
  protected String description;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminRoleImpl_Admin_Role_ID",
    group        = "AdminRoleImpl_Primary_Key",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "ADMIN_ROLE_ID")
  @GeneratedValue(generator = "AdminRoleId")
  @GenericGenerator(
    name       = "AdminRoleId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "AdminRoleImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.openadmin.server.security.domain.AdminRoleImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName             = "AdminRoleImpl_Name",
    order                    = 1,
    group                    = "AdminRoleImpl_Role",
    validationConfigurations = {
      @ValidationConfiguration(
        validationImplementation = "blRegexPropertyValidator",
        configurationItems   = {
          @ConfigurationItem(
            itemName         = "regularExpression",
            itemValue        = "ROLE_.+"
          ),
          @ConfigurationItem(
            itemName         = ConfigurationItem.ERROR_MESSAGE,
            itemValue        = "roleNameError"
          )
        }
      )
    }
  )
  @Column(
    name     = "NAME",
    nullable = false
  )
  protected String name;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    addType        = AddMethodType.LOOKUP,
    friendlyName   = "permissionListTitle",
    manyToField    = "allRoles",
    operationTypes = @AdminPresentationOperationTypes(removeType = OperationType.NONDESTRUCTIVEREMOVE)
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @JoinTable(
    name               = "BLC_ADMIN_ROLE_PERMISSION_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "ADMIN_ROLE_ID",
        referencedColumnName = "ADMIN_ROLE_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "ADMIN_PERMISSION_ID",
        referencedColumnName = "ADMIN_PERMISSION_ID"
      )
  )
  @ManyToMany(
    fetch        = FetchType.LAZY,
    targetEntity = AdminPermissionImpl.class
  )
  protected Set<AdminPermission> allPermissions = new HashSet<AdminPermission>();

  /** All users that have this role. */
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @JoinTable(
    name               = "BLC_ADMIN_USER_ROLE_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "ADMIN_ROLE_ID",
        referencedColumnName = "ADMIN_ROLE_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "ADMIN_USER_ID",
        referencedColumnName = "ADMIN_USER_ID"
      )
  )
  @ManyToMany(
    fetch        = FetchType.LAZY,
    targetEntity = AdminUserImpl.class
  )
  protected Set<AdminUser> allUsers = new HashSet<AdminUser>();

  //~ Methods ----------------------------------------------------------------------------------------------------------


  /**
   * DOCUMENT ME!
   *
   * @param   adminRole  DOCUMENT ME!
   *
   * @throws  CloneNotSupportedException  DOCUMENT ME!
   * @throws  SecurityException           DOCUMENT ME!
   * @throws  NoSuchMethodException       DOCUMENT ME!
   */
  public void checkCloneable(AdminRole adminRole) throws CloneNotSupportedException, SecurityException,
    NoSuchMethodException {
    Method cloneMethod = adminRole.getClass().getMethod("clone", new Class[] {});

    if (cloneMethod.getDeclaringClass().getName().startsWith("org.broadleafcommerce")
          && !adminRole.getClass().getName().startsWith("org.broadleafcommerce")) {
      // subclass is not implementing the clone method
      throw new CloneNotSupportedException("Custom extensions and implementations should implement clone.");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminRole#clone()
   */
  @Override public AdminRole clone() {
    AdminRole clone;

    try {
      clone = (AdminRole) Class.forName(this.getClass().getName()).newInstance();

      try {
        checkCloneable(clone);
      } catch (CloneNotSupportedException e) {
        LOG.warn("Clone implementation missing in inheritance hierarchy outside of Broadleaf: "
          + clone.getClass().getName(), e);
      }

      clone.setId(id);
      clone.setName(name);
      clone.setDescription(description);

      // don't clone the allUsers collection, as it would cause a recursion

      if (allPermissions != null) {
        for (AdminPermission permission : allPermissions) {
          AdminPermission permissionClone = permission.clone();
          clone.getAllPermissions().add(permissionClone);
        }
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } // end try-catch

    return clone;
  } // end method clone

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminRole#getAllPermissions()
   */
  @Override public Set<AdminPermission> getAllPermissions() {
    return allPermissions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Set<AdminUser> getAllUsers() {
    return allUsers;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminRole#getDescription()
   */
  @Override public String getDescription() {
    return description;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminRole#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.admin.domain.AdminMainEntity#getMainEntityName()
   */
  @Override public String getMainEntityName() {
    return getName();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminRole#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  allPermissions  DOCUMENT ME!
   */
  public void setAllPermissions(Set<AdminPermission> allPermissions) {
    this.allPermissions = allPermissions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminRole#setDescription(java.lang.String)
   */
  @Override public void setDescription(String description) {
    this.description = description;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminRole#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminRole#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

} // end class AdminRoleImpl
