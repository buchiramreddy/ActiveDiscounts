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

package org.broadleafcommerce.core.order.service.type;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * An extendible enumeration of order status types.
 *
 * <ul>
 *   <li><b>NAMED</b> - Represents a wishlist</li>
 *   <li><b>IN_PROCESS</b> - Represents a cart (non-submitted {@link org.broadleafcommerce.core.order.domain.Order}s)
 *   </li>
 *   <li><b>SUBMITTED</b> - Used to represent a completed {@link org.broadleafcommerce.core.order.domain.Order}. Note
 *     that this also means that the {@link org.broadleafcommerce.core.order.domain.Order} should have its
 *     {@link org.broadleafcommerce.core.order.domain.Order#getOrderNumber} set</li>
 * </ul>
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class OrderStatus implements Serializable, BroadleafEnumerationType {
  private static final long serialVersionUID = 1L;

  private static final Map<String, OrderStatus> TYPES = new LinkedHashMap<String, OrderStatus>();

  /** DOCUMENT ME! */
  public static final OrderStatus NAMED      = new OrderStatus("NAMED", "Named");

  /** DOCUMENT ME! */
  public static final OrderStatus IN_PROCESS = new OrderStatus("IN_PROCESS", "In Process");

  /** DOCUMENT ME! */
  public static final OrderStatus SUBMITTED = new OrderStatus("SUBMITTED", "Submitted");

  /** DOCUMENT ME! */
  public static final OrderStatus CANCELLED = new OrderStatus("CANCELLED", "Cancelled");

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static OrderStatus getInstance(final String type) {
    return TYPES.get(type);
  }

  private String type;
  private String friendlyType;

  /**
   * Creates a new OrderStatus object.
   */
  public OrderStatus() {
    // do nothing
  }

  /**
   * Creates a new OrderStatus object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public OrderStatus(final String type, final String friendlyType) {
    this.friendlyType = friendlyType;
    setType(type);
  }

  /**
   * @see  org.broadleafcommerce.common.BroadleafEnumerationType#getType()
   */
  @Override public String getType() {
    return type;
  }

  /**
   * @see  org.broadleafcommerce.common.BroadleafEnumerationType#getFriendlyType()
   */
  @Override public String getFriendlyType() {
    return friendlyType;
  }

  private void setType(final String type) {
    this.type = type;

    if (!TYPES.containsKey(type)) {
      TYPES.put(type, this);
    }
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((type == null) ? 0 : type.hashCode());

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

    OrderStatus other = (OrderStatus) obj;

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }

    return true;
  } // end method equals

} // end class OrderStatus
