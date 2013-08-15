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

package org.broadleafcommerce.profile.extensibility;

/**
 * DOCUMENT ME!
 *
 * @author   jfischer
 * @version  $Revision$, $Date$
 */
public class ExtensibilityTestBean3 extends ExtensibilityTestBean {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  private String testProperty  = "new";
  private String testProperty3 = "none3";

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * The testProperty.
   *
   * @return  the testProperty
   */
  @Override public String getTestProperty() {
    return testProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * The testProperty3.
   *
   * @return  the testProperty3
   */
  public String getTestProperty3() {
    return testProperty3;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  testProperty  the testProperty to set
   */
  @Override public void setTestProperty(String testProperty) {
    this.testProperty = testProperty;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   *
   * DOCUMENT ME!
   *
   * @param  testProperty3  the testProperty3 to set
   */
  public void setTestProperty3(String testProperty3) {
    this.testProperty3 = testProperty3;
  }

} // end class ExtensibilityTestBean3
