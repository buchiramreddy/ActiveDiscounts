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

package org.broadleafcommerce.cms.page.domain;

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

import org.broadleafcommerce.common.admin.domain.AdminMainEntity;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationMapField;
import org.broadleafcommerce.common.presentation.AdminPresentationMapFields;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.RequiredOverride;
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
  friendlyName        = "PageImpl_basePage"
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
    ),
    @AdminPresentationOverride(
      name = "pageTemplate.templateDescription",
      value = @AdminPresentation(excluded = true)
    ),
    @AdminPresentationOverride(
      name = "pageTemplate.templateName",
      value = @AdminPresentation(excluded = true)
    ),
    @AdminPresentationOverride(
      name = "pageTemplate.locale",
      value = @AdminPresentation(excluded = true)
    )
  }
)
@Entity
@EntityListeners(value = { AdminAuditableListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PAGE")
public class PageImpl implements Page, AdminMainEntity {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Integer ZERO = new Integer(0);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PageImpl_Archived",
    order        = 5,
    group        = Presentation.Group.Name.Basic,
    groupOrder   = Presentation.Group.Order.Basic,
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "ARCHIVED_FLAG")
  @Index(
    name        = "PAGE_ARCHVD_FLG_INDX",
    columnNames = { "ARCHIVED_FLAG" }
  )
  protected Boolean archivedFlag = false;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Embedded protected AdminAuditable auditable = new AdminAuditable();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PageImpl_Deleted",
    order        = 2,
    group        = Presentation.Group.Name.Basic,
    groupOrder   = Presentation.Group.Order.Basic,
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "DELETED_FLAG")
  @Index(
    name        = "PAGE_DLTD_FLG_INDX",
    columnNames = { "DELETED_FLAG" }
  )
  protected Boolean deletedFlag = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PageImpl_Description",
    order        = 3,
    group        = Presentation.Group.Name.Basic,
    groupOrder   = Presentation.Group.Order.Basic,
    prominent    = true,
    gridOrder    = 1
  )
  @Column(name = "DESCRIPTION")
  protected String description;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PageImpl_Full_Url",
    order        = 1,
    group        = Presentation.Group.Name.Basic,
    groupOrder   = Presentation.Group.Order.Basic,
    prominent    = true,
    gridOrder    = 2
  )
  @Column(name = "FULL_URL")
  @Index(
    name        = "PAGE_FULL_URL_INDEX",
    columnNames = { "FULL_URL" }
  )
  protected String fullUrl;

  /** DOCUMENT ME! */
  @Column(name = "PAGE_ID")
  @GeneratedValue(generator = "PageId")
  @GenericGenerator(
    name       = "PageId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "PageImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.cms.page.domain.PageImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PageImpl_Is_Locked",
    group        = Presentation.Group.Name.Page,
    groupOrder   = Presentation.Group.Order.Page,
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "LOCKED_FLAG")
  @Index(
    name        = "PAGE_LCKD_FLG_INDX",
    columnNames = { "LOCKED_FLAG" }
  )
  protected Boolean lockedFlag = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PageImpl_Offline",
    order        = 4,
    group        = Presentation.Group.Name.Basic,
    groupOrder   = Presentation.Group.Order.Basic
  )
  @Column(name = "OFFLINE_FLAG")
  protected Boolean offlineFlag = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PageImpl_Original_Page_ID",
    order        = 6,
    group        = Presentation.Group.Name.Page,
    groupOrder   = Presentation.Group.Order.Page,
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "ORIG_PAGE_ID")
  @Index(
    name        = "ORIG_PAGE_ID_INDX",
    columnNames = { "ORIG_PAGE_ID" }
  )
  protected Long originalPageId;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "ORIG_SANDBOX_ID")
  @ManyToOne(targetEntity = SandBoxImpl.class)
  protected SandBox originalSandBox;

  /** DOCUMENT ME! */
  @BatchSize(size = 20)
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @JoinTable(
    name               = "BLC_PAGE_FLD_MAP",
    joinColumns        =
      @JoinColumn(
        name           = "PAGE_ID",
        referencedColumnName = "PAGE_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "PAGE_FLD_ID",
        referencedColumnName = "PAGE_FLD_ID"
      )
  )
  @ManyToMany(
    targetEntity = PageFieldImpl.class,
    cascade      = CascadeType.ALL
  )
  @MapKeyColumn(name = "MAP_KEY")
  protected Map<String, PageField> pageFields = new HashMap<String, PageField>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName     = "PageImpl_Page_Template",
    order            = 2,
    group            = Presentation.Group.Name.Basic,
    groupOrder       = Presentation.Group.Order.Basic,
    requiredOverride = RequiredOverride.REQUIRED
  )
  @AdminPresentationToOneLookup(lookupDisplayProperty = "templateName")
  @JoinColumn(name = "PAGE_TMPLT_ID")
  @ManyToOne(targetEntity = PageTemplateImpl.class)
  protected PageTemplate pageTemplate;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "PageImpl_Priority",
    order        = 3,
    group        = Presentation.Group.Name.Basic,
    groupOrder   = Presentation.Group.Order.Basic
  )
  @Column(name = "PRIORITY")
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
    name               = "BLC_QUAL_CRIT_PAGE_XREF",
    joinColumns        = @JoinColumn(name = "PAGE_ID"),
    inverseJoinColumns = @JoinColumn(name = "PAGE_ITEM_CRITERIA_ID")
  )
  @OneToMany(
    fetch        = FetchType.LAZY,
    targetEntity = PageItemCriteriaImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected Set<PageItemCriteria> qualifyingItemCriteria = new HashSet<PageItemCriteria>();

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "SANDBOX_ID")
  @ManyToOne(targetEntity = SandBoxImpl.class)
  protected SandBox sandbox;

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
      )
    }
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @JoinTable(
    name               = "BLC_PAGE_RULE_MAP",
    inverseJoinColumns =
      @JoinColumn(
        name           = "PAGE_RULE_ID",
        referencedColumnName = "PAGE_RULE_ID"
      )
  )
  @ManyToMany(
    targetEntity = PageRuleImpl.class,
    cascade      = { CascadeType.ALL }
  )
  @MapKeyColumn(
    name     = "MAP_KEY",
    nullable = false
  )
  Map<String, PageRule> pageMatchRules = new HashMap<String, PageRule>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#cloneEntity()
   */
  @Override public Page cloneEntity() {
    PageImpl newPage = new PageImpl();

    newPage.archivedFlag    = archivedFlag;
    newPage.deletedFlag     = deletedFlag;
    newPage.pageTemplate    = pageTemplate;
    newPage.description     = description;
    newPage.sandbox         = sandbox;
    newPage.originalPageId  = originalPageId;
    newPage.offlineFlag     = offlineFlag;
    newPage.priority        = priority;
    newPage.originalSandBox = originalSandBox;
    newPage.fullUrl         = fullUrl;

    Map<String, PageRule> ruleMap = newPage.getPageMatchRules();

    for (String key : pageMatchRules.keySet()) {
      PageRule newField = pageMatchRules.get(key).cloneEntity();
      ruleMap.put(key, newField);
    }

    Set<PageItemCriteria> criteriaList = newPage.getQualifyingItemCriteria();

    for (PageItemCriteria pageItemCriteria : qualifyingItemCriteria) {
      PageItemCriteria newField = pageItemCriteria.cloneEntity();
      criteriaList.add(newField);
    }

    for (PageField oldPageField : pageFields.values()) {
      PageField newPageField = oldPageField.cloneEntity();
      newPageField.setPage(newPage);
      newPage.getPageFields().put(newPageField.getFieldKey(), newPageField);
    }

    return newPage;
  } // end method cloneEntity

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#getArchivedFlag()
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
   * @see  org.broadleafcommerce.cms.page.domain.Page#getAuditable()
   */
  @Override public AdminAuditable getAuditable() {
    return auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#getDeletedFlag()
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
   * @see  org.broadleafcommerce.cms.page.domain.Page#getDescription()
   */
  @Override public String getDescription() {
    return description;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#getFullUrl()
   */
  @Override public String getFullUrl() {
    return fullUrl;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#getLockedFlag()
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
   * @see  org.broadleafcommerce.common.admin.domain.AdminMainEntity#getMainEntityName()
   */
  @Override public String getMainEntityName() {
    return getFullUrl();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#getOfflineFlag()
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
   * @see  org.broadleafcommerce.cms.page.domain.Page#getOriginalPageId()
   */
  @Override public Long getOriginalPageId() {
    return originalPageId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#getOriginalSandBox()
   */
  @Override public SandBox getOriginalSandBox() {
    return originalSandBox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#getPageFields()
   */
  @Override public Map<String, PageField> getPageFields() {
    return pageFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#getPageMatchRules()
   */
  @Override public Map<String, PageRule> getPageMatchRules() {
    return pageMatchRules;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#getPageTemplate()
   */
  @Override public PageTemplate getPageTemplate() {
    return pageTemplate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#getPriority()
   */
  @Override public Integer getPriority() {
    if (priority == null) {
      return ZERO;
    }

    return priority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#getQualifyingItemCriteria()
   */
  @Override public Set<PageItemCriteria> getQualifyingItemCriteria() {
    return qualifyingItemCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#getSandbox()
   */
  @Override public SandBox getSandbox() {
    return sandbox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setArchivedFlag(java.lang.Boolean)
   */
  @Override public void setArchivedFlag(Boolean archivedFlag) {
    this.archivedFlag = archivedFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setAuditable(org.broadleafcommerce.openadmin.audit.AdminAuditable)
   */
  @Override public void setAuditable(AdminAuditable auditable) {
    this.auditable = auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setDeletedFlag(java.lang.Boolean)
   */
  @Override public void setDeletedFlag(Boolean deletedFlag) {
    this.deletedFlag = deletedFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setDescription(java.lang.String)
   */
  @Override public void setDescription(String description) {
    this.description = description;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setFullUrl(java.lang.String)
   */
  @Override public void setFullUrl(String fullUrl) {
    this.fullUrl = fullUrl;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setLockedFlag(java.lang.Boolean)
   */
  @Override public void setLockedFlag(Boolean lockedFlag) {
    this.lockedFlag = lockedFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setOfflineFlag(java.lang.Boolean)
   */
  @Override public void setOfflineFlag(Boolean offlineFlag) {
    this.offlineFlag = offlineFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setOriginalPageId(java.lang.Long)
   */
  @Override public void setOriginalPageId(Long originalPageId) {
    this.originalPageId = originalPageId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setOriginalSandBox(org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public void setOriginalSandBox(SandBox originalSandBox) {
    this.originalSandBox = originalSandBox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setPageFields(java.util.Map)
   */
  @Override public void setPageFields(Map<String, PageField> pageFields) {
    this.pageFields = pageFields;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setPageMatchRules(java.util.Map)
   */
  @Override public void setPageMatchRules(Map<String, PageRule> pageMatchRules) {
    this.pageMatchRules = pageMatchRules;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setPageTemplate(org.broadleafcommerce.cms.page.domain.PageTemplate)
   */
  @Override public void setPageTemplate(PageTemplate pageTemplate) {
    this.pageTemplate = pageTemplate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setPriority(java.lang.Integer)
   */
  @Override public void setPriority(Integer priority) {
    this.priority = priority;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setQualifyingItemCriteria(java.util.Set)
   */
  @Override public void setQualifyingItemCriteria(Set<PageItemCriteria> qualifyingItemCriteria) {
    this.qualifyingItemCriteria = qualifyingItemCriteria;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.page.domain.Page#setSandbox(org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public void setSandbox(SandBox sandbox) {
    this.sandbox = sandbox;
  }

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  public static class Presentation {
    //~ Inner Classes --------------------------------------------------------------------------------------------------

    public static class Tab {
      //~ Inner Classes ------------------------------------------------------------------------------------------------

      public static class Name {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final String Rules = "PageImpl_Rules_Tab";
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

        public static final String Basic = "PageImpl_Basic";
        public static final String Page  = "PageImpl_Page";
        public static final String Rules = "PageImpl_Rules";
      }

      public static class Order {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final int Basic = 1000;
        public static final int Page  = 2000;
        public static final int Rules = 1000;
      }
    }
  } // end class Presentation
} // end class PageImpl
