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

package org.broadleafcommerce.common.web.filter;

import javax.annotation.Resource;

import org.springframework.ui.ModelMap;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.context.request.WebRequestInterceptor;


/**
 * Interceptor for use with portlets that calls the
 * {@link org.broadleafcommerce.common.web.filter.TranslationRequestProcessor}.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class TranslationInterceptor implements WebRequestInterceptor {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blTranslationRequestProcessor")
  protected TranslationRequestProcessor translationRequestProcessor;

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
    translationRequestProcessor.process(request);
  }
} // end class TranslationInterceptor
