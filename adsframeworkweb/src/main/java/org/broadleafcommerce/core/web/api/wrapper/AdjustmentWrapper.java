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

import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.Adjustment;
import org.broadleafcommerce.core.offer.domain.Offer;


/**
 * This is a JAXB wrapper around OrderAdjustmentWrapper.
 *
 * <p>Author: ppatel, bpolster</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "adjustment")
public class AdjustmentWrapper extends BaseWrapper implements APIWrapper<Adjustment> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected Money adjustmentValue;

  /** DOCUMENT ME! */
  @XmlElement protected BigDecimal discountAmount;

  /** DOCUMENT ME! */
  @XmlElement protected String discountType;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected String marketingMessage;

  /** DOCUMENT ME! */
  @XmlElement protected Long offerid;

  /** DOCUMENT ME! */
  @XmlElement protected String reason;

  //~ Methods ----------------------------------------------------------------------------------------------------------


  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.offer.domain.Adjustment,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(Adjustment model, HttpServletRequest request) {
    if (model == null) {
      return;
    }

    this.id     = model.getId();
    this.reason = model.getReason();

    Offer offer = model.getOffer();

    if (offer != null) {
      if (model.getReason() == null) {
        this.reason = "OFFER";
      }

      this.offerid          = offer.getId();
      this.marketingMessage = offer.getMarketingMessage();
      this.discountType     = offer.getDiscountType().getType();
      this.discountAmount   = offer.getValue();
    }

    this.adjustmentValue = model.getValue();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.offer.domain.Adjustment,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(Adjustment model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class AdjustmentWrapper
