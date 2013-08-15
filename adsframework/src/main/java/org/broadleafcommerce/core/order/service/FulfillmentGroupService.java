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

package org.broadleafcommerce.core.order.service;

import java.util.List;

import org.broadleafcommerce.core.order.domain.FulfillmentGroup;
import org.broadleafcommerce.core.order.domain.FulfillmentGroupFee;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;
import org.broadleafcommerce.core.order.service.call.FulfillmentGroupItemRequest;
import org.broadleafcommerce.core.order.service.call.FulfillmentGroupRequest;
import org.broadleafcommerce.core.order.service.type.FulfillmentGroupStatusType;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface FulfillmentGroupService {
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
   * @return  DOCUMENT ME!
   */
  FulfillmentGroup createEmptyFulfillmentGroup();

  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroupId  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroup findFulfillmentGroupById(Long fulfillmentGroupId);

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroup  DOCUMENT ME!
   */
  void delete(FulfillmentGroup fulfillmentGroup);

  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroupRequest  DOCUMENT ME!
   * @param   priceOrder               DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  FulfillmentGroup addFulfillmentGroupToOrder(FulfillmentGroupRequest fulfillmentGroupRequest, boolean priceOrder)
    throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   fulfillmentGroupItemRequest  DOCUMENT ME!
   * @param   priceOrder                   DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  FulfillmentGroup addItemToFulfillmentGroup(FulfillmentGroupItemRequest fulfillmentGroupItemRequest,
    boolean priceOrder) throws PricingException;

  /**
   * DOCUMENT ME!
   *
   * @param   order       DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  Order removeAllFulfillmentGroupsFromOrder(Order order, boolean priceOrder) throws PricingException;

  /**
   * Removes every fulfillment group item in every fulfillment group in the order that is associated with the given
   * orderItem. Note that it does not save the changes made - instead, the caller is responsible for saving the order
   * further down.
   *
   * @param  order      DOCUMENT ME!
   * @param  orderItem  DOCUMENT ME!
   */
  void removeOrderItemFromFullfillmentGroups(Order order, OrderItem orderItem);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroupFee createFulfillmentGroupFee();

  /**
   * Associates FulfillmentGroupItems in the given Order such that they match the structure of the OrderMultishipOptions
   * associated with the given Order.
   *
   * @see     org.broadleafcommerce.core.order.domain.OrderMultishipOption
   *
   * @param   order       DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  the saved order
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  Order matchFulfillmentGroupsToMultishipOptions(Order order, boolean priceOrder) throws PricingException;

  /**
   * Collapses all of the fulfillment groups in the given order to the first fulfillment group in the order.
   *
   * @see     #matchFulfillmentGroupsToMultishipOptions(org.broadleafcommerce.core.order.domain.Order, boolean)
   *
   * @param   order       DOCUMENT ME!
   * @param   priceOrder  DOCUMENT ME!
   *
   * @return  the saved order
   *
   * @throws  org.broadleafcommerce.core.pricing.service.exception.PricingException
   */
  Order collapseToOneFulfillmentGroup(Order order, boolean priceOrder) throws PricingException;


  /**
   * Reads FulfillmentGroups whose status is not FULFILLED or DELIVERED.
   *
   * @param   start       DOCUMENT ME!
   * @param   maxResults  DOCUMENT ME!
   *
   * @return  reads FulfillmentGroups whose status is not FULFILLED or DELIVERED.
   */
  List<FulfillmentGroup> findUnfulfilledFulfillmentGroups(int start, int maxResults);

  /**
   * Reads FulfillmentGroups whose status is PARTIALLY_FULFILLED or PARTIALLY_DELIVERED.
   *
   * @param   start       DOCUMENT ME!
   * @param   maxResults  DOCUMENT ME!
   *
   * @return  reads FulfillmentGroups whose status is PARTIALLY_FULFILLED or PARTIALLY_DELIVERED.
   */
  List<FulfillmentGroup> findPartiallyFulfilledFulfillmentGroups(int start, int maxResults);

  /**
   * Returns FulfillmentGroups whose status is null, or where no processing has yet occured. Default returns in
   * ascending order according to date that the order was created.
   *
   * @param   start       DOCUMENT ME!
   * @param   maxResults  DOCUMENT ME!
   *
   * @return  FulfillmentGroups whose status is null, or where no processing has yet occured.
   */
  List<FulfillmentGroup> findUnprocessedFulfillmentGroups(int start, int maxResults);

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
  List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults,
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
  List<FulfillmentGroup> findFulfillmentGroupsByStatus(FulfillmentGroupStatusType status, int start, int maxResults);

} // end interface FulfillmentGroupService
