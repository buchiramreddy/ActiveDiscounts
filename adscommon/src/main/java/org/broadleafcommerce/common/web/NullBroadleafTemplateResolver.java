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

package org.broadleafcommerce.common.web;

import org.thymeleaf.TemplateProcessingParameters;

import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolution;


/**
 * Placeholder component to support a custom TemplateResolver.
 *
 * <p>Utilized by the Broadleaf Commerce CustomTemplate extension to introduce themes at the DB level.</p>
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public class NullBroadleafTemplateResolver implements ITemplateResolver {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.templateresolver.ITemplateResolver#getName()
   */
  @Override public String getName() {
    return "NullBroadleafTemplateResolver";
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.templateresolver.ITemplateResolver#getOrder()
   */
  @Override public Integer getOrder() {
    return 9999;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.templateresolver.ITemplateResolver#initialize()
   */
  @Override public void initialize() { }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.templateresolver.ITemplateResolver#resolveTemplate(org.thymeleaf.TemplateProcessingParameters)
   */
  @Override public TemplateResolution resolveTemplate(TemplateProcessingParameters templateProcessingParameters) {
    return null;
  }
} // end class NullBroadleafTemplateResolver
