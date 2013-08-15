/*
 * Copyright 2008-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.broadleafcommerce.common.web.dialect;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.thymeleaf.dialect.AbstractDialect;

import org.thymeleaf.processor.IProcessor;

import org.thymeleaf.standard.StandardDialect;
import org.thymeleaf.standard.expression.IStandardVariableExpressionEvaluator;
import org.thymeleaf.standard.expression.StandardExpressionExecutor;
import org.thymeleaf.standard.expression.StandardExpressionParser;
import org.thymeleaf.standard.expression.StandardExpressionProcessor;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class BLCDialect extends AbstractDialect {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  @Resource(name = "blVariableExpressionEvaluator")
  private IStandardVariableExpressionEvaluator expressionEvaluator;

  private Set<IProcessor> processors = new HashSet<IProcessor>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.dialect.AbstractDialect#getExecutionAttributes()
   */
  @Override public Map<String, Object> getExecutionAttributes() {
    final StandardExpressionExecutor executor = StandardExpressionProcessor.createStandardExpressionExecutor(
        expressionEvaluator);
    final StandardExpressionParser   parser   = StandardExpressionProcessor.createStandardExpressionParser(executor);

    final Map<String, Object> executionAttributes = new LinkedHashMap<String, Object>();
    executionAttributes.put(StandardDialect.EXPRESSION_EVALUATOR_EXECUTION_ATTRIBUTE, expressionEvaluator);
    executionAttributes.put(StandardExpressionProcessor.STANDARD_EXPRESSION_EXECUTOR_ATTRIBUTE_NAME, executor);
    executionAttributes.put(StandardExpressionProcessor.STANDARD_EXPRESSION_PARSER_ATTRIBUTE_NAME, parser);

    return executionAttributes;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.dialect.IDialect#getPrefix()
   */
  @Override public String getPrefix() {
    return "blc";
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.dialect.AbstractDialect#getProcessors()
   */
  @Override public Set<IProcessor> getProcessors() {
    return processors;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.dialect.IDialect#isLenient()
   */
  @Override public boolean isLenient() {
    return true;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  processors  DOCUMENT ME!
   */
  public void setProcessors(Set<IProcessor> processors) {
    this.processors = processors;
  }

} // end class BLCDialect
