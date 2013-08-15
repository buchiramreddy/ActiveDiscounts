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

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Polymorphism;
import org.hibernate.annotations.PolymorphismType;


/**
 * The Class CategoryXrefImpl is for testing purposes only. It helps autogenerate the cross reference table properly
 * with the DISPLY_ORDER column
 *
 * @author   krosenberg
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
@Table(name = "BLC_CATEGORY_XREF")
public class CategoryXrefImpl implements CategoryXref {
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The category id. */
  @EmbeddedId CategoryXrefPK categoryXrefPK = new CategoryXrefPK();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public CategoryXrefPK getCategoryXrefPK() {
    return categoryXrefPK;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  categoryXrefPK  DOCUMENT ME!
   */
  public void setCategoryXrefPK(final CategoryXrefPK categoryXrefPK) {
    this.categoryXrefPK = categoryXrefPK;
  }

  /** DOCUMENT ME! */
  @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
  @Column(name = "DISPLAY_ORDER")
  protected Long displayOrder;

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.CategoryXref#getDisplayOrder()
   */
  @Override public Long getDisplayOrder() {
    return displayOrder;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.CategoryXref#setDisplayOrder(java.lang.Long)
   */
  @Override public void setDisplayOrder(final Long displayOrder) {
    this.displayOrder = displayOrder;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @see     CategoryXrefImpl.CategoryXrefPK#getCategory()
   */
  @Override public Category getCategory() {
    return categoryXrefPK.getCategory();
  }

  /**
   *
   * DOCUMENT ME!
   *
   * @param  category  DOCUMENT ME!
   *
   * @see    CategoryXrefImpl.CategoryXrefPK#setCategory(Category)
   */
  @Override public void setCategory(Category category) {
    categoryXrefPK.setCategory(category);
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @see     CategoryXrefImpl.CategoryXrefPK#getSubCategory()
   */
  @Override public Category getSubCategory() {
    return categoryXrefPK.getSubCategory();
  }

  /**
   *
   * DOCUMENT ME!
   *
   * @param  subCategory  DOCUMENT ME!
   *
   * @see    CategoryXrefImpl.CategoryXrefPK#setSubCategory(Category)
   */
  @Override public void setSubCategory(Category subCategory) {
    categoryXrefPK.setSubCategory(subCategory);
  }

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (!(o instanceof CategoryXrefImpl)) {
      return false;
    }

    CategoryXrefImpl that = (CategoryXrefImpl) o;

    if ((categoryXrefPK != null) ? (!categoryXrefPK.equals(that.categoryXrefPK)) : (that.categoryXrefPK != null)) {
      return false;
    }

    if ((displayOrder != null) ? (!displayOrder.equals(that.displayOrder)) : (that.displayOrder != null)) {
      return false;
    }

    return true;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    int result = (categoryXrefPK != null) ? categoryXrefPK.hashCode() : 0;
    result = (31 * result) + ((displayOrder != null) ? displayOrder.hashCode() : 0);

    return result;
  }
} // end class CategoryXrefImpl
