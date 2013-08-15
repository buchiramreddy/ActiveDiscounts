/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.openadmin.server.security.domain;

import java.util.ArrayList;
import java.util.List;

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

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   elbertbautista
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "AdminSectionImpl_baseAdminSection")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ADMIN_SECTION")
public class AdminSectionImpl implements AdminSection {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminSectionImpl_Ceiling_Entity",
    order        = 6,
    group        = "AdminSectionImpl_Section"
  )
  @Column(
    name     = "CEILING_ENTITY",
    nullable = true
  )
  protected String ceilingEntity;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminSectionImpl_Display_Controller",
    order        = 4,
    group        = "AdminSectionImpl_Section"
  )
  @Column(
    name     = "DISPLAY_CONTROLLER",
    nullable = true
  )
  protected String displayController;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminSectionImpl_Display_Order",
    order        = 7,
    group        = "AdminSectionImpl_Section",
    prominent    = true
  )
  @Column(
    name     = "DISPLAY_ORDER",
    nullable = true
  )
  protected Integer displayOrder;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminSectionImpl_Admin_Section_ID",
    group        = "AdminSectionImpl_Primary_Key",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "ADMIN_SECTION_ID")
  @GeneratedValue(generator = "AdminSectionId")
  @GenericGenerator(
    name       = "AdminSectionId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "AdminSectionImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.openadmin.server.security.domain.AdminSectionImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Index(
    name        = "ADMINSECTION_MODULE_INDEX",
    columnNames = { "ADMIN_MODULE_ID" }
  )
  @JoinColumn(name = "ADMIN_MODULE_ID")
  @ManyToOne(
    optional     = false,
    targetEntity = AdminModuleImpl.class
  )
  protected AdminModule module;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminSectionImpl_Name",
    order        = 1,
    group        = "AdminSectionImpl_Section",
    prominent    = true
  )
  @Column(
    name     = "NAME",
    nullable = false
  )
  @Index(
    name        = "ADMINSECTION_NAME_INDEX",
    columnNames = { "NAME" }
  )
  protected String name;

  /** DOCUMENT ME! */
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @JoinTable(
    name               = "BLC_ADMIN_SEC_PERM_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "ADMIN_SECTION_ID",
        referencedColumnName = "ADMIN_SECTION_ID"
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
  protected List<AdminPermission> permissions = new ArrayList<AdminPermission>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminSectionImpl_Section_Key",
    order        = 2,
    group        = "AdminSectionImpl_Section",
    prominent    = true
  )
  @Column(
    name     = "SECTION_KEY",
    nullable = false,
    unique   = true
  )
  protected String sectionKey;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminSectionImpl_Url",
    order        = 3,
    group        = "AdminSectionImpl_Section",
    prominent    = true
  )
  @Column(
    name     = "URL",
    nullable = true
  )
  protected String url;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminSectionImpl_Use_Default_Handler",
    order        = 5,
    group        = "AdminSectionImpl_Section"
  )
  @Column(
    name     = "USE_DEFAULT_HANDLER",
    nullable = false
  )
  protected Boolean useDefaultHandler = Boolean.TRUE;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#getCeilingEntity()
   */
  @Override public String getCeilingEntity() {
    return ceilingEntity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#getDisplayController()
   */
  @Override public String getDisplayController() {
    return displayController;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#getDisplayOrder()
   */
  @Override public Integer getDisplayOrder() {
    return displayOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#getModule()
   */
  @Override public AdminModule getModule() {
    return module;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#getPermissions()
   */
  @Override public List<AdminPermission> getPermissions() {
    return permissions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#getSectionKey()
   */
  @Override public String getSectionKey() {
    return sectionKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#getUrl()
   */
  @Override public String getUrl() {
    return url;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#getUseDefaultHandler()
   */
  @Override public Boolean getUseDefaultHandler() {
    return useDefaultHandler;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#setCeilingEntity(java.lang.String)
   */
  @Override public void setCeilingEntity(String ceilingEntity) {
    this.ceilingEntity = ceilingEntity;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#setDisplayController(java.lang.String)
   */
  @Override public void setDisplayController(String displayController) {
    this.displayController = displayController;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#setDisplayOrder(java.lang.Integer)
   */
  @Override public void setDisplayOrder(Integer displayOrder) {
    this.displayOrder = displayOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#setModule(org.broadleafcommerce.openadmin.server.security.domain.AdminModule)
   */
  @Override public void setModule(AdminModule module) {
    this.module = module;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#setPermissions(java.util.List)
   */
  @Override public void setPermissions(List<AdminPermission> permissions) {
    this.permissions = permissions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#setSectionKey(java.lang.String)
   */
  @Override public void setSectionKey(String sectionKey) {
    this.sectionKey = sectionKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#setUrl(java.lang.String)
   */
  @Override public void setUrl(String url) {
    this.url = url;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminSection#setUseDefaultHandler(java.lang.Boolean)
   */
  @Override public void setUseDefaultHandler(Boolean useDefaultHandler) {
    this.useDefaultHandler = useDefaultHandler;
  }
} // end class AdminSectionImpl
