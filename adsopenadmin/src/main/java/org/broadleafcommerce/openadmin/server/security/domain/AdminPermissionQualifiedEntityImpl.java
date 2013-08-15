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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.presentation.AdminPresentation;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * Created by IntelliJ IDEA. User: jfischer Date: 9/24/11 Time: 4:34 PM To change this template use File | Settings |
 * File Templates.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ADMIN_PERMISSION_ENTITY")
public class AdminPermissionQualifiedEntityImpl implements AdminPermissionQualifiedEntity {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log  LOG              = LogFactory.getLog(AdminPermissionQualifiedEntityImpl.class);
  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @JoinColumn(name = "ADMIN_PERMISSION_ID")
  @ManyToOne(targetEntity = AdminPermissionImpl.class)
  protected AdminPermission adminPermission;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminPermissionQualifiedEntityImpl_Ceiling_Entity_Name",
    order        = 1,
    group        = "AdminPermissionQualifiedEntityImpl_Permission",
    prominent    = true
  )
  @Column(
    name     = "CEILING_ENTITY",
    nullable = false
  )
  protected String ceilingEntityFullyQualifiedName;

  /** DOCUMENT ME! */
  @Column(name = "ADMIN_PERMISSION_ENTITY_ID")
  @GeneratedValue(generator = "AdminPermissionEntityId")
  @GenericGenerator(
    name       = "AdminPermissionEntityId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "AdminPermissionEntityImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.openadmin.server.security.domain.AdminPermissionQualifiedEntityImpl"
      )
    }
  )
  @Id protected Long id;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   qualifiedEntity  DOCUMENT ME!
   *
   * @throws  CloneNotSupportedException  DOCUMENT ME!
   * @throws  SecurityException           DOCUMENT ME!
   * @throws  NoSuchMethodException       DOCUMENT ME!
   */
  public void checkCloneable(AdminPermissionQualifiedEntity qualifiedEntity) throws CloneNotSupportedException,
    SecurityException, NoSuchMethodException {
    Method cloneMethod = qualifiedEntity.getClass().getMethod("clone", new Class[] {});

    if (cloneMethod.getDeclaringClass().getName().startsWith("org.broadleafcommerce")
          && !qualifiedEntity.getClass().getName().startsWith("org.broadleafcommerce")) {
      // subclass is not implementing the clone method
      throw new CloneNotSupportedException("Custom extensions and implementations should implement clone.");
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminPermissionQualifiedEntity#clone()
   */
  @Override public AdminPermissionQualifiedEntity clone() {
    AdminPermissionQualifiedEntity clone;

    try {
      clone = (AdminPermissionQualifiedEntity) Class.forName(this.getClass().getName()).newInstance();

      try {
        checkCloneable(clone);
      } catch (CloneNotSupportedException e) {
        LOG.warn("Clone implementation missing in inheritance hierarchy outside of Broadleaf: "
          + clone.getClass().getName(), e);
      }

      clone.setId(id);
      clone.setCeilingEntityFullyQualifiedName(ceilingEntityFullyQualifiedName);

      // don't clone the AdminPermission, as it would cause a recursion
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return clone;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminPermissionQualifiedEntity#getAdminPermission()
   */
  @Override public AdminPermission getAdminPermission() {
    return adminPermission;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminPermissionQualifiedEntity#getCeilingEntityFullyQualifiedName()
   */
  @Override public String getCeilingEntityFullyQualifiedName() {
    return ceilingEntityFullyQualifiedName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminPermissionQualifiedEntity#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminPermissionQualifiedEntity#setAdminPermission(org.broadleafcommerce.openadmin.server.security.domain.AdminPermission)
   */
  @Override public void setAdminPermission(AdminPermission adminPermission) {
    this.adminPermission = adminPermission;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminPermissionQualifiedEntity#setCeilingEntityFullyQualifiedName(java.lang.String)
   */
  @Override public void setCeilingEntityFullyQualifiedName(String ceilingEntityFullyQualifiedName) {
    this.ceilingEntityFullyQualifiedName = ceilingEntityFullyQualifiedName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminPermissionQualifiedEntity#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }
} // end class AdminPermissionQualifiedEntityImpl
