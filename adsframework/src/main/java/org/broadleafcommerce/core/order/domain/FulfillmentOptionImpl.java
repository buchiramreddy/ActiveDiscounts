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
package org.broadleafcommerce.core.order.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import org.broadleafcommerce.common.i18n.service.DynamicTranslationProvider;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeEntry;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverride;
import org.broadleafcommerce.common.presentation.override.AdminPresentationMergeOverrides;
import org.broadleafcommerce.common.presentation.override.PropertyType;

import org.broadleafcommerce.core.order.service.type.FulfillmentType;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@AdminPresentationClass(friendlyName = "Base Fulfillment Option")
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
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_FULFILLMENT_OPTION")
public class FulfillmentOptionImpl implements FulfillmentOption {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "FULFILLMENT_OPTION_ID")
  @GeneratedValue(generator = "FulfillmentOptionId")
  @GenericGenerator(
    name       = "FulfillmentOptionId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "FulfillmentOptionImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.order.domain.FulfillmentOptionImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentOptionImpl_name",
    order        = Presentation.FieldOrder.NAME,
    prominent    = true,
    gridOrder    = 1000,
    translatable = true
  )
  @Column(name = "NAME")
  protected String name;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentOptionImpl_longDescription",
    order        = Presentation.FieldOrder.DESCRIPTION,
    translatable = true
  )
  @Column(
    name   = "LONG_DESCRIPTION",
    length = Integer.MAX_VALUE - 1
  )
  @Lob
  @Type(type = "org.hibernate.type.StringClobType")
  protected String      longDescription;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FulfillmentOptionImpl_useFlatRates",
    order        = Presentation.FieldOrder.FLATRATES
  )
  @Column(name = "USE_FLAT_RATES")
  protected Boolean useFlatRates = true;

  /** DOCUMENT ME! */
  @Column(
    name     = "FULFILLMENT_TYPE",
    nullable = false
  )
  protected String fulfillmentType;

  /** DOCUMENT ME! */
  @Column(
    name     = "TAX_CODE",
    nullable = true
  )
  protected String taxCode;

  /** DOCUMENT ME! */
  @Column(name = "TAXABLE")
  protected Boolean taxable = false;

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#getName()
   */
  @Override public String getName() {
    return DynamicTranslationProvider.getValue(this, "name", name);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#getLongDescription()
   */
  @Override public String getLongDescription() {
    return DynamicTranslationProvider.getValue(this, "longDescription", longDescription);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#setLongDescription(java.lang.String)
   */
  @Override public void setLongDescription(String longDescription) {
    this.longDescription = longDescription;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#getUseFlatRates()
   */
  @Override public Boolean getUseFlatRates() {
    return useFlatRates;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#setUseFlatRates(java.lang.Boolean)
   */
  @Override public void setUseFlatRates(Boolean useFlatRates) {
    this.useFlatRates = useFlatRates;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#getFulfillmentType()
   */
  @Override public FulfillmentType getFulfillmentType() {
    return FulfillmentType.getInstance(fulfillmentType);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#setFulfillmentType(org.broadleafcommerce.core.order.service.type.FulfillmentType)
   */
  @Override public void setFulfillmentType(FulfillmentType fulfillmentType) {
    this.fulfillmentType = (fulfillmentType == null) ? null : fulfillmentType.getType();
  }

  /**
   * @see  java.lang.Object#equals(java.lang.Object)
   */
  @Override public boolean equals(Object o) {
    return EqualsBuilder.reflectionEquals(this, o);
  }

  /**
   * @see  java.lang.Object#hashCode()
   */
  @Override public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#getTaxable()
   */
  @Override public Boolean getTaxable() {
    return this.taxable;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#setTaxable(java.lang.Boolean)
   */
  @Override public void setTaxable(Boolean taxable) {
    this.taxable = taxable;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#setTaxCode(java.lang.String)
   */
  @Override public void setTaxCode(String taxCode) {
    this.taxCode = taxCode;
  }

  /**
   * @see  org.broadleafcommerce.core.order.domain.FulfillmentOption#getTaxCode()
   */
  @Override public String getTaxCode() {
    return this.taxCode;
  }

  public static class Presentation {
    public static class Group {
      public static class Name { }

      public static class Order { }
    }

    public static class FieldOrder {
      public static final int NAME        = 1000;
      public static final int DESCRIPTION = 2000;
      public static final int FLATRATES   = 9000;
    }
  }
} // end class FulfillmentOptionImpl
