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

package org.broadleafcommerce.common.site.domain;

/**
 * Stores the path to the theme. Created by bpolster.
 *
 * @author   $author$
 * @version  $Revision$, $Date$
 */
public class ThemeDTO implements Theme {
  //~ Instance fields --------------------------------------------------------------------------------------------------

  /** DOCUMENT ME! */
  public String name = "";

  /** DOCUMENT ME! */
  public String path = "";

  //~ Constructors -----------------------------------------------------------------------------------------------------

  /**
   * Creates a new ThemeDTO object.
   */
  public ThemeDTO() {
    // empty constructor
  }

  /**
   * Creates a new ThemeDTO object.
   *
   * @param  name  DOCUMENT ME!
   * @param  path  DOCUMENT ME!
   */
  public ThemeDTO(String name, String path) {
    this.name = name;
    this.path = path;
  }

  //~ Methods ----------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Theme#getName()
   */
  @Override public String getName() {
    return name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Theme#getPath()
   */
  @Override public String getPath() {
    return path;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Theme#setName(java.lang.String)
   */
  @Override public void setName(String name) {
    this.name = name;
  }

  //~ ------------------------------------------------------------------------------------------------------------------

  /**
   * @see  org.broadleafcommerce.common.site.domain.Theme#setPath(java.lang.String)
   */
  @Override public void setPath(String path) {
    this.path = path;
  }
} // end class ThemeDTO
