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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "baseCategoryAttribute")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_CATEGORY_ATTRIBUTE")
public class CategoryAttributeImpl implements CategoryAttribute {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Index(
    name        = "CATEGORYATTRIBUTE_INDEX",
    columnNames = { "CATEGORY_ID" }
  )
  @JoinColumn(name = "CATEGORY_ID")
  @ManyToOne(
    targetEntity = CategoryImpl.class,
    optional     = false
  )
  protected Category category;

  /** DOCUMENT ME! */
  @Column(name = "CATEGORY_ATTRIBUTE_ID")
  @GeneratedValue(generator = "CategoryAttributeId")
  @GenericGenerator(
    name       = "CategoryAttributeId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "CategoryAttributeImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.catalog.domain.CategoryAttributeImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(
    name     = "NAME",
    nullable = false
  )
  @Index(
    name        = "CATEGORYATTRIBUTE_NAME_INDEX",
    columnNames = { "NAME" }
  )
  protected String name;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Column(name = "SEARCHABLE")
  protected Boolean searchable = false;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ProductAttributeImpl_Attribute_Value",
    order        = 2,
    group        = "ProductAttributeImpl_Description",
    prominent    = true
  )
  @Column(name = "VALUE")
  protected String value;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    CategoryAttributeImpl other = (CategoryAttributeImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }

    if (category == null) {
      if (other.category != null) {
        return false;
      }
    } else if (!category.equals(other.category)) {
      return false;
    }

    if (value == null) {
      if (other.value != null) {
        return false;
      }
    } else if (!value.equals(other.value)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.CategoryAttribute#getCategory()
   */
  @Override public Category getCategory() {
    return category;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.CategoryAttribute#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.CategoryAttribute#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.value.Searchable#getSearchable()
   */
  @Override public Boolean getSearchable() {
    if (searchable == null) {
      return Boolean.FALSE;
    } else {
      return searchable;
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.CategoryAttribute#getValue()
   */
  @Override public String getValue() {
    return value;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((name == null) ? 0 : name.hashCode());
    result = (prime * result) + ((category == null) ? 0 : category.hashCode());
    result = (prime * result) + ((value == null) ? 0 : value.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.CategoryAttribute#setCategory(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public void setCategory(Category category) {
    this.category = category;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.CategoryAttribute#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.CategoryAttribute#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.value.Searchable#setSearchable(java.lang.Boolean)
   */
  @Override public void setSearchable(Boolean searchable) {
    this.searchable = searchable;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.CategoryAttribute#setValue(java.lang.String)
   */
  @Override public void setValue(String value) {
    this.value = value;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    return value;
  }

} // end class CategoryAttributeImpl
