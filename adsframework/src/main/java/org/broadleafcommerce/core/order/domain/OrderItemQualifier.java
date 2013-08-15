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

package org.broadleafcommerce.core.order.domain;

import java.io.Serializable;

import org.broadleafcommerce.core.offer.domain.Offer;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface OrderItemQualifier extends Serializable {
  /**
   * Unique id of the item qualifier.
   *
   * @return  unique id of the item qualifier.
   */
  Long getId();

  /**
   * Sets the id for this OrderItemQualifier.
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  /**
   * The related order item.
   *
   * @return  the related order item.
   */
  OrderItem getOrderItem();

  /**
   * Sets the related order item.
   *
   * @param  orderItem  DOCUMENT ME!
   */
  void setOrderItem(OrderItem orderItem);

  /**
   * Sets the related offer.
   *
   * @param  offer  DOCUMENT ME!
   */
  void setOffer(Offer offer);

  /**
   * Returns the related offer.
   *
   * @return  the related offer.
   */
  Offer getOffer();

  /**
   * Sets the quantity of the associated OrderItem that was used as a qualifier.
   *
   * @param  quantity  DOCUMENT ME!
   */
  void setQuantity(Long quantity);

  /**
   * Returns the quantity of the associated OrderItem that was used as a qualifier.
   *
   * @return  the quantity of the associated OrderItem that was used as a qualifier.
   */
  Long getQuantity();
} // end interface OrderItemQualifier
