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

package org.broadleafcommerce.profile.core.service;

import javax.annotation.Resource;

import org.broadleafcommerce.profile.core.dao.PhoneDao;
import org.broadleafcommerce.profile.core.domain.Phone;

import org.springframework.stereotype.Service;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
@Service("blPhoneService")
public class PhoneServiceImpl implements PhoneService {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @Resource(name = "blPhoneDao")
  protected PhoneDao phoneDao;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.PhoneService#create()
   */
  @Override public Phone create() {
    return phoneDao.create();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.PhoneService#readPhoneById(java.lang.Long)
   */
  @Override public Phone readPhoneById(Long phoneId) {
    return phoneDao.readPhoneById(phoneId);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.profile.core.service.PhoneService#savePhone(org.broadleafcommerce.profile.core.domain.Phone)
   */
  @Override public Phone savePhone(Phone phone) {
    return phoneDao.save(phone);
  }
} // end class PhoneServiceImpl
