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

package org.broadleafcommerce.common.web.expression;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import org.thymeleaf.context.IProcessingContext;

import org.thymeleaf.spring3.expression.SpelVariableExpressionEvaluator;


/**
 * Provides a skeleton to register multiple
 * {@link org.broadleafcommerce.common.web.expression.BroadleafVariableExpression} implementors.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
@Component("blVariableExpressionEvaluator")
public class BroadleafVariableExpressionEvaluator extends SpelVariableExpressionEvaluator {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blVariableExpressions")
  protected List<BroadleafVariableExpression> expressions = new ArrayList<BroadleafVariableExpression>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.spring3.expression.SpelVariableExpressionEvaluator#computeAdditionalExpressionObjects(org.thymeleaf.context.IProcessingContext)
   */
  @Override protected Map<String, Object> computeAdditionalExpressionObjects(
    final IProcessingContext processingContext) {
    Map<String, Object> map = new HashMap<String, Object>();

    for (BroadleafVariableExpression expression : expressions) {
      if (!(expression instanceof NullBroadleafVariableExpression)) {
        map.put(expression.getName(), expression);
      }
    }

    return map;
  }

} // end class BroadleafVariableExpressionEvaluator
