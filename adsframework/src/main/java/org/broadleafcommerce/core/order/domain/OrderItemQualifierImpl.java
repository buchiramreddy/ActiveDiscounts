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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferImpl;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
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
@Table(name = "BLC_ITEM_OFFER_QUALIFIER")
public class OrderItemQualifierImpl implements OrderItemQualifier {
  /** DOCUMENT ME! */
  public static final Log  LOG              = LogFactory.getLog(OrderItemQualifierImpl.class);

  /** DOCUMENT ME! */
  public static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "ITEM_OFFER_QUALIFIER_ID")
  @GeneratedValue(generator = "OrderItemQualifierId")
  @GenericGenerator(
    name       = "OrderItemQualifierId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "OrderItemQualifierImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.order.domain.OrderItemQualifierImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @JoinColumn(name = "ORDER_ITEM_ID")
  @ManyToOne(targetEntity = OrderItemImpl.class)
  protected OrderItem orderItem;

  /** DOCUMENT ME! */
  @JoinColumn(name = "OFFER_ID")
  @ManyToOne(
    targetEntity = OfferImpl.class,
    optional     = false
  )
  protected Offer offer;

  /** DOCUMENT ME! */
  @Column(name = "QUANTITY")
  protected Long quantity;

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemQualifier#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemQualifier#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemQualifier#getOrderItem()
   */
  @Override public OrderItem getOrderItem() {
    return orderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemQualifier#setOrderItem(org.broadleafcommerce.core.order.domain.OrderItem)
   */
  @Override public void setOrderItem(OrderItem orderItem) {
    this.orderItem = orderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemQualifier#setOffer(org.broadleafcommerce.core.offer.domain.Offer)
   */
  @Override public void setOffer(Offer offer) {
    this.offer = offer;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemQualifier#getOffer()
   */
  @Override public Offer getOffer() {
    return offer;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemQualifier#setQuantity(java.lang.Long)
   */
  @Override public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.OrderItemQualifier#getQuantity()
   */
  @Override public Long getQuantity() {
    return quantity;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((id == null) ? 0 : id.hashCode());
    result = (prime * result) + ((offer == null) ? 0 : offer.hashCode());
    result = (prime * result) + ((orderItem == null) ? 0 : orderItem.hashCode());
    result = (prime * result) + ((quantity == null) ? 0 : quantity.hashCode());

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

    OrderItemQualifierImpl other = (OrderItemQualifierImpl) obj;

    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }

    if (offer == null) {
      if (other.offer != null) {
        return false;
      }
    } else if (!offer.equals(other.offer)) {
      return false;
    }

    if (orderItem == null) {
      if (other.orderItem != null) {
        return false;
      }
    } else if (!orderItem.equals(other.orderItem)) {
      return false;
    }

    if (quantity == null) {
      if (other.quantity != null) {
        return false;
      }
    } else if (!quantity.equals(other.quantity)) {
      return false;
    }

    return true;
  } // end method equals

} // end class OrderItemQualifierImpl
