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
import org.broadleafcommerce.core.extension.AbstractExtensionHandler;
import org.broadleafcommerce.core.extension.ExtensionResultStatusType;
import org.broadleafcommerce.core.search.domain.Field;
import org.broadleafcommerce.core.search.domain.ProductSearchCriteria;
import org.broadleafcommerce.core.search.domain.SearchFacetDTO;
import org.broadleafcommerce.core.search.domain.SearchFacetRange;
import org.broadleafcommerce.core.search.domain.solr.FieldType;


/**
 * Implementors of the SolrSearchServiceExtensionHandler interface should extend this class so that if additional
 * extension points are added which they don't care about, their code will not need to be modified.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
public abstract class AbstractSolrSearchServiceExtensionHandler extends AbstractExtensionHandler
  implements SolrSearchServiceExtensionHandler {
  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrSearchServiceExtensionHandler#buildPrefixListForSearchableFacet(org.broadleafcommerce.core.search.domain.Field,
   *       java.util.List)
   */
  @Override public ExtensionResultStatusType buildPrefixListForSearchableFacet(Field field, List<String> prefixList) {
    return ExtensionResultStatusType.NOT_HANDLED;
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrSearchServiceExtensionHandler#buildPrefixListForSearchableField(org.broadleafcommerce.core.search.domain.Field,
   *       org.broadleafcommerce.core.search.domain.solr.FieldType, java.util.List)
   */
  @Override public ExtensionResultStatusType buildPrefixListForSearchableField(Field field,
    FieldType searchableFieldType, List<String> prefixList) {
    return ExtensionResultStatusType.NOT_HANDLED;
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrSearchServiceExtensionHandler#filterSearchFacetRanges(org.broadleafcommerce.core.search.domain.SearchFacetDTO,
   *       java.util.List)
   */
  @Override public ExtensionResultStatusType filterSearchFacetRanges(SearchFacetDTO dto,
    List<SearchFacetRange> ranges) {
    return ExtensionResultStatusType.NOT_HANDLED;
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrSearchServiceExtensionHandler#addPropertyValues(org.broadleafcommerce.core.catalog.domain.Product,
   *       org.broadleafcommerce.core.search.domain.Field, org.broadleafcommerce.core.search.domain.solr.FieldType,
   *       java.util.Map, java.lang.String, java.util.List)
   */
  @Override public ExtensionResultStatusType addPropertyValues(Product product, Field field, FieldType fieldType,
    Map<String, Object> values, String propertyName, List<Locale> locales) throws IllegalAccessException,
    InvocationTargetException, NoSuchMethodException {
    return ExtensionResultStatusType.NOT_HANDLED;
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrSearchServiceExtensionHandler#modifySolrQuery(org.apache.solr.client.solrj.SolrQuery,
   *       java.lang.String, java.util.List, org.broadleafcommerce.core.search.domain.ProductSearchCriteria,
   *       java.lang.String)
   */
  @Override public ExtensionResultStatusType modifySolrQuery(SolrQuery query, String qualifiedSolrQuery,
    List<SearchFacetDTO> facets, ProductSearchCriteria searchCriteria, String defaultSort) {
    return ExtensionResultStatusType.NOT_HANDLED;
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrSearchServiceExtensionHandler#attachAdditionalBasicFields(org.broadleafcommerce.core.catalog.domain.Product,
   *       org.apache.solr.common.SolrInputDocument, org.broadleafcommerce.core.search.service.solr.SolrHelperService)
   */
  @Override public ExtensionResultStatusType attachAdditionalBasicFields(Product product, SolrInputDocument document,
    SolrHelperService shs) {
    return ExtensionResultStatusType.NOT_HANDLED;
  }

} // end class AbstractSolrSearchServiceExtensionHandler
