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
package org.broadleafcommerce.core.catalog.service.type;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * An extendible enumeration of product option types.
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class ProductOptionType implements Serializable, BroadleafEnumerationType {
  private static final long serialVersionUID = 1L;

  private static final Map<String, ProductOptionType> TYPES = new LinkedHashMap<String, ProductOptionType>();

  /** DOCUMENT ME! */
  public static final ProductOptionType COLOR   = new ProductOptionType("COLOR", "Color");

  /** DOCUMENT ME! */
  public static final ProductOptionType SIZE    = new ProductOptionType("SIZE", "Size");

  /** DOCUMENT ME! */
  public static final ProductOptionType DATE    = new ProductOptionType("DATE", "Date");

  /** DOCUMENT ME! */
  public static final ProductOptionType TEXT    = new ProductOptionType("TEXT", "Text");

  /** DOCUMENT ME! */
  public static final ProductOptionType BOOLEAN = new ProductOptionType("BOOLEAN", "Boolean");

  /** DOCUMENT ME! */
  public static final ProductOptionType DECIMAL = new ProductOptionType("DECIMAL", "Decimal");

  /** DOCUMENT ME! */
  public static final ProductOptionType INTEGER = new ProductOptionType("INTEGER", "Integer");

  /** DOCUMENT ME! */
  public static final ProductOptionType INPUT   = new ProductOptionType("INPUT", "Input");

  /** DOCUMENT ME! */
  public static final ProductOptionType PRODUCT = new ProductOptionType("PRODUCT", "Product");

  /**
   * DOCUMENT ME!
   *
   * @param   type  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static ProductOptionType getInstance(final String type) {
    return TYPES.get(type);
  }

  private String type;
  private String friendlyType;

  /**
   * Creates a new ProductOptionType object.
   */
  public ProductOptionType() {
    // do nothing
  }

  /**
   * Creates a new ProductOptionType object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public ProductOptionType(final String type, final String friendlyType) {
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

    ProductOptionType other = (ProductOptionType) obj;

    if (type == null) {
      if (other.type != null) {
        return false;
      }
    } else if (!type.equals(other.type)) {
      return false;
    }

    return true;
  } // end method equals
} // end class ProductOptionType
