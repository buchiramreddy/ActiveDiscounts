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

package org.broadleafcommerce.common.config;

/**
 * Determines the current runtime environment by reading a system property (specified in environmentKey); if no system
 * property is specified, a (reasonable) default of "runtime.environment" is used.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class SystemPropertyRuntimeEnvironmentKeyResolver implements RuntimeEnvironmentKeyResolver {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected String environmentKey = "runtime.environment";

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new SystemPropertyRuntimeEnvironmentKeyResolver object.
   */
  public SystemPropertyRuntimeEnvironmentKeyResolver() {
    // EMPTY
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.config.RuntimeEnvironmentKeyResolver#resolveRuntimeEnvironmentKey()
   */
  @Override public String resolveRuntimeEnvironmentKey() {
    return System.getProperty(environmentKey);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  environmentKey  DOCUMENT ME!
   */
  public void setEnvironmentKey(String environmentKey) {
    this.environmentKey = environmentKey;
  }
} // end class SystemPropertyRuntimeEnvironmentKeyResolver
