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

import java.util.Map;

import org.thymeleaf.Arguments;

import org.thymeleaf.dom.Element;
import org.thymeleaf.dom.NestableNode;

import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;


/**
 * DOCUMENT ME!
 *
 * @author   apazzolini
 *
 *           <p>Wrapper class around Thymeleaf's AbstractElementProcessor that facilitates adding Objects to the current
 *           evaluation context (model) for processing in the remainder of the page.</p>
 * @version  $Revision$, $Date$
 */
public abstract class AbstractModelVariableModifierProcessor extends AbstractElementProcessor {
  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new AbstractModelVariableModifierProcessor object.
   *
   * @param  elementName  DOCUMENT ME!
   */
  public AbstractModelVariableModifierProcessor(String elementName) {
    super(elementName);
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * This method must be overriding by a processor that wishes to modify the model. It will be called by this abstract
   * processor in the correct precendence in the evaluation chain.
   *
   * @param  arguments  DOCUMENT ME!
   * @param  element    DOCUMENT ME!
   */
  protected abstract void modifyModelAttributes(Arguments arguments, Element element);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Helper method to add a value to the expression evaluation root (model) Map.
   *
   * @param  arguments  DOCUMENT ME!
   * @param  key        the key to add to the model
   * @param  value      the value represented by the key
   */
  @SuppressWarnings("unchecked")
  protected void addToModel(Arguments arguments, String key, Object value) {
    ((Map<String, Object>) arguments.getExpressionEvaluationRoot()).put(key, value);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * This method will handle calling the modifyModelAttributes abstract method and return an "OK" processor result.
   *
   * @param   arguments  DOCUMENT ME!
   * @param   element    DOCUMENT ME!
   *
   * @return  this method will handle calling the modifyModelAttributes abstract method and return an "OK" processor
   *          result.
   */
  @Override protected ProcessorResult processElement(final Arguments arguments, final Element element) {
    modifyModelAttributes(arguments, element);

    // Remove the tag from the DOM
    final NestableNode parent = element.getParent();
    parent.removeChild(element);

    return ProcessorResult.OK;
  }
} // end class AbstractModelVariableModifierProcessor
