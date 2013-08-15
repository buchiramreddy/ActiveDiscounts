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

package org.broadleafcommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.broadleafcommerce.core.offer.domain.Offer;


/**
 * This is a JAXB wrapper around OfferWrapper.
 *
 * <p>User: Priyesh Patel</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "paymentInfo")
public class OfferWrapper extends BaseWrapper implements APIWrapper<Offer> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected BroadleafEnumerationTypeWrapper discountType;

  /** DOCUMENT ME! */
  @XmlElement protected String marketingMessage;

  /** DOCUMENT ME! */
  @XmlElement protected Long offerId;

  /** DOCUMENT ME! */
  @XmlElement protected BroadleafEnumerationTypeWrapper offerType;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.offer.domain.Offer,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(Offer model, HttpServletRequest request) {
    this.marketingMessage = model.getMarketingMessage();
    this.offerType        = (BroadleafEnumerationTypeWrapper) context.getBean(BroadleafEnumerationTypeWrapper.class
        .getName());
    this.offerType.wrapDetails(model.getType(), request);
    this.discountType = (BroadleafEnumerationTypeWrapper) context.getBean(BroadleafEnumerationTypeWrapper.class
        .getName());
    this.discountType.wrapDetails(model.getDiscountType(), request);

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.offer.domain.Offer,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(Offer model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class OfferWrapper
