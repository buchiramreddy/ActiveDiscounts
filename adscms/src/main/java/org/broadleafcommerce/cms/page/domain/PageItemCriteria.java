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

import org.broadleafcommerce.common.rule.QuantityBasedRule;


/**
 * Implementations of this interface contain item rule data that is used for targeting <code>Page</code>s.<br>
 * <br>
 * For example, a <code>Page</code> could be setup to only show to user's who have a particular product in their cart.
 *
 * @see      org.broadleafcommerce.core.order.service.PageCartRuleProcessor
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public interface PageItemCriteria extends QuantityBasedRule {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Builds a copy of this item. Used by the content management system when an item is edited.
   *
   * @return  a copy of this item
   */
  @Nonnull PageItemCriteria cloneEntity();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the parent <code>Page</code> to which this field belongs.
   *
   * @return  the parent <code>Page</code> to which this field belongs.
   */
  @Nonnull Page getPage();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Sets the parent <code>Page</code>.
   *
   * @param  page  DOCUMENT ME!
   */
  void setPage(@Nonnull Page page);

} // end interface PageItemCriteria
