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

package org.broadleafcommerce.core.web.api.wrapper;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.ApplicationContext;


/**
 * This interface is the super interface for all classes that will provide a JAXB unwrapper around classes. Any class
 * that will be exposed via JAXB annotations to the JAXRS API may implement this as a convenience to provide a standard
 * method to unwrap data objects.
 *
 * <p>This is not a requirement as objects will not generally be passed using a reference to this interface.</p>
 *
 * @param    <T>
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public interface APIUnwrapper<T> {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   request  DOCUMENT ME!
   * @param   context  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  T unwrap(HttpServletRequest request, ApplicationContext context);
}
