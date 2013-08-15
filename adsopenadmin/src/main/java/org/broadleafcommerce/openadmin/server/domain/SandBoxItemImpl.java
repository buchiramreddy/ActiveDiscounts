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

package org.broadleafcommerce.openadmin.server.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.presentation.override.AdminPresentationOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationOverrides;

import org.broadleafcommerce.openadmin.audit.AdminAuditable;
import org.broadleafcommerce.openadmin.audit.AdminAuditableListener;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(
  populateToOneFields = PopulateToOneFieldsEnum.TRUE,
  friendlyName        = "SandBoxItemImpl_baseSandBoxItem"
)
@AdminPresentationOverrides(
  {
    @AdminPresentationOverride(
      name = "auditable.createdBy.id",
      value =
        @AdminPresentation(
          readOnly   = true,
          visibility = VisibilityEnum.HIDDEN_ALL
        )
    ),
    @AdminPresentationOverride(
      name = "auditable.updatedBy.id",
      value =
        @AdminPresentation(
          readOnly   = true,
          visibility = VisibilityEnum.HIDDEN_ALL
        )
    ),
    @AdminPresentationOverride(
      name = "auditable.dateCreated",
      value =
        @AdminPresentation(
          visibility = VisibilityEnum.HIDDEN_ALL,
          readOnly   = true
        )
    ),
    @AdminPresentationOverride(
      name = "auditable.dateUpdated",
      value =
        @AdminPresentation(
          friendlyName = "SandBoxItemImpl_Date_Updated",
          order        = 4,
          group        = "SandBoxItemImpl_Audit",
          readOnly     = true,
          prominent    = true
        )
    ),
    @AdminPresentationOverride(
      name = "auditable.createdBy.login",
      value =
        @AdminPresentation(
          friendlyName = "SandBoxItemImpl_Admin_User_Login",
          order        = 5,
          group        = "SandBoxItemImpl_Audit",
          readOnly     = true,
          prominent    = true
        )
    )
  }
)
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blSandBoxElements"
)
@EntityListeners(value = { AdminAuditableListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SANDBOX_ITEM")
@javax.persistence.Entity public class SandBoxItemImpl implements SandBoxItem {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
  @Column(name = "ARCHIVED_FLAG")
  @Index(
    name        = "ARCHIVED_FLAG_INDEX",
    columnNames = { "ARCHIVED_FLAG" }
  )
  protected Character archivedFlag = 'N';

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Embedded protected AdminAuditable auditable = new AdminAuditable();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SandBoxItemImpl_Description",
    order        = 2,
    group        = "SandBoxItemImpl_Details",
    prominent    = true
  )
  @Column(name = "DESCRIPTION")
  protected String description;

  /** DOCUMENT ME! */
  @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
  @Column(name = "GRP_DESCRIPTION")
  protected String groupDescription;

  /** DOCUMENT ME! */
  @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
  @Column(name = "SANDBOX_ITEM_ID")
  @GeneratedValue(generator = "SandBoxItemId")
  @GenericGenerator(
    name       = "SandBoxItemId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "SandBoxItemImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.openadmin.server.domain.SandBoxItemImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SandBoxItemImpl_Original_Id",
    order        = 3,
    group        = "SandBoxItemImpl_Details",
    prominent    = true
  )
  @Column(name = "ORIGINAL_ITEM_ID")
  @Index(
    name        = "SB_ORIG_ITEM_ID_INDEX",
    columnNames = { "ORIGINAL_ITEM_ID" }
  )
  protected Long originalItemId;

  /** DOCUMENT ME! */
  @Column(name = "ORIG_SANDBOX_ID")
  @Index(
    name        = "ORIG_SANDBOX_ID_INDEX",
    columnNames = { "ORIG_SANDBOX_ID" }
  )
  protected Long originalSandBoxId;

  /** DOCUMENT ME! */
  @JoinTable(
    name               = "SANDBOX_ITEM_ACTION",
    joinColumns        = {
      @JoinColumn(
        name           = "SANDBOX_ITEM_ID",
        referencedColumnName = "SANDBOX_ITEM_ID"
      )
    },
    inverseJoinColumns = {
      @JoinColumn(
        name           = "SANDBOX_ACTION_ID",
        referencedColumnName = "SANDBOX_ACTION_ID"
      )
    }
  )
  @ManyToMany(
    targetEntity = SandBoxActionImpl.class,
    cascade      = CascadeType.ALL
  )
  protected List<SandBoxAction> sandBoxActions;

  /** DOCUMENT ME! */
  @Column(name = "SANDBOX_ID")
  @Index(
    name        = "SANDBOX_ID_INDEX",
    columnNames = { "SANDBOX_ID" }
  )
  protected Long sandBoxId;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "SandBoxItemImpl_Item_Type",
    order                = 1,
    group                = "SandBoxItemImpl_Details",
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.openadmin.server.domain.SandBoxItemType",
    prominent            = true
  )
  @Column(name = "SANDBOX_ITEM_TYPE")
  @Index(
    name        = "SANDBOX_ITEM_TYPE_INDEX",
    columnNames = { "SANDBOX_ITEM_TYPE" }
  )
  protected String sandBoxItemType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "SandBoxItemImpl_Operation_Type",
    order                = 6,
    group                = "SandBoxItemImpl_Details",
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.openadmin.server.domain.SandBoxOperationType",
    prominent            = true
  )
  @Column(name = "SANDBOX_OPERATION_TYPE")
  protected String sandboxOperationType;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(name = "TEMPORARY_ITEM_ID")
  @Index(
    name        = "TEMP_ITEM_INDEX",
    columnNames = { "TEMPORARY_ITEM_ID" }
  )
  protected Long temporaryItemId;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#addSandBoxAction(org.broadleafcommerce.openadmin.server.domain.SandBoxAction)
   */
  @Override public void addSandBoxAction(SandBoxAction action) {
    if (sandBoxActions == null) {
      sandBoxActions = new ArrayList<SandBoxAction>();
    }

    sandBoxActions.add(action);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    SandBoxItemImpl other = (SandBoxItemImpl) obj;

    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#getArchivedFlag()
   */
  @Override public Boolean getArchivedFlag() {
    if (archivedFlag == null) {
      return null;
    } else {
      return (archivedFlag == 'Y') ? Boolean.TRUE : Boolean.FALSE;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#getAuditable()
   */
  @Override public AdminAuditable getAuditable() {
    return auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#getDescription()
   */
  @Override public String getDescription() {
    return description;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#getGroupDescription()
   */
  @Override public String getGroupDescription() {
    return groupDescription;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#getOriginalItemId()
   */
  @Override public Long getOriginalItemId() {
    return originalItemId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#getOriginalSandBoxId()
   */
  @Override public Long getOriginalSandBoxId() {
    return originalSandBoxId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#getSandBoxActions()
   */
  @Override public List<SandBoxAction> getSandBoxActions() {
    return sandBoxActions;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#getSandBoxId()
   */
  @Override public Long getSandBoxId() {
    return sandBoxId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#getSandBoxItemType()
   */
  @Override public SandBoxItemType getSandBoxItemType() {
    return SandBoxItemType.getInstance(sandBoxItemType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#getSandBoxOperationType()
   */
  @Override public SandBoxOperationType getSandBoxOperationType() {
    return SandBoxOperationType.getInstance(sandboxOperationType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#getTemporaryItemId()
   */
  @Override public Long getTemporaryItemId() {
    return temporaryItemId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((id == null) ? 0 : id.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#setArchivedFlag(java.lang.Boolean)
   */
  @Override public void setArchivedFlag(Boolean archivedFlag) {
    if (archivedFlag == null) {
      this.archivedFlag = null;
    } else {
      this.archivedFlag = archivedFlag ? 'Y' : 'N';
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#setAuditable(org.broadleafcommerce.openadmin.audit.AdminAuditable)
   */
  @Override public void setAuditable(AdminAuditable auditable) {
    this.auditable = auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#setDescription(java.lang.String)
   */
  @Override public void setDescription(String description) {
    this.description = description;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#setGroupDescription(java.lang.String)
   */
  @Override public void setGroupDescription(String groupDescription) {
    this.groupDescription = groupDescription;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#setOriginalItemId(java.lang.Long)
   */
  @Override public void setOriginalItemId(Long itemIdentifer) {
    this.originalItemId = itemIdentifer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#setOriginalSandBoxId(java.lang.Long)
   */
  @Override public void setOriginalSandBoxId(Long originalSandBoxId) {
    this.originalSandBoxId = originalSandBoxId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#setSandBoxActions(java.util.List)
   */
  @Override public void setSandBoxActions(List<SandBoxAction> actionList) {
    this.sandBoxActions = actionList;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#setSandBoxId(java.lang.Long)
   */
  @Override public void setSandBoxId(Long sandBoxId) {
    this.sandBoxId = sandBoxId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#setSandBoxItemType(org.broadleafcommerce.openadmin.server.domain.SandBoxItemType)
   */
  @Override public void setSandBoxItemType(SandBoxItemType sandBoxItemType) {
    this.sandBoxItemType = sandBoxItemType.getType();
  }

  //~ ------------------------------------------------------------------------------------------------------------------


  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#setSandBoxOperationType(org.broadleafcommerce.openadmin.server.domain.SandBoxOperationType)
   */
  @Override public void setSandBoxOperationType(SandBoxOperationType sandboxOperationType) {
    this.sandboxOperationType = sandboxOperationType.getType();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.domain.SandBoxItem#setTemporaryItemId(java.lang.Long)
   */
  @Override public void setTemporaryItemId(Long temporaryItemId) {
    this.temporaryItemId = temporaryItemId;
  }

} // end class SandBoxItemImpl
