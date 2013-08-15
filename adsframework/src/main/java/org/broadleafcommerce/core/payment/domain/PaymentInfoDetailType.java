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

package org.broadleafcommerce.core.payment.domain;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * The PaymentInfoDetailType is used to represent in Broadleaf the the type of action used by the payment provider.
 *
 * <p>The following types are used for transactions.</p>
 *
 * <p>CAPTURE - Funds have been charged, submitted or debited. REFUND - Funds have been refunded or credited.
 * REVERSE_AUTH - Funds have been reverse authorized; this concept is used by credit card payment processors where funds
 * are first authorized to later be captured.</p>
 *
 * @author   Jerry Ocanas (jocanas)
 * @version  $Revision$, $Date$
 */
public class PaymentInfoDetailType implements Serializable, BroadleafEnumerationType {
  private static final long serialVersionUID = 1L;

  private static final Map<String, PaymentInfoDetailType> TYPES = new LinkedHashMap<String, PaymentInfoDetailType>();

  /** DOCUMENT ME! */
  public static final PaymentInfoDetailType CAPTURE      = new PaymentInfoDetailType("CAPTURE", "Capture");

  /** DOCUMENT ME! */
  public static final PaymentInfoDetailType REFUND       = new PaymentInfoDetailType("REFUND", "Refund");

  /** DOCUMENT ME! */
  public static final PaymentInfoDetailType REVERSE_AUTH = new PaymentInfoDetailType("REVERSE_AUTH", "Reverse Auth");

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static PaymentInfoDetailType getInstance(final String type) {
    return TYPES.get(type);
  }

  private String type;
  private String friendlyType;

  /**
   * Creates a new PaymentInfoDetailType object.
   */
  public PaymentInfoDetailType() {
    // do nothing
  }

  /**
   * Creates a new PaymentInfoDetailType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public PaymentInfoDetailType(String type, String friendlyType) {
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

    PaymentInfoDetailType other = (PaymentInfoDetailType) obj;

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }

    return true;
  } // end method equals
} // end class PaymentInfoDetailType
