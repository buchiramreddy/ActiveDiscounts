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

package org.broadleafcommerce.core.search.domain;

import java.math.BigDecimal;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class SearchFacetResultDTO {
  /** DOCUMENT ME! */
  protected SearchFacet facet;

  /** DOCUMENT ME! */
  protected String value;

  /** DOCUMENT ME! */
  protected BigDecimal minValue;

  /** DOCUMENT ME! */
  protected BigDecimal maxValue;

  /** DOCUMENT ME! */
  protected Integer quantity;

  /** DOCUMENT ME! */
  protected boolean active;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public SearchFacet getFacet() {
    return facet;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  facet  DOCUMENT ME!
   */
  public void setFacet(SearchFacet facet) {
    this.facet = facet;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getValue() {
    return value;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  value  DOCUMENT ME!
   */
  public void setValue(String value) {
    this.value = value;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public BigDecimal getMinValue() {
    return minValue;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  minValue  DOCUMENT ME!
   */
  public void setMinValue(BigDecimal minValue) {
    this.minValue = minValue;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public BigDecimal getMaxValue() {
    return maxValue;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  maxValue  DOCUMENT ME!
   */
  public void setMaxValue(BigDecimal maxValue) {
    this.maxValue = maxValue;
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
   * @param  quantity  DOCUMENT ME!
   */
  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isActive() {
    return active;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  active  DOCUMENT ME!
   */
  public void setActive(boolean active) {
    this.active = active;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getValueKey() {
    String value = getValue();

    if (value == null) {
      value = "range[" + getMinValue() + ":" + getMaxValue() + "]";
    }

    return value;
  }

} // end class SearchFacetResultDTO
