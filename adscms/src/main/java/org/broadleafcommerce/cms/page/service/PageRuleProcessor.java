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

package org.broadleafcommerce.cms.page.service;

import java.util.Map;

import org.broadleafcommerce.cms.page.dto.PageDTO;


/**
 * Page rule processors check each content item to see if it qualifies for inclusion in the result set.
 *
 * <p>This is called by PageService to determine if a given content item should be returned to the caller.</p>
 *
 * <p>BLC created rule processors to solve a dependency issue. Some variables to be used in rule processing are within
 * the scope of the CMS module while others are not.</p>
 *
 * <p>For example, checking for cart rules would tie the CMS rules engine with a direct dependency on the cart.</p>
 *
 * <p>Instead, we've opted to create this interface which allows other components to add rule-processors as needed.</p>
 *
 * @see      {@link org.broadleafcommerce.cms.page.service.PageDefaultRuleProcessor}
 * @author   bpolster.
 * @version  $Revision$, $Date$
 */
public interface PageRuleProcessor {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns true if the passed in <code>Page</code> is valid according to this rule processor.
   *
   * @param   page      DOCUMENT ME!
   * @param   valueMap  DOCUMENT ME!
   *
   * @return  true if the passed in <code>Page</code> is valid according to this rule processor.
   */
  boolean checkForMatch(PageDTO page, Map<String, Object> valueMap);
}
