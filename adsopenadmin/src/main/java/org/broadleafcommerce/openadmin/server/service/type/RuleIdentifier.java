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

package org.broadleafcommerce.openadmin.server.service.type;

import java.util.HashMap;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class RuleIdentifier {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final String CUSTOMER         = "CUSTOMER_FIELDS";

  /** DOCUMENT ME! */
  public static final String FULFILLMENTGROUP = "FULFILLMENT_GROUP_FIELDS";

  /** DOCUMENT ME! */
  public static final String LOCALE          = "LOCALE_FIELDS";

  /** DOCUMENT ME! */
  public static final String ORDER           = "ORDER_FIELDS";

  /** DOCUMENT ME! */
  public static final String ORDERITEM       = "ORDER_ITEM_FIELDS";

  /** DOCUMENT ME! */
  public static final String SKU             = "SKU_FIELDS";

  /** DOCUMENT ME! */
  public static final String TIME            = "TIME_FIELDS";

  /** DOCUMENT ME! */
  public static final String REQUEST         = "REQUEST_FIELDS";

  /** DOCUMENT ME! */
  public static final String PRICING_CONTEXT = "PRICING_CONTEXT_FIELDS";

  /** DOCUMENT ME! */
  public static final String PRODUCT  = "PRODUCT_FIELDS";

  /** DOCUMENT ME! */
  public static final String CATEGORY = "CATEGORY_FIELDS";

  /** DOCUMENT ME! */
  public static final String CUSTOMER_ENTITY_KEY         = "customer";

  /** DOCUMENT ME! */
  public static final String FULFILLMENTGROUP_ENTITY_KEY = "fulfillmentGroup";

  /** DOCUMENT ME! */
  public static final String LOCALE_ENTITY_KEY          = "locale";

  /** DOCUMENT ME! */
  public static final String ORDER_ENTITY_KEY           = "order";

  /** DOCUMENT ME! */
  public static final String ORDERITEM_ENTITY_KEY       = "orderItem";

  /** DOCUMENT ME! */
  public static final String SKU_ENTITY_KEY             = "sku";

  /** DOCUMENT ME! */
  public static final String TIME_ENTITY_KEY            = "time";

  /** DOCUMENT ME! */
  public static final String REQUEST_ENTITY_KEY         = "request";

  /** DOCUMENT ME! */
  public static final String PRICING_CONTEXT_ENTITY_KEY = "pricingContext";

  /** DOCUMENT ME! */
  public static final String PRODUCT_ENTITY_KEY  = "product";

  /** DOCUMENT ME! */
  public static final String CATEGORY_ENTITY_KEY = "category";

  /** DOCUMENT ME! */
  public static final String CUSTOMER_FIELD_KEY          = "CUSTOMER";

  /** DOCUMENT ME! */
  public static final String LOCALE_FIELD_KEY            = "LOCALE";

  /** DOCUMENT ME! */
  public static final String ORDER_FIELD_KEY             = "ORDER";

  /** DOCUMENT ME! */
  public static final String FULFILLMENT_GROUP_FIELD_KEY = "FULFILLMENT_GROUP";

  /** DOCUMENT ME! */
  public static final String TIME_FIELD_KEY            = "TIME";

  /** DOCUMENT ME! */
  public static final String REQUEST_FIELD_KEY         = "REQUEST";

  /** DOCUMENT ME! */
  public static final String PRICING_CONTEXT_FIELD_KEY = "PRICING_CONTEXT";

  /** DOCUMENT ME! */
  public static final String PRODUCT_FIELD_KEY  = "PRODUCT";

  /** DOCUMENT ME! */
  public static final String CATEGORY_FIELD_KEY = "CATEGORY";

  /** DOCUMENT ME! */
  public static Map<String, String> ENTITY_KEY_MAP = new HashMap<String, String>();

  static {
    ENTITY_KEY_MAP.put(CUSTOMER, CUSTOMER_ENTITY_KEY);
    ENTITY_KEY_MAP.put(FULFILLMENTGROUP, FULFILLMENTGROUP_ENTITY_KEY);
    ENTITY_KEY_MAP.put(LOCALE, LOCALE_ENTITY_KEY);
    ENTITY_KEY_MAP.put(ORDER, ORDER_ENTITY_KEY);
    ENTITY_KEY_MAP.put(ORDERITEM, ORDERITEM_ENTITY_KEY);
    ENTITY_KEY_MAP.put(SKU, SKU_ENTITY_KEY);
    ENTITY_KEY_MAP.put(TIME, TIME_ENTITY_KEY);
    ENTITY_KEY_MAP.put(REQUEST, REQUEST_ENTITY_KEY);
    ENTITY_KEY_MAP.put(PRODUCT, PRODUCT_ENTITY_KEY);
    ENTITY_KEY_MAP.put(CATEGORY, CATEGORY_ENTITY_KEY);
    ENTITY_KEY_MAP.put(PRICING_CONTEXT, PRICING_CONTEXT_ENTITY_KEY);
  }
} // end class RuleIdentifier
