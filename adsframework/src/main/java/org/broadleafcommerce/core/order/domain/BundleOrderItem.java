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

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.ProductBundle;
import org.broadleafcommerce.core.catalog.domain.Sku;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface BundleOrderItem extends OrderItem, OrderItemContainer, SkuAccessor {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<DiscreteOrderItem> getDiscreteOrderItems();

  /**
   * DOCUMENT ME!
   *
   * @param  discreteOrderItems  DOCUMENT ME!
   */
  void setDiscreteOrderItems(List<DiscreteOrderItem> discreteOrderItems);

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItem#getTaxablePrice()
   */
  @Override Money getTaxablePrice();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<BundleOrderItemFeePrice> getBundleOrderItemFeePrices();

  /**
   * DOCUMENT ME!
   *
   * @param  bundleOrderItemFeePrices  DOCUMENT ME!
   */
  void setBundleOrderItemFeePrices(List<BundleOrderItemFeePrice> bundleOrderItemFeePrices);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean hasAdjustedItems();

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
   * For BundleOrderItem created from a ProductBundle, this will represent the default sku of the product bundle.
   *
   * <p>This can be null for implementations that programatically create product bundles.</p>
   *
   * @return  for BundleOrderItem created from a ProductBundle, this will represent the default sku of the product
   *          bundle.
   */
  @Override Sku getSku();

  /**
   * DOCUMENT ME!
   *
   * @param  sku  DOCUMENT ME!
   */
  void setSku(Sku sku);

  /**
   * Returns the associated ProductBundle or null if not applicable.
   *
   * <p>If null, then this ProductBundle was manually created.</p>
   *
   * @return  the associated ProductBundle or null if not applicable.
   */
  ProductBundle getProductBundle();

  /**
   * Sets the ProductBundle associated with this BundleOrderItem.
   *
   * @param  bundle  DOCUMENT ME!
   */
  void setProductBundle(ProductBundle bundle);

  /**
   * Same as getProductBundle.
   *
   * @return  same as getProductBundle.
   */
  Product getProduct();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  boolean shouldSumItems();
} // end interface BundleOrderItem
