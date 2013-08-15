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

package org.broadleafcommerce.openadmin.server.service.artifact.image.effects.chain;

/**
 * DOCUMENT ME!
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class UnmarshalledParameter {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private boolean applyFactor;

  private String name;
  private String type;
  private String value;

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The name.
   *
   * @return  the name
   */
  public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The type.
   *
   * @return  the type
   */
  public String getType() {
    return type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The value.
   *
   * @return  the value
   */
  public String getValue() {
    return value;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The applyFactor.
   *
   * @return  the applyFactor
   */
  public boolean isApplyFactor() {
    return applyFactor;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  applyFactor  the applyFactor to set
   */
  public void setApplyFactor(boolean applyFactor) {
    this.applyFactor = applyFactor;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  name  the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  type  the type to set
   */
  public void setType(String type) {
    this.type = type;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  value  the value to set
   */
  public void setValue(String value) {
    this.value = value;
  }

} // end class UnmarshalledParameter
