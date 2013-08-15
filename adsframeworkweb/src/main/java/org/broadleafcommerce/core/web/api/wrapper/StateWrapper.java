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

import org.broadleafcommerce.profile.core.domain.State;
import org.broadleafcommerce.profile.core.service.StateService;

import org.springframework.context.ApplicationContext;


/**
 * This is a JAXB wrapper around State.
 *
 * <p>User: Elbert Bautista Date: 4/10/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "state")
public class StateWrapper extends BaseWrapper implements APIWrapper<State>, APIUnwrapper<State> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected String abbreviation;

  /** DOCUMENT ME! */
  @XmlElement protected String name;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIUnwrapper#unwrap(javax.servlet.http.HttpServletRequest, org.springframework.context.ApplicationContext)
   */
  @Override public State unwrap(HttpServletRequest request, ApplicationContext appContext) {
    StateService stateService = (StateService) appContext.getBean("blStateService");

    if (this.abbreviation != null) {
      State state = stateService.findStateByAbbreviation(this.abbreviation);

      return state;
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.profile.core.domain.State,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(State model, HttpServletRequest request) {
    this.name         = model.getName();
    this.abbreviation = model.getAbbreviation();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.profile.core.domain.State,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(State model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class StateWrapper
