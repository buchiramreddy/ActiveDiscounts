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
import org.broadleafcommerce.core.order.domain.FulfillmentGroupFee;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.type.FulfillmentGroupStatusType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface FulfillmentGroupDao {
  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroupId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroup readFulfillmentGroupById(Long fulfillmentGroupId);

  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroup  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroup save(FulfillmentGroup fulfillmentGroup);

  /**
   * DOCUMENT ME!
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroup readDefaultFulfillmentGroupForOrder(Order order);

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroup  DOCUMENT ME!
   */
  void delete(FulfillmentGroup fulfillmentGroup);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroup createDefault();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroup create();

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroupFee createFulfillmentGroupFee();

  /**
   * Reads FulfillmentGroups whose status is not FULFILLED or DELIVERED.
   *
   * @param   start       DOCUMENT ME!
   * @param   maxResults  DOCUMENT ME!
   *
   * @return  reads FulfillmentGroups whose status is not FULFILLED or DELIVERED.
   */
  List<FulfillmentGroup> readUnfulfilledFulfillmentGroups(int start, int maxResults);

  /**
   * Reads FulfillmentGroups whose status is PARTIALLY_FULFILLED or PARTIALLY_DELIVERED.
   *
   * @param   start       DOCUMENT ME!
   * @param   maxResults  DOCUMENT ME!
   *
   * @return  reads FulfillmentGroups whose status is PARTIALLY_FULFILLED or PARTIALLY_DELIVERED.
   */
  List<FulfillmentGroup> readPartiallyFulfilledFulfillmentGroups(int start, int maxResults);

  /**
   * Returns FulfillmentGroups whose status is null, or where no processing has yet occured. Default returns in
   * ascending order according to date that the order was created.
   *
   * @param   start       DOCUMENT ME!
   * @param   maxResults  DOCUMENT ME!
   *
   * @return  FulfillmentGroups whose status is null, or where no processing has yet occured.
   */
  List<FulfillmentGroup> readUnprocessedFulfillmentGroups(int start, int maxResults);

  /**
   * Reads FulfillmentGroups by status, either ascending or descending according to the date that the order was created.
   *
   * @param   status      DOCUMENT ME!
   * @param   start       DOCUMENT ME!
   * @param   maxResults  DOCUMENT ME!
   * @param   ascending   DOCUMENT ME!
   *
   * @return  reads FulfillmentGroups by status, either ascending or descending according to the date that the order was
   *          created.
   */
  List<FulfillmentGroup> readFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults,
    boolean ascending);

  /**
   * Reads FulfillmentGroups by status, ascending according to the date that the order was created.
   *
   * @param   status      DOCUMENT ME!
   * @param   start       DOCUMENT ME!
   * @param   maxResults  DOCUMENT ME!
   *
   * @return  reads FulfillmentGroups by status, ascending according to the date that the order was created.
   */
  List<FulfillmentGroup> readFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults);

  /**
   * Reads the max sequnce of fulfillment groups for a particular order and increments by 1.
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  reads the max sequnce of fulfillment groups for a particular order and increments by 1.
   */
  Integer readNextFulfillmentGroupSequnceForOrder(Order order);
} // end interface FulfillmentGroupDao
