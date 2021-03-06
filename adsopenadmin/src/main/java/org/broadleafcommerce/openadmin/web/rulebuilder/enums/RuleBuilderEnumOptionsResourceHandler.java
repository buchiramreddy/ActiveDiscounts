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

package org.broadleafcommerce.openadmin.web.rulebuilder.enums;

import java.util.List;

import org.broadleafcommerce.common.resource.GeneratedResource;
import org.broadleafcommerce.common.web.resource.AbstractGeneratedResourceHandler;

import org.springframework.core.io.Resource;

import org.springframework.stereotype.Component;


/**
 * Generated resource handler for blc-rulebuilder-options.js.
 *
 * <p>Delegates to all registered
 * {@link org.broadleafcommerce.openadmin.web.rulebuilder.enums.RuleBuilderEnumOptionsExtensionListener} to create the
 * resource</p>
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
@Component("blRuleBuilderEnumOptionsResourceHandler")
public class RuleBuilderEnumOptionsResourceHandler extends AbstractGeneratedResourceHandler {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @javax.annotation.Resource(name = "blRuleBuilderEnumOptionsExtensionManager")
  protected RuleBuilderEnumOptionsExtensionManager ruleBuilderEnumOptions;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.web.resource.AbstractGeneratedResourceHandler#canHandle(java.lang.String)
   */
  @Override public boolean canHandle(String path) {
    return "admin/components/ruleBuilder-options.js".equals(path);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.web.resource.AbstractGeneratedResourceHandler#getFileContents(java.lang.String, java.util.List)
   */
  @Override public Resource getFileContents(String path, List<Resource> locations) {
    return new GeneratedResource(ruleBuilderEnumOptions.getOptionValues().getBytes(), path);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.web.resource.AbstractGeneratedResourceHandler#isCachedResourceExpired(org.broadleafcommerce.common.resource.GeneratedResource,
   *       java.lang.String, java.util.List)
   */
  @Override public boolean isCachedResourceExpired(GeneratedResource cachedResource, String path,
    List<Resource> locations) {
    return false;
  }

} // end class RuleBuilderEnumOptionsResourceHandler
