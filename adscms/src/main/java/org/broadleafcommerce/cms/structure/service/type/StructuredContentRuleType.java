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

package org.broadleafcommerce.cms.structure.service.type;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * An extendible enumeration of offer rule types.<BR>
 * REQUEST - indicates a rule based on the incoming http request.<BR>
 * TIME - indicates a rule based on {@link org.broadleafcommerce.common.TimeDTO time}<br>
 * PRODUCT - indicates a rule based on {@link org.broadleafcommerce.core.catalog.domain.Product product} CUSTOMER -
 * indicates a rule based on {@link org.broadleafcommerce.profile.core.domain}
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class StructuredContentRuleType implements Serializable, BroadleafEnumerationType {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, StructuredContentRuleType> TYPES =
    new LinkedHashMap<String, StructuredContentRuleType>();

  /** DOCUMENT ME! */
  public static final StructuredContentRuleType REQUEST  = new StructuredContentRuleType("REQUEST", "Request");

  /** DOCUMENT ME! */
  public static final StructuredContentRuleType TIME     = new StructuredContentRuleType("TIME", "Time");

  /** DOCUMENT ME! */
  public static final StructuredContentRuleType PRODUCT  = new StructuredContentRuleType("PRODUCT", "Product");

  /** DOCUMENT ME! */
  public static final StructuredContentRuleType CUSTOMER = new StructuredContentRuleType("CUSTOMER", "Customer");

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String friendlyType;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new StructuredContentRuleType object.
   */
  public StructuredContentRuleType() {
    // do nothing
  }

  /**
   * Initialize the type and friendlyType.
   *
   * @param  type          <code>type</code>
   * @param  friendlyType  <code>friendlyType</code>
   */
  public StructuredContentRuleType(final String type, final String friendlyType) {
    this.friendlyType = friendlyType;
    setType(type);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Allows translation from the passed in String to a <code>StructuredContentRuleType.</code>
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  The matching rule type
   */
  public static StructuredContentRuleType getInstance(final String type) {
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

    StructuredContentRuleType other = (StructuredContentRuleType) obj;

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
   * Gets the name of the type.
   *
   * @return  gets the name of the type.
   */
  @Override public String getFriendlyType() {
    return friendlyType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the type.
   *
   * @return  gets the type.
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
   * Sets the type.
   *
   * @param  type  DOCUMENT ME!
   */
  public void setType(final String type) {
    this.type = type;

    if (!TYPES.containsKey(type)) {
      TYPES.put(type, this);
    }
  }

} // end class StructuredContentRuleType
