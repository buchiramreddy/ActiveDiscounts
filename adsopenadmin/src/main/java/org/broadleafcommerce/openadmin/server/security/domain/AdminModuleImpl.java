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
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
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
@AdminPresentationClass(friendlyName = "AdminModuleImpl_baseAdminModule")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ADMIN_MODULE")
public class AdminModuleImpl implements AdminModule {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminModuleImpl_Display_Order",
    order        = 4,
    group        = "AdminModuleImpl_Module",
    prominent    = true
  )
  @Column(
    name     = "DISPLAY_ORDER",
    nullable = true
  )
  protected Integer displayOrder;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminModuleImpl_Icon",
    order        = 3,
    group        = "AdminModuleImpl_Module",
    prominent    = true
  )
  @Column(
    name     = "ICON",
    nullable = true
  )
  protected String icon;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminModuleImpl_Admin_Module_ID",
    group        = "AdminModuleImpl_Primary_Key",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "ADMIN_MODULE_ID")
  @GeneratedValue(generator = "AdminModuleId")
  @GenericGenerator(
    name       = "AdminModuleId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "AdminModuleImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.openadmin.server.security.domain.AdminModuleImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminModuleImpl_Module_Key",
    order        = 2,
    group        = "AdminModuleImpl_Module",
    prominent    = true
  )
  @Column(
    name     = "MODULE_KEY",
    nullable = false
  )
  protected String moduleKey;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AdminModuleImpl_Name",
    order        = 1,
    group        = "AdminModuleImpl_Module",
    prominent    = true
  )
  @Column(
    name     = "NAME",
    nullable = false
  )
  @Index(
    name        = "ADMINMODULE_NAME_INDEX",
    columnNames = { "NAME" }
  )
  protected String name;

  /** DOCUMENT ME! */
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @OneToMany(
    mappedBy     = "module",
    targetEntity = AdminSectionImpl.class
  )
  protected List<AdminSection> sections = new ArrayList<AdminSection>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Set all properties except the sections.
   *
   * @return  set all properties except the sections.
   */
  public AdminModuleDTO getAdminModuleDTO() {
    AdminModuleDTO dto = new AdminModuleDTO();
    dto.setDisplayOrder(displayOrder);
    dto.setIcon(icon);
    dto.setId(id);
    dto.setModuleKey(moduleKey);
    dto.setName(name);

    return dto;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#getDisplayOrder()
   */
  @Override public Integer getDisplayOrder() {
    return displayOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#getIcon()
   */
  @Override public String getIcon() {
    return icon;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#getModuleKey()
   */
  @Override public String getModuleKey() {
    return moduleKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#getSections()
   */
  @Override public List<AdminSection> getSections() {
    return sections;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#setDisplayOrder(java.lang.Integer)
   */
  @Override public void setDisplayOrder(Integer displayOrder) {
    this.displayOrder = displayOrder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#setIcon(java.lang.String)
   */
  @Override public void setIcon(String icon) {
    this.icon = icon;
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
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#setModuleKey(java.lang.String)
   */
  @Override public void setModuleKey(String moduleKey) {
    this.moduleKey = moduleKey;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.security.domain.AdminModule#setSections(java.util.List)
   */
  @Override public void setSections(List<AdminSection> sections) {
    this.sections = sections;
  }
} // end class AdminModuleImpl
