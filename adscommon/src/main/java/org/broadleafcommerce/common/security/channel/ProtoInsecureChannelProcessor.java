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

package org.broadleafcommerce.common.security.channel;

import java.io.IOException;

import java.util.Collection;

import javax.servlet.ServletException;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.channel.InsecureChannelProcessor;


/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class ProtoInsecureChannelProcessor extends InsecureChannelProcessor {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.security.web.access.channel.InsecureChannelProcessor#decide(org.springframework.security.web.FilterInvocation,
   *       java.util.Collection)
   */
  @Override public void decide(FilterInvocation invocation, Collection<ConfigAttribute> config) throws IOException,
    ServletException {
    if ((invocation == null) || (config == null)) {
      throw new IllegalArgumentException("Nulls cannot be provided");
    }

    for (ConfigAttribute attribute : config) {
      if (supports(attribute)) {
        if (invocation.getHttpRequest().getHeader("X-Forwarded-Proto").equals("https")) {
          getEntryPoint().commence(invocation.getRequest(), invocation.getResponse());
        }
      }
    }
  }
}
