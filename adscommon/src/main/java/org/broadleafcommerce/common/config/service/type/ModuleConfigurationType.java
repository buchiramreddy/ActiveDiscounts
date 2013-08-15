/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.common.config.service.type;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class ModuleConfigurationType implements BroadleafEnumerationType, Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, ModuleConfigurationType> TYPES =
    new LinkedHashMap<String, ModuleConfigurationType>();

  /** DOCUMENT ME! */
  public static final ModuleConfigurationType FULFILLMENT_PRICING  = new ModuleConfigurationType("FULFILLMENT_PRICING",
      "Fulfillment Pricing Module");

  /** DOCUMENT ME! */
  public static final ModuleConfigurationType TAX_CALCULATION      = new ModuleConfigurationType("TAX_CALCULATION",
      "Tax Calculation Module");

  /** DOCUMENT ME! */
  public static final ModuleConfigurationType ADDRESS_VERIFICATION = new ModuleConfigurationType("ADDRESS_VERIFICATION",
      "Address Verification Module");

  /** DOCUMENT ME! */
  public static final ModuleConfigurationType PAYMENT_PROCESSOR = new ModuleConfigurationType("PAYMENT_PROCESSOR",
      "Payment Processor Module");

  /** DOCUMENT ME! */
  public static final ModuleConfigurationType CDN_PROVIDER = new ModuleConfigurationType("CDN_PROVIDER",
      "Content Delivery Network Module");

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String friendlyType;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new ModuleConfigurationType object.
   */
  public ModuleConfigurationType() {
    // do nothing
  }

  /**
   * Creates a new ModuleConfigurationType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public ModuleConfigurationType(final String type, final String friendlyType) {
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
  public static ModuleConfigurationType getInstance(final String type) {
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

    ModuleConfigurationType other = (ModuleConfigurationType) obj;

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

} // end class ModuleConfigurationType
