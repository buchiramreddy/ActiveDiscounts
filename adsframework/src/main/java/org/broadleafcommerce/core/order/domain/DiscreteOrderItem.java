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

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.catalog.domain.SkuBundleItem;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface DiscreteOrderItem extends OrderItem, SkuAccessor, Cloneable {
  /**
   * @see  org.broadleafcommerce.core.order.domain.SkuAccessor#getSku()
   */
  @Override Sku getSku();

  /**
   * DOCUMENT ME!
   *
   * @param  sku  DOCUMENT ME!
   */
  void setSku(Sku sku);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Product getProduct();

  /**
   * DOCUMENT ME!
   *
   * @param  product  DOCUMENT ME!
   */
  void setProduct(Product product);

  /**
   * If this item is part of a bundle, this method will return the containing bundle item.
   *
   * @return  if this item is part of a bundle, this method will return the containing bundle item.
   */
  BundleOrderItem getBundleOrderItem();

  /**
   * Sets the parent bundle item.
   *
   * <p>Setting to null removes this item from the bundle.</p>
   *
   * @param  bundleOrderItem  DOCUMENT ME!
   */
  void setBundleOrderItem(BundleOrderItem bundleOrderItem);

  /**
   * If this item is part of a bundle that was created via a ProductBundle, then this method returns a reference to the
   * corresponding SkuBundleItem.
   *
   * <p>For manually created</p>
   *
   * <p>For all others, this method returns null.</p>
   *
   * @return  if this item is part of a bundle that was created via a ProductBundle, then this method returns a
   *          reference to the corresponding SkuBundleItem.
   */
  SkuBundleItem getSkuBundleItem();

  /**
   * Sets the associated skuBundleItem.
   *
   * @param  skuBundleItem  DOCUMENT ME!
   */
  void setSkuBundleItem(SkuBundleItem skuBundleItem);

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItem#getTaxablePrice()
   */
  @Override Money getTaxablePrice();

  /**
   * Arbitrary attributes associated with the order item.
   *
   * @deprecated  use getOrderItemAttributes instead
   *
   * @return      the attributes
   */
  Map<String, String> getAdditionalAttributes();

  /**
   * Arbitrary attributes associated with the order item.
   *
   * @deprecated  use setOrderItemAttributes instead
   *
   * @param       additionalAttributes  the map of attributes
   */
  void setAdditionalAttributes(Map<String, String> additionalAttributes);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getBaseRetailPrice();

  /**
   * DOCUMENT ME!
   *
   * @param  baseRetailPrice  DOCUMENT ME!
   */
  void setBaseRetailPrice(Money baseRetailPrice);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getBaseSalePrice();

  /**
   * DOCUMENT ME!
   *
   * @param  baseSalePrice  DOCUMENT ME!
   */
  void setBaseSalePrice(Money baseSalePrice);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<DiscreteOrderItemFeePrice> getDiscreteOrderItemFeePrices();

  /**
   * DOCUMENT ME!
   *
   * @param  orderItemFeePrices  DOCUMENT ME!
   */
  void setDiscreteOrderItemFeePrices(List<DiscreteOrderItemFeePrice> orderItemFeePrices);

  /**
   * For items that are part of a bundle, this method will return the parent bundle item. Otherwise, returns null.
   *
   * @return  for items that are part of a bundle, this method will return the parent bundle item.
   */
  BundleOrderItem findParentItem();

  /**
   * Returns a boolean indicating whether this sku is active. This is used to determine whether a user the sku can add
   * the sku to their cart.
   *
   * @return  a boolean indicating whether this sku is active.
   */
  @Override boolean isSkuActive();
} // end interface DiscreteOrderItem
