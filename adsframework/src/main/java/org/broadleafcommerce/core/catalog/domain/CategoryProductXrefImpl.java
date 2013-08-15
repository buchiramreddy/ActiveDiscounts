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

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;


/**
 * The Class CategoryProductXrefImpl is the default implmentation of
 * {@link org.broadleafcommerce.core.catalog.domain.Category}. This entity is only used for executing a named query.
 *
 * <p>If you want to add fields specific to your implementation of BroadLeafCommerce you should extend this class and
 * add your fields. If you need to make significant changes to the class then you should implement your own version of
 * {@link org.broadleafcommerce.core.catalog.domain.Category}.<br>
 * <br>
 * This implementation uses a Hibernate implementation of JPA configured through annotations. The Entity references the
 * following tables: BLC_CATEGORY_PRODUCT_XREF,</p>
 *
 * @see      {@link org.broadleafcommerce.core.catalog.domain.Category},
 *           {@link org.broadleafcommerce.core.catalog.domain.ProductImpl}
 * @author   btaylor
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(excludeFromPolymorphism = false)
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Polymorphism(type = PolymorphismType.EXPLICIT)
@Table(name = "BLC_CATEGORY_PRODUCT_XREF")
public class CategoryProductXrefImpl implements CategoryProductXref {
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @EmbeddedId CategoryProductXrefPK categoryProductXref = new CategoryProductXrefPK();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public CategoryProductXrefPK getCategoryProductXref() {
    return categoryProductXref;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  categoryProductXref  DOCUMENT ME!
   */
  public void setCategoryProductXref(CategoryProductXrefPK categoryProductXref) {
    this.categoryProductXref = categoryProductXref;
  }

  /** The display order. */
  @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
  @Column(name = "DISPLAY_ORDER")
  protected Long displayOrder;

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.catalog.domain.CategoryProductXref#getDisplayOrder()
   */
  @Override public Long getDisplayOrder() {
    return displayOrder;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.catalog.domain.CategoryProductXref#setDisplayOrder(java.lang.Integer)
   */
  @Override public void setDisplayOrder(Long displayOrder) {
    this.displayOrder = displayOrder;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @see     CategoryProductXrefImpl.CategoryProductXrefPK#getCategory()
   */
  @Override public Category getCategory() {
    return categoryProductXref.getCategory();
  }

  /**
   *
   * DOCUMENT ME!
   *
   * @param  category  DOCUMENT ME!
   *
   * @see    CategoryProductXrefImpl.CategoryProductXrefPK#setCategory(Category)
   */
  @Override public void setCategory(Category category) {
    categoryProductXref.setCategory(category);
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @see     CategoryProductXrefImpl.CategoryProductXrefPK#getProduct()
   */
  @Override public Product getProduct() {
    return categoryProductXref.getProduct();
  }

  /**
   *
   * DOCUMENT ME!
   *
   * @param  product  DOCUMENT ME!
   *
   * @see    CategoryProductXrefImpl.CategoryProductXrefPK#setProduct(Product)
   */
  @Override public void setProduct(Product product) {
    categoryProductXref.setProduct(product);
  }

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (o instanceof CategoryProductXrefImpl) {
      CategoryProductXrefImpl that = (CategoryProductXrefImpl) o;

      return new EqualsBuilder().append(categoryProductXref, that.categoryProductXref).build();
    }

    return false;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (categoryProductXref != null) ? categoryProductXref.hashCode() : 0;

    return result;
  }

} // end class CategoryProductXrefImpl
