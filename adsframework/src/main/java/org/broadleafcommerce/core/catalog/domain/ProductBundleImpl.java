/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
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
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.money.BankersRounding;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.RequiredOverride;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.broadleafcommerce.core.catalog.service.type.ProductBundlePricingModelType;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(
  populateToOneFields = PopulateToOneFieldsEnum.TRUE,
  friendlyName        = "ProductImpl_bundleProduct"
)
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PRODUCT_BUNDLE")
public class ProductBundleImpl extends ProductImpl implements ProductBundle {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "productBundlePricingModel",
    group                = Presentation.Group.Name.Price,
    order                = 1,
    helpText             = "productBundlePricingModelHelp",
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.catalog.service.type.ProductBundlePricingModelType",
    requiredOverride     = RequiredOverride.REQUIRED
  )
  @Column(name = "PRICING_MODEL")
  protected String pricingModel;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(name = "AUTO_BUNDLE")
  protected Boolean autoBundle = false;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(name = "ITEMS_PROMOTABLE")
  protected Boolean itemsPromotable = false;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(name = "BUNDLE_PROMOTABLE")
  protected Boolean bundlePromotable = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    excluded     = true,
    friendlyName = "productBundlePriority",
    group        = "productBundleGroup"
  )
  @Column(name = "BUNDLE_PRIORITY")
  protected int priority = 99;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(friendlyName = "skuBundleItemsTitle")
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @Cascade(value = { org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
  @OneToMany(
    mappedBy     = "bundle",
    targetEntity = SkuBundleItemImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected List<SkuBundleItem> skuBundleItems = new ArrayList<SkuBundleItem>();

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#isOnSale()
   */
  @Override public boolean isOnSale() {
    Money retailPrice = getRetailPrice();
    Money salePrice   = getSalePrice();

    return ((salePrice != null) && !salePrice.isZero() && salePrice.lessThan(retailPrice));
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#getPricingModel()
   */
  @Override public ProductBundlePricingModelType getPricingModel() {
    return ProductBundlePricingModelType.getInstance(pricingModel);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#setPricingModel(org.broadleafcommerce.core.catalog.service.type.ProductBundlePricingModelType)
   */
  @Override public void setPricingModel(ProductBundlePricingModelType pricingModel) {
    this.pricingModel = (pricingModel == null) ? null : pricingModel.getType();
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getRetailPrice() {
    if (ProductBundlePricingModelType.ITEM_SUM.equals(getPricingModel())) {
      return getBundleItemsRetailPrice();
    } else if (ProductBundlePricingModelType.BUNDLE.equals(getPricingModel())) {
      return super.getDefaultSku().getRetailPrice();
    }

    return null;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getSalePrice() {
    if (ProductBundlePricingModelType.ITEM_SUM.equals(getPricingModel())) {
      return getBundleItemsSalePrice();
    } else if (ProductBundlePricingModelType.BUNDLE.equals(getPricingModel())) {
      return super.getDefaultSku().getSalePrice();
    }

    return null;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#getBundleItemsRetailPrice()
   */
  @Override public Money getBundleItemsRetailPrice() {
    Money price = new Money(BigDecimal.ZERO);

    for (SkuBundleItem item : getSkuBundleItems()) {
      price = price.add(item.getRetailPrice().multiply(item.getQuantity()));
    }

    return price;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#getBundleItemsSalePrice()
   */
  @Override public Money getBundleItemsSalePrice() {
    Money price = new Money(BigDecimal.ZERO);

    for (SkuBundleItem item : getSkuBundleItems()) {
      if (item.getSalePrice() != null) {
        price = price.add(item.getSalePrice().multiply(item.getQuantity()));
      } else {
        price = price.add(item.getRetailPrice().multiply(item.getQuantity()));
      }
    }

    return price;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#getAutoBundle()
   */
  @Override public Boolean getAutoBundle() {
    return (autoBundle == null) ? false : autoBundle;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#setAutoBundle(java.lang.Boolean)
   */
  @Override public void setAutoBundle(Boolean autoBundle) {
    this.autoBundle = autoBundle;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#getItemsPromotable()
   */
  @Override public Boolean getItemsPromotable() {
    return (itemsPromotable == null) ? false : itemsPromotable;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#setItemsPromotable(java.lang.Boolean)
   */
  @Override public void setItemsPromotable(Boolean itemsPromotable) {
    this.itemsPromotable = itemsPromotable;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#getBundlePromotable()
   */
  @Override public Boolean getBundlePromotable() {
    return (bundlePromotable == null) ? false : bundlePromotable;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#setBundlePromotable(java.lang.Boolean)
   */
  @Override public void setBundlePromotable(Boolean bundlePromotable) {
    this.bundlePromotable = bundlePromotable;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#getSkuBundleItems()
   */
  @Override public List<SkuBundleItem> getSkuBundleItems() {
    return skuBundleItems;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#setSkuBundleItems(java.util.List)
   */
  @Override public void setSkuBundleItems(List<SkuBundleItem> skuBundleItems) {
    this.skuBundleItems = skuBundleItems;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#getPriority()
   */
  @Override public Integer getPriority() {
    return priority;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#setPriority(java.lang.Integer)
   */
  @Override public void setPriority(Integer priority) {
    this.priority = priority;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductBundle#getPotentialSavings()
   */
  @Override public BigDecimal getPotentialSavings() {
    if (skuBundleItems != null) {
      Money totalNormalPrice = new Money(BankersRounding.zeroAmount());
      Money totalBundlePrice = new Money(BankersRounding.zeroAmount());

      for (SkuBundleItem skuBundleItem : skuBundleItems) {
        Sku sku = skuBundleItem.getSku();

        if ((sku != null) && (sku.getRetailPrice() != null)) {
          totalNormalPrice = totalNormalPrice.add(sku.getRetailPrice().multiply(skuBundleItem.getQuantity()));
        }

        if (ProductBundlePricingModelType.ITEM_SUM.equals(getPricingModel())) {
          if (skuBundleItem.getSalePrice() != null) {
            totalBundlePrice = totalBundlePrice.add(skuBundleItem.getSalePrice().multiply(skuBundleItem.getQuantity()));
          } else {
            totalBundlePrice = totalBundlePrice.add(skuBundleItem.getRetailPrice().multiply(
                  skuBundleItem.getQuantity()));
          }
        }

      }

      if (ProductBundlePricingModelType.BUNDLE.equals(getPricingModel())) {
        if (getSalePrice() != null) {
          totalBundlePrice = getSalePrice();
        } else {
          totalBundlePrice = getRetailPrice();
        }
      }

      return totalNormalPrice.subtract(totalBundlePrice).getAmount();

    } // end if

    return BigDecimal.ZERO;

  } // end method getPotentialSavings

} // end class ProductBundleImpl
