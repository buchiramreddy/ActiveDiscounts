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

import java.io.IOException;

import javax.annotation.Resource;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.GenericFilterBean;


/**
 * Responsible for setting the necessary attributes on the
 * {@link org.broadleafcommerce.common.i18n.service.TranslationConsiderationContext}.
 *
 * @author   Andre Azzolini (apazzolini), bpolster
 * @version  $Revision$, $Date$
 */
@Component("blTranslationFilter")
public class TranslationFilter extends GenericFilterBean {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blTranslationRequestProcessor")
  protected TranslationRequestProcessor translationRequestProcessor;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse,
   *       javax.servlet.FilterChain)
   */
  @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
    throws IOException, ServletException {
    translationRequestProcessor.process(new ServletWebRequest((HttpServletRequest) request,
        (HttpServletResponse) response));
    filterChain.doFilter(request, response);
  }
}
