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

package org.broadleafcommerce.common.currency.dao;

import java.util.List;

import javax.annotation.Resource;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.persistence.EntityConfiguration;

import org.springframework.stereotype.Repository;


/**
 * Author: jerryocanas Date: 9/6/12
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */

@Repository("blCurrencyDao")
public class BroadleafCurrencyDaoImpl implements BroadleafCurrencyDao {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @PersistenceContext(unitName = "blPU")
  protected EntityManager em;

  /** DOCUMENT ME! */
  @Resource(name = "blEntityConfiguration")
  protected EntityConfiguration entityConfiguration;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The locale for the passed in code.
   *
   * @param   currencyCode  DOCUMENT ME!
   *
   * @return  The locale for the passed in code.
   */
  @Override public BroadleafCurrency findCurrencyByCode(String currencyCode) {
    Query query = em.createNamedQuery("BC_READ_CURRENCY_BY_CODE");
    query.setParameter("currencyCode", currencyCode);
    query.setHint(org.hibernate.ejb.QueryHints.HINT_CACHEABLE, true);

    List<BroadleafCurrency> currencyList = (List<BroadleafCurrency>) query.getResultList();

    if (currencyList.size() >= 1) {
      return currencyList.get(0);
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.dao.BroadleafCurrencyDao#findDefaultBroadleafCurrency()
   */
  @Override public BroadleafCurrency findDefaultBroadleafCurrency() {
    Query query = em.createNamedQuery("BC_READ_DEFAULT_CURRENCY");
    query.setHint(org.hibernate.ejb.QueryHints.HINT_CACHEABLE, true);

    List<BroadleafCurrency> currencyList = (List<BroadleafCurrency>) query.getResultList();

    if (currencyList.size() >= 1) {
      return currencyList.get(0);
    }

    return null;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.dao.BroadleafCurrencyDao#getAllCurrencies()
   */
  @Override public List<BroadleafCurrency> getAllCurrencies() {
    Query query = em.createNamedQuery("BC_READ_ALL_CURRENCIES");
    query.setHint(org.hibernate.ejb.QueryHints.HINT_CACHEABLE, true);

    return (List<BroadleafCurrency>) query.getResultList();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.dao.BroadleafCurrencyDao#save(org.broadleafcommerce.common.currency.domain.BroadleafCurrency)
   */
  @Override public BroadleafCurrency save(BroadleafCurrency currency) {
    return em.merge(currency);
  }
} // end class BroadleafCurrencyDaoImpl
