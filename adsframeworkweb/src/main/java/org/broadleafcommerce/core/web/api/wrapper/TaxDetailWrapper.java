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

import org.broadleafcommerce.core.order.domain.TaxDetail;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "taxDetail")
public class TaxDetailWrapper extends BaseWrapper implements APIWrapper<TaxDetail> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected Money amount;

  /** DOCUMENT ME! */
  @XmlElement protected String country;

  /** DOCUMENT ME! */
  @XmlElement protected String currency;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected String jurisdictionName;

  /** DOCUMENT ME! */
  @XmlElement protected BigDecimal rate;

  /** DOCUMENT ME! */
  @XmlElement protected String region;

  /** DOCUMENT ME! */
  @XmlElement protected String taxName;

  /** DOCUMENT ME! */
  @XmlElement protected BroadleafEnumerationTypeWrapper taxType;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.order.domain.TaxDetail,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(TaxDetail model, HttpServletRequest request) {
    this.id = model.getId();

    if (model.getType() != null) {
      this.taxType = (BroadleafEnumerationTypeWrapper) context.getBean(BroadleafEnumerationTypeWrapper.class.getName());
      this.taxType.wrapDetails(model.getType(), request);
    }

    this.amount = model.getAmount();
    this.rate   = model.getRate();

    if (model.getCurrency() != null) {
      this.currency = model.getCurrency().getCurrencyCode();
    }

    this.jurisdictionName = model.getJurisdictionName();
    this.taxName          = model.getTaxName();
    this.region           = model.getRegion();
    this.country          = model.getCountry();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.order.domain.TaxDetail,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(TaxDetail model, HttpServletRequest request) {
    wrapDetails(model, request);
  }

} // end class TaxDetailWrapper
