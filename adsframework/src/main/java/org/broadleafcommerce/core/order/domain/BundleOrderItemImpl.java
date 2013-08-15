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

package org.broadleafcommerce.core.order.domain;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductBundle;
import org.broadleafcommerce.core.catalog.domain.ProductBundleImpl;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.domain.SkuImpl;
import org.broadleafcommerce.core.catalog.service.type.ProductBundlePricingModelType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "BundleOrderItemImpl_bundleOrderItem")
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_BUNDLE_ORDER_ITEM")
public class BundleOrderItemImpl extends OrderItemImpl implements BundleOrderItem {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "BundleOrderItemImpl_Discrete_Order_Items",
    tab          = OrderItemImpl.Presentation.Tab.Name.Advanced,
    tabOrder     = OrderItemImpl.Presentation.Tab.Order.Advanced
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    mappedBy     = "bundleOrderItem",
    targetEntity = DiscreteOrderItemImpl.class,
    cascade      = { CascadeType.ALL }
  )
  protected List<DiscreteOrderItem> discreteOrderItems = new ArrayList<DiscreteOrderItem>();

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "BundleOrderItemImpl_Item_Fee_Prices",
    tab          = OrderItemImpl.Presentation.Tab.Name.Advanced,
    tabOrder     = OrderItemImpl.Presentation.Tab.Order.Advanced
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    mappedBy      = "bundleOrderItem",
    targetEntity  = BundleOrderItemFeePriceImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected List<BundleOrderItemFeePrice> bundleOrderItemFeePrices = new ArrayList<BundleOrderItemFeePrice>();

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "BundleOrderItemImpl_Base_Retail_Price",
    order        = 2,
    group        = "BundleOrderItemImpl_Pricing",
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "BASE_RETAIL_PRICE",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal baseRetailPrice;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "BundleOrderItemImpl_Base_Sale_Price",
    order        = 2,
    group        = "BundleOrderItemImpl_Pricing",
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "BASE_SALE_PRICE",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal baseSalePrice;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "BundleOrderItemImpl_Sku",
    order        = Presentation.FieldOrder.SKU,
    group        = OrderItemImpl.Presentation.Group.Name.Catalog,
    groupOrder   = OrderItemImpl.Presentation.Group.Order.Catalog
  )
  @AdminPresentationToOneLookup
  @JoinColumn(name = "SKU_ID")
  @ManyToOne(targetEntity = SkuImpl.class)
  @NotFound(action = NotFoundAction.IGNORE)
  protected Sku                               sku;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "BundleOrderItemImpl_Product",
    order        = Presentation.FieldOrder.PRODUCT,
    group        = OrderItemImpl.Presentation.Group.Name.Catalog,
    groupOrder   = OrderItemImpl.Presentation.Group.Order.Catalog
  )
  @AdminPresentationToOneLookup
  @JoinColumn(name = "PRODUCT_BUNDLE_ID")
  @ManyToOne(targetEntity = ProductBundleImpl.class)
  protected ProductBundle productBundle;

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#getSku()
   */
  @Override public Sku getSku() {
    return sku;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#setSku(org.broadleafcommerce.core.catalog.domain.Sku)
   */
  @Override public void setSku(Sku sku) {
    this.sku = sku;

    if (sku != null) {
      if (sku.getRetailPrice() != null) {
        this.baseRetailPrice = sku.getRetailPrice().getAmount();
      }

      if (sku.getSalePrice() != null) {
        this.baseSalePrice = sku.getSalePrice().getAmount();
      }

      this.itemTaxable = sku.isTaxable();
      setName(sku.getName());
    }
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#getProductBundle()
   */
  @Override public ProductBundle getProductBundle() {
    return productBundle;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#setProductBundle(org.broadleafcommerce.core.catalog.domain.ProductBundle)
   */
  @Override public void setProductBundle(ProductBundle productBundle) {
    this.productBundle = productBundle;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemContainer#getOrderItems()
   */
  @Override public List<? extends OrderItem> getOrderItems() {
    return discreteOrderItems;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemContainer#getAllowDiscountsOnChildItems()
   */
  @Override public boolean getAllowDiscountsOnChildItems() {
    if (shouldSumItems()) {
      if (productBundle != null) {
        return productBundle.getItemsPromotable();
      } else {
        return true;
      }
    } else {
      return false;
    }
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemContainer#isPricingAtContainerLevel()
   */
  @Override public boolean isPricingAtContainerLevel() {
    return !shouldSumItems();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItem#isDiscountingAllowed()
   */
  @Override public boolean isDiscountingAllowed() {
    if (shouldSumItems()) {
      return false;
    } else {
      return productBundle.getBundlePromotable();
    }
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#getDiscreteOrderItems()
   */
  @Override public List<DiscreteOrderItem> getDiscreteOrderItems() {
    return discreteOrderItems;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#setDiscreteOrderItems(java.util.List)
   */
  @Override public void setDiscreteOrderItems(List<DiscreteOrderItem> discreteOrderItems) {
    this.discreteOrderItems = discreteOrderItems;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#getBundleOrderItemFeePrices()
   */
  @Override public List<BundleOrderItemFeePrice> getBundleOrderItemFeePrices() {
    return bundleOrderItemFeePrices;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#setBundleOrderItemFeePrices(java.util.List)
   */
  @Override public void setBundleOrderItemFeePrices(List<BundleOrderItemFeePrice> bundleOrderItemFeePrices) {
    this.bundleOrderItemFeePrices = bundleOrderItemFeePrices;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#getTaxablePrice()
   */
  @Override public Money getTaxablePrice() {
    if (shouldSumItems()) {
      Money currentBundleTaxablePrice = BroadleafCurrencyUtils.getMoney(getOrder().getCurrency());

      for (DiscreteOrderItem discreteOrderItem : discreteOrderItems) {
        BigDecimal currentItemTaxablePrice = discreteOrderItem.getTaxablePrice().getAmount();
        BigDecimal priceWithQuantity       = currentItemTaxablePrice.multiply(new BigDecimal(
              discreteOrderItem.getQuantity()));
        currentBundleTaxablePrice = currentBundleTaxablePrice.add(BroadleafCurrencyUtils.getMoney(priceWithQuantity,
              getOrder().getCurrency()));
      }

      for (BundleOrderItemFeePrice fee : getBundleOrderItemFeePrices()) {
        if (fee.isTaxable()) {
          currentBundleTaxablePrice = currentBundleTaxablePrice.add(fee.getAmount());
        }
      }

      return currentBundleTaxablePrice;
    } else {
      return super.getTaxablePrice();
    }
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItem#isTaxable()
   */
  @Override public Boolean isTaxable() {
    return ((sku == null) || (sku.isTaxable() == null) || sku.isTaxable());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#shouldSumItems()
   */
  @Override public boolean shouldSumItems() {
    if (productBundle != null) {
      return ProductBundlePricingModelType.ITEM_SUM.equals(productBundle.getPricingModel());
    }

    return true;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemImpl#getRetailPrice()
   */
  @Override public Money getRetailPrice() {
    if (shouldSumItems()) {
      Money bundleRetailPrice = BroadleafCurrencyUtils.getMoney(getOrder().getCurrency());

      for (DiscreteOrderItem discreteOrderItem : discreteOrderItems) {
        BigDecimal itemRetailPrice = discreteOrderItem.getRetailPrice().getAmount();
        BigDecimal quantityPrice   = itemRetailPrice.multiply(new BigDecimal(discreteOrderItem.getQuantity()));
        bundleRetailPrice = bundleRetailPrice.add(BroadleafCurrencyUtils.getMoney(quantityPrice,
              getOrder().getCurrency()));
      }

      for (BundleOrderItemFeePrice fee : getBundleOrderItemFeePrices()) {
        bundleRetailPrice = bundleRetailPrice.add(fee.getAmount());
      }

      return bundleRetailPrice;
    } else {
      return super.getRetailPrice();
    }
  }


  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemImpl#getSalePrice()
   */
  @Override public Money getSalePrice() {
    if (shouldSumItems()) {
      Money bundleSalePrice = null;

      if (hasSaleItems()) {
        bundleSalePrice = BroadleafCurrencyUtils.getMoney(getOrder().getCurrency());

        for (DiscreteOrderItem discreteOrderItem : discreteOrderItems) {
          BigDecimal itemSalePrice = null;

          if (discreteOrderItem.getSalePrice() != null) {
            itemSalePrice = discreteOrderItem.getSalePrice().getAmount();
          } else {
            itemSalePrice = discreteOrderItem.getRetailPrice().getAmount();
          }

          BigDecimal quantityPrice = itemSalePrice.multiply(new BigDecimal(discreteOrderItem.getQuantity()));
          bundleSalePrice = bundleSalePrice.add(BroadleafCurrencyUtils.getMoney(quantityPrice,
                getOrder().getCurrency()));
        }

        for (BundleOrderItemFeePrice fee : getBundleOrderItemFeePrices()) {
          bundleSalePrice = bundleSalePrice.add(fee.getAmount());
        }
      }

      return bundleSalePrice;
    } else {
      return super.getSalePrice();
    } // end if-else
  } // end method getSalePrice

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#getBaseRetailPrice()
   */
  @Override public Money getBaseRetailPrice() {
    return convertToMoney(baseRetailPrice);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#setBaseRetailPrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setBaseRetailPrice(Money baseRetailPrice) {
    this.baseRetailPrice = (baseRetailPrice == null) ? null : baseRetailPrice.getAmount();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#getBaseSalePrice()
   */
  @Override public Money getBaseSalePrice() {
    return convertToMoney(baseSalePrice);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#setBaseSalePrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setBaseSalePrice(Money baseSalePrice) {
    this.baseSalePrice = (baseSalePrice == null) ? null : baseSalePrice.getAmount();
  }

  private boolean hasSaleItems() {
    for (DiscreteOrderItem discreteOrderItem : discreteOrderItems) {
      if (discreteOrderItem.getSalePrice() != null) {
        return true;
      }
    }

    return false;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#hasAdjustedItems()
   */
  @Override public boolean hasAdjustedItems() {
    // TODO:  Handle this for bundle order items.
    return false;
  }

  private boolean updateSalePrice() {
    if (isSalePriceOverride()) {
      return false;
    }

    // Only need to update prices if we are not summing the contained items to determine
    // the price.
    if (!shouldSumItems()) {
      if ((getSku() != null) && (getSku().getSalePrice() != null) && !getSku().getSalePrice().equals(salePrice)) {
        baseSalePrice = getSku().getSalePrice().getAmount();
        salePrice     = getSku().getSalePrice().getAmount();

        return true;
      }
    }

    return false;
  }

  private boolean updateRetailPrice() {
    if (isRetailPriceOverride()) {
      return false;
    }

    // Only need to update prices if we are not summing the contained items to determine
    // the price.
    if (!shouldSumItems()) {
      if ((getSku() != null) && !getSku().getRetailPrice().equals(retailPrice)) {
        baseRetailPrice = getSku().getRetailPrice().getAmount();
        retailPrice     = getSku().getRetailPrice().getAmount();

        return true;
      }
    }

    return false;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItem#updateSaleAndRetailPrices()
   */
  @Override public boolean updateSaleAndRetailPrices() {
    boolean saleUpdated   = updateSalePrice();
    boolean retailUpdated = updateRetailPrice();

    return saleUpdated || retailUpdated;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemImpl#equals(java.lang.Object)
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

    BundleOrderItemImpl other = (BundleOrderItemImpl) obj;

    if (!super.equals(obj)) {
      return false;
    }

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

    return true;
  } // end method equals

  /**
   * @see  org.broadleafcommerce.core.order.domain.BundleOrderItem#getProduct()
   */
  @Override public Product getProduct() {
    return getProductBundle();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemImpl#convertToMoney(java.math.BigDecimal)
   */
  @Override protected Money convertToMoney(BigDecimal amount) {
    return (amount == null) ? null : BroadleafCurrencyUtils.getMoney(amount, getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemImpl#getTotalPrice()
   */
  @Override public Money getTotalPrice() {
    Money returnValue = convertToMoney(BigDecimal.ZERO);

    if (shouldSumItems()) {
      for (OrderItem containedItem : getOrderItems()) {
        returnValue = returnValue.add(containedItem.getTotalPrice());
      }
    } else {
      returnValue = super.getTotalPrice();
    }

    return returnValue;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItem#isSkuActive()
   */
  @Override public boolean isSkuActive() {
    if ((getSku() != null) && !getSku().isActive()) {
      return false;
    }

    for (DiscreteOrderItem discreteItem : getDiscreteOrderItems()) {
      if (!discreteItem.isSkuActive()) {
        return false;
      }
    }

    return true;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemImpl#clone()
   */
  @Override public OrderItem clone() {
    BundleOrderItemImpl orderItem = (BundleOrderItemImpl) super.clone();

    if (discreteOrderItems != null) {
      for (DiscreteOrderItem discreteOrderItem : discreteOrderItems) {
        DiscreteOrderItem temp = (DiscreteOrderItem) discreteOrderItem.clone();
        temp.setBundleOrderItem(orderItem);
        orderItem.getDiscreteOrderItems().add(temp);
      }
    }

    if (bundleOrderItemFeePrices != null) {
      for (BundleOrderItemFeePrice feePrice : bundleOrderItemFeePrices) {
        BundleOrderItemFeePrice cloneFeePrice = feePrice.clone();
        cloneFeePrice.setBundleOrderItem(orderItem);
        orderItem.getBundleOrderItemFeePrices().add(cloneFeePrice);
      }
    }

    orderItem.setBaseRetailPrice(convertToMoney(baseRetailPrice));
    orderItem.setBaseSalePrice(convertToMoney(baseSalePrice));
    orderItem.setSku(sku);
    orderItem.setProductBundle(productBundle);

    return orderItem;
  } // end method clone

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = super.hashCode();
    int       result = 1;
    result = (prime * result) + ((name == null) ? 0 : name.hashCode());

    return result;
  }

  public static class Presentation {
    public static class Tab {
      public static class Name {
        public static final String OrderItems = "OrderImpl_Order_Items_Tab";
      }

      public static class Order {
        public static final int OrderItems = 2000;
      }
    }

    public static class Group {
      public static class Name { }

      public static class Order { }
    }

    public static class FieldOrder {
      public static final int PRODUCT = 2000;
      public static final int SKU     = 3000;
    }
  }
} // end class BundleOrderItemImpl
