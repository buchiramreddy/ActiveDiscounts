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

package org.broadleafcommerce.common.email.service;

import java.util.HashMap;

import org.broadleafcommerce.common.email.domain.EmailTarget;
import org.broadleafcommerce.common.email.service.info.EmailInfo;


/**
 * This null implementation class can be used during development to work around the need to have a working SMTP service
 * to route emails through.
 *
 * @author   aazzolini
 * @version  $Revision$, $Date$
 */
public class NullEmailServiceImpl implements EmailService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.email.service.EmailService#sendBasicEmail(org.broadleafcommerce.common.email.service.info.EmailInfo,
   *       org.broadleafcommerce.common.email.domain.EmailTarget, java.util.HashMap)
   */
  @Override public boolean sendBasicEmail(EmailInfo emailInfo, EmailTarget emailTarget, HashMap<String, Object> props) {
    return true;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.email.service.EmailService#sendTemplateEmail(java.lang.String,org.broadleafcommerce.common.email.service.info.EmailInfo,
   *       java.util.HashMap)
   */
  @Override public boolean sendTemplateEmail(String emailAddress, EmailInfo emailInfo, HashMap<String, Object> props) {
    return true;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.email.service.EmailService#sendTemplateEmail(org.broadleafcommerce.common.email.domain.EmailTarget,
   *       org.broadleafcommerce.common.email.service.info.EmailInfo, java.util.HashMap)
   */
  @Override public boolean sendTemplateEmail(EmailTarget emailTarget, EmailInfo emailInfo,
    HashMap<String, Object> props) {
    return true;
  }

} // end class NullEmailServiceImpl
