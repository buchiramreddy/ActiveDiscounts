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

package org.broadleafcommerce.core.search.domain.solr;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * An extensible enumeration of entities that are used for searching and reporting.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class FieldType implements Serializable, BroadleafEnumerationType {
  private static final long serialVersionUID = 1L;

  private static final Map<String, FieldType> TYPES = new LinkedHashMap<String, FieldType>();

  /** DOCUMENT ME! */
  public static final FieldType ID       = new FieldType("id", "ID");

  /** DOCUMENT ME! */
  public static final FieldType CATEGORY = new FieldType("category", "Category");

  /** DOCUMENT ME! */
  public static final FieldType INT        = new FieldType("i", "Integer");

  /** DOCUMENT ME! */
  public static final FieldType INTS       = new FieldType("is", "Integer (Multi)");

  /** DOCUMENT ME! */
  public static final FieldType STRING     = new FieldType("s", "String");

  /** DOCUMENT ME! */
  public static final FieldType STRINGS    = new FieldType("ss", "String (Multi)");

  /** DOCUMENT ME! */
  public static final FieldType LONG       = new FieldType("l", "Long");

  /** DOCUMENT ME! */
  public static final FieldType LONGS      = new FieldType("ls", "Long (Multi)");

  /** DOCUMENT ME! */
  public static final FieldType TEXT       = new FieldType("t", "Text");

  /** DOCUMENT ME! */
  public static final FieldType TEXTS      = new FieldType("txt", "Text (Multi)");

  /** DOCUMENT ME! */
  public static final FieldType BOOLEAN    = new FieldType("b", "Boolean");

  /** DOCUMENT ME! */
  public static final FieldType BOOLEANS   = new FieldType("bs", "Boolean (Multi)");

  /** DOCUMENT ME! */
  public static final FieldType DOUBLE     = new FieldType("d", "Double");

  /** DOCUMENT ME! */
  public static final FieldType DOUBLES    = new FieldType("ds", "Double (Multi)");

  /** DOCUMENT ME! */
  public static final FieldType PRICE      = new FieldType("p", "Price");

  /** DOCUMENT ME! */
  public static final FieldType DATE       = new FieldType("dt", "Date");

  /** DOCUMENT ME! */
  public static final FieldType DATES      = new FieldType("dts", "Date (Multi)");

  /** DOCUMENT ME! */
  public static final FieldType TRIEINT    = new FieldType("tint", "Trie Integer");

  /** DOCUMENT ME! */
  public static final FieldType TRIELONG   = new FieldType("tlong", "Trie Long");

  /** DOCUMENT ME! */
  public static final FieldType TRIEDOUBLE = new FieldType("tdouble", "Trie Double");

  /** DOCUMENT ME! */
  public static final FieldType TRIEDATE = new FieldType("tdate", "Trie Date");

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

  private String type;
  private String friendlyType;

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

  /**
   * @see  org.broadleafcommerce.common.BroadleafEnumerationType#getType()
   */
  @Override public String getType() {
    return type;
  }

  /**
   * @see  org.broadleafcommerce.common.BroadleafEnumerationType#getFriendlyType()
   */
  @Override public String getFriendlyType() {
    return friendlyType;
  }

  private void setType(final String type) {
    this.type = type;

    if (!TYPES.containsKey(type)) {
      TYPES.put(type, this);
    }
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((type == null) ? 0 : type.hashCode());

    return result;
  }

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
} // end class FieldType
