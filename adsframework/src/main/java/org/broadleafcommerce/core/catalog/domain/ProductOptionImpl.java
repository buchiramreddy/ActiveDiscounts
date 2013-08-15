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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.broadleafcommerce.common.admin.domain.AdminMainEntity;
import org.broadleafcommerce.common.i18n.service.DynamicTranslationProvider;
import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.AdminPresentationClass;
import org.broadleafcommerce.common.presentation.AdminPresentationCollection;
import org.broadleafcommerce.common.presentation.PopulateToOneFieldsEnum;
import org.broadleafcommerce.common.presentation.client.AddMethodType;
import org.broadleafcommerce.common.presentation.client.SupportedFieldType;

import org.broadleafcommerce.core.catalog.service.type.ProductOptionType;
import org.broadleafcommerce.core.catalog.service.type.ProductOptionValidationType;

import org.hibernate.annotations.BatchSize;
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
@AdminPresentationClass(
  friendlyName        = "ProductOptionImpl_baseProductOption",
  populateToOneFields = PopulateToOneFieldsEnum.TRUE
)
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PRODUCT_OPTION")
public class ProductOptionImpl implements ProductOption, AdminMainEntity {
  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "PRODUCT_OPTION_ID")
  @GeneratedValue(generator = "ProductOptionId")
  @GenericGenerator(
    name       = "ProductOptionId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "ProductOptionImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.catalog.domain.ProductOptionImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName         = "productOption_Type",
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.catalog.service.type.ProductOptionType"
  )
  @Column(name = "OPTION_TYPE")
  protected String type;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "productOption_name",
    helpText     = "productOption_nameHelp"
  )
  @Column(name = "ATTRIBUTE_NAME")
  protected String attributeName;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "productOption_Label",
    helpText     = "productOption_labelHelp",
    prominent    = true,
    translatable = true
  )
  @Column(name = "LABEL")
  protected String label;

  /** DOCUMENT ME! */
  @AdminPresentation(friendlyName = "productOption_Required")
  @Column(name = "REQUIRED")
  protected Boolean required;

  @AdminPresentation(friendlyName = "productOption_UseInSKUGeneration")
  @Column(name = "USE_IN_SKU_GENERATION")
  private Boolean useInSkuGeneration;

  /** DOCUMENT ME! */
  @AdminPresentation(friendlyName = "productOption_displayOrder")
  @Column(name = "DISPLAY_ORDER")
  protected Integer displayOrder;

  @AdminPresentation(
    friendlyName         = "productOption_validationType",
    group                = "productOption_validation",
    fieldType            = SupportedFieldType.BROADLEAF_ENUMERATION,
    broadleafEnumeration = "org.broadleafcommerce.core.catalog.service.type.ProductOptionValidationType"
  )
  @Column(name = "VALIDATION_TYPE")
  private String productOptionValidationType;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "productOption_validationSring",
    group        = "productOption_validation"
  )
  @Column(name = "VALIDATION_STRING")
  protected String validationString;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "productOption_errorCode",
    group        = "productOption_validation"
  )
  @Column(name = "ERROR_CODE")
  protected String errorCode;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "productOption_errorMessage",
    group        = "productOption_validation"
  )
  @Column(name = "ERROR_MESSAGE")
  protected String errorMessage;

  /** DOCUMENT ME! */
  @AdminPresentationCollection(
    addType      = AddMethodType.PERSIST,
    friendlyName = "ProductOptionImpl_Allowed_Values"
  )
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @OneToMany(
    mappedBy     = "productOption",
    targetEntity = ProductOptionValueImpl.class,
    cascade      = { CascadeType.ALL }
  )
  @OrderBy(value = "displayOrder")
  protected List<ProductOptionValue> allowedValues = new ArrayList<ProductOptionValue>();

  /** DOCUMENT ME! */
  @BatchSize(size = 50)
  @Cache(
    usage  = CacheConcurrencyStrategy.READ_WRITE,
    region = "blStandardElements"
  )
  @JoinTable(
    name               = "BLC_PRODUCT_OPTION_XREF",
    joinColumns        =
      @JoinColumn(
        name           = "PRODUCT_OPTION_ID",
        referencedColumnName = "PRODUCT_OPTION_ID"
      ),
    inverseJoinColumns =
      @JoinColumn(
        name                 = "PRODUCT_ID",
        referencedColumnName = "PRODUCT_ID"
      )
  )
  @ManyToMany(
    fetch        = FetchType.LAZY,
    targetEntity = ProductImpl.class
  )
  protected List<Product> products = new ArrayList<Product>();

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#getType()
   */
  @Override public ProductOptionType getType() {
    return ProductOptionType.getInstance(type);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#setType(org.broadleafcommerce.core.catalog.service.type.ProductOptionType)
   */
  @Override public void setType(ProductOptionType type) {
    this.type = (type == null) ? null : type.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#getAttributeName()
   */
  @Override public String getAttributeName() {
    return attributeName;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#setAttributeName(java.lang.String)
   */
  @Override public void setAttributeName(String attributeName) {
    this.attributeName = attributeName;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#getLabel()
   */
  @Override public String getLabel() {
    return DynamicTranslationProvider.getValue(this, "label", label);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#setLabel(java.lang.String)
   */
  @Override public void setLabel(String label) {
    this.label = label;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#getRequired()
   */
  @Override public Boolean getRequired() {
    return required;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#setRequired(java.lang.Boolean)
   */
  @Override public void setRequired(Boolean required) {
    this.required = required;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#getDisplayOrder()
   */
  @Override public Integer getDisplayOrder() {
    return displayOrder;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#setDisplayOrder(java.lang.Integer)
   */
  @Override public void setDisplayOrder(Integer displayOrder) {
    this.displayOrder = displayOrder;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#getProducts()
   */
  @Override public List<Product> getProducts() {
    return products;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#setProducts(java.util.List)
   */
  @Override public void setProducts(List<Product> products) {
    this.products = products;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#getAllowedValues()
   */
  @Override public List<ProductOptionValue> getAllowedValues() {
    return allowedValues;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#setAllowedValues(java.util.List)
   */
  @Override public void setAllowedValues(List<ProductOptionValue> allowedValues) {
    this.allowedValues = allowedValues;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#getUseInSkuGeneration()
   */
  @Override public Boolean getUseInSkuGeneration() {
    return (useInSkuGeneration == null) ? true : useInSkuGeneration;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#setUseInSkuGeneration(java.lang.Boolean)
   */
  @Override public void setUseInSkuGeneration(Boolean useInSkuGeneration) {
    this.useInSkuGeneration = useInSkuGeneration;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#getProductOptionValidationType()
   */
  @Override public ProductOptionValidationType getProductOptionValidationType() {
    return ProductOptionValidationType.getInstance(productOptionValidationType);
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#setProductOptionValidationType(org.broadleafcommerce.core.catalog.service.type.ProductOptionValidationType)
   */
  @Override public void setProductOptionValidationType(ProductOptionValidationType productOptionValidationType) {
    this.productOptionValidationType = (productOptionValidationType == null) ? null
                                                                             : productOptionValidationType.getType();
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#getValidationString()
   */
  @Override public String getValidationString() {
    return validationString;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#setValidationString(java.lang.String)
   */
  @Override public void setValidationString(String validationString) {
    this.validationString = validationString;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#getErrorCode()
   */
  @Override public String getErrorCode() {
    return errorCode;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#setErrorCode(java.lang.String)
   */
  @Override public void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#getErrorMessage()
   */
  @Override public String getErrorMessage() {
    return errorMessage;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.ProductOption#setErrorMessage(java.lang.String)
   */
  @Override public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * @see  org.broadleafcommerce.common.admin.domain.AdminMainEntity#getMainEntityName()
   */
  @Override public String getMainEntityName() {
    return getLabel();
  }

} // end class ProductOptionImpl
