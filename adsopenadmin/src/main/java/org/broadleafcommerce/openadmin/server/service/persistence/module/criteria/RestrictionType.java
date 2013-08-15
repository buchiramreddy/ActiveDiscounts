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

package org.broadleafcommerce.openadmin.server.service.persistence.module.criteria;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * An extendible enumeration of service status types.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class RestrictionType implements Serializable, BroadleafEnumerationType {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, RestrictionType> TYPES = new LinkedHashMap<String, RestrictionType>();

  /** DOCUMENT ME! */
  public static final RestrictionType STRING_LIKE           = new RestrictionType("STRING_LIKE", "STRING_LIKE");

  /** DOCUMENT ME! */
  public static final RestrictionType BOOLEAN               = new RestrictionType("BOOLEAN", "BOOLEAN");

  /** DOCUMENT ME! */
  public static final RestrictionType CHARACTER             = new RestrictionType("CHARACTER", "CHARACTER");

  /** DOCUMENT ME! */
  public static final RestrictionType DATE                  = new RestrictionType("DATE", "DATE");

  /** DOCUMENT ME! */
  public static final RestrictionType DECIMAL               = new RestrictionType("DECIMAL", "DECIMAL");

  /** DOCUMENT ME! */
  public static final RestrictionType LONG                  = new RestrictionType("LONG", "LONG");

  /** DOCUMENT ME! */
  public static final RestrictionType COLLECTION_SIZE_EQUAL = new RestrictionType("COLLECTION_SIZE_EQUAL",
      "COLLECTION_SIZE_EQUAL");

  /** DOCUMENT ME! */
  public static final RestrictionType IS_NULL_LONG = new RestrictionType("IS_NULL_LONG", "IS_NULL_LONG");

  /** DOCUMENT ME! */
  public static final RestrictionType STRING_EQUAL = new RestrictionType("STRING_EQUAL", "STRING_EQUAL");

  /** DOCUMENT ME! */
  public static final RestrictionType LONG_EQUAL = new RestrictionType("LONG_EQUAL", "LONG_EQUAL");

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String friendlyType;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new RestrictionType object.
   */
  public RestrictionType() {
    // do nothing
  }

  /**
   * Creates a new RestrictionType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public RestrictionType(final String type, final String friendlyType) {
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
  public static RestrictionType getInstance(final String type) {
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

    RestrictionType other = (RestrictionType) obj;

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
    } else {
      throw new RuntimeException("Cannot add the type: (" + type + "). It already exists as a type via "
        + getInstance(type).getClass().getName());
    }
  }
} // end class RestrictionType
