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


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@Embeddable public class CategoryXrefPK implements Serializable {
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @JoinColumn(name = "CATEGORY_ID")
  @ManyToOne(
    targetEntity = CategoryImpl.class,
    optional     = false
  )
  protected Category category = new CategoryImpl();

  /** DOCUMENT ME! */
  @JoinColumn(name = "SUB_CATEGORY_ID")
  @ManyToOne(
    targetEntity = CategoryImpl.class,
    optional     = false
  )
  protected Category subCategory = new CategoryImpl();

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
  public void setCategory(final Category category) {
    this.category = category;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Category getSubCategory() {
    return subCategory;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  subCategory  DOCUMENT ME!
   */
  public void setSubCategory(final Category subCategory) {
    this.subCategory = subCategory;
  }

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(final Object obj) {
    if (obj == null) {
      return false;
    } else if (!(obj instanceof CategoryXrefPK)) {
      return false;
    }

    return category.getId().equals(((CategoryXrefPK) obj).getCategory().getId())
      && subCategory.getId().equals(((CategoryXrefPK) obj).getSubCategory().getId());
  }


  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    return category.hashCode() + subCategory.hashCode();
  }
} // end class CategoryXrefPK
