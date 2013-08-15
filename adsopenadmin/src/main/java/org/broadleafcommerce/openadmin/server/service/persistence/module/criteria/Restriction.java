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
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.converter.FilterValueConverter;
import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.predicate.PredicateProvider;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class Restriction {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected FieldPathBuilder     fieldPathBuilder     = new FieldPathBuilder();

  /** DOCUMENT ME! */
  protected FilterValueConverter filterValueConverter;

  /** DOCUMENT ME! */
  protected PredicateProvider predicateProvider;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * This method differs from buildRestriction in that it will return a FieldPathBuilder that could be used by the
   * caller to establish any additional Roots that might be necessary due to the path living inside of a polymorphic
   * version of the ceiling entity. The Predicate object that {@link #buildRestriction(...)} returns is also available
   * inside of the FieldPathBuilder object for the caller's use.
   *
   * @param   builder             DOCUMENT ME!
   * @param   root                DOCUMENT ME!
   * @param   ceilingEntity       DOCUMENT ME!
   * @param   targetPropertyName  DOCUMENT ME!
   * @param   explicitPath        DOCUMENT ME!
   * @param   directValues        DOCUMENT ME!
   * @param   shouldConvert       DOCUMENT ME!
   * @param   criteria            DOCUMENT ME!
   * @param   restrictions        DOCUMENT ME!
   *
   * @return  this method differs from buildRestriction in that it will return a FieldPathBuilder that could be used by
   *          the caller to establish any additional Roots that might be necessary due to the path living inside of a
   *          polymorphic version of the ceiling entity.
   */
  public Predicate buildPolymorphicRestriction(CriteriaBuilder builder, From root, String ceilingEntity,
    String targetPropertyName,
    Path explicitPath, List directValues, boolean shouldConvert, CriteriaQuery criteria, List<Predicate> restrictions) {
    fieldPathBuilder.setCriteria(criteria);
    fieldPathBuilder.setRestrictions(restrictions);

    return buildRestriction(builder, root, ceilingEntity, targetPropertyName, explicitPath, directValues,
        shouldConvert);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This method is deprecated in favor of
   * {@link #buildPolymorphicRestriction(javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.From, String, String, javax.persistence.criteria.Path, java.util.List, boolean, javax.persistence.criteria.CriteriaQuery, java.util.List)}.
   *
   * <p>It will be removed in Broadleaf version 3.1.0 and buildPolymorphicRestriction will be renamed to
   * buildRestriction</p>
   *
   * @param   builder             DOCUMENT ME!
   * @param   root                DOCUMENT ME!
   * @param   ceilingEntity       DOCUMENT ME!
   * @param   targetPropertyName  DOCUMENT ME!
   * @param   explicitPath        DOCUMENT ME!
   * @param   directValues        DOCUMENT ME!
   * @param   shouldConvert       DOCUMENT ME!
   *
   * @return  this method is deprecated in favor of
   *          {@link #buildPolymorphicRestriction(javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.From, String, String, javax.persistence.criteria.Path, java.util.List, boolean, javax.persistence.criteria.CriteriaQuery, java.util.List)}.
   */
  @Deprecated public Predicate buildRestriction(CriteriaBuilder builder, From root, String ceilingEntity,
    String targetPropertyName,
    Path explicitPath, List directValues, boolean shouldConvert) {
    List<Object> convertedValues = new ArrayList<Object>();

    if (shouldConvert && (filterValueConverter != null)) {
      for (Object item : directValues) {
        String stringItem = (String) item;
        convertedValues.add(filterValueConverter.convert(stringItem));
      }
    } else {
      convertedValues.addAll(directValues);
    }

    return predicateProvider.buildPredicate(builder, fieldPathBuilder, root, ceilingEntity, targetPropertyName,
        explicitPath, convertedValues);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  java.lang.Object#clone()
   */
  @Override public Restriction clone() {
    Restriction temp = new Restriction().withFilterValueConverter(getFilterValueConverter()).withPredicateProvider(
        getPredicateProvider()).withFieldPathBuilder(getFieldPathBuilder());

    return temp;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FieldPathBuilder getFieldPathBuilder() {
    return fieldPathBuilder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public FilterValueConverter getFilterValueConverter() {
    return filterValueConverter;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public PredicateProvider getPredicateProvider() {
    return predicateProvider;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  fieldPathBuilder  DOCUMENT ME!
   */
  public void setFieldPathBuilder(FieldPathBuilder fieldPathBuilder) {
    this.fieldPathBuilder = fieldPathBuilder;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  filterValueConverter  DOCUMENT ME!
   */
  public void setFilterValueConverter(FilterValueConverter filterValueConverter) {
    this.filterValueConverter = filterValueConverter;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  predicateProvider  DOCUMENT ME!
   */
  public void setPredicateProvider(PredicateProvider predicateProvider) {
    this.predicateProvider = predicateProvider;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   fieldPathBuilder  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Restriction withFieldPathBuilder(FieldPathBuilder fieldPathBuilder) {
    setFieldPathBuilder(fieldPathBuilder);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   filterValueConverter  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Restriction withFilterValueConverter(FilterValueConverter filterValueConverter) {
    setFilterValueConverter(filterValueConverter);

    return this;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   predicateProvider  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Restriction withPredicateProvider(PredicateProvider predicateProvider) {
    setPredicateProvider(predicateProvider);

    return this;
  }
} // end class Restriction
