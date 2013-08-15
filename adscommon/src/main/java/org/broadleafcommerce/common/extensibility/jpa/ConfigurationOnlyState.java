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

package org.broadleafcommerce.common.extensibility.jpa;

/**
 * DOCUMENT ME!
 *
 * @author   Jeff Fischer
 * @version  $Revision$, $Date$
 */
public class ConfigurationOnlyState {
  //~ Static fields/initializers ---------------------------------------------------------------------------------------

  private static final ThreadLocal<ConfigurationOnlyState> CONFIGURATIONONLYSTATE =
    new ThreadLocal<ConfigurationOnlyState>();

  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected boolean isConfigurationOnly;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public static ConfigurationOnlyState getState() {
    return CONFIGURATIONONLYSTATE.get();
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  state  DOCUMENT ME!
   */
  public static void setState(ConfigurationOnlyState state) {
    CONFIGURATIONONLYSTATE.set(state);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public boolean isConfigurationOnly() {
    return isConfigurationOnly;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  configurationOnly  DOCUMENT ME!
   */
  public void setConfigurationOnly(boolean configurationOnly) {
    isConfigurationOnly = configurationOnly;
  }
} // end class ConfigurationOnlyState
