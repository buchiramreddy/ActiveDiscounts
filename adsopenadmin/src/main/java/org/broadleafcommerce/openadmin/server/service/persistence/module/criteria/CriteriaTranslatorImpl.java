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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import org.broadleafcommerce.common.exception.NoPossibleResultsException;

import org.broadleafcommerce.openadmin.dto.ClassTree;
import org.broadleafcommerce.openadmin.dto.SortDirection;
import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;
import org.broadleafcommerce.openadmin.server.service.persistence.module.EmptyFilterValues;

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@Service("blCriteriaTranslator")
public class CriteriaTranslatorImpl implements CriteriaTranslator {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.CriteriaTranslator#translateCountQuery(org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       java.lang.String, java.util.List)
   */
  @Override public TypedQuery<Serializable> translateCountQuery(DynamicEntityDao dynamicEntityDao, String ceilingEntity,
    List<FilterMapping> filterMappings) {
    return constructQuery(dynamicEntityDao, ceilingEntity, filterMappings, true, null, null);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.CriteriaTranslator#translateQuery(org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao,
   *       java.lang.String, java.util.List, java.lang.Integer, java.lang.Integer)
   */
  @Override public TypedQuery<Serializable> translateQuery(DynamicEntityDao dynamicEntityDao, String ceilingEntity,
    List<FilterMapping> filterMappings, Integer firstResult, Integer maxResults) {
    return constructQuery(dynamicEntityDao, ceilingEntity, filterMappings, false, firstResult, maxResults);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  response     DOCUMENT ME!
   * @param  firstResult  DOCUMENT ME!
   * @param  maxResults   DOCUMENT ME!
   */
  protected void addPaging(Query response, Integer firstResult, Integer maxResults) {
    if (firstResult != null) {
      response.setFirstResult(firstResult);
    }

    if (maxResults != null) {
      response.setMaxResults(maxResults);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This method is deprecated in favor of
   * {@link #addRestrictions(String, java.util.List, javax.persistence.criteria.CriteriaBuilder, javax.persistence.criteria.Root, java.util.List, java.util.List, javax.persistence.criteria.CriteriaQuery)}
   * It will be removed in Broadleaf version 3.1.0.
   *
   * @param  ceilingEntity    DOCUMENT ME!
   * @param  filterMappings   DOCUMENT ME!
   * @param  criteriaBuilder  DOCUMENT ME!
   * @param  original         DOCUMENT ME!
   * @param  restrictions     DOCUMENT ME!
   * @param  sorts            DOCUMENT ME!
   */
  @Deprecated protected void addRestrictions(String ceilingEntity, List<FilterMapping> filterMappings,
    CriteriaBuilder criteriaBuilder,
    Root original, List<Predicate> restrictions, List<Order> sorts) {
    addRestrictions(ceilingEntity, filterMappings, criteriaBuilder, original, restrictions, sorts, null);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  ceilingEntity    DOCUMENT ME!
   * @param  filterMappings   DOCUMENT ME!
   * @param  criteriaBuilder  DOCUMENT ME!
   * @param  original         DOCUMENT ME!
   * @param  restrictions     DOCUMENT ME!
   * @param  sorts            DOCUMENT ME!
   * @param  criteria         DOCUMENT ME!
   */
  protected void addRestrictions(String ceilingEntity, List<FilterMapping> filterMappings,
    CriteriaBuilder criteriaBuilder,
    Root original, List<Predicate> restrictions, List<Order> sorts, CriteriaQuery criteria) {
    for (FilterMapping filterMapping : filterMappings) {
      Path explicitPath = null;

      if (filterMapping.getFieldPath() != null) {
        explicitPath = filterMapping.getRestriction().getFieldPathBuilder().getPath(original,
            filterMapping.getFieldPath(), criteriaBuilder);
      }

      if (filterMapping.getRestriction() != null) {
        List    directValues  = null;
        boolean shouldConvert = true;

        if (CollectionUtils.isNotEmpty(filterMapping.getFilterValues())) {
          directValues = filterMapping.getFilterValues();
        } else if (CollectionUtils.isNotEmpty(filterMapping.getDirectFilterValues())
              || ((filterMapping.getDirectFilterValues() != null)
                && (filterMapping.getDirectFilterValues() instanceof EmptyFilterValues))) {
          directValues  = filterMapping.getDirectFilterValues();
          shouldConvert = false;
        }

        if (directValues != null) {
          Predicate predicate = filterMapping.getRestriction().buildPolymorphicRestriction(criteriaBuilder, original,
              ceilingEntity, filterMapping.getFullPropertyName(), explicitPath, directValues, shouldConvert,
              criteria, restrictions);
          restrictions.add(predicate);
        }
      }

      if (filterMapping.getSortDirection() != null) {
        Path sortPath = explicitPath;

        if ((sortPath == null) && !StringUtils.isEmpty(filterMapping.getFullPropertyName())) {
          FieldPathBuilder fieldPathBuilder = filterMapping.getRestriction().getFieldPathBuilder();
          fieldPathBuilder.setCriteria(criteria);
          fieldPathBuilder.setRestrictions(restrictions);
          sortPath = filterMapping.getRestriction().getFieldPathBuilder().getPath(original,
              filterMapping.getFullPropertyName(), criteriaBuilder);
        }

        if (sortPath != null) {
          addSorting(criteriaBuilder, sorts, filterMapping, sortPath);
        }
      }
    } // end for
  } // end method addRestrictions

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  criteriaBuilder  DOCUMENT ME!
   * @param  sorts            DOCUMENT ME!
   * @param  filterMapping    DOCUMENT ME!
   * @param  path             DOCUMENT ME!
   */
  protected void addSorting(CriteriaBuilder criteriaBuilder, List<Order> sorts, FilterMapping filterMapping,
    Path path) {
    if (SortDirection.ASCENDING == filterMapping.getSortDirection()) {
      sorts.add(criteriaBuilder.asc(path));
    } else {
      sorts.add(criteriaBuilder.desc(path));
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   dynamicEntityDao  DOCUMENT ME!
   * @param   ceilingEntity     DOCUMENT ME!
   * @param   filterMappings    DOCUMENT ME!
   * @param   isCount           DOCUMENT ME!
   * @param   firstResult       DOCUMENT ME!
   * @param   maxResults        DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  @SuppressWarnings("unchecked")
  protected TypedQuery<Serializable> constructQuery(DynamicEntityDao dynamicEntityDao, String ceilingEntity,
    List<FilterMapping> filterMappings, boolean isCount, Integer firstResult, Integer maxResults) {
    CriteriaBuilder criteriaBuilder = dynamicEntityDao.getStandardEntityManager().getCriteriaBuilder();

    Class<Serializable> ceilingMarker;

    try {
      ceilingMarker = (Class<Serializable>) Class.forName(ceilingEntity);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }

    Class<Serializable>         ceilingClass = determineRoot(dynamicEntityDao, ceilingMarker, filterMappings);
    CriteriaQuery<Serializable> criteria     = criteriaBuilder.createQuery(ceilingMarker);
    Root<Serializable>          original     = criteria.from(ceilingClass);

    if (isCount) {
      criteria.select(criteriaBuilder.count(original));
    } else {
      criteria.select(original);
    }

    List<Predicate> restrictions = new ArrayList<Predicate>();
    List<Order>     sorts        = new ArrayList<Order>();
    addRestrictions(ceilingEntity, filterMappings, criteriaBuilder, original, restrictions, sorts, criteria);

    criteria.where(restrictions.toArray(new Predicate[restrictions.size()]));

    if (!isCount) {
      criteria.orderBy(sorts.toArray(new Order[sorts.size()]));
    }

    TypedQuery<Serializable> response = dynamicEntityDao.getStandardEntityManager().createQuery(criteria);

    if (!isCount) {
      addPaging(response, firstResult, maxResults);
    }

    return response;
  } // end method constructQuery

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Determines the appropriate entity in this current class tree to use as the ceiling entity for the query. Because we
   * filter with AND instead of OR, we throw an exception if an attempt to utilize properties from mutually exclusive
   * class trees is made as it would be impossible for such a query to return results.
   *
   * @param   dynamicEntityDao  DOCUMENT ME!
   * @param   ceilingMarker     DOCUMENT ME!
   * @param   filterMappings    DOCUMENT ME!
   *
   * @return  the root class
   *
   * @throws  org.broadleafcommerce.common.exception.NoPossibleResultsException
   */
  @SuppressWarnings("unchecked")
  protected Class<Serializable> determineRoot(DynamicEntityDao dynamicEntityDao, Class<Serializable> ceilingMarker,
    List<FilterMapping> filterMappings) throws NoPossibleResultsException {
    Class<?>[] polyEntities = dynamicEntityDao.getAllPolymorphicEntitiesFromCeiling(ceilingMarker);
    ClassTree  root         = dynamicEntityDao.getClassTree(polyEntities);

    List<ClassTree> parents = new ArrayList<ClassTree>();

    for (FilterMapping mapping : filterMappings) {
      if (mapping.getInheritedFromClass() != null) {
        root = determineRootInternal(root, parents, mapping.getInheritedFromClass());

        if (root == null) {
          throw new NoPossibleResultsException("AND filter on different class hierarchies produces no results");
        }
      }
    }

    for (Class<?> clazz : polyEntities) {
      if (clazz.getName().equals(root.getFullyQualifiedClassname())) {
        return (Class<Serializable>) clazz;
      }
    }

    throw new IllegalStateException("Class didn't match - this should not occur");
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Because of the restriction described in
   * {@link #determineRoot(org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao, Class, java.util.List)}, we must
   * check that a class lies inside of the same tree as the current known root. Consider the following situation:
   *
   * <p>Class C extends Class B, which extends Class A. Class E extends Class D, which also extends Class A.</p>
   *
   * <p>We can allow filtering on properties that are either all in C/B/A or all in E/D/A. Filtering on properties
   * across C/B and E/D will always produce no results given an AND style of joining the filtered properties.</p>
   *
   * @param   root          DOCUMENT ME!
   * @param   parents       DOCUMENT ME!
   * @param   classToCheck  DOCUMENT ME!
   *
   * @return  the (potentially new) root or null if invalid
   */
  protected ClassTree determineRootInternal(ClassTree root, List<ClassTree> parents, Class<?> classToCheck) {
    // If the class to check is the current root or a parent of this root, we will continue to use the same root
    if (root.getFullyQualifiedClassname().equals(classToCheck.getName())) {
      return root;
    }

    for (ClassTree parent : parents) {
      if (parent.getFullyQualifiedClassname().equals(classToCheck.getName())) {
        return root;
      }
    }

    try {
      Class<?> rootClass = Class.forName(root.getFullyQualifiedClassname());

      if (classToCheck.isAssignableFrom(rootClass)) {
        return root;
      }
    } catch (ClassNotFoundException e) {
      // Do nothing - we'll continue searching
    }

    parents.add(root);

    for (ClassTree child : root.getChildren()) {
      ClassTree result = child.find(classToCheck.getName());

      if (result != null) {
        return result;
      }
    }

    return null;
  } // end method determineRootInternal

} // end class CriteriaTranslatorImpl
