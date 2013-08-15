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

import java.util.List;

import javax.persistence.TypedQuery;

import org.broadleafcommerce.openadmin.server.dao.DynamicEntityDao;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public interface CriteriaTranslator {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   dynamicEntityDao  DOCUMENT ME!
   * @param   ceilingEntity     DOCUMENT ME!
   * @param   filterMappings    DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  TypedQuery<Serializable> translateCountQuery(DynamicEntityDao dynamicEntityDao, String ceilingEntity,
    List<FilterMapping> filterMappings);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   dynamicEntityDao  DOCUMENT ME!
   * @param   ceilingEntity     DOCUMENT ME!
   * @param   filterMappings    DOCUMENT ME!
   * @param   firstResult       DOCUMENT ME!
   * @param   maxResults        DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  TypedQuery<Serializable> translateQuery(DynamicEntityDao dynamicEntityDao, String ceilingEntity,
    List<FilterMapping> filterMappings, Integer firstResult, Integer maxResults);

} // end interface CriteriaTranslator
