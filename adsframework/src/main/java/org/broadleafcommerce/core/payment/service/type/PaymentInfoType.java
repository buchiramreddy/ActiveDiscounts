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

package org.broadleafcommerce.core.payment.service.type;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * An extendible enumeration of payment info types.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class PaymentInfoType implements Serializable, BroadleafEnumerationType {
  private static final long serialVersionUID = 1L;

  private static final Map<String, PaymentInfoType> TYPES = new LinkedHashMap<String, PaymentInfoType>();

  /** DOCUMENT ME! */
  public static final PaymentInfoType GIFT_CARD        = new PaymentInfoType("GIFT_CARD", "Gift Card");

  /** DOCUMENT ME! */
  public static final PaymentInfoType CREDIT_CARD      = new PaymentInfoType("CREDIT_CARD", "Credit Card");

  /** DOCUMENT ME! */
  public static final PaymentInfoType BANK_ACCOUNT     = new PaymentInfoType("BANK_ACCOUNT", "Bank Account");

  /** DOCUMENT ME! */
  public static final PaymentInfoType PAYPAL           = new PaymentInfoType("PAYPAL", "PayPal");

  /** DOCUMENT ME! */
  public static final PaymentInfoType CHECK            = new PaymentInfoType("CHECK", "Check");

  /** DOCUMENT ME! */
  public static final PaymentInfoType ELECTRONIC_CHECK = new PaymentInfoType("ELECTRONIC_CHECK", "Electronic Check");

  /** DOCUMENT ME! */
  public static final PaymentInfoType WIRE            = new PaymentInfoType("WIRE", "Wire Transfer");

  /** DOCUMENT ME! */
  public static final PaymentInfoType MONEY_ORDER     = new PaymentInfoType("MONEY_ORDER", "Money Order");

  /** DOCUMENT ME! */
  public static final PaymentInfoType CUSTOMER_CREDIT = new PaymentInfoType("CUSTOMER_CREDIT", "Customer Credit");

  /** DOCUMENT ME! */
  public static final PaymentInfoType ACCOUNT = new PaymentInfoType("ACCOUNT", "Account");

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static PaymentInfoType getInstance(final String type) {
    return TYPES.get(type);
  }

  private String type;
  private String friendlyType;

  /**
   * Creates a new PaymentInfoType object.
   */
  public PaymentInfoType() {
    // do nothing
  }

  /**
   * Creates a new PaymentInfoType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public PaymentInfoType(final String type, final String friendlyType) {
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

    PaymentInfoType other = (PaymentInfoType) obj;

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }

    return true;
  } // end method equals
} // end class PaymentInfoType
