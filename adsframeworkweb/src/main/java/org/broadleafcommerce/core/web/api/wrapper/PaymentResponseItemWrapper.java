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

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import org.broadleafcommerce.core.payment.domain.PaymentResponseItem;


/**
 * This is a JAXB wrapper around PaymentResponseItem.
 *
 * <p>User: Elbert Bautista Date: 4/26/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "paymentResponseItem")
public class PaymentResponseItemWrapper extends BaseWrapper implements APIWrapper<PaymentResponseItem> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement(name = "element")
  @XmlElementWrapper(name = "additionalFields")
  protected List<MapElementWrapper> additionalFields;

  /** DOCUMENT ME! */
  @XmlElement protected Long paymentInfoId;

  /** DOCUMENT ME! */
  @XmlElement protected String processorResponseCode;

  /** DOCUMENT ME! */
  @XmlElement protected Boolean transactionSuccess;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.payment.domain.PaymentResponseItem,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(PaymentResponseItem model, HttpServletRequest request) {
    this.paymentInfoId         = model.getPaymentInfoId();
    this.processorResponseCode = model.getProcessorResponseCode();
    this.transactionSuccess    = model.getTransactionSuccess();

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
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.payment.domain.PaymentResponseItem,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(PaymentResponseItem model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class PaymentResponseItemWrapper
