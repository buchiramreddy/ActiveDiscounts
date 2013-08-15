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
/**
 *
 */
package org.broadleafcommerce.core.catalog.domain;

import java.math.BigDecimal;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrencyImpl;
import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.broadleafcommerce.core.catalog.service.type.SkuFeeType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;


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
@Table(name = "BLC_SKU_FEE")
public class SkuFeeImpl implements SkuFee {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "SKU_FEE_ID")
  @GeneratedValue(generator = "SkuFeeId")
  @GenericGenerator(
    name       = "SkuFeeId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "SkuFeeImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.order.domain.SkuFeeImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @Column(name = "NAME")
  protected String name;

  /** DOCUMENT ME! */
  @Column(name = "DESCRIPTION")
  protected String description;

  /** DOCUMENT ME! */
  @Column(
    name      = "AMOUNT",
    precision = 19,
    scale     = 5,
    nullable  = false
  )
  protected BigDecimal amount;

  /** DOCUMENT ME! */
  @Column(name = "TAXABLE")
  protected Boolean taxable = Boolean.FALSE;

  /** DOCUMENT ME! */
  @Column(
    name   = "EXPRESSION",
    length = Integer.MAX_VALUE - 1
  )
  @Lob
  @Type(type = "org.hibernate.type.StringClobType")
  protected String      expression;

  /** DOCUMENT ME! */
  @AdminPresentation(
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.catalog.service.type.SkuFeeType"
  )
  @Column(name = "FEE_TYPE")
  protected String feeType = SkuFeeType.FULFILLMENT.getType();

  /** DOCUMENT ME! */
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @JoinTable(
    name               = "BLC_SKU_FEE_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "SKU_FEE_ID",
        referencedColumnName = "SKU_FEE_ID",
        nullable       = true
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "SKU_ID",
        referencedColumnName = "SKU_ID",
        nullable             = true
      )
  )
  @ManyToMany(
    fetch        = FetchType.LAZY,
    targetEntity = SkuImpl.class
  )
  protected List<Sku> skus;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "TaxDetailImpl_Currency_Code",
    order        = 1,
    group        = "FixedPriceFulfillmentOptionImpl_Details",
    prominent    = true
  )
  @JoinColumn(name = "CURRENCY_CODE")
  @ManyToOne(targetEntity = BroadleafCurrencyImpl.class)
  protected BroadleafCurrency currency;


  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#getName()
   */
  @Override public String getName() {
    return name;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#getDescription()
   */
  @Override public String getDescription() {
    return description;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#setDescription(java.lang.String)
   */
  @Override public void setDescription(String description) {
    this.description = description;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#getAmount()
   */
  @Override public Money getAmount() {
    return BroadleafCurrencyUtils.getMoney(amount, getCurrency());
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#setAmount(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setAmount(Money amount) {
    this.amount = Money.toAmount(amount);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#getTaxable()
   */
  @Override public Boolean getTaxable() {
    return taxable;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#setTaxable(java.lang.Boolean)
   */
  @Override public void setTaxable(Boolean taxable) {
    this.taxable = taxable;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#getExpression()
   */
  @Override public String getExpression() {
    return expression;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#setExpression(java.lang.String)
   */
  @Override public void setExpression(String expression) {
    this.expression = expression;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#getFeeType()
   */
  @Override public SkuFeeType getFeeType() {
    return SkuFeeType.getInstance(feeType);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#setFeeType(org.broadleafcommerce.core.catalog.service.type.SkuFeeType)
   */
  @Override public void setFeeType(SkuFeeType feeType) {
    this.feeType = (feeType == null) ? null : feeType.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#getSkus()
   */
  @Override public List<Sku> getSkus() {
    return skus;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#setSkus(java.util.List)
   */
  @Override public void setSkus(List<Sku> skus) {
    this.skus = skus;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#getCurrency()
   */
  @Override public BroadleafCurrency getCurrency() {
    return currency;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.SkuFee#setCurrency(org.broadleafcommerce.common.currency.domain.BroadleafCurrency)
   */
  @Override public void setCurrency(BroadleafCurrency currency) {
    this.currency = currency;
  }
} // end class SkuFeeImpl
