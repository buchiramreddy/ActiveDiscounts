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

package org.broadleafcommerce.common.vendor.service.monitor.handler;

import javax.annotation.Resource;

import org.broadleafcommerce.common.email.domain.EmailTarget;
import org.broadleafcommerce.common.email.service.EmailService;
import org.broadleafcommerce.common.email.service.info.EmailInfo;
import org.broadleafcommerce.common.vendor.service.monitor.StatusHandler;
import org.broadleafcommerce.common.vendor.service.type.ServiceStatusType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class EmailStatusHandler implements StatusHandler {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected EmailInfo emailInfo;

  /** DOCUMENT ME! */
  @Resource(name = "blEmailService")
  protected EmailService emailService;

  /** DOCUMENT ME! */
  protected EmailTarget  emailTarget;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EmailInfo getEmailInfo() {
    return emailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public EmailTarget getEmailTarget() {
    return emailTarget;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.vendor.service.monitor.StatusHandler#handleStatus(java.lang.String, org.broadleafcommerce.common.vendor.service.type.ServiceStatusType)
   */
  @Override public void handleStatus(String serviceName, ServiceStatusType status) {
    String    message = serviceName + " is reporting a status of " + status.getType();
    EmailInfo copy    = emailInfo.clone();
    copy.setMessageBody(message);
    copy.setSubject(message);
    emailService.sendBasicEmail(copy, emailTarget, null);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  emailInfo  DOCUMENT ME!
   */
  public void setEmailInfo(EmailInfo emailInfo) {
    this.emailInfo = emailInfo;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  emailTarget  DOCUMENT ME!
   */
  public void setEmailTarget(EmailTarget emailTarget) {
    this.emailTarget = emailTarget;
  }

} // end class EmailStatusHandler
