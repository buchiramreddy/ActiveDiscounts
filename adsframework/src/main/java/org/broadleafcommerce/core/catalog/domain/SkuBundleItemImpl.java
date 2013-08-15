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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.RequiredOverride;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.broadleafcommerce.core.catalog.service.dynamic.DefaultDynamicSkuPricingInvocationHandler;
import org.broadleafcommerce.core.catalog.service.dynamic.DynamicSkuPrices;
import org.broadleafcommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import org.springframework.util.ClassUtils;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(populateToOneFields = PopulateToOneFieldsEnum.FALSE)
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SKU_BUNDLE_ITEM")
public class SkuBundleItemImpl implements SkuBundleItem {
  private static final long serialVersionUID = 1L;

  /** The id. */
  @AdminPresentation(
    friendlyName = "SkuBundleItemImpl_ID",
    visibility   = VisibilityEnum.HIDDEN_ALL
  )
  @Column(name = "SKU_BUNDLE_ITEM_ID")
  @GeneratedValue(generator = "SkuBundleItemId")
  @GenericGenerator(
    name       = "SkuBundleItemId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "SkuBundleItemImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.catalog.domain.SkuBundleItemImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName     = "bundleItemQuantity",
    prominent        = true,
    requiredOverride = RequiredOverride.REQUIRED
  )
  @Column(
    name     = "QUANTITY",
    nullable = false
  )
  protected Integer quantity;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "bundleItemSalePrice",
    prominent    = true,
    tooltip      = "bundleItemSalePriceTooltip",
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "ITEM_SALE_PRICE",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal itemSalePrice;

  /** DOCUMENT ME! */
  @JoinColumn(
    name                 = "PRODUCT_BUNDLE_ID",
    referencedColumnName = "PRODUCT_ID"
  )
  @ManyToOne(
    targetEntity = ProductBundleImpl.class,
    optional     = false
  )
  protected ProductBundle bundle;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "Sku",
    prominent    = true,
    order        = 0,
    gridOrder    = 0
  )
  @AdminPresentationToOneLookup
  @JoinColumn(
    name                 = "SKU_ID",
    referencedColumnName = "SKU_ID"
  )
  @ManyToOne(
    targetEntity = SkuImpl.class,
    optional     = false
  )
  protected Sku                               sku;

  /** DOCUMENT ME! */
  @Transient protected DynamicSkuPrices dynamicPrices = null;

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuBundleItem#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuBundleItem#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuBundleItem#getQuantity()
   */
  @Override public Integer getQuantity() {
    return quantity;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuBundleItem#setQuantity(java.lang.Integer)
   */
  @Override public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   sku        DOCUMENT ME!
   * @param   salePrice  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getDynamicSalePrice(Sku sku, BigDecimal salePrice) {
    Money returnPrice = null;

    if (SkuPricingConsiderationContext.hasDynamicPricing()) {
      if (dynamicPrices != null) {
        returnPrice = dynamicPrices.getSalePrice();
      } else {
        DefaultDynamicSkuPricingInvocationHandler handler = new DefaultDynamicSkuPricingInvocationHandler(sku,
            salePrice);
        Sku                                       proxy   = (Sku) Proxy.newProxyInstance(sku.getClass()
            .getClassLoader(), ClassUtils.getAllInterfacesForClass(sku.getClass()), handler);
        dynamicPrices = SkuPricingConsiderationContext.getSkuPricingService().getSkuPrices(proxy,
            SkuPricingConsiderationContext.getSkuPricingConsiderationContext());
        returnPrice   = dynamicPrices.getSalePrice();
      }
    } else {
      if (salePrice != null) {
        returnPrice = new Money(salePrice, Money.defaultCurrency());
      }
    }

    return returnPrice;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuBundleItem#setSalePrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setSalePrice(Money salePrice) {
    if (salePrice != null) {
      this.itemSalePrice = salePrice.getAmount();
    } else {
      this.itemSalePrice = null;
    }
  }


  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuBundleItem#getSalePrice()
   */
  @Override public Money getSalePrice() {
    if (itemSalePrice == null) {
      return sku.getSalePrice();
    } else {
      return getDynamicSalePrice(sku, itemSalePrice);
    }
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuBundleItem#getRetailPrice()
   */
  @Override public Money getRetailPrice() {
    return sku.getRetailPrice();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuBundleItem#getBundle()
   */
  @Override public ProductBundle getBundle() {
    return bundle;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuBundleItem#setBundle(org.broadleafcommerce.core.catalog.domain.ProductBundle)
   */
  @Override public void setBundle(ProductBundle bundle) {
    this.bundle = bundle;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuBundleItem#getSku()
   */
  @Override public Sku getSku() {
    return sku;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuBundleItem#setSku(org.broadleafcommerce.core.catalog.domain.Sku)
   */
  @Override public void setSku(Sku sku) {
    this.sku = sku;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuBundleItem#clearDynamicPrices()
   */
  @Override public void clearDynamicPrices() {
    this.dynamicPrices = null;
  }
} // end class SkuBundleItemImpl
