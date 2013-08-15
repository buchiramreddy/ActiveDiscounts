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
 * An extendible enumeration of offer rule types.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class OfferRuleType implements Serializable, BroadleafEnumerationType {
  private static final long serialVersionUID = 1L;

  private static final Map<String, OfferRuleType> TYPES = new LinkedHashMap<String, OfferRuleType>();

  /** DOCUMENT ME! */
  public static final OfferRuleType ORDER             = new OfferRuleType("ORDER", "Order");

  /** DOCUMENT ME! */
  public static final OfferRuleType FULFILLMENT_GROUP = new OfferRuleType("FULFILLMENT_GROUP", "Fulfillment Group");

  /** DOCUMENT ME! */
  public static final OfferRuleType CUSTOMER = new OfferRuleType("CUSTOMER", "Customer");

  /** DOCUMENT ME! */
  public static final OfferRuleType TIME    = new OfferRuleType("TIME", "Time");

  /** DOCUMENT ME! */
  public static final OfferRuleType REQUEST = new OfferRuleType("REQUEST", "Request");

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static OfferRuleType getInstance(final String type) {
    return TYPES.get(type);
  }

  private String type;
  private String friendlyType;

  /**
   * Creates a new OfferRuleType object.
   */
  public OfferRuleType() {
    // do nothing
  }

  /**
   * Creates a new OfferRuleType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public OfferRuleType(final String type, final String friendlyType) {
    this.friendlyType = friendlyType;
    setType(type);
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

    OfferRuleType other = (OfferRuleType) obj;

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }

    return true;
  } // end method equals

} // end class OfferRuleType
