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

package org.broadleafcommerce.common.money;

import java.util.HashMap;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class CurrencyConversionContext {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final ThreadLocal<CurrencyConversionService> currencyConversionService =
    new ThreadLocal<CurrencyConversionService>();

  private static final ThreadLocal<HashMap> currencyConversionContext = new ThreadLocal<HashMap>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static HashMap getCurrencyConversionContext() {
    return CurrencyConversionContext.currencyConversionContext.get();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static CurrencyConversionService getCurrencyConversionService() {
    return CurrencyConversionContext.currencyConversionService.get();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  currencyConsiderationContext  DOCUMENT ME!
   */
  public static void setCurrencyConversionContext(HashMap currencyConsiderationContext) {
    CurrencyConversionContext.currencyConversionContext.set(currencyConsiderationContext);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  currencyDeterminationService  DOCUMENT ME!
   */
  public static void setCurrencyConversionService(CurrencyConversionService currencyDeterminationService) {
    CurrencyConversionContext.currencyConversionService.set(currencyDeterminationService);
  }

} // end class CurrencyConversionContext
