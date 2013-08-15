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

package org.broadleafcommerce.core.catalog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.broadleafcommerce.common.presentation.AdminPresentation;
import org.broadleafcommerce.common.presentation.client.VisibilityEnum;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Index;
import org.hibernate.annotations.Parameter;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Cache(
  usage  = CacheConcurrencyStrategy.READ_WRITE,
  region = "blStandardElements"
)
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "BLC_PRODUCT_CROSS_SALE")
public class CrossSaleProductImpl implements RelatedProduct {
  /** DOCUMENT ME! */
  protected static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  @Column(name = "CROSS_SALE_PRODUCT_ID")
  @GeneratedValue(generator = "CrossSaleProductId")
  @GenericGenerator(
    name       = "CrossSaleProductId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "CrossSaleProductImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.catalog.domain.CrossSaleProductImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "CrossSaleProductImpl_Cross_Sale_Promotion_Message",
    largeEntry   = true
  )
  @Column(name = "PROMOTION_MESSAGE")
  protected String promotionMessage;

  /** DOCUMENT ME! */
  @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
  @Column(name = "SEQUENCE")
  protected Long sequence;

  /** DOCUMENT ME! */
  @Index(
    name        = "CROSSSALE_INDEX",
    columnNames = { "PRODUCT_ID" }
  )
  @JoinColumn(name = "PRODUCT_ID")
  @ManyToOne(targetEntity = ProductImpl.class)
  protected Product product;

  /** DOCUMENT ME! */
  @Index(
    name        = "CROSSSALE_CATEGORY_INDEX",
    columnNames = { "CATEGORY_ID" }
  )
  @JoinColumn(name = "CATEGORY_ID")
  @ManyToOne(targetEntity = CategoryImpl.class)
  protected Category category;

  /** DOCUMENT ME! */
  @Index(
    name        = "CROSSSALE_RELATED_INDEX",
    columnNames = { "RELATED_SALE_PRODUCT_ID" }
  )
  @JoinColumn(
    name                 = "RELATED_SALE_PRODUCT_ID",
    referencedColumnName = "PRODUCT_ID"
  )
  @ManyToOne(
    targetEntity = ProductImpl.class,
    optional     = false
  )
  protected Product relatedSaleProduct = new ProductImpl();

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.RelatedProduct#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.RelatedProduct#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.RelatedProduct#getPromotionMessage()
   */
  @Override public String getPromotionMessage() {
    return promotionMessage;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.RelatedProduct#setPromotionMessage(java.lang.String)
   */
  @Override public void setPromotionMessage(String promotionMessage) {
    this.promotionMessage = promotionMessage;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.RelatedProduct#getSequence()
   */
  @Override public Long getSequence() {
    return sequence;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.RelatedProduct#setSequence(java.lang.Long)
   */
  @Override public void setSequence(Long sequence) {
    this.sequence = sequence;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.RelatedProduct#getProduct()
   */
  @Override public Product getProduct() {
    return product;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.RelatedProduct#setProduct(org.broadleafcommerce.core.catalog.domain.Product)
   */
  @Override public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.RelatedProduct#getCategory()
   */
  @Override public Category getCategory() {
    return category;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.RelatedProduct#setCategory(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public void setCategory(Category category) {
    this.category = category;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.RelatedProduct#getRelatedProduct()
   */
  @Override public Product getRelatedProduct() {
    return relatedSaleProduct;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.RelatedProduct#setRelatedProduct(org.broadleafcommerce.core.catalog.domain.Product)
   */
  @Override public void setRelatedProduct(Product relatedSaleProduct) {
    this.relatedSaleProduct = relatedSaleProduct;
  }
} // end class CrossSaleProductImpl
