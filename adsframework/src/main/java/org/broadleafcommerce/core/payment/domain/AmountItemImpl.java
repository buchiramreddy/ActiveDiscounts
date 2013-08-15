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

package org.broadleafcommerce.core.payment.domain;

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

import org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
@AdminPresentationMergeOverrides(
  {
    @AdminPresentationMergeOverride(
      name = "",
      mergeEntries =
        @AdminPresentationMergeEntry(
          propertyType         = PropertyType.AdminPresentation.READONLY,
          booleanOverrideValue = true
        )
    )
  }
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_AMOUNT_ITEM")
public class AmountItemImpl implements AmountItem, CurrencyCodeIdentifiable {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "AMOUNT_ITEM_ID")
  @GeneratedValue(generator = "AmountItemId")
  @GenericGenerator(
    name       = "AmountItemId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "AmountItemImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.payment.domain.AmountItemImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AmountItemImpl_Short_Description",
    order        = 1000,
    prominent    = true,
    gridOrder    = 1000
  )
  @Column(
    name     = "SHORT_DESCRIPTION",
    nullable = true
  )
  @Index(
    name        = "SHORT_DESCRIPTION_INDEX",
    columnNames = { "SHORT_DESCRIPTION" }
  )
  protected String shortDescription;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AmountItemImpl_Description",
    order        = 2000
  )
  @Column(name = "DESCRIPTION")
  protected String description;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AmountItemImpl_Unit_Price",
    order        = 3000,
    gridOrder    = 2000,
    prominent    = true,
    fieldType    = SupportedFieldType.MONEY
  )
  @Column(
    name      = "UNIT_PRICE",
    nullable  = false,
    precision = 19,
    scale     = 5
  )
  protected BigDecimal unitPrice;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AmountItemImpl_Quantity",
    order        = 4000,
    prominent    = true,
    gridOrder    = 3000
  )
  @Column(
    name     = "QUANTITY",
    nullable = false
  )
  protected Long quantity;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "AmountItemImpl_SystemId",
    order        = 5000
  )
  @Column(name = "SYSTEM_ID")
  protected String systemId;

  /** DOCUMENT ME! */
  @AdminPresentation(excluded = true)
  @Index(
    name        = "AMOUNTITEM_PAYMENTINFO_INDEX",
    columnNames = { "PAYMENT_ID" }
  )
  @JoinColumn(name = "PAYMENT_ID")
  @ManyToOne(
    targetEntity = PaymentInfoImpl.class,
    optional     = true
  )
  protected PaymentInfo paymentInfo;

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.payment.domain.AmountItem#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.payment.domain.AmountItem#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.payment.domain.AmountItem#getShortDescription()
   */
  @Override public String getShortDescription() {
    return shortDescription;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.payment.domain.AmountItem#setShortDescription(java.lang.String)
   */
  @Override public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.payment.domain.AmountItem#getDescription()
   */
  @Override public String getDescription() {
    return description;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.payment.domain.AmountItem#setDescription(java.lang.String)
   */
  @Override public void setDescription(String description) {
    this.description = description;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.payment.domain.AmountItem#getUnitPrice()
   */
  @Override public BigDecimal getUnitPrice() {
    return unitPrice;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.payment.domain.AmountItem#setUnitPrice(java.math.BigDecimal)
   */
  @Override public void setUnitPrice(BigDecimal unitPrice) {
    this.unitPrice = unitPrice;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.payment.domain.AmountItem#getQuantity()
   */
  @Override public Long getQuantity() {
    return quantity;
  }

  /* (non-Javadoc)
   * @see org.broadleafcommerce.core.payment.domain.AmountItem#setQuantity(java.lang.Long)
   */
  @Override public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.AmountItem#getPaymentInfo()
   */
  @Override public PaymentInfo getPaymentInfo() {
    return paymentInfo;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.AmountItem#setPaymentInfo(org.broadleafcommerce.core.payment.domain.PaymentInfo)
   */
  @Override public void setPaymentInfo(PaymentInfo paymentInfo) {
    this.paymentInfo = paymentInfo;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.AmountItem#getSystemId()
   */
  @Override public String getSystemId() {
    return systemId;
  }

  /**
   * @see  org.broadleafcommerce.core.payment.domain.AmountItem#setSystemId(java.lang.String)
   */
  @Override public void setSystemId(String systemId) {
    this.systemId = systemId;
  }

  /**
   * @see  org.broadleafcommerce.common.currency.util.CurrencyCodeIdentifiable#getCurrencyCode()
   */
  @Override public String getCurrencyCode() {
    return ((CurrencyCodeIdentifiable) paymentInfo).getCurrencyCode();
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((description == null) ? 0 : description.hashCode());
    result = (prime * result) + ((id == null) ? 0 : id.hashCode());
    result = (prime * result) + ((quantity == null) ? 0 : quantity.hashCode());
    result = (prime * result) + ((shortDescription == null) ? 0 : shortDescription.hashCode());
    result = (prime * result) + ((unitPrice == null) ? 0 : unitPrice.hashCode());
    result = (prime * result) + ((systemId == null) ? 0 : systemId.hashCode());

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

    AmountItemImpl other = (AmountItemImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (description == null) {
      if (other.description != null) {
        return false;
      }
    } else if (!description.equals(other.description)) {
      return false;
    }

    if (quantity == null) {
      if (other.quantity != null) {
        return false;
      }
    } else if (!quantity.equals(other.quantity)) {
      return false;
    }

    if (shortDescription == null) {
      if (other.shortDescription != null) {
        return false;
      }
    } else if (!shortDescription.equals(other.shortDescription)) {
      return false;
    }

    if (unitPrice == null) {
      if (other.unitPrice != null) {
        return false;
      }
    } else if (!unitPrice.equals(other.unitPrice)) {
      return false;
    }

    if (systemId == null) {
      if (other.systemId != null) {
        return false;
      }
    } else if (!systemId.equals(other.systemId)) {
      return false;
    }

    return true;
  } // end method equals


} // end class AmountItemImpl
