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
@Table(name = "BLC_FULFILLMENT_PRICE_BAND")
public class FulfillmentPriceBandImpl extends FulfillmentBandImpl implements FulfillmentPriceBand {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "FULFILLMENT_PRICE_BAND_ID")
  @GeneratedValue(generator = "FulfillmentPriceBandId")
  @GenericGenerator(
    name       = "FulfillmentPriceBandId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "FulfillmentPriceBandImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentPriceBandImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(
    name      = "RETAIL_PRICE_MINIMUM_AMOUNT",
    precision = 19,
    scale     = 5,
    nullable  = false
  )
  protected BigDecimal retailPriceMinimumAmount;

  /** DOCUMENT ME! */
  @JoinColumn(name = "FULFILLMENT_OPTION_ID")
  @ManyToOne(targetEntity = BandedPriceFulfillmentOptionImpl.class)
  protected BandedPriceFulfillmentOption option;

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
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentPriceBand#getRetailPriceMinimumAmount()
   */
  @Override public BigDecimal getRetailPriceMinimumAmount() {
    return retailPriceMinimumAmount;
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentPriceBand#setRetailPriceMinimumAmount(java.math.BigDecimal)
   */
  @Override public void setRetailPriceMinimumAmount(BigDecimal retailPriceMinimumAmount) {
    this.retailPriceMinimumAmount = retailPriceMinimumAmount;
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentPriceBand#getOption()
   */
  @Override public BandedPriceFulfillmentOption getOption() {
    return option;
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentPriceBand#setOption(org.broadleafcommerce.core.order.fulfillment.domain.BandedPriceFulfillmentOption)
   */
  @Override public void setOption(BandedPriceFulfillmentOption option) {
    this.option = option;
  }

} // end class FulfillmentPriceBandImpl
