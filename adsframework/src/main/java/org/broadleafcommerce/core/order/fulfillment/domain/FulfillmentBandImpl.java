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
import javax.persistence.MappedSuperclass;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.broadleafcommerce.core.order.service.type.FulfillmentBandResultAmountType;


/**
 * DOCUMENT ME!
 *
 * @author   Phillip Verheyden
 * @see      {@link org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentPriceBandImpl},
 *           {@link org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentWeightBandImpl}
 * @version  $Revision$, $Date$
 */
@MappedSuperclass public abstract class FulfillmentBandImpl implements FulfillmentBand {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(
    name      = "RESULT_AMOUNT",
    precision = 19,
    scale     = 5,
    nullable  = false
  )
  protected BigDecimal resultAmount;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "Result Type",
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.order.service.type.FulfillmentBandResultAmountType"
  )
  @Column(
    name     = "RESULT_AMOUNT_TYPE",
    nullable = false
  )
  protected String resultAmountType = FulfillmentBandResultAmountType.RATE.getType();

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentBand#getResultAmount()
   */
  @Override public BigDecimal getResultAmount() {
    return resultAmount;
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentBand#setResultAmount(java.math.BigDecimal)
   */
  @Override public void setResultAmount(BigDecimal resultAmount) {
    this.resultAmount = resultAmount;
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentBand#getResultAmountType()
   */
  @Override public FulfillmentBandResultAmountType getResultAmountType() {
    return FulfillmentBandResultAmountType.getInstance(resultAmountType);
  }

  /**
   * @see  org.broadleafcommerce.core.order.fulfillment.domain.FulfillmentBand#setResultAmountType(org.broadleafcommerce.core.order.service.type.FulfillmentBandResultAmountType)
   */
  @Override public void setResultAmountType(FulfillmentBandResultAmountType resultAmountType) {
    this.resultAmountType = resultAmountType.getType();
  }

} // end class FulfillmentBandImpl
