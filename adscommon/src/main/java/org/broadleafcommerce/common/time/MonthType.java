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

package org.broadleafcommerce.common.time;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * An extendible enumeration of container shape types.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class MonthType implements Serializable, BroadleafEnumerationType {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, MonthType> TYPES = new LinkedHashMap<String, MonthType>();

  /** DOCUMENT ME! */
  public static final MonthType JANUARY   = new MonthType("1", "January");

  /** DOCUMENT ME! */
  public static final MonthType FEBRUARY  = new MonthType("2", "February");

  /** DOCUMENT ME! */
  public static final MonthType MARCH     = new MonthType("3", "March");

  /** DOCUMENT ME! */
  public static final MonthType APRIL     = new MonthType("4", "April");

  /** DOCUMENT ME! */
  public static final MonthType MAY       = new MonthType("5", "May");

  /** DOCUMENT ME! */
  public static final MonthType JUNE      = new MonthType("6", "June");

  /** DOCUMENT ME! */
  public static final MonthType JULY      = new MonthType("7", "July");

  /** DOCUMENT ME! */
  public static final MonthType AUGUST    = new MonthType("8", "August");

  /** DOCUMENT ME! */
  public static final MonthType SEPTEMBER = new MonthType("9", "September");

  /** DOCUMENT ME! */
  public static final MonthType OCTOBER  = new MonthType("10", "October");

  /** DOCUMENT ME! */
  public static final MonthType NOVEMBER = new MonthType("11", "November");

  /** DOCUMENT ME! */
  public static final MonthType DECEMBER = new MonthType("12", "December");

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String friendlyType;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new MonthType object.
   */
  public MonthType() {
    // do nothing
  }

  /**
   * Creates a new MonthType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public MonthType(final String type, final String friendlyType) {
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
  public static MonthType getInstance(final String type) {
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

    MonthType other = (MonthType) obj;

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
} // end class MonthType
