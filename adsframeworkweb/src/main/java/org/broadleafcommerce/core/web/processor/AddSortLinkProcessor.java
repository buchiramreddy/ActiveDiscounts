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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.broadleafcommerce.common.web.BroadleafRequestContext;

import org.broadleafcommerce.core.search.domain.ProductSearchCriteria;
import org.broadleafcommerce.core.web.util.ProcessorUtils;

import org.springframework.stereotype.Component;

import org.thymeleaf.Arguments;

import org.thymeleaf.dom.Element;

import org.thymeleaf.processor.attr.AbstractAttributeModifierAttrProcessor;


/**
 * A Thymeleaf processor that generates a search query link that will reflect the current search criteria in addition to
 * the requested sort string.
 *
 * @author   apazzolini
 * @version  $Revision$, $Date$
 */
@Component("blAddSortLinkProcessor")
public class AddSortLinkProcessor extends AbstractAttributeModifierAttrProcessor {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected boolean allowMultipleSorts = false;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Sets the name of this processor to be used in Thymeleaf template.
   */
  public AddSortLinkProcessor() {
    super("addsortlink");
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

    String key       = ProductSearchCriteria.SORT_STRING;
    String sortField = element.getAttributeValue(attributeName);

    List<String[]> sortedFields = new ArrayList<String[]>();

    String[] paramValues = params.get(key);

    if ((paramValues != null) && (paramValues.length > 0)) {
      String sortQueries = paramValues[0];

      for (String sortQuery : sortQueries.split(",")) {
        String[] sort = sortQuery.split(" ");

        if (sort.length == 2) {
          sortedFields.add(new String[] { sort[0], sort[1] });
        }
      }
    }

    boolean currentlySortingOnThisField   = false;
    boolean currentlyAscendingOnThisField = false;

    for (String[] sortedField : sortedFields) {
      if (sortField.equals(sortedField[0])) {
        currentlySortingOnThisField   = true;
        currentlyAscendingOnThisField = sortedField[1].equals("asc");
        sortedField[1]                = currentlyAscendingOnThisField ? "desc" : "asc";
      }
    }

    String sortString  = sortField;
    String classString = "";

    if (currentlySortingOnThisField) {
      classString += "active ";

      if (currentlyAscendingOnThisField) {
        sortString  += " desc";
        classString += "asc ";
      } else {
        sortString  += " asc";
        classString += "desc ";
      }
    } else {
      sortString  += " asc";
      classString += "asc ";
      params.remove(ProductSearchCriteria.PAGE_NUMBER);
    }

    if (allowMultipleSorts) {
      StringBuilder sortSb = new StringBuilder();

      for (String[] sortedField : sortedFields) {
        sortSb.append(sortedField[0]).append(" ").append(sortedField[1]).append(",");
      }

      sortString = sortSb.toString();

      if (sortString.charAt(sortString.length() - 1) == ',') {
        sortString = sortString.substring(0, sortString.length() - 1);
      }
    }

    params.put(key, new String[] { sortString });

    String url = ProcessorUtils.getUrl(baseUrl, params);

    attrs.put("class", classString);
    attrs.put("href", url);

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
} // end class AddSortLinkProcessor
