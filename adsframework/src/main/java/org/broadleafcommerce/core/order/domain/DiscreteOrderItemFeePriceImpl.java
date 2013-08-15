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

package org.broadleafcommerce.core.order.domain;

import java.lang.reflect.Method;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "DiscreteOrderItemFeePriceImpl_baseDiscreteOrderItemFreePrice")
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
@Cache(
  usage  = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE,
  region = "blOrderElements"
)
@DiscriminatorColumn(name = "TYPE")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_DISC_ITEM_FEE_PRICE")
public class DiscreteOrderItemFeePriceImpl implements DiscreteOrderItemFeePrice {
  /** DOCUMENT ME! */
  public static final Log   LOG              = LogFactory.getLog(DiscreteOrderItemFeePriceImpl.class);
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "DISC_ITEM_FEE_PRICE_ID")
  @GeneratedValue(generator = "DiscreteOrderItemFeePriceId")
  @GenericGenerator(
    name       = "DiscreteOrderItemFeePriceId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "DiscreteOrderItemFeePriceImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePriceImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @JoinColumn(name = "ORDER_ITEM_ID")
  @ManyToOne(
    targetEntity = DiscreteOrderItemImpl.class,
    optional     = false
  )
  protected DiscreteOrderItem discreteOrderItem;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "DiscreteOrderItemFeePriceImpl_Amount",
    order        = 2,
    prominent    = true
  )
  @Column(
    name      = "AMOUNT",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal amount;

  @AdminPresentation(
    friendlyName = "DiscreteOrderItemFeePriceImpl_Name",
    order        = 1,
    prominent    = true
  )
  @Column(name = "NAME")
  private String name;

  @AdminPresentation(
    friendlyName = "DiscreteOrderItemFeePriceImpl_Reporting_Code",
    order        = 3,
    prominent    = true
  )
  @Column(name = "REPORTING_CODE")
  private String reportingCode;

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePrice#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePrice#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePrice#getDiscreteOrderItem()
   */
  @Override public DiscreteOrderItem getDiscreteOrderItem() {
    return discreteOrderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePrice#setDiscreteOrderItem(org.broadleafcommerce.core.order.domain.DiscreteOrderItem)
   */
  @Override public void setDiscreteOrderItem(DiscreteOrderItem discreteOrderItem) {
    this.discreteOrderItem = discreteOrderItem;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePrice#getAmount()
   */
  @Override public Money getAmount() {
    return convertToMoney(amount);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePrice#setAmount(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setAmount(Money amount) {
    this.amount = Money.toAmount(amount);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePrice#getName()
   */
  @Override public String getName() {
    return name;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePrice#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePrice#getReportingCode()
   */
  @Override public String getReportingCode() {
    return reportingCode;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePrice#setReportingCode(java.lang.String)
   */
  @Override public void setReportingCode(String reportingCode) {
    this.reportingCode = reportingCode;
  }

  /**
   * DOCUMENT ME!
   *
   * @param   discreteFeePrice  DOCUMENT ME!
   *
   * @throws  CloneNotSupportedException  DOCUMENT ME!
   * @throws  SecurityException           DOCUMENT ME!
   * @throws  NoSuchMethodException       DOCUMENT ME!
   */
  public void checkCloneable(DiscreteOrderItemFeePrice discreteFeePrice) throws CloneNotSupportedException,
    SecurityException, NoSuchMethodException {
    Method cloneMethod = discreteFeePrice.getClass().getMethod("clone", new Class[] {});

    if (cloneMethod.getDeclaringClass().getName().startsWith("org.broadleafcommerce")
          && !discreteFeePrice.getClass().getName().startsWith("org.broadleafcommerce")) {
      // subclass is not implementing the clone method
      throw new CloneNotSupportedException(
        "Custom extensions and implementations should implement clone in order to guarantee split and merge operations are performed accurately");
    }
  }

  /**
   * DOCUMENT ME!
   *
   * @param   amount  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected Money convertToMoney(BigDecimal amount) {
    return (amount == null) ? null : new Money(amount);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.DiscreteOrderItemFeePrice#clone()
   */
  @Override public DiscreteOrderItemFeePrice clone() {
    // instantiate from the fully qualified name via reflection
    DiscreteOrderItemFeePrice clone;

    try {
      clone = (DiscreteOrderItemFeePrice) Class.forName(this.getClass().getName()).newInstance();

      try {
        checkCloneable(clone);
      } catch (CloneNotSupportedException e) {
        LOG.warn("Clone implementation missing in inheritance hierarchy outside of Broadleaf: "
          + clone.getClass().getName(), e);
      }

      clone.setAmount(convertToMoney(amount));
      clone.setName(name);
      clone.setReportingCode(reportingCode);
      clone.setDiscreteOrderItem(discreteOrderItem);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    return clone;
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    final int prime  = 31;
    int       result = 1;
    result = (prime * result) + ((amount == null) ? 0 : amount.hashCode());
    result = (prime * result) + ((discreteOrderItem == null) ? 0 : discreteOrderItem.hashCode());
    result = (prime * result) + ((id == null) ? 0 : id.hashCode());
    result = (prime * result) + ((name == null) ? 0 : name.hashCode());
    result = (prime * result) + ((reportingCode == null) ? 0 : reportingCode.hashCode());

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

    DiscreteOrderItemFeePriceImpl other = (DiscreteOrderItemFeePriceImpl) obj;

    if (amount == null) {
      if (other.amount != null) {
        return false;
      }
    } else if (!amount.equals(other.amount)) {
      return false;
    }

    if (discreteOrderItem == null) {
      if (other.discreteOrderItem != null) {
        return false;
      }
    } else if (!discreteOrderItem.equals(other.discreteOrderItem)) {
      return false;
    }

    if (id == null) {
      if (other.id != null) {
        return false;
      }
    } else if (!id.equals(other.id)) {
      return false;
    }

    if (name == null) {
      if (other.name != null) {
        return false;
      }
    } else if (!name.equals(other.name)) {
      return false;
    }

    if (reportingCode == null) {
      if (other.reportingCode != null) {
        return false;
      }
    } else if (!reportingCode.equals(other.reportingCode)) {
      return false;
    }

    return true;
  } // end method equals
} // end class DiscreteOrderItemFeePriceImpl
