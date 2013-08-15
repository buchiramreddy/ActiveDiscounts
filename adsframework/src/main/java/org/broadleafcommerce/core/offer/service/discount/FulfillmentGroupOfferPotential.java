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

package org.broadleafcommerce.core.offer.service.discount;

import org.broadleafcommerce.common.money.BankersRounding;
import org.broadleafcommerce.common.money.Money;

import org.broadleafcommerce.core.offer.domain.Offer;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class FulfillmentGroupOfferPotential {
  /** DOCUMENT ME! */
  protected Offer offer;

  /** DOCUMENT ME! */
  protected Money totalSavings = new Money(BankersRounding.zeroAmount());

  /** DOCUMENT ME! */
  protected int   priority;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Offer getOffer() {
    return offer;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  offer  DOCUMENT ME!
   */
  public void setOffer(Offer offer) {
    this.offer = offer;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Money getTotalSavings() {
    return totalSavings;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  totalSavings  DOCUMENT ME!
   */
  public void setTotalSavings(Money totalSavings) {
    this.totalSavings = totalSavings;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public int getPriority() {
    return priority;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  priority  DOCUMENT ME!
   */
  public void setPriority(int priority) {
    this.priority = priority;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((offer == null) ? 0 : offer.hashCode());

    return result;
  }

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (obj == null) {
      return false;
    }

    if (getClass() != obj.getClass()) {
      return false;
    }

    FulfillmentGroupOfferPotential other = (FulfillmentGroupOfferPotential) obj;

    if (offer == null) {
      if (other.offer != null) {
        return false;
      }
    } else if (!offer.equals(other.offer)) {
      return false;
    }

    return true;
  } // end method equals

} // end class FulfillmentGroupOfferPotential
