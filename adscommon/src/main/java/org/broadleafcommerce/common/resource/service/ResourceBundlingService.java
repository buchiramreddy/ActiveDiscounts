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

package org.broadleafcommerce.common.resource.service;

import java.io.IOException;

import java.util.List;

import org.broadleafcommerce.common.web.resource.BroadleafResourceHttpRequestHandler;

import org.springframework.core.io.Resource;


/**
 * This service is responsible for interaction with the
 * {@link org.broadleafcommerce.common.web.processor.ResourceBundleProcessor} to generate versioned names for bundles as
 * well as the bundle content.
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public interface ResourceBundlingService {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * A list of additional files that are registered for the given bundle name.
   *
   * @param   bundleName  DOCUMENT ME!
   *
   * @return  a list of additional files that are registered for the given bundle name
   */
  List<String> getAdditionalBundleFiles(String bundleName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * For the given versioned bundle name, returns a Resource that holds the contents of the combined, and possibly
   * minified (if enabled) bundle.
   *
   * @param   versionedBundleName  DOCUMENT ME!
   *
   * @return  the Resource
   */
  Resource getBundle(String versionedBundleName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * For a given unversioned bundle name, such as "global.js", returns the currently known versioned bundle name, such
   * as "global12345.js".
   *
   * @param   unversionedBundleName  DOCUMENT ME!
   *
   * @return  the versioned bundle name
   */
  String getVersionedBundleName(String unversionedBundleName);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Whether or not the given versioned bundle name is currently registered in the system.
   *
   * @param   versionedBundle  DOCUMENT ME!
   *
   * @return  whether or not the given versioned bundle name is currently registered in the system
   */
  boolean hasBundle(String versionedBundle);

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * Registers a new bundle with the given name to its files. Will utilize the locations map in handler as well as any
   * configured generated resource handlers in the handler to determine legitimate paths for each of the files in the
   * list.
   *
   * @param   bundleName  DOCUMENT ME!
   * @param   files       DOCUMENT ME!
   * @param   handler     DOCUMENT ME!
   *
   * @return  the versioned bundle name
   *
   * @throws  java.io.IOException
   */
  String registerBundle(String bundleName, List<String> files, BroadleafResourceHttpRequestHandler handler)
    throws IOException;

} // end interface ResourceBundlingService
