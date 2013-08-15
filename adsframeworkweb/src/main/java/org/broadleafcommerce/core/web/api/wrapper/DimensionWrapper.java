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

import javax.xml.bind.annotation.XmlElement;

import org.broadleafcommerce.core.catalog.domain.Dimension;


/**
 * JAXB wrapper for Dimension.
 *
 * <p>User: Kelly Tisdell Date: 4/10/12</p>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class DimensionWrapper extends BaseWrapper implements APIWrapper<Dimension> {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @XmlElement protected String container;

  /** DOCUMENT ME! */
  @XmlElement protected BigDecimal depth;

  /** DOCUMENT ME! */
  @XmlElement protected String dimensionUnitOfMeasure;

  /** DOCUMENT ME! */
  @XmlElement protected BigDecimal girth;

  /** DOCUMENT ME! */
  @XmlElement protected BigDecimal height;

  /** DOCUMENT ME! */
  @XmlElement protected String size;

  /** DOCUMENT ME! */
  @XmlElement protected BigDecimal width;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapDetails(org.broadleafcommerce.core.catalog.domain.Dimension,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapDetails(Dimension model, HttpServletRequest request) {
    this.width  = model.getWidth();
    this.depth  = model.getDepth();
    this.height = model.getHeight();
    this.girth  = model.getGirth();

    if (model.getDimensionUnitOfMeasure() != null) {
      this.dimensionUnitOfMeasure = model.getDimensionUnitOfMeasure().getType();
    }

    if (model.getSize() != null) {
      this.size = model.getSize().getType();
    }

    if (model.getContainer() != null) {
      this.container = model.getContainer().getType();
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.web.api.wrapper.APIWrapper#wrapSummary(org.broadleafcommerce.core.catalog.domain.Dimension,
   *       javax.servlet.http.HttpServletRequest)
   */
  @Override public void wrapSummary(Dimension model, HttpServletRequest request) {
    wrapDetails(model, request);
  }
} // end class DimensionWrapper
