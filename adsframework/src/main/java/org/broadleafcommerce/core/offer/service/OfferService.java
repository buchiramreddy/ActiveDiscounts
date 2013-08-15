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

package org.broadleafcommerce.core.offer.service;

import java.util.List;

import org.broadleafcommerce.core.offer.dao.CustomerOfferDao;
import org.broadleafcommerce.core.offer.dao.OfferCodeDao;
import org.broadleafcommerce.core.offer.dao.OfferDao;
import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.broadleafcommerce.core.offer.service.discount.domain.PromotableItemFactory;
import org.broadleafcommerce.core.offer.service.processor.FulfillmentGroupOfferProcessor;
import org.broadleafcommerce.core.offer.service.processor.ItemOfferProcessor;
import org.broadleafcommerce.core.offer.service.processor.OrderOfferProcessor;
import org.broadleafcommerce.core.order.domain.Order;
import org.broadleafcommerce.core.order.service.OrderService;
import org.broadleafcommerce.core.pricing.service.exception.PricingException;

import org.broadleafcommerce.profile.core.domain.Customer;


/**
 * The Interface OfferService.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface OfferService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   offers  DOCUMENT ME!
   * @param   order   DOCUMENT ME!
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  void applyFulfillmentGroupOffersToOrder(List<Offer> offers, Order order) throws PricingException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Apply offers to order.
   *
   * @param   offers  the offers
   * @param   order   the order
   *
   * @throws  PricingException  DOCUMENT ME!
   */
  void applyOffersToOrder(List<Offer> offers, Order order) throws PricingException;

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Create a list of offers that applies to this order.
   *
   * @param   order  DOCUMENT ME!
   *
   * @return  create a list of offers that applies to this order.
   */
  List<Offer> buildOfferListForOrder(Order order);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns all offers.
   *
   * @return  all offers
   */
  List<Offer> findAllOffers();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Lookup an OfferCode by its id.
   *
   * @param   id  the offer id
   *
   * @return  the offer
   */
  OfferCode findOfferCodeById(Long id);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  CustomerOfferDao getCustomerOfferDao();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  FulfillmentGroupOfferProcessor getFulfillmentGroupOfferProcessor();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  ItemOfferProcessor getItemOfferProcessor();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferCodeDao getOfferCodeDao();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OfferDao getOfferDao();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderOfferProcessor getOrderOfferProcessor();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  OrderService getOrderService();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  PromotableItemFactory getPromotableItemFactory();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Lookup offer by code.
   *
   * @param   code  the code
   *
   * @return  the offer
   */
  Offer lookupOfferByCode(String code);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Lookup OfferCode by code.
   *
   * @param   code  the code
   *
   * @return  the offer
   */
  OfferCode lookupOfferCodeByCode(String code);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Save a new offer or updates an existing offer.
   *
   * @param   offer  DOCUMENT ME!
   *
   * @return  the offer
   */
  Offer save(Offer offer);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Saves a new Offer or updates an existing Offer that belongs to an OfferCode, then saves or updates the OfferCode.
   *
   * @param   offerCode  DOCUMENT ME!
   *
   * @return  the offerCode
   */
  OfferCode saveOfferCode(OfferCode offerCode);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  customerOfferDao  DOCUMENT ME!
   */
  void setCustomerOfferDao(CustomerOfferDao customerOfferDao);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fulfillmentGroupOfferProcessor  DOCUMENT ME!
   */
  void setFulfillmentGroupOfferProcessor(FulfillmentGroupOfferProcessor fulfillmentGroupOfferProcessor);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  itemOfferProcessor  DOCUMENT ME!
   */
  void setItemOfferProcessor(ItemOfferProcessor itemOfferProcessor);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  offerCodeDao  DOCUMENT ME!
   */
  void setOfferCodeDao(OfferCodeDao offerCodeDao);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  offerDao  DOCUMENT ME!
   */
  void setOfferDao(OfferDao offerDao);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  orderOfferProcessor  DOCUMENT ME!
   */
  void setOrderOfferProcessor(OrderOfferProcessor orderOfferProcessor);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  orderService  DOCUMENT ME!
   */
  void setOrderService(OrderService orderService);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  promotableItemFactory  DOCUMENT ME!
   */
  void setPromotableItemFactory(PromotableItemFactory promotableItemFactory);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Validates that the passed in customer has not exceeded the max uses for the passed in offer.
   *
   * <p>Returns true if it is ok for the customer to use this offer with their current order. Returns false if it is not
   * ok for the customer to use this offer with their current order.</p>
   *
   * <p>This condition could pass if the system allows two concurrent carts for the same customer. The condition will
   * fail at order submisstion time when the VerfiyCustomerMaxOfferUsesActivity runs (if that activity is configured as
   * part of the checkout workflow.)</p>
   *
   * <p>This method only checks offers who have a max_customer_uses value that is greater than zero. By default offers
   * can be used as many times as the customer's order qualifies.</p>
   *
   * <p>This method offers no protection against systems that allow customers to create multiple ids in the system.</p>
   *
   * @param   customer  The customer to check
   * @param   offer     The offer to check
   *
   * @return  validates that the passed in customer has not exceeded the max uses for the passed in offer.
   */
  boolean verifyMaxCustomerUsageThreshold(Customer customer, Offer offer);
} // end interface OfferService
