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

package org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.predicate;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.FieldPathBuilder;

import org.springframework.stereotype.Component;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
@Component("blIsNullPredicateProvider")
public class IsNullPredicateProvider implements PredicateProvider {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.predicate.PredicateProvider#buildPredicate(javax.persistence.criteria.CriteriaBuilder,
   *       org.broadleafcommerce.openadmin.server.service.persistence.module.criteria.FieldPathBuilder,
   *       javax.persistence.criteria.From, java.lang.String, java.lang.String, javax.persistence.criteria.Path,
   *       java.util.List)
   */
  @Override public Predicate buildPredicate(CriteriaBuilder builder, FieldPathBuilder fieldPathBuilder, From root,
    String ceilingEntity,
    String fullPropertyName, Path explicitPath, List directValues) {
    Path<Long> path;

    if (explicitPath != null) {
      path = explicitPath;
    } else {
      path = fieldPathBuilder.getPath(root, fullPropertyName, builder);
    }

    return builder.isNull(path);
  }
}
