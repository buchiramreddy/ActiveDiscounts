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
 * An extendible enumeration of payment transaction types.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class TransactionType implements Serializable, BroadleafEnumerationType {
  private static final long serialVersionUID = 1L;

  private static final Map<String, TransactionType> TYPES = new LinkedHashMap<String, TransactionType>();

  /** DOCUMENT ME! */
  public static final TransactionType AUTHORIZE         = new TransactionType("AUTHORIZE", "Authorize");

  /** DOCUMENT ME! */
  public static final TransactionType DEBIT             = new TransactionType("DEBIT", "Debit");

  /** DOCUMENT ME! */
  public static final TransactionType AUTHORIZEANDDEBIT = new TransactionType("AUTHORIZEANDDEBIT",
      "Authorize and Debit");

  /** DOCUMENT ME! */
  public static final TransactionType CREDIT           = new TransactionType("CREDIT", "Credit");

  /** DOCUMENT ME! */
  public static final TransactionType VOIDPAYMENT      = new TransactionType("VOIDPAYMENT", "Void Payment");

  /** DOCUMENT ME! */
  public static final TransactionType BALANCE          = new TransactionType("BALANCE", "Balance");

  /** DOCUMENT ME! */
  public static final TransactionType REVERSEAUTHORIZE = new TransactionType("REVERSEAUTHORIZE", "Reverse Authorize");

  /** DOCUMENT ME! */
  public static final TransactionType PARTIALPAYMENT = new TransactionType("PARTIALPAYMENT", "Partial Payment");

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static TransactionType getInstance(final String type) {
    return TYPES.get(type);
  }

  private String type;
  private String friendlyType;

  /**
   * Creates a new TransactionType object.
   */
  public TransactionType() {
    // do nothing
  }

  /**
   * Creates a new TransactionType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public TransactionType(final String type, final String friendlyType) {
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

    TransactionType other = (TransactionType) obj;

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }

    return true;
  } // end method equals
} // end class TransactionType
