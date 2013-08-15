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
public class HourOfDayType implements Serializable, BroadleafEnumerationType {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, HourOfDayType> TYPES = new LinkedHashMap<String, HourOfDayType>();

  /** DOCUMENT ME! */
  public static final HourOfDayType ZERO        = new HourOfDayType("0", "00");

  /** DOCUMENT ME! */
  public static final HourOfDayType ONE         = new HourOfDayType("1", "01");

  /** DOCUMENT ME! */
  public static final HourOfDayType TWO         = new HourOfDayType("2", "02");

  /** DOCUMENT ME! */
  public static final HourOfDayType THREE       = new HourOfDayType("3", "03");

  /** DOCUMENT ME! */
  public static final HourOfDayType FOUR        = new HourOfDayType("4", "04");

  /** DOCUMENT ME! */
  public static final HourOfDayType FIVE        = new HourOfDayType("5", "05");

  /** DOCUMENT ME! */
  public static final HourOfDayType SIX         = new HourOfDayType("6", "06");

  /** DOCUMENT ME! */
  public static final HourOfDayType SEVEN       = new HourOfDayType("7", "07");

  /** DOCUMENT ME! */
  public static final HourOfDayType EIGHT       = new HourOfDayType("8", "08");

  /** DOCUMENT ME! */
  public static final HourOfDayType NINE        = new HourOfDayType("9", "09");

  /** DOCUMENT ME! */
  public static final HourOfDayType TEN         = new HourOfDayType("10", "10");

  /** DOCUMENT ME! */
  public static final HourOfDayType ELEVEN      = new HourOfDayType("11", "11");

  /** DOCUMENT ME! */
  public static final HourOfDayType TWELVE      = new HourOfDayType("12", "12");

  /** DOCUMENT ME! */
  public static final HourOfDayType THIRTEEN    = new HourOfDayType("13", "13");

  /** DOCUMENT ME! */
  public static final HourOfDayType FOURTEEN    = new HourOfDayType("14", "14");

  /** DOCUMENT ME! */
  public static final HourOfDayType FIFTEEN     = new HourOfDayType("15", "15");

  /** DOCUMENT ME! */
  public static final HourOfDayType SIXTEEN     = new HourOfDayType("16", "16");

  /** DOCUMENT ME! */
  public static final HourOfDayType SEVENTEEN   = new HourOfDayType("17", "17");

  /** DOCUMENT ME! */
  public static final HourOfDayType EIGHTEEN    = new HourOfDayType("18", "18");

  /** DOCUMENT ME! */
  public static final HourOfDayType NINETEEN    = new HourOfDayType("19", "19");

  /** DOCUMENT ME! */
  public static final HourOfDayType TWENTY      = new HourOfDayType("20", "20");

  /** DOCUMENT ME! */
  public static final HourOfDayType TWENTYONE   = new HourOfDayType("21", "21");

  /** DOCUMENT ME! */
  public static final HourOfDayType TWNETYTWO   = new HourOfDayType("22", "22");

  /** DOCUMENT ME! */
  public static final HourOfDayType TWENTYTHREE = new HourOfDayType("23", "23");

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String friendlyType;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new HourOfDayType object.
   */
  public HourOfDayType() {
    // do nothing
  }

  /**
   * Creates a new HourOfDayType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public HourOfDayType(final String type, final String friendlyType) {
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
  public static HourOfDayType getInstance(final String type) {
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

    HourOfDayType other = (HourOfDayType) obj;

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
} // end class HourOfDayType
