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

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.LRUMap;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.rule.MvelHelper;

import org.mvel2.CompileException;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;


/**
 * This class is useful as a starting point for rule processors that need to execute MVEL rules.
 *
 * <p>Sets up an LRU cache for rule processing and a convenience method for executing MVEL rules.</p>
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public abstract class AbstractStructuredContentRuleProcessor implements StructuredContentRuleProcessor {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(AbstractStructuredContentRuleProcessor.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  private Map<String, String> contextClassNames = new HashMap<String, String>();

  private Map           expressionCache = Collections.synchronizedMap(new LRUMap(1000));
  private ParserContext parserContext;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * List of class names to add to the MVEL ParserContext.
   *
   * @return  list of class names to add to the MVEL ParserContext.
   *
   * @see     {@link org.mvel2.ParserContext}
   */
  public Map<String, String> getContextClassNames() {
    return contextClassNames;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * List of class names to add to the MVEL ParserContext.
   *
   * @param  contextClassNames  DOCUMENT ME!
   *
   * @see    {@link org.mvel2.ParserContext}
   */
  public void setContextClassNames(Map<String, String> contextClassNames) {
    this.contextClassNames = contextClassNames;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Helpful method for processing a boolean MVEL expression and associated arguments.
   *
   * <p>Caches the expression in an LRUCache.</p>
   *
   * @param   expression  DOCUMENT ME!
   * @param   vars        DOCUMENT ME!
   *
   * @return  the result of the expression
   */
  protected Boolean executeExpression(String expression, Map<String, Object> vars) {
    Serializable exp = (Serializable) expressionCache.get(expression);
    vars.put("MVEL", MVEL.class);

    if (exp == null) {
      try {
        exp = MVEL.compileExpression(expression, getParserContext());
      } catch (CompileException ce) {
        LOG.warn("Compile exception processing phrase: " + expression, ce);

        return Boolean.FALSE;
      }

      expressionCache.put(expression, exp);
    }

    try {
      return (Boolean) MVEL.executeExpression(exp, vars);
    } catch (Exception e) {
      LOG.error(e);
    }

    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Having a parser context that imports the classes speeds MVEL by up to 60%.
   *
   * @return  having a parser context that imports the classes speeds MVEL by up to 60%.
   */
  protected ParserContext getParserContext() {
    if (parserContext == null) {
      parserContext = new ParserContext();
      parserContext.addImport("MVEL", MVEL.class);
      parserContext.addImport("MvelHelper", MvelHelper.class);
      /*  Getting errors when the following is in place.
      for (String key : contextClassNames.keySet()) {
           String className = contextClassNames.get(key);
           try {
               Class c = Class.forName(className);
               parserContext.addImport(key, c);
           } catch (ClassNotFoundException e) {
               LOG.error("Error resolving classname while setting up MVEL context, rule processing based on the key " + key + " will not be optimized", e);
           }

       }  */
    }

    return parserContext;
  }
} // end class AbstractStructuredContentRuleProcessor
