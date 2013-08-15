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


/**
 * Only the product and quantity are required to add an item to an order.
 *
 * <p>The category can be inferred from the product's default category.</p>
 *
 * <p>The sku can be inferred from either the passed in attributes as they are compared to the product's options or the
 * sku can be determined from the product's default sku.</p>
 *
 * <p>When adding a bundle using this DTO, you MUST have the
 * {@link org.broadleafcommerce.core.catalog.domain.ProductBundle} included in the productId for it to properly
 * instantiate the {@link org.broadleafcommerce.core.order.domain.BundleOrderItem}</p>
 *
 * <p>Important Note: To protect against misuse, the {@link org.broadleafcommerce.core.order.service.OrderService}'s
 * addItemToCart method will blank out any values passed in on this DTO for the overrideSalePrice or
 * overrideRetailPrice.</p>
 *
 * <p>Instead, implementors should call the more explicit addItemWithPriceOverrides.</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class OrderItemRequestDTO {
  private Long                skuId;
  private Long                categoryId;
  private Long                productId;
  private Long                orderItemId;
  private Integer             quantity;
  private Money               overrideSalePrice;
  private Money               overrideRetailPrice;
  private Map<String, String> itemAttributes = new HashMap<String, String>();

  /**
   * Creates a new OrderItemRequestDTO object.
   */
  public OrderItemRequestDTO() { }

  /**
   * Creates a new OrderItemRequestDTO object.
   *
   * @param  productId  DOCUMENT ME!
   * @param  quantity   DOCUMENT ME!
   */
  public OrderItemRequestDTO(Long productId, Integer quantity) {
    setProductId(productId);
    setQuantity(quantity);
  }

  /**
   * Creates a new OrderItemRequestDTO object.
   *
   * @param  productId  DOCUMENT ME!
   * @param  skuId      DOCUMENT ME!
   * @param  quantity   DOCUMENT ME!
   */
  public OrderItemRequestDTO(Long productId, Long skuId, Integer quantity) {
    setProductId(productId);
    setSkuId(skuId);
    setQuantity(quantity);
  }

  /**
   * Creates a new OrderItemRequestDTO object.
   *
   * @param  productId   DOCUMENT ME!
   * @param  skuId       DOCUMENT ME!
   * @param  categoryId  DOCUMENT ME!
   * @param  quantity    DOCUMENT ME!
   */
  public OrderItemRequestDTO(Long productId, Long skuId, Long categoryId, Integer quantity) {
    setProductId(productId);
    setSkuId(skuId);
    setCategoryId(categoryId);
    setQuantity(quantity);
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getSkuId() {
    return skuId;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   skuId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OrderItemRequestDTO setSkuId(Long skuId) {
    this.skuId = skuId;

    return this;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getCategoryId() {
    return categoryId;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   categoryId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OrderItemRequestDTO setCategoryId(Long categoryId) {
    this.categoryId = categoryId;

    return this;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getProductId() {
    return productId;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   productId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OrderItemRequestDTO setProductId(Long productId) {
    this.productId = productId;

    return this;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getQuantity() {
    return quantity;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   quantity  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OrderItemRequestDTO setQuantity(Integer quantity) {
    this.quantity = quantity;

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
  public OrderItemRequestDTO setItemAttributes(Map<String, String> itemAttributes) {
    this.itemAttributes = itemAttributes;

    return this;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getOrderItemId() {
    return orderItemId;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   orderItemId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OrderItemRequestDTO setOrderItemId(Long orderItemId) {
    this.orderItemId = orderItemId;

    return this;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getOverrideSalePrice() {
    return overrideSalePrice;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  overrideSalePrice  DOCUMENT ME!
   */
  public void setOverrideSalePrice(Money overrideSalePrice) {
    this.overrideSalePrice = overrideSalePrice;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getOverrideRetailPrice() {
    return overrideRetailPrice;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  overrideRetailPrice  DOCUMENT ME!
   */
  public void setOverrideRetailPrice(Money overrideRetailPrice) {
    this.overrideRetailPrice = overrideRetailPrice;
  }
} // end class OrderItemRequestDTO
