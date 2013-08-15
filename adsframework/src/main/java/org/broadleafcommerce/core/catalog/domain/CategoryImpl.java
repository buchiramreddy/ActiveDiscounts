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

package org.broadleafcommerce.core.catalog.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.admin.domain.AdminMainEntity;
import org.broadleafcommerce.common.cache.Hydrated;
import org.broadleafcommerce.common.cache.HydratedSetup;
import org.broadleafcommerce.common.cache.engine.CacheFactoryException;
import org.broadleafcommerce.common.i18n.service.DynamicTranslationProvider;
import org.broadleafcommerce.common.media.domain.Media;
import org.broadleafcommerce.common.media.domain.MediaImpl;
import org.broadleafcommerce.common.persistence.ArchiveStatus;
import org.broadleafcommerce.common.persistence.Status;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationAdornedTargetCollection;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationMap;
import org.broadleafcommerce.common.presentation.AdminPresentationMapKey;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.util.DateUtil;
import org.broadleafcommerce.common.util.UrlUtil;

import org.broadleafcommerce.core.inventory.service.type.InventoryType;
import org.broadleafcommerce.core.order.service.type.FulfillmentType;
import org.broadleafcommerce.core.search.domain.CategorySearchFacet;
import org.broadleafcommerce.core.search.domain.CategorySearchFacetImpl;
import org.broadleafcommerce.core.search.domain.SearchFacet;
import org.broadleafcommerce.core.search.domain.SearchFacetImpl;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;


/**
 * DOCUMENT ME!
 *
 * @author   bTaylor
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "CategoryImpl_baseCategory")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SQLDelete(sql = "UPDATE BLC_CATEGORY SET ARCHIVED = 'Y' WHERE CATEGORY_ID = ?")
@Table(name = "BLC_CATEGORY")
public class CategoryImpl implements Category, Status, AdminMainEntity {
  private static final long serialVersionUID = 1L;
  private static final Log  LOG              = LogFactory.getLog(CategoryImpl.class);

  private static String buildLink(Category category, boolean ignoreTopLevel) {
    Category      myCategory = category;
    StringBuilder linkBuffer = new StringBuilder(50);

    while (myCategory != null) {
      if (!ignoreTopLevel || (myCategory.getDefaultParentCategory() != null)) {
        if (linkBuffer.length() == 0) {
          linkBuffer.append(myCategory.getUrlKey());
        } else if ((myCategory.getUrlKey() != null) && !"/".equals(myCategory.getUrlKey())) {
          linkBuffer.insert(0, myCategory.getUrlKey() + '/');
        }
      }

      myCategory = myCategory.getDefaultParentCategory();
    }

    return linkBuffer.toString();
  }

  private static void fillInURLMapForCategory(Map<String, List<Long>> categoryUrlMap, Category category,
    String startingPath, List<Long> startingCategoryList) throws CacheFactoryException {
    String urlKey = category.getUrlKey();

    if (urlKey == null) {
      throw new CacheFactoryException("Cannot create childCategoryURLMap - the urlKey for a category("
        + category.getId() + ") was null");
    }

    String currentPath = "";

    if (!"/".equals(category.getUrlKey())) {
      currentPath = startingPath + "/" + category.getUrlKey();
    }

    List<Long> newCategoryList = new ArrayList<Long>(startingCategoryList);
    newCategoryList.add(category.getId());

    categoryUrlMap.put(currentPath, newCategoryList);

    for (CategoryXref currentCategory : category.getChildCategoryXrefs()) {
      fillInURLMapForCategory(categoryUrlMap, currentCategory.getSubCategory(), currentPath, newCategoryList);
    }
  }

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CategoryImpl_Category_ID",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "CATEGORY_ID")
  @GeneratedValue(generator = "CategoryId")
  @GenericGenerator(
    name       = "CategoryId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "CategoryImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.catalog.domain.CategoryImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CategoryImpl_Category_Name",
    order        = 1000,
    group        = Presentation.Group.Name.General,
    groupOrder   = Presentation.Group.Order.General,
    prominent    = true,
    gridOrder    = 1,
    columnWidth  = "300px",
    translatable = true
  )
  @Column(
    name     = "NAME",
    nullable = false
  )
  @Index(
    name        = "CATEGORY_NAME_INDEX",
    columnNames = { "NAME" }
  )
  protected String name;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CategoryImpl_Category_Url",
    order        = 2000,
    group        = Presentation.Group.Name.General,
    groupOrder   = Presentation.Group.Order.General,
    prominent    = true,
    gridOrder    = 2,
    columnWidth  = "300px"
  )
  @Column(name = "URL")
  @Index(
    name        = "CATEGORY_URL_INDEX",
    columnNames = { "URL" }
  )
  protected String url;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CategoryImpl_Category_Url_Key",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced,
    excluded     = true
  )
  @Column(name = "URL_KEY")
  @Index(
    name        = "CATEGORY_URLKEY_INDEX",
    columnNames = { "URL_KEY" }
  )
  protected String urlKey;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CategoryImpl_Category_Description",
    group        = Presentation.Group.Name.General,
    groupOrder   = Presentation.Group.Order.General,
    largeEntry   = true,
    excluded     = true,
    translatable = true
  )
  @Column(name = "DESCRIPTION")
  protected String description;

  /** DOCUMENT ME! */
  @Column(name = "TAX_CODE")
  protected String taxCode;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CategoryImpl_Category_Active_Start_Date",
    order        = 1000,
    group        = Presentation.Group.Name.ActiveDateRange,
    groupOrder   = Presentation.Group.Order.ActiveDateRange
  )
  @Column(name = "ACTIVE_START_DATE")
  protected Date activeStartDate;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CategoryImpl_Category_Active_End_Date",
    order        = 2000,
    group        = Presentation.Group.Name.ActiveDateRange,
    groupOrder   = Presentation.Group.Order.ActiveDateRange
  )
  @Column(name = "ACTIVE_END_DATE")
  protected Date activeEndDate;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CategoryImpl_Category_Display_Template",
    order        = 1000,
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced
  )
  @Column(name = "DISPLAY_TEMPLATE")
  protected String displayTemplate;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CategoryImpl_Category_Long_Description",
    order        = 3000,
    group        = Presentation.Group.Name.General,
    groupOrder   = Presentation.Group.Order.General,
    largeEntry   = true,
    fieldType    = SupportedFieldType.HTML_BASIC,
    translatable = true
  )
  @Column(
    name   = "LONG_DESCRIPTION",
    length = Integer.MAX_VALUE - 1
  )
  @Lob
  @Type(type = "org.hibernate.type.StringClobType")
  protected String      longDescription;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CategoryImpl_defaultParentCategory",
    order        = 4000,
    group        = Presentation.Group.Name.General,
    groupOrder   = Presentation.Group.Order.General
  )
  @AdminPresentationToOneLookup
  @Index(
    name        = "CATEGORY_PARENT_INDEX",
    columnNames = { "DEFAULT_PARENT_CATEGORY_ID" }
  )
  @JoinColumn(name = "DEFAULT_PARENT_CATEGORY_ID")
  @ManyToOne(targetEntity = CategoryImpl.class)
  protected Category                               defaultParentCategory;

  /** DOCUMENT ME! */
  @AdminPresentationAdornedTargetCollection(
    targetObjectProperty = "categoryXrefPK.subCategory",
    parentObjectProperty = "categoryXrefPK.category",
    friendlyName         = "allChildCategoriesTitle",
    sortProperty         = "displayOrder",
    tab                  = Presentation.Tab.Name.Advanced,
    tabOrder             = Presentation.Tab.Order.Advanced,
    gridVisibleFields    = { "name" }
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST })
  @OneToMany(
    targetEntity = CategoryXrefImpl.class,
    mappedBy     = "categoryXrefPK.category"
  )
  @OrderBy(value = "displayOrder")
  protected List<CategoryXref> allChildCategoryXrefs = new ArrayList<CategoryXref>(10);

  /** DOCUMENT ME! */
  @AdminPresentationAdornedTargetCollection(
    targetObjectProperty = "categoryXrefPK.category",
    parentObjectProperty = "categoryXrefPK.subCategory",
    friendlyName         = "allParentCategoriesTitle",
    sortProperty         = "displayOrder",
    tab                  = Presentation.Tab.Name.Advanced,
    tabOrder             = Presentation.Tab.Order.Advanced,
    gridVisibleFields    = { "name" }
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST })
  @OneToMany(
    targetEntity = CategoryXrefImpl.class,
    mappedBy     = "categoryXrefPK.subCategory"
  )
  @OrderBy(value = "displayOrder")
  protected List<CategoryXref> allParentCategoryXrefs = new ArrayList<CategoryXref>(10);

  /** DOCUMENT ME! */
  @AdminPresentationAdornedTargetCollection(
    targetObjectProperty = "categoryProductXref.product",
    parentObjectProperty = "categoryProductXref.category",
    friendlyName         = "allProductsTitle",
    sortProperty         = "displayOrder",
    tab                  = Presentation.Tab.Name.Products,
    tabOrder             = Presentation.Tab.Order.Products,
    gridVisibleFields    = { "defaultSku.name" }
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST })
  @OneToMany(
    targetEntity = CategoryProductXrefImpl.class,
    mappedBy     = "categoryProductXref.category"
  )
  @OrderBy(value = "displayOrder")
  protected List<CategoryProductXref> allProductXrefs = new ArrayList<CategoryProductXref>(10);

  /** DOCUMENT ME! */
  @BatchSize(size = 50)
  @CollectionTable(
    name        = "BLC_CATEGORY_IMAGE",
    joinColumns = @JoinColumn(name = "CATEGORY_ID")
  )
  @Column(name = "URL")
  @Deprecated @ElementCollection
  @MapKeyColumn(name = "NAME")
  protected Map<String, String> categoryImages = new HashMap<String, String>(10);

  /** DOCUMENT ME! */
  @AdminPresentationMap(
    friendlyName            = "SkuImpl_Sku_Media",
    tab                     = Presentation.Tab.Name.Media,
    tabOrder                = Presentation.Tab.Order.Media,
    keyPropertyFriendlyName = "SkuImpl_Sku_Media_Key",
    deleteEntityUponRemove  = true,
    mediaField              = "url",
    keys                    = {
      @AdminPresentationMapKey(
        keyName             = "primary",
        friendlyKeyName     = "mediaPrimary"
      ),
      @AdminPresentationMapKey(
        keyName             = "alt1",
        friendlyKeyName     = "mediaAlternate1"
      ),
      @AdminPresentationMapKey(
        keyName             = "alt2",
        friendlyKeyName     = "mediaAlternate2"
      ),
      @AdminPresentationMapKey(
        keyName             = "alt3",
        friendlyKeyName     = "mediaAlternate3"
      ),
      @AdminPresentationMapKey(
        keyName             = "alt4",
        friendlyKeyName     = "mediaAlternate4"
      ),
      @AdminPresentationMapKey(
        keyName             = "alt5",
        friendlyKeyName     = "mediaAlternate5"
      ),
      @AdminPresentationMapKey(
        keyName             = "alt6",
        friendlyKeyName     = "mediaAlternate6"
      )
    }
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @JoinTable(
    name               = "BLC_CATEGORY_MEDIA_MAP",
    inverseJoinColumns =
      @JoinColumn(
        name           = "MEDIA_ID",
        referencedColumnName = "MEDIA_ID"
      )
  )
  @ManyToMany(targetEntity = MediaImpl.class)
  @MapKeyColumn(name = "MAP_KEY")
  protected Map<String, Media> categoryMedia = new HashMap<String, Media>(10);

  /** DOCUMENT ME! */
  @AdminPresentationAdornedTargetCollection(
    friendlyName                  = "featuredProductsTitle",
    order                         = 1000,
    tab                           = Presentation.Tab.Name.Marketing,
    tabOrder                      = Presentation.Tab.Order.Marketing,
    targetObjectProperty          = "product",
    sortProperty                  = "sequence",
    maintainedAdornedTargetFields = { "promotionMessage" },
    gridVisibleFields             = { "defaultSku.name", "promotionMessage" }
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @OneToMany(
    mappedBy     = "category",
    targetEntity = FeaturedProductImpl.class,
    cascade      = { CascadeType.ALL }
  )
  @OrderBy(value = "sequence")
  protected List<FeaturedProduct> featuredProducts = new ArrayList<FeaturedProduct>(10);

  /** DOCUMENT ME! */
  @AdminPresentationAdornedTargetCollection(
    friendlyName                  = "crossSaleProductsTitle",
    order                         = 2000,
    tab                           = Presentation.Tab.Name.Marketing,
    tabOrder                      = Presentation.Tab.Order.Marketing,
    targetObjectProperty          = "relatedSaleProduct",
    sortProperty                  = "sequence",
    maintainedAdornedTargetFields = { "promotionMessage" },
    gridVisibleFields             = { "defaultSku.name", "promotionMessage" }
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @OneToMany(
    mappedBy     = "category",
    targetEntity = CrossSaleProductImpl.class,
    cascade      = { CascadeType.ALL }
  )
  @OrderBy(value = "sequence")
  protected List<RelatedProduct> crossSaleProducts = new ArrayList<RelatedProduct>();

  /** DOCUMENT ME! */
  @AdminPresentationAdornedTargetCollection(
    friendlyName                  = "upsaleProductsTitle",
    order                         = 3000,
    tab                           = Presentation.Tab.Name.Marketing,
    tabOrder                      = Presentation.Tab.Order.Marketing,
    targetObjectProperty          = "relatedSaleProduct",
    sortProperty                  = "sequence",
    maintainedAdornedTargetFields = { "promotionMessage" },
    gridVisibleFields             = { "defaultSku.name", "promotionMessage" }
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @OneToMany(
    mappedBy     = "category",
    targetEntity = UpSaleProductImpl.class,
    cascade      = { CascadeType.ALL }
  )
  @OrderBy(value = "sequence")
  protected List<RelatedProduct> upSaleProducts = new ArrayList<RelatedProduct>();

  /** DOCUMENT ME! */
  @AdminPresentationAdornedTargetCollection(
    friendlyName         = "categoryFacetsTitle",
    order                = 1000,
    tab                  = Presentation.Tab.Name.SearchFacets,
    tabOrder             = Presentation.Tab.Order.SearchFacets,
    targetObjectProperty = "searchFacet",
    sortProperty         = "sequence",
    gridVisibleFields    = { "field", "label", "searchDisplayPriority" }
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @OneToMany(
    mappedBy     = "category",
    targetEntity = CategorySearchFacetImpl.class,
    cascade      = { CascadeType.ALL }
  )
  @OrderBy(value = "sequence")
  protected List<CategorySearchFacet> searchFacets = new ArrayList<CategorySearchFacet>();

  /** DOCUMENT ME! */
  @AdminPresentationAdornedTargetCollection(
    order                = 2000,
    joinEntityClass      = "org.broadleafcommerce.core.search.domain.CategoryExcludedSearchFacetImpl",
    targetObjectProperty = "searchFacet",
    parentObjectProperty = "category",
    friendlyName         = "excludedFacetsTitle",
    tab                  = Presentation.Tab.Name.SearchFacets,
    tabOrder             = Presentation.Tab.Order.SearchFacets,
    gridVisibleFields    = { "field", "label", "searchDisplayPriority" }
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST })
  @JoinTable(
    name               = "BLC_CAT_SEARCH_FACET_EXCL_XREF",
    joinColumns        = @JoinColumn(name = "CATEGORY_ID"),
    inverseJoinColumns =
      @JoinColumn(
        name           = "SEARCH_FACET_ID",
        nullable       = true
      )
  )
  @ManyToMany(targetEntity = SearchFacetImpl.class)
  protected List<SearchFacet> excludedSearchFacets = new ArrayList<SearchFacet>(10);

  /** DOCUMENT ME! */
  @AdminPresentationMap(
    friendlyName            = "categoryAttributesTitle",
    tab                     = Presentation.Tab.Name.Advanced,
    tabOrder                = Presentation.Tab.Order.Advanced,
    deleteEntityUponRemove  = true,
    forceFreeFormKeys       = true,
    keyPropertyFriendlyName = "ProductAttributeImpl_Attribute_Name"
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @MapKey(name = "name")
  @OneToMany(
    mappedBy      = "category",
    targetEntity  = CategoryAttributeImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected Map<String, CategoryAttribute> categoryAttributes = new HashMap<String, CategoryAttribute>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "CategoryImpl_Category_InventoryType",
    order                = 2000,
    tab                  = Presentation.Tab.Name.Advanced,
    tabOrder             = Presentation.Tab.Order.Advanced,
    group                = Presentation.Group.Name.Advanced,
    groupOrder           = Presentation.Group.Order.Advanced,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.inventory.service.type.InventoryType"
  )
  @Column(name = "INVENTORY_TYPE")
  protected String inventoryType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "CategoryImpl_Category_FulfillmentType",
    order                = 3000,
    tab                  = Presentation.Tab.Name.Advanced,
    tabOrder             = Presentation.Tab.Order.Advanced,
    group                = Presentation.Group.Name.Advanced,
    groupOrder           = Presentation.Group.Order.Advanced,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.order.service.type.FulfillmentType"
  )
  @Column(name = "FULFILLMENT_TYPE")
  protected String fulfillmentType;

  /** DOCUMENT ME! */
  @Embedded protected ArchiveStatus archiveStatus = new ArchiveStatus();

  /** DOCUMENT ME! */
  @Deprecated
  @Hydrated(factoryMethod = "createChildCategoryURLMap")
  @Transient protected Map<String, List<Long>>             childCategoryURLMap;

  /** DOCUMENT ME! */
  @Hydrated(factoryMethod = "createChildCategoryIds")
  @Transient protected List<Long> childCategoryIds;

  /** DOCUMENT ME! */
  @Transient protected List<CategoryXref> childCategoryXrefs = new ArrayList<CategoryXref>(50);

  /** DOCUMENT ME! */
  @Transient protected List<Category> legacyChildCategories = new ArrayList<Category>(50);

  /** DOCUMENT ME! */
  @Transient protected List<Category> allLegacyChildCategories = new ArrayList<Category>(50);

  /** DOCUMENT ME! */
  @Transient protected List<FeaturedProduct> filteredFeaturedProducts = null;

  /** DOCUMENT ME! */
  @Transient protected List<RelatedProduct> filteredCrossSales = null;

  /** DOCUMENT ME! */
  @Transient protected List<RelatedProduct> filteredUpSales = null;

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getName()
   */
  @Override public String getName() {
    return DynamicTranslationProvider.getValue(this, "name", name);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getUrl()
   */
  @Override public String getUrl() {
    // TODO: if null return
    // if blank return
    // if startswith "/" return
    // if contains a ":" and no "?" or (contains a ":" before a "?") return
    // else "add a /" at the beginning
    if ((url == null) || url.equals("") || url.startsWith("/")) {
      return url;
    } else if ((url.contains(":") && !url.contains("?")) || (url.indexOf('?', url.indexOf(':')) != -1)) {
      return url;
    } else {
      return "/" + url;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setUrl(java.lang.String)
   */
  @Override public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getUrlKey()
   */
  @Override public String getUrlKey() {
    if (((urlKey == null) || "".equals(urlKey.trim())) && (name != null)) {
      return UrlUtil.generateUrlKey(name);
    }

    return urlKey;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getGeneratedUrl()
   */
  @Override public String getGeneratedUrl() {
    return buildLink(this, false);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setUrlKey(java.lang.String)
   */
  @Override public void setUrlKey(String urlKey) {
    this.urlKey = urlKey;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getDescription()
   */
  @Override public String getDescription() {
    return DynamicTranslationProvider.getValue(this, "description", description);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setDescription(java.lang.String)
   */
  @Override public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getActiveStartDate()
   */
  @Override public Date getActiveStartDate() {
    if ('Y' == getArchived()) {
      return null;
    }

    return activeStartDate;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setActiveStartDate(java.util.Date)
   */
  @Override public void setActiveStartDate(Date activeStartDate) {
    this.activeStartDate = new Date(activeStartDate.getTime());
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getActiveEndDate()
   */
  @Override public Date getActiveEndDate() {
    return activeEndDate;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setActiveEndDate(java.util.Date)
   */
  @Override public void setActiveEndDate(Date activeEndDate) {
    this.activeEndDate = new Date(activeEndDate.getTime());
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#isActive()
   */
  @Override public boolean isActive() {
    if (LOG.isDebugEnabled()) {
      if (!DateUtil.isActive(activeStartDate, activeEndDate, true)) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("category, " + id + ", inactive due to date");
        }
      }

      if ('Y' == getArchived()) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("category, " + id + ", inactive due to archived status");
        }
      }
    }

    return DateUtil.isActive(activeStartDate, activeEndDate, true) && ('Y' != getArchived());
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getDisplayTemplate()
   */
  @Override public String getDisplayTemplate() {
    return displayTemplate;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setDisplayTemplate(java.lang.String)
   */
  @Override public void setDisplayTemplate(String displayTemplate) {
    this.displayTemplate = displayTemplate;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getLongDescription()
   */
  @Override public String getLongDescription() {
    return DynamicTranslationProvider.getValue(this, "longDescription", longDescription);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setLongDescription(java.lang.String)
   */
  @Override public void setLongDescription(String longDescription) {
    this.longDescription = longDescription;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getDefaultParentCategory()
   */
  @Override public Category getDefaultParentCategory() {
    return defaultParentCategory;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setDefaultParentCategory(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public void setDefaultParentCategory(Category defaultParentCategory) {
    this.defaultParentCategory = defaultParentCategory;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getAllChildCategoryXrefs()
   */
  @Override public List<CategoryXref> getAllChildCategoryXrefs() {
    return allChildCategoryXrefs;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getChildCategoryXrefs()
   */
  @Override public List<CategoryXref> getChildCategoryXrefs() {
    if (childCategoryXrefs.isEmpty()) {
      for (CategoryXref category : allChildCategoryXrefs) {
        if (category.getSubCategory().isActive()) {
          childCategoryXrefs.add(category);
        }
      }
    }

    return Collections.unmodifiableList(childCategoryXrefs);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setChildCategoryXrefs(java.util.List)
   */
  @Override public void setChildCategoryXrefs(List<CategoryXref> childCategories) {
    this.childCategoryXrefs.clear();

    for (CategoryXref category : childCategories) {
      this.childCategoryXrefs.add(category);
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setAllChildCategoryXrefs(java.util.List)
   */
  @Override public void setAllChildCategoryXrefs(List<CategoryXref> childCategories) {
    allChildCategoryXrefs.clear();

    for (CategoryXref category : childCategories) {
      allChildCategoryXrefs.add(category);
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getAllChildCategories()
   */
  @Deprecated @Override public List<Category> getAllChildCategories() {
    if (allLegacyChildCategories.isEmpty()) {
      for (CategoryXref category : allChildCategoryXrefs) {
        allLegacyChildCategories.add(category.getSubCategory());
      }
    }

    return Collections.unmodifiableList(allLegacyChildCategories);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#hasAllChildCategories()
   */
  @Override public boolean hasAllChildCategories() {
    return !allChildCategoryXrefs.isEmpty();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setAllChildCategories(java.util.List)
   */
  @Deprecated @Override public void setAllChildCategories(List<Category> childCategories) {
    throw new UnsupportedOperationException("Not Supported - Use setAllChildCategoryXrefs()");
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getChildCategories()
   */
  @Deprecated @Override public List<Category> getChildCategories() {
    if (legacyChildCategories.isEmpty()) {
      for (CategoryXref category : allChildCategoryXrefs) {
        if (category.getSubCategory().isActive()) {
          legacyChildCategories.add(category.getSubCategory());
        }
      }
    }

    return Collections.unmodifiableList(legacyChildCategories);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#hasChildCategories()
   */
  @Override public boolean hasChildCategories() {
    return !getChildCategoryXrefs().isEmpty();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setChildCategories(java.util.List)
   */
  @Deprecated @Override public void setChildCategories(List<Category> childCategories) {
    throw new UnsupportedOperationException("Not Supported - Use setChildCategoryXrefs()");
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getChildCategoryIds()
   */
  @Override public List<Long> getChildCategoryIds() {
    if (childCategoryIds == null) {
      HydratedSetup.populateFromCache(this, "childCategoryIds");
    }

    return childCategoryIds;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setChildCategoryIds(java.util.List)
   */
  @Override public void setChildCategoryIds(List<Long> childCategoryIds) {
    this.childCategoryIds = childCategoryIds;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<Long> createChildCategoryIds() {
    childCategoryIds = new ArrayList<Long>();

    for (CategoryXref category : allChildCategoryXrefs) {
      if (category.getSubCategory().isActive()) {
        childCategoryIds.add(category.getSubCategory().getId());
      }
    }

    return childCategoryIds;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getCategoryImages()
   */
  @Deprecated @Override public Map<String, String> getCategoryImages() {
    return categoryImages;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getCategoryImage(java.lang.String)
   */
  @Deprecated @Override public String getCategoryImage(String imageKey) {
    return categoryImages.get(imageKey);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setCategoryImages(java.util.Map)
   */
  @Deprecated @Override public void setCategoryImages(Map<String, String> categoryImages) {
    this.categoryImages.clear();

    for (Map.Entry<String, String> me : categoryImages.entrySet()) {
      this.categoryImages.put(me.getKey(), me.getValue());
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getChildCategoryURLMap()
   */
  @Deprecated @Override public Map<String, List<Long>> getChildCategoryURLMap() {
    if (childCategoryURLMap == null) {
      HydratedSetup.populateFromCache(this, "childCategoryURLMap");
    }

    return childCategoryURLMap;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  public Map<String, List<Long>> createChildCategoryURLMap() {
    try {
      Map<String, List<Long>> newMap = new HashMap<String, List<Long>>(50);
      fillInURLMapForCategory(newMap, this, "", new ArrayList<Long>(10));

      return newMap;
    } catch (CacheFactoryException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setChildCategoryURLMap(java.util.Map)
   */
  @Deprecated @Override public void setChildCategoryURLMap(Map<String, List<Long>> childCategoryURLMap) {
    this.childCategoryURLMap = childCategoryURLMap;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#buildFullCategoryHierarchy(java.util.List)
   */
  @Override public List<Category> buildFullCategoryHierarchy(List<Category> currentHierarchy) {
    if (currentHierarchy == null) {
      currentHierarchy = new ArrayList<Category>();
      currentHierarchy.add(this);
    }

    List<Category> myParentCategories = new ArrayList<Category>();

    if (defaultParentCategory != null) {
      myParentCategories.add(defaultParentCategory);
    }

    if ((allParentCategoryXrefs != null) && (allParentCategoryXrefs.size() > 0)) {
      for (CategoryXref parent : allParentCategoryXrefs) {
        myParentCategories.add(parent.getCategory());
      }
    }

    for (Category category : myParentCategories) {
      if (!currentHierarchy.contains(category)) {
        currentHierarchy.add(category);
        category.buildFullCategoryHierarchy(currentHierarchy);
      }
    }

    return currentHierarchy;
  } // end method buildFullCategoryHierarchy

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#buildCategoryHierarchy(java.util.List)
   */
  @Override public List<Category> buildCategoryHierarchy(List<Category> currentHierarchy) {
    if (currentHierarchy == null) {
      currentHierarchy = new ArrayList<Category>();
      currentHierarchy.add(this);
    }

    if ((defaultParentCategory != null) && !currentHierarchy.contains(defaultParentCategory)) {
      currentHierarchy.add(defaultParentCategory);
      defaultParentCategory.buildCategoryHierarchy(currentHierarchy);
    }

    return currentHierarchy;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getAllParentCategoryXrefs()
   */
  @Override public List<CategoryXref> getAllParentCategoryXrefs() {
    return allParentCategoryXrefs;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setAllParentCategoryXrefs(java.util.List)
   */
  @Override public void setAllParentCategoryXrefs(List<CategoryXref> allParentCategories) {
    this.allParentCategoryXrefs.clear();
    allParentCategoryXrefs.addAll(allParentCategories);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getAllParentCategories()
   */
  @Deprecated @Override public List<Category> getAllParentCategories() {
    List<Category> parents = new ArrayList<Category>(allParentCategoryXrefs.size());

    for (CategoryXref xref : allParentCategoryXrefs) {
      parents.add(xref.getCategory());
    }

    return Collections.unmodifiableList(parents);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setAllParentCategories(java.util.List)
   */
  @Deprecated @Override public void setAllParentCategories(List<Category> allParentCategories) {
    throw new UnsupportedOperationException("Not Supported - Use setAllParentCategoryXrefs()");
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getFeaturedProducts()
   */
  @Override public List<FeaturedProduct> getFeaturedProducts() {
    if ((filteredFeaturedProducts == null) && (featuredProducts != null)) {
      filteredFeaturedProducts = new ArrayList<FeaturedProduct>(featuredProducts.size());
      filteredFeaturedProducts.addAll(featuredProducts);
      CollectionUtils.filter(filteredFeaturedProducts, new Predicate() {
          @Override public boolean evaluate(Object arg) {
            return 'Y' != ((Status) ((FeaturedProduct) arg).getProduct()).getArchived();
          }
        });
    }

    return filteredFeaturedProducts;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setFeaturedProducts(java.util.List)
   */
  @Override public void setFeaturedProducts(List<FeaturedProduct> featuredProducts) {
    this.featuredProducts.clear();

    for (FeaturedProduct featuredProduct : featuredProducts) {
      this.featuredProducts.add(featuredProduct);
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getCrossSaleProducts()
   */
  @Override public List<RelatedProduct> getCrossSaleProducts() {
    if ((filteredCrossSales == null) && (crossSaleProducts != null)) {
      filteredCrossSales = new ArrayList<RelatedProduct>(crossSaleProducts.size());
      filteredCrossSales.addAll(crossSaleProducts);
      CollectionUtils.filter(filteredCrossSales, new Predicate() {
          @Override public boolean evaluate(Object arg) {
            return 'Y' != ((Status) ((CrossSaleProductImpl) arg).getRelatedProduct()).getArchived();
          }
        });
    }

    return filteredCrossSales;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setCrossSaleProducts(java.util.List)
   */
  @Override public void setCrossSaleProducts(List<RelatedProduct> crossSaleProducts) {
    this.crossSaleProducts.clear();

    for (RelatedProduct relatedProduct : crossSaleProducts) {
      this.crossSaleProducts.add(relatedProduct);
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getUpSaleProducts()
   */
  @Override public List<RelatedProduct> getUpSaleProducts() {
    if ((filteredUpSales == null) && (upSaleProducts != null)) {
      filteredUpSales = new ArrayList<RelatedProduct>(upSaleProducts.size());
      filteredUpSales.addAll(upSaleProducts);
      CollectionUtils.filter(filteredUpSales, new Predicate() {
          @Override public boolean evaluate(Object arg) {
            return 'Y' != ((Status) ((UpSaleProductImpl) arg).getRelatedProduct()).getArchived();
          }
        });
    }

    return filteredUpSales;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getCumulativeCrossSaleProducts()
   */
  @Override public List<RelatedProduct> getCumulativeCrossSaleProducts() {
    Set<RelatedProduct> returnProductsSet = new LinkedHashSet<RelatedProduct>();

    List<Category> categoryHierarchy = buildCategoryHierarchy(null);

    for (Category category : categoryHierarchy) {
      returnProductsSet.addAll(category.getCrossSaleProducts());
    }

    return new ArrayList<RelatedProduct>(returnProductsSet);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getCumulativeUpSaleProducts()
   */
  @Override public List<RelatedProduct> getCumulativeUpSaleProducts() {
    Set<RelatedProduct> returnProductsSet = new LinkedHashSet<RelatedProduct>();

    List<Category> categoryHierarchy = buildCategoryHierarchy(null);

    for (Category category : categoryHierarchy) {
      returnProductsSet.addAll(category.getUpSaleProducts());
    }

    return new ArrayList<RelatedProduct>(returnProductsSet);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getCumulativeFeaturedProducts()
   */
  @Override public List<FeaturedProduct> getCumulativeFeaturedProducts() {
    Set<FeaturedProduct> returnProductsSet = new LinkedHashSet<FeaturedProduct>();

    List<Category> categoryHierarchy = buildCategoryHierarchy(null);

    for (Category category : categoryHierarchy) {
      returnProductsSet.addAll(category.getFeaturedProducts());
    }

    return new ArrayList<FeaturedProduct>(returnProductsSet);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setUpSaleProducts(java.util.List)
   */
  @Override public void setUpSaleProducts(List<RelatedProduct> upSaleProducts) {
    this.upSaleProducts.clear();

    for (RelatedProduct relatedProduct : upSaleProducts) {
      this.upSaleProducts.add(relatedProduct);
    }

    this.upSaleProducts = upSaleProducts;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getActiveProductXrefs()
   */
  @Override public List<CategoryProductXref> getActiveProductXrefs() {
    List<CategoryProductXref> result = new ArrayList<CategoryProductXref>();

    for (CategoryProductXref product : allProductXrefs) {
      if (product.getProduct().isActive()) {
        result.add(product);
      }
    }

    return Collections.unmodifiableList(result);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getAllProductXrefs()
   */
  @Override public List<CategoryProductXref> getAllProductXrefs() {
    return allProductXrefs;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setAllProductXrefs(java.util.List)
   */
  @Override public void setAllProductXrefs(List<CategoryProductXref> allProducts) {
    this.allProductXrefs.clear();
    allProductXrefs.addAll(allProducts);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getActiveProducts()
   */
  @Deprecated @Override public List<Product> getActiveProducts() {
    List<Product> result = new ArrayList<Product>();

    for (CategoryProductXref product : allProductXrefs) {
      if (product.getProduct().isActive()) {
        result.add(product.getProduct());
      }
    }

    return Collections.unmodifiableList(result);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getAllProducts()
   */
  @Deprecated @Override public List<Product> getAllProducts() {
    List<Product> result = new ArrayList<Product>();

    for (CategoryProductXref product : allProductXrefs) {
      result.add(product.getProduct());
    }

    return Collections.unmodifiableList(result);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setAllProducts(java.util.List)
   */
  @Deprecated @Override public void setAllProducts(List<Product> allProducts) {
    throw new UnsupportedOperationException("Not Supported - Use setAllProductXrefs()");
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getSearchFacets()
   */
  @Override public List<CategorySearchFacet> getSearchFacets() {
    return searchFacets;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setSearchFacets(java.util.List)
   */
  @Override public void setSearchFacets(List<CategorySearchFacet> searchFacets) {
    this.searchFacets = searchFacets;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getExcludedSearchFacets()
   */
  @Override public List<SearchFacet> getExcludedSearchFacets() {
    return excludedSearchFacets;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setExcludedSearchFacets(java.util.List)
   */
  @Override public void setExcludedSearchFacets(List<SearchFacet> excludedSearchFacets) {
    this.excludedSearchFacets = excludedSearchFacets;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getInventoryType()
   */
  @Override public InventoryType getInventoryType() {
    return InventoryType.getInstance(this.inventoryType);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setInventoryType(org.broadleafcommerce.core.inventory.service.type.InventoryType)
   */
  @Override public void setInventoryType(InventoryType inventoryType) {
    this.inventoryType = inventoryType.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getFulfillmentType()
   */
  @Override public FulfillmentType getFulfillmentType() {
    return FulfillmentType.getInstance(this.fulfillmentType);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setFulfillmentType(org.broadleafcommerce.core.order.service.type.FulfillmentType)
   */
  @Override public void setFulfillmentType(FulfillmentType fulfillmentType) {
    this.fulfillmentType = fulfillmentType.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getCumulativeSearchFacets()
   */
  @Override public List<CategorySearchFacet> getCumulativeSearchFacets() {
    final List<CategorySearchFacet> returnFacets = new ArrayList<CategorySearchFacet>();
    returnFacets.addAll(getSearchFacets());
    Collections.sort(returnFacets, facetPositionComparator);

    // Add in parent facets unless they are excluded
    List<CategorySearchFacet> parentFacets = null;

    if (defaultParentCategory != null) {
      parentFacets = defaultParentCategory.getCumulativeSearchFacets();
      CollectionUtils.filter(parentFacets, new Predicate() {
          @Override public boolean evaluate(Object arg) {
            CategorySearchFacet csf = (CategorySearchFacet) arg;

            return !getExcludedSearchFacets().contains(csf.getSearchFacet())
              && !returnFacets.contains(csf.getSearchFacet());
          }
        });
    }

    if (parentFacets != null) {
      returnFacets.addAll(parentFacets);
    }

    return returnFacets;
  } // end method getCumulativeSearchFacets

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getCategoryMedia()
   */
  @Override public Map<String, Media> getCategoryMedia() {
    return categoryMedia;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setCategoryMedia(java.util.Map)
   */
  @Override public void setCategoryMedia(Map<String, Media> categoryMedia) {
    this.categoryMedia.clear();

    for (Map.Entry<String, Media> me : categoryMedia.entrySet()) {
      this.categoryMedia.put(me.getKey(), me.getValue());
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getCategoryAttributesMap()
   */
  @Override public Map<String, CategoryAttribute> getCategoryAttributesMap() {
    return categoryAttributes;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setCategoryAttributesMap(java.util.Map)
   */
  @Override public void setCategoryAttributesMap(Map<String, CategoryAttribute> categoryAttributes) {
    this.categoryAttributes = categoryAttributes;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getCategoryAttributes()
   */
  @Override public List<CategoryAttribute> getCategoryAttributes() {
    List<CategoryAttribute> ca = new ArrayList<CategoryAttribute>(categoryAttributes.values());

    return Collections.unmodifiableList(ca);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setCategoryAttributes(java.util.List)
   */
  @Override public void setCategoryAttributes(List<CategoryAttribute> categoryAttributes) {
    this.categoryAttributes = new HashMap<String, CategoryAttribute>();

    for (CategoryAttribute categoryAttribute : categoryAttributes) {
      this.categoryAttributes.put(categoryAttribute.getName(), categoryAttribute);
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getCategoryAttributeByName(java.lang.String)
   */
  @Override public CategoryAttribute getCategoryAttributeByName(String name) {
    for (CategoryAttribute attribute : getCategoryAttributes()) {
      if (attribute.getName().equals(name)) {
        return attribute;
      }
    }

    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getMappedCategoryAttributes()
   */
  @Override public Map<String, CategoryAttribute> getMappedCategoryAttributes() {
    Map<String, CategoryAttribute> map = new HashMap<String, CategoryAttribute>();

    for (CategoryAttribute attr : getCategoryAttributes()) {
      map.put(attr.getName(), attr);
    }

    return map;
  }

  /**
   * @see  org.broadleafcommerce.common.persistence.Status#getArchived()
   */
  @Override public Character getArchived() {
    if (archiveStatus == null) {
      archiveStatus = new ArchiveStatus();
    }

    return archiveStatus.getArchived();
  }

  /**
   * @see  org.broadleafcommerce.common.persistence.Status#setArchived(java.lang.Character)
   */
  @Override public void setArchived(Character archived) {
    if (archiveStatus == null) {
      archiveStatus = new ArchiveStatus();
    }

    archiveStatus.setArchived(archived);
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int prime  = 31;
    int result = 1;
    result = (prime * result) + ((name == null) ? 0 : name.hashCode());
    result = (prime * result) + ((url == null) ? 0 : url.hashCode());

    return result;
  }

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

    CategoryImpl other = (CategoryImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }

    if (url == null) {
      if (other.url != null) {
        return false;
      }
    } else if (!url.equals(other.url)) {
      return false;
    }

    return true;
  } // end method equals

  /** DOCUMENT ME! */
  protected static Comparator<CategorySearchFacet> facetPositionComparator = new Comparator<CategorySearchFacet>() {
    @Override public int compare(CategorySearchFacet o1, CategorySearchFacet o2) {
      return o1.getSequence().compareTo(o2.getSequence());
    }
  };

  public static class Presentation {
    public static class Tab {
      public static class Name {
        public static final String Marketing    = "CategoryImpl_Marketing_Tab";
        public static final String Media        = "CategoryImpl_Media_Tab";
        public static final String Advanced     = "CategoryImpl_Advanced_Tab";
        public static final String Products     = "CategoryImpl_Products_Tab";
        public static final String SearchFacets = "CategoryImpl_categoryFacetsTab";
      }

      public static class Order {
        public static final int Marketing    = 2000;
        public static final int Media        = 3000;
        public static final int Advanced     = 4000;
        public static final int Products     = 5000;
        public static final int SearchFacets = 3500;
      }
    }

    public static class Group {
      public static class Name {
        public static final String General         = "CategoryImpl_Category_Description";
        public static final String ActiveDateRange = "CategoryImpl_Active_Date_Range";
        public static final String Advanced        = "CategoryImpl_Advanced";
      }

      public static class Order {
        public static final int General         = 1000;
        public static final int ActiveDateRange = 2000;
        public static final int Advanced        = 1000;
      }
    }
  } // end class Presentation

  /**
   * @see  org.broadleafcommerce.common.admin.domain.AdminMainEntity#getMainEntityName()
   */
  @Override public String getMainEntityName() {
    return getName();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#getTaxCode()
   */
  @Override public String getTaxCode() {
    return this.taxCode;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Category#setTaxCode(java.lang.String)
   */
  @Override public void setTaxCode(String taxCode) {
    this.taxCode = taxCode;
  }

} // end class CategoryImpl
