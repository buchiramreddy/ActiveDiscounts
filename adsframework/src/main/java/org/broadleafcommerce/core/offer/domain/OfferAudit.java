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

package org.broadleafcommerce.core.offer.domain;

import java.io.Serializable;

import java.util.Date;


/**
 * Captures when an offer was applied to a customer.
 *
 * <p>Utilized by the offer process to enforce max use by customer rules and as a high-level audit of what orders and
 * customers have used an offer.</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface OfferAudit extends Serializable {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The id of the associated customer.
   *
   * @return  the id of the associated customer.
   */
  Long getCustomerId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * System generated unique id for this audit record.
   *
   * @return  system generated unique id for this audit record.
   */
  Long getId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The associated offer id.
   *
   * @return  the associated offer id.
   */
  Long getOfferId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The associated order id.
   *
   * @return  the associated order id.
   */
  Long getOrderId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The date the offer was applied to the order.
   *
   * @return  the date the offer was applied to the order.
   */
  Date getRedeemedDate();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the customer id.
   *
   * @param  customerId  DOCUMENT ME!
   */
  void setCustomerId(Long customerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the id.
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the associated offer id.
   *
   * @param  offerId  DOCUMENT ME!
   */
  void setOfferId(Long offerId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the associated order id.
   *
   * @param  orderId  DOCUMENT ME!
   */
  void setOrderId(Long orderId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the offer redeemed date.
   *
   * @param  redeemedDate  DOCUMENT ME!
   */
  void setRedeemedDate(Date redeemedDate);
} // end interface OfferAudit
