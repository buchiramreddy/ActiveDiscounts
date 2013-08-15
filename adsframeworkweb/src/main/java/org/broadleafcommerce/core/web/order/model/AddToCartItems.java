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

package org.broadleafcommerce.core.web.order.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.FactoryUtils;
import org.apache.commons.collections.list.LazyList;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class AddToCartItems {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  @SuppressWarnings("unchecked")
  // TOOD: this should probably be refactored to be called "rows" like in other model objects
  private List<AddToCartItem> addToCartItems = LazyList.decorate(
      new ArrayList<AddToCartItem>(),
      FactoryUtils.instantiateFactory(AddToCartItem.class));
  private long                categoryId;

  private long productId;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<AddToCartItem> getAddToCartItems() {
    return addToCartItems;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public long getCategoryId() {
    return categoryId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public long getProductId() {
    return productId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  addToCartItems  DOCUMENT ME!
   */
  public void setAddToCartItem(List<AddToCartItem> addToCartItems) {
    this.addToCartItems = addToCartItems;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  categoryId  DOCUMENT ME!
   */
  public void setCategoryId(long categoryId) {
    this.categoryId = categoryId;

    for (AddToCartItem addToCartItem : addToCartItems) {
      addToCartItem.setCategoryId(categoryId);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  productId  DOCUMENT ME!
   */
  public void setProductId(long productId) {
    this.productId = productId;

    for (AddToCartItem addToCartItem : addToCartItems) {
      addToCartItem.setProductId(productId);
    }
  }

} // end class AddToCartItems
