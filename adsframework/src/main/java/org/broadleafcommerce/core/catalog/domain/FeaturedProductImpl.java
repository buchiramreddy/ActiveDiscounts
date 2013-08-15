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
@Table(name = "BLC_PRODUCT_FEATURED")
public class FeaturedProductImpl implements FeaturedProduct {
  private static final long serialVersionUID = 1L;

  /** The id. */
  @Column(name = "FEATURED_PRODUCT_ID")
  @GeneratedValue(generator = "FeaturedProductId")
  @GenericGenerator(
    name       = "FeaturedProductId",
    strategy   = "org.broadleafcommerce.common.persistence.IdOverrideTableGenerator",
    parameters = {
      @Parameter(
        name   = "segment_value",
        value  = "FeaturedProductImpl"
      ),
      @Parameter(
        name   = "entity_name",
        value  = "org.broadleafcommerce.core.catalog.domain.FeaturedProductImpl"
      )
    }
  )
  @Id protected Long id;

  /** DOCUMENT ME! */
  @AdminPresentation(visibility = VisibilityEnum.HIDDEN_ALL)
  @Column(name = "SEQUENCE")
  protected Long sequence;

  /** DOCUMENT ME! */
  @AdminPresentation(
    friendlyName = "FeaturedProductImpl_Featured_Product_Promotion_Message",
    largeEntry   = true
  )
  @Column(name = "PROMOTION_MESSAGE")
  protected String promotionMessage;

  /** DOCUMENT ME! */
  @Index(
    name        = "PRODFEATURED_CATEGORY_INDEX",
    columnNames = { "CATEGORY_ID" }
  )
  @JoinColumn(name = "CATEGORY_ID")
  @ManyToOne(targetEntity = CategoryImpl.class)
  protected Category category = new CategoryImpl();

  /** DOCUMENT ME! */
  @Index(
    name        = "PRODFEATURED_PRODUCT_INDEX",
    columnNames = { "PRODUCT_ID" }
  )
  @JoinColumn(name = "PRODUCT_ID")
  @ManyToOne(targetEntity = ProductImpl.class)
  protected Product product = new ProductImpl();

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.FeaturedProduct#getId()
   */
  @Override public Long getId() {
    return id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.FeaturedProduct#setId(java.lang.Long)
   */
  @Override public void setId(Long id) {
    this.id = id;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.FeaturedProduct#setSequence(java.lang.Long)
   */
  @Override public void setSequence(Long sequence) {
    this.sequence = sequence;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.FeaturedProduct#getSequence()
   */
  @Override public Long getSequence() {
    return this.sequence;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.FeaturedProduct#getPromotionMessage()
   */
  @Override public String getPromotionMessage() {
    return promotionMessage;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.FeaturedProduct#setPromotionMessage(java.lang.String)
   */
  @Override public void setPromotionMessage(String promotionMessage) {
    this.promotionMessage = promotionMessage;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.FeaturedProduct#getCategory()
   */
  @Override public Category getCategory() {
    return category;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.FeaturedProduct#setCategory(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public void setCategory(Category category) {
    this.category = category;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.FeaturedProduct#getProduct()
   */
  @Override public Product getProduct() {
    return product;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.FeaturedProduct#setProduct(org.broadleafcommerce.core.catalog.domain.Product)
   */
  @Override public void setProduct(Product product) {
    this.product = product;
  }

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.FeaturedProduct#getRelatedProduct()
   */
  @Override public Product getRelatedProduct() {
    return product;
  }

} // end class FeaturedProductImpl
