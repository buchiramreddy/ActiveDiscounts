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

package org.broadleafcommerce.common.currency;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;
import org.broadleafcommerce.common.currency.domain.BroadleafCurrencyImpl;

import org.testng.annotations.DataProvider;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class BroadleafCurrencyProvider {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @DataProvider(name = "FRCurrency")
  public static Object[][] provideFRCurrency() {
    BroadleafCurrency currency = new BroadleafCurrencyImpl();
    currency.setCurrencyCode("EUR");
    currency.setDefaultFlag(true);
    currency.setFriendlyName("EURO Dollar");

    return new Object[][] {
        { currency }
      };
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  @DataProvider(name = "USCurrency")
  public static Object[][] provideUSCurrency() {
    BroadleafCurrency currency = new BroadleafCurrencyImpl();
    currency.setCurrencyCode("USD");
    currency.setDefaultFlag(true);
    currency.setFriendlyName("US Dollar");

    return new Object[][] {
        { currency }
      };
  }
} // end class BroadleafCurrencyProvider
