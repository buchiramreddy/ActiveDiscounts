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

package org.broadleafcommerce.cms.page.domain;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.broadleafcommerce.common.rule.SimpleRule;


/**
 * Implementations hold the values for a rule used to determine if a <code>Page</code> should be displayed.<br>
 * The rule is represented as a valid MVEL string. The Content Management System by default is able to process rules
 * based on the current customer, product, {@link org.broadleafcommerce.common.TimeDTO time}, or
 * {@link org.broadleafcommerce.common.RequestDTO request}
 *
 * @see      org.broadleafcommerce.cms.web.structure.DisplayContentTag
 * @see      org.broadleafcommerce.cms.structure.service.PageServiceImpl#evaluateAndPriortizePages(java.util.List, int,
 *           java.util.Map)
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public interface PageRule extends SimpleRule {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Builds a copy of this content rule. Used by the content management system when an item is edited.
   *
   * @return  a copy of this rule
   */
  @Nonnull PageRule cloneEntity();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Gets the primary key.
   *
   * @return  the primary key
   */
  @Nullable Long getId();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the primary key.
   *
   * @param  id  the new primary key
   */
  void setId(@Nullable Long id);

} // end interface PageRule
