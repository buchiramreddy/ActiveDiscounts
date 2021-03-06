/*
 * Copyright 2008-2013 the original author or authors.
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

package org.broadleafcommerce.core.web.order.security;

import javax.annotation.Resource;

import org.springframework.ui.ModelMap;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;


/**
 * Interceptor responsible for putting the current cart on the request. Carts are defined in BLC as an {@link Order}
 * with a status of IN_PROCESS. This interceptor should go after
 * {@link org.broadleafcommerce.profile.web.core.security.CustomerStateInterceptor} since it relies on
 * {@link org.broadleafcommerce.profile.web.core.CustomerState}.
 *
 * <p>Note that in servlet applications you should be using
 * {@link org.broadleafcommerce.core.web.order.security.CartStateFilter}</p>
 *
 * @author   Phillip Verheyden
 * @see      {@link org.broadleafcommerce.core.web.order.CartState}
 * @version  $Revision$, $Date$
 */
public class CartStateInterceptor implements WebRequestInterceptor {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCartStateRequestProcessor")
  protected CartStateRequestProcessor cartStateProcessor;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.web.context.request.WebRequestInterceptor#afterCompletion(org.springframework.web.context.request.WebRequest,
   *       java.lang.Exception)
   */
  @Override public void afterCompletion(WebRequest request, Exception ex) throws Exception {
    // unimplemented
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.web.context.request.WebRequestInterceptor#postHandle(org.springframework.web.context.request.WebRequest,
   *       org.springframework.ui.ModelMap)
   */
  @Override public void postHandle(WebRequest request, ModelMap model) throws Exception {
    // unimplemented
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.web.context.request.WebRequestInterceptor#preHandle(org.springframework.web.context.request.WebRequest)
   */
  @Override public void preHandle(WebRequest request) throws Exception {
    cartStateProcessor.process(request);
  }

} // end class CartStateInterceptor
