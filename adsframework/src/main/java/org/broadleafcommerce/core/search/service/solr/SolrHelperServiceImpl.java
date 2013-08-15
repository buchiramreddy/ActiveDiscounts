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

package org.broadleafcommerce.core.search.service.solr;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.request.CoreAdminRequest;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.CoreAdminParams.CoreAdminAction;

import org.broadleafcommerce.common.exception.ServiceException;
import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.locale.service.LocaleService;
import org.broadleafcommerce.common.web.BroadleafRequestContext;

import org.broadleafcommerce.core.catalog.domain.Category;
import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.search.domain.Field;
import org.broadleafcommerce.core.search.domain.solr.FieldType;

import org.springframework.stereotype.Service;


/**
 * Provides utility methods that are used by other Solr service classes.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
@Service("blSolrHelperService")
public class SolrHelperServiceImpl implements SolrHelperService {
  private static final Log LOG = LogFactory.getLog(SolrHelperServiceImpl.class);

  // The value of these two fields has no special significance, but they must be non-blank
  /** DOCUMENT ME! */
  protected static final String GLOBAL_FACET_TAG_FIELD = "a";

  /** DOCUMENT ME! */
  protected static final String DEFAULT_NAMESPACE = "d";

  /** DOCUMENT ME! */
  protected static final String PREFIX_SEPARATOR = "_";

  /** DOCUMENT ME! */
  protected static Locale     defaultLocale;

  /** DOCUMENT ME! */
  protected static SolrServer server;

  /** DOCUMENT ME! */
  @Resource(name = "blLocaleService")
  protected LocaleService localeService;

  /** DOCUMENT ME! */
  @Resource(name = "blSolrSearchServiceExtensionManager")
  protected SolrSearchServiceExtensionManager extensionManager;

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#swapActiveCores()
   */
  @Override public void swapActiveCores() throws ServiceException {
    if (SolrContext.isSingleCoreMode()) {
      if (LOG.isDebugEnabled()) {
        LOG.debug("In single core mode. There are no cores to swap.");
      }
    } else {
      if (LOG.isDebugEnabled()) {
        LOG.debug("Swapping active cores");
      }

      CoreAdminRequest car = new CoreAdminRequest();
      car.setCoreName(SolrContext.PRIMARY);
      car.setOtherCoreName(SolrContext.REINDEX);
      car.setAction(CoreAdminAction.SWAP);

      try {
        SolrContext.getServer().request(car);
        SolrContext.getServer().commit();
      } catch (Exception e) {
        LOG.error(e);
        throw new ServiceException("Unable to swap cores", e);
      }
    }
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getCurrentNamespace()
   */
  @Override public String getCurrentNamespace() {
    return DEFAULT_NAMESPACE;
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getGlobalFacetTagField()
   */
  @Override public String getGlobalFacetTagField() {
    return GLOBAL_FACET_TAG_FIELD;
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getPropertyNameForFieldSearchable(org.broadleafcommerce.core.search.domain.Field,
   *       org.broadleafcommerce.core.search.domain.solr.FieldType, java.lang.String)
   */
  @Override public String getPropertyNameForFieldSearchable(Field field, FieldType searchableFieldType, String prefix) {
    return new StringBuilder().append(prefix).append(field.getAbbreviation()).append("_").append(
        searchableFieldType.getType()).toString();
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getPropertyNameForFieldFacet(org.broadleafcommerce.core.search.domain.Field,
   *       java.lang.String)
   */
  @Override public String getPropertyNameForFieldFacet(Field field, String prefix) {
    if (field.getFacetFieldType() == null) {
      return null;
    }

    return new StringBuilder().append(prefix).append(field.getAbbreviation()).append("_").append(
        field.getFacetFieldType().getType()).toString();
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getSearchableFieldTypes(org.broadleafcommerce.core.search.domain.Field)
   */
  @Override public List<FieldType> getSearchableFieldTypes(Field field) {
    // We will index all configured searchable field types
    List<FieldType> typesToConsider = new ArrayList<FieldType>();

    if (CollectionUtils.isNotEmpty(field.getSearchableFieldTypes())) {
      typesToConsider.addAll(field.getSearchableFieldTypes());
    }

    // If there were no searchable field types configured, we will use TEXT as a default one
    if (CollectionUtils.isEmpty(typesToConsider)) {
      typesToConsider.add(FieldType.TEXT);
    }

    return typesToConsider;
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getPropertyNameForFieldSearchable(org.broadleafcommerce.core.search.domain.Field,
   *       org.broadleafcommerce.core.search.domain.solr.FieldType)
   */
  @Override public String getPropertyNameForFieldSearchable(Field field, FieldType searchableFieldType) {
    List<String> prefixList = new ArrayList<String>();
    extensionManager.getProxy().buildPrefixListForSearchableField(field, searchableFieldType, prefixList);

    String prefix = convertPrefixListToString(prefixList);

    return getPropertyNameForFieldSearchable(field, searchableFieldType, prefix);
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getPropertyNameForFieldFacet(org.broadleafcommerce.core.search.domain.Field)
   */
  @Override public String getPropertyNameForFieldFacet(Field field) {
    FieldType fieldType = field.getFacetFieldType();

    if (fieldType == null) {
      return null;
    }

    List<String> prefixList = new ArrayList<String>();

    extensionManager.getProxy().buildPrefixListForSearchableFacet(field, prefixList);

    String prefix = convertPrefixListToString(prefixList);

    return getPropertyNameForFieldFacet(field, prefix);
  }

  /**
   * DOCUMENT ME!
   *
   * @param   prefixList  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  protected String convertPrefixListToString(List<String> prefixList) {
    StringBuilder prefixString = new StringBuilder();

    for (String prefix : prefixList) {
      if ((prefix != null) && !prefix.isEmpty()) {
        prefixString = prefixString.append(prefix).append(PREFIX_SEPARATOR);
      }
    }

    return prefixString.toString();
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getSolrDocumentId(org.apache.solr.common.SolrInputDocument,
   *       org.broadleafcommerce.core.catalog.domain.Product)
   */
  @Override public String getSolrDocumentId(SolrInputDocument document, Product product) {
    return String.valueOf(product.getId());
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getNamespaceFieldName()
   */
  @Override public String getNamespaceFieldName() {
    return "namespace";
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getIdFieldName()
   */
  @Override public String getIdFieldName() {
    return "id";
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getProductIdFieldName()
   */
  @Override public String getProductIdFieldName() {
    return "productId";
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getCategoryFieldName()
   */
  @Override public String getCategoryFieldName() {
    return "category";
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getExplicitCategoryFieldName()
   */
  @Override public String getExplicitCategoryFieldName() {
    return "explicitCategory";
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getCategorySortFieldName(org.broadleafcommerce.core.catalog.domain.Category)
   */
  @Override public String getCategorySortFieldName(Category category) {
    return new StringBuilder().append(getCategoryFieldName()).append("_").append(category.getId()).append("_").append(
        "sort_i").toString();
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getLocalePrefix()
   */
  @Override public String getLocalePrefix() {
    if (BroadleafRequestContext.getBroadleafRequestContext() != null) {
      Locale locale = BroadleafRequestContext.getBroadleafRequestContext().getLocale();

      if (locale != null) {
        return locale.getLocaleCode() + "_";
      }
    }

    return getDefaultLocalePrefix();
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getDefaultLocalePrefix()
   */
  @Override public String getDefaultLocalePrefix() {
    return getDefaultLocale().getLocaleCode() + "_";
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrHelperService#getDefaultLocale()
   */
  @Override public Locale getDefaultLocale() {
    if (defaultLocale == null) {
      defaultLocale = localeService.findDefaultLocale();
    }

    return defaultLocale;
  }

} // end class SolrHelperServiceImpl
