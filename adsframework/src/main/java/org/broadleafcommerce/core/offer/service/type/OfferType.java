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

package org.broadleafcommerce.core.offer.service.type;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * An extendible enumeration of offer types.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class OfferType implements Serializable, BroadleafEnumerationType, Comparable<OfferType> {
  private static final long serialVersionUID = 1L;

  private static final Map<String, OfferType> TYPES             = new LinkedHashMap<String, OfferType>();

  /** DOCUMENT ME! */
  public static final OfferType               ORDER_ITEM        = new OfferType("ORDER_ITEM", "Order Item", 1000);

  /** DOCUMENT ME! */
  public static final OfferType               ORDER             = new OfferType("ORDER", "Order", 2000);

  /** DOCUMENT ME! */
  public static final OfferType               FULFILLMENT_GROUP = new OfferType("FULFILLMENT_GROUP",
      "Fulfillment Group", 3000);


  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static OfferType getInstance(final String type) {
    return TYPES.get(type);
  }

  private String type;
  private String friendlyType;
  private int    order;

  /**
   * Creates a new OfferType object.
   */
  public OfferType() {
    // do nothing
  }

  /**
   * Creates a new OfferType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   * @param  order         DOCUMENT ME!
   */
  public OfferType(final String type, final String friendlyType, int order) {
    this.friendlyType = friendlyType;
    setType(type);
    setOrder(order);
  }

  /**
   * DOCUMENT ME!
   *
   * @param  type  DOCUMENT ME!
   */
  public void setType(final String type) {
    this.type = type;

    if (!TYPES.containsKey(type)) {
      TYPES.put(type, this);
    }
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

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getOrder() {
    return order;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  public void setOrder(int order) {
    this.order = order;
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

    OfferType other = (OfferType) obj;

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }

    return true;
  } // end method equals

  /**
   * @see  java.lang.Comparable#compareTo(org.broadleafcommerce.core.offer.service.type.OfferType)
   */
  @Override public int compareTo(OfferType arg0) {
    return this.order - arg0.order;
  }

} // end class OfferType
