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

import java.util.Map;


/**
 * Container that holds additional criteria to consider when performing searches for Products.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class ProductSearchCriteria {
  /** DOCUMENT ME! */
  public static String PAGE_SIZE_STRING = "pageSize";

  /** DOCUMENT ME! */
  public static String PAGE_NUMBER  = "page";

  /** DOCUMENT ME! */
  public static String SORT_STRING  = "sort";

  /** DOCUMENT ME! */
  public static String QUERY_STRING = "q";

  /** DOCUMENT ME! */
  protected Integer               page           = 1;

  /** DOCUMENT ME! */
  protected Integer               pageSize;

  /** DOCUMENT ME! */
  protected String                sortQuery;

  /** DOCUMENT ME! */
  protected Map<String, String[]> filterCriteria;

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getPage() {
    return page;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  page  DOCUMENT ME!
   */
  public void setPage(Integer page) {
    this.page = page;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Integer getPageSize() {
    return pageSize;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  pageSize  DOCUMENT ME!
   */
  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getSortQuery() {
    return sortQuery;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  sortQuery  DOCUMENT ME!
   */
  public void setSortQuery(String sortQuery) {
    this.sortQuery = sortQuery;
  }

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, String[]> getFilterCriteria() {
    return filterCriteria;
  }

  /**
   * DOCUMENT ME!
   *
   * @param  filterCriteria  DOCUMENT ME!
   */
  public void setFilterCriteria(Map<String, String[]> filterCriteria) {
    this.filterCriteria = filterCriteria;
  }

} // end class ProductSearchCriteria
