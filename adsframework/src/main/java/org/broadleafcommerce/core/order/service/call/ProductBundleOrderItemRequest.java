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

package org.broadleafcommerce.core.order.service.call;

import java.util.HashMap;
import java.util.Map;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.ProductBundle;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.order.domain.Order;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class ProductBundleOrderItemRequest {
  /** DOCUMENT ME! */
  protected String            name;

  /** DOCUMENT ME! */
  protected Category          category;

  /** DOCUMENT ME! */
  protected Sku               sku;

  /** DOCUMENT ME! */
  protected Order             order;

  /** DOCUMENT ME! */
  protected int               quantity;

  /** DOCUMENT ME! */
  protected ProductBundle     productBundle;
  private Map<String, String> itemAttributes      = new HashMap<String, String>();

  /** DOCUMENT ME! */
  protected Money             salePriceOverride;

  /** DOCUMENT ME! */
  protected Money             retailPriceOverride;

  /**
   * Creates a new ProductBundleOrderItemRequest object.
   */
  public ProductBundleOrderItemRequest() { }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getName() {
    return name;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   name  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ProductBundleOrderItemRequest setName(String name) {
    this.name = name;

    return this;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Category getCategory() {
    return category;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   category  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ProductBundleOrderItemRequest setCategory(Category category) {
    this.category = category;

    return this;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Sku getSku() {
    return sku;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   sku  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ProductBundleOrderItemRequest setSku(Sku sku) {
    this.sku = sku;

    return this;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ProductBundleOrderItemRequest setOrder(Order order) {
    this.order = order;

    return this;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Order getOrder() {
    return order;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getQuantity() {
    return quantity;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   quantity  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ProductBundleOrderItemRequest setQuantity(int quantity) {
    this.quantity = quantity;

    return this;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ProductBundle getProductBundle() {
    return productBundle;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   productBundle  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ProductBundleOrderItemRequest setProductBundle(ProductBundle productBundle) {
    this.productBundle = productBundle;

    return this;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, String> getItemAttributes() {
    return itemAttributes;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   itemAttributes  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public ProductBundleOrderItemRequest setItemAttributes(Map<String, String> itemAttributes) {
    this.itemAttributes = itemAttributes;

    return this;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getSalePriceOverride() {
    return salePriceOverride;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  salePriceOverride  DOCUMENT ME!
   */
  public void setSalePriceOverride(Money salePriceOverride) {
    this.salePriceOverride = salePriceOverride;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getRetailPriceOverride() {
    return retailPriceOverride;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  retailPriceOverride  DOCUMENT ME!
   */
  public void setRetailPriceOverride(Money retailPriceOverride) {
    this.retailPriceOverride = retailPriceOverride;
  }

} // end class ProductBundleOrderItemRequest
