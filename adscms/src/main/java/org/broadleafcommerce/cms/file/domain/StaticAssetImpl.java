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

package org.broadleafcommerce.cms.file.domain;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.broadleafcommerce.cms.field.type.StorageType;

import org.broadleafcommerce.common.admin.domain.AdminMainEntity;
import org.broadleafcommerce.common.locale.domain.LocaleImpl;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationMap;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.RequiredOverride;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.presentation.override.AdminPresentationOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationOverrides;
import org.broadleafcommerce.common.sandbox.domain.SandBox;
import org.broadleafcommerce.common.sandbox.domain.SandBoxImpl;
import org.broadleafcommerce.common.site.domain.Site;

import org.broadleafcommerce.openadmin.audit.AdminAuditable;
import org.broadleafcommerce.openadmin.audit.AdminAuditableListener;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
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
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.TRUE)
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
      name = "sandbox",
      value = @AdminPresentation(excluded = true)
    )
  }
)
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blCMSElements"
)
@Entity
@EntityListeners(value = { AdminAuditableListener.class })
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_STATIC_ASSET")
public class StaticAssetImpl implements StaticAsset, AdminMainEntity {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 6990685254640110350L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StaticAssetImpl_Alt_Text",
    order        = Presentation.FieldOrder.ALT_TEXT,
    translatable = true
  )
  @Column(
    name     = "ALT_TEXT",
    nullable = true
  )
  protected String altText;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Embedded protected AdminAuditable auditable = new AdminAuditable();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StaticAssetImpl_File_Extension",
    order        = Presentation.FieldOrder.FILE_EXTENSION,
    tab          = Presentation.Tab.Name.File_Details,
    tabOrder     = Presentation.Tab.Order.File_Details,
    readOnly     = true
  )
  @Column(name = "FILE_EXTENSION")
  protected String fileExtension;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StaticAssetImpl_File_Size_Bytes",
    order        = Presentation.FieldOrder.FILE_SIZE,
    tab          = Presentation.Tab.Name.File_Details,
    tabOrder     = Presentation.Tab.Order.File_Details,
    readOnly     = true
  )
  @Column(name = "FILE_SIZE")
  protected Long fileSize;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName     = "StaticAssetImpl_Full_URL",
    order            = Presentation.FieldOrder.URL,
    gridOrder        = Presentation.FieldOrder.URL,
    requiredOverride = RequiredOverride.REQUIRED,
    fieldType        = SupportedFieldType.ASSET_URL,
    prominent        = true
  )
  @Column(
    name     = "FULL_URL",
    nullable = false
  )
  @Index(
    name        = "ASST_FULL_URL_INDX",
    columnNames = { "FULL_URL" }
  )
  protected String fullUrl;

  /** DOCUMENT ME! */
  @Column(name = "STATIC_ASSET_ID")
  @GeneratedValue(generator = "StaticAssetId")
  @GenericGenerator(
    name       = "StaticAssetId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "StaticAssetImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.cms.file.domain.StaticAssetImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StaticAssetImpl_Mime_Type",
    order        = Presentation.FieldOrder.MIME_TYPE,
    tab          = Presentation.Tab.Name.File_Details,
    tabOrder     = Presentation.Tab.Order.File_Details,
    readOnly     = true
  )
  @Column(name = "MIME_TYPE")
  protected String mimeType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName     = "StaticAssetImpl_Item_Name",
    order            = Presentation.FieldOrder.NAME,
    requiredOverride = RequiredOverride.NOT_REQUIRED,
    gridOrder        = Presentation.FieldOrder.NAME,
    prominent        = true
  )
  @Column(
    name     = "NAME",
    nullable = false
  )
  protected String name;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StaticAssetImpl_Archived_Flag",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "ARCHIVED_FLAG")
  @Index(
    name        = "ASST_ARCHVD_FLG_INDX",
    columnNames = { "ARCHIVED_FLAG" }
  )
  protected Boolean archivedFlag = false;

  /** DOCUMENT ME! */
  @AdminPresentationMap(
    excluded                       = true,
    tab                            = Presentation.Tab.Name.Advanced,
    tabOrder                       = Presentation.Tab.Order.Advanced,
    friendlyName                   = "assetDescriptionTitle",
    keyPropertyFriendlyName        = "SkuImpl_Sku_Media_Key",
    deleteEntityUponRemove         = true,
    mapKeyOptionEntityClass        = LocaleImpl.class,
    mapKeyOptionEntityDisplayField = "friendlyName",
    mapKeyOptionEntityValueField   = "localeCode"
  )
  @BatchSize(size = 20)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blCMSElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @JoinTable(
    name               = "BLC_ASSET_DESC_MAP",
    joinColumns        = @JoinColumn(name = "STATIC_ASSET_ID"),
    inverseJoinColumns = @JoinColumn(name = "STATIC_ASSET_DESC_ID")
  )
  @ManyToMany(
    targetEntity = StaticAssetDescriptionImpl.class,
    cascade      = CascadeType.ALL
  )
  @MapKeyColumn(name = "MAP_KEY")
  protected Map<String, StaticAssetDescription> contentMessageValues = new HashMap<String, StaticAssetDescription>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StaticAssetImpl_Deleted_Flag",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "DELETED_FLAG")
  @Index(
    name        = "ASST_DLTD_FLG_INDX",
    columnNames = { "DELETED_FLAG" }
  )
  protected Boolean deletedFlag = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StaticAssetImpl_Is_Locked",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "LOCKED_FLAG")
  @Index(
    name        = "ASST_LCKD_FLG_INDX",
    columnNames = { "LOCKED_FLAG" }
  )
  protected Boolean lockedFlag = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StaticAssetImpl_Original_Asset_ID",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "ORIG_ASSET_ID")
  @Index(
    name        = "ORIG_ASSET_ID_INDX",
    columnNames = { "ORIG_ASSET_ID" }
  )
  protected Long originalAssetId;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "ORIG_SANDBOX_ID")
  @ManyToOne(targetEntity = SandBoxImpl.class)
  protected SandBox originalSandBox;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "SANDBOX_ID")
  @ManyToOne(targetEntity = SandBoxImpl.class)
  protected SandBox sandbox;

  /*@ManyToOne(targetEntity = SiteImpl.class)
  @JoinColumn(name="SITE_ID")*/
  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Transient protected Site site;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(name = "STORAGE_TYPE")
  protected String storageType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "StaticAssetImpl_Title",
    order        = Presentation.FieldOrder.TITLE,
    translatable = true
  )
  @Column(
    name     = "TITLE",
    nullable = true
  )
  protected String title;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#cloneEntity()
   */
  @Override public StaticAsset cloneEntity() {
    StaticAssetImpl asset = new StaticAssetImpl();
    asset.name            = name;
    asset.site            = site;
    asset.archivedFlag    = archivedFlag;
    asset.deletedFlag     = deletedFlag;
    asset.fullUrl         = fullUrl;
    asset.fileSize        = fileSize;
    asset.mimeType        = mimeType;
    asset.sandbox         = sandbox;
    asset.originalSandBox = originalSandBox;
    asset.originalAssetId = originalAssetId;
    asset.fileExtension   = fileExtension;

    for (String key : contentMessageValues.keySet()) {
      StaticAssetDescription oldAssetDescription = contentMessageValues.get(key);
      StaticAssetDescription newAssetDescription = oldAssetDescription.cloneEntity();
      asset.getContentMessageValues().put(key, newAssetDescription);
    }

    return asset;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getAltText()
   */
  @Override public String getAltText() {
    return altText;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getArchivedFlag()
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
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getAuditable()
   */
  @Override public AdminAuditable getAuditable() {
    return auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getContentMessageValues()
   */
  @Override public Map<String, StaticAssetDescription> getContentMessageValues() {
    return contentMessageValues;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getDeletedFlag()
   */
  @Override public Boolean getDeletedFlag() {
    return deletedFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getFileExtension()
   */
  @Override public String getFileExtension() {
    return fileExtension;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getFileSize()
   */
  @Override public Long getFileSize() {
    return fileSize;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getFullUrl()
   */
  @Override public String getFullUrl() {
    return fullUrl;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getLockedFlag()
   */
  @Override public Boolean getLockedFlag() {
    return lockedFlag;
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
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getMimeType()
   */
  @Override public String getMimeType() {
    return mimeType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getOriginalAssetId()
   */
  @Override public Long getOriginalAssetId() {
    return originalAssetId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getOriginalSandBox()
   */
  @Override public SandBox getOriginalSandBox() {
    return originalSandBox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getSandbox()
   */
  @Override public SandBox getSandbox() {
    return sandbox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getSite()
   */
  @Override public Site getSite() {
    return site;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getStorageType()
   */
  @Override public StorageType getStorageType() {
    StorageType st = StorageType.getInstance(storageType);

    if (st == null) {
      return StorageType.DATABASE;
    } else {
      return st;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#getTitle()
   */
  @Override public String getTitle() {
    return title;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setAltText(java.lang.String)
   */
  @Override public void setAltText(String altText) {
    this.altText = altText;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setArchivedFlag(java.lang.Boolean)
   */
  @Override public void setArchivedFlag(Boolean archivedFlag) {
    this.archivedFlag = archivedFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setAuditable(org.broadleafcommerce.openadmin.audit.AdminAuditable)
   */
  @Override public void setAuditable(AdminAuditable auditable) {
    this.auditable = auditable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setContentMessageValues(java.util.Map)
   */
  @Override public void setContentMessageValues(Map<String, StaticAssetDescription> contentMessageValues) {
    this.contentMessageValues = contentMessageValues;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setDeletedFlag(java.lang.Boolean)
   */
  @Override public void setDeletedFlag(Boolean deletedFlag) {
    this.deletedFlag = deletedFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setFileExtension(java.lang.String)
   */
  @Override public void setFileExtension(String fileExtension) {
    this.fileExtension = fileExtension;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setFileSize(java.lang.Long)
   */
  @Override public void setFileSize(Long fileSize) {
    this.fileSize = fileSize;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setFullUrl(java.lang.String)
   */
  @Override public void setFullUrl(String fullUrl) {
    this.fullUrl = fullUrl;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setLockedFlag(java.lang.Boolean)
   */
  @Override public void setLockedFlag(Boolean lockedFlag) {
    this.lockedFlag = lockedFlag;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setMimeType(java.lang.String)
   */
  @Override public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setOriginalAssetId(java.lang.Long)
   */
  @Override public void setOriginalAssetId(Long originalAssetId) {
    this.originalAssetId = originalAssetId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setOriginalSandBox(org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public void setOriginalSandBox(SandBox originalSandBox) {
    this.originalSandBox = originalSandBox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setSandbox(org.broadleafcommerce.common.sandbox.domain.SandBox)
   */
  @Override public void setSandbox(SandBox sandbox) {
    this.sandbox = sandbox;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setSite(org.broadleafcommerce.common.site.domain.Site)
   */
  @Override public void setSite(Site site) {
    this.site = site;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setStorageType(org.broadleafcommerce.cms.field.type.StorageType)
   */
  @Override public void setStorageType(StorageType storageType) {
    this.storageType = storageType.getType();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.cms.file.domain.StaticAsset#setTitle(java.lang.String)
   */
  @Override public void setTitle(String title) {
    this.title = title;
  }

  //~ Inner Classes ----------------------------------------------------------------------------------------------------

  public static class Presentation {
    //~ Inner Classes --------------------------------------------------------------------------------------------------

    public static class Tab {
      //~ Inner Classes ------------------------------------------------------------------------------------------------

      public static class Name {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final String File_Details = "StaticAssetImpl_FileDetails_Tab";
        public static final String Advanced     = "StaticAssetImpl_Advanced_Tab";
      }

      public static class Order {
        //~ Static fields/initializers ---------------------------------------------------------------------------------

        public static final int File_Details = 2000;
        public static final int Advanced     = 3000;
      }
    }

    public static class FieldOrder {
      //~ Static fields/initializers -----------------------------------------------------------------------------------

      // General Fields
      public static final int NAME     = 1000;
      public static final int URL      = 2000;
      public static final int TITLE    = 3000;
      public static final int ALT_TEXT = 4000;

      public static final int MIME_TYPE      = 5000;
      public static final int FILE_EXTENSION = 6000;
      public static final int FILE_SIZE      = 7000;

      // Used by subclasses to know where the last field is.
      public static final int LAST = 7000;

    }
  } // end class Presentation
} // end class StaticAssetImpl
