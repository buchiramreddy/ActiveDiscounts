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

package org.broadleafcommerce.common.locale.dao;

import java.util.List;

import org.broadleafcommerce.common.locale.domain.Locale;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface LocaleDao {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * Returns all supported BLC locales.
   *
   * @return  all supported BLC locales.
   */
  List<Locale> findAllLocales();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Returns the page template with the passed in id.
   *
   * @return  The default locale
   */
  Locale findDefaultLocale();

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The locale for the passed in code.
   *
   * @param   localeCode  DOCUMENT ME!
   *
   * @return  The locale for the passed in code.
   */
  Locale findLocaleByCode(String localeCode);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   locale  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  Locale save(Locale locale);

} // end interface LocaleDao
