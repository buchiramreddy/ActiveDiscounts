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

package org.broadleafcommerce.core.offer.service.type;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * An extendible enumeration of offer types.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class OfferTimeZoneType implements Serializable, BroadleafEnumerationType {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, OfferTimeZoneType> TYPES = new LinkedHashMap<String, OfferTimeZoneType>();

  /** DOCUMENT ME! */
  public static final OfferTimeZoneType SERVER      = new OfferTimeZoneType("SERVER", "Server");

  /** DOCUMENT ME! */
  public static final OfferTimeZoneType APPLICATION = new OfferTimeZoneType("APPLICATION", "Application Supplied");

  /** DOCUMENT ME! */
  public static final OfferTimeZoneType CST = new OfferTimeZoneType("CST", "CST", true);

  /** DOCUMENT ME! */
  public static final OfferTimeZoneType UTC = new OfferTimeZoneType("UTC", "UTC", true);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String  friendlyType;
  private Boolean javaStandardTimeZone;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------


  /**
   * Creates a new OfferTimeZoneType object.
   */
  public OfferTimeZoneType() {
    // do nothing
  }

  /**
   * Creates a new OfferTimeZoneType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public OfferTimeZoneType(final String type, final String friendlyType) {
    this(type, friendlyType, false);
  }

  /**
   * Creates a new OfferTimeZoneType object.
   *
   * @param  type                  DOCUMENT ME!
   * @param  friendlyType          DOCUMENT ME!
   * @param  javaStandardTimeZone  DOCUMENT ME!
   */
  public OfferTimeZoneType(final String type, final String friendlyType, Boolean javaStandardTimeZone) {
    this.friendlyType = friendlyType;
    setType(type);
    setJavaStandardTimeZone(javaStandardTimeZone);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static OfferTimeZoneType getInstance(final String type) {
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

    OfferTimeZoneType other = (OfferTimeZoneType) obj;

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
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getJavaStandardTimeZone() {
    return javaStandardTimeZone;
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

  /**
   * DOCUMENT ME!
   *
   * @param  javaStandardTimeZone  DOCUMENT ME!
   */
  public void setJavaStandardTimeZone(Boolean javaStandardTimeZone) {
    this.javaStandardTimeZone = javaStandardTimeZone;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  type  DOCUMENT ME!
   */
  public void setType(final String type) {
    this.type = type;

    if (!TYPES.containsKey(type)) {
      TYPES.put(type, this);
    }
  }

} // end class OfferTimeZoneType
