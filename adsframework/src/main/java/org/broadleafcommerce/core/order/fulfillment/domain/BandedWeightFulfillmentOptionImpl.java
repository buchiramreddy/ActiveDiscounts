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

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentationClass;

import org.broadleafcommerce.core.order.domain.FulfillmentOptionImpl;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;


/**
 * DOCUMENT ME!
 *
 * @author   Phillip Verheyden
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "Banded Weight Fulfillment Option")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_FULFILLMENT_OPT_BANDED_WGT")
public class BandedWeightFulfillmentOptionImpl extends FulfillmentOptionImpl implements BandedWeightFulfillmentOption {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @OneToMany(
    mappedBy     = "option",
    targetEntity = FulfillmentWeightBandImpl.class
  )
  protected List<FulfillmentWeightBand> bands;

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.BandedWeightFulfillmentOption#getBands()
   */
  @Override public List<FulfillmentWeightBand> getBands() {
    return bands;
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.BandedWeightFulfillmentOption#setBands(java.util.List)
   */
  @Override public void setBands(List<FulfillmentWeightBand> bands) {
    this.bands = bands;
  }

} // end class BandedWeightFulfillmentOptionImpl
