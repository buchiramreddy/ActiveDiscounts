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

import javax.persistence.CascadeType;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

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
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.sandbox.domain.SandBox;
import org.broadleafcommerce.common.sandbox.domain.SandBoxImpl;

import org.broadleafcommerce.openadmin.server.service.type.ContextType;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "AdminUserImpl_baseAdminUser")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ADMIN_USER")
public class AdminUserImpl implements AdminUser, AdminMainEntity {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log  LOG              = LogFactory.getLog(AdminUserImpl.class);
  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminUserImpl_Admin_Email_Address",
    order        = 4,
    group        = "AdminUserImpl_User"
  )
  @Column(
    name     = "EMAIL",
    nullable = false
  )
  @Index(
    name        = "ADMINPERM_EMAIL_INDEX",
    columnNames = { "EMAIL" }
  )
  protected String email;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminUserImpl_Admin_Login",
    order        = 2,
    group        = "AdminUserImpl_User",
    prominent    = true
  )
  @Column(
    name     = "LOGIN",
    nullable = false
  )
  protected String login;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName             = "AdminUserImpl_Admin_Name",
    order                    = 1,
    group                    = "AdminUserImpl_User",
    prominent                = true,
    validationConfigurations = {
      @ValidationConfiguration(
        validationImplementation =
          "org.broadleafcommerce.openadmin.server.service.persistence.validation.RegexPropertyValidator",
        configurationItems   = {
          @ConfigurationItem(
            itemName         = "regularExpression",
            itemValue        = "\\w+"
          ),
          @ConfigurationItem(
            itemName         = ConfigurationItem.ERROR_MESSAGE,
            itemValue        = "Only word characters are allowed"
          )
        }
      )
    }
  )
  @Column(
    name     = "NAME",
    nullable = false
  )
  @Index(
    name        = "ADMINUSER_NAME_INDEX",
    columnNames = { "NAME" }
  )
  protected String name;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminUserImpl_Active_Status",
    order        = 6,
    group        = "AdminUserImpl_User"
  )
  @Column(name = "ACTIVE_STATUS_FLAG")
  protected Boolean activeStatusFlag = Boolean.TRUE;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    addType        = AddMethodType.LOOKUP,
    friendlyName   = "permissionListTitle",
    manyToField    = "allUsers",
    operationTypes = @AdminPresentationOperationTypes(removeType = OperationType.NONDESTRUCTIVEREMOVE)
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @JoinTable(
    name               = "BLC_ADMIN_USER_PERMISSION_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "ADMIN_USER_ID",
        referencedColumnName = "ADMIN_USER_ID"
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

  /** All roles that this user has. */
  @AdminPresentationCollection(
    addType        = AddMethodType.LOOKUP,
    friendlyName   = "roleListTitle",
    manyToField    = "allUsers",
    operationTypes = @AdminPresentationOperationTypes(removeType = OperationType.NONDESTRUCTIVEREMOVE)
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @JoinTable(
    name               = "BLC_ADMIN_USER_ROLE_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "ADMIN_USER_ID",
        referencedColumnName = "ADMIN_USER_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "ADMIN_ROLE_ID",
        referencedColumnName = "ADMIN_ROLE_ID"
      )
  )
  @ManyToMany(
    fetch        = FetchType.LAZY,
    targetEntity = AdminRoleImpl.class
  )
  protected Set<AdminRole> allRoles = new HashSet<AdminRole>();

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinTable(
    name               = "BLC_ADMIN_USER_SANDBOX",
    joinColumns        =
      @JoinColumn(
        name           = "ADMIN_USER_ID",
        referencedColumnName = "ADMIN_USER_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "SANDBOX_ID",
        referencedColumnName = "SANDBOX_ID"
      )
  )
  @ManyToOne(
    targetEntity = SandBoxImpl.class,
    cascade      = { CascadeType.MERGE, CascadeType.PERSIST }
  )
  protected SandBox overrideSandBox;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName             = "AdminUserImpl_Admin_Password",
    order                    = 3,
    group                    = "AdminUserImpl_User",
    fieldType                = SupportedFieldType.PASSWORD,
    validationConfigurations = {
      @ValidationConfiguration(
        validationImplementation =
          "org.broadleafcommerce.openadmin.server.service.persistence.validation.MatchesFieldValidator",
        configurationItems   = {
          @ConfigurationItem(
            itemName         = ConfigurationItem.ERROR_MESSAGE,
            itemValue        = "passwordNotMatchError"
          ),
          @ConfigurationItem(
            itemName         = "otherField",
            itemValue        = "passwordConfirm"
          )
        }
      )
    }
  )
  @Column(
    name     = "PASSWORD",
    nullable = false
  )
  protected String password;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminUserImpl_Phone_Number",
    order        = 5,
    group        = "AdminUserImpl_User"
  )
  @Column(name = "PHONE_NUMBER")
  protected String phoneNumber;

  /** DOCUMENT ME! */
  @Transient protected String unencodedPassword;

  @AdminPresentation(
    friendlyName = "AdminUserImpl_Admin_User_ID",
    group        = "AdminUserImpl_Primary_Key",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "ADMIN_USER_ID")
  @GeneratedValue(generator = "AdminUserId")
  @GenericGenerator(
    name       = "AdminUserId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "AdminUserImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.openadmin.server.security.domain.AdminUserImpl"
      )
    }
  )
  @Id private Long id;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   adminUser  DOCUMENT ME!
   *
   * @throws  CloneNotSupportedException  DOCUMENT ME!
   * @throws  SecurityException           DOCUMENT ME!
   * @throws  NoSuchMethodException       DOCUMENT ME!
   */
  public void checkCloneable(AdminUser adminUser) throws CloneNotSupportedException, SecurityException,
    NoSuchMethodException {
    Method cloneMethod = adminUser.getClass().getMethod("clone", new Class[] {});

    if (cloneMethod.getDeclaringClass().getName().startsWith("org.broadleafcommerce")
          && !adminUser.getClass().getName().startsWith("org.broadleafcommerce")) {
      // subclass is not implementing the clone method
      throw new CloneNotSupportedException("Custom extensions and implementations should implement clone.");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#clone()
   */
  @Override public AdminUser clone() {
    AdminUser clone;

    try {
      clone = (AdminUser) Class.forName(this.getClass().getName()).newInstance();

      try {
        checkCloneable(clone);
      } catch (CloneNotSupportedException e) {
        LOG.warn("Clone implementation missing in inheritance hierarchy outside of Broadleaf: "
          + clone.getClass().getName(), e);
      }

      clone.setId(id);
      clone.setName(name);
      clone.setLogin(login);
      clone.setPassword(password);
      clone.setEmail(email);
      clone.setPhoneNumber(phoneNumber);
      clone.setActiveStatusFlag(activeStatusFlag);

      if (allRoles != null) {
        for (AdminRole role : allRoles) {
          AdminRole roleClone = role.clone();
          clone.getAllRoles().add(roleClone);
        }
      }

      if (allPermissions != null) {
        for (AdminPermission permission : allPermissions) {
          AdminPermission permissionClone = permission.clone();
          clone.getAllPermissions().add(permissionClone);
        }
      }

      if (overrideSandBox != null) {
        clone.setOverrideSandBox(overrideSandBox.clone());
      }
    } catch (Exception e) {
      throw new RuntimeException(e);
    } // end try-catch

    return clone;
  } // end method clone

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#getActiveStatusFlag()
   */
  @Override public Boolean getActiveStatusFlag() {
    return activeStatusFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#getAllPermissions()
   */
  @Override public Set<AdminPermission> getAllPermissions() {
    return allPermissions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#getAllRoles()
   */
  @Override public Set<AdminRole> getAllRoles() {
    return allRoles;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSecurityContext#getContextKey()
   */
  @Override public String getContextKey() {
    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSecurityContext#getContextType()
   */
  @Override public ContextType getContextType() {
    return ContextType.GLOBAL;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#getEmail()
   */
  @Override public String getEmail() {
    return email;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#getLogin()
   */
  @Override public String getLogin() {
    return login;
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
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#getOverrideSandBox()
   */
  @Override public SandBox getOverrideSandBox() {
    return overrideSandBox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#getPassword()
   */
  @Override public String getPassword() {
    return password;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#getPhoneNumber()
   */
  @Override public String getPhoneNumber() {
    return phoneNumber;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#getUnencodedPassword()
   */
  @Override public String getUnencodedPassword() {
    return unencodedPassword;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#setActiveStatusFlag(java.lang.Boolean)
   */
  @Override public void setActiveStatusFlag(Boolean activeStatusFlag) {
    this.activeStatusFlag = activeStatusFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#setAllPermissions(java.util.Set)
   */
  @Override public void setAllPermissions(Set<AdminPermission> allPermissions) {
    this.allPermissions = allPermissions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#setAllRoles(java.util.Set)
   */
  @Override public void setAllRoles(Set<AdminRole> allRoles) {
    this.allRoles = allRoles;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSecurityContext#setContextKey(java.lang.String)
   */
  @Override public void setContextKey(String contextKey) {
    // do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSecurityContext#setContextType(org.broadleafcommerce.openadmin.server.service.type.ContextType)
   */
  @Override public void setContextType(ContextType contextType) {
    // do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#setEmail(java.lang.String)
   */
  @Override public void setEmail(String email) {
    this.email = email;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#setLogin(java.lang.String)
   */
  @Override public void setLogin(String login) {
    this.login = login;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#setOverrideSandBox(org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public void setOverrideSandBox(SandBox overrideSandBox) {
    this.overrideSandBox = overrideSandBox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#setPassword(java.lang.String)
   */
  @Override public void setPassword(String password) {
    this.password = password;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#setPhoneNumber(java.lang.String)
   */
  @Override public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminUser#setUnencodedPassword(java.lang.String)
   */
  @Override public void setUnencodedPassword(String unencodedPassword) {
    this.unencodedPassword = unencodedPassword;
  }

} // end class AdminUserImpl
