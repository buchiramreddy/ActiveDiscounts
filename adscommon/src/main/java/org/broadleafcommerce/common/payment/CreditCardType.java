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

package org.broadleafcommerce.common.payment;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * An extendible enumeration of credit card types.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class CreditCardType implements Serializable, BroadleafEnumerationType {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, CreditCardType> TYPES = new LinkedHashMap<String, CreditCardType>();

  /** DOCUMENT ME! */
  public static final CreditCardType MASTERCARD              = new CreditCardType("MASTERCARD", "Master Card");

  /** DOCUMENT ME! */
  public static final CreditCardType VISA                    = new CreditCardType("VISA", "Visa");

  /** DOCUMENT ME! */
  public static final CreditCardType AMEX                    = new CreditCardType("AMEX", "American Express");

  /** DOCUMENT ME! */
  public static final CreditCardType DINERSCLUB_CARTEBLANCHE = new CreditCardType("DINERSCLUB_CARTEBLANCHE",
      "Diner's Club / Carte Blanche");

  /** DOCUMENT ME! */
  public static final CreditCardType DISCOVER = new CreditCardType("DISCOVER", "Discover");

  /** DOCUMENT ME! */
  public static final CreditCardType ENROUTE = new CreditCardType("ENROUTE", "En Route");

  /** DOCUMENT ME! */
  public static final CreditCardType JCB = new CreditCardType("JCB", "JCB");

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String friendlyType;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new CreditCardType object.
   */
  public CreditCardType() {
    // do nothing
  }

  /**
   * Creates a new CreditCardType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public CreditCardType(final String type, final String friendlyType) {
    this.friendlyType = friendlyType;
    setType(type);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static CreditCardType getInstance(final String type) {
    return TYPES.get(type);
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

    CreditCardType other = (CreditCardType) obj;

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

  private void setType(final String type) {
    this.type = type;

    if (!TYPES.containsKey(type)) {
      TYPES.put(type, this);
    }
  }
} // end class CreditCardType
