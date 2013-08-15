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

package org.broadleafcommerce.common.i18n.domain;

import java.io.Serializable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.broadleafcommerce.common.BroadleafEnumerationType;


/**
 * An extensible enumeration of entities that have translatable fields. Any entity that wishes to have a translatable
 * field must register itself in this TYPES map.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class TranslatedEntity implements Serializable, BroadleafEnumerationType {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  private static final Map<String, TranslatedEntity> TYPES = new LinkedHashMap<String, TranslatedEntity>();

  /** DOCUMENT ME! */
  public static final TranslatedEntity PRODUCT              = new TranslatedEntity(
      "org.broadleafcommerce.core.catalog.domain.Product", "Product");

  /** DOCUMENT ME! */
  public static final TranslatedEntity SKU                  = new TranslatedEntity(
      "org.broadleafcommerce.core.catalog.domain.Sku", "Sku");

  /** DOCUMENT ME! */
  public static final TranslatedEntity CATEGORY             = new TranslatedEntity(
      "org.broadleafcommerce.core.catalog.domain.Category", "Category");

  /** DOCUMENT ME! */
  public static final TranslatedEntity PRODUCT_OPTION       = new TranslatedEntity(
      "org.broadleafcommerce.core.catalog.domain.ProductOption", "ProdOption");

  /** DOCUMENT ME! */
  public static final TranslatedEntity PRODUCT_OPTION_VALUE = new TranslatedEntity(
      "org.broadleafcommerce.core.catalog.domain.ProductOptionValue", "ProdOptionVal");

  /** DOCUMENT ME! */
  public static final TranslatedEntity STATIC_ASSET       = new TranslatedEntity(
      "org.broadleafcommerce.cms.file.domain.StaticAsset", "StaticAsset");

  /** DOCUMENT ME! */
  public static final TranslatedEntity SEARCH_FACET       = new TranslatedEntity(
      "org.broadleafcommerce.core.search.domain.SearchFacet", "SearchFacet");

  /** DOCUMENT ME! */
  public static final TranslatedEntity FULFILLMENT_OPTION = new TranslatedEntity(
      "org.broadleafcommerce.core.order.domain.FulfillmentOption", "FulfillmentOption");

  /** DOCUMENT ME! */
  public static final TranslatedEntity OFFER = new TranslatedEntity("org.broadleafcommerce.core.offer.domain.Offer",
      "Offer");

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String friendlyType;

  private String type;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new TranslatedEntity object.
   */
  public TranslatedEntity() {
    // do nothing
  }

  /**
   * Creates a new TranslatedEntity object.
   *
   * @param  type          DOCUMENT ME!
   * @param  friendlyType  DOCUMENT ME!
   */
  public TranslatedEntity(final String type, final String friendlyType) {
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
  public static TranslatedEntity getInstance(final String type) {
    return TYPES.get(type);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   friendlyType  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static TranslatedEntity getInstanceFromFriendlyType(final String friendlyType) {
    for (Entry<String, TranslatedEntity> entry : TYPES.entrySet()) {
      if (entry.getValue().getFriendlyType().equals(friendlyType)) {
        return entry.getValue();
      }
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static Map<String, TranslatedEntity> getTypes() {
    return TYPES;
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

    TranslatedEntity other = (TranslatedEntity) obj;

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

} // end class TranslatedEntity
