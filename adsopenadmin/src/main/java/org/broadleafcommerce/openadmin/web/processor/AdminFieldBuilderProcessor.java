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

package org.broadleafcommerce.openadmin.web.processor;

import java.util.HashMap;
import java.util.Map;

import org.broadleafcommerce.openadmin.web.rulebuilder.dto.FieldWrapper;
import org.broadleafcommerce.openadmin.web.rulebuilder.service.RuleBuilderFieldService;
import org.broadleafcommerce.openadmin.web.rulebuilder.service.RuleBuilderFieldServiceFactory;

import org.springframework.context.ApplicationContext;

import org.springframework.stereotype.Component;

import org.thymeleaf.Arguments;

import org.thymeleaf.dom.Element;

import org.thymeleaf.processor.element.AbstractLocalVariableDefinitionElementProcessor;

import org.thymeleaf.spring3.context.SpringWebContext;

import org.thymeleaf.standard.expression.StandardExpressionProcessor;


/**
 * DOCUMENT ME!
 *
 * @author   Elbert Bautista (elbertbautista)
 * @version  $Revision$, $Date$
 */
@Component("blAdminFieldBuilderProcessor")
public class AdminFieldBuilderProcessor extends AbstractLocalVariableDefinitionElementProcessor {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private RuleBuilderFieldServiceFactory ruleBuilderFieldServiceFactory;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Sets the name of this processor to be used in Thymeleaf template.
   */
  public AdminFieldBuilderProcessor() {
    super("admin_field_builder");
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.AbstractProcessor#getPrecedence()
   */
  @Override public int getPrecedence() {
    return 100;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.element.AbstractLocalVariableDefinitionElementProcessor#getNewLocalVariables(org.thymeleaf.Arguments,
   *       org.thymeleaf.dom.Element)
   */
  @Override protected Map<String, Object> getNewLocalVariables(Arguments arguments, Element element) {
    initServices(arguments);

    FieldWrapper fieldWrapper = new FieldWrapper();

    String fieldBuilder = (String) StandardExpressionProcessor.processExpression(arguments,
        element.getAttributeValue("fieldBuilder"));

    if (fieldBuilder != null) {
      RuleBuilderFieldService ruleBuilderFieldService = ruleBuilderFieldServiceFactory.createInstance(fieldBuilder);

      if (ruleBuilderFieldService != null) {
        fieldWrapper = ruleBuilderFieldService.buildFields();
      }
    }

    Map<String, Object> newVars = new HashMap<String, Object>();
    newVars.put("fieldWrapper", fieldWrapper);

    return newVars;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  arguments  DOCUMENT ME!
   */
  protected void initServices(Arguments arguments) {
    final ApplicationContext applicationContext = ((SpringWebContext) arguments.getContext()).getApplicationContext();

    if (ruleBuilderFieldServiceFactory == null) {
      ruleBuilderFieldServiceFactory = (RuleBuilderFieldServiceFactory) applicationContext.getBean(
          "blRuleBuilderFieldServiceFactory");
    }

  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.element.AbstractLocalVariableDefinitionElementProcessor#removeHostElement(org.thymeleaf.Arguments,
   *       org.thymeleaf.dom.Element)
   */
  @Override protected boolean removeHostElement(Arguments arguments, Element element) {
    return false;
  }
} // end class AdminFieldBuilderProcessor
