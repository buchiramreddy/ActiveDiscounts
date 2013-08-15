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
 * An extendible enumeration of delivery types.
 *
 * <p>Enumeration of how the offer should be applied. AUTOMATIC - will be applied to everyone's order MANUAL - offer is
 * manually assigned to a Customer by an administrator CODE - a offer code must be supplied in order to receive this
 * offer</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class OfferDeliveryType implements Serializable, BroadleafEnumerationType, Comparable<OfferDeliveryType> {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, OfferDeliveryType> TYPES = new LinkedHashMap<String, OfferDeliveryType>();

  /** DOCUMENT ME! */
  public static final OfferDeliveryType AUTOMATIC = new OfferDeliveryType("AUTOMATIC", "Automatically", 1000);

  /** DOCUMENT ME! */
  public static final OfferDeliveryType CODE   = new OfferDeliveryType("CODE", "Using Shared Code", 2000);

  /** DOCUMENT ME! */
  public static final OfferDeliveryType MANUAL = new OfferDeliveryType("MANUAL", "Via Application or Shared Code",
      3000);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String friendlyType;
  private int    order;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new OfferDeliveryType object.
   */
  public OfferDeliveryType() {
    // do nothing
  }

  /**
   * Creates a new OfferDeliveryType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   * @param  order         DOCUMENT ME!
   */
  public OfferDeliveryType(final String type, final String friendlyType, int order) {
    this.friendlyType = friendlyType;
    setType(type);
    setOrder(order);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static OfferDeliveryType getInstance(final String type) {
    return TYPES.get(type);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Comparable#compareTo(org.broadleafcommerce.core.offer.service.type.OfferDeliveryType)
   */
  @Override public int compareTo(OfferDeliveryType arg0) {
    return this.order - arg0.order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

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

    OfferDeliveryType other = (OfferDeliveryType) obj;

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.BroadleafEnumerationType#getFriendlyType()
   */
  @Override public String getFriendlyType() {
    return friendlyType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getOrder() {
    return order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.BroadleafEnumerationType#getType()
   */
  @Override public String getType() {
    return type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((type == null) ? 0 : type.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  public void setOrder(int order) {
    this.order = order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

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

} // end class OfferDeliveryType
