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

package org.broadleafcommerce.openadmin.web.service;

import java.util.List;

import org.broadleafcommerce.common.i18n.domain.Translation;

import org.broadleafcommerce.openadmin.web.form.TranslationForm;
import org.broadleafcommerce.openadmin.web.form.component.ListGrid;
import org.broadleafcommerce.openadmin.web.form.entity.EntityForm;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface TranslationFormBuilderService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Builds a ListGrid for the given list of translations.
   *
   * @param   translations  DOCUMENT ME!
   * @param   isRte         - whether or not the field that this translation is tied to is a rich text edit field
   *
   * @return  the list grid
   */
  ListGrid buildListGrid(List<Translation> translations, boolean isRte);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Builds an EntityForm used to create or edit a translation value.
   *
   * @param   formProperties  DOCUMENT ME!
   *
   * @return  the entity form
   */
  EntityForm buildTranslationForm(TranslationForm formProperties);


} // end interface TranslationFormBuilderService
