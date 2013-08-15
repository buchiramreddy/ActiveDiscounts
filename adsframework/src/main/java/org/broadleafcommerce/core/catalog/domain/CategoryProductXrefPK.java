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

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.builder.EqualsBuilder;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@Embeddable public class CategoryProductXrefPK implements Serializable {
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @JoinColumn(name = "CATEGORY_ID")
  @ManyToOne(
    targetEntity = CategoryImpl.class,
    optional     = false
  )
  protected Category category = new CategoryImpl();

  /** The product. */
  @JoinColumn(name = "PRODUCT_ID")
  @ManyToOne(
    targetEntity = ProductImpl.class,
    optional     = false
  )
  protected Product product = new ProductImpl();

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
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    return category.hashCode() + product.hashCode();
  }

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (obj instanceof CategoryProductXrefPK) {
      CategoryProductXrefPK that = (CategoryProductXrefPK) obj;

      return new EqualsBuilder().append(category.getId(), that.category.getId()).append(product.getId(),
          that.product.getId()).build();
    }

    return false;
  }

} // end class CategoryProductXrefPK
