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

package org.broadleafcommerce.core.offer.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_OFFER_AUDIT")
public class OfferAuditImpl implements OfferAudit {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final long serialVersionUID = 1L;

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Column(name = "CUSTOMER_ID")
  @Index(
    name        = "OFFERAUDIT_CUSTOMER_INDEX",
    columnNames = { "CUSTOMER_ID" }
  )
  protected Long customerId;

  /** DOCUMENT ME! */
  @Column(name = "OFFER_AUDIT_ID")
  @GeneratedValue(generator = "OfferAuditId")
  @GenericGenerator(
    name       = "OfferAuditId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "OfferAuditImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.offer.domain.OfferAuditImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(name = "OFFER_ID")
  @Index(
    name        = "OFFERAUDIT_OFFER_INDEX",
    columnNames = { "OFFER_ID" }
  )
  protected Long offerId;

  /** DOCUMENT ME! */
  @Column(name = "ORDER_ID")
  @Index(
    name        = "OFFERAUDIT_ORDER_INDEX",
    columnNames = { "ORDER_ID" }
  )
  protected Long orderId;

  /** DOCUMENT ME! */
  @Column(name = "REDEEMED_DATE")
  protected Date redeemedDate;

  //~ Methods ----------------------------------------------------------------------------------------------------------

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

    OfferAuditImpl other = (OfferAuditImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (customerId == null) {
      if (other.customerId != null) {
        return false;
      }
    } else if (!customerId.equals(other.customerId)) {
      return false;
    }

    if (offerId == null) {
      if (other.offerId != null) {
        return false;
      }
    } else if (!offerId.equals(other.offerId)) {
      return false;
    }

    if (redeemedDate == null) {
      if (other.redeemedDate != null) {
        return false;
      }
    } else if (!redeemedDate.equals(other.redeemedDate)) {
      return false;
    }

    if (orderId == null) {
      if (other.orderId != null) {
        return false;
      }
    } else if (!orderId.equals(other.orderId)) {
      return false;
    }

    return true;
  } // end method equals

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferAudit#getCustomerId()
   */
  @Override public Long getCustomerId() {
    return customerId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferAudit#getId()
   */
  @Override public Long getId() {
    return id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferAudit#getOfferId()
   */
  @Override public Long getOfferId() {
    return offerId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferAudit#getOrderId()
   */
  @Override public Long getOrderId() {
    return orderId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferAudit#getRedeemedDate()
   */
  @Override public Date getRedeemedDate() {
    return redeemedDate;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((customerId == null) ? 0 : customerId.hashCode());
    result = (prime * result) + ((offerId == null) ? 0 : offerId.hashCode());
    result = (prime * result) + ((redeemedDate == null) ? 0 : redeemedDate.hashCode());
    result = (prime * result) + ((orderId == null) ? 0 : orderId.hashCode());

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferAudit#setCustomerId(java.lang.Long)
   */
  @Override public void setCustomerId(Long customerId) {
    this.customerId = customerId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferAudit#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferAudit#setOfferId(java.lang.Long)
   */
  @Override public void setOfferId(Long offerId) {
    this.offerId = offerId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferAudit#setOrderId(java.lang.Long)
   */
  @Override public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.offer.domain.OfferAudit#setRedeemedDate(java.util.Date)
   */
  @Override public void setRedeemedDate(Date redeemedDate) {
    this.redeemedDate = redeemedDate;
  }
} // end class OfferAuditImpl
