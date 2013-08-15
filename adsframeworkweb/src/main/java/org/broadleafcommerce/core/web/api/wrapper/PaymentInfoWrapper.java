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

import java.util.ArrayList;
import java.util.Currency;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.money.util.CurrencyAdapter;
import org.broadleafcommerce.common.util.xml.BigDecimalRoundingAdapter;

import org.broadleafcommerce.core.payment.domain.AmountItem;
import org.broadleafcommerce.core.payment.domain.PaymentInfo;
import org.broadleafcommerce.core.payment.service.PaymentInfoService;
import org.broadleafcommerce.core.payment.service.type.PaymentInfoType;

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
@XmlRootElement(name = "paymentInfo")
public class PaymentInfoWrapper extends BaseWrapper implements APIWrapper<PaymentInfo>, APIUnwrapper<PaymentInfo> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement(name = "element")
  @XmlElementWrapper(name = "additionalFields")
  protected List<MapElementWrapper> additionalFields;

  /** DOCUMENT ME! */
  @XmlElement protected AddressWrapper address;

  /** DOCUMENT ME! */
  @XmlElement
  @XmlJavaTypeAdapter(value = BigDecimalRoundingAdapter.class)
  protected BigDecimal             amount;

  /** DOCUMENT ME! */
  @XmlElement(name = "amountItem")
  @XmlElementWrapper(name = "amountItems")
  protected List<AmountItemWrapper> amountItems;

  /** DOCUMENT ME! */
  @XmlElement
  @XmlJavaTypeAdapter(value = CurrencyAdapter.class)
  protected Currency             currency;

  /** DOCUMENT ME! */
  @XmlElement protected String customerIpAddress;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected Long orderId;

  /** DOCUMENT ME! */
  @XmlElement protected PhoneWrapper phone;

  /** DOCUMENT ME! */
  @XmlElement protected String referenceNumber;

  /** DOCUMENT ME! */
  @XmlElement protected String type;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIUnwrapper#unwrap(javax.servlet.http.HttpServletRequest, org.springframework.context.ApplicationContext)
   */
  @Override public PaymentInfo unwrap(HttpServletRequest request, ApplicationContext context) {
    PaymentInfoService paymentInfoService = (PaymentInfoService) context.getBean("blPaymentInfoService");
    PaymentInfo        paymentInfo        = paymentInfoService.create();

    paymentInfo.setType(PaymentInfoType.getInstance(this.type));

    if (this.address != null) {
      paymentInfo.setAddress(this.address.unwrap(request, context));
    }

    if (this.phone != null) {
      paymentInfo.setPhone(this.phone.unwrap(request, context));
    }

    if ((this.additionalFields != null) && !this.additionalFields.isEmpty()) {
      Map<String, String> fields = new HashMap<String, String>();

      for (MapElementWrapper mapElementWrapper : this.additionalFields) {
        fields.put(mapElementWrapper.getKey(), mapElementWrapper.getValue().toString());
      }

      paymentInfo.setAdditionalFields(fields);
    }

    if (this.amount != null) {
      if (this.currency != null) {
        paymentInfo.setAmount(new Money(this.amount, this.currency));
      } else {
        paymentInfo.setAmount(new Money(this.amount));
      }
    }

    paymentInfo.setCustomerIpAddress(this.customerIpAddress);
    paymentInfo.setReferenceNumber(this.referenceNumber);

    return paymentInfo;
  } // end method unwrap

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.payment.domain.PaymentInfo,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(PaymentInfo model, HttpServletRequest request) {
    this.id = model.getId();

    if (model.getOrder() != null) {
      this.orderId = model.getOrder().getId();
    }

    if (model.getType() != null) {
      this.type = model.getType().getType();
    }

    if (model.getAddress() != null) {
      AddressWrapper addressWrapper = (AddressWrapper) context.getBean(AddressWrapper.class.getName());
      addressWrapper.wrapDetails(model.getAddress(), request);
      this.address = addressWrapper;
    }

    if (model.getPhone() != null) {
      PhoneWrapper phoneWrapper = (PhoneWrapper) context.getBean(PhoneWrapper.class.getName());
      phoneWrapper.wrapDetails(model.getPhone(), request);
      this.phone = phoneWrapper;
    }

    if ((model.getAdditionalFields() != null) && !model.getAdditionalFields().isEmpty()) {
      List<MapElementWrapper> mapElementWrappers = new ArrayList<MapElementWrapper>();

      for (String key : model.getAdditionalFields().keySet()) {
        MapElementWrapper mapElementWrapper = new MapElementWrapper();
        mapElementWrapper.setKey(key);
        mapElementWrapper.setValue(model.getAdditionalFields().get(key));
        mapElementWrappers.add(mapElementWrapper);
      }

      this.additionalFields = mapElementWrappers;
    }

    if (model.getAmount() != null) {
      this.amount   = model.getAmount().getAmount();
      this.currency = model.getAmount().getCurrency();
    }

    if (model.getAmountItems() != null) {
      List<AmountItemWrapper> wrappers = new ArrayList<AmountItemWrapper>();

      for (AmountItem amountItem : model.getAmountItems()) {
        AmountItemWrapper amountItemWrapper = (AmountItemWrapper) context.getBean(AmountItemWrapper.class.getName());
        amountItemWrapper.wrapSummary(amountItem, request);
        wrappers.add(amountItemWrapper);
      }

      this.amountItems = wrappers;
    }

    this.customerIpAddress = model.getCustomerIpAddress();
    this.referenceNumber   = model.getReferenceNumber();
  } // end method wrapDetails

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.payment.domain.PaymentInfo,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(PaymentInfo model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class PaymentInfoWrapper
