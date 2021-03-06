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

import java.io.Serializable;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface CategoryXref extends Serializable {
  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Long getDisplayOrder();

  /**
   * DOCUMENT ME!
   *
   * @param  displayOrder  DOCUMENT ME!
   */
  void setDisplayOrder(final Long displayOrder);

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
  void setCategory(final Category category);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Category getSubCategory();

  /**
   * DOCUMENT ME!
   *
   * @param  subCategory  DOCUMENT ME!
   */
  void setSubCategory(final Category subCategory);

} // end interface CategoryXref
