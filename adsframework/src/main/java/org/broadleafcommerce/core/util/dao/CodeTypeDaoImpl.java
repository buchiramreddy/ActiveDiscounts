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

package org.broadleafcommerce.core.util.dao;

import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.core.util.domain.CodeType;
import org.broadleafcommerce.core.util.domain.CodeTypeImpl;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blCodeTypeDao")
public class CodeTypeDaoImpl implements CodeTypeDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.dao.CodeTypeDao#create()
   */
  @Override public CodeType create() {
    return ((CodeType) entityConfiguration.createEntityInstance(CodeType.class.getName()));
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.dao.CodeTypeDao#delete(org.broadleafcommerce.core.util.domain.CodeType)
   */
  @Override public void delete(CodeType codeType) {
    if (!em.contains(codeType)) {
      codeType = (CodeType) em.find(CodeTypeImpl.class, codeType.getId());
    }

    em.remove(codeType);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.dao.CodeTypeDao#readAllCodeTypes()
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<CodeType> readAllCodeTypes() {
    Query query = em.createNamedQuery("BC_READ_ALL_CODE_TYPES");

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.dao.CodeTypeDao#readCodeTypeById(java.lang.Long)
   */
  @Override public CodeType readCodeTypeById(Long codeTypeId) {
    return (CodeType) em.find(entityConfiguration.lookupEntityClass(CodeType.class.getName()), codeTypeId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.dao.CodeTypeDao#readCodeTypeByKey(java.lang.String)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<CodeType> readCodeTypeByKey(String key) {
    Query query = em.createNamedQuery("BC_READ_CODE_TYPE_BY_KEY");
    query.setParameter("key", key);

    List<CodeType> result = query.getResultList();

    return result;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.core.util.dao.CodeTypeDao#save(org.broadleafcommerce.core.util.domain.CodeType)
   */
  @Override public CodeType save(CodeType codeType) {
    if (codeType.getId() == null) {
      em.persist(codeType);
    } else {
      codeType = em.merge(codeType);
    }

    return codeType;
  }

} // end class CodeTypeDaoImpl
