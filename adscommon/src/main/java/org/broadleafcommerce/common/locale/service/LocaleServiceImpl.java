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

package org.broadleafcommerce.common.locale.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.locale.dao.LocaleDao;
import org.broadleafcommerce.common.locale.domain.Locale;

import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;


/**
 * Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blLocaleService")
public class LocaleServiceImpl implements LocaleService {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(LocaleServiceImpl.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blLocaleDao")
  protected LocaleDao localeDao;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.service.LocaleService#findAllLocales()
   */
  @Override public List<Locale> findAllLocales() {
    return localeDao.findAllLocales();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.service.LocaleService#findDefaultLocale()
   */
  @Override public Locale findDefaultLocale() {
    return localeDao.findDefaultLocale();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.service.LocaleService#findLocaleByCode(java.lang.String)
   */
  @Override public Locale findLocaleByCode(String localeCode) {
    return localeDao.findLocaleByCode(localeCode);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.locale.service.LocaleService#save(org.broadleafcommerce.common.locale.domain.Locale)
   */
  @Override
  @Transactional("blTransactionManager")
  public Locale save(Locale locale) {
    return localeDao.save(locale);
  }

} // end class LocaleServiceImpl
