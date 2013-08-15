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

package org.broadleafcommerce.core.web.catalog;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.broadleafcommerce.core.catalog.service.dynamic.SkuPricingConsiderationContext;


/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @see      {@link org.broadleafcommerce.core.web.catalog.DefaultDynamicSkuPricingFilter}
 * @version  $Revision$, $Date$
 */
public abstract class AbstractDynamicSkuPricingFilter implements DynamicSkuPricingFilter {
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
    SkuPricingConsiderationContext.setSkuPricingConsiderationContext(getPricingConsiderations(request));
    SkuPricingConsiderationContext.setSkuPricingService(getDynamicSkuPricingService(request));

    try {
      filterChain.doFilter(request, response);
    } finally {
      SkuPricingConsiderationContext.setSkuPricingConsiderationContext(null);
      SkuPricingConsiderationContext.setSkuPricingService(null);
    }

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.Filter#init(javax.servlet.FilterConfig)
   */
  @Override public void init(FilterConfig config) throws ServletException {
    // do nothing
  }

} // end class AbstractDynamicSkuPricingFilter
