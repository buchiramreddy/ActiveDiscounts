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

package org.broadleafcommerce.core.order.dao;

import java.util.List;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupItem;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface FulfillmentGroupItemDao {
  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroupItemId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroupItem readFulfillmentGroupItemById(Long fulfillmentGroupItemId);

  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroupItem  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroupItem save(FulfillmentGroupItem fulfillmentGroupItem);

  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroup  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  List<FulfillmentGroupItem> readFulfillmentGroupItemsForFulfillmentGroup(FulfillmentGroup fulfillmentGroup);

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroupItem  DOCUMENT ME!
   */
  void delete(FulfillmentGroupItem fulfillmentGroupItem);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroupItem create();
} // end interface FulfillmentGroupItemDao
