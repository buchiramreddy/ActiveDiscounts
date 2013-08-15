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
public class DayOfMonthType implements Serializable, BroadleafEnumerationType {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, DayOfMonthType> TYPES = new LinkedHashMap<String, DayOfMonthType>();

  /** DOCUMENT ME! */
  public static final DayOfMonthType ONE         = new DayOfMonthType("1", "01");

  /** DOCUMENT ME! */
  public static final DayOfMonthType TWO         = new DayOfMonthType("2", "02");

  /** DOCUMENT ME! */
  public static final DayOfMonthType THREE       = new DayOfMonthType("3", "03");

  /** DOCUMENT ME! */
  public static final DayOfMonthType FOUR        = new DayOfMonthType("4", "04");

  /** DOCUMENT ME! */
  public static final DayOfMonthType FIVE        = new DayOfMonthType("5", "05");

  /** DOCUMENT ME! */
  public static final DayOfMonthType SIX         = new DayOfMonthType("6", "06");

  /** DOCUMENT ME! */
  public static final DayOfMonthType SEVEN       = new DayOfMonthType("7", "07");

  /** DOCUMENT ME! */
  public static final DayOfMonthType EIGHT       = new DayOfMonthType("8", "08");

  /** DOCUMENT ME! */
  public static final DayOfMonthType NINE        = new DayOfMonthType("9", "09");

  /** DOCUMENT ME! */
  public static final DayOfMonthType TEN         = new DayOfMonthType("10", "10");

  /** DOCUMENT ME! */
  public static final DayOfMonthType ELEVEN      = new DayOfMonthType("11", "11");

  /** DOCUMENT ME! */
  public static final DayOfMonthType TWELVE      = new DayOfMonthType("12", "12");

  /** DOCUMENT ME! */
  public static final DayOfMonthType THIRTEEN    = new DayOfMonthType("13", "13");

  /** DOCUMENT ME! */
  public static final DayOfMonthType FOURTEEN    = new DayOfMonthType("14", "14");

  /** DOCUMENT ME! */
  public static final DayOfMonthType FIFTEEN     = new DayOfMonthType("15", "15");

  /** DOCUMENT ME! */
  public static final DayOfMonthType SIXTEEN     = new DayOfMonthType("16", "16");

  /** DOCUMENT ME! */
  public static final DayOfMonthType SEVENTEEN   = new DayOfMonthType("17", "17");

  /** DOCUMENT ME! */
  public static final DayOfMonthType EIGHTEEN    = new DayOfMonthType("18", "18");

  /** DOCUMENT ME! */
  public static final DayOfMonthType NINETEEN    = new DayOfMonthType("19", "19");

  /** DOCUMENT ME! */
  public static final DayOfMonthType TWENTY      = new DayOfMonthType("20", "20");

  /** DOCUMENT ME! */
  public static final DayOfMonthType TWENTYONE   = new DayOfMonthType("21", "21");

  /** DOCUMENT ME! */
  public static final DayOfMonthType TWNETYTWO   = new DayOfMonthType("22", "22");

  /** DOCUMENT ME! */
  public static final DayOfMonthType TWENTYTHREE = new DayOfMonthType("23", "23");

  /** DOCUMENT ME! */
  public static final DayOfMonthType TWENTYFOUR  = new DayOfMonthType("24", "24");

  /** DOCUMENT ME! */
  public static final DayOfMonthType TWENTYFIVE  = new DayOfMonthType("25", "25");

  /** DOCUMENT ME! */
  public static final DayOfMonthType TWENTYSIX   = new DayOfMonthType("26", "26");

  /** DOCUMENT ME! */
  public static final DayOfMonthType TWENTYSEVEN = new DayOfMonthType("27", "27");

  /** DOCUMENT ME! */
  public static final DayOfMonthType TWENTYEIGHT = new DayOfMonthType("28", "28");

  /** DOCUMENT ME! */
  public static final DayOfMonthType TWENTYNINE = new DayOfMonthType("29", "29");

  /** DOCUMENT ME! */
  public static final DayOfMonthType THIRTY    = new DayOfMonthType("30", "30");

  /** DOCUMENT ME! */
  public static final DayOfMonthType THIRTYONE = new DayOfMonthType("31", "31");

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String friendlyType;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new DayOfMonthType object.
   */
  public DayOfMonthType() {
    // do nothing
  }

  /**
   * Creates a new DayOfMonthType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public DayOfMonthType(final String type, final String friendlyType) {
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
  public static DayOfMonthType getInstance(final String type) {
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

    DayOfMonthType other = (DayOfMonthType) obj;

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
} // end class DayOfMonthType
