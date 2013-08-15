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

package org.broadleafcommerce.core.web.money;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.broadleafcommerce.common.money.CurrencyConversionContext;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public abstract class AbstractCurrencyConversionPricingFilter implements CurrencyConversionPricingFilter {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.Filter#destroy()
   */
  @Override public void destroy() {
    // do nothing
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
   *       javax.servlet.FilterChain)
   */
  @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
    throws IOException, ServletException {
    CurrencyConversionContext.setCurrencyConversionContext(getCurrencyConversionContext(request));
    CurrencyConversionContext.setCurrencyConversionService(getCurrencyConversionService(request));

    try {
      filterChain.doFilter(request, response);
    } finally {
      CurrencyConversionContext.setCurrencyConversionContext(null);
      CurrencyConversionContext.setCurrencyConversionService(null);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.Filter#init(javax.servlet.FilterConfig)
   */
  @Override public void init(FilterConfig arg0) throws ServletException {
    // do nothing
  }

} // end class AbstractCurrencyConversionPricingFilter
