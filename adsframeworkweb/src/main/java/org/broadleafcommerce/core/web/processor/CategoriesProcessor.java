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
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import org.broadleafcommerce.common.web.dialect.AbstractModelVariableModifierProcessor;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.CategoryXref;
import org.broadleafcommerce.core.catalog.service.CatalogService;

import org.springframework.stereotype.Component;

import org.thymeleaf.Arguments;

import org.thymeleaf.dom.Element;


/**
 * A Thymeleaf processor that will add the desired categories to the model. It does this by searching for the
 * parentCategory name and adding up to maxResults subcategories under the model attribute specified by resultVar
 *
 * @author   apazzolini
 * @version  $Revision$, $Date$
 */
@Component("blCategoriesProcessor")
public class CategoriesProcessor extends AbstractModelVariableModifierProcessor {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blCatalogService")
  protected CatalogService catalogService;

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Sets the name of this processor to be used in Thymeleaf template.
   */
  public CategoriesProcessor() {
    super("categories");
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
   * @see  org.broadleafcommerce.common.web.dialect.AbstractModelVariableModifierProcessor#modifyModelAttributes(org.thymeleaf.Arguments,
   *       org.thymeleaf.dom.Element)
   */
  @Override protected void modifyModelAttributes(Arguments arguments, Element element) {
    String resultVar          = element.getAttributeValue("resultVar");
    String parentCategory     = element.getAttributeValue("parentCategory");
    String unparsedMaxResults = element.getAttributeValue("maxResults");

    // TODO: Potentially write an algorithm that will pick the minimum depth category
    // instead of the first category in the list
    List<Category> categories = catalogService.findCategoriesByName(parentCategory);

    if ((categories != null) && (categories.size() > 0)) {
      // gets child categories in order ONLY if they are in the xref table and active
      List<CategoryXref> subcategories = categories.get(0).getChildCategoryXrefs();

      if ((subcategories != null) && !subcategories.isEmpty()) {
        if (StringUtils.isNotEmpty(unparsedMaxResults)) {
          int maxResults = Integer.parseInt(unparsedMaxResults);

          if (subcategories.size() > maxResults) {
            subcategories = subcategories.subList(0, maxResults);
          }
        }
      }

      List<Category> results = new ArrayList<Category>(subcategories.size());

      for (CategoryXref xref : subcategories) {
        results.add(xref.getSubCategory());
      }

      addToModel(arguments, resultVar, results);
    }
  } // end method modifyModelAttributes
} // end class CategoriesProcessor
