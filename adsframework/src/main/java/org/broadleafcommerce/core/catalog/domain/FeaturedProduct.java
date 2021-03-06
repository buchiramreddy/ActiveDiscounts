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


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface FeaturedProduct extends PromotableProduct {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getId();

  /**
   * DOCUMENT ME!
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Category getCategory();

  /**
   * DOCUMENT ME!
   *
   * @param  category  DOCUMENT ME!
   */
  void setCategory(Category category);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Product getProduct();

  /**
   * DOCUMENT ME!
   *
   * @param  product  DOCUMENT ME!
   */
  void setProduct(Product product);

  /**
   * DOCUMENT ME!
   *
   * @param  sequence  DOCUMENT ME!
   */
  void setSequence(Long sequence);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getSequence();

  /**
   * @see  org.broadleafcommerce.core.catalog.domain.PromotableProduct#getPromotionMessage()
   */
  @Override String getPromotionMessage();

  /**
   * DOCUMENT ME!
   *
   * @param  promotionMessage  DOCUMENT ME!
   */
  void setPromotionMessage(String promotionMessage);

  /**
   * Pass through to getProdcut() to meet the contract for promotable product.
   *
   * @return  pass through to getProdcut() to meet the contract for promotable product.
   */
  @Override Product getRelatedProduct();

} // end interface FeaturedProduct
