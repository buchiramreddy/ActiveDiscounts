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

package org.broadleafcommerce.cms.structure.domain;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.locale.domain.LocaleImpl;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationMapField;
import org.broadleafcommerce.common.presentation.AdminPresentationMapFields;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.RequiredOverride;
import org.broadleafcommerce.common.presentation.client.LookupType;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.presentation.override.AdminPresentationOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationOverrides;
import org.broadleafcommerce.common.sandbox.domain.SandBox;
import org.broadleafcommerce.common.sandbox.domain.SandBoxImpl;

import org.broadleafcommerce.openadmin.audit.AdminAuditable;
import org.broadleafcommerce.openadmin.audit.AdminAuditableListener;
import org.broadleafcommerce.openadmin.server.service.type.RuleIdentifier;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(
  populateToOneFields = PopulateToOneFieldsEnum.TRUE,
  friendlyName        = "StructuredContentImpl_baseStructuredContent"
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
      name = "auditable.createdBy.name",
      value =
        @AdminPresentation(
          readOnly   = true,
          visibility = VisibilityEnum.HIDDEN_ALL
        )
    ),
    @AdminPresentationOverride(
      name = "auditable.updatedBy.name",
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
          readOnly   = true,
          visibility = VisibilityEnum.HIDDEN_ALL
        )
    ),
    @AdminPresentationOverride(
      name = "auditable.dateUpdated",
      value =
        @AdminPresentation(
          readOnly   = true,
          visibility = VisibilityEnum.HIDDEN_ALL
        )
    )
  }
)
@Entity
@EntityListeners(value = { AdminAuditableListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SC")
public class StructuredContentImpl implements StructuredContent {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StructuredContentImpl_Archived",
    order        = 3,
    group        = Presentation.Group.Name.Internal,
    groupOrder   = Presentation.Group.Order.Internal,
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "ARCHIVED_FLAG")
  @Index(
    name        = "SC_ARCHVD_FLG_INDX",
    columnNames = { "ARCHIVED_FLAG" }
  )
  protected Boolean archivedFlag = false;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Embedded protected AdminAuditable auditable = new AdminAuditable();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StructuredContentImpl_Content_Name",
    order        = 1,
    group        = Presentation.Group.Name.Description,
    groupOrder   = Presentation.Group.Order.Description,
    prominent    = true,
    gridOrder    = 1
  )
  @Column(
    name     = "CONTENT_NAME",
    nullable = false
  )
  @Index(
    name        = "CONTENT_NAME_INDEX",
    columnNames = { "CONTENT_NAME", "ARCHIVED_FLAG", "SC_TYPE_ID" }
  )
  protected String contentName;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StructuredContentImpl_Deleted",
    order        = 2,
    group        = Presentation.Group.Name.Internal,
    groupOrder   = Presentation.Group.Order.Internal,
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "DELETED_FLAG")
  @Index(
    name        = "SC_DLTD_FLG_INDX",
    columnNames = { "DELETED_FLAG" }
  )
  protected Boolean deletedFlag = false;

  /** DOCUMENT ME! */
  @Column(name = "SC_ID")
  @GeneratedValue(generator = "StructuredContentId")
  @GenericGenerator(
    name       = "StructuredContentId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "StructuredContentImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.cms.structure.domain.StructuredContentImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StructuredContentImpl_Locale",
    order        = 2,
    group        = Presentation.Group.Name.Description,
    groupOrder   = Presentation.Group.Order.Description,
    prominent    = true,
    gridOrder    = 2
  )
  @AdminPresentationToOneLookup(
    lookupDisplayProperty = "friendlyName",
    lookupType            = LookupType.DROPDOWN
  )
  @JoinColumn(name = "LOCALE_CODE")
  @ManyToOne(
    targetEntity = LocaleImpl.class,
    optional     = false
  )
  protected Locale locale;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StructuredContentImpl_Is_Locked",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "LOCKED_FLAG")
  @Index(
    name        = "SC_LCKD_FLG_INDX",
    columnNames = { "LOCKED_FLAG" }
  )
  protected Boolean lockedFlag = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StructuredContentImpl_Offline",
    order        = 4,
    group        = Presentation.Group.Name.Description,
    groupOrder   = Presentation.Group.Order.Description
  )
  @Column(name = "OFFLINE_FLAG")
  @Index(
    name        = "SC_OFFLN_FLG_INDX",
    columnNames = { "OFFLINE_FLAG" }
  )
  protected Boolean offlineFlag = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StructuredContentImpl_Original_Item_Id",
    order        = 1,
    group        = Presentation.Group.Name.Internal,
    groupOrder   = Presentation.Group.Order.Internal,
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "ORIG_ITEM_ID")
  @Index(
    name        = "SC_ORIG_ITEM_ID_INDEX",
    columnNames = { "ORIG_ITEM_ID" }
  )
  protected Long originalItemId;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "ORIG_SANDBOX_ID")
  @ManyToOne(targetEntity = SandBoxImpl.class)
  protected SandBox originalSandBox;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StructuredContentImpl_Priority",
    order        = 3,
    group        = Presentation.Group.Name.Description,
    groupOrder   = Presentation.Group.Order.Description
  )
  @Column(
    name     = "PRIORITY",
    nullable = false
  )
  @Index(
    name        = "CONTENT_PRIORITY_INDEX",
    columnNames = { "PRIORITY" }
  )
  protected Integer priority;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName   = "Generic_Item_Rule",
    order          = 5,
    tab            = Presentation.Tab.Name.Rules,
    tabOrder       = Presentation.Tab.Order.Rules,
    group          = Presentation.Group.Name.Rules,
    groupOrder     = Presentation.Group.Order.Rules,
    fieldType      = SupportedFieldType.RULE_WITH_QUANTITY,
    ruleIdentifier = RuleIdentifier.ORDERITEM
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @JoinTable(
    name               = "BLC_QUAL_CRIT_SC_XREF",
    joinColumns        = @JoinColumn(name = "SC_ID"),
    inverseJoinColumns = @JoinColumn(name = "SC_ITEM_CRITERIA_ID")
  )
  @OneToMany(
    fetch        = FetchType.LAZY,
    targetEntity = StructuredContentItemCriteriaImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected Set<StructuredContentItemCriteria> qualifyingItemCriteria = new HashSet<StructuredContentItemCriteria>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StructuredContentImpl_Content_SandBox",
    order        = 1,
    group        = Presentation.Group.Name.Internal,
    groupOrder   = Presentation.Group.Order.Internal,
    excluded     = true
  )
  @JoinColumn(name = "SANDBOX_ID")
  @ManyToOne(targetEntity = SandBoxImpl.class)
  protected SandBox sandbox;

  /** DOCUMENT ME! */
  @BatchSize(size = 20)
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @JoinTable(
    name               = "BLC_SC_FLD_MAP",
    joinColumns        =
      @JoinColumn(
        name           = "SC_ID",
        referencedColumnName = "SC_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "SC_FLD_ID",
        referencedColumnName = "SC_FLD_ID"
      )
  )
  @ManyToMany(
    targetEntity = StructuredContentFieldImpl.class,
    cascade      = CascadeType.ALL
  )
  @MapKeyColumn(name = "MAP_KEY")
  protected Map<String, StructuredContentField> structuredContentFields = new HashMap<String, StructuredContentField>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName     = "StructuredContentImpl_Content_Type",
    order            = 2,
    group            = Presentation.Group.Name.Description,
    groupOrder       = Presentation.Group.Order.Description,
    requiredOverride = RequiredOverride.REQUIRED
  )
  @AdminPresentationToOneLookup(lookupDisplayProperty = "name")
  @JoinColumn(name = "SC_TYPE_ID")
  @ManyToOne(targetEntity = StructuredContentTypeImpl.class)
  protected StructuredContentType structuredContentType;

  /** DOCUMENT ME! */
  @AdminPresentationMapFields(
    mapDisplayFields = {
      @AdminPresentationMapField(
        fieldName = RuleIdentifier.CUSTOMER_FIELD_KEY,
        fieldPresentation =
          @AdminPresentation(
            fieldType      = SupportedFieldType.RULE_SIMPLE,
            order          = 1,
            tab            = Presentation.Tab.Name.Rules,
            tabOrder       = Presentation.Tab.Order.Rules,
            group          = Presentation.Group.Name.Rules,
            groupOrder     = Presentation.Group.Order.Rules,
            ruleIdentifier = RuleIdentifier.CUSTOMER,
            friendlyName   = "Generic_Customer_Rule"
          )
      ),
      @AdminPresentationMapField(
        fieldName = RuleIdentifier.TIME_FIELD_KEY,
        fieldPresentation =
          @AdminPresentation(
            fieldType      = SupportedFieldType.RULE_SIMPLE,
            order          = 2,
            tab            = Presentation.Tab.Name.Rules,
            tabOrder       = Presentation.Tab.Order.Rules,
            group          = Presentation.Group.Name.Rules,
            groupOrder     = Presentation.Group.Order.Rules,
            ruleIdentifier = RuleIdentifier.TIME,
            friendlyName   = "Generic_Time_Rule"
          )
      ),
      @AdminPresentationMapField(
        fieldName = RuleIdentifier.REQUEST_FIELD_KEY,
        fieldPresentation =
          @AdminPresentation(
            fieldType      = SupportedFieldType.RULE_SIMPLE,
            order          = 3,
            tab            = Presentation.Tab.Name.Rules,
            tabOrder       = Presentation.Tab.Order.Rules,
            group          = Presentation.Group.Name.Rules,
            groupOrder     = Presentation.Group.Order.Rules,
            ruleIdentifier = RuleIdentifier.REQUEST,
            friendlyName   = "Generic_Request_Rule"
          )
      ),
      @AdminPresentationMapField(
        fieldName = RuleIdentifier.PRODUCT_FIELD_KEY,
        fieldPresentation =
          @AdminPresentation(
            fieldType      = SupportedFieldType.RULE_SIMPLE,
            order          = 4,
            tab            = Presentation.Tab.Name.Rules,
            tabOrder       = Presentation.Tab.Order.Rules,
            group          = Presentation.Group.Name.Rules,
            groupOrder     = Presentation.Group.Order.Rules,
            ruleIdentifier = RuleIdentifier.PRODUCT,
            friendlyName   = "Generic_Product_Rule"
          )
      ),
      @AdminPresentationMapField(
        fieldName = RuleIdentifier.ORDER_FIELD_KEY,
        fieldPresentation =
          @AdminPresentation(
            fieldType      = SupportedFieldType.RULE_SIMPLE,
            order          = 5,
            tab            = Presentation.Tab.Name.Rules,
            tabOrder       = Presentation.Tab.Order.Rules,
            group          = Presentation.Group.Name.Rules,
            groupOrder     = Presentation.Group.Order.Rules,
            ruleIdentifier = RuleIdentifier.ORDER,
            friendlyName   = "Generic_Order_Rule"
          )
      )
    }
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @JoinTable(
    name               = "BLC_SC_RULE_MAP",
    inverseJoinColumns =
      @JoinColumn(
        name           = "SC_RULE_ID",
        referencedColumnName = "SC_RULE_ID"
      )
  )
  @ManyToMany(
    targetEntity = StructuredContentRuleImpl.class,
    cascade      = { CascadeType.ALL }
  )
  @MapKeyColumn(
    name     = "MAP_KEY",
    nullable = false
  )
  Map<String, StructuredContentRule> structuredContentMatchRules = new HashMap<String, StructuredContentRule>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#cloneEntity()
   */
  @Override public StructuredContent cloneEntity() {
    StructuredContentImpl newContent = new StructuredContentImpl();
    newContent.archivedFlag          = archivedFlag;
    newContent.contentName           = contentName;
    newContent.deletedFlag           = deletedFlag;
    newContent.locale                = locale;
    newContent.offlineFlag           = offlineFlag;
    newContent.originalItemId        = originalItemId;
    newContent.priority              = priority;
    newContent.structuredContentType = structuredContentType;

    Map<String, StructuredContentRule> ruleMap = newContent.getStructuredContentMatchRules();

    for (String key : structuredContentMatchRules.keySet()) {
      StructuredContentRule newField = structuredContentMatchRules.get(key).cloneEntity();
      ruleMap.put(key, newField);
    }

    Set<StructuredContentItemCriteria> criteriaList = newContent.getQualifyingItemCriteria();

    for (StructuredContentItemCriteria structuredContentItemCriteria : qualifyingItemCriteria) {
      StructuredContentItemCriteria newField = structuredContentItemCriteria.cloneEntity();
      criteriaList.add(newField);
    }

    Map<String, StructuredContentField> fieldMap = newContent.getStructuredContentFields();

    for (StructuredContentField field : structuredContentFields.values()) {
      StructuredContentField newField = field.cloneEntity();
      fieldMap.put(newField.getFieldKey(), newField);
    }

    return newContent;
  } // end method cloneEntity

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getArchivedFlag()
   */
  @Override public Boolean getArchivedFlag() {
    if (archivedFlag == null) {
      return Boolean.FALSE;
    } else {
      return archivedFlag;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getAuditable()
   */
  @Override public AdminAuditable getAuditable() {
    return auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getContentName()
   */
  @Override public String getContentName() {
    return contentName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getDeletedFlag()
   */
  @Override public Boolean getDeletedFlag() {
    if (deletedFlag == null) {
      return Boolean.FALSE;
    } else {
      return deletedFlag;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getLocale()
   */
  @Override public Locale getLocale() {
    return locale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getLockedFlag()
   */
  @Override public Boolean getLockedFlag() {
    if (lockedFlag == null) {
      return Boolean.FALSE;
    } else {
      return lockedFlag;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getMainEntityName() {
    return getContentName();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getOfflineFlag()
   */
  @Override public Boolean getOfflineFlag() {
    if (offlineFlag == null) {
      return Boolean.FALSE;
    } else {
      return offlineFlag;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getOriginalItemId()
   */
  @Override public Long getOriginalItemId() {
    return originalItemId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getOriginalSandBox()
   */
  @Override public SandBox getOriginalSandBox() {
    return originalSandBox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getPriority()
   */
  @Override public Integer getPriority() {
    return priority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getQualifyingItemCriteria()
   */
  @Override public Set<StructuredContentItemCriteria> getQualifyingItemCriteria() {
    return qualifyingItemCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getSandbox()
   */
  @Override public SandBox getSandbox() {
    return sandbox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getStructuredContentFields()
   */
  @Override public Map<String, StructuredContentField> getStructuredContentFields() {
    return structuredContentFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getStructuredContentMatchRules()
   */
  @Override public Map<String, StructuredContentRule> getStructuredContentMatchRules() {
    return structuredContentMatchRules;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#getStructuredContentType()
   */
  @Override public StructuredContentType getStructuredContentType() {
    return structuredContentType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setArchivedFlag(java.lang.Boolean)
   */
  @Override public void setArchivedFlag(Boolean archivedFlag) {
    this.archivedFlag = archivedFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setAuditable(org.broadleafcommerce.openadmin.audit.AdminAuditable)
   */
  @Override public void setAuditable(AdminAuditable auditable) {
    this.auditable = auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setContentName(java.lang.String)
   */
  @Override public void setContentName(String contentName) {
    this.contentName = contentName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setDeletedFlag(java.lang.Boolean)
   */
  @Override public void setDeletedFlag(Boolean deletedFlag) {
    this.deletedFlag = deletedFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setLocale(org.broadleafcommerce.common.locale.domain.Locale)
   */
  @Override public void setLocale(Locale locale) {
    this.locale = locale;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setLockedFlag(java.lang.Boolean)
   */
  @Override public void setLockedFlag(Boolean lockedFlag) {
    this.lockedFlag = lockedFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setOfflineFlag(java.lang.Boolean)
   */
  @Override public void setOfflineFlag(Boolean offlineFlag) {
    this.offlineFlag = offlineFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setOriginalItemId(java.lang.Long)
   */
  @Override public void setOriginalItemId(Long originalItemId) {
    this.originalItemId = originalItemId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setOriginalSandBox(org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public void setOriginalSandBox(SandBox originalSandBox) {
    this.originalSandBox = originalSandBox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setPriority(java.lang.Integer)
   */
  @Override public void setPriority(Integer priority) {
    this.priority = priority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setQualifyingItemCriteria(java.util.Set)
   */
  @Override public void setQualifyingItemCriteria(Set<StructuredContentItemCriteria> qualifyingItemCriteria) {
    this.qualifyingItemCriteria = qualifyingItemCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setSandbox(org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public void setSandbox(SandBox sandbox) {
    this.sandbox = sandbox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setStructuredContentFields(java.util.Map)
   */
  @Override public void setStructuredContentFields(Map<String, StructuredContentField> structuredContentFields) {
    this.structuredContentFields = structuredContentFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setStructuredContentMatchRules(java.util.Map)
   */
  @Override public void setStructuredContentMatchRules(Map<String, StructuredContentRule> structuredContentMatchRules) {
    this.structuredContentMatchRules = structuredContentMatchRules;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.structure.domain.StructuredContent#setStructuredContentType(org.broadleafcommerce.cms.structure.domain.StructuredContentType)
   */
  @Override public void setStructuredContentType(StructuredContentType structuredContentType) {
    this.structuredContentType = structuredContentType;
  }

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  public static class Presentation {
    //~ Inner Classes --------------------------------------------------------------------------------------------------

    public static class Tab {
      //~ Inner Classes ------------------------------------------------------------------------------------------------

      public static class Name {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final String Rules = "StructuredContentImpl_Rules_Tab";
      }

      public static class Order {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final int Rules = 1000;
      }
    }

    public static class Group {
      //~ Inner Classes ------------------------------------------------------------------------------------------------

      public static class Name {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final String Description = "StructuredContentImpl_Description";
        public static final String Internal    = "StructuredContentImpl_Internal";
        public static final String Rules       = "StructuredContentImpl_Rules";
      }

      public static class Order {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final int Description = 1000;
        public static final int Internal    = 2000;
        public static final int Rules       = 1000;
      }
    }
  } // end class Presentation

} // end class StructuredContentImpl
