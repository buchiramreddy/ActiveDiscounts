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

package org.broadleafcommerce.core.offer.service.discount.domain;

import java.math.RoundingMode;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public abstract class AbstractPromotionRounding implements PromotionRounding {
  /** DOCUMENT ME! */
  protected boolean      roundOfferValues = true;

  /** DOCUMENT ME! */
  protected int          roundingScale = 2;

  /** DOCUMENT ME! */
  protected RoundingMode roundingMode = RoundingMode.HALF_EVEN;

  /**
   * It is sometimes problematic to offer percentage-off offers with regards to rounding. For example, consider an item
   * that costs 9.99 and has a 50% promotion. To be precise, the offer value is 4.995, but this may be a strange value
   * to display to the user depending on the currency being used.
   *
   * @return  it is sometimes problematic to offer percentage-off offers with regards to rounding.
   */
  @Override public boolean isRoundOfferValues() {
    return roundOfferValues;
  }

  /**
   *
   * DOCUMENT ME!
   *
   * @see    #isRoundOfferValues()
   *
   * @param  roundingScale  DOCUMENT ME!
   */
  public void setRoundingScale(int roundingScale) {
    this.roundingScale = roundingScale;
  }

  /**
   *
   * DOCUMENT ME!
   *
   * @see    #isRoundOfferValues()
   *
   * @param  roundingMode  DOCUMENT ME!
   */
  public void setRoundingMode(RoundingMode roundingMode) {
    this.roundingMode = roundingMode;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotionRounding#getRoundingMode()
   */
  @Override public RoundingMode getRoundingMode() {
    return roundingMode;
  }

  /**
   * @see  org.broadleafcommerce.core.offer.service.discount.domain.PromotionRounding#getRoundingScale()
   */
  @Override public int getRoundingScale() {
    return roundingScale;
  }

} // end class AbstractPromotionRounding
