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

import org.broadleafcommerce.profile.core.domain.Address;
import org.broadleafcommerce.profile.core.domain.AddressImpl;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_ORDER_MULTISHIP_OPTION")
public class OrderMultishipOptionImpl implements OrderMultishipOption {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "ORDER_MULTISHIP_OPTION_ID")
  @GeneratedValue(generator = "OrderMultishipOptionId")
  @GenericGenerator(
    name       = "OrderMultishipOptionId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "OrderMultishipOptionImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.order.domain.OrderMultishipOptionImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Index(
    name        = "MULTISHIP_OPTION_ORDER_INDEX",
    columnNames = { "ORDER_ID" }
  )
  @JoinColumn(name = "ORDER_ID")
  @ManyToOne(targetEntity = OrderImpl.class)
  protected Order order;

  /** DOCUMENT ME! */
  @JoinColumn(name = "ORDER_ITEM_ID")
  @ManyToOne(targetEntity = OrderItemImpl.class)
  protected OrderItem orderItem;

  /** DOCUMENT ME! */
  @JoinColumn(name = "ADDRESS_ID")
  @ManyToOne(targetEntity = AddressImpl.class)
  protected Address address;

  /** DOCUMENT ME! */
  @JoinColumn(name = "FULFILLMENT_OPTION_ID")
  @ManyToOne(targetEntity = FulfillmentOptionImpl.class)
  protected FulfillmentOption fulfillmentOption;

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderMultishipOption#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderMultishipOption#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderMultishipOption#getOrder()
   */
  @Override public Order getOrder() {
    return order;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderMultishipOption#setOrder(org.broadleafcommerce.core.order.domain.Order)
   */
  @Override public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderMultishipOption#getOrderItem()
   */
  @Override public OrderItem getOrderItem() {
    return orderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderMultishipOption#setOrderItem(org.broadleafcommerce.core.order.domain.OrderItem)
   */
  @Override public void setOrderItem(OrderItem orderItem) {
    this.orderItem = orderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderMultishipOption#getAddress()
   */
  @Override public Address getAddress() {
    return address;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderMultishipOption#setAddress(org.broadleafcommerce.profile.core.domain.Address)
   */
  @Override public void setAddress(Address address) {
    this.address = address;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderMultishipOption#getFulfillmentOption()
   */
  @Override public FulfillmentOption getFulfillmentOption() {
    return fulfillmentOption;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderMultishipOption#setFulfillmentOption(org.broadleafcommerce.core.order.domain.FulfillmentOption)
   */
  @Override public void setFulfillmentOption(FulfillmentOption fulfillmentOption) {
    this.fulfillmentOption = fulfillmentOption;
  }

} // end class OrderMultishipOptionImpl
