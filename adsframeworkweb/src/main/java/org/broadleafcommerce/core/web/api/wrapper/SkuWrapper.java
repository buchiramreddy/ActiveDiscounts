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

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.util.xml.ISO8601DateAdapter;

import org.broadleafcommerce.core.catalog.domain.Sku;


/**
 * This is a JAXB wrapper to wrap Sku.
 *
 * <p>User: Kelly Tisdell Date: 4/10/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "sku")
public class SkuWrapper extends BaseWrapper implements APIWrapper<Sku> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected Boolean active;

  /** DOCUMENT ME! */
  @XmlElement
  @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
  protected Date             activeEndDate;

  /** DOCUMENT ME! */
  @XmlElement
  @XmlJavaTypeAdapter(ISO8601DateAdapter.class)
  protected Date             activeStartDate;

  /** DOCUMENT ME! */
  @XmlElement protected String description;

  /** DOCUMENT ME! */
  @XmlElement protected DimensionWrapper dimension;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected String name;

  /** DOCUMENT ME! */
  @XmlElement protected Money retailPrice;

  /** DOCUMENT ME! */
  @XmlElement protected Money salePrice;

  /** DOCUMENT ME! */
  @XmlElement protected WeightWrapper weight;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.catalog.domain.Sku,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(Sku model, HttpServletRequest request) {
    this.id              = model.getId();
    this.activeStartDate = model.getActiveStartDate();
    this.activeEndDate   = model.getActiveEndDate();
    this.name            = model.getName();
    this.description     = model.getDescription();
    this.retailPrice     = model.getRetailPrice();
    this.salePrice       = model.getSalePrice();
    this.active          = model.isActive();

    if (model.getWeight() != null) {
      weight = (WeightWrapper) context.getBean(WeightWrapper.class.getName());
      weight.wrapDetails(model.getWeight(), request);
    }

    if (model.getDimension() != null) {
      dimension = (DimensionWrapper) context.getBean(DimensionWrapper.class.getName());
      dimension.wrapDetails(model.getDimension(), request);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.catalog.domain.Sku,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(Sku model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class SkuWrapper
