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

/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class RelatedProductDTO {
  private Long                   categoryId;
  private Long                   productId;
  private RelatedProductTypeEnum type              = RelatedProductTypeEnum.FEATURED;
  private boolean                cumulativeResults = true;
  private Integer                quantity          = null;

  /**
   * Returns the categoryId for which the system should find related products.
   *
   * @return  the categoryId for which the system should find related products.
   */
  public Long getCategoryId() {
    return categoryId;
  }

  /**
   * Sets the categoryId for which the system should find related products. May be ignored by implementations if an
   * productId is also specified.
   *
   * @param  categoryId  DOCUMENT ME!
   */
  public void setCategoryId(Long categoryId) {
    this.categoryId = categoryId;
  }

  /**
   * Returns the productId for which the system should find related products.
   *
   * @return  the productId for which the system should find related products.
   */
  public Long getProductId() {
    return productId;
  }

  /**
   * Sets the productId for which the system should find related products.
   *
   * @param  productId  DOCUMENT ME!
   */
  public void setProductId(Long productId) {
    this.productId = productId;
  }

  /**
   * Returns the type of relationship that is desired.
   *
   * @see     org.broadleafcommerce.core.catalog.domain.RelatedProductTypeEnum
   *
   * @return  the type of relationship that is desired.
   */
  public RelatedProductTypeEnum getType() {
    return type;
  }

  /**
   * Sets the type of relationship that is desired.
   *
   * @see    org.broadleafcommerce.core.catalog.domain.RelatedProductTypeEnum
   *
   * @param  type  DOCUMENT ME!
   */
  public void setType(RelatedProductTypeEnum type) {
    this.type = type;
  }

  /**
   * Returns whether cumulative results are desired. Defaults to true.
   *
   * @return  whether cumulative results are desired.
   */
  public boolean isCumulativeResults() {
    return cumulativeResults;
  }

  /**
   * Sets whether cumulative results are desired.
   *
   * @param  cumulativeResults  DOCUMENT ME!
   */
  public void setCumulativeResults(boolean cumulativeResults) {
    this.cumulativeResults = cumulativeResults;
  }

  /**
   * The number of results to return. The system will not look for additional results after the quantity has been met.
   * Null indicates to return all results.
   *
   * @return  the number of results to return.
   */
  public Integer getQuantity() {
    return quantity;
  }

  /**
   * The number of results to return. Null indicates to return all results.
   *
   * @param  quantity  DOCUMENT ME!
   */
  public void setQuantity(Integer quantity) {
    this.quantity = quantity;
  }
} // end class RelatedProductDTO
