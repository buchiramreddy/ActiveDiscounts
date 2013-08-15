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

package org.broadleafcommerce.core.catalog.domain;

import java.io.Serializable;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.util.WeightUnitOfMeasureType;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@Embeddable public class Weight implements Serializable {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "ProductWeight_Product_Weight",
    order        = 8000,
    tab          = ProductImpl.Presentation.Tab.Name.Shipping,
    tabOrder     = ProductImpl.Presentation.Tab.Order.Shipping,
    group        = ProductImpl.Presentation.Group.Name.Shipping,
    groupOrder   = ProductImpl.Presentation.Group.Order.Shipping
  )
  @Column(name = "WEIGHT")
  protected BigDecimal weight;


  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "ProductWeight_Product_Weight_Units",
    order                = 9000,
    tab                  = ProductImpl.Presentation.Tab.Name.Shipping,
    tabOrder             = ProductImpl.Presentation.Tab.Order.Shipping,
    group                = ProductImpl.Presentation.Group.Name.Shipping,
    groupOrder           = ProductImpl.Presentation.Group.Order.Shipping,
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.common.util.WeightUnitOfMeasureType"
  )
  @Column(name = "WEIGHT_UNIT_OF_MEASURE")
  protected String weightUnitOfMeasure;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public WeightUnitOfMeasureType getWeightUnitOfMeasure() {
    return WeightUnitOfMeasureType.getInstance(weightUnitOfMeasure);
  }

  /**
   * DOCUMENT ME!
   *
   * @param  weightUnitOfMeasure  DOCUMENT ME!
   */
  public void setWeightUnitOfMeasure(WeightUnitOfMeasureType weightUnitOfMeasure) {
    if (weightUnitOfMeasure != null) {
      this.weightUnitOfMeasure = weightUnitOfMeasure.getType();
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public BigDecimal getWeight() {
    return weight;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  weight  DOCUMENT ME!
   */
  public void setWeight(BigDecimal weight) {
    this.weight = weight;
  }

} // end class Weight
