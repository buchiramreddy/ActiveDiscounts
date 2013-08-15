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

package org.broadleafcommerce.common.extensibility.config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import org.springframework.core.io.Resource;


/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class PropertyConfigurer extends PropertyPlaceholderConfigurer {
  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.core.io.support.PropertiesLoaderSupport#setLocation(org.springframework.core.io.Resource)
   */
  @Override public void setLocation(Resource location) {
    super.setLocation(location);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.springframework.core.io.support.PropertiesLoaderSupport#setLocations(org.springframework.core.io.Resource[])
   */
  @Override public void setLocations(Resource[] locations) {
    super.setLocations(locations);
  }

}
