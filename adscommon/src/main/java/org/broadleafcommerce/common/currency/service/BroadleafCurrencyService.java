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

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;


/**
 * Author: jerryocanas Date: 9/6/12
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface BroadleafCurrencyService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns a Broadleaf currency found by a code.
   *
   * @param   currencyCode  DOCUMENT ME!
   *
   * @return  The currency
   */
  BroadleafCurrency findCurrencyByCode(String currencyCode);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the default Broadleaf currency.
   *
   * @return  The default currency
   */
  BroadleafCurrency findDefaultBroadleafCurrency();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a list of all the Broadleaf Currencies.
   *
   * @return  List of currencies
   */
  List<BroadleafCurrency> getAllCurrencies();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   currency  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  BroadleafCurrency save(BroadleafCurrency currency);
} // end interface BroadleafCurrencyService
