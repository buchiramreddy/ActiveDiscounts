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

package org.broadleafcommerce.core.catalog.service.dynamic;

import java.io.Serializable;

import org.broadleafcommerce.common.money.Money;


/**
 * DTO to represent pricing overrides returned from invocations to
 * {@link org.broadleafcommerce.core.catalog.service.dynamic.DynamicSkuPricingService}.
 *
 * @author   jfischer
 * @see      {@link org.broadleafcommerce.core.catalog.service.dynamic.DynamicSkuPricingService}
 * @version  $Revision$, $Date$
 */
public class DynamicSkuPrices implements Serializable {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  protected Money retailPrice;

  /** DOCUMENT ME! */
  protected Money salePrice;

  /** DOCUMENT ME! */
  protected Money priceAdjustment;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getRetailPrice() {
    return retailPrice;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  retailPrice  DOCUMENT ME!
   */
  public void setRetailPrice(Money retailPrice) {
    this.retailPrice = retailPrice;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getSalePrice() {
    return salePrice;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  salePrice  DOCUMENT ME!
   */
  public void setSalePrice(Money salePrice) {
    this.salePrice = salePrice;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getPriceAdjustment() {
    return priceAdjustment;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  priceAdjustment  DOCUMENT ME!
   */
  public void setPriceAdjustment(Money priceAdjustment) {
    this.priceAdjustment = priceAdjustment;
  }

} // end class DynamicSkuPrices
