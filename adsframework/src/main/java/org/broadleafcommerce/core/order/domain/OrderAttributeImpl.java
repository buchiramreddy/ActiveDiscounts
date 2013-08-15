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

package org.broadleafcommerce.core.order.domain;

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
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * The Class OrderAttributeImpl.
 *
 * @see      org.broadleafcommerce.core.order.domain.OrderAttribute
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "OrderAttributeImpl_baseProductAttribute")
@AdminPresentationMergeOverrides(
  {
    @AdminPresentationMergeOverride(
      name = "",
      mergeEntries =
        @AdminPresentationMergeEntry(
          propertyType         = PropertyType.AdminPresentation.READONLY,
          booleanOverrideValue = true
        )
    )
  }
)
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ORDER_ATTRIBUTE")
public class OrderAttributeImpl implements OrderAttribute {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "ORDER_ATTRIBUTE_ID")
  @GeneratedValue(generator = "OrderAttributeId")
  @GenericGenerator(
    name       = "OrderAttributeId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "OrderAttributeImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.catalog.domain.OrderAttributeImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderAttributeImpl_Attribute_Name",
    order        = 1000,
    prominent    = true
  )
  @Column(
    name     = "NAME",
    nullable = false
  )
  protected String name;

  /** The value. */
  @AdminPresentation(
    friendlyName = "OrderAttributeImpl_Attribute_Value",
    order        = 2000,
    prominent    = true
  )
  @Column(name = "VALUE")
  protected String value;

  /** DOCUMENT ME! */
  @JoinColumn(name = "ORDER_ID")
  @ManyToOne(
    targetEntity = OrderImpl.class,
    optional     = false
  )
  protected Order order;

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderAttribute#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderAttribute#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderAttribute#getValue()
   */
  @Override public String getValue() {
    return value;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderAttribute#setValue(java.lang.String)
   */
  @Override public void setValue(String value) {
    this.value = value;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderAttribute#getName()
   */
  @Override public String getName() {
    return name;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderAttribute#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  /**
   * @see  java.lang.Object#toString()
   */
  @Override public String toString() {
    return value;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderAttribute#getOrder()
   */
  @Override public Order getOrder() {
    return order;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderAttribute#setOrder(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    return value.hashCode();
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

    if (value == null) {
      return false;
    }

    return value.equals(((OrderAttribute) obj).getValue());
  }
} // end class OrderAttributeImpl
