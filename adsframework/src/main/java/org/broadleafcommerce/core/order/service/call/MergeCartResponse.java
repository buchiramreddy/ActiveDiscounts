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

package org.broadleafcommerce.core.order.service.call;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.domain.OrderItem;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class MergeCartResponse implements Serializable {
  private static final long serialVersionUID = 1L;

  private Order order;

  private List<OrderItem> addedItems = new ArrayList<OrderItem>();
  ;

  private List<OrderItem> removedItems = new ArrayList<OrderItem>();
  ;

  private boolean merged;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Order getOrder() {
    return order;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  order  DOCUMENT ME!
   */
  public void setOrder(Order order) {
    this.order = order;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<OrderItem> getAddedItems() {
    return addedItems;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  addedItems  DOCUMENT ME!
   */
  public void setAddedItems(List<OrderItem> addedItems) {
    this.addedItems = addedItems;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<OrderItem> getRemovedItems() {
    return removedItems;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  removedItems  DOCUMENT ME!
   */
  public void setRemovedItems(List<OrderItem> removedItems) {
    this.removedItems = removedItems;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isMerged() {
    return merged;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  merged  DOCUMENT ME!
   */
  public void setMerged(boolean merged) {
    this.merged = merged;
  }

} // end class MergeCartResponse
