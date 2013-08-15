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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationToOneLookup;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductImpl;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.domain.SkuBundleItem;
import org.broadleafcommerce.core.catalog.domain.SkuBundleItemImpl;
import org.broadleafcommerce.core.catalog.domain.SkuImpl;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "DiscreteOrderItemImpl_discreteOrderItem")
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_DISCRETE_ORDER_ITEM")
public class DiscreteOrderItemImpl extends OrderItemImpl implements DiscreteOrderItem {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @AdminPresentation(
    excluded     = true,
    friendlyName = "DiscreteOrderItemImpl_Base_Retail_Price",
    order        = 2,
    group        = "DiscreteOrderItemImpl_Pricing",
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
    excluded     = true,
    friendlyName = "DiscreteOrderItemImpl_Base_Sale_Price",
    order        = 2,
    group        = "DiscreteOrderItemImpl_Pricing",
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
    friendlyName = "DiscreteOrderItemImpl_Sku",
    order        = Presentation.FieldOrder.SKU,
    group        = OrderItemImpl.Presentation.Group.Name.Catalog,
    groupOrder   = OrderItemImpl.Presentation.Group.Order.Catalog
  )
  @AdminPresentationToOneLookup
  @Index(
    name        = "DISCRETE_SKU_INDEX",
    columnNames = { "SKU_ID" }
  )
  @JoinColumn(
    name     = "SKU_ID",
    nullable = false
  )
  @ManyToOne(
    targetEntity = SkuImpl.class,
    optional     = false
  )
  protected Sku                               sku;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "DiscreteOrderItemImpl_Product",
    order        = Presentation.FieldOrder.PRODUCT,
    group        = OrderItemImpl.Presentation.Group.Name.Catalog,
    groupOrder   = OrderItemImpl.Presentation.Group.Order.Catalog
  )
  @AdminPresentationToOneLookup
  @Index(
    name        = "DISCRETE_PRODUCT_INDEX",
    columnNames = { "PRODUCT_ID" }
  )
  @JoinColumn(name = "PRODUCT_ID")
  @ManyToOne(targetEntity = ProductImpl.class)
  @NotFound(action = NotFoundAction.IGNORE)
  protected Product                               product;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "BUNDLE_ORDER_ITEM_ID")
  @ManyToOne(targetEntity = BundleOrderItemImpl.class)
  protected BundleOrderItem bundleOrderItem;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @JoinColumn(name = "SKU_BUNDLE_ITEM_ID")
  @ManyToOne(targetEntity = SkuBundleItemImpl.class)
  protected SkuBundleItem skuBundleItem;

  /** DOCUMENT ME! */
  @BatchSize(size = 50)
  @CollectionTable(
    name        = "BLC_ORDER_ITEM_ADD_ATTR",
    joinColumns = @JoinColumn(name = "ORDER_ITEM_ID")
  )
  @Column(name = "VALUE")
  @Deprecated @ElementCollection
  @MapKeyColumn(name = "NAME")
  protected Map<String, String> additionalAttributes = new HashMap<String, String>();

  /** DOCUMENT ME! */
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    mappedBy      = "discreteOrderItem",
    targetEntity  = DiscreteOrderItemFeePriceImpl.class,
    cascade       = { CascadeType.ALL },
    orphanRemoval = true
  )
  protected List<DiscreteOrderItemFeePrice> discreteOrderItemFeePrices = new ArrayList<DiscreteOrderItemFeePrice>();

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#getSku()
   */
  @Override public Sku getSku() {
    return sku;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#setSku(org.broadleafcommerce.core.catalog.domain.Sku)
   */
  @Override public void setSku(Sku sku) {
    this.sku = sku;

    if (sku.getRetailPrice() != null) {
      this.baseRetailPrice = sku.getRetailPrice().getAmount();
    }

    if (sku.getSalePrice() != null) {
      this.baseSalePrice = sku.getSalePrice().getAmount();
    }

    this.itemTaxable = sku.isTaxable();
    setName(sku.getName());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItem#isTaxable()
   */
  @Override public Boolean isTaxable() {
    return ((sku == null) || (sku.isTaxable() == null) || sku.isTaxable());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#getProduct()
   */
  @Override public Product getProduct() {
    return product;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#setProduct(org.broadleafcommerce.core.catalog.domain.Product)
   */
  @Override public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#getBundleOrderItem()
   */
  @Override public BundleOrderItem getBundleOrderItem() {
    return bundleOrderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#setBundleOrderItem(org.broadleafcommerce.core.order.domain.BundleOrderItem)
   */
  @Override public void setBundleOrderItem(BundleOrderItem bundleOrderItem) {
    if ((this.order != null) && (bundleOrderItem != null)) {
      throw new IllegalStateException(
        "Cannot set a BundleOrderItem on a DiscreteOrderItem that is already associated with an Order");
    }

    this.bundleOrderItem = bundleOrderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemImpl#setOrder(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public void setOrder(Order order) {
    if ((order != null) && (bundleOrderItem != null)) {
      throw new IllegalStateException(
        "Cannot set an Order on a DiscreteOrderItem that is already associated with a BundleOrderItem");
    }

    this.order = order;
  }

  /**
   * If this item is part of a bundle that was created via a ProductBundle, then this method returns a reference to the
   * corresponding SkuBundleItem.
   *
   * <p/>For manually created
   * </p>For all others, this method returns null.
   *
   * @return  if this item is part of a bundle that was created via a ProductBundle, then this method returns a
   *          reference to the corresponding SkuBundleItem.
   */
  @Override public SkuBundleItem getSkuBundleItem() {
    return skuBundleItem;
  }

  /**
   * Sets the associated SkuBundleItem.
   *
   * @param  SkuBundleItem  DOCUMENT ME!
   */
  @Override public void setSkuBundleItem(SkuBundleItem SkuBundleItem) {
    this.skuBundleItem = SkuBundleItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItem#getName()
   */
  @Override public String getName() {
    String name = super.getName();

    if (name == null) {
      return sku.getName();
    }

    return name;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItem#getOrder()
   */
  @Override public Order getOrder() {
    if (order == null) {
      if (getBundleOrderItem() != null) {
        return getBundleOrderItem().getOrder();
      }
    }

    return order;
  }

  private boolean updateSalePrice() {
    if (isSalePriceOverride()) {
      return false;
    }

    Money skuSalePrice = ((getSku().getSalePrice() == null) ? null : getSku().getSalePrice());

    // Override retail/sale prices from skuBundle.
    if (skuBundleItem != null) {
      if (skuBundleItem.getSalePrice() != null) {
        skuSalePrice = skuBundleItem.getSalePrice();
      }
    }

    boolean updated = false;

    // use the sku prices - the retail and sale prices could be null
    if ((skuSalePrice != null) && !skuSalePrice.equals(salePrice)) {
      baseSalePrice = skuSalePrice.getAmount();
      salePrice     = skuSalePrice.getAmount();
      updated       = true;
    }

    // Adjust prices by adding in fees if they are attached.
    if (getDiscreteOrderItemFeePrices() != null) {
      for (DiscreteOrderItemFeePrice fee : getDiscreteOrderItemFeePrices()) {
        Money returnPrice = convertToMoney(salePrice);
        salePrice = returnPrice.add(fee.getAmount()).getAmount();
      }
    }

    return updated;
  } // end method updateSalePrice

  private boolean updateRetailPrice() {
    if (isRetailPriceOverride()) {
      return false;
    }

    Money skuRetailPrice = getSku().getRetailPrice();

    // Override retail/sale prices from skuBundle.
    if (skuBundleItem != null) {
      if (skuBundleItem.getRetailPrice() != null) {
        skuRetailPrice = skuBundleItem.getRetailPrice();
      }
    }

    boolean updated = false;

    // use the sku prices - the retail and sale prices could be null
    if (!skuRetailPrice.equals(retailPrice)) {
      baseRetailPrice = skuRetailPrice.getAmount();
      retailPrice     = skuRetailPrice.getAmount();
      updated         = true;
    }

    // Adjust prices by adding in fees if they are attached.
    if (getDiscreteOrderItemFeePrices() != null) {
      for (DiscreteOrderItemFeePrice fee : getDiscreteOrderItemFeePrices()) {
        Money returnPrice = convertToMoney(retailPrice);
        retailPrice = returnPrice.add(fee.getAmount()).getAmount();
      }
    }

    return updated;
  } // end method updateRetailPrice

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemImpl#updateSaleAndRetailPrices()
   */
  @Override public boolean updateSaleAndRetailPrices() {
    boolean salePriceUpdated   = updateSalePrice();
    boolean retailPriceUpdated = updateRetailPrice();

    if (!isRetailPriceOverride() && !isSalePriceOverride()) {
      if (salePrice != null) {
        price = salePrice;
      } else {
        price = retailPrice;
      }
    }

    return salePriceUpdated || retailPriceUpdated;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#getAdditionalAttributes()
   */
  @Override public Map<String, String> getAdditionalAttributes() {
    return additionalAttributes;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#setAdditionalAttributes(java.util.Map)
   */
  @Override public void setAdditionalAttributes(Map<String, String> additionalAttributes) {
    this.additionalAttributes = additionalAttributes;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#getBaseRetailPrice()
   */
  @Override public Money getBaseRetailPrice() {
    return convertToMoney(baseRetailPrice);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#setBaseRetailPrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setBaseRetailPrice(Money baseRetailPrice) {
    this.baseRetailPrice = baseRetailPrice.getAmount();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#getBaseSalePrice()
   */
  @Override public Money getBaseSalePrice() {
    return convertToMoney(baseSalePrice);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#setBaseSalePrice(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setBaseSalePrice(Money baseSalePrice) {
    this.baseSalePrice = (baseSalePrice == null) ? null : baseSalePrice.getAmount();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#getDiscreteOrderItemFeePrices()
   */
  @Override public List<DiscreteOrderItemFeePrice> getDiscreteOrderItemFeePrices() {
    return discreteOrderItemFeePrices;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#setDiscreteOrderItemFeePrices(java.util.List)
   */
  @Override public void setDiscreteOrderItemFeePrices(List<DiscreteOrderItemFeePrice> discreteOrderItemFeePrices) {
    this.discreteOrderItemFeePrices = discreteOrderItemFeePrices;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemImpl#convertToMoney(java.math.BigDecimal)
   */
  @Override protected Money convertToMoney(BigDecimal amount) {
    return (amount == null) ? null : BroadleafCurrencyUtils.getMoney(amount, getOrder().getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemImpl#clone()
   */
  @Override public OrderItem clone() {
    DiscreteOrderItem orderItem = (DiscreteOrderItem) super.clone();

    if (discreteOrderItemFeePrices != null) {
      for (DiscreteOrderItemFeePrice feePrice : discreteOrderItemFeePrices) {
        DiscreteOrderItemFeePrice cloneFeePrice = feePrice.clone();
        cloneFeePrice.setDiscreteOrderItem(orderItem);
        orderItem.getDiscreteOrderItemFeePrices().add(cloneFeePrice);
      }
    }

    if (additionalAttributes != null) {
      orderItem.getAdditionalAttributes().putAll(additionalAttributes);
    }

    orderItem.setBaseRetailPrice(convertToMoney(baseRetailPrice));
    orderItem.setBaseSalePrice(convertToMoney(baseSalePrice));
    orderItem.setBundleOrderItem(bundleOrderItem);
    orderItem.setProduct(product);
    orderItem.setSku(sku);

    if (orderItem.getOrder() == null) {
      throw new IllegalStateException("Either an Order or a BundleOrderItem must be set on the DiscreteOrderItem");
    }

    return orderItem;
  } // end method clone

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

    DiscreteOrderItemImpl other = (DiscreteOrderItemImpl) obj;

    if (!super.equals(obj)) {
      return false;
    }

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (bundleOrderItem == null) {
      if (other.bundleOrderItem != null) {
        return false;
      }
    } else if (!bundleOrderItem.equals(other.bundleOrderItem)) {
      return false;
    }

    if (sku == null) {
      if (other.sku != null) {
        return false;
      }
    } else if (!sku.equals(other.sku)) {
      return false;
    }

    return true;
  } // end method equals

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = super.hashCode();
    int       result = 1;
    result = (prime * result) + ((bundleOrderItem == null) ? 0 : bundleOrderItem.hashCode());
    result = (prime * result) + ((sku == null) ? 0 : sku.hashCode());

    return result;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemImpl#isDiscountingAllowed()
   */
  @Override public boolean isDiscountingAllowed() {
    if (discountsAllowed == null) {
      return sku.isDiscountable();
    } else {
      return discountsAllowed.booleanValue();
    }
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#findParentItem()
   */
  @Override public BundleOrderItem findParentItem() {
    for (OrderItem orderItem : getOrder().getOrderItems()) {
      if (orderItem instanceof BundleOrderItem) {
        BundleOrderItem bundleItem = (BundleOrderItem) orderItem;

        for (OrderItem containedItem : bundleItem.getOrderItems()) {
          if (containedItem.equals(this)) {
            return bundleItem;
          }
        }
      }
    }

    return null;
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

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItem#isSkuActive()
   */
  @Override public boolean isSkuActive() {
    return sku.isActive();
  }
} // end class DiscreteOrderItemImpl
