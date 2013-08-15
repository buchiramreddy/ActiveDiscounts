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

import java.io.Serializable;

import java.util.List;

import org.broadleafcommerce.core.catalog.service.type.ProductOptionType;
import org.broadleafcommerce.core.catalog.service.type.ProductOptionValidationType;


/**
 * <p>A product option represents a value that is entered to specify more information about a product prior to entering
 * into the cart.</p>
 *
 * <p>For example, a product of type shirt might have product options of "size" and "color".</p>
 *
 * <p>There is an inherent relationship between product options and product SKUs. A sku is meant to provide a way to
 * override the pricing of a product for a specific set of options. Inventory can also be tracked at the SKU level.</p>
 *
 * <p>For example, consider a shirt that is sold in 5 colors and 5 sizes. For this example, there would be 1 product. It
 * would have 10 options (5 colors + 5 sizes). The product would have as few as 1 SKu and a many as 26 SKUs.</p>
 *
 * <p>1 SKU would indicate that the system is not tracking inventory for the items and that all of the variations of
 * shirt are priced the same way.</p>
 *
 * <p>26 would indicate that there are 25 SKUs that are used to track inventory and potentially override pricing. The
 * extra "1" sku is used to hold the default pricing.</p>
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public interface ProductOption extends Serializable {
  /**
   * Returns unique identifier of the product option.
   *
   * @return  unique identifier of the product option.
   */
  Long getId();

  /**
   * Sets the unique identifier of the product option.
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  /**
   * Returns the option type. For example, "color", "size", etc. These are used primarily to determine how the UI should
   * prompt for and validate the product option.
   *
   * @return  the option type.
   */
  ProductOptionType getType();

  /**
   * Sets the option type. This is primarily used for display to render the option selection.
   *
   * @param  type  DOCUMENT ME!
   */
  void setType(ProductOptionType type);

  /**
   * Gets the attribute name for where the ProductOptionValue selected for this ProductOption is stored in the
   * OrderItemAttributes for the OrderItem.
   *
   * @return  the name of the OrderItemAttribute to store the selected ProductOptionValue in the Order domain
   *
   * @see     {@link org.broadleafcommerce.core.order.domain.OrderItemAttribute},
   *          {@link org.broadleafcommerce.core.order.domain.OrderItem}
   */
  String getAttributeName();

  /**
   * Sets the attribute name that will be used in storing the selected ProductOptionValue for this ProductOption.
   *
   * @param  name  - the name of the OrderItemAttribute to store the selected ProductOptionValue in the Order domain
   */
  void setAttributeName(String name);

  /**
   * The label to show to the user when selecting from the available
   * {@link org.broadleafcommerce.core.catalog.domain.ProductOptionValue}s. This might be "Color" or "Size"
   *
   * @return  the label to show to the user when selecting from the available
   *          {@link org.broadleafcommerce.core.catalog.domain.ProductOptionValue}s.
   */
  String getLabel();

  /**
   * Sets the label to show the user when selecting from the available
   * {@link org.broadleafcommerce.core.catalog.domain.ProductOptionValue}s.
   *
   * @param  label  DOCUMENT ME!
   */
  void setLabel(String label);

  /**
   * Whether or not this ProductOption is required.
   *
   * @return  whether or not this ProductOption is required
   */
  Boolean getRequired();

  /**
   * Sets whether or not.
   *
   * @param  required  DOCUMENT ME!
   */
  void setRequired(Boolean required);

  /**
   * Gets the display order of this option in relation to the other
   * {@link org.broadleafcommerce.core.catalog.domain.ProductOption}s.
   *
   * @return  gets the display order of this option in relation to the other
   *          {@link org.broadleafcommerce.core.catalog.domain.ProductOption}s.
   */
  Integer getDisplayOrder();

  /**
   * Gets the display order of this option in relation to the other
   * {@link org.broadleafcommerce.core.catalog.domain.ProductOption}s.
   *
   * @param  displayOrder  DOCUMENT ME!
   */
  void setDisplayOrder(Integer displayOrder);


  /**
   * Gets all the Products associated with this ProductOption.
   *
   * @return  the Products associated with this ProductOption
   */
  List<Product> getProducts();

  /**
   * Set the Products to associate with this ProductOption.
   *
   * @param  products  DOCUMENT ME!
   */
  void setProducts(List<Product> products);

  /**
   * Gets the available values that a user can select for this ProductOption. This value will be stored in
   * OrderItemAttributes at the OrderItem level. The OrderItemAttribute name will be whatever was returned from
   * {@link #getAttributeName()}
   *
   * @return  the allowed values for this ProductOption
   */
  List<ProductOptionValue> getAllowedValues();

  /**
   * Set the allowed values for this ProductOption.
   *
   * @param  allowedValues  DOCUMENT ME!
   */
  void setAllowedValues(List<ProductOptionValue> allowedValues);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Boolean getUseInSkuGeneration();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ProductOptionValidationType getProductOptionValidationType();

  /**
   * DOCUMENT ME!
   *
   * @param  productOptionValidationType  DOCUMENT ME!
   */
  void setProductOptionValidationType(ProductOptionValidationType productOptionValidationType);

  /**
   * DOCUMENT ME!
   *
   * @param  useInSkuGeneration  DOCUMENT ME!
   */
  void setUseInSkuGeneration(Boolean useInSkuGeneration);

  /**
   * DOCUMENT ME!
   *
   * @param  errorMessage  DOCUMENT ME!
   */
  void setErrorMessage(String errorMessage);

  /**
   * DOCUMENT ME!
   *
   * @param  errorCode  DOCUMENT ME!
   */
  void setErrorCode(String errorCode);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getErrorMessage();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getValidationString();

  /**
   * DOCUMENT ME!
   *
   * @param  validationString  DOCUMENT ME!
   */
  void setValidationString(String validationString);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  String getErrorCode();

} // end interface ProductOption
