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

import java.math.BigDecimal;

import java.util.Calendar;

import org.broadleafcommerce.common.time.SystemTime;

import org.broadleafcommerce.core.offer.dao.OfferCodeDao;
import org.broadleafcommerce.core.offer.dao.OfferDao;
import org.broadleafcommerce.core.offer.domain.Offer;
import org.broadleafcommerce.core.offer.domain.OfferCode;
import org.broadleafcommerce.core.offer.service.type.OfferDeliveryType;
import org.broadleafcommerce.core.offer.service.type.OfferDiscountType;
import org.broadleafcommerce.core.offer.service.type.OfferType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@SuppressWarnings("deprecation")
public class CreateOfferUtility {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private OfferCodeDao offerCodeDao;

  private OfferDao     offerDao;
  private OfferService offerService;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new CreateOfferUtility object.
   *
   * @param  offerDao      DOCUMENT ME!
   * @param  offerCodeDao  DOCUMENT ME!
   * @param  offerService  DOCUMENT ME!
   */
  public CreateOfferUtility(OfferDao offerDao, OfferCodeDao offerCodeDao, OfferService offerService) {
    this.offerDao     = offerDao;
    this.offerCodeDao = offerCodeDao;
    this.offerService = offerService;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   offerName     DOCUMENT ME!
   * @param   offerType     DOCUMENT ME!
   * @param   discountType  DOCUMENT ME!
   * @param   value         DOCUMENT ME!
   * @param   customerRule  DOCUMENT ME!
   * @param   orderRule     DOCUMENT ME!
   * @param   stackable     DOCUMENT ME!
   * @param   combinable    DOCUMENT ME!
   * @param   priority      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Offer createOffer(String offerName, OfferType offerType, OfferDiscountType discountType, double value,
    String customerRule, String orderRule, boolean stackable, boolean combinable, int priority) {
    Offer offer = offerDao.create();
    offer.setName(offerName);
    offer.setStartDate(SystemTime.asDate());

    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.DATE, -1);
    offer.setStartDate(calendar.getTime());
    calendar.add(Calendar.DATE, 2);
    offer.setEndDate(calendar.getTime());
    offer.setType(offerType);
    offer.setDiscountType(discountType);
    offer.setValue(BigDecimal.valueOf(value));
    offer.setDeliveryType(OfferDeliveryType.CODE);
    offer.setStackable(stackable);
    offer.setAppliesToOrderRules(orderRule);
    offer.setAppliesToCustomerRules(customerRule);
    offer.setCombinableWithOtherOffers(combinable);
    offer.setPriority(priority);
    offer = offerService.save(offer);
    offer.setMaxUses(50);

    return offer;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   offerName     DOCUMENT ME!
   * @param   offerType     DOCUMENT ME!
   * @param   discountType  DOCUMENT ME!
   * @param   value         DOCUMENT ME!
   * @param   customerRule  DOCUMENT ME!
   * @param   orderRule     DOCUMENT ME!
   * @param   stackable     DOCUMENT ME!
   * @param   combinable    DOCUMENT ME!
   * @param   priority      DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OfferCode createOfferCode(String offerName, OfferType offerType, OfferDiscountType discountType, double value,
    String customerRule, String orderRule, boolean stackable, boolean combinable, int priority) {
    return createOfferCode("NONAME", offerName, offerType, discountType, value, customerRule, orderRule, stackable,
        combinable, priority);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   offerCodeName  DOCUMENT ME!
   * @param   offerName      DOCUMENT ME!
   * @param   offerType      DOCUMENT ME!
   * @param   discountType   DOCUMENT ME!
   * @param   value          DOCUMENT ME!
   * @param   customerRule   DOCUMENT ME!
   * @param   orderRule      DOCUMENT ME!
   * @param   stackable      DOCUMENT ME!
   * @param   combinable     DOCUMENT ME!
   * @param   priority       DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public OfferCode createOfferCode(String offerCodeName, String offerName, OfferType offerType,
    OfferDiscountType discountType, double value, String customerRule, String orderRule, boolean stackable,
    boolean combinable, int priority) {
    OfferCode offerCode = offerCodeDao.create();
    Offer     offer     = createOffer(offerName, offerType, discountType, value, customerRule, orderRule, stackable,
        combinable, priority);
    offerCode.setOffer(offer);
    offerCode.setOfferCode(offerCodeName);
    offerCode = offerService.saveOfferCode(offerCode);

    return offerCode;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   code     DOCUMENT ME!
   * @param   maxUses  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Offer updateOfferCodeMaxCustomerUses(OfferCode code, Long maxUses) {
    code.getOffer().setMaxUsesPerCustomer(maxUses);

    return offerService.save(code.getOffer());
  }
} // end class CreateOfferUtility
