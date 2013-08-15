/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.core.order.fulfillment.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.util.WeightUnitOfMeasureType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_FULFILLMENT_WEIGHT_BAND")
public class FulfillmentWeightBandImpl extends FulfillmentBandImpl implements FulfillmentWeightBand {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "FULFILLMENT_WEIGHT_BAND_ID")
  @GeneratedValue(generator = "FulfillmentWeightBandId")
  @GenericGenerator(
    name       = "FulfillmentWeightBandId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "FulfillmentWeightBandImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentWeightBandImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(friendlyName = "FulfillmentWeightBandImpl_Weight")
  @Column(
    name      = "MINIMUM_WEIGHT",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal minimumWeight;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "FulfillmentWeightBandImpl_Weight_Units",
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.common.util.WeightUnitOfMeasureType"
  )
  @Column(name = "WEIGHT_UNIT_OF_MEASURE")
  protected String weightUnitOfMeasure;

  /** DOCUMENT ME! */
  @JoinColumn(name = "FULFILLMENT_OPTION_ID")
  @ManyToOne(targetEntity = BandedWeightFulfillmentOptionImpl.class)
  protected BandedWeightFulfillmentOption option;

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentBand#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentBand#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentWeightBand#getMinimumWeight()
   */
  @Override public BigDecimal getMinimumWeight() {
    return minimumWeight;
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentWeightBand#setMinimumWeight(java.math.BigDecimal)
   */
  @Override public void setMinimumWeight(BigDecimal minimumWeight) {
    this.minimumWeight = minimumWeight;
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentWeightBand#getOption()
   */
  @Override public BandedWeightFulfillmentOption getOption() {
    return option;
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentWeightBand#setOption(org.broadleafcommerce.core.order.fulfillment.domain.BandedWeightFulfillmentOption)
   */
  @Override public void setOption(BandedWeightFulfillmentOption option) {
    this.option = option;
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentWeightBand#getWeightUnitOfMeasure()
   */
  @Override public WeightUnitOfMeasureType getWeightUnitOfMeasure() {
    return WeightUnitOfMeasureType.getInstance(weightUnitOfMeasure);
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentWeightBand#setWeightUnitOfMeasure(org.broadleafcommerce.common.util.WeightUnitOfMeasureType)
   */
  @Override public void setWeightUnitOfMeasure(WeightUnitOfMeasureType weightUnitOfMeasure) {
    if (weightUnitOfMeasure != null) {
      this.weightUnitOfMeasure = weightUnitOfMeasure.getType();
    }
  }

} // end class FulfillmentWeightBandImpl
