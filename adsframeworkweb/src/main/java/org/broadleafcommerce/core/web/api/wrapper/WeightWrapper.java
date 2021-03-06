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

import org.broadleafcommerce.core.catalog.domain.Weight;


/**
 * This is a JAXB wrapper around Weight.
 *
 * <p>User: Kelly Tisdell Date: 4/10/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@XmlAccessorType(value = XmlAccessType.FIELD)
@XmlRootElement(name = "weight")
public class WeightWrapper implements APIWrapper<Weight> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected String unitOfMeasure;

  /** DOCUMENT ME! */
  @XmlElement protected BigDecimal weight;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.catalog.domain.Weight,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(Weight model, HttpServletRequest request) {
    this.weight = model.getWeight();

    if (model.getWeightUnitOfMeasure() != null) {
      this.unitOfMeasure = model.getWeightUnitOfMeasure().getType();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.catalog.domain.Weight,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(Weight model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class WeightWrapper
