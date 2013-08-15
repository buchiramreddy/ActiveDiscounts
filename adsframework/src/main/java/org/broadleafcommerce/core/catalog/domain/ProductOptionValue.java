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

import org.broadleafcommerce.common.money.Money;


/**
 * <p>Stores the values for a given product option.</p>
 *
 * <p>For example, a ProductOption of type "color" might have values of ("red","blue").</p>
 *
 * <p>ProductOptionValues can also have a price adjustment associated with it which will be automatically added to the
 * Sku retail price and sale price</p>
 *
 * @author   bpolster.
 * @version  $Revision$, $Date$
 */
public interface ProductOptionValue extends Serializable {
  /**
   * Returns unique identifier of the product option value.
   *
   * @return  unique identifier of the product option value.
   */
  Long getId();

  /**
   * Sets the unique identifier of the product option value.
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  /**
   * Gets the option value. (e.g. "red")
   *
   * @return  gets the option value.
   */
  String getAttributeValue();

  /**
   * Sets the option value. (e.g. "red")
   *
   * @param  attributeValue  DOCUMENT ME!
   */
  void setAttributeValue(String attributeValue);

  /**
   * Returns the order that the option value should be displayed in.
   *
   * @return  the order that the option value should be displayed in.
   */
  Long getDisplayOrder();

  /**
   * Sets the display order.
   *
   * @param  order  DOCUMENT ME!
   */
  void setDisplayOrder(Long order);

  /**
   * Gets the price adjustment associated with this value. For instance, if this ProductOptionValue represented an
   * extra-large shirt, that might be a $1 upcharge. This adjustments will be automatically added to the Sku retail
   * price and sale price<br />
   * <br />
   * Note: This could also be a negative value if you wanted to offer a particular ProductOptionValue at a discount
   *
   * @return  the price adjustment for this
   */
  Money getPriceAdjustment();

  /**
   * Gets the price adjustment associated with this value. For instance, if this ProductOptionValue represented an
   * extra-large shirt, that might be a $1 upcharge. These adjustments will be automatically added to the Sku retail
   * price and sale price. To offer this particular ProductOptionValue at a discount, you could also provide a negative
   * value here
   *
   * @param  priceAdjustment  DOCUMENT ME!
   */
  void setPriceAdjustment(Money priceAdjustment);

  /**
   * Returns the associated ProductOption.
   *
   * @return  the associated ProductOption.
   */
  ProductOption getProductOption();

  /**
   * Sets the associated product option.
   *
   * @param  productOption  DOCUMENT ME!
   */
  void setProductOption(ProductOption productOption);

} // end interface ProductOptionValue
