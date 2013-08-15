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

package org.broadleafcommerce.core.search.domain;

import org.broadleafcommerce.core.catalog.domain.Category;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public interface CategorySearchFacet {
  /**
   * Gets the internal id.
   *
   * @return  the internal id
   */
  Long getId();

  /**
   * Sets the internal id.
   *
   * @param  id  DOCUMENT ME!
   */
  void setId(Long id);

  /**
   * Gets the associated category.
   *
   * @return  the associated category
   */
  Category getCategory();

  /**
   * Sets the associated category.
   *
   * @param  category  DOCUMENT ME!
   */
  void setCategory(Category category);

  /**
   * Gets the associated search facet.
   *
   * @return  the associated search facet
   */
  SearchFacet getSearchFacet();

  /**
   * Sets the associated search facet.
   *
   * @param  searchFacet  DOCUMENT ME!
   */
  void setSearchFacet(SearchFacet searchFacet);

  /**
   * Gets the priority of this search facet in relationship to other search facets in this category.
   *
   * @return  the sequence of this search facet
   */
  Long getSequence();

  /**
   * Sets the sequence of this search facet.
   *
   * @see    #getPosition()
   *
   * @param  sequence  position
   */
  void setSequence(Long sequence);

} // end interface CategorySearchFacet
