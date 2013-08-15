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

package org.broadleafcommerce.common.vendor.service.monitor;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.aspectj.lang.ProceedingJoinPoint;

import org.broadleafcommerce.common.vendor.service.monitor.handler.LogStatusHandler;
import org.broadleafcommerce.common.vendor.service.type.ServiceStatusType;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class ServiceMonitor {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(ServiceMonitor.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected StatusHandler defaultHandler = new LogStatusHandler();

  /** DOCUMENT ME! */
  protected Map<ServiceStatusDetectable, StatusHandler>     serviceHandlers =
    new HashMap<ServiceStatusDetectable, StatusHandler>();

  /** DOCUMENT ME! */
  protected Map<ServiceStatusDetectable, ServiceStatusType> statusMap =
    new HashMap<ServiceStatusDetectable, ServiceStatusType>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  statusDetectable  DOCUMENT ME!
   */
  public void checkService(ServiceStatusDetectable statusDetectable) {
    ServiceStatusType type = statusDetectable.getServiceStatus();

    if (!statusMap.containsKey(statusDetectable)) {
      statusMap.put(statusDetectable, type);

      if (type.equals(ServiceStatusType.DOWN)) {
        handleStatusChange(statusDetectable, type);
      }
    }

    if (!statusMap.get(statusDetectable).equals(type)) {
      handleStatusChange(statusDetectable, type);
      statusMap.put(statusDetectable, type);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   call  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   *
   * @throws  Throwable  DOCUMENT ME!
   */
  public Object checkServiceAOP(ProceedingJoinPoint call) throws Throwable {
    try {
      checkService((ServiceStatusDetectable) call.getThis());
    } catch (Throwable e) {
      LOG.error("Could not check service status", e);
    }

    return call.proceed();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public StatusHandler getDefaultHandler() {
    return defaultHandler;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<ServiceStatusDetectable, StatusHandler> getServiceHandlers() {
    return serviceHandlers;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   */
  public synchronized void init() {
    for (ServiceStatusDetectable statusDetectable : serviceHandlers.keySet()) {
      checkService(statusDetectable);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  defaultHandler  DOCUMENT ME!
   */
  public void setDefaultHandler(StatusHandler defaultHandler) {
    this.defaultHandler = defaultHandler;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  serviceHandlers  DOCUMENT ME!
   */
  public void setServiceHandlers(Map<ServiceStatusDetectable, StatusHandler> serviceHandlers) {
    this.serviceHandlers = serviceHandlers;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  serviceStatus      DOCUMENT ME!
   * @param  serviceStatusType  DOCUMENT ME!
   */
  protected void handleStatusChange(ServiceStatusDetectable serviceStatus, ServiceStatusType serviceStatusType) {
    if (serviceHandlers.containsKey(serviceStatus)) {
      serviceHandlers.get(serviceStatus).handleStatus(serviceStatus.getServiceName(), serviceStatusType);
    } else {
      defaultHandler.handleStatus(serviceStatus.getServiceName(), serviceStatusType);
    }
  }
} // end class ServiceMonitor
