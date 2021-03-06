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

import java.io.Serializable;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public interface RequiredFacet extends Serializable {
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
  SearchFacet getRequiredFacet();

  /**
   * DOCUMENT ME!
   *
   * @param  requiredFacet  DOCUMENT ME!
   */
  void setRequiredFacet(SearchFacet requiredFacet);

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  SearchFacet getSearchFacet();

  /**
   * DOCUMENT ME!
   *
   * @param  searchFacet  DOCUMENT ME!
   */
  void setSearchFacet(SearchFacet searchFacet);

} // end interface RequiredFacet
