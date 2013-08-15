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

package org.broadleafcommerce.common.i18n.dao;

import java.util.List;
import java.util.Map;

import org.broadleafcommerce.common.i18n.domain.TranslatedEntity;
import org.broadleafcommerce.common.i18n.domain.Translation;


/**
 * Provides data access for the {@link org.broadleafcommerce.common.i18n.domain.Translation} entity.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public interface TranslationDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Creates an empty translation instance that is not persisted to the database.
   *
   * @return  the unsaved, empty translation
   */
  Translation create();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Deletes the given translation.
   *
   * @param  translation  DOCUMENT ME!
   */
  void delete(Translation translation);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns a map that holds the following data for the given entity: "name" --> idProperty (the name of the id
   * property, always a String) "type" --> idProperty's type (usually either Long or String)
   *
   * @param   entity  DOCUMENT ME!
   *
   * @return  the id property's metadata
   */
  Map<String, Object> getIdPropertyMetadata(TranslatedEntity entity);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Reads a translation for the requested parameters. Returns null if there is no translation found
   *
   * @param   entity      DOCUMENT ME!
   * @param   entityId    DOCUMENT ME!
   * @param   fieldName   DOCUMENT ME!
   * @param   localeCode  DOCUMENT ME!
   *
   * @return  the translation
   */
  Translation readTranslation(TranslatedEntity entity, String entityId, String fieldName, String localeCode);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Reads a translation by its own primary key.
   *
   * @param   translationId  DOCUMENT ME!
   *
   * @return  the translation
   */
  Translation readTranslationById(Long translationId);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Reads all translations for a given field.
   *
   * @param   entity     DOCUMENT ME!
   * @param   entityId   DOCUMENT ME!
   * @param   fieldName  DOCUMENT ME!
   *
   * @return  the list of translations
   */
  List<Translation> readTranslations(TranslatedEntity entity, String entityId, String fieldName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Persists the given translation.
   *
   * @param   translation  DOCUMENT ME!
   *
   * @return  the saved translation
   */
  Translation save(Translation translation);

} // end interface TranslationDao
