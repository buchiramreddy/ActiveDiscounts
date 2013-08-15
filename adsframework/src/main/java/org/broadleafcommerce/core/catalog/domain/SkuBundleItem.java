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

import java.io.Serializable;

import org.broadleafcommerce.common.money.Money;


/**
 * Represents the {@link Sku} being sold in a bundle along with metadata about the relationship itself like how many
 * items should be included in the bundle.
 *
 * @author   Phillip Verheyden
 * @see      org.broadleafcommerce.core.catalog.domain.ProductBundle, Product
 * @version  $Revision$, $Date$
 */
public interface SkuBundleItem extends Serializable {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Integer getQuantity();

  /**
   * DOCUMENT ME!
   *
   * @param  quantity  DOCUMENT ME!
   */
  void setQuantity(Integer quantity);

  /**
   * Allows for overriding the related Product's sale price. This is only used if the pricing model for the bundle is a
   * composition of its parts getProduct().getDefaultSku().getSalePrice()
   *
   * @param  salePrice  The sale price for this bundle item
   */
  void setSalePrice(Money salePrice);

  /**
   * This itemSalePrice if it is set, getProduct().getDefaultSku().getSalePrice() if this item's itemSalePrice is null.
   *
   * @return  this itemSalePrice if it is set, getProduct().getDefaultSku().getSalePrice() if this item's itemSalePrice
   *          is null
   */
  Money getSalePrice();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ProductBundle getBundle();

  /**
   * DOCUMENT ME!
   *
   * @param  bundle  DOCUMENT ME!
   */
  void setBundle(ProductBundle bundle);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Money getRetailPrice();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Sku getSku();

  /**
   * DOCUMENT ME!
   *
   * @param  sku  DOCUMENT ME!
   */
  void setSku(Sku sku);

  /**
   * Removes any currently stored dynamic pricing.
   */
  void clearDynamicPrices();
} // end interface SkuBundleItem
