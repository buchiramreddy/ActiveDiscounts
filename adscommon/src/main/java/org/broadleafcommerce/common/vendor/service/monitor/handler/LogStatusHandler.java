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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.vendor.service.monitor.StatusHandler;
import org.broadleafcommerce.common.vendor.service.type.ServiceStatusType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class LogStatusHandler implements StatusHandler {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(LogStatusHandler.class);

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.vendor.service.monitor.StatusHandler#handleStatus(java.lang.String, org.broadleafcommerce.common.vendor.service.type.ServiceStatusType)
   */
  @Override public void handleStatus(String serviceName, ServiceStatusType status) {
    if (status.equals(ServiceStatusType.DOWN)) {
      LOG.error(serviceName + " is reporting a status of DOWN");
    } else if (status.equals(ServiceStatusType.PAUSED)) {
      LOG.warn(serviceName + " is reporting a status of PAUSED");
    } else {
      LOG.info(serviceName + " is reporting a status of UP");
    }
  }

}
