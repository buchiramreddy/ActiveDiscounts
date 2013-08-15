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

package org.broadleafcommerce.common.i18n.service;

import java.util.List;
import java.util.Locale;

import org.broadleafcommerce.common.i18n.domain.TranslatedEntity;
import org.broadleafcommerce.common.i18n.domain.Translation;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface TranslationService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Deletes the given translations.
   *
   * @param  translationId  DOCUMENT ME!
   */
  void deleteTranslationById(Long translationId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the translated value of the property for the given entity. For example, if entity is an instance of Product
   * and property is equal to name, this method might return "Hoppin' Hot Sauce" if we are in an English locale and
   * "Salsa de la Muerte Saltante" if we are in a Spanish locale.
   *
   * <p>If a country is set on the locale (locale code en_GB for example), we will first look for a translation that
   * matches en_GB, and then look for a translation for en. If a translated value for the given locale is not available,
   * it will return null.</p>
   *
   * @param   entity    DOCUMENT ME!
   * @param   property  DOCUMENT ME!
   * @param   locale    DOCUMENT ME!
   *
   * @return  the translated value of the property for the given entity
   */
  String getTranslatedValue(Object entity, String property, Locale locale);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Attempts to find the translation object for the given parameters.
   *
   * @param   entity      DOCUMENT ME!
   * @param   entityId    DOCUMENT ME!
   * @param   fieldName   DOCUMENT ME!
   * @param   localeCode  DOCUMENT ME!
   *
   * @return  the persisted translation
   */
  Translation getTranslation(TranslatedEntity entity, String entityId, String fieldName, String localeCode);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Finds all current translations for the specified field.
   *
   * @param   ceilingEntityClassname  DOCUMENT ME!
   * @param   entityId                DOCUMENT ME!
   * @param   property                DOCUMENT ME!
   *
   * @return  the list of translations
   */
  List<Translation> getTranslations(String ceilingEntityClassname, String entityId, String property);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Persists the given translation.
   *
   * @param   translation  DOCUMENT ME!
   *
   * @return  the persisted translation
   */
  Translation save(Translation translation);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Creates a new translation object for the requested parameters, saves it, and returns the saved instance. <b>Note:
   * This method will overwrite a previously existing translation if it matches on entityType, entityId, fieldName, and
   * localeCode.</b>
   *
   * @param   entityType       DOCUMENT ME!
   * @param   entityId         DOCUMENT ME!
   * @param   fieldName        DOCUMENT ME!
   * @param   localeCode       DOCUMENT ME!
   * @param   translatedValue  DOCUMENT ME!
   *
   * @return  the persisted translation
   */
  Translation save(String entityType, String entityId, String fieldName, String localeCode,
    String translatedValue);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Updates the given translation id with the new locale code and translated value.
   *
   * @param   translationId    DOCUMENT ME!
   * @param   localeCode       DOCUMENT ME!
   * @param   translatedValue  DOCUMENT ME!
   *
   * @return  the persisted translation
   */
  Translation update(Long translationId, String localeCode, String translatedValue);


} // end interface TranslationService
