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
package org.broadleafcommerce.common.extensibility.context.merge;

import org.springframework.core.PriorityOrdered;


/**
 * Use this merge bean post processor for merging tasks that should take place before the persistence layer is
 * initialized. This would include adding class transformers for load time weaving, and the like. See
 * {@link org.broadleafcommerce.common.extensibility.context.merge.AbstractMergeBeanPostProcessor} for usage
 * information.
 *
 * @see      org.broadleafcommerce.common.extensibility.context.merge.AbstractMergeBeanPostProcessor
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class EarlyStageMergeBeanPostProcessor extends AbstractMergeBeanPostProcessor implements PriorityOrdered {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected int order = Integer.MIN_VALUE;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * This is the priority order for this post processor and will determine when this processor is run in relation to
   * other priority ordered processors (e.g.
   * {@link org.springframework.context.annotation.CommonAnnotationBeanPostProcessor}) The default value if
   * Integer.MIN_VALUE.
   *
   * @return  this is the priority order for this post processor and will determine when this processor is run in
   *          relation to other priority ordered processors (e.g.
   */
  @Override public int getOrder() {
    return order;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This is the priority order for this post processor and will determine when this processor is run in relation to
   * other priority ordered processors (e.g.
   * {@link org.springframework.context.annotation.CommonAnnotationBeanPostProcessor}) The default value if
   * Integer.MIN_VALUE.
   *
   * @param  order  the priority ordering
   */
  public void setOrder(int order) {
    this.order = order;
  }

} // end class EarlyStageMergeBeanPostProcessor
