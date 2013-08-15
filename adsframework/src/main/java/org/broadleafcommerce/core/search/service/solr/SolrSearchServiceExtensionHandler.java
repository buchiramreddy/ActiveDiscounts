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

package org.broadleafcommerce.core.search.service.solr;

import java.lang.reflect.InvocationTargetException;

import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.common.SolrInputDocument;

import org.broadleafcommerce.common.locale.domain.Locale;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.extension.ExtensionHandler;
import org.broadleafcommerce.core.extension.ExtensionResultStatusType;
import org.broadleafcommerce.core.search.domain.Field;
import org.broadleafcommerce.core.search.domain.ProductSearchCriteria;
import org.broadleafcommerce.core.search.domain.SearchFacetDTO;
import org.broadleafcommerce.core.search.domain.SearchFacetRange;
import org.broadleafcommerce.core.search.domain.solr.FieldType;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini), bpolster
 * @version  $Revision$, $Date$
 */
public interface SolrSearchServiceExtensionHandler extends ExtensionHandler {
  /**
   * Returns a prefix if required for the passed in facet.
   *
   * @param   field       DOCUMENT ME!
   * @param   prefixList  DOCUMENT ME!
   *
   * @return  a prefix if required for the passed in facet.
   */
  ExtensionResultStatusType buildPrefixListForSearchableFacet(Field field, List<String> prefixList);

  /**
   * Returns a prefix if required for the passed in searchable field.
   *
   * @param   field                DOCUMENT ME!
   * @param   searchableFieldType  DOCUMENT ME!
   * @param   prefixList           DOCUMENT ME!
   *
   * @return  a prefix if required for the passed in searchable field.
   */
  ExtensionResultStatusType buildPrefixListForSearchableField(Field field, FieldType searchableFieldType,
    List<String> prefixList);

  /**
   * Builds the search facet ranges for the provided dto.
   *
   * @param   dto     DOCUMENT ME!
   * @param   ranges  DOCUMENT ME!
   *
   * @return  builds the search facet ranges for the provided dto.
   */
  ExtensionResultStatusType filterSearchFacetRanges(SearchFacetDTO dto, List<SearchFacetRange> ranges);

  /**
   * Given the input field, populates the values array with the fields needed for the passed in field.
   *
   * <p>For example, a handler might create multiple fields for the given passed in field.</p>
   *
   * @param   product       DOCUMENT ME!
   * @param   field         DOCUMENT ME!
   * @param   fieldType     DOCUMENT ME!
   * @param   values        DOCUMENT ME!
   * @param   propertyName  DOCUMENT ME!
   * @param   locales       DOCUMENT ME!
   *
   * @return  given the input field, populates the values array with the fields needed for the passed in field.
   *
   * @throws  IllegalAccessException
   * @throws  java.lang.reflect.InvocationTargetException
   * @throws  NoSuchMethodException
   */
  ExtensionResultStatusType addPropertyValues(Product product, Field field, FieldType fieldType,
    Map<String, Object> values, String propertyName, List<Locale> locales) throws IllegalAccessException,
    InvocationTargetException, NoSuchMethodException;

  /**
   * Provides an extension point to modify the SolrQuery.
   *
   * @param   query               DOCUMENT ME!
   * @param   qualifiedSolrQuery  DOCUMENT ME!
   * @param   facets              DOCUMENT ME!
   * @param   searchCriteria      DOCUMENT ME!
   * @param   defaultSort         DOCUMENT ME!*
   *
   * @return  provides an extension point to modify the SolrQuery.
   */
  ExtensionResultStatusType modifySolrQuery(SolrQuery query, String qualifiedSolrQuery, List<SearchFacetDTO> facets,
    ProductSearchCriteria searchCriteria, String defaultSort);

  /**
   * Allows the extension additional fields to the document that are not configured via the DB.
   *
   * @param   product   DOCUMENT ME!
   * @param   document  DOCUMENT ME!
   * @param   shs       DOCUMENT ME!
   *
   * @return  allows the extension additional fields to the document that are not configured via the DB.
   */
  ExtensionResultStatusType attachAdditionalBasicFields(Product product, SolrInputDocument document,
    SolrHelperService shs);
} // end interface SolrSearchServiceExtensionHandler
