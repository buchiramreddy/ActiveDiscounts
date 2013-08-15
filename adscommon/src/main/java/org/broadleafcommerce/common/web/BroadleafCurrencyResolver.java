/*
 * Copyright 2008-2009 the original author or authors.
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

package org.broadleafcommerce.common.web;

import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.common.currency.domain.BroadleafCurrency;

import org.springframework.web.context.request.WebRequest;


/**
 * Author: jerryocanas Date: 9/6/12
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */

/**
 * Responsible for returning the currency to use for the current request.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface BroadleafCurrencyResolver {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param       request  DOCUMENT ME!
   *
   * @return      DOCUMENT ME!
   *
   * @deprecated  use {@link #resolveCurrency(org.springframework.web.context.request.WebRequest)} instead
   */
  @Deprecated BroadleafCurrency resolveCurrency(HttpServletRequest request);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  BroadleafCurrency resolveCurrency(WebRequest request);

} // end interface BroadleafCurrencyResolver
