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

import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.openadmin.server.security.domain.AdminSection;

import org.springframework.stereotype.Component;

import org.thymeleaf.Arguments;

import org.thymeleaf.dom.Element;

import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;

import org.thymeleaf.spring3.context.SpringWebContext;

import org.thymeleaf.standard.expression.StandardExpressionProcessor;


/**
 * A Thymeleaf processor that will generate the HREF of a given Admin Section. This is useful in constructing the left
 * navigation menu for the admin console.
 *
 * @author   elbertbautista
 * @version  $Revision$, $Date$
 */
@Component("blAdminSectionHrefProcessor")
public class AdminSectionHrefProcessor extends AbstractAttributeModifierAttrProcessor {
  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Sets the name of this processor to be used in Thymeleaf template.
   */
  public AdminSectionHrefProcessor() {
    super("admin_section_href");
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
    String href = "#";

    AdminSection section = (AdminSection) StandardExpressionProcessor.processExpression(arguments,
        element.getAttributeValue(attributeName));

    if (section != null) {
      HttpServletRequest request = ((SpringWebContext) arguments.getContext()).getHttpServletRequest();

      href = request.getContextPath() + section.getUrl();
    }

    Map<String, String> attrs = new HashMap<String, String>();
    attrs.put("href", href);

    return attrs;
  }

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

} // end class AdminSectionHrefProcessor
