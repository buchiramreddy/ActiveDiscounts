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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "GiftWrapOrderItemImpl_giftWrapOrderItem")
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_GIFTWRAP_ORDER_ITEM")
public class GiftWrapOrderItemImpl extends DiscreteOrderItemImpl implements GiftWrapOrderItem {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    friendlyName = "OrderItemImpl_Price_Details",
    tab          = OrderItemImpl.Presentation.Tab.Name.Advanced,
    tabOrder     = OrderItemImpl.Presentation.Tab.Order.Advanced
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
    region = "blOrderElements"
  )
  @OneToMany(
    fetch        = FetchType.LAZY,
    mappedBy     = "giftWrapOrderItem",
    targetEntity = OrderItemImpl.class,
    cascade      = { CascadeType.MERGE, CascadeType.PERSIST }
  )
  protected List<OrderItem> wrappedItems = new ArrayList<OrderItem>();

  /**
   * @see  org.broadleafcommerce.core.order.domain.GiftWrapOrderItem#getWrappedItems()
   */
  @Override public List<OrderItem> getWrappedItems() {
    return wrappedItems;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.GiftWrapOrderItem#setWrappedItems(java.util.List)
   */
  @Override public void setWrappedItems(List<OrderItem> wrappedItems) {
    this.wrappedItems = wrappedItems;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItemImpl#clone()
   */
  @Override public OrderItem clone() {
    GiftWrapOrderItem orderItem = (GiftWrapOrderItem) super.clone();

    if (wrappedItems != null) {
      orderItem.getWrappedItems().addAll(wrappedItems);
    }

    return orderItem;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = super.hashCode();
    int       result = super.hashCode();
    result = (prime * result) + ((wrappedItems == null) ? 0 : wrappedItems.hashCode());

    return result;
  }

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!super.equals(obj)) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    GiftWrapOrderItemImpl other = (GiftWrapOrderItemImpl) obj;

    if (!super.equals(obj)) {
      return false;
    }

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (wrappedItems == null) {
      if (other.wrappedItems != null) {
        return false;
      }
    } else if (!wrappedItems.equals(other.wrappedItems)) {
      return false;
    }

    return true;
  } // end method equals
} // end class GiftWrapOrderItemImpl
