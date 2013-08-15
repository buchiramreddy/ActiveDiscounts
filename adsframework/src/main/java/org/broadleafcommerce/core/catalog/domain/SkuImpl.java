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

import java.lang.reflect.Proxy;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
import javax.persistence.MapKeyClass;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrencyImpl;
import org.broadleafcommerce.common.i18n.service.DynamicTranslationProvider;
import org.broadleafcommerce.common.media.domain.Media;
import org.broadleafcommerce.common.media.domain.MediaImpl;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationDataDrivenEnumeration;
import org.broadleafcommerce.common.presentation.AdminPresentationMap;
import org.broadleafcommerce.common.presentation.AdminPresentationMapField;
import org.broadleafcommerce.common.presentation.AdminPresentationMapFields;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.OptionFilterParam;
import org.broadleafcommerce.common.presentation.OptionFilterParamType;
import org.broadleafcommerce.common.presentation.client.LookupType;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;
import org.broadleafcommerce.common.util.DateUtil;

import org.broadleafcommerce.core.catalog.domain.ProductImpl.Presentation;
import org.broadleafcommerce.core.catalog.service.dynamic.DefaultDynamicSkuPricingInvocationHandler;
import org.broadleafcommerce.core.catalog.service.dynamic.DynamicSkuPrices;
import org.broadleafcommerce.core.catalog.service.dynamic.SkuActiveDateConsiderationContext;
import org.broadleafcommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext;
import org.broadleafcommerce.core.inventory.service.type.InventoryType;
import org.broadleafcommerce.core.order.domain.FulfillmentOption;
import org.broadleafcommerce.core.order.domain.FulfillmentOptionImpl;
import org.broadleafcommerce.core.order.service.type.FulfillmentType;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import org.springframework.util.ClassUtils;


/**
 * The Class SkuImpl is the default implementation of {@link org.broadleafcommerce.core.catalog.domain.Sku}. A SKU is a
 * specific item that can be sold including any specific attributes of the item such as color or size.<br>
 * <br>
 * If you want to add fields specific to your implementation of BroadLeafCommerce you should extend this class and add
 * your fields. If you need to make significant changes to the SkuImpl then you should implement your own version of
 * {@link org.broadleafcommerce.core.catalog.domain.Sku}.<br>
 * <br>
 * This implementation uses a Hibernate implementation of JPA configured through annotations. The Entity references the
 * following tables: BLC_SKU, BLC_SKU_IMAGE
 *
 * <p>!!!!!!!!!!!!!!!!!</p>
 *
 * <p>For admin required field validation, if this Sku is apart of an additionalSkus list (meaning it is not a
 * defaultSku) then it should have no required restrictions on it. All additional Skus can delegate to the defaultSku of
 * the related product for all of its fields. For this reason, if you would like to mark more fields as required then
 * rather than using {@link org.broadleafcommerce.common.presentation.AdminPresentation#requiredOverride()}, use the
 * mo:overrides section in bl-admin-applicationContext.xml for Product and reference each required field like
 * 'defaultSku.name' or 'defaultSku.retailPrice'.</p>
 *
 * @author   btaylor
 * @see      {@link org.broadleafcommerce.core.catalog.domain.Sku}
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "baseSku")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SKU")
public class SkuImpl implements Sku {
  private static final Log LOG = LogFactory.getLog(SkuImpl.class);

  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SkuImpl_Sku_ID",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "SKU_ID")
  @GeneratedValue(generator = "SkuId")
  @GenericGenerator(
    name       = "SkuId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "SkuImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.catalog.domain.SkuImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SkuImpl_Sku_Sale_Price",
    order        = 2000,
    group        = Presentation.Group.Name.Price,
    groupOrder   = Presentation.Group.Order.Price,
    prominent    = true,
    gridOrder    = 6,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "SALE_PRICE",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal salePrice;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SkuImpl_Sku_Retail_Price",
    order        = 1000,
    group        = Presentation.Group.Name.Price,
    groupOrder   = Presentation.Group.Order.Price,
    prominent    = true,
    gridOrder    = 5,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "RETAIL_PRICE",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal retailPrice;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SkuImpl_Sku_Name",
    order        = Presentation.FieldOrder.NAME,
    group        = Presentation.Group.Name.General,
    groupOrder   = Presentation.Group.Order.General,
    prominent    = true,
    gridOrder    = 1,
    columnWidth  = "260px",
    translatable = true
  )
  @Column(name = "NAME")
  @Index(
    name        = "SKU_NAME_INDEX",
    columnNames = { "NAME" }
  )
  protected String name;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SkuImpl_Sku_Description",
    order        = Presentation.FieldOrder.SHORT_DESCRIPTION,
    group        = Presentation.Group.Name.General,
    groupOrder   = Presentation.Group.Order.General,
    largeEntry   = true,
    excluded     = true,
    translatable = true
  )
  @Column(name = "DESCRIPTION")
  protected String description;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SkuImpl_Sku_Large_Description",
    order        = Presentation.FieldOrder.LONG_DESCRIPTION,
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
    friendlyName = "SkuImpl_Sku_TaxCode",
    order        = 1001,
    group        = Presentation.Group.Name.Financial
  )
  @AdminPresentationDataDrivenEnumeration(
    optionFilterParams = {
      @OptionFilterParam(
        param = "type.key",
        value = "TAX_CODE",
        paramType = OptionFilterParamType.STRING
      )
    }
  )
  @Column(name = "TAX_CODE")
  protected String taxCode;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SkuImpl_Sku_Taxable",
    order        = 1000,
    group        = Presentation.Group.Name.Financial
  )
  @Column(name = "TAXABLE_FLAG")
  @Index(
    name        = "SKU_TAXABLE_INDEX",
    columnNames = { "TAXABLE_FLAG" }
  )
  protected Character taxable;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SkuImpl_Sku_Discountable",
    order        = 2000,
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced
  )
  @Column(name = "DISCOUNTABLE_FLAG")
  @Index(
    name        = "SKU_DISCOUNTABLE_INDEX",
    columnNames = { "DISCOUNTABLE_FLAG" }
  )
  protected Character discountable;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SkuImpl_Sku_Available",
    order        = 2000,
    tab          = Presentation.Tab.Name.Inventory,
    tabOrder     = Presentation.Tab.Order.Inventory,
    group        = Presentation.Group.Name.Inventory,
    groupOrder   = Presentation.Group.Order.Inventory
  )
  @Column(name = "AVAILABLE_FLAG")
  @Index(
    name        = "SKU_AVAILABLE_INDEX",
    columnNames = { "AVAILABLE_FLAG" }
  )
  protected Character available;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SkuImpl_Sku_Start_Date",
    order        = 1000,
    group        = Presentation.Group.Name.ActiveDateRange,
    groupOrder   = Presentation.Group.Order.ActiveDateRange,
    tooltip      = "skuStartDateTooltip"
  )
  @Column(name = "ACTIVE_START_DATE")
  protected Date activeStartDate;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "SkuImpl_Sku_End_Date",
    order        = 2000,
    group        = Presentation.Group.Name.ActiveDateRange,
    groupOrder   = Presentation.Group.Order.ActiveDateRange,
    tooltip      = "skuEndDateTooltip"
  )
  @Column(name = "ACTIVE_END_DATE")
  @Index(
    name        = "SKU_ACTIVE_INDEX",
    columnNames = { "ACTIVE_START_DATE", "ACTIVE_END_DATE" }
  )
  protected Date activeEndDate;

  /** DOCUMENT ME! */
  @Embedded protected Dimension dimension = new Dimension();

  /** DOCUMENT ME! */
  @Embedded protected Weight weight = new Weight();

  /** DOCUMENT ME! */
  @Transient protected DynamicSkuPrices dynamicPrices = null;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ProductImpl_Is_Product_Machine_Sortable",
    order        = 10000,
    tab          = Presentation.Tab.Name.Shipping,
    tabOrder     = Presentation.Tab.Order.Shipping,
    group        = Presentation.Group.Name.Shipping,
    groupOrder   = Presentation.Group.Order.Shipping
  )
  @Column(name = "IS_MACHINE_SORTABLE")
  protected Boolean isMachineSortable = true;

  /** DOCUMENT ME! */
  @AdminPresentationMap(
    friendlyName            = "SkuImpl_Sku_Media",
    tab                     = Presentation.Tab.Name.Media,
    tabOrder                = Presentation.Tab.Order.Media,
    keyPropertyFriendlyName = "SkuImpl_Sku_Media_Key",
    deleteEntityUponRemove  = true,
    mediaField              = "url",
    forceFreeFormKeys       = true
  )
  @AdminPresentationMapFields(
    mapDisplayFields = {
      @AdminPresentationMapField(
        fieldName = "primary",
        fieldPresentation =
          @AdminPresentation(
            fieldType    = SupportedFieldType.MEDIA,
            group        = Presentation.Group.Name.General,
            groupOrder   = Presentation.Group.Order.General,
            order        = Presentation.FieldOrder.PRIMARY_MEDIA,
            friendlyName = "SkuImpl_Primary_Media"
          )
      )
    }
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @JoinTable(
    name               = "BLC_SKU_MEDIA_MAP",
    inverseJoinColumns =
      @JoinColumn(
        name           = "MEDIA_ID",
        referencedColumnName = "MEDIA_ID"
      )
  )
  @ManyToMany(targetEntity = MediaImpl.class)
  @MapKeyColumn(name = "MAP_KEY")
  protected Map<String, Media> skuMedia = new HashMap<String, Media>();

  /** This will be non-null if and only if this Sku is the default Sku for a Product. */
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL })
  @JoinColumn(name = "DEFAULT_PRODUCT_ID")
  @OneToOne(
    optional     = true,
    targetEntity = ProductImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected Product defaultProduct;

  /**
   * This relationship will be non-null if and only if this Sku is contained in the list of additional Skus for a
   * Product (for Skus based on ProductOptions).
   */
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @JoinTable(
    name               = "BLC_PRODUCT_SKU_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "SKU_ID",
        referencedColumnName = "SKU_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "PRODUCT_ID",
        referencedColumnName = "PRODUCT_ID"
      )
  )
  @ManyToOne(
    optional     = true,
    targetEntity = ProductImpl.class
  )
  protected Product product;

  /** DOCUMENT ME! */
  @AdminPresentationMap(
    friendlyName           = "skuAttributesTitle",
    tab                    = Presentation.Tab.Name.Advanced,
    tabOrder               = Presentation.Tab.Order.Advanced,
    deleteEntityUponRemove = true,
    forceFreeFormKeys      = true
  )
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blStandardElements"
  )
  @MapKey(name = "name")
  @OneToMany(
    mappedBy      = "sku",
    targetEntity  = SkuAttributeImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected Map<String, SkuAttribute> skuAttributes = new HashMap<String, SkuAttribute>();

  /** DOCUMENT ME! */
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @JoinTable(
    name               = "BLC_SKU_OPTION_VALUE_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "SKU_ID",
        referencedColumnName = "SKU_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "PRODUCT_OPTION_VALUE_ID",
        referencedColumnName = "PRODUCT_OPTION_VALUE_ID"
      )
  )
  @ManyToMany(targetEntity = ProductOptionValueImpl.class)
  protected List<ProductOptionValue> productOptionValues = new ArrayList<ProductOptionValue>();

  /** DOCUMENT ME! */
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @JoinTable(
    name               = "BLC_SKU_FEE_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "SKU_ID",
        referencedColumnName = "SKU_ID",
        nullable       = true
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "SKU_FEE_ID",
        referencedColumnName = "SKU_FEE_ID",
        nullable             = true
      )
  )
  @ManyToMany(
    fetch        = FetchType.LAZY,
    targetEntity = SkuFeeImpl.class
  )
  protected List<SkuFee> fees = new ArrayList<SkuFee>();

  /** DOCUMENT ME! */
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(org.hibernate.annotations.CascadeType.ALL)
  @CollectionTable(
    name        = "BLC_SKU_FULFILLMENT_FLAT_RATES",
    joinColumns =
      @JoinColumn(
        name    = "SKU_ID",
        referencedColumnName = "SKU_ID",
        nullable = true
      )
  )
  @Column(
    name      = "RATE",
    precision = 19,
    scale     = 5
  )
  @ElementCollection
  @MapKeyClass(FulfillmentOptionImpl.class)
  @MapKeyJoinColumn(
    name                 = "FULFILLMENT_OPTION_ID",
    referencedColumnName = "FULFILLMENT_OPTION_ID"
  )
  protected Map<FulfillmentOption, BigDecimal> fulfillmentFlatRates = new HashMap<FulfillmentOption, BigDecimal>();

  /** DOCUMENT ME! */
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @JoinTable(
    name               = "BLC_SKU_FULFILLMENT_EXCLUDED",
    joinColumns        =
      @JoinColumn(
        name           = "SKU_ID",
        referencedColumnName = "SKU_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "FULFILLMENT_OPTION_ID",
        referencedColumnName = "FULFILLMENT_OPTION_ID"
      )
  )
  @ManyToMany(targetEntity = FulfillmentOptionImpl.class)
  protected List<FulfillmentOption> excludedFulfillmentOptions = new ArrayList<FulfillmentOption>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "SkuImpl_Sku_InventoryType",
    order                = 1000,
    tab                  = Presentation.Tab.Name.Inventory,
    tabOrder             = Presentation.Tab.Order.Inventory,
    group                = Presentation.Group.Name.Inventory,
    groupOrder           = Presentation.Group.Order.Inventory,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.inventory.service.type.InventoryType"
  )
  @Column(name = "INVENTORY_TYPE")
  protected String inventoryType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "SkuImpl_Sku_FulfillmentType",
    order                = 2000,
    tab                  = Presentation.Tab.Name.Inventory,
    tabOrder             = Presentation.Tab.Order.Inventory,
    group                = Presentation.Group.Name.Inventory,
    groupOrder           = Presentation.Group.Order.Inventory,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.order.service.type.FulfillmentType"
  )
  @Column(name = "FULFILLMENT_TYPE")
  protected String fulfillmentType;

  /**
   * Note that this field is not the target of the currencyCodeField attribute on either retailPrice or salePrice. This
   * is because SKUs are special in that we want to return the currency on this SKU if there is one, falling back to the
   * defaultSku's currency if possible.
   */
  @AdminPresentation(
    friendlyName = "SkuImpl_Currency",
    order        = 3000,
    tab          = Presentation.Tab.Name.Advanced,
    tabOrder     = Presentation.Tab.Order.Advanced,
    group        = Presentation.Group.Name.Advanced,
    groupOrder   = Presentation.Group.Order.Advanced
  )
  @AdminPresentationToOneLookup(
    lookupType            = LookupType.DROPDOWN,
    lookupDisplayProperty = "friendlyName"
  )
  @JoinColumn(name = "CURRENCY_CODE")
  @ManyToOne(targetEntity = BroadleafCurrencyImpl.class)
  protected BroadleafCurrency currency;

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#isOnSale()
   */
  @Override public boolean isOnSale() {
    Money retailPrice = getRetailPrice();
    Money salePrice   = getSalePrice();

    return ((salePrice != null) && !salePrice.isZero() && salePrice.lessThan(retailPrice));
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected boolean hasDefaultSku() {
    return ((product != null) && (product.getDefaultSku() != null) && !getId().equals(product.getDefaultSku().getId()));
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Sku lookupDefaultSku() {
    if ((product != null) && (product.getDefaultSku() != null)) {
      return product.getDefaultSku();
    } else {
      return null;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getProductOptionValueAdjustments()
   */
  @Override public Money getProductOptionValueAdjustments() {
    Money optionValuePriceAdjustments = null;

    if (getProductOptionValues() != null) {
      for (ProductOptionValue value : getProductOptionValues()) {
        if (value.getPriceAdjustment() != null) {
          if (optionValuePriceAdjustments == null) {
            optionValuePriceAdjustments = value.getPriceAdjustment();
          } else {
            optionValuePriceAdjustments = optionValuePriceAdjustments.add(value.getPriceAdjustment());
          }
        }
      }
    }

    return optionValuePriceAdjustments;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getSalePrice()
   */
  @Override public Money getSalePrice() {
    Money returnPrice            = null;
    Money optionValueAdjustments = null;

    if (SkuPricingConsiderationContext.hasDynamicPricing()) {
      // We have dynamic pricing, so we will pull the sale price from there
      if (dynamicPrices == null) {
        DefaultDynamicSkuPricingInvocationHandler handler = new DefaultDynamicSkuPricingInvocationHandler(this);
        Sku                                       proxy   = (Sku) Proxy.newProxyInstance(getClass().getClassLoader(),
            ClassUtils.getAllInterfacesForClass(getClass()), handler);

        dynamicPrices = SkuPricingConsiderationContext.getSkuPricingService().getSkuPrices(proxy,
            SkuPricingConsiderationContext.getSkuPricingConsiderationContext());
      }

      returnPrice            = dynamicPrices.getSalePrice();
      optionValueAdjustments = dynamicPrices.getPriceAdjustment();
    } else if (salePrice != null) {
      // We have an explicitly set sale price directly on this entity. We will not apply any adjustments
      returnPrice = new Money(salePrice, getCurrency());
    }

    if ((returnPrice == null) && hasDefaultSku()) {
      returnPrice            = lookupDefaultSku().getSalePrice();
      optionValueAdjustments = getProductOptionValueAdjustments();
    }

    if (returnPrice == null) {
      return null;
    }

    if (optionValueAdjustments != null) {
      returnPrice = returnPrice.add(optionValueAdjustments);
    }

    return returnPrice;
  } // end method getSalePrice


  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setSalePrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setSalePrice(Money salePrice) {
    this.salePrice = Money.toAmount(salePrice);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getRetailPrice()
   */
  @Override public Money getRetailPrice() {
    Money returnPrice            = null;
    Money optionValueAdjustments = null;

    if (SkuPricingConsiderationContext.hasDynamicPricing()) {
      // We have dynamic pricing, so we will pull the retail price from there
      if (dynamicPrices == null) {
        DefaultDynamicSkuPricingInvocationHandler handler = new DefaultDynamicSkuPricingInvocationHandler(this);
        Sku                                       proxy   = (Sku) Proxy.newProxyInstance(getClass().getClassLoader(),
            ClassUtils.getAllInterfacesForClass(getClass()), handler);

        dynamicPrices = SkuPricingConsiderationContext.getSkuPricingService().getSkuPrices(proxy,
            SkuPricingConsiderationContext.getSkuPricingConsiderationContext());
      }

      returnPrice            = dynamicPrices.getRetailPrice();
      optionValueAdjustments = dynamicPrices.getPriceAdjustment();
    } else if (retailPrice != null) {
      returnPrice = new Money(retailPrice, getCurrency());
    }

    if ((returnPrice == null) && hasDefaultSku()) {
      // Otherwise, we'll pull the retail price from the default sku
      returnPrice            = lookupDefaultSku().getRetailPrice();
      optionValueAdjustments = getProductOptionValueAdjustments();
    }

    if (returnPrice == null) {
      throw new IllegalStateException("Retail price on Sku with id " + getId() + " was null");
    }

    if (optionValueAdjustments != null) {
      returnPrice = returnPrice.add(optionValueAdjustments);
    }

    return returnPrice;
  } // end method getRetailPrice

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setRetailPrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setRetailPrice(Money retailPrice) {
    this.retailPrice = Money.toAmount(retailPrice);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getListPrice()
   */
  @Override public Money getListPrice() {
    return getRetailPrice();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setListPrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setListPrice(Money listPrice) {
    this.retailPrice = Money.toAmount(listPrice);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getName()
   */
  @Override public String getName() {
    if ((name == null) && hasDefaultSku()) {
      return lookupDefaultSku().getName();
    }

    return DynamicTranslationProvider.getValue(this, "name", name);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getDescription()
   */
  @Override public String getDescription() {
    if ((description == null) && hasDefaultSku()) {
      return lookupDefaultSku().getDescription();
    }

    return DynamicTranslationProvider.getValue(this, "description", description);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setDescription(java.lang.String)
   */
  @Override public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getLongDescription()
   */
  @Override public String getLongDescription() {
    if ((longDescription == null) && hasDefaultSku()) {
      return lookupDefaultSku().getLongDescription();
    }

    return DynamicTranslationProvider.getValue(this, "longDescription", longDescription);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setLongDescription(java.lang.String)
   */
  @Override public void setLongDescription(String longDescription) {
    this.longDescription = longDescription;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#isTaxable()
   */
  @Override public Boolean isTaxable() {
    if (taxable == null) {
      if (hasDefaultSku()) {
        return lookupDefaultSku().isTaxable();
      }

      return null;
    }

    return (taxable == 'Y') ? Boolean.TRUE : Boolean.FALSE;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getTaxable()
   */
  @Override public Boolean getTaxable() {
    return isTaxable();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setTaxable(java.lang.Boolean)
   */
  @Override public void setTaxable(Boolean taxable) {
    if (taxable == null) {
      this.taxable = null;
    } else {
      this.taxable = taxable ? 'Y' : 'N';
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#isDiscountable()
   */
  @Override public Boolean isDiscountable() {
    if (discountable == null) {
      if (hasDefaultSku()) {
        return lookupDefaultSku().isDiscountable();
      }

      return Boolean.FALSE;
    }

    return (discountable == 'Y') ? Boolean.TRUE : Boolean.FALSE;
  }

  /*
   * This is to facilitate serialization to non-Java clients
   */
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getDiscountable() {
    return isDiscountable();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setDiscountable(java.lang.Boolean)
   */
  @Override public void setDiscountable(Boolean discountable) {
    if (discountable == null) {
      this.discountable = null;
    } else {
      this.discountable = discountable ? 'Y' : 'N';
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#isAvailable()
   */
  @Override public Boolean isAvailable() {
    if (available == null) {
      if (hasDefaultSku()) {
        return lookupDefaultSku().isAvailable();
      }

      return null;
    }

    return (available == 'Y') ? Boolean.TRUE : Boolean.FALSE;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getAvailable()
   */
  @Override public Boolean getAvailable() {
    return isAvailable();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setAvailable(java.lang.Boolean)
   */
  @Override public void setAvailable(Boolean available) {
    if (available == null) {
      this.available = null;
    } else {
      this.available = available ? 'Y' : 'N';
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getActiveStartDate()
   */
  @Override public Date getActiveStartDate() {
    Date returnDate = null;

    if (SkuActiveDateConsiderationContext.hasDynamicActiveDates()) {
      returnDate = SkuActiveDateConsiderationContext.getSkuActiveDatesService().getDynamicSkuActiveStartDate(this);
    }

    if (returnDate == null) {
      if ((activeStartDate == null) && hasDefaultSku()) {
        return lookupDefaultSku().getActiveStartDate();
      } else {
        return activeStartDate;
      }
    } else {
      return returnDate;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setActiveStartDate(java.util.Date)
   */
  @Override public void setActiveStartDate(Date activeStartDate) {
    this.activeStartDate = activeStartDate;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getActiveEndDate()
   */
  @Override public Date getActiveEndDate() {
    Date returnDate = null;

    if (SkuActiveDateConsiderationContext.hasDynamicActiveDates()) {
      returnDate = SkuActiveDateConsiderationContext.getSkuActiveDatesService().getDynamicSkuActiveEndDate(this);
    }

    if (returnDate == null) {
      if ((activeEndDate == null) && hasDefaultSku()) {
        return lookupDefaultSku().getActiveEndDate();
      } else {
        return activeEndDate;
      }
    } else {
      return returnDate;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setActiveEndDate(java.util.Date)
   */
  @Override public void setActiveEndDate(Date activeEndDate) {
    this.activeEndDate = activeEndDate;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getDimension()
   */
  @Override public Dimension getDimension() {
    if ((dimension == null) && hasDefaultSku()) {
      return lookupDefaultSku().getDimension();
    } else {
      return dimension;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setDimension(org.broadleafcommerce.core.catalog.domain.Dimension)
   */
  @Override public void setDimension(Dimension dimension) {
    this.dimension = dimension;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getWeight()
   */
  @Override public Weight getWeight() {
    if ((weight == null) && hasDefaultSku()) {
      return lookupDefaultSku().getWeight();
    } else {
      return weight;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setWeight(org.broadleafcommerce.core.catalog.domain.Weight)
   */
  @Override public void setWeight(Weight weight) {
    this.weight = weight;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#isActive()
   */
  @Override public boolean isActive() {
    if ((activeStartDate == null) && (activeEndDate == null) && hasDefaultSku()) {
      return lookupDefaultSku().isActive();
    }

    if (LOG.isDebugEnabled()) {
      if (!DateUtil.isActive(getActiveStartDate(), getActiveEndDate(), true)) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("sku, " + id + ", inactive due to date");
        }
      }
    }

    return DateUtil.isActive(getActiveStartDate(), getActiveEndDate(), true);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#isActive(org.broadleafcommerce.core.catalog.domain.Product, org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public boolean isActive(Product product, Category category) {
    if (LOG.isDebugEnabled()) {
      if (!DateUtil.isActive(getActiveStartDate(), getActiveEndDate(), true)) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("sku, " + id + ", inactive due to date");
        }
      } else if (!product.isActive()) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("sku, " + id + ", inactive due to product being inactive");
        }
      } else if (!category.isActive()) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("sku, " + id + ", inactive due to category being inactive");
        }
      }
    }

    return this.isActive() && ((product == null) || product.isActive()) && ((category == null) || category.isActive());
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getSkuMedia()
   */
  @Override public Map<String, Media> getSkuMedia() {
    if ((skuMedia == null) || skuMedia.isEmpty()) {
      if (hasDefaultSku()) {
        return lookupDefaultSku().getSkuMedia();
      }
    }

    return skuMedia;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setSkuMedia(java.util.Map)
   */
  @Override public void setSkuMedia(Map<String, Media> skuMedia) {
    this.skuMedia = skuMedia;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getDefaultProduct()
   */
  @Override public Product getDefaultProduct() {
    return defaultProduct;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setDefaultProduct(org.broadleafcommerce.core.catalog.domain.Product)
   */
  @Override public void setDefaultProduct(Product defaultProduct) {
    this.defaultProduct = defaultProduct;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getProduct()
   */
  @Override public Product getProduct() {
    return (getDefaultProduct() != null) ? getDefaultProduct() : this.product;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setProduct(org.broadleafcommerce.core.catalog.domain.Product)
   */
  @Override public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getProductOptionValues()
   */
  @Override public List<ProductOptionValue> getProductOptionValues() {
    return productOptionValues;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setProductOptionValues(java.util.List)
   */
  @Override public void setProductOptionValues(List<ProductOptionValue> productOptionValues) {
    this.productOptionValues = productOptionValues;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#isMachineSortable()
   */
  @Deprecated @Override public Boolean isMachineSortable() {
    if ((isMachineSortable == null) && hasDefaultSku()) {
      return lookupDefaultSku().isMachineSortable();
    }

    return (isMachineSortable == null) ? false : isMachineSortable;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getIsMachineSortable()
   */
  @Override public Boolean getIsMachineSortable() {
    if ((isMachineSortable == null) && hasDefaultSku()) {
      return lookupDefaultSku().getIsMachineSortable();
    }

    return (isMachineSortable == null) ? false : isMachineSortable;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setMachineSortable(java.lang.Boolean)
   */
  @Deprecated @Override public void setMachineSortable(Boolean isMachineSortable) {
    this.isMachineSortable = isMachineSortable;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setIsMachineSortable(java.lang.Boolean)
   */
  @Override public void setIsMachineSortable(Boolean isMachineSortable) {
    this.isMachineSortable = isMachineSortable;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getFees()
   */
  @Override public List<SkuFee> getFees() {
    return fees;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setFees(java.util.List)
   */
  @Override public void setFees(List<SkuFee> fees) {
    this.fees = fees;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getFulfillmentFlatRates()
   */
  @Override public Map<FulfillmentOption, BigDecimal> getFulfillmentFlatRates() {
    return fulfillmentFlatRates;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setFulfillmentFlatRates(java.util.Map)
   */
  @Override public void setFulfillmentFlatRates(Map<FulfillmentOption, BigDecimal> fulfillmentFlatRates) {
    this.fulfillmentFlatRates = fulfillmentFlatRates;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getExcludedFulfillmentOptions()
   */
  @Override public List<FulfillmentOption> getExcludedFulfillmentOptions() {
    return excludedFulfillmentOptions;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setExcludedFulfillmentOptions(java.util.List)
   */
  @Override public void setExcludedFulfillmentOptions(List<FulfillmentOption> excludedFulfillmentOptions) {
    this.excludedFulfillmentOptions = excludedFulfillmentOptions;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getInventoryType()
   */
  @Override public InventoryType getInventoryType() {
    return InventoryType.getInstance(this.inventoryType);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setInventoryType(org.broadleafcommerce.core.inventory.service.type.InventoryType)
   */
  @Override public void setInventoryType(InventoryType inventoryType) {
    this.inventoryType = (inventoryType == null) ? null : inventoryType.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getFulfillmentType()
   */
  @Override public FulfillmentType getFulfillmentType() {
    return FulfillmentType.getInstance(this.fulfillmentType);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setFulfillmentType(org.broadleafcommerce.core.order.service.type.FulfillmentType)
   */
  @Override public void setFulfillmentType(FulfillmentType fulfillmentType) {
    this.fulfillmentType = fulfillmentType.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getSkuAttributes()
   */
  @Override public Map<String, SkuAttribute> getSkuAttributes() {
    return skuAttributes;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setSkuAttributes(java.util.Map)
   */
  @Override public void setSkuAttributes(Map<String, SkuAttribute> skuAttributes) {
    this.skuAttributes = skuAttributes;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getCurrency()
   */
  @Override public BroadleafCurrency getCurrency() {
    if ((currency == null) && hasDefaultSku()) {
      return lookupDefaultSku().getCurrency();
    } else {
      return currency;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setCurrency(org.broadleafcommerce.common.currency.domain.BroadleafCurrency)
   */
  @Override public void setCurrency(BroadleafCurrency currency) {
    this.currency = currency;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#clearDynamicPrices()
   */
  @Override public void clearDynamicPrices() {
    this.dynamicPrices = null;
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

    SkuImpl other = (SkuImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (getName() == null) {
      if (other.getName() != null) {
        return false;
      }
    } else if (!getName().equals(other.getName())) {
      return false;
    }

    return true;
  } // end method equals

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((getName() == null) ? 0 : getName().hashCode());

    return result;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#getTaxCode()
   */
  @Override public String getTaxCode() {
    if (StringUtils.isEmpty(taxCode) && hasDefaultSku()) {
      return lookupDefaultSku().getTaxCode();
    } else if (StringUtils.isEmpty(taxCode)) {
      return getProduct().getTaxCode();
    }

    return taxCode;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.Sku#setTaxCode(java.lang.String)
   */
  @Override public void setTaxCode(String taxCode) {
    this.taxCode = taxCode;
  }

} // end class SkuImpl
