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

import org.broadleafcommerce.common.config.domain.AbstractModuleConfiguration;
import org.broadleafcommerce.common.config.domain.ModuleConfiguration;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrencyImpl;
import org.broadleafcommerce.common.currency.util.BroadleafCurrencyUtils;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;

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
@AdminPresentationClass(friendlyName = "TaxDetailImpl_baseTaxDetail")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blOrderElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_TAX_DETAIL")
public class TaxDetailImpl implements TaxDetail {
  private static final long serialVersionUID = -4036994446393527252L;

  /** DOCUMENT ME! */
  @Column(name = "TAX_DETAIL_ID")
  @GeneratedValue(generator = "TaxDetailId")
  @GenericGenerator(
    name       = "TaxDetailId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "TaxDetailImpl"
      ),
      @Parameter(
        name   = "increment_size",
        value  = "150"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.catalog.domain.TaxDetailImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "TaxDetailImpl_Tax_Type",
    order        = 1,
    group        = "TaxDetailImpl_Tax_Detail"
  )
  @Column(name = "TYPE")
  protected String type;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "TaxDetailImpl_Tax_Amount",
    order        = 2,
    group        = "TaxDetailImpl_Tax_Detail"
  )
  @Column(
    name      = "AMOUNT",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal amount;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "TaxDetailImpl_Tax_Rate",
    order        = 3,
    group        = "TaxDetailImpl_Tax_Detail"
  )
  @Column(
    name      = "RATE",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal rate;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "TaxDetailImpl_Tax_Jurisdiction_Name",
    order        = 4,
    group        = "TaxDetailImpl_Tax_Detail"
  )
  @Column(name = "JURISDICTION_NAME")
  protected String jurisdictionName;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "TaxDetailImpl_Tax_Country",
    order        = 5,
    group        = "TaxDetailImpl_Tax_Detail"
  )
  @Column(name = "TAX_COUNTRY")
  protected String country;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "TaxDetailImpl_Tax_Region",
    order        = 6,
    group        = "TaxDetailImpl_Tax_Detail"
  )
  @Column(name = "TAX_REGION")
  protected String region;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "TaxDetailImpl_Tax_Name",
    order        = 7,
    group        = "TaxDetailImpl_Tax_Detail"
  )
  @Column(name = "TAX_NAME")
  protected String taxName;

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

  /** DOCUMENT ME! */
  @JoinColumn(name = "MODULE_CONFIG_ID")
  @ManyToOne(targetEntity = AbstractModuleConfiguration.class)
  protected ModuleConfiguration moduleConfiguation;

  /**
   * Creates a new TaxDetailImpl object.
   */
  public TaxDetailImpl() { }

  /**
   * Creates a new TaxDetailImpl object.
   *
   * @param  type    DOCUMENT ME!
   * @param  amount  DOCUMENT ME!
   * @param  rate    DOCUMENT ME!
   */
  public TaxDetailImpl(TaxType type, Money amount, BigDecimal rate) {
    this.type   = type.getType();
    this.amount = amount.getAmount();
    this.rate   = rate;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#getType()
   */
  @Override public TaxType getType() {
    return TaxType.getInstance(this.type);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#setType(org.broadleafcommerce.core.order.domain.TaxType)
   */
  @Override public void setType(TaxType type) {
    this.type = type.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#getAmount()
   */
  @Override public Money getAmount() {
    return BroadleafCurrencyUtils.getMoney(amount, currency);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#setAmount(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setAmount(Money amount) {
    this.amount = amount.getAmount();
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#getRate()
   */
  @Override public BigDecimal getRate() {
    return rate;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#setRate(java.math.BigDecimal)
   */
  @Override public void setRate(BigDecimal rate) {
    this.rate = rate;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#getCurrency()
   */
  @Override public BroadleafCurrency getCurrency() {
    return currency;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#setCurrency(org.broadleafcommerce.common.currency.domain.BroadleafCurrency)
   */
  @Override public void setCurrency(BroadleafCurrency currency) {
    this.currency = currency;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#getModuleConfiguration()
   */
  @Override public ModuleConfiguration getModuleConfiguration() {
    return this.moduleConfiguation;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#setModuleConfiguration(org.broadleafcommerce.common.config.domain.ModuleConfiguration)
   */
  @Override public void setModuleConfiguration(ModuleConfiguration config) {
    this.moduleConfiguation = config;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#setJurisdictionName(java.lang.String)
   */
  @Override public void setJurisdictionName(String jurisdiction) {
    this.jurisdictionName = jurisdiction;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#getJurisdictionName()
   */
  @Override public String getJurisdictionName() {
    return this.jurisdictionName;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#setTaxName(java.lang.String)
   */
  @Override public void setTaxName(String taxName) {
    this.taxName = taxName;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#getTaxName()
   */
  @Override public String getTaxName() {
    return this.taxName;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#setRegion(java.lang.String)
   */
  @Override public void setRegion(String region) {
    this.region = region;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#getRegion()
   */
  @Override public String getRegion() {
    return this.region;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#setCountry(java.lang.String)
   */
  @Override public void setCountry(String country) {
    this.country = country;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.TaxDetail#getCountry()
   */
  @Override public String getCountry() {
    return this.country;
  }

} // end class TaxDetailImpl
