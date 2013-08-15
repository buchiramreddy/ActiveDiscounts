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

import org.broadleafcommerce.core.payment.domain.AmountItem;
import org.broadleafcommerce.core.payment.domain.AmountItemImpl;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.service.PaymentInfoService;

import org.springframework.context.ApplicationContext;


/**
 * This is a JAXB wrapper around PaymentInfo.
 *
 * <p>User: Elbert Bautista Date: 4/26/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "amountItem")
public class AmountItemWrapper extends BaseWrapper implements APIWrapper<AmountItem>, APIUnwrapper<AmountItem> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected String description;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected Long paymentInfoId;

  /** DOCUMENT ME! */
  @XmlElement protected Long quantity;

  /** DOCUMENT ME! */
  @XmlElement protected String shortDescription;

  /** DOCUMENT ME! */
  @XmlElement protected String systemId;

  /** DOCUMENT ME! */
  @XmlElement protected BigDecimal unitPrice;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIUnwrapper#unwrap(javax.servlet.http.HttpServletRequest, org.springframework.context.ApplicationContext)
   */
  @Override public AmountItem unwrap(HttpServletRequest request, ApplicationContext context) {
    AmountItem amountItem = new AmountItemImpl();
    amountItem.setId(this.id);
    amountItem.setDescription(this.description);

    PaymentInfoService paymentInfoService = (PaymentInfoService) context.getBean("blPaymentInfoService");
    PaymentInfo        paymentInfo        = paymentInfoService.readPaymentInfoById(this.paymentInfoId);

    if (paymentInfo != null) {
      amountItem.setPaymentInfo(paymentInfo);
    }

    amountItem.setQuantity(this.quantity);
    amountItem.setShortDescription(this.shortDescription);
    amountItem.setSystemId(this.systemId);
    amountItem.setUnitPrice(this.unitPrice);

    return amountItem;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.payment.domain.AmountItem,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(AmountItem model, HttpServletRequest request) {
    this.id          = model.getId();
    this.description = model.getDescription();

    if (model.getPaymentInfo() != null) {
      this.paymentInfoId = model.getPaymentInfo().getId();
    }

    this.quantity         = model.getQuantity();
    this.shortDescription = model.getShortDescription();
    this.systemId         = model.getSystemId();
    this.unitPrice        = model.getUnitPrice();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.payment.domain.AmountItem,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(AmountItem model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class AmountItemWrapper
