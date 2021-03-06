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

package org.broadleafcommerce.openadmin.web.form.entity;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author   Andre Azzolini (apazzolini)
 * @version  $Revision$, $Date$
 */
public class ComboField extends Field {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  protected Map<String, String> options = new LinkedHashMap<String, String>();

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param   optionKey  DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public String getOption(String optionKey) {
    return getOptions().get(optionKey);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ************************** */
  /* STANDARD GETTERS / SETTERS */
  /* ************************** */

  /**
   * DOCUMENT ME!
   *
   * @return  DOCUMENT ME!
   */
  public Map<String, String> getOptions() {
    return options;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* *********** */
  /* ADD METHODS */
  /* *********** */

  /**
   * DOCUMENT ME!
   *
   * @param  key    DOCUMENT ME!
   * @param  value  DOCUMENT ME!
   */
  public void putOption(String key, String value) {
    options.put(key, value);
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /* ************************** */
  /* CUSTOM GETTERS / SETTERS */
  /* ************************** */

  /**
   * DOCUMENT ME!
   *
   * @param  options  DOCUMENT ME!
   */
  public void setOptions(String[][] options) {
    for (String[] option : options) {
      putOption(option[0], option[1]);
    }
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * DOCUMENT ME!
   *
   * @param  options  DOCUMENT ME!
   */
  public void setOptions(Map<String, String> options) {
    this.options = options;
  }

} // end class ComboField
