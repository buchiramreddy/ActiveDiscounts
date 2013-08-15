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

import org.apache.commons.lang3.StringUtils;

import org.broadleafcommerce.openadmin.web.form.component.ListGrid;
import org.broadleafcommerce.openadmin.web.form.entity.Field;

import org.springframework.stereotype.Component;

import org.thymeleaf.Arguments;

import org.thymeleaf.dom.Element;

import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;

import org.thymeleaf.standard.expression.StandardExpressionProcessor;


/**
 * A Thymeleaf processor that will generate the appropriate ID for a given admin component.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
@Component("blAdminComponentIdProcessor")
public class AdminComponentIdProcessor extends AbstractAttributeModifierAttrProcessor {
  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Sets the name of this processor to be used in Thymeleaf template.
   */
  public AdminComponentIdProcessor() {
    super("component_id");
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.AbstractProcessor#getPrecedence()
   */
  @Override public int getPrecedence() {
    return 10002;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   in  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected String cleanCssIdString(String in) {
    in = in.replaceAll("[^a-zA-Z0-9-]", "-");

    return in;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor#getModificationType(org.thymeleaf.Arguments,
   *       org.thymeleaf.dom.Element, java.lang.String, java.lang.String)
   */
  @Override protected ModificationType getModificationType(Arguments arguments, Element element, String attributeName,
    String newAttributeName) {
    return ModificationType.SUBSTITUTION;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor#getModifiedAttributeValues(org.thymeleaf.Arguments,
   *       org.thymeleaf.dom.Element, java.lang.String)
   */
  @Override protected Map<String, String> getModifiedAttributeValues(Arguments arguments, Element element,
    String attributeName) {
    Object component = StandardExpressionProcessor.processExpression(arguments,
        element.getAttributeValue(attributeName));

    String fieldName = "";
    String id        = "";

    if (component instanceof ListGrid) {
      ListGrid lg = (ListGrid) component;

      fieldName = "listGrid-";

      if (ListGrid.Type.MAIN.toString().toLowerCase().equals(lg.getListGridType())) {
        fieldName += ListGrid.Type.MAIN.toString().toLowerCase();
      } else {
        fieldName = fieldName + lg.getListGridType() + '-' + lg.getSubCollectionFieldName();
      }
    } else if (component instanceof Field) {
      Field field = (Field) component;
      fieldName = "field-" + field.getName();
    }

    if (StringUtils.isNotBlank(fieldName)) {
      id = cleanCssIdString(fieldName);
    }

    Map<String, String> attrs = new HashMap<String, String>();
    attrs.put("id", id);

    return attrs;
  } // end method getModifiedAttributeValues

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor#recomputeProcessorsAfterExecution(org.thymeleaf.Arguments,
   *       org.thymeleaf.dom.Element, java.lang.String)
   */
  @Override protected boolean recomputeProcessorsAfterExecution(Arguments arguments, Element element,
    String attributeName) {
    return false;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor#removeAttributeIfEmpty(org.thymeleaf.Arguments,
   *       org.thymeleaf.dom.Element, java.lang.String, java.lang.String)
   */
  @Override protected boolean removeAttributeIfEmpty(Arguments arguments, Element element, String attributeName,
    String newAttributeName) {
    return true;
  }

} // end class AdminComponentIdProcessor
