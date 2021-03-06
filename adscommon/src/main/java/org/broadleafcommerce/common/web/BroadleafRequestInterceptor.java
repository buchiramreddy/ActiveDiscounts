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

package org.broadleafcommerce.common.web;

import javax.annotation.Resource;

import org.springframework.ui.ModelMap;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;


/**
 * <p>Interceptor responsible for setting up the BroadleafRequestContext for the life of the request. This interceptor
 * should be the very first one in the list, as other interceptors might also use
 * {@link org.broadleafcommerce.common.web.BroadleafRequestContext}.</p>
 *
 * <p>Note that in Servlet applications you should be using the
 * {@link org.broadleafcommerce.common.web.BroadleafRequestFilter}.</p>
 *
 * @author   Phillip Verheyden
 * @see      {@link org.broadleafcommerce.common.web.BroadleafRequestProcessor}
 * @see      {@link org.broadleafcommerce.common.web.BroadleafRequestContext}
 * @version  $Revision$, $Date$
 */
public class BroadleafRequestInterceptor implements WebRequestInterceptor {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blRequestProcessor")
  protected BroadleafRequestProcessor requestProcessor;

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
    requestProcessor.process(request);
  }

} // end class BroadleafRequestInterceptor
