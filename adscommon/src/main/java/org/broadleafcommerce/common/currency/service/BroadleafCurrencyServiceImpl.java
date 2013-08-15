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

package org.broadleafcommerce.common.currency.service;

import java.util.List;

import javax.annotation.Resource;

import org.broadleafcommerce.common.currency.dao.BroadleafCurrencyDao;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;

import org.springframework.stereotype.Service;


/**
 * Author: jerryocanas Date: 9/6/12
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */

@Service("blCurrencyService")
public class BroadleafCurrencyServiceImpl implements BroadleafCurrencyService {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCurrencyDao")
  protected BroadleafCurrencyDao currencyDao;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The currency for the passed in code.
   *
   * @param   currencyCode  DOCUMENT ME!
   *
   * @return  The currency for the passed in code.
   */
  @Override public BroadleafCurrency findCurrencyByCode(String currencyCode) {
    return currencyDao.findCurrencyByCode(currencyCode);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the default Broadleaf currency.
   *
   * @return  The default currency
   */
  @Override public BroadleafCurrency findDefaultBroadleafCurrency() {
    return currencyDao.findDefaultBroadleafCurrency();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a list of all the Broadleaf Currencies.
   *
   * @return  List of currencies
   */
  @Override public List<BroadleafCurrency> getAllCurrencies() {
    return currencyDao.getAllCurrencies();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.currency.service.BroadleafCurrencyService#save(org.broadleafcommerce.common.currency.domain.BroadleafCurrency)
   */
  @Override public BroadleafCurrency save(BroadleafCurrency currency) {
    return currencyDao.save(currency);
  }
} // end class BroadleafCurrencyServiceImpl
