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

package org.broadleafcommerce.openadmin.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class FilterAndSortCriteria {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final long serialVersionUID = 1L;

  /** DOCUMENT ME! */
  public static final String SORT_PROPERTY_PARAMETER  = "sortProperty";

  /** DOCUMENT ME! */
  public static final String SORT_DIRECTION_PARAMETER = "sortDirection";

  /** DOCUMENT ME! */
  public static final String START_INDEX_PARAMETER = "startIndex";

  /** DOCUMENT ME! */
  public static final String MAX_INDEX_PARAMETER = "maxIndex";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected List<String> filterValues = new ArrayList<String>();

  /** DOCUMENT ME! */
  protected String propertyId;

  /** DOCUMENT ME! */
  protected SortDirection sortDirection;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new FilterAndSortCriteria object.
   *
   * @param  propertyId  DOCUMENT ME!
   */
  public FilterAndSortCriteria(String propertyId) {
    this.propertyId = propertyId;
  }

  /**
   * Creates a new FilterAndSortCriteria object.
   *
   * @param  propertyId   DOCUMENT ME!
   * @param  filterValue  DOCUMENT ME!
   */
  public FilterAndSortCriteria(String propertyId, String filterValue) {
    this.propertyId = propertyId;
    setFilterValue(filterValue);
  }

  /**
   * Creates a new FilterAndSortCriteria object.
   *
   * @param  propertyId    DOCUMENT ME!
   * @param  filterValues  DOCUMENT ME!
   */
  public FilterAndSortCriteria(String propertyId, List<String> filterValues) {
    this.propertyId = propertyId;
    setFilterValues(filterValues);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  public void clearFilterValues() {
    filterValues.clear();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<String> getFilterValues() {
    return filterValues;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getPropertyId() {
    return propertyId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Boolean getSortAscending() {
    return (sortDirection == null) ? null : SortDirection.ASCENDING.equals(sortDirection);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public SortDirection getSortDirection() {
    return sortDirection;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  value  DOCUMENT ME!
   */
  public void setFilterValue(String value) {
    clearFilterValues();
    filterValues.add(value);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  filterValues  DOCUMENT ME!
   */
  public void setFilterValues(List<String> filterValues) {
    this.filterValues = filterValues;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  propertyId  DOCUMENT ME!
   */
  public void setPropertyId(String propertyId) {
    this.propertyId = propertyId;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sortAscending  DOCUMENT ME!
   */
  public void setSortAscending(Boolean sortAscending) {
    this.sortDirection = (sortAscending) ? SortDirection.ASCENDING : SortDirection.DESCENDING;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  sortDirection  DOCUMENT ME!
   */
  public void setSortDirection(SortDirection sortDirection) {
    this.sortDirection = sortDirection;
  }

} // end class FilterAndSortCriteria
