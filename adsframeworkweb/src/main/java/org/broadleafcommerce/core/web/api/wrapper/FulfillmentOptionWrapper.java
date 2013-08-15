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

import org.broadleafcommerce.core.order.domain.FulfillmentOption;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "fulfillmentOption")
public class FulfillmentOptionWrapper extends BaseWrapper implements APIWrapper<FulfillmentOption> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected String description;

  /** DOCUMENT ME! */
  @XmlElement protected BroadleafEnumerationTypeWrapper fulfillmentType;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected String name;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Long getId() {
    return this.id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.order.domain.FulfillmentOption,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(FulfillmentOption model, HttpServletRequest request) {
    this.id = model.getId();

    if (model.getFulfillmentType() != null) {
      this.fulfillmentType = (BroadleafEnumerationTypeWrapper) context.getBean(BroadleafEnumerationTypeWrapper.class
          .getName());
      this.fulfillmentType.wrapDetails(model.getFulfillmentType(), request);
    }

    this.name        = model.getName();
    this.description = model.getLongDescription();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.order.domain.FulfillmentOption,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(FulfillmentOption model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class FulfillmentOptionWrapper
