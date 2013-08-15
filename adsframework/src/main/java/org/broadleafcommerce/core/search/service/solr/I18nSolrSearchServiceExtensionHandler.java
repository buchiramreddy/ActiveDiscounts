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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.beanutils.PropertyUtils;

import org.broadleafcommerce.common.i18n.service.TranslationConsiderationContext;
import org.broadleafcommerce.common.i18n.service.TranslationService;
import org.broadleafcommerce.common.locale.domain.Locale;
import org.broadleafcommerce.common.web.BroadleafRequestContext;

import org.broadleafcommerce.core.catalog.domain.Product;
import org.broadleafcommerce.core.extension.ExtensionResultStatusType;
import org.broadleafcommerce.core.search.domain.Field;
import org.broadleafcommerce.core.search.domain.solr.FieldType;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;


/**
 * If the field is translatable, then this method prefixes the field with supported locales.
 *
 * @author   bpolster
 * @version  $Revision$, $Date$
 */
@Service("blI18nSolrSearchServiceExtensionHandler")
public class I18nSolrSearchServiceExtensionHandler extends AbstractSolrSearchServiceExtensionHandler
  implements SolrSearchServiceExtensionHandler {
  /** DOCUMENT ME! */
  @Resource(name = "blSolrHelperService")
  protected SolrHelperService shs;

  /** DOCUMENT ME! */
  @Resource(name = "blSolrSearchServiceExtensionManager")
  protected SolrSearchServiceExtensionManager extensionManager;

  /** DOCUMENT ME! */
  @Resource(name = "blTranslationService")
  protected TranslationService translationService;

  /** DOCUMENT ME! */
  @Value("${i18n.translation.enabled}")
  protected boolean translationEnabled = false;

  private static String ATTR_MAP = SolrIndexServiceImpl.ATTR_MAP;

  /**
   * DOCUMENT ME!
   */
  @PostConstruct public void init() {
    extensionManager.getHandlers().add(this);
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrSearchServiceExtensionHandler#buildPrefixListForSearchableFacet(org.broadleafcommerce.core.search.domain.Field,
   *       java.util.List)
   */
  @Override public ExtensionResultStatusType buildPrefixListForSearchableFacet(Field field, List<String> prefixList) {
    return getLocalePrefix(field, prefixList);
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.SolrSearchServiceExtensionHandler#buildPrefixListForSearchableField(org.broadleafcommerce.core.search.domain.Field,
   *       org.broadleafcommerce.core.search.domain.solr.FieldType, java.util.List)
   */
  @Override public ExtensionResultStatusType buildPrefixListForSearchableField(Field field,
    FieldType searchableFieldType, List<String> prefixList) {
    return getLocalePrefix(field, prefixList);
  }

  /**
   * @see  org.broadleafcommerce.core.search.service.solr.AbstractSolrSearchServiceExtensionHandler#addPropertyValues(org.broadleafcommerce.core.catalog.domain.Product,
   *       org.broadleafcommerce.core.search.domain.Field, org.broadleafcommerce.core.search.domain.solr.FieldType,
   *       java.util.Map, java.lang.String, java.util.List)
   */
  @Override public ExtensionResultStatusType addPropertyValues(Product product, Field field, FieldType fieldType,
    Map<String, Object> values, String propertyName, List<Locale> locales) throws NoSuchMethodException,
    InvocationTargetException, IllegalAccessException {
    ExtensionResultStatusType result = ExtensionResultStatusType.NOT_HANDLED;

    if (field.getTranslatable()) {
      result = ExtensionResultStatusType.HANDLED;

      for (Locale locale : locales) {
        String localeCode = locale.getLocaleCode();
        TranslationConsiderationContext.setTranslationConsiderationContext(translationEnabled);
        TranslationConsiderationContext.setTranslationService(translationService);

        BroadleafRequestContext tempContext = BroadleafRequestContext.getBroadleafRequestContext();

        if (tempContext == null) {
          tempContext = new BroadleafRequestContext();
        }

        tempContext.setLocale(locale);
        BroadleafRequestContext.setBroadleafRequestContext(tempContext);

        final Object propertyValue;

        if (propertyName.contains(ATTR_MAP)) {
          propertyValue = PropertyUtils.getMappedProperty(product, ATTR_MAP,
              propertyName.substring(ATTR_MAP.length() + 1));
        } else {
          propertyValue = PropertyUtils.getProperty(product, propertyName);
        }

        values.put(localeCode, propertyValue);
      } // end for
    } // end if

    return result;
  } // end method addPropertyValues

  /**
   * If the field is translatable, take the current locale and add that as a prefix.
   *
   * @param   field       DOCUMENT ME!
   * @param   prefixList  context
   *
   * @return  if the field is translatable, take the current locale and add that as a prefix.
   */
  protected ExtensionResultStatusType getLocalePrefix(Field field, List<String> prefixList) {
    if (field.getTranslatable()) {
      if (BroadleafRequestContext.getBroadleafRequestContext() != null) {
        Locale locale = BroadleafRequestContext.getBroadleafRequestContext().getLocale();

        if (locale != null) {
          prefixList.add(locale.getLocaleCode());

          return ExtensionResultStatusType.HANDLED_CONTINUE;
        }
      }
    }

    return ExtensionResultStatusType.NOT_HANDLED;
  }

  /**
   * @see  org.broadleafcommerce.core.extension.AbstractExtensionHandler#getPriority()
   */
  @Override public int getPriority() {
    return 1000;
  }
} // end class I18nSolrSearchServiceExtensionHandler
