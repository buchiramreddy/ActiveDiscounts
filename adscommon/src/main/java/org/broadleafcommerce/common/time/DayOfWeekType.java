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
public class DayOfWeekType implements Serializable, BroadleafEnumerationType {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, DayOfWeekType> TYPES = new LinkedHashMap<String, DayOfWeekType>();

  /** DOCUMENT ME! */
  public static final DayOfWeekType SUNDAY    = new DayOfWeekType("1", "Sunday");

  /** DOCUMENT ME! */
  public static final DayOfWeekType MONDAY    = new DayOfWeekType("2", "Monday");

  /** DOCUMENT ME! */
  public static final DayOfWeekType TUESDAY   = new DayOfWeekType("3", "Tuesday");

  /** DOCUMENT ME! */
  public static final DayOfWeekType WEDNESDAY = new DayOfWeekType("4", "Wednesday");

  /** DOCUMENT ME! */
  public static final DayOfWeekType THURSDAY = new DayOfWeekType("5", "Thursday");

  /** DOCUMENT ME! */
  public static final DayOfWeekType FRIDAY   = new DayOfWeekType("6", "Friday");

  /** DOCUMENT ME! */
  public static final DayOfWeekType SATURDAY = new DayOfWeekType("7", "Saturday");

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String friendlyType;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new DayOfWeekType object.
   */
  public DayOfWeekType() {
    // do nothing
  }

  /**
   * Creates a new DayOfWeekType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public DayOfWeekType(final String type, final String friendlyType) {
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
  public static DayOfWeekType getInstance(final String type) {
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

    DayOfWeekType other = (DayOfWeekType) obj;

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
} // end class DayOfWeekType
