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

package org.broadleafcommerce.cms.structure.service;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.cms.structure.dto.StructuredContentDTO;

import org.springframework.stereotype.Service;


/**
 * By default, this rule processor combines all of the rules from
 * {@link org.broadleafcommerce.cms.structure.domain.StructuredContent#getStructuredContentMatchRules()} into a single
 * MVEL expression.
 *
 * @author   bpolster.
 * @version  $Revision$, $Date$
 */
@Service("blContentDefaultRuleProcessor")
public class StructuredContentDefaultRuleProcessor extends AbstractStructuredContentRuleProcessor {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(StructuredContentDefaultRuleProcessor.class);

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns true if all of the rules associated with the passed in <code>StructuredContent</code> item match based on
   * the passed in vars.
   *
   * <p>Also returns true if no rules are present for the passed in item.</p>
   *
   * @param   sc    - a structured content item to test
   * @param   vars  - a map of objects used by the rule MVEL expressions
   *
   * @return  the result of the rule checks
   */
  @Override public boolean checkForMatch(StructuredContentDTO sc, Map<String, Object> vars) {
    String ruleExpression = sc.getRuleExpression();

    if (ruleExpression != null) {
      if (LOG.isTraceEnabled()) {
        LOG.trace("Processing content rule for StructuredContent with id " + sc.getId() + ".   Value = "
          + ruleExpression);
      }

      boolean result = executeExpression(ruleExpression, vars);

      if (!result) {
        if (LOG.isDebugEnabled()) {
          LOG.debug("Content failed to pass rule and will not be included for StructuredContent with id " + sc.getId()
            + ".   Value = " + ruleExpression);
        }
      }

      return result;
    } else {
      // If no rule found, then consider this a match.
      return true;
    }
  }
} // end class StructuredContentDefaultRuleProcessor
