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
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.catalog.domain.Sku;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.PersonalMessage;


/**
 * Only the product is required to add an item to an order.
 *
 * <p>The category can be inferred from the product's default category.</p>
 *
 * <p>The sku can be inferred from either the passed in attributes as they are compared to the product's options or the
 * sku can be determined from the product's default sku.</p>
 *
 * <p>Personal message is optional.</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public abstract class AbstractOrderItemRequest {
  /** DOCUMENT ME! */
  protected Sku                 sku;

  /** DOCUMENT ME! */
  protected Category            category;

  /** DOCUMENT ME! */
  protected Product             product;

  /** DOCUMENT ME! */
  protected Order               order;

  /** DOCUMENT ME! */
  protected int                 quantity;

  /** DOCUMENT ME! */
  protected Money               salePriceOverride;

  /** DOCUMENT ME! */
  protected Money               retailPriceOverride;

  /** DOCUMENT ME! */
  protected PersonalMessage     personalMessage;

  /** DOCUMENT ME! */
  protected Map<String, String> itemAttributes = new HashMap<String, String>();

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
   * @param  sku  DOCUMENT ME!
   */
  public void setSku(Sku sku) {
    this.sku = sku;
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
   * @param  category  DOCUMENT ME!
   */
  public void setCategory(Category category) {
    this.category = category;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Product getProduct() {
    return product;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  product  DOCUMENT ME!
   */
  public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  public void setOrder(Order order) {
    this.order = order;
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
   * @param  quantity  DOCUMENT ME!
   */
  public void setQuantity(int quantity) {
    this.quantity = quantity;
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
   * @param  itemAttributes  DOCUMENT ME!
   */
  public void setItemAttributes(Map<String, String> itemAttributes) {
    this.itemAttributes = itemAttributes;
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

  /**
   * DOCUMENT ME!
   *
   * @param  newRequest  DOCUMENT ME!
   */
  protected void copyProperties(AbstractOrderItemRequest newRequest) {
    newRequest.setCategory(category);
    newRequest.setItemAttributes(itemAttributes);
    newRequest.setPersonalMessage(personalMessage);
    newRequest.setProduct(product);
    newRequest.setQuantity(quantity);
    newRequest.setSku(sku);
    newRequest.setOrder(order);
    newRequest.setSalePriceOverride(salePriceOverride);
    newRequest.setRetailPriceOverride(retailPriceOverride);
  }

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof AbstractOrderItemRequest)) {
      return false;
    }

    AbstractOrderItemRequest that = (AbstractOrderItemRequest) o;

    if (quantity != that.quantity) {
      return false;
    }

    if ((category != null) ? (!category.equals(that.category)) : (that.category != null)) {
      return false;
    }

    if ((product != null) ? (!product.equals(that.product)) : (that.product != null)) {
      return false;
    }

    if ((salePriceOverride != null) ? (!salePriceOverride.equals(that.salePriceOverride))
                                    : (that.salePriceOverride != null)) {
      return false;
    }

    if ((sku != null) ? (!sku.equals(that.sku)) : (that.sku != null)) {
      return false;
    }

    if ((order != null) ? (!order.equals(that.order)) : (that.order != null)) {
      return false;
    }

    return true;
  } // end method equals

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (sku != null) ? sku.hashCode() : 0;
    result = (31 * result) + ((category != null) ? category.hashCode() : 0);
    result = (31 * result) + ((product != null) ? product.hashCode() : 0);
    result = (31 * result) + ((order != null) ? order.hashCode() : 0);
    result = (31 * result) + quantity;
    result = (31 * result) + ((salePriceOverride != null) ? salePriceOverride.hashCode() : 0);

    return result;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PersonalMessage getPersonalMessage() {
    return personalMessage;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  personalMessage  DOCUMENT ME!
   */
  public void setPersonalMessage(PersonalMessage personalMessage) {
    this.personalMessage = personalMessage;
  }
} // end class AbstractOrderItemRequest
