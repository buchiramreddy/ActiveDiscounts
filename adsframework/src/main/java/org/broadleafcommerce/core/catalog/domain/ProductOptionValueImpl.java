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

package org.broadleafcommerce.core.catalog.domain;

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

import org.broadleafcommerce.common.i18n.service.DynamicTranslationProvider;
import org.broadleafcommerce.common.money.Money;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.broadleafcommerce.core.catalog.service.dynamic.DynamicSkuPrices;
import org.broadleafcommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext;

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
@AdminPresentationClass(friendlyName = "Product Option Value")
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PRODUCT_OPTION_VALUE")
public class ProductOptionValueImpl implements ProductOptionValue {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "PRODUCT_OPTION_VALUE_ID")
  @GeneratedValue(generator = "ProductOptionValueId")
  @GenericGenerator(
    name       = "ProductOptionValueId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "ProductOptionValueImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.catalog.domain.ProductOptionValueImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "productOptionValue_attributeValue",
    prominent    = true,
    translatable = true
  )
  @Column(name = "ATTRIBUTE_VALUE")
  protected String attributeValue;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "productOptionValue_displayOrder",
    prominent    = true
  )
  @Column(name = "DISPLAY_ORDER")
  protected Long displayOrder;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "productOptionValue_adjustment",
    fieldType    = SupportedFieldType.MONEY,
    prominent    = true
  )
  @Column(
    name      = "PRICE_ADJUSTMENT",
    precision = 19,
    scale     = 5
  )
  protected BigDecimal priceAdjustment;

  /** DOCUMENT ME! */
  @JoinColumn(name = "PRODUCT_OPTION_ID")
  @ManyToOne(targetEntity = ProductOptionImpl.class)
  protected ProductOption productOption;

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOptionValue#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOptionValue#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOptionValue#getAttributeValue()
   */
  @Override public String getAttributeValue() {
    return DynamicTranslationProvider.getValue(this, "attributeValue", attributeValue);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOptionValue#setAttributeValue(java.lang.String)
   */
  @Override public void setAttributeValue(String attributeValue) {
    this.attributeValue = attributeValue;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOptionValue#getDisplayOrder()
   */
  @Override public Long getDisplayOrder() {
    return displayOrder;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOptionValue#setDisplayOrder(java.lang.Long)
   */
  @Override public void setDisplayOrder(Long displayOrder) {
    this.displayOrder = displayOrder;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOptionValue#getPriceAdjustment()
   */
  @Override public Money getPriceAdjustment() {
    Money returnPrice = null;

    if (SkuPricingConsiderationContext.hasDynamicPricing()) {
      DynamicSkuPrices dynamicPrices = SkuPricingConsiderationContext.getSkuPricingService().getPriceAdjustment(this,
          (priceAdjustment == null) ? null : new Money(priceAdjustment),
          SkuPricingConsiderationContext.getSkuPricingConsiderationContext());
      returnPrice = dynamicPrices.getPriceAdjustment();

    } else {
      if (priceAdjustment != null) {
        returnPrice = new Money(priceAdjustment, Money.defaultCurrency());
      }
    }

    return returnPrice;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOptionValue#setPriceAdjustment(org.broadleafcommerce.common.money.Money)
   */
  @Override public void setPriceAdjustment(Money priceAdjustment) {
    this.priceAdjustment = Money.toAmount(priceAdjustment);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOptionValue#getProductOption()
   */
  @Override public ProductOption getProductOption() {
    return productOption;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOptionValue#setProductOption(org.broadleafcommerce.core.catalog.domain.ProductOption)
   */
  @Override public void setProductOption(ProductOption productOption) {
    this.productOption = productOption;
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

    ProductOptionValueImpl other = (ProductOptionValueImpl) obj;

    if ((id != null) && (other.id != null)) {
      return id.equals(other.id);
    }

    if (getAttributeValue() == null) {
      if (other.getAttributeValue() != null) {
        return false;
      }
    } else if (!getAttributeValue().equals(other.getAttributeValue())) {
      return false;
    }

    return true;
  } // end method equals

} // end class ProductOptionValueImpl
