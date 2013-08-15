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

package org.broadleafcommerce.common.config.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.broadleafcommerce.common.audit.Auditable;
import org.broadleafcommerce.common.audit.AuditableListener;
import org.broadleafcommerce.common.config.service.type.ModuleConfigurationType;
import org.broadleafcommerce.common.persistence.ArchiveStatus;
import org.broadleafcommerce.common.persistence.Status;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.RequiredOverride;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.util.DateUtil;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * Modules that need to be configured via the database should extend this. Classes that extend this MUST call
 * setModuleConfigurationType(ModuleConfigurationType type) in their constructor.
 *
 * @author   Kelly Tisdell
 * @version  $Revision$, $Date$
 */

@AdminPresentationClass(
  excludeFromPolymorphism = true,
  friendlyName            = "AbstractModuleConfiguration"
)
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blStandardElements"
)
@Entity
@EntityListeners(value = { AuditableListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_MODULE_CONFIGURATION")
public abstract class AbstractModuleConfiguration implements ModuleConfiguration, Status {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AbstractModuleConfiguration_Active_End_Date",
    order        = 4000,
    prominent    = true,
    fieldType    = SupportedFieldType.DATE
  )
  @Column(
    name     = "ACTIVE_END_DATE",
    nullable = true
  )
  protected Date activeEndDate;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AbstractModuleConfiguration_Active_Start_Date",
    order        = 3000,
    prominent    = true,
    fieldType    = SupportedFieldType.DATE
  )
  @Column(
    name     = "ACTIVE_START_DATE",
    nullable = true
  )
  protected Date activeStartDate;

  /** DOCUMENT ME! */
  @Embedded protected ArchiveStatus archiveStatus = new ArchiveStatus();

  /** DOCUMENT ME! */
  @Embedded protected Auditable auditable = new Auditable();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "AbstractModuleConfiguration_Config_Type",
    order                = 1000,
    prominent            = true,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.common.config.service.type.ModuleConfigurationType",
    requiredOverride     = RequiredOverride.REQUIRED,
    readOnly             = true
  )
  @Column(
    name     = "CONFIG_TYPE",
    nullable = false
  )
  protected String configType;

  /** DOCUMENT ME! */
  @Column(name = "MODULE_CONFIG_ID")
  @GeneratedValue(generator = "ModuleConfigurationId")
  @GenericGenerator(
    name       = "ModuleConfigurationId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "ModuleConfigurationImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.common.config.domain.AbstractModuleConfiguration"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName     = "AbstractModuleConfiguration_Is_Default",
    order            = 5000,
    prominent        = true,
    requiredOverride = RequiredOverride.REQUIRED
  )
  @Column(
    name     = "IS_DEFAULT",
    nullable = false
  )
  protected Boolean isDefault = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName     = "AbstractModuleConfiguration_Module_Name",
    order            = 2000,
    prominent        = true,
    requiredOverride = RequiredOverride.REQUIRED
  )
  @Column(
    name     = "MODULE_NAME",
    nullable = false
  )
  protected String moduleName;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName     = "AbstractModuleConfiguration_Priority",
    order            = 6000,
    prominent        = true,
    requiredOverride = RequiredOverride.REQUIRED,
    tooltip          = "AbstractModuleConfiguration_Priority_Tooltip"
  )
  @Column(
    name     = "MODULE_PRIORITY",
    nullable = false
  )
  protected Integer priority = 100;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#getActiveEndDate()
   */
  @Override public Date getActiveEndDate() {
    return this.activeEndDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#getActiveStartDate()
   */
  @Override public Date getActiveStartDate() {
    return this.activeStartDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.persistence.Status#getArchived()
   */
  @Override public Character getArchived() {
    return archiveStatus.getArchived();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#getAuditable()
   */
  @Override public Auditable getAuditable() {
    return this.auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#getId()
   */
  @Override public Long getId() {
    return this.id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#getIsDefault()
   */
  @Override public Boolean getIsDefault() {
    if (this.isDefault == null) {
      this.isDefault = Boolean.FALSE;
    }

    return this.isDefault;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#getModuleConfigurationType()
   */
  @Override public ModuleConfigurationType getModuleConfigurationType() {
    return ModuleConfigurationType.getInstance(this.configType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#getModuleName()
   */
  @Override public String getModuleName() {
    return moduleName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#getPriority()
   */
  @Override public Integer getPriority() {
    return priority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.persistence.Status#isActive()
   */
  @Override public boolean isActive() {
    return DateUtil.isActive(activeStartDate, activeEndDate, true) && ('Y' != getArchived());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#setActiveEndDate(java.util.Date)
   */
  @Override public void setActiveEndDate(Date endDate) {
    this.activeEndDate = endDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#setActiveStartDate(java.util.Date)
   */
  @Override public void setActiveStartDate(Date startDate) {
    this.activeStartDate = startDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.persistence.Status#setArchived(java.lang.Character)
   */
  @Override public void setArchived(Character archived) {
    archiveStatus.setArchived(archived);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#setAuditable(org.broadleafcommerce.common.audit.Auditable)
   */
  @Override public void setAuditable(Auditable auditable) {
    this.auditable = auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#setIsDefault(java.lang.Boolean)
   */
  @Override public void setIsDefault(Boolean isDefault) {
    this.isDefault = isDefault;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#setModuleName(java.lang.String)
   */
  @Override public void setModuleName(String name) {
    this.moduleName = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.domain.ModuleConfiguration#setPriority(java.lang.Integer)
   */
  @Override public void setPriority(Integer priority) {
    this.priority = priority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Subclasses of this must set the ModuleConfigType in their constructor.
   *
   * @param  moduleConfigurationType  DOCUMENT ME!
   */
  protected void setModuleConfigurationType(ModuleConfigurationType moduleConfigurationType) {
    this.configType = moduleConfigurationType.getType();
  }

} // end class AbstractModuleConfiguration
