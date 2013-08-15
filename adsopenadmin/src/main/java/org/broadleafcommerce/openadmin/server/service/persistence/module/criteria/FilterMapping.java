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

package org.broadleafcommerce.openadmin.server.service.persistence.module.criteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import org.broadleafcommerce.openadmin.dto.SortDirection;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class FilterMapping {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public static final String RANGE_SPECIFIER_REGEX = "->";

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected List         directFilterValues = new ArrayList();

  /** DOCUMENT ME! */
  protected FieldPath    fieldPath;

  /** DOCUMENT ME! */
  protected List<String> filterValues = new ArrayList<String>();

  /** DOCUMENT ME! */
  protected String        fullPropertyName;

  /** DOCUMENT ME! */
  protected Class<?>      inheritedFromClass;

  /** DOCUMENT ME! */
  protected Restriction   restriction;

  /** DOCUMENT ME! */
  protected SortDirection sortDirection;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List getDirectFilterValues() {
    return directFilterValues;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FieldPath getFieldPath() {
    return fieldPath;
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
  public String getFullPropertyName() {
    return fullPropertyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Class<?> getInheritedFromClass() {
    return inheritedFromClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Restriction getRestriction() {
    return restriction;
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
   * @param   directFilterValues  DOCUMENT ME!
   *
   * @throws  IllegalArgumentException  DOCUMENT ME!
   */
  public void setDirectFilterValues(List directFilterValues) {
    if (CollectionUtils.isNotEmpty(filterValues)) {
      throw new IllegalArgumentException("Cannot set both filter values and direct filter values");
    }

    this.directFilterValues = directFilterValues;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fieldPath  DOCUMENT ME!
   */
  public void setFieldPath(FieldPath fieldPath) {
    this.fieldPath = fieldPath;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   filterValues  DOCUMENT ME!
   *
   * @throws  IllegalArgumentException  DOCUMENT ME!
   */
  public void setFilterValues(List<String> filterValues) {
    if (CollectionUtils.isNotEmpty(directFilterValues)) {
      throw new IllegalArgumentException("Cannot set both filter values and direct filter values");
    }

    List<String> parsedValues = new ArrayList<String>();

    for (String unfiltered : filterValues) {
      parsedValues.addAll(Arrays.asList(parseFilterValue(unfiltered)));
    }

    this.filterValues.addAll(parsedValues);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fullPropertyName  DOCUMENT ME!
   */
  public void setFullPropertyName(String fullPropertyName) {
    this.fullPropertyName = fullPropertyName;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  inheritedFromClass  DOCUMENT ME!
   */
  public void setInheritedFromClass(Class<?> inheritedFromClass) {
    this.inheritedFromClass = inheritedFromClass;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  restriction  DOCUMENT ME!
   */
  public void setRestriction(Restriction restriction) {
    this.restriction = restriction;
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

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   directFilterValues  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FilterMapping withDirectFilterValues(List directFilterValues) {
    setDirectFilterValues(directFilterValues);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fieldPath  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FilterMapping withFieldPath(FieldPath fieldPath) {
    setFieldPath(fieldPath);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   filterValues  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FilterMapping withFilterValues(List<String> filterValues) {
    setFilterValues(filterValues);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fullPropertyName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FilterMapping withFullPropertyName(String fullPropertyName) {
    setFullPropertyName(fullPropertyName);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   inheritedFromClass  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FilterMapping withInheritedFromClass(Class<?> inheritedFromClass) {
    setInheritedFromClass(inheritedFromClass);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   restriction  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FilterMapping withRestriction(Restriction restriction) {
    setRestriction(restriction);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   sortDirection  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FilterMapping withSortDirection(SortDirection sortDirection) {
    setSortDirection(sortDirection);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   filterValue  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected String[] parseFilterValue(String filterValue) {
    // We do it this way because the String.split() method will return only a single array member
    // when there is nothing on one side of the delimiter. We want to have two array members (one empty)
    // in this case.
    String[] vals;

    if (filterValue.contains(RANGE_SPECIFIER_REGEX)) {
      vals = new String[] {
        filterValue.substring(0, filterValue.indexOf(RANGE_SPECIFIER_REGEX)),
        filterValue.substring(filterValue.indexOf(RANGE_SPECIFIER_REGEX) + RANGE_SPECIFIER_REGEX.length(),
          filterValue.length())
      };
    } else {
      vals = new String[] { filterValue };
    }

    for (int j = 0; j < vals.length; j++) {
      vals[j] = vals[j].trim();
    }

    return vals;
  }
} // end class FilterMapping
