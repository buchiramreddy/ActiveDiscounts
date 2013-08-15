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

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import javax.persistence.MapKey;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.admin.domain.AdminMainEntity;
import org.broadleafcommerce.common.media.domain.Media;
import org.broadleafcommerce.common.persistence.ArchiveStatus;
import org.broadleafcommerce.common.persistence.Status;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationAdornedTargetCollection;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.AdminPresentationMap;
import org.broadleafcommerce.common.presentation.AdminPresentationOperationTypes;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.RequiredOverride;
import org.broadleafcommerce.common.presentation.client.AddMethodType;
import org.broadleafcommerce.common.presentation.client.OperationType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.util.DateUtil;
import org.broadleafcommerce.common.vendor.service.type.ContainerShapeType;
import org.broadleafcommerce.common.vendor.service.type.ContainerSizeType;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.SQLDelete;


/**
 * The Class ProductImpl is the default implementation of {@link org.broadleafcommerce.core.catalog.domain.Product}. A
 * product is a general description of an item that can be sold (for example: a hat). Products are not sold or added to
 * a cart. {@link org.broadleafcommerce.core.catalog.domain.Sku}s which are specific items (for example: a XL Blue Hat)
 * are sold or added to a cart.<br>
 * <br>
 * If you want to add fields specific to your implementation of BroadLeafCommerce you should extend this class and add
 * your fields. If you need to make significant changes to the ProductImpl then you should implement your own version of
 * {@link org.broadleafcommerce.core.catalog.domain.Product}.<br>
 * <br>
 * This implementation uses a Hibernate implementation of JPA configured through annotations. The Entity references the
 * following tables: BLC_PRODUCT, BLC_PRODUCT_SKU_XREF, BLC_PRODUCT_IMAGE
 *
 * @author   btaylor
 * @see      {@link org.broadleafcommerce.core.catalog.domain.Product},
 *           {@link org.broadleafcommerce.core.catalog.domain.SkuImpl},
 *           {@link org.broadleafcommerce.core.catalog.domain.CategoryImpl}
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(
  populateToOneFields = PopulateToOneFieldsEnum.TRUE,
  friendlyName        = "baseProduct"
)
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@SQLDelete(sql = "UPDATE BLC_PRODUCT SET ARCHIVED = 'Y' WHERE PRODUCT_ID = ?")
@javax.persistence.Table(name = "BLC_PRODUCT")

// multi-column indexes don't appear to get exported correctly when declared at the field level, so declaring here as a workaround
@org.hibernate.annotations.Table(
  appliesTo = "BLC_PRODUCT",
  indexes   = {
    @Index(
      name  = "PRODUCT_URL_INDEX",
      columnNames = { "URL", "URL_KEY" }
    )
  }
)
public class ProductImpl implements Product, Status, AdminMainEntity {
  private static final Log LOG = LogFactory.getLog(ProductImpl.class);

  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The id. */
  @AdminPresentation(
    friendlyName = "ProductImpl_Product_ID",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "PRODUCT_ID")
  @GeneratedValue(generator = "ProductId")
  @GenericGenerator(
    name       = "ProductId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "ProductImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.catalog.domain.ProductImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName     = "ProductImpl_Product_Url",
    order            = Presentation.FieldOrder.URL,
    group            = Presentation.Group.Name.General,
    groupOrder       = Presentation.Group.Order.General,
    prominent        = true,
    gridOrder        = 3,
    columnWidth      = "200px",
    requiredOverride = RequiredOverride.REQUIRED
  )
  @Column(name = "URL")
  protected String url;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ProductImpl_Product_UrlKey",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.General,
    groupOrder   = Presentation.Group.Order.General,
    excluded     = true
  )
  @Column(name = "URL_KEY")
  protected String urlKey;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ProductImpl_Product_Display_Template",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced
  )
  @Column(name = "DISPLAY_TEMPLATE")
  protected String displayTemplate;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ProductImpl_Product_Model",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced
  )
  @Column(name = "MODEL")
  protected String model;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ProductImpl_Product_Manufacturer",
    order        = Presentation.FieldOrder.MANUFACTURER,
    group        = Presentation.Group.Name.General,
    groupOrder   = Presentation.Group.Order.General,
    prominent    = true,
    gridOrder    = 4
  )
  @Column(name = "MANUFACTURE")
  protected String manufacturer;

  /** DOCUMENT ME! */
  @Column(name = "TAX_CODE")
  protected String taxCode;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ProductImpl_Is_Featured_Product",
    tab          = Presentation.Tab.Name.Marketing,
    tabOrder     = Presentation.Tab.Order.Marketing,
    group        = Presentation.Group.Name.Badges,
    groupOrder   = Presentation.Group.Order.Badges
  )
  @Column(
    name     = "IS_FEATURED_PRODUCT",
    nullable = false
  )
  protected Boolean isFeaturedProduct = false;

  /** DOCUMENT ME! */
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL })
  @OneToOne(
    optional     = false,
    targetEntity = SkuImpl.class,
    cascade      = { CascadeType.ALL },
    mappedBy     = "defaultProduct"
  )
  protected Sku defaultSku;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ProductImpl_Can_Sell_Without_Options",
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced
  )
  @Column(name = "CAN_SELL_WITHOUT_OPTIONS")
  protected Boolean canSellWithoutOptions = false;

  /** DOCUMENT ME! */
  @Transient protected List<Sku> skus = new ArrayList<Sku>();

  /** DOCUMENT ME! */
  @Transient protected String promoMessage;

  /** DOCUMENT ME! */
  @AdminPresentationAdornedTargetCollection(
    friendlyName                  = "crossSaleProductsTitle",
    order                         = 1000,
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
    mappedBy     = "product",
    targetEntity = CrossSaleProductImpl.class,
    cascade      = { CascadeType.ALL }
  )
  @OrderBy(value = "sequence")
  protected List<RelatedProduct> crossSaleProducts = new ArrayList<RelatedProduct>();

  /** DOCUMENT ME! */
  @AdminPresentationAdornedTargetCollection(
    friendlyName                  = "upsaleProductsTitle",
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
    mappedBy     = "product",
    targetEntity = UpSaleProductImpl.class,
    cascade      = { CascadeType.ALL }
  )
  @OrderBy(value = "sequence")
  protected List<RelatedProduct> upSaleProducts = new ArrayList<RelatedProduct>();

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "ProductImpl_Additional_Skus",
    order        = 1000,
    tab          = Presentation.Tab.Name.ProductOptions,
    tabOrder     = Presentation.Tab.Order.ProductOptions
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @OneToMany(
    fetch        = FetchType.LAZY,
    targetEntity = SkuImpl.class,
    mappedBy     = "product"
  )
  protected List<Sku> additionalSkus = new ArrayList<Sku>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName     = "ProductImpl_Product_Default_Category",
    order            = Presentation.FieldOrder.DEFAULT_CATEGORY,
    group            = Presentation.Group.Name.General,
    groupOrder       = Presentation.Group.Order.General,
    prominent        = true,
    gridOrder        = 2,
    requiredOverride = RequiredOverride.REQUIRED
  )
  @AdminPresentationToOneLookup
  @Index(
    name        = "PRODUCT_CATEGORY_INDEX",
    columnNames = { "DEFAULT_CATEGORY_ID" }
  )
  @JoinColumn(name = "DEFAULT_CATEGORY_ID")
  @ManyToOne(targetEntity = CategoryImpl.class)
  protected Category                               defaultCategory;

  /** DOCUMENT ME! */
  @AdminPresentationAdornedTargetCollection(
    friendlyName         = "allParentCategoriesTitle",
    order                = 3000,
    tab                  = Presentation.Tab.Name.Marketing,
    tabOrder             = Presentation.Tab.Order.Marketing,
    joinEntityClass      = "org.broadleafcommerce.core.catalog.domain.CategoryProductXrefImpl",
    targetObjectProperty = "categoryProductXref.category",
    parentObjectProperty = "categoryProductXref.product",
    sortProperty         = "displayOrder",
    gridVisibleFields    = { "name" }
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.MERGE, org.hibernate.annotations.CascadeType.PERSIST })
  @OneToMany(
    targetEntity = CategoryProductXrefImpl.class,
    mappedBy     = "categoryProductXref.product"
  )
  @OrderBy(value = "displayOrder")
  protected List<CategoryProductXref> allParentCategoryXrefs = new ArrayList<CategoryProductXref>();

  /** DOCUMENT ME! */
  @AdminPresentationMap(
    friendlyName            = "productAttributesTitle",
    tab                     = Presentation.Tab.Name.Advanced,
    tabOrder                = Presentation.Tab.Order.Advanced,
    deleteEntityUponRemove  = true,
    forceFreeFormKeys       = true,
    keyPropertyFriendlyName = "ProductAttributeImpl_Attribute_Name"
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blStandardElements"
  )
  @MapKey(name = "name")
  @OneToMany(
    mappedBy      = "product",
    targetEntity  = ProductAttributeImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected Map<String, ProductAttribute> productAttributes = new HashMap<String, ProductAttribute>();

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName   = "productOptionsTitle",
    tab            = Presentation.Tab.Name.ProductOptions,
    tabOrder       = Presentation.Tab.Order.ProductOptions,
    addType        = AddMethodType.LOOKUP,
    manyToField    = "products",
    operationTypes = @AdminPresentationOperationTypes(removeType = OperationType.NONDESTRUCTIVEREMOVE)
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @JoinTable(
    name               = "BLC_PRODUCT_OPTION_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "PRODUCT_ID",
        referencedColumnName = "PRODUCT_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "PRODUCT_OPTION_ID",
        referencedColumnName = "PRODUCT_OPTION_ID"
      )
  )
  @ManyToMany(
    fetch        = FetchType.LAZY,
    targetEntity = ProductOptionImpl.class
  )
  protected List<ProductOption> productOptions = new ArrayList<ProductOption>();

  /** DOCUMENT ME! */
  @Embedded protected ArchiveStatus archiveStatus = new ArchiveStatus();

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getName()
   */
  @Override public String getName() {
    return getDefaultSku().getName();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    getDefaultSku().setName(name);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getDescription()
   */
  @Override public String getDescription() {
    return getDefaultSku().getDescription();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setDescription(java.lang.String)
   */
  @Override public void setDescription(String description) {
    getDefaultSku().setDescription(description);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getLongDescription()
   */
  @Override public String getLongDescription() {
    return getDefaultSku().getLongDescription();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setLongDescription(java.lang.String)
   */
  @Override public void setLongDescription(String longDescription) {
    getDefaultSku().setLongDescription(longDescription);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getActiveStartDate()
   */
  @Override public Date getActiveStartDate() {
    return getDefaultSku().getActiveStartDate();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setActiveStartDate(java.util.Date)
   */
  @Override public void setActiveStartDate(Date activeStartDate) {
    getDefaultSku().setActiveStartDate(activeStartDate);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getActiveEndDate()
   */
  @Override public Date getActiveEndDate() {
    return getDefaultSku().getActiveEndDate();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setActiveEndDate(java.util.Date)
   */
  @Override public void setActiveEndDate(Date activeEndDate) {
    getDefaultSku().setActiveEndDate(activeEndDate);
  }

  /**
   * @see  org.broadleafcommerce.common.persistence.Status#isActive()
   */
  @Override public boolean isActive() {
    if (LOG.isDebugEnabled()) {
      if (!DateUtil.isActive(getActiveStartDate(), getActiveEndDate(), true)) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("product, " + id + ", inactive due to date");
        }
      }

      if ('Y' == getArchived()) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("product, " + id + ", inactive due to archived status");
        }
      }
    }

    return DateUtil.isActive(getActiveStartDate(), getActiveEndDate(), true) && ('Y' != getArchived());
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getModel()
   */
  @Override public String getModel() {
    return model;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setModel(java.lang.String)
   */
  @Override public void setModel(String model) {
    this.model = model;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getManufacturer()
   */
  @Override public String getManufacturer() {
    return manufacturer;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setManufacturer(java.lang.String)
   */
  @Override public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#isFeaturedProduct()
   */
  @Override public boolean isFeaturedProduct() {
    return isFeaturedProduct;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setFeaturedProduct(boolean)
   */
  @Override public void setFeaturedProduct(boolean isFeaturedProduct) {
    this.isFeaturedProduct = isFeaturedProduct;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getDefaultSku()
   */
  @Override public Sku getDefaultSku() {
    return defaultSku;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getCanSellWithoutOptions()
   */
  @Override public Boolean getCanSellWithoutOptions() {
    return (canSellWithoutOptions == null) ? false : canSellWithoutOptions;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setCanSellWithoutOptions(java.lang.Boolean)
   */
  @Override public void setCanSellWithoutOptions(Boolean canSellWithoutOptions) {
    this.canSellWithoutOptions = canSellWithoutOptions;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setDefaultSku(org.broadleafcommerce.core.catalog.domain.Sku)
   */
  @Override public void setDefaultSku(Sku defaultSku) {
    defaultSku.setDefaultProduct(this);
    this.defaultSku = defaultSku;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getPromoMessage()
   */
  @Override public String getPromoMessage() {
    return promoMessage;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setPromoMessage(java.lang.String)
   */
  @Override public void setPromoMessage(String promoMessage) {
    this.promoMessage = promoMessage;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getAllSkus()
   */
  @Override public List<Sku> getAllSkus() {
    List<Sku> allSkus = new ArrayList<Sku>();
    allSkus.add(getDefaultSku());

    for (Sku additionalSku : additionalSkus) {
      if (!additionalSku.getId().equals(getDefaultSku().getId())) {
        allSkus.add(additionalSku);
      }
    }

    return allSkus;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getSkus()
   */
  @Override public List<Sku> getSkus() {
    if (skus.size() == 0) {
      List<Sku> additionalSkus = getAdditionalSkus();

      for (Sku sku : additionalSkus) {
        if (sku.isActive()) {
          skus.add(sku);
        }
      }
    }

    return skus;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getAdditionalSkus()
   */
  @Override public List<Sku> getAdditionalSkus() {
    return additionalSkus;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setAdditionalSkus(java.util.List)
   */
  @Override public void setAdditionalSkus(List<Sku> skus) {
    this.additionalSkus.clear();

    for (Sku sku : skus) {
      this.additionalSkus.add(sku);
    }
    // this.skus.clear();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getDefaultCategory()
   */
  @Override public Category getDefaultCategory() {
    return defaultCategory;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getMedia()
   */
  @Override public Map<String, Media> getMedia() {
    return getDefaultSku().getSkuMedia();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setMedia(java.util.Map)
   */
  @Override public void setMedia(Map<String, Media> media) {
    getDefaultSku().setSkuMedia(media);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getAllSkuMedia()
   */
  @Override public Map<String, Media> getAllSkuMedia() {
    Map<String, Media> result = new HashMap<String, Media>();
    result.putAll(getMedia());

    for (Sku additionalSku : getAdditionalSkus()) {
      if (!additionalSku.getId().equals(getDefaultSku().getId())) {
        result.putAll(additionalSku.getSkuMedia());
      }
    }

    return result;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setDefaultCategory(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public void setDefaultCategory(Category defaultCategory) {
    this.defaultCategory = defaultCategory;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getAllParentCategoryXrefs()
   */
  @Override public List<CategoryProductXref> getAllParentCategoryXrefs() {
    return allParentCategoryXrefs;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setAllParentCategoryXrefs(java.util.List)
   */
  @Override public void setAllParentCategoryXrefs(List<CategoryProductXref> allParentCategories) {
    this.allParentCategoryXrefs.clear();
    allParentCategoryXrefs.addAll(allParentCategories);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getAllParentCategories()
   */
  @Deprecated @Override public List<Category> getAllParentCategories() {
    List<Category> parents = new ArrayList<Category>();

    for (CategoryProductXref xref : allParentCategoryXrefs) {
      parents.add(xref.getCategory());
    }

    return Collections.unmodifiableList(parents);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setAllParentCategories(java.util.List)
   */
  @Deprecated @Override public void setAllParentCategories(List<Category> allParentCategories) {
    throw new UnsupportedOperationException("Not Supported - Use setAllParentCategoryXrefs()");
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getDimension()
   */
  @Override public Dimension getDimension() {
    return getDefaultSku().getDimension();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setDimension(org.broadleafcommerce.core.catalog.domain.Dimension)
   */
  @Override public void setDimension(Dimension dimension) {
    getDefaultSku().setDimension(dimension);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getWidth()
   */
  @Override public BigDecimal getWidth() {
    return getDefaultSku().getDimension().getWidth();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setWidth(java.math.BigDecimal)
   */
  @Override public void setWidth(BigDecimal width) {
    getDefaultSku().getDimension().setWidth(width);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getHeight()
   */
  @Override public BigDecimal getHeight() {
    return getDefaultSku().getDimension().getHeight();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setHeight(java.math.BigDecimal)
   */
  @Override public void setHeight(BigDecimal height) {
    getDefaultSku().getDimension().setHeight(height);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getDepth()
   */
  @Override public BigDecimal getDepth() {
    return getDefaultSku().getDimension().getDepth();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setDepth(java.math.BigDecimal)
   */
  @Override public void setDepth(BigDecimal depth) {
    getDefaultSku().getDimension().setDepth(depth);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getGirth()
   */
  @Override public BigDecimal getGirth() {
    return getDefaultSku().getDimension().getGirth();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setGirth(java.math.BigDecimal)
   */
  @Override public void setGirth(BigDecimal girth) {
    getDefaultSku().getDimension().setGirth(girth);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getSize()
   */
  @Override public ContainerSizeType getSize() {
    return getDefaultSku().getDimension().getSize();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setSize(org.broadleafcommerce.common.vendor.service.type.ContainerSizeType)
   */
  @Override public void setSize(ContainerSizeType size) {
    getDefaultSku().getDimension().setSize(size);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getContainer()
   */
  @Override public ContainerShapeType getContainer() {
    return getDefaultSku().getDimension().getContainer();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setContainer(org.broadleafcommerce.common.vendor.service.type.ContainerShapeType)
   */
  @Override public void setContainer(ContainerShapeType container) {
    getDefaultSku().getDimension().setContainer(container);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getDimensionString()
   */
  @Override public String getDimensionString() {
    return getDefaultSku().getDimension().getDimensionString();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getWeight()
   */
  @Override public Weight getWeight() {
    return getDefaultSku().getWeight();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setWeight(org.broadleafcommerce.core.catalog.domain.Weight)
   */
  @Override public void setWeight(Weight weight) {
    getDefaultSku().setWeight(weight);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getCrossSaleProducts()
   */
  @Override public List<RelatedProduct> getCrossSaleProducts() {
    List<RelatedProduct> returnProducts = new ArrayList<RelatedProduct>();

    if (crossSaleProducts != null) {
      returnProducts.addAll(crossSaleProducts);
      CollectionUtils.filter(returnProducts, new Predicate() {
          @Override public boolean evaluate(Object arg) {
            return 'Y' != ((Status) ((CrossSaleProductImpl) arg).getRelatedProduct()).getArchived();
          }
        });
    }

    return returnProducts;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setCrossSaleProducts(java.util.List)
   */
  @Override public void setCrossSaleProducts(List<RelatedProduct> crossSaleProducts) {
    this.crossSaleProducts.clear();

    for (RelatedProduct relatedProduct : crossSaleProducts) {
      this.crossSaleProducts.add(relatedProduct);
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getUpSaleProducts()
   */
  @Override public List<RelatedProduct> getUpSaleProducts() {
    List<RelatedProduct> returnProducts = new ArrayList<RelatedProduct>();

    if (upSaleProducts != null) {
      returnProducts.addAll(upSaleProducts);
      CollectionUtils.filter(returnProducts, new Predicate() {
          @Override public boolean evaluate(Object arg) {
            return 'Y' != ((Status) ((UpSaleProductImpl) arg).getRelatedProduct()).getArchived();
          }
        });
    }

    return returnProducts;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setUpSaleProducts(java.util.List)
   */
  @Override public void setUpSaleProducts(List<RelatedProduct> upSaleProducts) {
    this.upSaleProducts.clear();

    for (RelatedProduct relatedProduct : upSaleProducts) {
      this.upSaleProducts.add(relatedProduct);
    }

    this.upSaleProducts = upSaleProducts;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getCumulativeCrossSaleProducts()
   */
  @Override public List<RelatedProduct> getCumulativeCrossSaleProducts() {
    List<RelatedProduct> returnProducts = getCrossSaleProducts();

    if (defaultCategory != null) {
      List<RelatedProduct> categoryProducts = defaultCategory.getCumulativeCrossSaleProducts();

      if (categoryProducts != null) {
        returnProducts.addAll(categoryProducts);
      }
    }

    Iterator<RelatedProduct> itr = returnProducts.iterator();

    while (itr.hasNext()) {
      RelatedProduct relatedProduct = itr.next();

      if (relatedProduct.getRelatedProduct().equals(this)) {
        itr.remove();
      }
    }

    return returnProducts;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getCumulativeUpSaleProducts()
   */
  @Override public List<RelatedProduct> getCumulativeUpSaleProducts() {
    List<RelatedProduct> returnProducts = getUpSaleProducts();

    if (defaultCategory != null) {
      List<RelatedProduct> categoryProducts = defaultCategory.getCumulativeUpSaleProducts();

      if (categoryProducts != null) {
        returnProducts.addAll(categoryProducts);
      }
    }

    Iterator<RelatedProduct> itr = returnProducts.iterator();

    while (itr.hasNext()) {
      RelatedProduct relatedProduct = itr.next();

      if (relatedProduct.getRelatedProduct().equals(this)) {
        itr.remove();
      }
    }

    return returnProducts;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getProductAttributes()
   */
  @Override public Map<String, ProductAttribute> getProductAttributes() {
    return productAttributes;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setProductAttributes(java.util.Map)
   */
  @Override public void setProductAttributes(Map<String, ProductAttribute> productAttributes) {
    this.productAttributes = productAttributes;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getProductOptions()
   */
  @Override public List<ProductOption> getProductOptions() {
    return productOptions;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setProductOptions(java.util.List)
   */
  @Override public void setProductOptions(List<ProductOption> productOptions) {
    this.productOptions = productOptions;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getUrl()
   */
  @Override public String getUrl() {
    if (url == null) {
      return getGeneratedUrl();
    } else {
      return url;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setUrl(java.lang.String)
   */
  @Override public void setUrl(String url) {
    this.url = url;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getDisplayTemplate()
   */
  @Override public String getDisplayTemplate() {
    return displayTemplate;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setDisplayTemplate(java.lang.String)
   */
  @Override public void setDisplayTemplate(String displayTemplate) {
    this.displayTemplate = displayTemplate;
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
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((skus == null) ? 0 : skus.hashCode());

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

    ProductImpl other = (ProductImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (skus == null) {
      if (other.skus != null) {
        return false;
      }
    } else if (!skus.equals(other.skus)) {
      return false;
    }

    return true;
  } // end method equals

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getUrlKey()
   */
  @Override public String getUrlKey() {
    if (urlKey != null) {
      return urlKey;
    } else {
      if (getName() != null) {
        String returnKey = getName().toLowerCase();

        returnKey = returnKey.replaceAll(" ", "-");

        return returnKey.replaceAll("[^A-Za-z0-9/-]", "");
      }
    }

    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setUrlKey(java.lang.String)
   */
  @Override public void setUrlKey(String urlKey) {
    this.urlKey = urlKey;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getGeneratedUrl()
   */
  @Override public String getGeneratedUrl() {
    if ((getDefaultCategory() != null) && (getDefaultCategory().getGeneratedUrl() != null)) {
      String generatedUrl = getDefaultCategory().getGeneratedUrl();

      if (generatedUrl.endsWith("//")) {
        return generatedUrl + getUrlKey();
      } else {
        return generatedUrl + "//" + getUrlKey();
      }
    }

    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#clearDynamicPrices()
   */
  @Override public void clearDynamicPrices() {
    for (Sku sku : getAllSkus()) {
      sku.clearDynamicPrices();
    }
  }

  /**
   * @see  org.broadleafcommerce.common.admin.domain.AdminMainEntity#getMainEntityName()
   */
  @Override public String getMainEntityName() {
    String manufacturer = getManufacturer();

    return StringUtils.isBlank(manufacturer) ? getName() : (manufacturer + " " + getName());
  }

  public static class Presentation {
    public static class Tab {
      public static class Name {
        public static final String Marketing      = "ProductImpl_Marketing_Tab";
        public static final String Media          = "SkuImpl_Media_Tab";
        public static final String ProductOptions = "ProductImpl_Product_Options_Tab";
        public static final String Inventory      = "ProductImpl_Inventory_Tab";
        public static final String Shipping       = "ProductImpl_Shipping_Tab";
        public static final String Advanced       = "ProductImpl_Advanced_Tab";

      }

      public static class Order {
        public static final int Marketing      = 2000;
        public static final int Media          = 3000;
        public static final int ProductOptions = 4000;
        public static final int Inventory      = 5000;
        public static final int Shipping       = 6000;
        public static final int Advanced       = 7000;
      }
    }

    public static class Group {
      public static class Name {
        public static final String General         = "ProductImpl_Product_Description";
        public static final String Price           = "SkuImpl_Price";
        public static final String ActiveDateRange = "ProductImpl_Product_Active_Date_Range";
        public static final String Advanced        = "ProductImpl_Advanced";
        public static final String Inventory       = "SkuImpl_Sku_Inventory";
        public static final String Badges          = "ProductImpl_Badges";
        public static final String Shipping        = "ProductWeight_Shipping";
        public static final String Financial       = "ProductImpl_Financial";
      }

      public static class Order {
        public static final int General         = 1000;
        public static final int Price           = 2000;
        public static final int ActiveDateRange = 3000;
        public static final int Advanced        = 1000;
        public static final int Inventory       = 1000;
        public static final int Badges          = 1000;
        public static final int Shipping        = 1000;
      }

    }

    public static class FieldOrder {
      public static final int NAME              = 1000;
      public static final int SHORT_DESCRIPTION = 2000;
      public static final int PRIMARY_MEDIA     = 3000;
      public static final int LONG_DESCRIPTION  = 4000;
      public static final int DEFAULT_CATEGORY  = 5000;
      public static final int MANUFACTURER      = 6000;
      public static final int URL               = 7000;
    }
  } // end class Presentation

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#getTaxCode()
   */
  @Override public String getTaxCode() {
    if (StringUtils.isEmpty(taxCode) && (getDefaultCategory() != null)) {
      return getDefaultCategory().getTaxCode();
    }

    return taxCode;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Product#setTaxCode(java.lang.String)
   */
  @Override public void setTaxCode(String taxCode) {
    this.taxCode = taxCode;
  }

} // end class ProductImpl
