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

import org.broadleafcommerce.profile.core.domain.CustomerAttribute;
import org.broadleafcommerce.profile.core.domain.CustomerAttributeImpl;

import org.springframework.context.ApplicationContext;


/**
 * API wrapper to wrap Customer Attributes.
 *
 * @author   Priyesh Patel
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "customerAttribute")
public class CustomerAttributeWrapper extends BaseWrapper implements APIWrapper<CustomerAttribute>,
  APIUnwrapper<CustomerAttribute> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected Long customerId;

  /** DOCUMENT ME! */
  @XmlElement protected Long id;

  /** DOCUMENT ME! */
  @XmlElement protected String name;

  /** DOCUMENT ME! */
  @XmlElement protected String value;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIUnwrapper#unwrap(javax.servlet.http.HttpServletRequest, org.springframework.context.ApplicationContext)
   */
  @Override public CustomerAttribute unwrap(HttpServletRequest request, ApplicationContext context) {
    CustomerAttribute attribute = new CustomerAttributeImpl();
    attribute.setId(id);
    attribute.setName(name);
    attribute.setValue(value);

    return attribute;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.profile.core.domain.CustomerAttribute,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(CustomerAttribute model, HttpServletRequest request) {
    this.id         = model.getId();
    this.name       = model.getName();
    this.value      = model.getValue();
    this.customerId = model.getCustomer().getId();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.profile.core.domain.CustomerAttribute,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(CustomerAttribute model, HttpServletRequest request) {
    wrapDetails(model, request);
  }

} // end class CustomerAttributeWrapper
