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

package org.broadleafcommerce.core.web.processor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.common.web.BroadleafRequestContext;

import org.broadleafcommerce.core.search.domain.ProductSearchCriteria;
import org.broadleafcommerce.core.web.util.ProcessorUtils;

import org.springframework.stereotype.Component;

import org.thymeleaf.Arguments;

import org.thymeleaf.dom.Element;

import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;

import org.thymeleaf.standard.expression.StandardExpressionProcessor;


/**
 * A Thymeleaf processor that processes the value attribute on the element it's tied to with a predetermined value based
 * on the SearchFacetResultDTO object that is passed into this processor.
 *
 * @author   apazzolini
 * @version  $Revision$, $Date$
 */
@Component("blPaginationPageLinkProcessor")
public class PaginationPageLinkProcessor extends AbstractAttributeModifierAttrProcessor {
  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Sets the name of this processor to be used in Thymeleaf template.
   */
  public PaginationPageLinkProcessor() {
    super("paginationpagelink");
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.thymeleaf.processor.AbstractProcessor#getPrecedence()
   */
  @Override public int getPrecedence() {
    return 10000;
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
  @Override
  @SuppressWarnings("unchecked")
  protected Map<String, String> getModifiedAttributeValues(Arguments arguments, Element element, String attributeName) {
    Map<String, String> attrs = new HashMap<String, String>();

    BroadleafRequestContext blcContext = BroadleafRequestContext.getBroadleafRequestContext();
    HttpServletRequest      request    = blcContext.getRequest();

    String                baseUrl = request.getRequestURL().toString();
    Map<String, String[]> params  = new HashMap<String, String[]>(request.getParameterMap());

    Integer page = (Integer) StandardExpressionProcessor.processExpression(arguments,
        element.getAttributeValue(attributeName));

    if ((page != null) && (page > 1)) {
      params.put(ProductSearchCriteria.PAGE_NUMBER, new String[] { page.toString() });
    } else {
      params.remove(ProductSearchCriteria.PAGE_NUMBER);
    }

    String url = ProcessorUtils.getUrl(baseUrl, params);

    attrs.put("href", url);

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
} // end class PaginationPageLinkProcessor
