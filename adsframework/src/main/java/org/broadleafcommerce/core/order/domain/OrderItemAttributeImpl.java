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

import java.lang.reflect.Method;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * Arbitrary attributes to add to an order-item.
 *
 * @see      OrderItemAttribute
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "OrderItemAttributeImpl_baseProductAttribute")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ORDER_ITEM_ATTRIBUTE")
public class OrderItemAttributeImpl implements OrderItemAttribute {
  /** DOCUMENT ME! */
  public static final Log   LOG              = LogFactory.getLog(OrderItemAttributeImpl.class);
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "ORDER_ITEM_ATTRIBUTE_ID")
  @GeneratedValue(generator = "OrderItemAttributeId")
  @GenericGenerator(
    name       = "OrderItemAttributeId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "OrderItemAttributeImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.catalog.domain.OrderItemAttributeImpl"
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
  protected String name;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "OrderItemAttributeImpl_Attribute_Value",
    order        = 2,
    group        = "OrderItemAttributeImpl_Description",
    prominent    = true
  )
  @Column(
    name     = "VALUE",
    nullable = false
  )
  protected String value;

  /** DOCUMENT ME! */
  @JoinColumn(name = "ORDER_ITEM_ID")
  @ManyToOne(
    targetEntity = OrderItemImpl.class,
    optional     = false
  )
  protected OrderItem orderItem;

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemAttribute#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemAttribute#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.common.value.ValueAssignable#getValue()
   */
  @Override public String getValue() {
    return value;
  }

  /**
   * @see  org.broadleafcommerce.common.value.ValueAssignable#setValue(java.lang.String)
   */
  @Override public void setValue(String value) {
    this.value = value;
  }

  /**
   * @see  org.broadleafcommerce.common.value.ValueAssignable#getName()
   */
  @Override public String getName() {
    return name;
  }

  /**
   * @see  org.broadleafcommerce.common.value.ValueAssignable#setName(java.lang.String)
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
   * @see  org.broadleafcommerce.core.order.domain.OrderItemAttribute#getOrderItem()
   */
  @Override public OrderItem getOrderItem() {
    return orderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemAttribute#setOrderItem(org.broadleafcommerce.core.order.domain.OrderItem)
   */
  @Override public void setOrderItem(OrderItem orderItem) {
    this.orderItem = orderItem;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   itemAttribute  DOCUMENT ME!
   *
   * @throws  CloneNotSupportedException  DOCUMENT ME!
   * @throws  SecurityException           DOCUMENT ME!
   * @throws  NoSuchMethodException       DOCUMENT ME!
   */
  public void checkCloneable(OrderItemAttribute itemAttribute) throws CloneNotSupportedException, SecurityException,
    NoSuchMethodException {
    Method cloneMethod = itemAttribute.getClass().getMethod("clone", new Class[] {});

    if (cloneMethod.getDeclaringClass().getName().startsWith("org.broadleafcommerce")
          && !itemAttribute.getClass().getName().startsWith("org.broadleafcommerce")) {
      // subclass is not implementing the clone method
      throw new CloneNotSupportedException(
        "Custom extensions and implementations should implement clone in order to guarantee split and merge operations are performed accurately");
    }
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemAttribute#clone()
   */
  @Override public OrderItemAttribute clone() {
    // instantiate from the fully qualified name via reflection
    OrderItemAttribute itemAttribute;

    try {
      itemAttribute = (OrderItemAttribute) Class.forName(this.getClass().getName()).newInstance();

      try {
        checkCloneable(itemAttribute);
      } catch (CloneNotSupportedException e) {
        LOG.warn("Clone implementation missing in inheritance hierarchy outside of Broadleaf: "
          + itemAttribute.getClass().getName(), e);
      }

      itemAttribute.setName(name);
      itemAttribute.setOrderItem(orderItem);
      itemAttribute.setValue(value);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return itemAttribute;
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
} // end class OrderItemAttributeImpl
