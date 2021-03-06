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
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * The Class SkuAttributeImpl is the default implementation of
 * {@link org.broadleafcommerce.core.catalog.domain.SkuAttribute}. A SKU Attribute is a designator on a SKU that
 * differentiates it from other similar SKUs (for example: Blue attribute for hat). If you want to add fields specific
 * to your implementation of BroadLeafCommerce you should extend this class and add your fields. If you need to make
 * significant changes to the SkuImpl then you should implement your own version of
 * {@link org.broadleafcommerce.core.catalog.domain.Sku}.<br>
 * <br>
 * This implementation uses a Hibernate implementation of JPA configured through annotations. The Entity references the
 * following tables: BLC_SKU_ATTRIBUTES,
 *
 * @see      {@link org.broadleafcommerce.core.catalog.domain.SkuAttribute},
 *           {@link org.broadleafcommerce.core.catalog.domain.SkuImpl}
 * @author   btaylor
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_SKU_ATTRIBUTE")
public class SkuAttributeImpl implements SkuAttribute {
  /** The Constant serialVersionUID. */
  private static final long serialVersionUID = 1L;

  /** The id. */
  @Column(name = "SKU_ATTR_ID")
  @GeneratedValue(generator = "SkuAttributeId")
  @GenericGenerator(
    name       = "SkuAttributeId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "SkuAttributeImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.catalog.domain.SkuAttributeImpl"
      )
    }
  )
  @Id protected Long id;

  /** The name. */
  @AdminPresentation(excluded = true)
  @Column(
    name     = "NAME",
    nullable = false
  )
  @Index(
    name        = "SKUATTR_NAME_INDEX",
    columnNames = { "NAME" }
  )
  protected String name;

  /** The value. */
  @AdminPresentation(
    friendlyName = "SkuAttributeImpl_Attribute_Value",
    order        = 2,
    group        = "SkuAttributeImpl_Description",
    prominent    = true
  )
  @Column(
    name     = "VALUE",
    nullable = false
  )
  protected String value;

  /** The searchable. */
  @AdminPresentation(excluded = true)
  @Column(name = "SEARCHABLE")
  protected Boolean searchable = false;

  /** The sku. */
  @Index(
    name        = "SKUATTR_SKU_INDEX",
    columnNames = { "SKU_ID" }
  )
  @JoinColumn(name = "SKU_ID")
  @ManyToOne(
    targetEntity = SkuImpl.class,
    optional     = false
  )
  protected Sku sku;

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.catalog.domain.SkuAttribute#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.catalog.domain.SkuAttribute#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.catalog.domain.SkuAttribute#getValue()
   */
  @Override public String getValue() {
    return value;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.catalog.domain.SkuAttribute#setValue(java.lang.String)
   */
  @Override public void setValue(String value) {
    this.value = value;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.catalog.domain.SkuAttribute#getSearchable()
   */
  @Override public Boolean getSearchable() {
    if (searchable == null) {
      return Boolean.FALSE;
    } else {
      return searchable;
    }
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.catalog.domain.SkuAttribute#setSearchable(java.lang.Boolean)
   */
  @Override public void setSearchable(Boolean searchable) {
    this.searchable = searchable;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.catalog.domain.SkuAttribute#getName()
   */
  @Override public String getName() {
    return name;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.catalog.domain.SkuAttribute#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  /* (non-Javadoc)
   * @see java.lang.Object#toString()
   */
  @Override public String toString() {
    return value;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.catalog.domain.SkuAttribute#getSku()
   */
  @Override public Sku getSku() {
    return sku;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.catalog.domain.SkuAttribute#setSku(org.broadleafcommerce.core.catalog.domain.Sku)
   */
  @Override public void setSku(Sku sku) {
    this.sku = sku;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((name == null) ? 0 : name.hashCode());
    result = (prime * result) + ((sku == null) ? 0 : sku.hashCode());
    result = (prime * result) + ((value == null) ? 0 : value.hashCode());

    return result;
  }

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

    SkuAttributeImpl other = (SkuAttributeImpl) obj;

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

    if (sku == null) {
      if (other.sku != null) {
        return false;
      }
    } else if (!sku.equals(other.sku)) {
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

} // end class SkuAttributeImpl
