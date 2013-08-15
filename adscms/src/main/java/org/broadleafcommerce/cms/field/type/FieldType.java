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

package org.broadleafcommerce.cms.field.type;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;


/**
 * Supported field types within the Broadleaf CMS admin.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class FieldType implements Serializable {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, FieldType> TYPES = new HashMap<String, FieldType>();

  /** DOCUMENT ME! */
  public static final FieldType BOOLEAN     = new FieldType("BOOLEAN", "Boolean");

  /** DOCUMENT ME! */
  public static final FieldType DATE        = new FieldType("DATE", "Date");

  /** DOCUMENT ME! */
  public static final FieldType TIME        = new FieldType("TIME", "Time");

  /** DOCUMENT ME! */
  public static final FieldType INTEGER     = new FieldType("INTEGER", "Integer");

  /** DOCUMENT ME! */
  public static final FieldType DECIMAL     = new FieldType("DECIMAL", "Decimal");

  /** DOCUMENT ME! */
  public static final FieldType STRING      = new FieldType("STRING", "String");

  /** DOCUMENT ME! */
  public static final FieldType RICH_TEXT   = new FieldType("RICH_TEXT", "Rich Text");

  /** DOCUMENT ME! */
  public static final FieldType HTML        = new FieldType("HTML", "HTML");

  /** DOCUMENT ME! */
  public static final FieldType ENUMERATION = new FieldType("ENUMERATION", "Enumeration");

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String friendlyType;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new FieldType object.
   */
  public FieldType() {
    // do nothing
  }

  /**
   * Creates a new FieldType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public FieldType(final String type, final String friendlyType) {
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
  public static FieldType getInstance(final String type) {
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

    FieldType other = (FieldType) obj;

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
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getFriendlyType() {
    return friendlyType;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getType() {
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
} // end class FieldType
