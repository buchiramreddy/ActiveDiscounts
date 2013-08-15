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

package org.broadleafcommerce.profile.core.dao;

import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.broadleafcommerce.profile.core.domain.Country;
import org.broadleafcommerce.profile.core.domain.CountryImpl;
import org.broadleafcommerce.profile.core.domain.State;
import org.broadleafcommerce.profile.core.domain.StateImpl;

import org.hibernate.ejb.QueryHints;

import org.springframework.stereotype.Repository;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Repository("blStateDao")
public class StateDaoImpl implements StateDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.StateDao#create()
   */
  @Override public State create() {
    return (State) entityConfiguration.createEntityInstance(State.class.getName());
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @SuppressWarnings("unchecked")
  public List<Country> findCountries() {
    Query query = em.createNamedQuery("BC_FIND_COUNTRIES");
    query.setHint(QueryHints.HINT_CACHEABLE, true);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   shortName  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Country findCountryByShortName(String shortName) {
    return (Country) em.find(CountryImpl.class, shortName);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.StateDao#findStateByAbbreviation(java.lang.String)
   */
  @Override public State findStateByAbbreviation(String abbreviation) {
    return (State) em.find(StateImpl.class, abbreviation);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.StateDao#findStates()
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<State> findStates() {
    Query query = em.createNamedQuery("BC_FIND_STATES");
    query.setHint(QueryHints.HINT_CACHEABLE, true);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.StateDao#findStates(java.lang.String)
   */
  @Override
  @SuppressWarnings("unchecked")
  public List<State> findStates(String countryAbbreviation) {
    Query query = em.createNamedQuery("BC_FIND_STATES_BY_COUNTRY_ABBREVIATION");
    query.setParameter("countryAbbreviation", countryAbbreviation);
    query.setHint(QueryHints.HINT_CACHEABLE, true);

    return query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.dao.StateDao#save(org.broadleafcommerce.profile.core.domain.State)
   */
  @Override public State save(State state) {
    return em.merge(state);
  }
} // end class StateDaoImpl
