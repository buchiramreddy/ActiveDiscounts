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

package org.broadleafcommerce.core.search.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.core.search.domain.SearchSynonym;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blSearchSynonymDao")
public class SearchSynonymDaoImpl implements SearchSynonymDao {
  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /**
   * @see  org.broadleafcommerce.core.search.dao.SearchSynonymDao#getAllSynonyms()
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<SearchSynonym> getAllSynonyms() {
    Query               query  = em.createNamedQuery("BC_READ_SEARCH_SYNONYMS");
    List<SearchSynonym> result;

    try {
      result = (List<SearchSynonym>) query.getResultList();
    } catch (NoResultException e) {
      result = null;
    }

    return result;
  }

  /**
   * @see  org.broadleafcommerce.core.search.dao.SearchSynonymDao#createSynonym(org.broadleafcommerce.core.search.domain.SearchSynonym)
   */
  @Override public void createSynonym(SearchSynonym synonym) {
    em.persist(synonym);
  }

  /**
   * @see  org.broadleafcommerce.core.search.dao.SearchSynonymDao#deleteSynonym(org.broadleafcommerce.core.search.domain.SearchSynonym)
   */
  @Override public void deleteSynonym(SearchSynonym synonym) {
    em.remove(synonym);
  }

  /**
   * @see  org.broadleafcommerce.core.search.dao.SearchSynonymDao#updateSynonym(org.broadleafcommerce.core.search.domain.SearchSynonym)
   */
  @Override public void updateSynonym(SearchSynonym synonym) {
    em.merge(synonym);
  }
} // end class SearchSynonymDaoImpl
