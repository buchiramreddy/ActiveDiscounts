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

package org.broadleafcommerce.common.web.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.broadleafcommerce.common.resource.service.ResourceBundlingService;

import org.springframework.core.io.Resource;

import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class BroadleafResourceHttpRequestHandler extends ResourceHttpRequestHandler {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final Log LOG = LogFactory.getLog(BroadleafResourceHttpRequestHandler.class);

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  @javax.annotation.Resource(name = "blResourceBundlingService")
  protected ResourceBundlingService bundlingService;

  // XML Configured generated resource handlers
  /** DOCUMENT ME! */
  protected List<AbstractGeneratedResourceHandler> handlers;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /* *********** */
  /* BOILERPLATE */
  /* *********** */

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public List<AbstractGeneratedResourceHandler> getHandlers() {
    return handlers;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * A clone of the locations list that is in
   * {@link org.springframework.web.servlet.resource.ResourceHttpRequestHandler}. Note that we must use reflection to
   * access this field as it is marked private.
   *
   * @return  a clone of the locations list that is in
   *          {@link org.springframework.web.servlet.resource.ResourceHttpRequestHandler}. Note that we must use
   *          reflection to access this field as it is marked private.
   *
   * @throws  RuntimeException  DOCUMENT ME!
   */
  @SuppressWarnings("unchecked")
  public List<Resource> getLocations() {
    try {
      List<Resource> locations = (List<Resource>) FieldUtils.readField(this, "locations", true);

      return new ArrayList<Resource>(locations);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isBundleRequest(HttpServletRequest request) {
    String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

    return bundlingService.hasBundle(path);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  handlers  DOCUMENT ME!
   */
  public void setHandlers(List<AbstractGeneratedResourceHandler> handlers) {
    this.handlers = handlers;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Checks to see if the requested path corresponds to a registered bundle. If so, returns the generated bundle.
   * Otherwise, checks to see if any of the configured GeneratedResourceHandlers can handle the given request. If
   * neither of those cases match, delegates to the normal ResourceHttpRequestHandler
   *
   * @param   request  DOCUMENT ME!
   *
   * @return  checks to see if the requested path corresponds to a registered bundle.
   */
  @Override protected Resource getResource(HttpServletRequest request) {
    String path = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);

    if (bundlingService.hasBundle(path)) {
      return bundlingService.getBundle(path);
    }

    if (handlers != null) {
      for (AbstractGeneratedResourceHandler handler : handlers) {
        if (handler.canHandle(path)) {
          return handler.getResource(path, getLocations());
        }
      }
    }

    return super.getResource(request);
  }

} // end class BroadleafResourceHttpRequestHandler
